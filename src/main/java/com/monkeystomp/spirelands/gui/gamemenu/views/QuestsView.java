package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestTabs;
import com.monkeystomp.spirelands.quest.Quest;
import com.monkeystomp.spirelands.quest.QuestManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The game menu view for when users click on the quests button.
 * @author Aaron Michael McNulty
 */
public class QuestsView extends DisplayView {
  
  private final int questListItemX = 130,
                    questListItemStartingY = 40,
                    spaceBetweenItems = 20;
  private final QuestTabs questTabs = new QuestTabs();
  private final ArrayList<QuestListItem> questListItems = new ArrayList<>();
  private final HashMap<Integer, Quest> acceptedQuestsMap = QuestManager.getQuestManager().getAcceptedQuests();

  private void createListItems() {
    // Look at RecipesSubView.java for an example of pagination
    questListItems.clear();
    Set<Integer> keys = acceptedQuestsMap.keySet();
    keys.forEach(key -> {
      questListItems.add(
        new QuestListItem(
          questListItemX,
          questListItemStartingY + questListItems.size() * spaceBetweenItems,
          acceptedQuestsMap.get(key)
        )
      );
    });
    
  }
  
  @Override
  public void enteringView() {
    questTabs.resetTabs();
    createListItems();
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
    questTabs.update();
    for (QuestListItem questListItem: questListItems) {
      questListItem.update();
    }
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    questTabs.render(screen, gl);
    for (QuestListItem questListItem: questListItems) {
      questListItem.render(screen, gl);
    }
  }

}
