package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.quest.Quest;
import com.monkeystomp.spirelands.quest.QuestStatus;
import com.monkeystomp.spirelands.quest.QuestType;
import java.awt.Color;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * List item for displaying quests in the game menu.
 * @author Aaron Michael McNulty
 */
public class QuestListItem {
  
  private final int x,
                    y,
                    textX,
                    exclamationY,
                    nameY,
                    descriptionY,
                    buttonX,
                    buttonY,
                    buttonWidth = 259,
                    buttonHeight = 16;
  private final Quest quest;
  private final FontInfo questName = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo exclamation = GameFonts.getGAME_MENU_HEADLINE();
  private final FontInfo questDescription = GameFonts.getGAME_MENU_MUTED_TEXT();
  private final Sprite indicatorBar;
  private boolean isNewQuest;
  private final GameMenuSecondaryButton listItemButton;
  private static final HashMap<QuestType, Integer> QUEST_TYPE_TO_COLOR_MAP = new HashMap<>();
  
  static {
    QUEST_TYPE_TO_COLOR_MAP.put(QuestType.Main, GameColors.PRIMARY_BLUE);
    QUEST_TYPE_TO_COLOR_MAP.put(QuestType.Secondary, GameColors.PASTEL_GREEN);
    QUEST_TYPE_TO_COLOR_MAP.put(QuestType.Collectible, GameColors.BRIGHT_GOLD);
  }
  
  public QuestListItem(int x, int y, Quest quest, Consumer<Quest> onListItemClick) {
    this.x = x;
    this.y = y;
    this.buttonX = x + buttonWidth / 2;
    this.buttonY = y + buttonHeight / 2;
    this.quest = quest;
    this.textX = x + 8;
    this.exclamationY = y + 7;
    this.nameY = y + 3;
    this.descriptionY = nameY + 8;
    this.isNewQuest = quest.getStatus().equals(QuestStatus.New);
    exclamation.setText("!");
    exclamation.setX(textX);
    exclamation.setY(exclamationY);
    exclamation.setColor(new Color(GameColors.RED));
    questName.setText(quest.getName());
    questName.setX(textX + (isNewQuest ? 5 : 0));
    questName.setY(nameY);
    questDescription.setText(quest.getDescription().substring(0, 80) + "...");
    questDescription.setX(textX + (isNewQuest ? 5 : 0));
    questDescription.setY(descriptionY);
    indicatorBar = new Sprite(4, 16, QUEST_TYPE_TO_COLOR_MAP.get(quest.getType()));
    listItemButton = new GameMenuSecondaryButton("", buttonX, buttonY, buttonWidth, buttonHeight, () -> onListItemClick.accept(quest));
  }
  
  public void update() {
    if (isNewQuest) {
      isNewQuest = quest.getStatus().equals(QuestStatus.New);
      if (!isNewQuest) {
        questName.setX(textX);
        questDescription.setX(textX);
      }
    }
    listItemButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    listItemButton.render(screen, gl);
    screen.renderSprite(gl, x, y, indicatorBar, false);
    if (isNewQuest) screen.renderFonts(exclamation);
    screen.renderFonts(questName);
    screen.renderFonts(questDescription);
  }

}
