package com.monkeystomp.spirelands.battle.enemy;

/**
 * Enemy Move class is used to describe a move that can be made by an enemy during a battle sequence.
 * @author Aaron Michael McNulty
 */
public class EnemyMove {
  /**
   * Used for the 'type' property for physical moves.
   */
  public static final String PHYSICAL = "Physical";
  /**
   * Used for the 'type' property for magical moves.
   */
  public static final String MAGICAL = "Magical";
  /**
   * Used for the 'variety' property for offensive moves.
   */
  public static final String OFFENSIVE = "Offensive";
  /**
   * Used for the 'variety' property for defensive moves.
   */
  public static final String DEFENSIVE = "Defensive";
  // The name of the move.
  private final String name;
  // Physical or magical type.
  private final String type;
  // Offensive or defensive variety.
  private final String variety;
  // Power level of the move.
  private final int powerLevel;
  // Accuracy of the move.
  private final int accuracy;
  // Is the move a ranged move?
  private final boolean ranged;
  
  public EnemyMove(String name, String type, String variety, int powerLevel, int accuracy, boolean ranged) {
    this.name = name;
    this.type = type;
    this.variety = variety;
    this.powerLevel = powerLevel;
    this.accuracy = accuracy;
    this.ranged = ranged;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getVariety() {
    return variety;
  }

  public int getPowerLevel() {
    return powerLevel;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public boolean isRanged() {
    return ranged;
  }

}
