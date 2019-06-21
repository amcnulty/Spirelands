package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.graphics.Sprite;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * The BattleMoveBuilder class is used to configure a BattleMove and call its constructor.
 * </p>
 * <h3>
 * Example Usage:
 * </h3>
 * <pre>
 * {@code
 * private BattleMove
 *   fireAttack = new BattleMoveBuilder()
 *     .name("Fire Attack")
 *     .magicalAttack()
 *     .powerLevel(30)
 *     .accuracy(100)
 *     .ranged(true)
 *     .build();}
 * </pre>
 * @author Aaron Michael McNulty
 */
public class BattleMoveBuilder {
  
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
   * The thumbnail for this move.
   */
  public Sprite thumbnail;
  /**
   * Sets the name of the move.
   * @param name Display name for the move.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder name(String name) {
    this.name = name;
    return this;
  }
  /**
   * Sets the type as physical and the variety as offensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder physicalAttack() {
    this.type = BattleMove.PHYSICAL;
    this.variety = BattleMove.OFFENSIVE;
    return this;
  }
  /**
   * Sets the type as physical and the variety as defensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder physicalDefensive() {
    this.type = BattleMove.PHYSICAL;
    this.variety = BattleMove.DEFENSIVE;
    return this;
  }
  /**
   * Sets the type as magical and the variety as offensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder magicalAttack() {
    this.type = BattleMove.MAGICAL;
    this.variety = BattleMove.OFFENSIVE;
    return this;
  }
  /**
   * Sets the type as magical and the variety as defensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder magicalDefensive() {
    this.type = BattleMove.MAGICAL;
    this.variety = BattleMove.DEFENSIVE;
    return this;
  }
  /**
   * Sets the power level to the given amount.
   * @param amount Integer value to set the power level to.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder powerLevel(int amount) {
    this.powerLevel = amount;
    return this;
  }
  /**
   * Sets the accuracy level to the given amount.
   * @param amount Integer representation of the percent accuracy i.e. 95 = 95%.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder accuracy(int amount) {
    this.accuracy = amount;
    return this;
  }
  /**
   * Sets this move to ranged if passed true.
   * @param isRanged Flag for if this move should be ranged.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder ranged(boolean isRanged) {
    this.ranged = isRanged;
    return this;
  }
  /**
   * Sets the move animation to stabbing animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder stabbingAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playStabbingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to swinging animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder swingingAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playSwingingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to shooting animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder shootingAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playShootingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to physical skill animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder physicalSkillAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playUsePhysicalSkillAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to magical skill animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder magicalSkillAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playUseMagicalSkillAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the move animation to use item animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder useItemAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playUseItemAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Sets the thumbnail for this move.
   * @param thumbnail The thumbnail sprite for this move.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder thumbnail(Sprite thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }
  /**
   * Builds this enemy move by calling the EnemyMove class constructor with this instance of BattleMoveBuilder as the argument.
   * @return The newly created EnemyMove.
   */
  public BattleMove build() {
    return new BattleMove(this);
  }
  
}