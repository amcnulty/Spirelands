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
   * A basic health potion. (EQUIPMENT)
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
   * Basic leather helmet. (ARMOR)
   */
  public static final ArmorItem
    LEATHER_HELMET = new ItemBuilder()
          .title("Leather Helmet")
          .description("A basic leather helmet used for defense.")
          .price(300)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 1, 1, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  /**
   * Steel boots. (ARMOR)
   */
  public static final ArmorItem
    STEEL_BOOTS = new ItemBuilder()
          .title("Steel Boots")
          .description("Military grade steel boots. Goes best with a matching suit of armor.")
          .price(2500)
          .thumbnail(new Sprite(SPRITE_SIZE, SPRITE_SIZE, 4, 2, SpriteSheet.itemsSheet))
          .build(ArmorItem.class);
  /**
   * Common sword. (WEAPON)
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
   * Katana sword. (WEAPON)
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
   * Sky Saber. (WEAPON)
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
   * Scimitar sword. (WEAPON)
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
   * Crusader sword. (WEAPON)
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
   * Talon sword. (WEAPON)
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
   * 'Spellblade'. (WEAPON)
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
   * Dark Sword. (WEAPON)
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
   * Poison Sword. (WEAPON)
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
   * 'Longsword'. (WEAPON)
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
   * Dragon Sword. (WEAPON)
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
   * Sword Of The Spire. (WEAPON)
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
