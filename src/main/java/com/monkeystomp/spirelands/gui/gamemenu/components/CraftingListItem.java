package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.monkeystomp.spirelands.crafting.CraftingManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * List item for crafting items that reacts to quantity of items on the crafting table.
 * @author Aaron Michael McNulty
 */
public class CraftingListItem extends UsableInventoryListItem {
  
  public CraftingListItem(InventoryReference ref, int x, int y, String primaryButtonText, Consumer<Item> IHandlePrimaryClick, Consumer<Item> IShowItemDetails) {
    super(ref, x, y, primaryButtonText, IHandlePrimaryClick, IShowItemDetails);
  }
  
  public void refresh() {
    int quantityOfTypeOnTable = CraftingManager.getCraftingManager().getCraftingTableItems().stream()
      .filter(craftingItem -> craftingItem.getId() == inventoryReference.getItem().getId())
      .collect(Collectors.toList()).size();
    int inventoryLessQuantityOnTable = inventoryReference.getAmount() - quantityOfTypeOnTable;
    amountFont.setText("" + inventoryLessQuantityOnTable);
    if (inventoryLessQuantityOnTable == 0) {
      useButton.setDisabled(true);
    }
    else {
      useButton.setDisabled(false);
    }
  }

}
