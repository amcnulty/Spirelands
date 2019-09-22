package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.elemental.Elemental;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.Item;
import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

/**
 * Battle Move class is used to describe a move that can be made by a battle entity during a battle sequence.
 * @author Aaron Michael McNulty
 */
public class BattleMove implements Cloneable {
  /**
   * Used for the 'type' property for physical moves.
   */
  public static final String PHYSICAL = "Physical";
  /**
   * Used for the 'type' property for magical moves.
   */
  public static final String MAGICAL = "Magical";
  /**
   * Used for 'type' for item moves.
   */
  public static final String ITEM = "Item";
  /**
   * Used for the 'action' property for offensive moves.
   */
  public static final String OFFENSIVE = "Offensive";
  /**
   * Used for the 'action' property for defensive moves.
   */
  public static final String DEFENSIVE = "Defensive";
  // Map of all the moves to their ids.
  public static final HashMap<Integer, BattleMove> MOVE_MAP = new HashMap<>();
  // Instance of the Random class.
  private static final Random random = new Random();
  // Used to track the ids when creating moves.
  private static int  nextItemId = 1000,
                      nextPhysicalId = 2000,
                      nextMagicalId = 3000;
  // The id of the move.
  private final int id;
  // The name of the move.
  private String name;
  // The elemental type of the move.
  private String element;
  // Physical or magical type.
  private final String type;
  // Offensive or defensive action.
  private final String action;
  // Power level of the move.
  private final int powerLevel;
  // Amount of mana requited for this move.
  private final int manaRequired;
  // Accuracy of the move.
  private final int accuracy;
  // Is the move a ranged move?
  private final boolean ranged;
  // The animation method for the battle entity.
  private final Method moveAnimation;
  // The thumbnail for this move.
  private final Sprite thumbnail;
  // The sound effect for this move.
  private final File sound;
  // The animation this move makes on the target.
  private final AnimatedSprite targetAnimation;
  // Delay in milliseconds after playing the move animation before playing the target animation.
  private final int targetAnimationDelay;
  // A consumer to define the action to take on a defensive move. Returns a FlashMessage object.
  private final Function<MoveInformation, FlashMessage> defensiveAction;
  // Flag to set this move as a single target move that only targets the user.
  private final boolean singleTarget;
  // Item associated with item move.
  private final Item item;
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!        Move Animations         !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Slashing animation for physical attacks.
   */
  public static final AnimatedSprite
  BASIC_SLASH = new AnimatedSprite(
          128,
          32,
          new SpriteSheet("./resources/animations/slash/basic_slash.png"),
          AnimatedSprite.MEDIUM,
          6,
          true
  );
  /**
   * Explosion with blue flames for magic attacks.
   */
  public static final AnimatedSprite
  BLUE_EXPLOSION = new AnimatedSprite(
          128,
          48,
          new SpriteSheet("./resources/animations/magic/blueExplosion/blue_explosion.png"),
          AnimatedSprite.FAST,
          30,
          true
  );
  /**
   * Colorful animation for healing moves.
   */
  public static final AnimatedSprite
  CURE_ANIMATION = new AnimatedSprite(
          192,
          48,
          new SpriteSheet("./resources/animations/magic/cure/cure.png"),
          AnimatedSprite.MEDIUM,
          20,
          true
  );
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!             Moves              !!
   *          !!                                !!
   *          !!################################!!
   */
  
  /**
   * A basic attack move. Physical & Offensive.
   */
  public static final BattleMove BASIC_ATTACK = new BattleMoveBuilder()
          .name("Basic Attack")
          .physicalAttack()
          .powerLevel(20)
          .accuracy(95)
          .ranged(false)
          .stabbingAnimation()
          .thumbnail(Item.COMMON_SWORD.getThumbnail())
          .sound(SoundEffects.QUICK_HIT)
          .targetAnimation(BASIC_SLASH)
          .build();
  /**
   * A basic typeless magic attack. Magical & Offensive.
   */
  public static final BattleMove MAGIC_ENERGY = new BattleMoveBuilder()
          .name("Magic Energy")
          .magicalAttack()
          .powerLevel(20)
          .manaRequired(5)
          .accuracy(100)
          .ranged(true)
          .magicalSkillAnimation()
          .thumbnail(Item.PUPIL_WAND.getThumbnail())
          .sound(SoundEffects.MAGICAL_ENERGY)
          .targetAnimation(BLUE_EXPLOSION)
          .build();
  /**
   * A stronger but less accurate attack. Physical & Offensive.
   */
  public static final BattleMove BLUNT_FORCE = new BattleMoveBuilder()
          .name("Blunt Force")
          .physicalAttack()
          .powerLevel(30)
          .accuracy(60)
          .ranged(false)
          .swingingAnimation()
          .thumbnail(Item.BROADAXE.getThumbnail())
          .sound(SoundEffects.STRONG_MALE_ATTACK)
          .build();
  /**
   * A cure spell for recovering HP. Magical & Defensive.
   */
  public static final BattleMove CURE = new BattleMoveBuilder()
          .name("Cure")
          .magicalDefensive()
          .powerLevel(20)
          .manaRequired(5)
          .accuracy(100)
          .ranged(true)
          .magicalSkillAnimation()
          .thumbnail(new Sprite(16, 16, 3, 13, SpriteSheet.itemsSheet))
          .sound(SoundEffects.HEALING_SOUND)
          .targetAnimation(CURE_ANIMATION)
          .defensiveAction(moveInfo -> {
            int attackPower, overallEffect;
            attackPower = (int)(moveInfo.getMove().getPowerLevel() * moveInfo.getUser().getStatModel().getIntellect() * ( .1 + ( .009 * ( moveInfo.getUser().getStatModel().getLevel() ))));
            overallEffect = (int)( attackPower * ( 1 + (  ( random.nextDouble() - .5 ) / 5 )));
            moveInfo.getTarget().getStatModel().increaseHealth(overallEffect);
            FlashMessage message = new FlashMessage(moveInfo.getTarget(), String.valueOf(overallEffect));
            message.setColor(new Color(GameColors.ATB_GAUGE_BAR_FILLED));
            message.floatMessageUp(true);
            return message;
          })
          .build();
  /**
   * A defensive move to limit physical damage. Physical & Defensive.
   */
  public static final BattleMove GUARD = new BattleMoveBuilder()
          .name("Guard")
          .physicalDefensive()
          .guardAnimation()
          .thumbnail(new Sprite(16, 16, 9, 0, SpriteSheet.itemsSheet))
          .defensiveAction(moveInfo -> {
            moveInfo.getUser().setGuarding(true);
            moveInfo.getUser().returnToIdleState();
            return null;
          })
          .singleTarget(true)
          .build();
  
  /**
   * A basic fire magic attack. Magical & Offensive.
   */
  public static final BattleMove FIREBALL = new BattleMoveBuilder()
          .name("Fireball")
          .element(Elemental.FIRE)
          .magicalAttack()
          .powerLevel(20)
          .manaRequired(5)
          .ranged(true)
          .magicalSkillAnimation()
          .thumbnail(new Sprite(16, 16, 6, 11, SpriteSheet.itemsSheet))
          .sound(SoundEffects.MAGICAL_ENERGY)
          .targetAnimation(BLUE_EXPLOSION)
          .build();
  /**
   * A basic water attack. Magical & Offensive.
   */
  public static final BattleMove TIDAL_WAVE = new BattleMoveBuilder()
          .name("Tidal Wave")
          .element(Elemental.WATER)
          .magicalAttack()
          .powerLevel(20)
          .manaRequired(5)
          .ranged(true)
          .magicalSkillAnimation()
          .thumbnail(new Sprite(16, 16, 0, 13, SpriteSheet.itemsSheet))
          .sound(SoundEffects.MAGICAL_ENERGY)
          .targetAnimation(BLUE_EXPLOSION)
          .build();
  /**
   * Creates a new EnemyMove object with the given configuration from the EnemyMoveBuilder object.
   * @param builder Configuration object for creating this EnemyMove instance.
   */
  public BattleMove(BattleMoveBuilder builder) {
    this.type = builder.type;
    this.id = getNextId();
    this.name = builder.name;
    this.element = builder.element;
    this.action = builder.action;
    this.powerLevel = builder.powerLevel;
    this.manaRequired = builder.manaRequired;
    this.accuracy = builder.accuracy;
    this.ranged = builder.ranged;
    this.moveAnimation = builder.moveAnimation;
    this.thumbnail = builder.thumbnail;
    this.sound = builder.sound;
    this.targetAnimation = builder.targetAnimation;
    this.targetAnimationDelay = builder.targetAnimationDelay;
    this.defensiveAction = builder.defensiveAction;
    this.singleTarget = builder.singleTarget;
    this.item = builder.item;
  }
  
  static {
    BattleMove.BASIC_ATTACK.setMap();
  }
  
  public void setMap() {
    Field[] fields = BattleMove.class.getFields();
    BattleMove fieldValue;
    for (Field fieldDef: fields) {
      try {
        fieldDef.setAccessible(true);
        fieldValue = (BattleMove)fieldDef.get(this);
        Method getId = fieldValue.getClass().getDeclaredMethod("getId", new Class[]{});
        BattleMove.MOVE_MAP.put((Integer)getId.invoke(fieldValue), fieldValue);
      } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException | ClassCastException ex) {
        // Fail silently
        //Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  public static void createBattleMoveFromItem(Item item, String action, AnimatedSprite targetAnimation) {
    BattleMove itemMove = new BattleMoveBuilder().itemMove(item, action, targetAnimation).build();
    MOVE_MAP.put(
      itemMove.getId(),
      itemMove
    );
  }
  
  private int getNextId() {
    switch (getType()) {
      case BattleMove.ITEM:
        return nextItemId++;
      case BattleMove.PHYSICAL:
        return nextPhysicalId++;
      case BattleMove.MAGICAL:
        return nextMagicalId++;
      default:
        return 0;
    }
  }

  /**
   * Overrides the name property for this move. Only do this with cloned instances and not the original static member of the BattleMove class.
   * @param customName The custom name to give to this move.
   */
  public void setCustomName(String customName) {
    this.name = customName;
  }
  
  public int getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }

  public String getElement() {
    return element;
  }

  public String getType() {
    return type;
  }

  public String getAction() {
    return action;
  }

  public int getPowerLevel() {
    return powerLevel;
  }

  public int getManaRequired() {
    return manaRequired;
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

  public Sprite getThumbnail() {
    return thumbnail;
  }
  /**
   * Checks if this move has a sound associated with it.
   * @return True if there is a sound for this move otherwise false.
   */
  public boolean hasSound() {
    return sound != null;
  }

  public File getSound() {
    return sound;
  }
  /**
   * Checks if this move has a target animation associated with it.
   * @return True if there is a target animation otherwise false.
   */
  public boolean hasTargetAnimation() {
    return targetAnimation != null;
  }

  public AnimatedSprite getTargetAnimation() {
    return targetAnimation;
  }

  public int getTargetAnimationDelay() {
    return targetAnimationDelay;
  }

  public Function<MoveInformation, FlashMessage> getDefensiveAction() {
    return defensiveAction;
  }

  public boolean isSingleTarget() {
    return singleTarget;
  }

  public Item getItem() {
    return item;
  }
  
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}
