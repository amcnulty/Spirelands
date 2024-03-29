package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.BattleItem;
import com.monkeystomp.spirelands.inventory.EquipmentItem;
import com.monkeystomp.spirelands.inventory.Item;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
   * The description of the move.
   */
  public String description;
  /**
   * The level of this move.
   */
  public int level;
  /**
   * The elemental type of this move.
   */
  public String element;
  /**
   * The type of move Physical | Magical
   */
  public String type;
  /**
   * The action of move Offensive | Defensive | Buff
   */
  public String action;
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
  public boolean ranged = false;
  /**
   * Flag to check if this move is multi-target.
   */
  public boolean multiTarget = false;
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
  public Function<MoveInformation, FlashMessage> defensiveAction;
  /**
   * Flag to set this move as a single target move that only targets the user.
   */
  public boolean singleTarget = false;
  /**
   * Item associated with item move.
   */
  public Item item;
  /**
   * Buff for this move.
   */
  public final Buff buff = new Buff();
  /** 
   * he list of attributes for this BattleMove object.
   */
  public final ArrayList<BattleMoveAttribute> attributes = new ArrayList<>();
  /**
   * Sets the name of the move. Keep name length less than 20 characters.
   * @param name Display name for the move.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder name(String name) {
    this.name = name;
    return this;
  }
  /**
   * Sets the description text for the move.
   * @param description Description to show for this move.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder description(String description) {
    this.description = description;
    return this;
  }
  /**
   * Sets the level of the move.
   * @param level Level to set the move to.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder level(int level) {
    this.level = level;
    return this;
  }
  /**
   * Sets the element of the move.
   * @param element Element to set for the move.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder element(String element) {
    this.element = element;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ELEMENT, element));
    return this;
  }
  /**
   * Sets the type as physical and the action as offensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder physicalAttack() {
    this.type = BattleMove.PHYSICAL;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.TYPE, BattleMove.PHYSICAL));
    this.action = BattleMove.OFFENSIVE;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACTION, BattleMove.OFFENSIVE));
    return this;
  }
  /**
   * Sets the type as physical and the action as defensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder physicalDefensive() {
    this.type = BattleMove.PHYSICAL;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.TYPE, BattleMove.PHYSICAL));
    this.action = BattleMove.DEFENSIVE;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACTION, BattleMove.DEFENSIVE));
    return this;
  }
  /**
   * Sets the type as magical and the action as offensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder magicalAttack() {
    this.type = BattleMove.MAGICAL;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.TYPE, BattleMove.MAGICAL));
    this.action = BattleMove.OFFENSIVE;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACTION, BattleMove.OFFENSIVE));
    return this;
  }
  /**
   * Sets the type as magical and the action as defensive.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder magicalDefensive() {
    this.type = BattleMove.MAGICAL;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.TYPE, BattleMove.MAGICAL));
    this.action = BattleMove.DEFENSIVE;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACTION, BattleMove.DEFENSIVE));
    return this;
  }
  /**
   * Sets the type as magical and the action as buff.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder magicalBuff() {
    this.type = BattleMove.MAGICAL;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.TYPE, BattleMove.MAGICAL));
    this.action = BattleMove.BUFF;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACTION, BattleMove.BUFF));
    return this;
  }
  /**
   * Sets the power level to the given amount.
   * @param amount Integer value to set the power level to.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder powerLevel(int amount) {
    this.powerLevel = amount;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.POWER_LEVEL, String.valueOf(amount)));
    return this;
  }
  /**
   * Sets the amount of mana required for this move.
   * @param amount Integer value to set to the mana required value.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder manaRequired(int amount) {
    this.manaRequired = amount;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.MANA_REQUIRED, String.valueOf(amount)));
    return this;
  }
  /**
   * Sets the accuracy level to the given amount.
   * @param amount Integer representation of the percent accuracy i.e. 95 = 95%.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder accuracy(int amount) {
    if (amount != 100) {
      this.accuracy = amount;
      attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACCURACY, String.valueOf(amount) + "%"));
    }
    return this;
  }
  /**
   * Sets this move to ranged.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder ranged() {
    this.ranged = true;
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.RANGED, "true"));
    return this;
  }
  /**
   * Sets this move as a multi-target move.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder multiTarget() {
    this.multiTarget = true;
      attributes.add(new BattleMoveAttribute(BattleMoveAttribute.MULTI_TARGET, "true"));
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
   * Sets the attack buff modifier for this move. A value of .2 would mean attack power is increased by 20%.
   * @param percent The modifier amount for the attack stat. Values should be less than 1.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder attackBuff(double percent) {
    this.buff.setAttackBuff(percent);
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ATTACK_BUFF, String.valueOf((int)(percent * 100)) + "%"));
    return this;
  }
  /**
   * Sets the defensive buff modifier for this move. A value of .2 would mean all physical attacks are reduced by 20%.
   * @param percent The modifier amount for the defense stat. Values should be less than 1.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder defensiveBuff(double percent) {
    this.buff.setDefenseBuff(percent);
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.DEFENSE_BUFF, String.valueOf((int)(percent * 100)) + "%"));
    return this;
  }
  /**
   * Sets the intellect buff modifier for this move. A value of .2 would mean magic attack power is increased by 20%.
   * @param percent The modifier amount for the intellect stat. Values should be less than 1.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder intellectBuff(double percent) {
    this.buff.setIntellectBuff(percent);
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.INTELLECT_BUFF, String.valueOf((int)(percent * 100)) + "%"));
    return this;
  }
  /**
   * Sets the magical buff modifier for this move. A value of .2 would mean all magical attacks are reduced by 20%.
   * @param percent The modifier amount for the spirit stat. Values should be less than 1.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder spiritBuff(double percent) {
    this.buff.setSpiritBuff(percent);
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.SPIRIT_BUFF, String.valueOf((int)(percent * 100)) + "%"));
    return this;
  }
  /**
   * Sets the elemental effect of this move with the type and percentage.
   * @param elEffect ElementalEffect object to set the type and percentage of the effect.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder addElementalBuff(ElementalEffect elEffect) {
    this.buff.addElementalEffect(elEffect);
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ELEMENTAL_EFFECT_MAP.get(elEffect.getElement()), String.valueOf(elEffect.getPercentage() - 100) + "%"));
    return this;
  }
  /**
   * Set the time in seconds for how long this buff will last.
   * @param time Time in seconds for buff to last.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder buffTime(int time) {
    this.buff.setBuffTime(time);
    attributes.add(new BattleMoveAttribute(BattleMoveAttribute.BUFF_TIME, String.valueOf(time) + "s"));
    return this;
  }
  /**
   * Sets the action to take on defensive moves for this move.
   * @param defensiveAction The BiConsumer that takes the user and target.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder defensiveAction(Function<MoveInformation, FlashMessage> defensiveAction) {
    this.defensiveAction = defensiveAction;
    return this;
  }
  /**
   * Sets this move to only target the user.
   * @return The BattleMoveBuilder reference.
   */
  public BattleMoveBuilder singleTarget() {
    this.singleTarget = true;
    return this;
  }
  /**
   * Creates a battle move from the given item with the given action and target animation.
   * @param item Item which to build the move from.
   * @param action 
   * @param targetAnimation
   * @return 
   */
  public BattleMoveBuilder itemMove(Item item, String action, AnimatedSprite targetAnimation) {
    this.item = item;
    if (this.item instanceof EquipmentItem) ((EquipmentItem)this.item).setEquippable(true);
    if (this.item instanceof BattleItem) {
      this.powerLevel = ((BattleItem)this.item).getPowerLevel();
      this.element = ((BattleItem)this.item).getElementalType();
    }
    this.name = item.getTitle();
    this.description = item.getDescription();
    this.type = BattleMove.ITEM;
    this.action = action;
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
    if (!this.multiTarget)
      attributes.add(new BattleMoveAttribute(BattleMoveAttribute.MULTI_TARGET, "false"));
    if (this.accuracy == 100)
      attributes.add(new BattleMoveAttribute(BattleMoveAttribute.ACCURACY, "100"));
    return new BattleMove(this);
  }
  
}
