package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.character.Character;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Item class holds information about game items and has an interface for interacting with the inventory for the item.
 * @author Aaron Michael McNulty
 */
public class Item {

  /**
   * Weapon type item
   */
  public static final String WEAPON = "Weapon";
  /**
   * Armor type item
   */
  public static final String ARMOR = "Armor";
  /**
   * Equipment type item
   */
  public static final String EQUIPMENT = "Equipment";
  /**
   * Special type item
   */
  public static final String SPECIAL = "Special";
  
  public static final HashMap<Integer, Item> ITEM_MAP = new HashMap<>();
  
  private final String  title,
                        description,
                        type;
  private final Sprite thumbnail;
  private final int id,
                    price;
  private static int nextId = 0;
  private Character character;
  protected final ArrayList<ItemAttribute> attributes = new ArrayList<>();
  protected static final InventoryManager INVENTORY_MANAGER = InventoryManager.getInventoryManager();
  private static final int  SPRITE_SIZE = 16,
                            WEAPON_SPRITE_SIZE = 32,
                            NO_PRICE = -1;
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!        Item Declarations       !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!        Equipment Items         !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * A basic health potion (EQUIPMENT)
   */
  public static final EquipmentItem
    SMALL_HP_POTION = new ItemBuilder()
          .title("Small HP Potion")
          .description("A basic health potion for replenishing HP.")
          .price(50)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 2, 9, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    SMALL_HP_POTION.setHealingPoints(100);
    SMALL_HP_POTION.setUseItemSound(SoundEffects.HEALING_SOUND);
  }
  /**
   * Medium Health Potion (EQUIPMENT)
   */
  public static final EquipmentItem
    MEDIUM_HP_POTION = new ItemBuilder()
          .title("Medium Potion")
          .description("A medium size health potion for replenishing HP.")
          .price(150)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 9, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    MEDIUM_HP_POTION.setHealingPoints(400);
    MEDIUM_HP_POTION.setUseItemSound(SoundEffects.HEALING_SOUND);
  }
  /**
   * Health Bottle Potion (EQUIPMENT)
   */
  public static final EquipmentItem
    HEALTH_BOTTLE = new ItemBuilder()
          .title("Health Bottle")
          .description("A bottle of healing potion. What a life saver!")
          .price(500)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 0, 9, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    HEALTH_BOTTLE.setHealingPoints(1200);
    HEALTH_BOTTLE.setUseItemSound(SoundEffects.HEALING_SOUND);
  }
  /**
   * Mana Vile (EQUIPMENT)
   */
  public static final EquipmentItem
    MANA_VILE = new ItemBuilder()
          .title("Mana Vile")
          .description("A vile of mana potion for a small increase in magic power.")
          .price(200)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 7, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    MANA_VILE.setManaRestorePoints(20);
    MANA_VILE.setUseItemSound(SoundEffects.HEALING_SOUND);
  }
  /**
   * Mana Potion (EQUIPMENT)
   */
  public static final EquipmentItem
    MANA_POTION = new ItemBuilder()
          .title("Mana Potion")
          .description("A wonderful potion used to restore drained magic power.")
          .price(800)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 9, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    MANA_POTION.setManaRestorePoints(80);
    MANA_POTION.setUseItemSound(SoundEffects.HEALING_SOUND);
  }
  /**
   * Mana Bottle (EQUIPMENT)
   */
  public static final EquipmentItem
    MANA_BOTTLE = new ItemBuilder()
          .title("Mana Bottle")
          .description("A bottle of mana restoring liquid. This potion is expensive but really useful in long battles where quick mana restoring is required.")
          .price(2000)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 5, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    MANA_BOTTLE.setManaRestorePoints(200);
    MANA_BOTTLE.setUseItemSound(SoundEffects.HEALING_SOUND);
  }
  /**
   * Ribs (EQUIPMENT - STRENGTH UP)
   */
  public static final EquipmentItem
    RIBS = new ItemBuilder()
          .title("Ribs")
          .description("Delicious ribs that bolsters ones strength.")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 7, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    RIBS.setStrengthUp(1);
    RIBS.setUseItemSound(SoundEffects.GULP_EAT);
  }
  /**
   * Apple (EQUIPMENT - DEFENSE UP)
   */
  public static final EquipmentItem
    APPLE = new ItemBuilder()
          .title("Apple")
          .description("A perfectly grown apple. Very sweet and healthy too.")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 0, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    APPLE.setDefenseUp(1);
    APPLE.setUseItemSound(SoundEffects.CRUNCH_EAT);
  }
  /**
   * Banana (EQUIPMENT - INTELLECT UP)
   */
  public static final EquipmentItem
    BANANA = new ItemBuilder()
          .title("Banana")
          .description("The most wonderful food there is. The banana is a great traveling snack and it will increase your metal capacity.")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    BANANA.setIntellectUp(1);
    BANANA.setUseItemSound(SoundEffects.QUICK_EAT);
  }
  /**
   * Dragon Tooth (EQUIPMENT - SPIRIT UP)
   */
  public static final EquipmentItem
    DRAGON_TOOTH = new ItemBuilder()
          .title("Dragon Tooth")
          .description("A huge tooth from a flying dragon. Keeping this on your person is said to increase your spirit.")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 5, 9, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    DRAGON_TOOTH.setSpiritUp(1);
    DRAGON_TOOTH.setUseItemSound(SoundEffects.STAT_UP);
  }
  /**
   * Feather (EQUIPMENT - SPEED UP)
   */
  public static final EquipmentItem
    FEATHER = new ItemBuilder()
          .title("Feather")
          .description("As light as a feather is all you need to think and then you will be able to run with the wind!")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 8, 9, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    FEATHER.setSpeedUp(1);
    FEATHER.setUseItemSound(SoundEffects.STAT_UP);
  }
  /**
   * A cookie (EQUIPMENT - LUCK UP)
   */
  public static final EquipmentItem
    COOKIE = new ItemBuilder()
          .title("Cookie")
          .description("A freshly baked cookie. Looks delicious.")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    COOKIE.setLuckUp(1);
    COOKIE.setUseItemSound(SoundEffects.QUICK_EAT);
  }
  /**
   * Special Candy (EQUIPMENT - LEVEL UP)
   */
  public static final EquipmentItem
    SPECIAL_CANDY = new ItemBuilder()
          .title("Special Candy")
          .description("A very special candy indeed. This lovely little treat will increase your level by 1!")
          .price(5000)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    SPECIAL_CANDY.setLevelUp(1);
    SPECIAL_CANDY.setUseItemSound(SoundEffects.LEVEL_UP);
  }
  /**
   * A training book to increase stats (EQUIPMENT)
   */
  public static final EquipmentItem
    TRAINING_BOOK_V1 = new ItemBuilder()
          .title("Training Book v.1")
          .description("Volume 1 of the training book series. This book will teach the basic skills that it takes to increase ones overall skills.")
          .price(1500)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 11, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    TRAINING_BOOK_V1.setStrengthUp(1);
    TRAINING_BOOK_V1.setDefenseUp(1);
    TRAINING_BOOK_V1.setIntellectUp(1);
    TRAINING_BOOK_V1.setSpiritUp(1);
    TRAINING_BOOK_V1.setSpeedUp(1);
    TRAINING_BOOK_V1.setLuckUp(1);
    TRAINING_BOOK_V1.setUseItemSound(SoundEffects.LEVEL_UP);
  }
  /**
   * A training book to increase stats (EQUIPMENT)
   */
  public static final EquipmentItem
    TRAINING_BOOK_V2 = new ItemBuilder()
          .title("Training Book v.2")
          .description("Volume 2 of the training book series. In this installment fighters will learn specific tactics to reach the next level of their trainin'g.")
          .price(2000)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 11, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    TRAINING_BOOK_V2.setStrengthUp(2);
    TRAINING_BOOK_V2.setDefenseUp(2);
    TRAINING_BOOK_V2.setIntellectUp(2);
    TRAINING_BOOK_V2.setSpiritUp(2);
    TRAINING_BOOK_V2.setSpeedUp(2);
    TRAINING_BOOK_V2.setLuckUp(2);
    TRAINING_BOOK_V2.setLevelUp(1);
    TRAINING_BOOK_V2.setUseItemSound(SoundEffects.LEVEL_UP);
  }
  
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!           Armor Items          !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Leather Jacket (ARMOR ITEM)
   */
  public static final ArmorItem
    LEATHER_JACKET = new ItemBuilder()
          .title("Leather Jacket")
          .description("A protective leather jacket. While wearing it you feel like you could win a knife fight. Provides level 1 protection.")
          .price(300)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    LEATHER_JACKET.setPhysicalDefense(5);
    LEATHER_JACKET.setMagicalDefense(1);
    LEATHER_JACKET.setArmorType(ArmorItem.CHESTPLATE);
  }
  /**
   * War Tunic (ARMOR)
   */
  public static final ArmorItem
    WAR_TUNIC = new ItemBuilder()
          .title("War Tunic")
          .description("A heavy weight yet flexible material makes this warrior's armor. Provides level 2 protection.")
          .price(600)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    WAR_TUNIC.setPhysicalDefense(10);
    WAR_TUNIC.setMagicalDefense(2);
    WAR_TUNIC.setSpeedPenalty(2);
    WAR_TUNIC.setArmorType(ArmorItem.CHESTPLATE);
  }
  /**
   * Metal Vest (ARMOR)
   */
  public static final ArmorItem
    METAL_VEST = new ItemBuilder()
          .title("Metal Vest")
          .description("This chest plate is made of solid strong metal. It is very heavy but offers level 3 protection.")
          .price(900)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    METAL_VEST.setPhysicalDefense(15);
    METAL_VEST.setMagicalDefense(3);
    METAL_VEST.setSpeedPenalty(6);
    METAL_VEST.setArmorType(ArmorItem.CHESTPLATE);
  }
  /**
   * Gold Tunic (ARMOR)
   */
  public static final ArmorItem
    GOLD_TUNIC = new ItemBuilder()
          .title("Gold Chestplate")
          .description("A chestplate of armor made from rare gold. Offers level 4 protection.")
          .price(1200)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 6, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    GOLD_TUNIC.setPhysicalDefense(20);
    GOLD_TUNIC.setMagicalDefense(5);
    GOLD_TUNIC.setSpeedPenalty(9);
    GOLD_TUNIC.setArmorType(ArmorItem.CHESTPLATE);
  }
  /**
   * Basic leather helmet (ARMOR)
   */
  public static final ArmorItem
    LEATHER_HELMET = new ItemBuilder()
          .title("Leather Helmet")
          .description("A basic leather helmet used for defense.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    LEATHER_HELMET.setPhysicalDefense(3);
    LEATHER_HELMET.setMagicalDefense(1);
    LEATHER_HELMET.setArmorType(ArmorItem.HELMET);
  }
  /**
   * War helmet (ARMOR)
   */
  public static final ArmorItem
    WAR_HELMET = new ItemBuilder()
          .title("War Helmet")
          .description("A warriors helmet this will provide level 2 protection.")
          .price(200)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    WAR_HELMET.setPhysicalDefense(6);
    WAR_HELMET.setMagicalDefense(2);
    WAR_HELMET.setArmorType(ArmorItem.HELMET);
  }
  /**
   * Metal Helmet (ARMOR)
   */
  public static final ArmorItem
    METAL_HELMET = new ItemBuilder()
          .title("Metal Helmet")
          .description("A metal helmet that looks like it was designed for a king's knight. This will provide level 3 protection.")
          .price(300)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    METAL_HELMET.setPhysicalDefense(9);
    METAL_HELMET.setMagicalDefense(3);
    METAL_HELMET.setArmorType(ArmorItem.HELMET);
  }
  /**
   * Gold Helmet (ARMOR)
   */
  public static final ArmorItem
    GOLD_HELMET = new ItemBuilder()
          .title("Gold Helmet")
          .description("A helmet made from gold. This will provide level 4 protection.")
          .price(400)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 6, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    GOLD_HELMET.setPhysicalDefense(12);
    GOLD_HELMET.setMagicalDefense(4);
    GOLD_HELMET.setArmorType(ArmorItem.HELMET);
  }
  /**
   * Leather Boots (ARMOR)
   */
  public static final ArmorItem
    LEATHER_BOOTS = new ItemBuilder()
          .title("Leather Boots")
          .description("These leather boots look like they have gone around the block a few times, however, they offer protection level 1.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 2, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    LEATHER_BOOTS.setPhysicalDefense(3);
    LEATHER_BOOTS.setMagicalDefense(1);
    LEATHER_BOOTS.setSpeedPenalty(1);
    LEATHER_BOOTS.setArmorType(ArmorItem.BOOTS);
  }
  /**
   * War Boots (ARMOR)
   */
  public static final ArmorItem
    WAR_BOOTS = new ItemBuilder()
          .title("War Boots")
          .description("Boots that are made for combat. These boots offer level 2 protection.")
          .price(200)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 2, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    WAR_BOOTS.setPhysicalDefense(6);
    WAR_BOOTS.setMagicalDefense(2);
    WAR_BOOTS.setSpeedPenalty(2);
    WAR_BOOTS.setArmorType(ArmorItem.BOOTS);
  }
  /**
   * Steel boots (ARMOR)
   */
  public static final ArmorItem
    STEEL_BOOTS = new ItemBuilder()
          .title("Steel Boots")
          .description("Military grade steel boots. Goes best with a matching suit of armor. Offers level 3 protection.")
          .price(300)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 2, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    STEEL_BOOTS.setPhysicalDefense(9);
    STEEL_BOOTS.setMagicalDefense(3);
    STEEL_BOOTS.setSpeedPenalty(5);
    STEEL_BOOTS.setArmorType(ArmorItem.BOOTS);
  }
  /**
   * Gold Boots (ARMOR)
   */
  public static final ArmorItem
    GOLD_BOOTS = new ItemBuilder()
          .title("Gold Boots")
          .description("Boots made from solid gold. You feel like a god wearing these shoes. Overs level 4 protection.")
          .price(400)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 6, 2, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    GOLD_BOOTS.setPhysicalDefense(5);
    GOLD_BOOTS.setMagicalDefense(4);
    GOLD_BOOTS.setSpeedPenalty(7);
    GOLD_BOOTS.setArmorType(ArmorItem.BOOTS);
  }
  /**
   * Wood Shield (ARMOR)
   */
  public static final ArmorItem
    WOOD_SHIELD = new ItemBuilder()
          .title("Wood Shield")
          .description("A basic wood shield used for protection in combat. This provides level 1 protection.")
          .price(250)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 7, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    WOOD_SHIELD.setPhysicalDefense(5);
    WOOD_SHIELD.setSpeedPenalty(2);
    WOOD_SHIELD.setArmorType(ArmorItem.SHIELD);
  }
  /**
   * War Shield (ARMOR)
   */
  public static final ArmorItem
    WAR_SHIELD = new ItemBuilder()
          .title("War Shield")
          .description("The shield of a warrior. This is used by fighters all across Spirelands and is standard issue for many militias. You can rely on this shield to provide level 2 protection.")
          .price(500)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 8, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    WAR_SHIELD.setPhysicalDefense(10);
    WAR_SHIELD.setSpeedPenalty(3);
    WAR_SHIELD.setArmorType(ArmorItem.SHIELD);
  }
  /**
   * Metal Shield (ARMOR)
   */
  public static final ArmorItem
    METAL_SHIELD = new ItemBuilder()
          .title("Metal Shield")
          .description("A metal shield to be used in combat. You must be in great shape to use this heavy shield in combat to get the benefits of its level 3 protection.")
          .price(750)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 9, 0, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    METAL_SHIELD.setPhysicalDefense(15);
    METAL_SHIELD.setSpeedPenalty(5);
    METAL_SHIELD.setArmorType(ArmorItem.SHIELD);
  }
  /**
   * King's Shield (ARMOR)
   */
  public static final ArmorItem
    KINGS_SHIELD = new ItemBuilder()
          .title("King's Shield")
          .description("The shield of a great king! It is as large as it is noble and offers level 4 protection due to its great size and strength.")
          .price(1000)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 9, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  static {
    KINGS_SHIELD.setPhysicalDefense(20);
    KINGS_SHIELD.setSpeedPenalty(8);
    KINGS_SHIELD.setArmorType(ArmorItem.SHIELD);
  }
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!           Weapon Items         !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Common sword (WEAPON)
   */
  public static final WeaponItem
    COMMON_SWORD = new ItemBuilder()
          .title("Common Sword")
          .description("A common sword wielded by many townspeople across Spirelands. This inexpensize sword is good for a novice as it will only inflict minor damage to those it is used against.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    COMMON_SWORD.setAttackPower(10);
    COMMON_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Katana sword (WEAPON)
   */
  public static final WeaponItem
    KATANA_SWORD = new ItemBuilder()
          .title("Katana")
          .description("The katana is characterized by its distinctive appearance: a curved, single-edged blade with a circular or squared guard and long grip to accommodate two hands.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    KATANA_SWORD.setAttackPower(25);
    KATANA_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Sky Saber (WEAPON)
   */
  public static final WeaponItem
    SKY_SABER = new ItemBuilder()
          .title("Sky Saber")
          .description("This light sword is both fast and deadly. It is very lightweight and easy to swing. It was created in the sky relm.")
          .price(1200)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SKY_SABER.setAttackPower(27);
    SKY_SABER.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Scimitar sword (WEAPON)
   */
  public static final WeaponItem
    SCIMITAR_SWORD = new ItemBuilder()
          .title("Scimitar")
          .description("The Scimitar makes a great warior's swoard because of its relatively light weight when compared to larger swords and its curved design, good for slashing opponents.")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SCIMITAR_SWORD.setAttackPower(30);
    SCIMITAR_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Crusader sword (WEAPON)
   */
  public static final WeaponItem
    CRUSADER_SWORD = new ItemBuilder()
          .title("The Crusader")
          .description("A heavy straight sword with a single edged blade and deadly double edged point. The Crusader is the ideal weapon for an officer's side arm so they are always ready to duel.")
          .price(1750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    CRUSADER_SWORD.setAttackPower(33);
    CRUSADER_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Talon sword (WEAPON)
   */
  public static final WeaponItem
    TALON_SWORD = new ItemBuilder()
          .title("Talon Sword")
          .description("This intimidating blade will strike terror in the opponents it will eventually cut down. It has a double handed grip to allow for powerful thrusts and slashes.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    TALON_SWORD.setAttackPower(40);
    TALON_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * 'Spellblade' (WEAPON)
   */
  public static final WeaponItem
    SPELLBLADE = new ItemBuilder()
          .title("Spellblade")
          .description("Magical powers enchant this unique sword. The engergy within it greatly raises the wielder's magical abilities allowing them to unleash physical and magical attacks upon their foes.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SPELLBLADE.setAttackPower(38);
    SPELLBLADE.setMagicAttackPower(25);
    SPELLBLADE.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Dark Sword (WEAPON)
   */
  public static final WeaponItem
    DARK_SWORD = new ItemBuilder()
          .title("Dark Sword")
          .description("This evil sword will fill the hearts of its opponents with terror. It is thought to have been originally crafted by an evil dark lord over 900 years in the past.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    DARK_SWORD.setAttackPower(50);
    DARK_SWORD.setMagicAttackPower(10);
    DARK_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Poison Sword (WEAPON)
   */
  public static final WeaponItem
    POISON_SWORD = new ItemBuilder()
          .title("Poison Sword")
          .description("Poisonous venom fills this sword. When a victim is cut with this blade they are not only damaged by its sharp blade but by the lasting poison effect that it infects them with.")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    POISON_SWORD.setAttackPower(55);
    POISON_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * 'Longsword' (WEAPON)
   */
  public static final WeaponItem
    LONGSWORD = new ItemBuilder()
          .title("Longsword")
          .description("A sword used by knights that has a cruciform hilt with a grip for two-handed use and a straight double-edged blade. The user must be strong enough to weild this mighty weapon.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    LONGSWORD.setAttackPower(60);
    LONGSWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Dragon Sword (WEAPON)
   */
  public static final WeaponItem
    DRAGON_SWORD = new ItemBuilder()
          .title("Dragon Sword")
          .description("Deep within the realm of the dragons this sword was crafted to slay the mightiest of beasts. Anyone who challenges a holder of the Dragon Sword is sure to be out of their mind!")
          .price(5000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    DRAGON_SWORD.setAttackPower(65);
    DRAGON_SWORD.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Sword Of The Spire (WEAPON)
   */
  public static final WeaponItem
    SWORD_OF_THE_SPIRE = new ItemBuilder()
          .title("Sword Of The Spire")
          .description("Long ago a legendary warrior fought for the right to own the Sword Of The Spire. He went on to become king of Spireland and some say he never let this sword out of his sight.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 0, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SWORD_OF_THE_SPIRE.setAttackPower(80);
    SWORD_OF_THE_SPIRE.setMagicAttackPower(30);
    SWORD_OF_THE_SPIRE.setWeaponType(WeaponItem.SWORD);
  }
  /**
   * Basic Bow (WEAPON)
   */
  public static final WeaponItem
    BASIC_BOW = new ItemBuilder()
          .title("Basic Bow")
          .description("A basic short bow with a reflex shape. This lightweight, cheap, and easy to use weapon is a great beginners bow.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    BASIC_BOW.setAttackPower(10);
    BASIC_BOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Sparrow Bow (Weapon)
   */
  public static final WeaponItem
    SPARROW_BOW = new ItemBuilder()
          .title("Sparrow Bow")
          .description("This bow features a smooth curved body with a solid grip. It is a medium range bow that offers a great balance of weight, range, and arrow speed.")
          .price(750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SPARROW_BOW.setAttackPower(15);
    SPARROW_BOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Small Crossbow (WEAPON)
   */
  public static final WeaponItem
    SMALL_CROSSBOW = new ItemBuilder()
          .title("Small Crossbow")
          .description("Due to its light weight and medium recoil this small crossbow is easy to use. What it lacks in shooting power it makes up for in its fast reload time and compact small design.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SMALL_CROSSBOW.setAttackPower(20);
    SMALL_CROSSBOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Short Bow (WEAPON)
   */
  public static final WeaponItem
    SHORT_BOW = new ItemBuilder()
          .title("Short Bow")
          .description("Once thought of as a less than desireable weapon, the Short Bow has the unique advantages of being better in tight spaces and traveling long distances where weight is an issue.")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SHORT_BOW.setAttackPower(25);
    SHORT_BOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Common Longbow (WEAPON)
   */
  public static final WeaponItem
    COMMON_LONGBOW = new ItemBuilder()
          .title("Common Longbow")
          .description("A mainstay weapon of large armies. Standing at six feet tall the Longbow has superior range and arrow speed which allow arrows shot from it to penetrate metal armor.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    COMMON_LONGBOW.setAttackPower(30);
    COMMON_LONGBOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Lightning Strike (WEAPON)
   */
  public static final WeaponItem
    LIGHTNING_STRIKE = new ItemBuilder()
          .title("Lightning Strike")
          .description("It is said the arrows that fly from this bow are equal to the bolts of lightning that are thrown from the heavens by the gods. One can feel the energy coming from this weapon if close enough.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    LIGHTNING_STRIKE.setAttackPower(35);
    LIGHTNING_STRIKE.setMagicAttackPower(5);
    LIGHTNING_STRIKE.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Heavy Crossbow (WEAPON)
   */
  public static final WeaponItem
    HEAVY_CROSSBOW = new ItemBuilder()
          .title("Heavy Crossbow")
          .description("A serious crossbow with serious attacking power. Due to its bulky frame shots fired from this weapon have the ability to penetrate metal armor and to knock back opponents.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    HEAVY_CROSSBOW.setAttackPower(40);
    HEAVY_CROSSBOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Metallic Bow (WEAPON)
   */
  public static final WeaponItem
    METALLIC_BOW = new ItemBuilder()
          .title("Metallic Bow")
          .description("This special bow has a body crafted out of metal. It is heavier than a wood bow, but for those strong enough to use it the power that it generates is unparalleled.")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    METALLIC_BOW.setAttackPower(50);
    METALLIC_BOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Heavy Longbow (WEAPON)
   */
  public static final WeaponItem
    HEAVY_LONGBOW = new ItemBuilder()
          .title("Heavy Longbow")
          .description("A more powerful variant of the Common Longbow with increased range and arrow speed. This bow requires both a strong and tall user to effectively harness its power.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    HEAVY_LONGBOW.setAttackPower(55);
    HEAVY_LONGBOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Power Bow (WEAPON)
   */
  public static final WeaponItem
    POWER_BOW = new ItemBuilder()
          .title("Power Bow")
          .description("Built by a master weapon maker the Power Bow is unique in all of Spirelands. Its design produces a powerful shot and the ability to stab enemies should the user be engaged in melee combat.")
          .price(4500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    POWER_BOW.setAttackPower(60);
    POWER_BOW.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Golden Hawk (WEAPON)
   */
  public static final WeaponItem
    GOLDEN_HAWK = new ItemBuilder()
          .title("Golden Hawk")
          .description("A truely stunning bow! Highly accurate, powerful, compact and beautifully guilded in gold this bow is as much a joy to use as it is to look at.")
          .price(5000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GOLDEN_HAWK.setAttackPower(65);
    GOLDEN_HAWK.setMagicAttackPower(10);
    GOLDEN_HAWK.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Calamity Shot (WEAPON)
   */
  public static final WeaponItem
    CALAMITY_SHOT = new ItemBuilder()
          .title("Calamity Shot")
          .description("The Calamity Shot is a weapon of legend. It is said that the first shot ever taken from this bow caused a 100 year war between nations. Where this bow goes disaster follows in its wake.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 1, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    CALAMITY_SHOT.setAttackPower(80);
    CALAMITY_SHOT.setMagicAttackPower(15);
    CALAMITY_SHOT.setWeaponType(WeaponItem.BOW);
  }
  /**
   * Field Dagger (WEAPON)
   */
  public static final WeaponItem
    FIELD_DAGGER = new ItemBuilder()
          .title("Field Dagger")
          .description("A basic weapon for a basic purpose, protection. This knife is commonly used by pesants who work in the field for protection from wildlife and robbers.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    FIELD_DAGGER.setAttackPower(7);
    FIELD_DAGGER.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Shank Dagger (WEAPON)
   */
  public static final WeaponItem
    SHANK_DAGGER = new ItemBuilder()
          .title("Shank Dagger")
          .description("Simple in design, the Shank Dagger is a perfect travel companion as it is easy to carry and store. This dagger can be useful as a hunting knife or for personal protection.")
          .price(750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SHANK_DAGGER.setAttackPower(12);
    SHANK_DAGGER.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Bowie Knife (WEAPON)
   */
  public static final WeaponItem
    BOWIE_KNIFE = new ItemBuilder()
          .title("Bowie Knife")
          .description("This knife has a long curved thick blade with a tipped point and features a crossguard on the grip so you can press hard into what you are stabbing into.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    BOWIE_KNIFE.setAttackPower(20);
    BOWIE_KNIFE.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * 'Stumpblade' dagger (WEAPON)
   */
  public static final WeaponItem
    STUMPBLADE_DAGGER = new ItemBuilder()
          .title("Stumpblade")
          .description("Short and wide double edged blade with a high quality handle. The wideness of the blade adds weight which translates to harder hitting power but with reduced speed")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    STUMPBLADE_DAGGER.setAttackPower(25);
    STUMPBLADE_DAGGER.setWeaponType(WeaponItem.DAGGER);
  }
  
  public static final WeaponItem
    SERRATOR_DAGGER = new ItemBuilder()
          .title("Serrator")
          .description("Rip your enemies to shreds while you feel like you are slicing them like bread. With this blade at your side you are always ready to kill or just be useful in the kitchen.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SERRATOR_DAGGER.setAttackPower(30);
    SERRATOR_DAGGER.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Stiletto (WEAPON)
   */
  public static final WeaponItem
    STILETTO = new ItemBuilder()
          .title("Stiletto")
          .description("This long skinny dagger is quick to cut its foes due to its light weight. The Stiletto's extra length gives it the advantage of acting more like a short sword that does less damage.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    STILETTO.setAttackPower(35);
    STILETTO.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Dragon Claw (WEAPON)
   */
  public static final WeaponItem
    DRAGON_CLAW = new ItemBuilder()
          .title("Dragon Claw")
          .description("It is said that this knife has the same effect as a dragon impaling you with one of its deadly claws. The deadly inward curved tip of the blade digs into those that it is cutting.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    DRAGON_CLAW.setAttackPower(40);
    DRAGON_CLAW.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Shadow Blade (WEAPON)
   */
  public static final WeaponItem
    SHADOW_BLADE = new ItemBuilder()
          .title("Shadow Blade")
          .description("An evil blade that fills those who get near it with horror. He who holds the Shadow Blade may find themselves surrounded with shadows from the dark relm who looking to wreak havoc in this world.")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SHADOW_BLADE.setAttackPower(45);
    SHADOW_BLADE.setMagicAttackPower(8);
    SHADOW_BLADE.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Poison Dagger (WEAPON)
   */
  public static final WeaponItem
    POISON_DAGGER = new ItemBuilder()
          .title("Poison Dagger")
          .description("There is a legend of a blade so sinister that whoever owns it is cursed by a fatal tragedy. Said to be over 2000 years old the Poison Blade was the most powerful weapon in the world at the time.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    POISON_DAGGER.setAttackPower(50);
    POISON_DAGGER.setMagicAttackPower(15);
    POISON_DAGGER.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Kings Short Blade (WEAPON)
   */
  public static final WeaponItem
    KINGS_SHORTBLADE = new ItemBuilder()
          .title("King's Shortblade")
          .description("A noble weapon that blurs the line between dagger and sword. It's long double edge blade with protective hand guard makes it a superior sidearm for close combat situations.")
          .price(4500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    KINGS_SHORTBLADE.setAttackPower(55);
    KINGS_SHORTBLADE.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Crippler (WEAPON)
   */
  public static final WeaponItem
    CRIPPLER = new ItemBuilder()
          .title("The Crippler")
          .description("This dagger does massive internal damage to those who are stabbed by it. The dramatic ribbed sides of the blade help tear through flesh while stabbing or slashing enemies.")
          .price(5000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    CRIPPLER.setAttackPower(60);
    CRIPPLER.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Golden Touch (WEAPON)
   */
  public static final WeaponItem
    GOLDEN_TOUCH = new ItemBuilder()
          .title("Golden Touch")
          .description("The Golden Touch is a beautifuly crafted weapon that is equally as deadly. Unlike the name insinuates the blade does not turn everything it touches into gold, however, it will save your life in a fight.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 2, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GOLDEN_TOUCH.setAttackPower(70);
    GOLDEN_TOUCH.setMagicAttackPower(20);
    GOLDEN_TOUCH.setWeaponType(WeaponItem.DAGGER);
  }
  /**
   * Wooden Staff (WEAPON)
   */
  public static final WeaponItem
    WOODEN_STAFF = new ItemBuilder()
          .title("Wooden Staff")
          .description("A wooden staff to be used by a magic user. Though it is very simple in design and presentation it still holds some magical quality about it.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    WOODEN_STAFF.setAttackPower(1);
    WOODEN_STAFF.setMagicAttackPower(8);
    WOODEN_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Ancient Staff (WEAPON)
   */
  public static final WeaponItem
    ANCIENT_STAFF = new ItemBuilder()
          .title("Ancient Staff")
          .description("A staff as old as time. Once held by a great king wizard that ruled thousands of years ago this staff has stood the test of time and remained intact bound by the magical energy within.")
          .price(750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    ANCIENT_STAFF.setAttackPower(1);
    ANCIENT_STAFF.setMagicAttackPower(16);
    ANCIENT_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Caterpillar Staff (WEAPON)
   */
  public static final WeaponItem
    CATERPILLAR = new ItemBuilder()
          .title("Caterpillar")
          .description("Named after the green caterpillars found in the woods of the region this magical staff embodies the power of the insets and small animals of the forest.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    CATERPILLAR.setAttackPower(4);
    CATERPILLAR.setMagicAttackPower(24);
    CATERPILLAR.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Solar Staff (WEAPON)
   */
  public static final WeaponItem
    SOLAR_STAFF = new ItemBuilder()
          .title("Solar Staff")
          .description("This staff draws its magical powers from the star it is nearest. This means that in Spirelands the staff's power will remain mostly constant since there is only one star nearby.")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SOLAR_STAFF.setAttackPower(7);
    SOLAR_STAFF.setMagicAttackPower(31);
    SOLAR_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Life Staff (WEAPON)
   */
  public static final WeaponItem
    LIFE_STAFF = new ItemBuilder()
          .title("Life Staff")
          .description("All life around this staff embues it with power to create a strong magical aura. The Life Staff responds to the life energy like a well tuned chord making it resonate at times of high energy.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    LIFE_STAFF.setAttackPower(9);
    LIFE_STAFF.setMagicAttackPower(35);
    LIFE_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Metal Staff (WEAPON)
   */
  public static final WeaponItem
    METAL_STAFF = new ItemBuilder()
          .title("Metal Staff")
          .description("The alloy that makes this staff is unique. It was forged by a great wizard out of special high quality metals such as platinum and silver. No one truely knows how it was made.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    METAL_STAFF.setAttackPower(18);
    METAL_STAFF.setMagicAttackPower(40);
    METAL_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Enchanted Rod (WEAPON)
   */
  public static final WeaponItem
    ENCHANTED_ROD = new ItemBuilder()
          .title("Enchanted Rod")
          .description("Don't let the appearance of this staff deceive you, it is a powerful magical staff that is strong and light. Skilled users can summon more power by quickly spinning the rod overhead")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    ENCHANTED_ROD.setAttackPower(14);
    ENCHANTED_ROD.setMagicAttackPower(45);
    ENCHANTED_ROD.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Necromancer Staff (WEAPON)
   */
  public static final WeaponItem
    NECROMANCER_STAFF = new ItemBuilder()
          .title("Necromancer")
          .description("Evil energy pulsates out of this dark and mysterious staff. It has the power to summon the dead to do its user's bidding. The dark power can consume the user if they are weak spirited.")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    NECROMANCER_STAFF.setAttackPower(25);
    NECROMANCER_STAFF.setMagicAttackPower(50);
    NECROMANCER_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Fire Staff (WEAPON)
   */
  public static final WeaponItem
    FIRE_STAFF = new ItemBuilder()
          .title("Fire Staff")
          .description("Harness the power of fire with this staff! Fire energy swarms around the user improving their ability to cast fire type magic spells and attacks.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    FIRE_STAFF.setAttackPower(29);
    FIRE_STAFF.setMagicAttackPower(55);
    FIRE_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Shock Staff (WEAPON)
   */
  public static final WeaponItem
    SHOCK_STAFF = new ItemBuilder()
          .title("Shock Staff")
          .description("Send shockwaves of electrical energy out in all directions with increased power using this lightning based staff. Increases lightning spells and attacks")
          .price(4500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SHOCK_STAFF.setAttackPower(32);
    SHOCK_STAFF.setMagicAttackPower(60);
    SHOCK_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Tsunami Staff (WEAPON)
   */
  public static final WeaponItem
    TSUNAMI_STAFF = new ItemBuilder()
          .title("Tsunami")
          .description("Oceans of power are bestowed upon the user of this great enchanted staff. Within it the power of the ocean tides, currents, and the animals of the deap are unlocked.")
          .price(5000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    TSUNAMI_STAFF.setAttackPower(35);
    TSUNAMI_STAFF.setMagicAttackPower(65);
    TSUNAMI_STAFF.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Sorcerer's Heart Staff (WEAPON)
   */
  public static final WeaponItem
    SORCERERS_HEART = new ItemBuilder()
          .title("Sorcerer's Heart")
          .description("Once crafted by a legendar master Sorcerer this staff is designed to bring out the one true power that lies hidden inside each one of us. Only a true master wizard can use the powers of this staff.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 3, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SORCERERS_HEART.setAttackPower(38);
    SORCERERS_HEART.setMagicAttackPower(80);
    SORCERERS_HEART.setWeaponType(WeaponItem.STAFF);
  }
  /**
   * Simple Axe (WEAPON)
   */
  public static final WeaponItem
    SIMPLE_AXE = new ItemBuilder()
          .title("Simple Axe")
          .description("A small battle axe with a single sided blade that can easily be used with one hand. Not the most impressive weapon, but it comes in handy whenever you need to do some chopping.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SIMPLE_AXE.setAttackPower(10);
    SIMPLE_AXE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Sledgehammer (WEAPON)
   */
  public static final WeaponItem
    SLEDGEHAMMER = new ItemBuilder()
          .title("Sledgehammer")
          .description("You must be strong and skilled to properly use this hammer in combat. This hammer is very heavy and costs a lot of energy to swing suggesting that it may be better served as a tool.")
          .price(750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SLEDGEHAMMER.setAttackPower(17);
    SLEDGEHAMMER.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Broad axe (WEAPON)
   */
  public static final WeaponItem
    BROADAXE = new ItemBuilder()
          .title("Broadaxe")
          .description("The blade on this axe is actually two normal sized blades that have been forged together to create one broad blade. A larger blade makes for more damage with each hit.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    BROADAXE.setAttackPower(25);
    BROADAXE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Twin Crescent (WEAPON)
   */
  public static final WeaponItem
    TWIN_CRESCENT = new ItemBuilder()
          .title("Twin Crescent")
          .description("A long handled battle axe with a twin blade. Designed for hand to hand combat coming from all sides with its long reach and sharp, deadly, double sided blade.")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    TWIN_CRESCENT.setAttackPower(30);
    TWIN_CRESCENT.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * 'Masakari' axe (WEAPON)
   */
  public static final WeaponItem
    MASAKARI_AXE = new ItemBuilder()
          .title("Masakari")
          .description("Made with destruction in mind, this deadly weapon has a long, uniquely curved blade affixed to a short handle. Perfect for close combat situations where a light deadly weapon is needed.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    MASAKARI_AXE.setAttackPower(35);
    MASAKARI_AXE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Obsidian Axe (WEAPON)
   */
  public static final WeaponItem
    OBSIDIAN_AXE = new ItemBuilder()
          .title("Obsidian Axe")
          .description("This one of a kind weapon is both destructive and beautiful. A double sided axe blade made from carefully sharpened obsidian with a long rosewood handle makes this a weapon to be proud of.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    OBSIDIAN_AXE.setAttackPower(40);
    OBSIDIAN_AXE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Twin Broad blade axe (WEAPON)
   */
  public static final WeaponItem
    TWIN_BROADBLADE = new ItemBuilder()
          .title("Twin Broadblade")
          .description("The Twin Broadblade is a weapon for a serious warrior. The long cutting edges, and its supprising light weight are what stand out most about its design.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    TWIN_BROADBLADE.setAttackPower(45);
    TWIN_BROADBLADE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Doom Hammer (WEAPON)
   */
  public static final WeaponItem
    DOOM_HAMMER = new ItemBuilder()
          .title("Doom Hammer")
          .description("This is known by ledgend as 'the hammer of doom and destruction'. Only evil can come from those who use it and it is said to be the only weapon that can kill purely evil spirits.")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    DOOM_HAMMER.setAttackPower(50);
    DOOM_HAMMER.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Kings Axe (WEAPON)
   */
  public static final WeaponItem
    KINGS_AXE = new ItemBuilder()
          .title("Kings Axe")
          .description("An axe forged for a great king nearly two centuries ago. It is constructed with golden blades and its handle is adorned with golden hand grips and end pieces. A stunning and unique weapon.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    KINGS_AXE.setAttackPower(55);
    KINGS_AXE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Dragon Slayer (WEAPON)
   */
  public static final WeaponItem
    DRAGON_SLAYER = new ItemBuilder()
          .title("Dragon Slayer")
          .description("The dragon slayer is certainly powerful enough to live up to its name. The blades are shaped like the wings of a dragon in flight and forged with light weight and durable metals.")
          .price(4500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    DRAGON_SLAYER.setAttackPower(60);
    DRAGON_SLAYER.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Golden Brute (WEAPON)
   */
  public static final WeaponItem
    GOLDEN_BRUTE = new ItemBuilder()
          .title("Golden Brute")
          .description("A rare axe created entirely from gold. The brilliance of this weapon will stun enemies on the battle field. A unique treasure that is also capable of doing great amounts of damage.")
          .price(5000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GOLDEN_BRUTE.setAttackPower(65);
    GOLDEN_BRUTE.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Gods Hammer (WEAPON)
   */
  public static final WeaponItem
    GODS_HAMMER = new ItemBuilder()
          .title("Gods Hammer")
          .description("The ultimate weapon for anyone who has mastered the art of the axe and hammer. God's Hammer is made for the gods themselves and to use it is to be one with them.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 4, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GODS_HAMMER.setAttackPower(80);
    GODS_HAMMER.setMagicAttackPower(20);
    GODS_HAMMER.setWeaponType(WeaponItem.BATTLE_AXE);
  }
  /**
   * Pupil Wand (WEAPON)
   */
  public static final WeaponItem
    PUPIL_WAND = new ItemBuilder()
          .title("The Pupil")
          .description("This is the first wand that students of magic receive when they train at the school of wizardry. It is designed to use low level magic targeted for the budding wizard.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    PUPIL_WAND.setMagicAttackPower(8);
    PUPIL_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Graduate Wand (WEAPON)
   */
  public static final WeaponItem
    GRADUATE_WAND = new ItemBuilder()
          .title("The Graduate")
          .description("This wand is issued by the school of wizardry to those deserving students who have proven their skills in magic and made it to graduation.")
          .price(750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GRADUATE_WAND.setMagicAttackPower(15);
    GRADUATE_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Tri-Coil Wand (WEAPON)
   */
  public static final WeaponItem
    TRI_COIL_WAND = new ItemBuilder()
          .title("Tri-Coil Want")
          .description("An interesting wand that features a board tip wrapped in three distinct coils. These three coils radiate magical energy out of the wand which produces powerful attacks and spells.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    TRI_COIL_WAND.setMagicAttackPower(24);
    TRI_COIL_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Blunt Tip Wand (WEAPON)
   */
  public static final WeaponItem
    BLUNT_TIP_WAND = new ItemBuilder()
          .title("Blunt Tip Wand")
          .description("Some may mistake this wand for a hammer or some sort of gardening tool, but it is infact a wizard's wand to be used in casting magic spells. It can also be used as a hammer...")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    BLUNT_TIP_WAND.setAttackPower(8);
    BLUNT_TIP_WAND.setMagicAttackPower(30);
    BLUNT_TIP_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Earth Power (WEAPON)
   */
  public static final WeaponItem
    EARTH_POWER = new ItemBuilder()
          .title("Earth Power")
          .description("Harness the natural energy of the earth with this wand. Your allies are the plants and animals of nature that lend their power to the holder of this special magic wand.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    EARTH_POWER.setMagicAttackPower(35);
    EARTH_POWER.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Rainbow Wand (WEAPON)
   */
  public static final WeaponItem
    RAINBOW_WAND = new ItemBuilder()
          .title("Rainbow Wand")
          .description("Is this a toy? Is this a weapon? Is anyone supposed to take this thing seriously? Find out for yourself by trying the rainbow wand in your next encounter with a monster or a villain.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    RAINBOW_WAND.setMagicAttackPower(40);
    RAINBOW_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * 'Aquastrike' Wand (WEAPON)
   */
  public static final WeaponItem
    AQUASTRIKE_WAND = new ItemBuilder()
          .title("Aquastrike")
          .description("The aquastrike is a wand that specializes in harnessing the power of water. At first thought water may not seem all that powerful, however, the power of the tides is not to be underestimated.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    AQUASTRIKE_WAND.setMagicAttackPower(45);
    AQUASTRIKE_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Voodoo Wand (WEAPON)
   */
  public static final WeaponItem
    VOODOO_WAND = new ItemBuilder()
          .title("Voodoo Wand")
          .description("Those who use black magic are said to be in line with the devil or do the devils work. This wand was found in the home of three evil witches hundreds of years ago. Who knows what horror this wand has unleashed.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    VOODOO_WAND.setMagicAttackPower(50);
    VOODOO_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Wand Of Riches (WEAPON)
   */
  public static final WeaponItem
    WAND_OF_RICHES = new ItemBuilder()
          .title("Wand Of Riches")
          .description("The Wand Of Riches has been sought out by those looking for fortune and glory. Tales of this wand say that whomever uses it can create gold out of thin air!")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    WAND_OF_RICHES.setMagicAttackPower(55);
    WAND_OF_RICHES.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Electro Wand (WEAPON)
   */
  public static final WeaponItem
    ELECTRO_WAND = new ItemBuilder()
          .title("Electro")
          .description("There is a warning labeled on this wand that the electrical energy that this produces can kill the spellcaster if they are not careful. Better make sure you are properly grounded before use.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    ELECTRO_WAND.setMagicAttackPower(60);
    ELECTRO_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Azula's Wand (WEAPON)
   */
  public static final WeaponItem
    AZULAS_WAND = new ItemBuilder()
          .title("Azula's Wand")
          .description("Azula was a powerful wizard from a long time ago. Not much is known about her but her wand has survived the ages. Magical energy surges from the wand in an almost uncontrollable frenzy.")
          .price(4500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    AZULAS_WAND.setMagicAttackPower(65);
    AZULAS_WAND.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Fire Element (WEAPON)
   */
  public static final WeaponItem
    FIRE_ELEMENT = new ItemBuilder()
          .title("Fire Element")
          .description("A powerful wand that radiates intense heat and is capable of sending out waves of fire in all directions. Some say this wand rivals the power of the sun.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 5, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    FIRE_ELEMENT.setMagicAttackPower(80);
    FIRE_ELEMENT.setWeaponType(WeaponItem.WAND);
  }
  /**
   * Wood Club (WEAPON)
   */
  public static final WeaponItem
    WOOD_CLUB = new ItemBuilder()
          .title("Wood Club")
          .description("A primitive wood club to be used as an offensive weapon. Beat your foes into submission with this oversized stick, but just don't expect it to do much damage.")
          .price(550)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 0, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    WOOD_CLUB.setAttackPower(8);
    WOOD_CLUB.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Small Mace (WEAPON)
   */
  public static final WeaponItem
    SMALL_MACE = new ItemBuilder()
          .title("Small Mace")
          .description("This mace is perfect travel companion due to its small size and light weight. As far as maces go it is a bit on the too small size but can still be an effective weapon for self defense.")
          .price(750)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 1, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SMALL_MACE.setAttackPower(16);
    SMALL_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Steel Mace (WEAPON)
   */
  public static final WeaponItem
    STEEL_MACE = new ItemBuilder()
          .title("Steel Mace")
          .description("The head of this mace is made from steel that has been formed into a egg-like shape with a rough surface. The overall weight of this weapon makes each attack a slow methodical procedure.")
          .price(1000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 2, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    STEEL_MACE.setAttackPower(25);
    STEEL_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Snaggletooth Mace (WEAPON)
   */
  public static final WeaponItem
    SNAGGLETOOTH_MACE = new ItemBuilder()
          .title("Snaggletooth")
          .description("Holding this weapon you might feel like you belong to a band of wandering raiders. The Snaggletooth is a wood club with shards of metal sticking out of the top, which, have a tendency to get caught on things.")
          .price(1500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 3, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    SNAGGLETOOTH_MACE.setAttackPower(30);
    SNAGGLETOOTH_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Copperhead Mace (WEAPON)
   */
  public static final WeaponItem
    COPPERHEAD_MACE = new ItemBuilder()
          .title("Copperhead")
          .description("An interesting mace made of a special, strong copper alloy. At first it may seem a strange idea to make a weapon out of copper but the weight to strength ratio pays of in this case.")
          .price(2000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 4, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    COPPERHEAD_MACE.setAttackPower(35);
    COPPERHEAD_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Mad Ball Mac (WEAPON)
   */
  public static final WeaponItem
    MAD_BALL_MACE = new ItemBuilder()
          .title("Mad Ball")
          .description("Featuring a long handle with an intimidating metal head with sharp protruding spikes this mace is standard issue for many armies in the region. Truly a combat ready weapon.")
          .price(2500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 5, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    MAD_BALL_MACE.setAttackPower(40);
    MAD_BALL_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Blue Steel (WEAPON)
   */
  public static final WeaponItem
    BLUE_STEEL = new ItemBuilder()
          .title("Blue Steel")
          .description("The Blue Steel is a professional's weapon of choice. This mace is constructed with the finest of materials to create a both lightweight and strong metal shaped into a sleek and deadly design.")
          .price(3000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 6, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    BLUE_STEEL.setAttackPower(45);
    BLUE_STEEL.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Ebony Mace (WEAPON)
   */
  public static final WeaponItem
    EBONY_MACE = new ItemBuilder()
          .title("Ebony Mace")
          .description("This all black weapon is as dark as it's origins. Said to be used in the war between good and evil, the Ebony Mace was often seen in the hands of the evil forces hellbent on destruction.")
          .price(3500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 7, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    EBONY_MACE.setAttackPower(50);
    EBONY_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Gold Mace (WEAPON)
   */
  public static final WeaponItem
    GOLD_MACE = new ItemBuilder()
          .title("Gold Mace")
          .description("A mace gilded with gold and adorned with gold on its handle. The creator of this weapon wanted to make a something with the elegance fit for a king and the power to destroy any adversary.")
          .price(4000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 8, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GOLD_MACE.setAttackPower(55);
    GOLD_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Axe Club (WEAPON)
   */
  public static final WeaponItem
    AXE_CLUB = new ItemBuilder()
          .title("Axe Club")
          .description("Not quite an axe and not quite a club, it's the Axe Club! A unique combination of weapons come together to make this light and powerful weapon that can be used to slash and bash all in one!")
          .price(4500)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 9, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    AXE_CLUB.setAttackPower(60);
    AXE_CLUB.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Golden Nugget (WEAPON)
   */
  public static final WeaponItem
    GOLDEN_NUGGET = new ItemBuilder()
          .title("Golden Nugget")
          .description("Originally made for a wealthy prince who liked to spend his money on extravagant weapons this mace features a head that is made of a solid gold head. Your foes will become gold striken by it...literally.")
          .price(5000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 10, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    GOLDEN_NUGGET.setAttackPower(65);
    GOLDEN_NUGGET.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * Power Orb (WEAPON)
   */
  public static final WeaponItem
    POWER_ORB_MACE = new ItemBuilder()
          .title("Power Orb Mace")
          .description("A one of a kind treasure. The Power Orb is a mace with a magical orb as the head that is able to unleash spells and deadly physical attacks. Only a master of blunt weaponry should use the Power Orb.")
          .price(7000)
          .thumbnail(new Sprite(WEAPON_SPRITE_SIZE, SPRITE_SIZE, 11, 6, SpriteSheet.weaponsSheet))
          .build(WeaponItem.class);
  static {
    POWER_ORB_MACE.setAttackPower(80);
    POWER_ORB_MACE.setMagicAttackPower(20);
    POWER_ORB_MACE.setWeaponType(WeaponItem.BLUNT);
  }
  /**
   * A briefcase to hold more items. (SPECIAL)
   */
  public static final SpecialItem
    BRIEFCASE = new ItemBuilder()
          .title("Briefcase")
          .description("Use this briefcase to carry more items on your journey.")
          .price(350)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 11, SpriteSheet.itemsSheet))
          .build(SpecialItem.class);
  /**
   * A message scroll to be delivered. (SPECIAL)
   */
  public static final SpecialItem
    MESSAGE_SCROLL = new ItemBuilder()
          .title("Message Scroll")
          .description("A scroll with an urgent message to be sent to the king of the neighboring city. I wonder what it says...")
          .price(Item.NO_PRICE)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 5, 11, SpriteSheet.itemsSheet))
          .build(SpecialItem.class);
  /**
   * Creates an Item object with the specified configuration.
   * @param builder The item builder to create this item.
   */
  public Item(ItemBuilder builder) {
    this.id = getNextId();
    this.title = builder.title;
    this.description = builder.description;
    this.price = builder.price;
    this.thumbnail = builder.thumbnail;
    this.type = builder.type;
  }
  
  static {
    Item.ANCIENT_STAFF.setMap();
  }
  
  public void setMap() {
    Field[] fields = Item.class.getFields();
    Item fieldValue;
    for (Field fieldDef: fields) {
      try {
        fieldDef.setAccessible(true);
        fieldValue = (Item)fieldDef.get(this);
        Method getId = fieldValue.getClass().getDeclaredMethod("getId", new Class[]{});
        Item.ITEM_MAP.put((Integer)getId.invoke(fieldValue), fieldValue);
      } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException | ClassCastException ex) {
        // Fail silently
        //Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private int getNextId() {
    return nextId++;
  }

  public String getTitle() {
    return title;
  }
  
  public String getType() {
    return type;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }
  
  public Character getCharacter() {
    return character;
  }
  
  public ArrayList<ItemAttribute> getAttributes() {
    return attributes;
  }

  public String getDescription() {
    return description;
  }

  public Sprite getThumbnail() {
    return thumbnail;
  }

  public int getId() {
    return id;
  }

  public int getPrice() {
    return price;
  }
  /**
   * Adds this item to inventory.
   */
  public void addToInventory() {
    INVENTORY_MANAGER.addToInventory(this);
  }
  /**
   * Removes this item from inventory.
   */
  public void removeFromInventory() {
    INVENTORY_MANAGER.removeFromInventory(this);
  }
  
}
