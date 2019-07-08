package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.Item;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Battle Move class is used to describe a move that can be made by a battle entity during a battle sequence.
 * @author Aaron Michael McNulty
 */
public class BattleMove {
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
  // Map of all the moves to their ids.
  public static final HashMap<Integer, BattleMove> MOVE_MAP = new HashMap<>();
  // Used to track the ids when creating moves.
  private static int nextId = 0;
  // The id of the move.
  private final int id;
  // The name of the move.
  private final String name;
  // Physical or magical type.
  private final String type;
  // Offensive or defensive variety.
  private final String variety;
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
          .build();
  
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
          .build();
  
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
   * Creates a new EnemyMove object with the given configuration from the EnemyMoveBuilder object.
   * @param builder Configuration object for creating this EnemyMove instance.
   */
  public BattleMove(BattleMoveBuilder builder) {
    this.id = getNextId();
    this.name = builder.name;
    this.type = builder.type;
    this.variety = builder.variety;
    this.powerLevel = builder.powerLevel;
    this.manaRequired = builder.manaRequired;
    this.accuracy = builder.accuracy;
    this.ranged = builder.ranged;
    this.moveAnimation = builder.moveAnimation;
    this.thumbnail = builder.thumbnail;
    this.sound = builder.sound;
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

  private int getNextId() {
    return nextId++;
  }

  public int getId() {
    return id;
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

  public File getSound() {
    return sound;
  }

}
