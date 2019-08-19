package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.EquipmentItem;
import com.monkeystomp.spirelands.inventory.Item;
import java.io.File;
import java.lang.reflect.Method;
import java.util.function.Function;
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
 *     .manaRequired(20)
 *     .accuracy(100)
 *     .ranged(true)
 *     .magicalSkillAnimation()
 *     .thumbnail(Item.PUPIL_WAND.getThumbnail())
 *     .sound(SoundEffects.MAGICAL_ENERGY)
 *     .targetAnimation(blueExplosion)
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
  public int powerLevel = 0;
  /**
   * The amount of mana required for this move. Default is zero if not specified.
   */
  public int manaRequired = 0;
  /**
   * The accuracy value of this move.
   */
  public int accuracy = 100;
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
   * The sound effect for this move.
   */
  public File sound;
  /**
   * The animation this move makes on the target.
   */
  public AnimatedSprite targetAnimation;
  /**
   * Delay in milliseconds after playing the move animation before playing the target animation.
   */
  public int targetAnimationDelay = 0;
  /**
   * A consumer to define the action to take on a defensive move.
   */
  public Function<MoveInformation, FlashMessage> action;
  /**
   * Flag to set this move as a single target move that only targets the user.
   */
  public boolean singleTarget = false;
  /**
   * Item associated with item move.
   */
  public Item item;
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
   * Sets the amount of mana required for this move.
   * @param amount Integer value to set to the mana required value.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder manaRequired(int amount) {
    this.manaRequired = amount;
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
   * Sets the move animation to guard animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder guardAnimation() {
    try {
      this.moveAnimation = BattleEntity.class.getDeclaredMethod("playGuardAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(BattleMoveBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
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
    targetAnimationDelay = 600;
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
    targetAnimationDelay = 600;
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
   * Sets the sound effect for this move.
   * @param sound The file location of the sound resource.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder sound(File sound) {
    this.sound = sound;
    return this;
  }
  /**
   * Sets the animation that plays over the target for this move.
   * @param animation AnimatedSprite with the desired animation.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder targetAnimation(AnimatedSprite animation) {
    this.targetAnimation = animation;
    return this;
  }
  /**
   * Sets the action to take on defensive moves for this move.
   * @param action The BiConsumer that takes the user and target.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder action(Function<MoveInformation, FlashMessage> action) {
    this.action = action;
    return this;
  }
  /**
   * Sets the single target flag for this move.
   * @param singleTarget Indicates if the move can only target the user.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder singleTarget(boolean singleTarget) {
    this.singleTarget = singleTarget;
    return this;
  }
  
  public BattleMoveBuilder itemMove(Item item, String variety, AnimatedSprite targetAnimation) {
    this.item = item;
    if (this.item instanceof EquipmentItem) ((EquipmentItem)this.item).setEquippable(true);
    this.name = item.getTitle();
    this.type = BattleMove.ITEM;
    this.variety = variety;
    this.ranged = true;
    useItemAnimation();
    this.thumbnail = item.getThumbnail();
    this.targetAnimation = targetAnimation;
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
