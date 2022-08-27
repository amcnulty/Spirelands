package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.quest.Quest;
import java.util.ArrayList;

/**
 * Used to show the details of a quest when a quest list item is selected in the game menu.
 * @author Aaron Michael McNulty
 */
public class QuestDetailModal {
  
  private final int modalLeft = 140,
                    modalTop = 33,
                    modalWidth = 239,
                    modalHeight = 135,
                    modalCenterX = modalLeft + modalWidth / 2,
                    modalCenterY = modalTop + modalHeight / 2,
                    modalPadding = 5,
                    titleY = modalTop + modalPadding + 2,
                    subTitleY = titleY + 9,
                    rewardLabelX = 348,
                    rewardLabelY = subTitleY,
                    rewardDividerHeight = modalHeight - 28,
                    rewardDividerX = 340,
                    rewardDividerY = modalCenterY - rewardDividerHeight / 2,
                    descriptionX = modalLeft + modalPadding + 5,
                    descriptionY = subTitleY + 10,
                    descriptionWidth = modalWidth - 55,
                    spaceBetweenLines = 7,
                    maxNumberOfLines = 14,
                    previousButtonX = modalLeft + modalPadding + 16,
                    nextButtonX = modalLeft + modalWidth - modalPadding - 45,
                    pageButtonY = modalTop + modalHeight - modalPadding - 3;
  private int page = 0;
  private final FontInfo  title = GameFonts.getGAME_MENU_HEADLINE(),
                          subTitle = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          rewardsLabel = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final ArrayList<FontInfo> description = new ArrayList<>();
  private final String times = "\u2A2F";
  private final GameMenuSecondaryButton exitButton,
                                        previousButton = new GameMenuSecondaryButton("Previous",
                                          GameFonts.getPrimaryButtonText(),
                                          previousButtonX,
                                          pageButtonY,
                                          30,
                                          11,
                                          () -> page--
                                        ),
                                        nextButton = new GameMenuSecondaryButton("Next",
                                          GameFonts.getPrimaryButtonText(),
                                          nextButtonX,
                                          pageButtonY,
                                          20,
                                          11,
                                          () -> page++
                                        );
  private final Sprite  backdrop = new Sprite(271, 155, GameColors.BLACK),
                        background = new Sprite(modalWidth, modalHeight, GameColors.GAME_MENU_BACKGROUND),
                        rewardDivider = new Sprite(1, rewardDividerHeight, GameColors.GAME_MENU_MUTED_TEXT);
  private Quest quest;
  
  public QuestDetailModal(ICallback onExitButtonClick) {
    exitButton = new GameMenuSecondaryButton(
      times,
      modalLeft + modalPadding,
      modalTop + modalPadding,
      9,
      9,
      onExitButtonClick
    );
    rewardsLabel.setText("Rewards");
    rewardsLabel.setX(rewardLabelX);
    rewardsLabel.setY(rewardLabelY);
  }
  
  private void setFontInfo() {
    title.setText(quest.getName());
    title.setX(modalCenterX);
    title.centerText();
    title.setY(titleY);
    subTitle.setText(quest.getType().toString() + " Quest");
    subTitle.setX(modalCenterX);
    subTitle.centerText();
    subTitle.setY(subTitleY);
    
    description.clear();
    TextUtil.createWrappedText(quest.getDescription(), GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL().getFont(), descriptionWidth)
      .forEach(line -> {
        FontInfo newLine = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
        newLine.setText(line);
        newLine.setX(descriptionX);
        newLine.setY(descriptionY + (description.size() % maxNumberOfLines) * spaceBetweenLines);
        description.add(newLine);
      });
  }

  /**
   * Set the quest to display details about in the modal.
   * @param quest Quest to show details of.
   */
  public void setQuest(Quest quest) {
    this.quest = quest;
    setFontInfo();
  }
  
  public void update() {
    exitButton.update();
    if (page > 0) {
      previousButton.update();
    }
    if (description.size() > maxNumberOfLines && (page + 1) * maxNumberOfLines < description.size()) {
      nextButton.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 124, 23, backdrop, .7f, false);
    screen.renderSprite(gl, modalLeft, modalTop, background, false);
    exitButton.render(screen, gl);
    screen.renderFonts(title);
    screen.renderFonts(subTitle);
    screen.renderFonts(rewardsLabel);
    screen.renderSprite(gl, rewardDividerX, rewardDividerY, rewardDivider, false);
    for (FontInfo line: description.subList(page * maxNumberOfLines, (page + 1) * maxNumberOfLines > description.size() ? description.size() : (page + 1) * maxNumberOfLines)) {
      screen.renderFonts(line);
    }
    if (page > 0) {
      previousButton.render(screen, gl);
    }
    if (description.size() > maxNumberOfLines && (page + 1) * maxNumberOfLines < description.size()) {
      nextButton.render(screen, gl);
    }
  }

}
