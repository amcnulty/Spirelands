package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Character defines the name and stats of a group member that can be used in the player's party.
 * @author Aaron Michael McNulty
 */
public class Character extends StatModel {
  // The modifier for when health stat is increased
  private final int healthWeightIncreaseModifier = 40;
  // The modifier for when mana stat is increased
  private final int manaWeightIncreaseModifier = 8;
  // The max number of equiped moves.
  private final int equippedMoveMax = 8;
  // The unique id for this character
  private String id;
  // Thumbnail image for character
  private Sprite thumbnail;
  // Overworld sprite sheet for character
  private SpriteSheet overworldSheet;
  // Battler sprite sheet for character
  private SpriteSheet battleSheet;
  // Character weapon type
  private String weaponType;
  // Experience points
  private int experience;
  // Stat weight for health stat
  private String healthWeight;
  // Stat weight for mana stat
  private String manaWeight;
  // Stat weight for strength stat
  private String strengthWeight;
  // Stat weight for defense stat
  private String defenseWeight;
  // Stat weight for intellect stat
  private String intellectWeight;
  // Stat weight for spirit stat
  private String spiritWeight;
  // Stat weight for speed stat
  private String speedWeight;
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
  // Array of equipped moves.
  private final ArrayList<BattleMove> equippedMoves = new ArrayList<>();
  
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

  public void setLevel(int level) {
    this.level = level;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public void setHealth(int health) {
    this.health = health;
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

  public void setMana(int mana) {
    this.mana = mana;
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

  public void setStrength(int strength) {
    this.strength = strength;
  }
  
  public void setStrengthWeight(String weight) {
    this.strengthWeight = weight;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }
  
  public void setDefenseWeight(String weight) {
    this.defenseWeight = weight;
  }

  public void setIntellect(int intellect) {
    this.intellect = intellect;
  }
  
  public void setIntellectWeight(String weight) {
    this.intellectWeight = weight;
  }

  public void setSpirit(int spirit) {
    this.spirit = spirit;
  }
  
  public void setSpiritWeight(String weight) {
    this.spiritWeight = weight;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
  
  public void setSpeedWeight(String weight) {
    this.speedWeight = weight;
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

  public ArrayList<BattleMove> getEquippedMoves() {
    return equippedMoves;
  }
  
  public void equipMove(BattleMove move) {
    if (equippedMoves.size() < equippedMoveMax) {
      equippedMoves.add(move);
    }
  }
    
  /**
   *      !!################################################!!
   *      !!                                                !!
   *      !!         Equipment setters and getters          !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
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
   *      !!         Level and experience Methods           !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
  /**
   * Increase the level stat of this character
   * @param amount The amount to change level by.
   */
  public void increaseLevel(int amount) {
    for (int i = 0; i < amount; i++) {
      level++;
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
    experience = 0;
  }
  
  /**
   * Gets the number of experience points needed to get to the next level.
   * @return Number of experience point needed to get to the next level.
   */
  public int getExpToNextLevel() {
    int nextLevel = level + 1;
    return (int)(.025 * Math.pow(nextLevel, 4) + Math.pow(nextLevel, 3) + 1.5 * Math.pow(nextLevel, 2) + .35 * nextLevel - 2.875);
  }
  
  public void addExperiencePoints(int amount) {
    if (experience + amount >= getExpToNextLevel()) {
      int remaining = amount - (getExpToNextLevel() - experience);
      increaseLevel(1);
      addExperiencePoints(remaining);
    }
    else {
      experience += amount;
    }
  }
    
}
