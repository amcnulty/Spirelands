package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * The ItemBuilder class is used in the to configure an Item and call its constructor.
 * </p>
 * <h3>
 * Example Usage:
 * </h3>
 * <pre>
 * {@code
 * public static final Item
 *   COMMON_SWORD = new ItemBuilder()}
 *     .title("Common Sword")
 *     .description("A common sword wielded by many townspeople across Spirelands.")
 *     .price(550)
 *     .thumbnail(new Sprite(SPRITE_SIZE, 6, 3, SpriteSheet.itemsSheet))
 *     .build();}
 * </pre>
 * @author Aaron Michael McNulty
 */
public class ItemBuilder {
  /**
   * The display title of the item.
   */
  public String title;
  /**
   * The description of the item.
   */
  public String description;
  /**
   * The thumbnail image of the item.
   */
  public Sprite thumbnail;
  /**
   * The price of the item.
   */
  public int price;
  /**
   * The type of the item.
   */
  public int type;
  /**
   * Sets the display title of the item.
   * @param title The string to set as the title of the item.
   * @return This ItemBuilder reference.
   */
  public ItemBuilder title(String title) {
    this.title = title;
    return this;
  }
  /**
   * Sets the description of the item.
   * @param description The string to set as the description of the item.
   * @return This ItemBuilder reference.
   */
  public ItemBuilder description(String description) {
    this.description = description;
    return this;
  }
  /**
   * Sets the thumbnail of the item.
   * @param thumbnail Sprite to set as the thumbnail of the item.
   * @return This ItemBuilder reference.
   */
  public ItemBuilder thumbnail(Sprite thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }
  /**
   * Sets the price of the item.
   * @param price The price to set for this item.
   * @return This ItemBuilder reference.
   */
  public ItemBuilder price(int price) {
    this.price = price;
    return this;
  }
  /**
   * Sets the type of the item.
   * @param type The type to set for this item
   * @return This ItemBuilder reference.
   */
  public ItemBuilder type(int type) {
    this.type = type;
    return this;
  }
  /**
   * Builds this item by calling the Item class constructor with this instance of ItemBuilder as the argument.
   * @param <T> The type of item.
   * @param itemClass The class reference of the item.
   * @return A newly created Item instance.
   */
  public <T extends Item> T build(Class<T> itemClass) {
    try {
      return itemClass.getConstructor(this.getClass()).newInstance(this);
    }
    catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public Item build() {
    return new Item(this);
  }
  
}
