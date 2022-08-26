package com.monkeystomp.spirelands.quest;

import java.util.HashMap;

/**
 * Class used to describe a quest object in the game.
 * Quests are objectives for the player to complete and can be tracked in the game menu.
 * @author Aaron Michael McNulty
 */
public class Quest {
  
  /**
   * Unique id of this quest.
   */
  private final int id;
  /**
   * Next id to be given to instance of Quest.
   */
  private static int nextId = 1000;
  /**
   * The type of the quest.
   */
  private final QuestType type;
  /**
   * The name or title that will display for this quest.
   */
  private final String name;
  /**
   * The description of this quest.
   */
  private final String description;
  /**
   * The current status of this quest.
   */
  private QuestStatus status;
  /**
   * All quest instances mapped by their id.
   */
  private static final HashMap<Integer, Quest> QUEST_INDEX = new HashMap<>();
  
  /**
   * Creates a quest with the given input type, name and description
   * @param type The type of the quest.
   * @param name The name or title that will display for this quest.
   * @param description The description of this quest.
   */
  public Quest(QuestType type, String name, String description) {
    this.id = nextId++;
    this.type = type;
    this.name = name;
    this.description = description;
    this.status = QuestStatus.New;
    QUEST_INDEX.put(id, this);
  }

  /**
   * Unique id of this quest.
   * @return Unique id of this quest.
   */
  public int getId() {
    return id;
  }

  /**
   * The type of the quest.
   * @return The type of the quest.
   */
  public QuestType getType() {
    return type;
  }

  /**
   * The name or title that will display for this quest.
   * @return The name or title that will display for this quest.
   */
  public String getName() {
    return name;
  }

  /**
   * The description of this quest.
   * @return The description of this quest.
   */
  public String getDescription() {
    return description;
  }

  /**
   * The current status of this quest.
   * @return The current status of this quest.
   */
  public QuestStatus getStatus() {
    return status;
  }

  /**
   * Sets the current status of this quest.
   * @param status The current status of this quest.
   */
  public void setStatus(QuestStatus status) {
    this.status = status;
  }

 /**
  * All quest instances mapped by their id.
  * @return All quest instances mapped by their id.
  */
  public static HashMap<Integer, Quest> getQUEST_INDEX() {
    return QUEST_INDEX;
  }
  
  /**
   * List of quests starts here.
   */
  static {
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!           Main Quests          !!
   *          !!                                !!
   *          !!################################!!
   */
    new Quest(
      QuestType.Main,
      "Learn To Run",
      "It's important to know how to get around quickly in Spirelands. Running will help you get around much faster than just walking. Hold down the shift key as you are walking to make your character run. Now test your new skills in a race with Barry."
    );
    
    new Quest(
      QuestType.Main,
      "Save The Princess",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!        Secondary Quests        !!
   *          !!                                !!
   *          !!################################!!
   */
    new Quest(
      QuestType.Secondary,
      "Learn Heavy Strike",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!       Collectible Quests       !!
   *          !!                                !!
   *          !!################################!!
   */
    new Quest(
      QuestType.Collectible,
      "Coin Collector",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );
  }

}
