package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
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
  private final ICallback itemAction;
  private static final InventoryManager INVENTORY_MANAGER = InventoryManager.getInventoryManager();
  private static final int  SPRITE_SIZE = 16,
                            NO_PRICE = -1;
  // The types to assign items.
  public static final int WEAPON = 0,
                          ARMOR = 1,
                          EQUIPMENT = 2,
                          SPECIAL = 3;
  
  
  // Health Potion
  public static final Item
    HEALTH_POTION = new ItemBuilder()
          .title("Health Potion")
          .description("A basic health potion for replenishing HP.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, 8, 8, SpriteSheet.itemsSheet))
          .type(EQUIPMENT)
          .itemAction(() -> {
            System.out.println("Performing action of health potion");
            INVENTORY_MANAGER.removeFromInventory(Item.HEALTH_POTION);
          })
          .build(),
    COOKIE = new ItemBuilder()
          .title("Cookie")
          .description("A freshly baked cookie. Looks delicious.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, 3, 8, SpriteSheet.itemsSheet))
          .type(EQUIPMENT)
          .itemAction(() -> {
            System.out.println("Performing action of cookie");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build(),
    LEATHER_HELMET = new ItemBuilder()
          .title("Leather Helmet")
          .description("A basic leather helmet used for defense.")
          .price(300)
          .thumbnail(new Sprite(SPRITE_SIZE, 1, 1, SpriteSheet.itemsSheet))
          .type(ARMOR)
          .itemAction(() -> {
            System.out.println("Performing action of leather helmet");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build(),
    STEEL_BOOTS = new ItemBuilder()
          .title("Steel Boots")
          .description("Military grade steel boots. Goes best with a matching suit of armor.")
          .price(2500)
          .thumbnail(new Sprite(SPRITE_SIZE, 4, 2))
          .type(ARMOR)
          .itemAction(() -> {
            System.out.println("Performing action of Steel Boots");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build(),
    COMMON_SWORD = new ItemBuilder()
          .title("Common Sword")
          .description("A common sword wielded by many townspeople across Spirelands.")
          .price(550)
          .thumbnail(new Sprite(SPRITE_SIZE, 6, 3, SpriteSheet.itemsSheet))
          .type(WEAPON)
          .itemAction(() -> {
            System.out.println("Performing action of Common Sword");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build(),
    BLOOD_AXE = new ItemBuilder()
          .title("Blood Axe")
          .description("The blood axe of lore. Its blade is stained by the blood of its victims.")
          .price(3300)
          .thumbnail(new Sprite(SPRITE_SIZE, 4, 5, SpriteSheet.itemsSheet))
          .type(WEAPON)
          .itemAction(() -> {
            System.out.println("Performing action of cookie");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build(),
    BRIEFCASE = new ItemBuilder()
          .title("Briefcase")
          .description("Use this briefcase to carry more items on your journey.")
          .price(350)
          .thumbnail(new Sprite(SPRITE_SIZE, 1, 11, SpriteSheet.itemsSheet))
          .type(SPECIAL)
          .itemAction(() -> {
            System.out.println("Performing action of Briefcase");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build(),
    MESSAGE_SCROLL = new ItemBuilder()
          .title("Message Scroll")
          .description("A scroll with an urgent message to be sent to the king of the neighboring city. I wonder what it says...")
          .price(Item.NO_PRICE)
          .thumbnail(new Sprite(SPRITE_SIZE, 5, 11, SpriteSheet.itemsSheet))
          .type(SPECIAL)
          .itemAction(() -> {
            System.out.println("Performing action of cookie");
            INVENTORY_MANAGER.removeFromInventory(Item.COOKIE);
          })
          .build();
          

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
  
  public void addToInventory() {
    INVENTORY_MANAGER.addToInventory(this);
  }
  
  public void removeFromInventory() {
    INVENTORY_MANAGER.removeFromInventory(this);
  }
  
  public void useItem() {
    this.itemAction.execute();
  }
}