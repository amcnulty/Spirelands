package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.quest.Quest;
import com.monkeystomp.spirelands.quest.QuestType;
import java.util.HashMap;

/**
 * List item for displaying quests in the game menu.
 * @author Aaron Michael McNulty
 */
public class QuestListItem {
  
  private final int x,
                    y,
                    textX,
                    nameY,
                    descriptionY;
  private final FontInfo questName = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo questDescription = GameFonts.getGAME_MENU_MUTED_TEXT();
  private final Sprite indicatorBar;
  private static final HashMap<QuestType, Integer> QUEST_TYPE_TO_COLOR_MAP = new HashMap<>();
  
  static {
    QUEST_TYPE_TO_COLOR_MAP.put(QuestType.Main, GameColors.PRIMARY_BLUE);
    QUEST_TYPE_TO_COLOR_MAP.put(QuestType.Secondary, GameColors.PASTEL_GREEN);
    QUEST_TYPE_TO_COLOR_MAP.put(QuestType.Collectible, GameColors.BRIGHT_GOLD);
  }
  
  public QuestListItem(int x, int y, Quest quest) {
    this.x = x;
    this.y = y;
    this.textX = x + 8;
    this.nameY = y + 3;
    this.descriptionY = nameY + 8;
    questName.setText(quest.getName());
    questName.setX(textX);
    questName.setY(nameY);
    questDescription.setText(quest.getDescription().substring(0, 80) + "...");
    questDescription.setX(textX);
    questDescription.setY(descriptionY);
    indicatorBar = new Sprite(4, 16, QUEST_TYPE_TO_COLOR_MAP.get(quest.getType()));
  }
  
  public void update() {
    
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, indicatorBar, false);
    screen.renderFonts(questName);
    screen.renderFonts(questDescription);
  }

}
