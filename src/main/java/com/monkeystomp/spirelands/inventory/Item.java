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
  private static final int SPRITE_SIZE = 16;
  // The types to assign items.
  public static final int WEAPON = 0,
                          ARMOR = 1,
                          EQUIPMENT = 2,
                          SPECIAL = 3;
  
  
  // Health Potion
  public static final Item HEALTH_POTION = new ItemBuilder()
          .title("Health Potion")
          .description("A basic health potion for replenishing HP.")
          .price(100)
          .thumbnail(new Sprite(SPRITE_SIZE, 8, 8, SpriteSheet.itemsSheet))
          .type(Item.EQUIPMENT)
          .itemAction(() -> {
            System.out.println("Performing action of health potion");
            INVENTORY_MANAGER.removeFromInventory(Item.HEALTH_POTION);
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