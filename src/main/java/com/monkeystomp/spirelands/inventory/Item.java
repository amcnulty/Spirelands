package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.character.Character;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Item class holds information about game items and has an interface for interacting with the inventory for the item.
 * @author Aaron Michael McNulty
 */
public class Item {

  private final String  title,
                        description;
  private final Sprite thumbnail;
  private final int id,
                    price,
                    type;
  private static int nextId = 0;
  private Character character;
  protected final ArrayList<ItemAttribute> attributes = new ArrayList<>();
  protected static final InventoryManager INVENTORY_MANAGER = InventoryManager.getInventoryManager();
  private static final int  SPRITE_SIZE = 16,
                            WEAPON_SPRITE_SIZE = 32,
                            NO_PRICE = -1;
  // The types to assign items.
  public static final int WEAPON = 0,
                          ARMOR = 1,
                          EQUIPMENT = 2,
                          SPECIAL = 3;
  private static final HashMap<Integer, String> TYPE_MAP = new HashMap<>();
  static {
    TYPE_MAP.put(WEAPON, "Weapon");
    TYPE_MAP.put(ARMOR, "Armor");
    TYPE_MAP.put(EQUIPMENT, "Equipment");
    TYPE_MAP.put(SPECIAL, "Special");
  }
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!        Item Declarations       !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * A basic health potion (EQUIPMENT)
   */
  public static final EquipmentItem
    HEALTH_POTION = new ItemBuilder()
          .title("Health Potion")
          .description("A basic health potion for replenishing HP.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 8, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    HEALTH_POTION.setHealingPoints(100);
  }
  /**
   * A cookie (EQUIPMENT)
   */
  public static final EquipmentItem
    COOKIE = new ItemBuilder()
          .title("Cookie")
          .description("A freshly baked cookie. Looks delicious.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 8, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  static {
    COOKIE.setHealingPoints(5);
    COOKIE.setManaRestorePoints(5);
  }
  /**
   * A training book to increase stats (EQUIPMENT)
   */
  public static final EquipmentItem
    TRAINING_BOOK_V1 = new ItemBuilder()
          .title("Training Book v.1")
          .description("Volume 1 of the training book series. This book will teach the basic skills that it takes to increase ones overall skills.")
          .price(1000)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 3, 11, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  /**
   * A training book to increase stats (EQUIPMENT)
   */
  public static final EquipmentItem
    TRAINING_BOOK_V2 = new ItemBuilder()
          .title("Training Book v.2")
          .description("Volume 2 of the training book series. In this installment fighters will learn specific tactics to react the next level of their trainin'g.")
          .price(2000)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 11, SpriteSheet.itemsSheet))
          .build(EquipmentItem.class);
  /**
   * Basic leather helmet (ARMOR)
   */
  public static final ArmorItem
    LEATHER_HELMET = new ItemBuilder()
          .title("Leather Helmet")
          .description("A basic leather helmet used for defense.")
          .price(300)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  /**
   * Steel boots (ARMOR)
   */
  public static final ArmorItem
    STEEL_BOOTS = new ItemBuilder()
          .title("Steel Boots")
          .description("Military grade steel boots. Goes best with a matching suit of armor.")
          .price(2500)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 2, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
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
  }
  /**
   * The blood axe. (WEAPON)
   */
  public static final WeaponItem
    BLOOD_AXE = new ItemBuilder()
          .title("Blood Axe")
          .description("The blood axe of lore. Its blade is stained by the blood of its victims.")
          .price(3300)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 5, SpriteSheet.itemsSheet))
          .build(WeaponItem.class);
  static {
    BLOOD_AXE.setAttackPower(50);
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
  
  private int getNextId() {
    return nextId++;
  }

  public String getTitle() {
    return title;
  }
  
  public int getType() {
    return type;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }
  
  public Character getCharacter() {
    return character;
  }
  /**
   * Returns the type of this item as a string to be displayed.
   * @param type The type of this item to use to lookup the string type.
   * @return The string type of this item in a displayable format.
   */
  public String getTypeAsString(int type) {
    return TYPE_MAP.get(type);
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
