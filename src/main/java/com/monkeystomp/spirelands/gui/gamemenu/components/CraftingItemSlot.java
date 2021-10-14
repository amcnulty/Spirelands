package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.DangerButton;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;

/**
 * Item slot used in the crafting view.
 * @author Aaron Michael McNulty
 */
public class CraftingItemSlot extends ItemSlot {
  
  private final Consumer<ItemSlot> onAdd;
  private final ICallback onRemove;
  
  private DangerButton remove = new DangerButton(
    "Remove",
    x + width / 2 + 14,
    showPopoverTop ? y + height / 2 - 19 : y + height / 2 + 19,
    25,
    11,
    () -> {
      closePopover();
      handleRemoveClick();
    }
  );
  
  private PrimaryButton add = new PrimaryButton(
    "Add",
    x + width / 2 + 14,
    showPopoverTop ? y + height / 2 - 19 : y + height / 2 + 19,
    25,
    11,
    () -> {
      closePopover();
      handleAddClick();
    }
  );
  
  public CraftingItemSlot(int x, int y, Consumer<Item> handleInfo, Boolean showPopoverTop, Consumer<ItemSlot> onAdd, ICallback onRemove) {
    super(x, y, handleInfo, showPopoverTop);
    this.onAdd = onAdd;
    this.onRemove = onRemove;
    remove.setClickSound(SoundEffects.UNEQUIP_ARMOR);
  }
  
  private void handleAddClick() {
    onAdd.accept(this);
  }
  
  private void handleRemoveClick() {
    removeItem();
    onRemove.execute();
  }
  
  @Override
  protected void click() {
    super.click();
    if (item == null) {
      togglePopover();
      super.click();
    }
  }
  
  @Override
  public void update() {
    super.update();
    if (item == null && !info.isDisabled()) info.setDisabled(true);
    else if (item != null && info.isDisabled()) info.setDisabled(false);
    if (item != null && popoverShowing) {
      remove.update();
    }
    else if (item == null && popoverShowing) {
      add.update();
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    if (item != null && popoverShowing) {
      remove.render(screen, gl);
    }
    else if (item == null && popoverShowing) {
      add.render(screen, gl);
    }
  }

}
