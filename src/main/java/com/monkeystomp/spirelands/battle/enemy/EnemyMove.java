package com.monkeystomp.spirelands.battle.enemy;

import java.lang.reflect.Method;

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
  // The animation method for the battle entity.
  private final Method moveAnimation;
  
  /**
   * A basic attack move. Physical & Offensive.
   */
  public static final EnemyMove BASIC_ATTACK = new EnemyMoveBuilder()
          .name("Basic Attack")
          .physicalAttack()
          .powerLevel(20)
          .accuracy(95)
          .ranged(false)
          .stabbingAnimation()
          .build();
  
  /**
   * Creates a new EnemyMove object with the given configuration from the EnemyMoveBuilder object.
   * @param builder Configuration object for creating this EnemyMove instance.
   */
  public EnemyMove(EnemyMoveBuilder builder) {
    this.name = builder.name;
    this.type = builder.type;
    this.variety = builder.variety;
    this.powerLevel = builder.powerLevel;
    this.accuracy = builder.accuracy;
    this.ranged = builder.ranged;
    this.moveAnimation = builder.moveAnimation;
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

  public Method getMoveAnimation() {
    return moveAnimation;
  }

}
