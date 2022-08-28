package com.monkeystomp.spirelands.quest;

import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
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
   * Additional text to show along with the description.
   */
  private final ArrayList<String> paragraphs = new ArrayList<>();
  /**
   * The current status of this quest.
   */
  private QuestStatus status;
  /**
   * Rewards for completion of this quest.
   */
  private final ArrayList<QuestReward> questRewards = new ArrayList<>();
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
  
  private Quest addParagraph(String text) {
    paragraphs.add(text);
    return this;
  }
  
  private Quest addReward(QuestReward questReward) {
    questRewards.add(questReward);
    return this;
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
   * Additional text to show along with the description.
   * @return Additional text to show along with the description.
   */
  public ArrayList<String> getParagraphs() {
    return paragraphs;
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
   * Rewards for completion of this quest.
   * @return Rewards for completion of this quest.
   */
  public ArrayList<QuestReward> getQuestRewards() {
    return questRewards;
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
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them."
    ).addParagraph("Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess.")
      .addReward(QuestReward.goldReward("200"))
      .addReward(QuestReward.itemReward(Item.RIBS))
      .addReward(QuestReward.itemReward(Item.DARK_SWORD));
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 1",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 2",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 3",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 4",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 5",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 6",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 7",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 8",
      "It is of vital importance that you save the princess from the evildoers in the far lands of Kathertal where they have taken her. You have heard rumors that she may be sentenced to death by the end of the month for reasons yet unknown. What is known is that your home land will never be the same if the princess is allowed to be executed by these evil tyrants. You have been instructed by the king to set out immediately and have the royal guard at your side should you need them. Best of luck traveler! Remember, you will be rewarded handsomely for your good deeds upon return with the beloved princess."
    );
    
    new Quest(
      QuestType.Main,
      "Test Main Quest 9",
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

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 1",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 2",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 3",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 4",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 5",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 6",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 7",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 8",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 9",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 10",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 11",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 12",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 13",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 14",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 15",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 16",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 17",
      "All good swordsmen know how to strike but do you know how to perform a heavy strike? This powerful move will help you defeat your enemies with less effort. Only a master of the sword can learn this move and it must be taught to you by someone who already knows it themselves. Search the world high and low for a master swordsman who is willing to be your master and teach you this special move."
    );

    new Quest(
      QuestType.Secondary,
      "Test Secondary Quest 18",
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

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 1",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 2",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 3",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 4",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 5",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 6",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 7",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 8",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 9",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 10",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 11",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 12",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 13",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 14",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 15",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 16",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 17",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 18",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 19",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 20",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 21",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 22",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 23",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 24",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 25",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 26",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 27",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 28",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 29",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );

    new Quest(
      QuestType.Collectible,
      "Test Collectible Quest 30",
      "Ever since you were a little boy you've been fascinated by rare coins. Recently you learned there is a town full of unique and rare items where collectors gather to show off their prized collections. If you can impress the locals with your coin collection perhaps they will let you in on the secrets they hold. Collect coins on your travels and go to the far off town to complete this collectibles quest."
    );
  }

}
