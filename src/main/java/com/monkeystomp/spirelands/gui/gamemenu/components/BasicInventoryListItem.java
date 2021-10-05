package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;

/**
 * List item to show an inventory item with only an info button.
 * @author Aaron Michael McNulty
 */
public class BasicInventoryListItem extends InventoryListItem {

  public BasicInventoryListItem(InventoryReference ref, int x, int y, Consumer<Item> IShowItemDetails) {
    super(ref, x, y, IShowItemDetails);
  }

}
