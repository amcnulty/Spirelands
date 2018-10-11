package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ItemBuilder {
  public String  title,
                  description;
  public Sprite thumbnail;
  public int id,
              price,
              type;
  public ICallback itemAction;

  public ItemBuilder title(String title) {
    this.title = title;
    return this;
  }
  
  public ItemBuilder description(String description) {
    this.description = description;
    return this;
  }
  
  public ItemBuilder thumbnail(Sprite thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }
  
  public ItemBuilder itemAction(ICallback itemAction) {
    this.itemAction = itemAction;
    return this;
  }
  
  public ItemBuilder price(int price) {
    this.price = price;
    return this;
  }
  
  public ItemBuilder type(int type) {
    this.type = type;
    return this;
  }
  
  public Item build() {
    return new Item(this);
  }
}