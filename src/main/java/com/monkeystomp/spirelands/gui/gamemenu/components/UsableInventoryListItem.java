package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;

/**
 * List item to show an inventory item with a primary action button and info button.
 * @author Aaron Michael McNulty
 */
public class UsableInventoryListItem extends InventoryListItem {
  
  private final String primaryButtonText;
  protected PrimaryButton useButton;
  private final Consumer<Item> IHandlePrimaryClick;

  public UsableInventoryListItem(InventoryReference ref, int x, int y, String primaryButtonText, Consumer<Item> IHandlePrimaryClick, Consumer<Item> IShowItemDetails) {
    super(ref, x, y, IShowItemDetails);
    this.primaryButtonText = primaryButtonText;
    this.IHandlePrimaryClick = IHandlePrimaryClick;
    addPrimaryButton();
  }
  
  private void addPrimaryButton() {
    useButton = new PrimaryButton(primaryButtonText, x + 126, y, 19, 11, () -> IHandlePrimaryClick.accept(item));
    useButton.disableButtonClickSound();
  }
  
  public void hideAmountFont() {
    amountFont.setText("");
  }
  
  @Override
  public void update() {
    super.update();
    useButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    useButton.render(screen, gl);
  }

}
