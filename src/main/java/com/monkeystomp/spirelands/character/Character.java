package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.util.HashMap;

/**
 * A Character defines the name and stats of a group member that can be used in the player's party.
 * @author Aaron Michael McNulty
 */
public class Character {
  /**
   * Health stat name for displaying stats.
   */
  public static final String HP = "HP";
  /**
   * Mana stat name for displaying stats.
   */
  public static final String MANA = "Mana";
  /**
   * Attack stat name for displaying stats.
   */
  public static final String STRENGTH = "Strength";
  /**
   * Defense stat name for displaying stats.
   */
  public static final String DEFENSE = "Defense";
  /**
   * Intellect stat name for displaying stats.
   */
  public static final String INTELLECT = "Intellect";
  /**
   * Spirit stat name for displaying stats.
   */
  public static final String SPIRIT = "Spirit";
  /**
   * Speed stat name for displaying stats.
   */
  public static final String SPEED = "Speed";
  /**
   * Luck stat name for displaying stats.
   */
  public static final String LUCK = "Luck";
  /**
   * Very low stat weight for setting values on level up.
   */
  public static final String VERY_LOW = "Very Low";
  /**
   * Low stat weight for setting values on level up.
   */
  public static final String LOW = "Low";
  /**
   * Average stat weight for setting values on level up.
   */
  public static final String AVERAGE = "Average";
  /**
   * High stat weight for setting values on level up.
   */
  public static final String HIGH = "High";
  /**
   * Very high stat weight for setting values on level up.
   */
  public static final String VERY_HIGH = "Very High";
  // The max for any stat other than mana or HP
  private final int statMax = 255;
  // The modifier for when health stat is increased
  private final int healthWeightIncreaseModifier = 40;
  // The modifier for when mana stat is increased
  private final int manaWeightIncreaseModifier = 8;
  // The unique id for this character
  private String id;
  // Character name
  private String name;
  // Thumbnail image for character
  private Sprite thumbnail;
  // Overworld sprite sheet for character
  private SpriteSheet overworldSheet;
  // Battler sprite sheet for character
  private SpriteSheet battleSheet;
  // Character weapon type
  private String weaponType;
  // Character level
  private int level;
  // Experience points
  private int experience;
  // Current hit points
  private int health;
  // Hit point max
  private int healthMax;
  // Stat weight for health stat
  private String healthWeight;
  // Current mana points
  private int mana;
  // Max mana points
  private int manaMax;
  // Stat weight for mana stat
  private String manaWeight;
  // Physical strength stat
  private int strength;
  // Stat weight for strength stat
  private String strengthWeight;
  // Physical attack resistance
  private int defense;
  // Stat weight for defense stat
  private String defenseWeight;
  // Magic attack stat
  private int intellect;
  // Stat weight for intellect stat
  private String intellectWeight;
  // Magic attack resistance
  private int spirit;
  // Stat weight for spirit stat
  private String spiritWeight;
  // Character speed
  private int speed;
  // Stat weight for speed stat
  private String speedWeight;
  // Luck
  private int luck;
  // Stat weight for luck stat
  private String luckWeight;
  // The currently equipped weapon
  private WeaponItem equippedWeapon;
  // The currently equipped helmet.
  private ArmorItem equippedHelmet;
  // The currently equipped chestplate.
  private ArmorItem equippedChestplate;
  // The currently equipped shield.
  private ArmorItem equippedShield;
  // The currently equipped boots.
  private ArmorItem equippedBoots;
  // Map of stat weight to array values.
  private final HashMap<String, int[]> statWeightToValuesMap = new HashMap<>();
  
  public Character() {
    statWeightToValuesMap.put(Character.VERY_LOW, new int[]{1, 1, 1, 1});
    statWeightToValuesMap.put(Character.LOW, new int[]{1, 2, 1, 1});
    statWeightToValuesMap.put(Character.AVERAGE, new int[]{1, 2, 1, 2});
    statWeightToValuesMap.put(Character.HIGH, new int[]{1, 2, 2, 2});
    statWeightToValuesMap.put(Character.VERY_HIGH, new int[]{2, 2, 2, 2});
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Sprite getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(Sprite thumbnail) {
    this.thumbnail = thumbnail;
  }

  public SpriteSheet getOverworldSheet() {
    return overworldSheet;
  }

  public void setOverworldSheet(SpriteSheet overworldSheet) {
    this.overworldSheet = overworldSheet;
  }

  public SpriteSheet getBattleSheet() {
    return battleSheet;
  }

  public void setBattleSheet(SpriteSheet battleSheet) {
    this.battleSheet = battleSheet;
  }
  
  public void setWeaponType(String weaponType) {
    this.weaponType = weaponType;
  }
  
  public String getWeaponType() {
    return weaponType;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getHealthMax() {
    return healthMax;
  }

  public void setHealthMax(int healthMax) {
    this.healthMax = healthMax;
  }
  
  private void increaseHealthMax(int amount) {
    increaseHealth(amount);
    setHealthMax(healthMax + amount);
  }
  
  public void setHealthWeight(String weight) {
    this.healthWeight = weight;
  }

  public int getMana() {
    return mana;
  }

  public void setMana(int mana) {
    this.mana = mana;
  }

  public int getManaMax() {
    return manaMax;
  }

  public void setManaMax(int manaMax) {
    this.manaMax = manaMax;
  }
  
  private void increaseManaMax(int amount) {
    increaseMana(amount);
    setManaMax(manaMax + amount);
  }
  
  public void setManaWeight(String weight) {
    this.manaWeight = weight;
  }

  public int getStrength() {
    return strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }
  
  public void setStrengthWeight(String weight) {
    this.strengthWeight = weight;
  }

  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }
  
  public void setDefenseWeight(String weight) {
    this.defenseWeight = weight;
  }

  public int getIntellect() {
    return intellect;
  }

  public void setIntellect(int intellect) {
    this.intellect = intellect;
  }
  
  public void setIntellectWeight(String weight) {
    this.intellectWeight = weight;
  }

  public int getSpirit() {
    return spirit;
  }

  public void setSpirit(int spirit) {
    this.spirit = spirit;
  }
  
  public void setSpiritWeight(String weight) {
    this.spiritWeight = weight;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  public void setSpeedWeight(String weight) {
    this.speedWeight = weight;
  }

  public int getLuck() {
    return luck;
  }

  public void setLuck(int luck) {
    this.luck = luck;
  }
  
  public void setLuckWeight(String weight) {
    this.luckWeight = weight;
  }

  public WeaponItem getEquippedWeapon() {
    return equippedWeapon;
  }
  /**
   * <span style="color: red">WARNING!!</span> Only use this for setting equipment on game start. To equip weapon from inventory use equipWeapon().
   * @param equippedWeapon Weapon item to set as equipped weapon.
   * @see #equipWeapon(WeaponItem)
   */
  public void setEquippedWeapon(WeaponItem equippedWeapon) {
    this.equippedWeapon = equippedWeapon;
  }
  /**
   * Equip this character with the given weapon.
   * @param equippedWeapon Weapon to equip this character with.
   */
  public void equipWeapon(WeaponItem equippedWeapon) {
    unequipWeapon();
    this.equippedWeapon = equippedWeapon;
    equippedWeapon.removeFromInventory();
  }
  /**
   * Unequip this character's weapon.
   */
  public void unequipWeapon() {
    if (equippedWeapon != null) {
      equippedWeapon.addToInventory();
      equippedWeapon = null;
    }
  }

  public ArmorItem getEquippedHelmet() {
    return equippedHelmet;
  }
  /**
   * <span style="color: red">WARNING!!</span> Only use this for setting equipment on game start. To equip helmet from inventory use equipHelmet().
   * @param equippedHelmet Armor item to set as equipped helmet.
   * @see #equipHelmet(ArmorItem)
   */
  public void setEquippedHelmet(ArmorItem equippedHelmet) {
    this.equippedHelmet = equippedHelmet;
  }
  /**
   * Equip this character with the given helmet.
   * @param equippedHelmet The helmet to equip this character with.
   */
  public void equipHelmet(ArmorItem equippedHelmet) {
    unequipHelmet(false);
    this.equippedHelmet = equippedHelmet;
    equippedHelmet.playEquipSound();
    equippedHelmet.removeFromInventory();
  }
  /**
   * Unequip this character's helmet.
   * @param playAudio Flag to check if audio should be played while doing this operation.
   */
  public void unequipHelmet(boolean playAudio) {
    if (equippedHelmet != null) {
      if (playAudio) equippedHelmet.playUnequipSound();
      equippedHelmet.addToInventory();
      equippedHelmet = null;
    }
  }

  public ArmorItem getEquippedChestplate() {
    return equippedChestplate;
  }
  /**
   * <span style="color: red">WARNING!!</span> Only use this for setting equipment on game start. To equip chestplate from inventory use equipChestplate().
   * @param equippedChestplate Armor item to set as equipped chestplate.
   * @see #equipChestplate(ArmorItem)
   */
  public void setEquippedChestplate(ArmorItem equippedChestplate) {
    this.equippedChestplate = equippedChestplate;
  }
  /**
   * Equip this character with the given chestplate.
   * @param equippedChestplate The chestplate to equip this character with.
   */
  public void equipChestplate(ArmorItem equippedChestplate) {
    unequipChestplate(false);
    this.equippedChestplate = equippedChestplate;
    equippedChestplate.playEquipSound();
    equippedChestplate.removeFromInventory();
  }
  /**
   * Unequip this character's chestplate.
   * @param playAudio Flag to check if audio should be played while doing this operation.
   */
  public void unequipChestplate(boolean playAudio) {
    if (equippedChestplate != null) {
      if (playAudio) equippedChestplate.playUnequipSound();
      equippedChestplate.addToInventory();
      equippedChestplate = null;
    }
  }

  public ArmorItem getEquippedShield() {
    return equippedShield;
  }
  /**
   * <span style="color: red">WARNING!!</span> Only use this for setting equipment on game start. To equip shield from inventory use equipShield().
   * @param equippedShield Armor item to set as equipped shield.
   * @see #equipShield(ArmorItem)
   */
  public void setEquippedShield(ArmorItem equippedShield) {
    this.equippedShield = equippedShield;
  }
  /**
   * Equip this character with the given shield.
   * @param equippedShield The shield to equip this character with.
   */
  public void equipShield(ArmorItem equippedShield) {
    unequipShield(false);
    this.equippedShield = equippedShield;
    equippedShield.playEquipSound();
    equippedShield.removeFromInventory();
  }
  /**
   * Unequip this character's shield.
   * @param playAudio Flag to check if audio should be played while doing this operation.
   */
  public void unequipShield(boolean playAudio) {
    if (equippedShield != null) {
      if (playAudio) equippedShield.playUnequipSound();
      equippedShield.addToInventory();
      equippedShield = null;
    }
  }

  public ArmorItem getEquippedBoots() {
    return equippedBoots;
  }
  /**
   * <span style="color: red">WARNING!!</span> Only use this for setting equipment on game start. To equip boots from inventory use equipBoots().
   * @param equippedBoots Armor item to set as equipped boots.
   * @see #equipBoots(ArmorItem)
   */
  public void setEquippedBoots(ArmorItem equippedBoots) {
    this.equippedBoots = equippedBoots;
  }
  /**
   * Equip this character with the given boots.
   * @param equippedBoots The shield to equip this character with.
   */
  public void equipBoots(ArmorItem equippedBoots) {
    unequipBoots(false);
    this.equippedBoots = equippedBoots;
    equippedBoots.playEquipSound();
    equippedBoots.removeFromInventory();
  }
  /**
   * Unequip this character's boots.
   * @param playAudio Flag to check if audio should be played while doing this operation.
   */
  public void unequipBoots(boolean playAudio) {
    if (equippedBoots != null) {
      if (playAudio) equippedBoots.playUnequipSound();
      equippedBoots.addToInventory();
      equippedBoots = null;
    }
  }
  
  
  /**
   *      !!################################################!!
   *      !!                                                !!
   *      !!         Combined Stat Getting Methods          !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
  /**
   * Gets the combined attack stat for this character which is a figure based on the strength stat and the attack power of the current equipped weapon.
   * @return The combined attack stat for this character.
   */
  public int getCombinedAttack() {
    if (equippedWeapon != null) {
      return equippedWeapon.getAttackPower() + strength;
    }
    else return strength;
  }
  /**
   * Gets the combined defense stat for this character which is a figure based on the defense stat and the physical defense of all combined armor.
   * @return The combined defense stat for this character.
   */
  public int getCombinedDefense() {
    int combinedDefense = defense;
    if (equippedHelmet != null) {
      combinedDefense += equippedHelmet.getPhysicalDefense();
    }
    if (equippedChestplate != null) {
      combinedDefense += equippedChestplate.getPhysicalDefense();
    }
    if (equippedShield != null) {
      combinedDefense += equippedShield.getPhysicalDefense();
    }
    if (equippedBoots != null) {
      combinedDefense += equippedBoots.getPhysicalDefense();
    }
    return combinedDefense;
  }
  
  public int getCombinedIntellect() {
    if (equippedWeapon != null) {
      return equippedWeapon.getMagicPower() + intellect;
    }
    else return intellect;
  }
  
  public int getCombinedSpirit() {
    int combinedSpirit = spirit;
    if (equippedHelmet != null) {
      combinedSpirit += equippedHelmet.getMagicalDefense();
    }
    if (equippedChestplate != null) {
      combinedSpirit += equippedChestplate.getMagicalDefense();
    }
    if (equippedShield != null) {
      combinedSpirit += equippedShield.getMagicalDefense();
    }
    if (equippedBoots != null) {
      combinedSpirit += equippedBoots.getMagicalDefense();
    }
    return combinedSpirit;
  }
  
  public int getCombinedSpeed() {
    int combinedSpeed = speed;
    if (equippedHelmet != null) {
      combinedSpeed -= equippedHelmet.getSpeedPenalty();
    }
    if (equippedChestplate != null) {
      combinedSpeed -= equippedChestplate.getSpeedPenalty();
    }
    if (equippedShield != null) {
      combinedSpeed -= equippedShield.getSpeedPenalty();
    }
    if (equippedBoots != null) {
      combinedSpeed -= equippedBoots.getSpeedPenalty();
    }
    if (combinedSpeed < 0) combinedSpeed = 0;
    return combinedSpeed;
  }
  
  public int getCombinedLuck() {
    return luck;
  }
  
  /**
   *      !!################################################!!
   *      !!                                                !!
   *      !!           Raw Stat Changing Methods            !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
  /**
   * Increases the health stat of this character. Health cannot be increased more than the healthMax stat.
   * @param amount The amount to increase the health by.
   */
  public void increaseHealth(int amount) {
    health += amount;
    if (health > healthMax) health = healthMax;
  }
  /**
   * Decreases the health of this character. Health cannot be decreased below zero.
   * @param amount The amount to decrease the health by.
   */
  public void decreaseHealth(int amount) {
    health -= amount;
    if (health < 0) health = 0;
  }
  /**
   * Increases the mana of this character. Mana cannot be increased more than the manaMax stat.
   * @param amount The amount to increase the mana by.
   */
  public void increaseMana(int amount) {
    mana += amount;
    if (mana > manaMax) mana = manaMax;
  }
  /**
   * Decreases the mana of this character. Mana cannot be decreased below zero.
   * @param amount The amount to decrease the mana by.
   */
  public void decreaseMana(int amount) {
    mana -= amount;
    if (mana < 0) mana = 0;
  }
  /**
   * Increases the strength stat of this character.
   * @param amount The amount to change strength by.
   */
  public void increaseStrength(int amount) {
    strength += amount;
    if (strength > statMax) strength = statMax;
  }
  /**
   * Increase the defense stat of this character.
   * @param amount The amount to change defense by.
   */
  public void increaseDefense(int amount) {
    defense += amount;
    if (defense > statMax) defense = statMax;
  }
  /**
   * Increase the intellect stat of this character.
   * @param amount The amount to change intellect by.
   */
  public void increaseIntellect(int amount) {
    intellect += amount;
    if (intellect > statMax) intellect = statMax;
  }
  /**
   * Increase the spirit stat of this character.
   * @param amount The amount to change spirit by.
   */
  public void increaseSpirit(int amount) {
    spirit += amount;
    if (spirit > statMax) spirit = statMax;
  }
  /**
   * Increase the speed stat of this character.
   * @param amount The amount to change speed by.
   */
  public void increaseSpeed(int amount) {
    speed += amount;
    if (speed > statMax) speed = statMax;
  }
  /**
   * Increase the luck stat of this character.
   * @param amount The amount to change luck by.
   */
  public void increaseLuck(int amount) {
    luck += amount;
    if (luck > statMax) luck = statMax;
  }
  /**
   * Increase the level stat of this character
   * @param amount The amount to change level by.
   */
  public void increaseLevel(int amount) {
    level += amount;
    if (level > 100) level = 100;
    else {
      increaseHealthMax(statWeightToValuesMap.get(healthWeight)[level % 4] * healthWeightIncreaseModifier);
      increaseManaMax(statWeightToValuesMap.get(manaWeight)[level % 4] * manaWeightIncreaseModifier);
      increaseStrength(statWeightToValuesMap.get(strengthWeight)[level % 4]);
      increaseDefense(statWeightToValuesMap.get(defenseWeight)[level % 4]);
      increaseIntellect(statWeightToValuesMap.get(intellectWeight)[level % 4]);
      increaseSpirit(statWeightToValuesMap.get(spiritWeight)[level % 4]);
      increaseSpeed(statWeightToValuesMap.get(speedWeight)[level % 4]);
      increaseLuck(statWeightToValuesMap.get(luckWeight)[level % 4]);    
    }
  }
    
}
