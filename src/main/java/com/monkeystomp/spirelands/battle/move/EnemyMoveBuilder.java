package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * The EnemyMoveBuilder class is used to configure an EnemyMove and call its constructor.
 * </p>
 * <h3>
 * Example Usage:
 * </h3>
 * <pre>
 * {@code
 * private EnemyMove
 *   fireAttack = new EnemyMoveBuilder()
 *     .name("Fire Attack")
 *     .magicalAttack()
 *     .powerLevel(30)
 *     .accuracy(100)
 *     .ranged(true)
 *     .build();}
 * </pre>
 * @author Aaron Michael McNulty
 */
public class EnemyMoveBuilder {
  
  private final Class<?>[] args = null;
  /**
   * The name of this move.
   */
  public String name;
  /**
   * The type of move Physical | Magical
   */
  public String type;
  /**
   * The variety of move Offensive | Defensive
   */
  public String variety;
  /**
   * The power level of this move.
   */
  public int powerLevel;
  /**
   * The accuracy value of this move.
   */
  public int accuracy;
  /**
   * Flag to check if this move is ranged.
   */
  public boolean ranged;
  /**
   * The battle entity animation for this move.
   */
  public Method moveAnimation;
  /**
   * Sets the name of the move.
   * @param name Display name for the move.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder name(String name) {
    this.name = name;
    return this;
  }
  /**
   * Sets the type as physical and the variety as offensive.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder physicalAttack() {
    this.type = EnemyMove.PHYSICAL;
    this.variety = EnemyMove.OFFENSIVE;
    return this;
  }
  /**
   * Sets the type as physical and the variety as defensive.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder physicalDefensive() {
    this.type = EnemyMove.PHYSICAL;
    this.variety = EnemyMove.DEFENSIVE;
    return this;
  }
  /**
   * Sets the type as magical and the variety as offensive.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder magicalAttack() {
    this.type = EnemyMove.MAGICAL;
    this.variety = EnemyMove.OFFENSIVE;
    return this;
  }
  /**
   * Sets the type as magical and the variety as defensive.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder magicalDefensive() {
    this.type = EnemyMove.MAGICAL;
    this.variety = EnemyMove.DEFENSIVE;
    return this;
  }
  /**
   * Sets the power level to the given amount.
   * @param amount Integer value to set the power level to.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder powerLevel(int amount) {
    this.powerLevel = amount;
    return this;
  }
  /**
   * Sets the accuracy level to the given amount.
   * @param amount Integer representation of the percent accuracy i.e. 95 = 95%.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder accuracy(int amount) {
    this.accuracy = amount;
    return this;
  }
  /**
   * Sets this move to ranged if passed true.
   * @param isRanged Flag for if this move should be ranged.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder ranged(boolean isRanged) {
    this.ranged = isRanged;
    return this;
  }
  /**
   * Sets the move animation to stabbing animation.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder stabbingAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playStabbingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to swinging animation.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder swingingAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playSwingingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to shooting animation.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder shootingAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playShootingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to physical skill animation.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder physicalSkillAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playUsePhysicalSkillAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to magical skill animation.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder magicalSkillAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playUseMagicalSkillAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to use item animation.
   * @return The EnemyMoveBuilder reference.
   */
  public EnemyMoveBuilder useItemAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playUseItemAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Builds this enemy move by calling the EnemyMove class constructor with this instance of EnemyMoveBuilder as the argument.
   * @return The newly created EnemyMove.
   */
  public EnemyMove build() {
    return new EnemyMove(this);
  }
  
}
