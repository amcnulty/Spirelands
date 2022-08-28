package com.monkeystomp.spirelands.quest;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.Item;

/**
 * A reward that is received after completion of a quest.
 * @author Aaron Michael McNulty
 */
public class QuestReward {
  /**
   * The display name of the reward.
   */
  private final String name;
  /**
   * The display sprite image of the reward.
   */
  private final Sprite rewardImage;

  /**
   * Creates a QuestReward object with the given name and reward image.
   * @param name The display name of the reward.
   * @param rewardImage The display sprite image of the reward.
   */
  public QuestReward(String name, Sprite rewardImage) {
    this.name = name;
    this.rewardImage = rewardImage;
  }
  
  /**
   * Returns a quest reward for the given amount of gold.
   * This will automatically use the gold indicator image.
   * @param amount The amount of gold for this reward
   * @return A QuestReward object for the given amount of gold.
   */
  public static QuestReward goldReward(String amount) {
    return new QuestReward("Gold", Sprite.GOLD_INDICATOR);
  }
  
  /**
   * Returns a quest reward for the given item.
   * This will automatically use the item thumbnail image.
   * @param item The item to set as the reward.
   * @return A QuestReward object for the given item.
   */
  public static QuestReward itemReward(Item item) {
    return new QuestReward(item.getTitle(), item.getThumbnail());
  }

  /**
   * The display name of the reward.
   * @return The display name of the reward.
   */
  public String getName() {
    return name;
  }

  /**
   * The display sprite image of the reward.
   * @return The display sprite image of the reward.
   */
  public Sprite getRewardImage() {
    return rewardImage;
  }

}
