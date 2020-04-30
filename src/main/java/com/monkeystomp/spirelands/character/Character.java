package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.gamemenu.components.AbilitySlot;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Character defines the name and stats of a group member that can be used in the player's party.
 * @author Aaron Michael McNulty
 */
public class Character extends StatModel {
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
  // Array of equipped moves.
  private final ArrayList<BattleMove> equippedMoves = new ArrayList<>();
  // Array of ability slots.
  private final ArrayList<AbilitySlot> abilitySlots = new ArrayList<>();
  
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
  
  public void setEquippedMoves(ArrayList<BattleMove> moves) {
    equippedMoves.clear();
    equippedMoves.addAll(moves);
  }
  /**
   * Adds the given ability slot to this character's array of slots.
   * @param slot The ability slot to add.
   */
  public void addAbilitySlot(AbilitySlot slot) {
    abilitySlots.add(slot);
  }

  public ArrayList<AbilitySlot> getAbilitySlots() {
    return abilitySlots;
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
  @Override
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
  @Override
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
  
  @Override
  public int getCombinedIntellect() {
    if (equippedWeapon != null) {
      return equippedWeapon.getMagicPower() + intellect;
    }
    else return intellect;
  }
  
  @Override
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
  
  @Override
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
  
  @Override
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

  @Override  
  public void increaseLevel(int amount) {
    super.increaseLevel(amount);
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
  
  public double getWeaponElementalModifier(String element) {
    if (equippedWeapon != null) {
      List<ElementalEffect> elEffect = equippedWeapon.getElementalEffects().stream()
              .filter(effect -> effect.getElement().equals(element))
              .collect(Collectors.toList());
      if (elEffect.size() > 0) return elEffect.get(0).getPercentage() / 100.0;
    }
    return 1.0;
  }
  
  public double getArmorElementalResistance(String element) {
    int totalPercentage = 0;
    List<ElementalEffect> elEffect;
    if (this.equippedHelmet != null) {
      elEffect = equippedHelmet.getElementalEffects().stream()
              .filter(effect -> effect.getElement().equals(element))
              .collect(Collectors.toList());
      if (elEffect.size() > 0) totalPercentage += elEffect.get(0).getPercentage() - 100;
    }
    if (this.equippedChestplate != null) {
      elEffect = equippedChestplate.getElementalEffects().stream()
              .filter(effect -> effect.getElement().equals(element))
              .collect(Collectors.toList());
      if (elEffect.size() > 0) totalPercentage += elEffect.get(0).getPercentage() - 100;
    }
    if (this.equippedShield != null) {
      elEffect = equippedShield.getElementalEffects().stream()
              .filter(effect -> effect.getElement().equals(element))
              .collect(Collectors.toList());
      if (elEffect.size() > 0) totalPercentage += elEffect.get(0).getPercentage() - 100;
    }
    if (this.equippedBoots != null) {
      elEffect = equippedBoots.getElementalEffects().stream()
              .filter(effect -> effect.getElement().equals(element))
              .collect(Collectors.toList());
      if (elEffect.size() > 0) totalPercentage += elEffect.get(0).getPercentage() - 100;
    }
    return totalPercentage / 100.0;
  }
    
}
