package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.input.ICallback;
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
  private final ICallback itemAction;
  protected final ArrayList<ItemAttribute> attributes = new ArrayList<>();
  protected static final InventoryManager INVENTORY_MANAGER = InventoryManager.getInventoryManager();
  private static final int  SPRITE_SIZE = 16,
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
  // Item declarations
  /**
   * A basic health potion. (EQUIPMENT)
   */
  public static final EquipmentItem
    HEALTH_POTION = new ItemBuilder()
          .title("Health Potion")
          .description("A basic health potion for replenishing HP.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, 8, 8, SpriteSheet.itemsSheet))
          .itemAction(() -> {
            System.out.println("Performing action of health potion");
          })
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
          .thumbnail(new Sprite(SPRITE_SIZE, 3, 8, SpriteSheet.itemsSheet))
          .itemAction(() -> {
            System.out.println("Performing action of cookie");
          })
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
          .thumbnail(new Sprite(SPRITE_SIZE, 1, 1, SpriteSheet.itemsSheet))
          .itemAction(() -> {
            System.out.println("Performing action of leather helmet");
          })
          .build(ArmorItem.class);
  /**
   * Steel boots. (ARMOR)
   */
  public static final ArmorItem
    STEEL_BOOTS = new ItemBuilder()
          .title("Steel Boots")
          .description("Military grade steel boots. Goes best with a matching suit of armor.")
          .price(2500)
          .thumbnail(new Sprite(SPRITE_SIZE, 4, 2))
          .itemAction(() -> {
            System.out.println("Performing action of Steel Boots");
          })
          .build(ArmorItem.class);
  /**
   * Basic sword. (WEAPON)
   */
  public static final WeaponItem
    COMMON_SWORD = new ItemBuilder()
          .title("Common Sword")
          .description("A common sword wielded by many townspeople across Spirelands.")
          .price(550)
          .thumbnail(new Sprite(SPRITE_SIZE, 6, 3, SpriteSheet.itemsSheet))
          .itemAction(() -> {
            System.out.println("Performing action of Common Sword");
          })
          .build(WeaponItem.class);
  /**
   * The blood axe. (WEAPON)
   */
  public static final WeaponItem
    BLOOD_AXE = new ItemBuilder()
          .title("Blood Axe")
          .description("The blood axe of lore. Its blade is stained by the blood of its victims.")
          .price(3300)
          .thumbnail(new Sprite(SPRITE_SIZE, 4, 5, SpriteSheet.itemsSheet))
          .itemAction(() -> {
            System.out.println("Performing action of cookie");
          })
          .build(WeaponItem.class);
  /**
   * A briefcase to hold more items. (SPECIAL)
   */
  public static final SpecialItem
    BRIEFCASE = new ItemBuilder()
          .title("Briefcase")
          .description("Use this briefcase to carry more items on your journey.")
          .price(350)
          .thumbnail(new Sprite(SPRITE_SIZE, 1, 11, SpriteSheet.itemsSheet))
          .itemAction(() -> {
            System.out.println("Performing action of Briefcase");
          })
          .build(SpecialItem.class);
  /**
   * A message scroll to be delivered. (SPECIAL)
   */
  public static final SpecialItem
    MESSAGE_SCROLL = new ItemBuilder()
          .title("Message Scroll")
          .description("A scroll with an urgent message to be sent to the king of the neighboring city. I wonder what it says...")
          .price(Item.NO_PRICE)
          .thumbnail(new Sprite(SPRITE_SIZE, 5, 11, SpriteSheet.itemsSheet))
          .type(SPECIAL)
          .itemAction(() -> {
            System.out.println("Performing action of cookie");
          })
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
    this.itemAction = builder.itemAction;
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
  /**
   * Calls the action method that has been set for this item.
   */
  public void useItem() {
    this.itemAction.execute();
  }
}
