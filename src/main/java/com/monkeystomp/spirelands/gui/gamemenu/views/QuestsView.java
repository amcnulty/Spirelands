package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestTabs;
import com.monkeystomp.spirelands.quest.Quest;
import com.monkeystomp.spirelands.quest.QuestType;
import java.util.ArrayList;

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

  private void createListItems() {
    questListItems.add(new QuestListItem(questListItemX, questListItemStartingY, new Quest(QuestType.Main, "Save The Princess", "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess.")));
    questListItems.add(new QuestListItem(questListItemX, questListItemStartingY + spaceBetweenItems, new Quest(QuestType.Secondary, "Learn Heavy Strike", "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move.")));
    questListItems.add(new QuestListItem(questListItemX, questListItemStartingY + 2 * spaceBetweenItems, new Quest(QuestType.Collectible, "Coin Collector", "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest.")));
    questListItems.add(new QuestListItem(questListItemX, questListItemStartingY + 3 * spaceBetweenItems, new Quest(QuestType.Main, "Save The Princess", "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess.")));
    questListItems.add(new QuestListItem(questListItemX, questListItemStartingY + 4 * spaceBetweenItems, new Quest(QuestType.Secondary, "Learn Heavy Strike", "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move.")));
    questListItems.add(new QuestListItem(questListItemX, questListItemStartingY + 5 * spaceBetweenItems, new Quest(QuestType.Collectible, "Coin Collector", "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest.")));
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
