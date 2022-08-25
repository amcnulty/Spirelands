package com.monkeystomp.spirelands.quest;

/**
 * Class used to describe a quest object in the game.
 * Quests are objectives for the player to complete and can be tracked in the game menu.
 * @author Aaron Michael McNulty
 */
public class Quest {
  
  private final QuestType type;
  private final String name;
  private final String description;
  private final QuestStatus status;
  
  public Quest(QuestType type, String name, String description) {
    this.type = type;
    this.name = name;
    this.description = description;
    this.status = QuestStatus.New;
  }

  public QuestType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public QuestStatus getStatus() {
    return status;
  }

}
