package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.DangerButton;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Item slot used in the weapon and armor views to display equipment items.
 * @author Aaron Michael McNulty
 */
public class EquippedItemSlot extends ItemSlot {

  private final Supplier<Item> IGetEquipment;
  private final ICallback handleUnequip;
  private DangerButton unequip;
  
  public EquippedItemSlot(int x, int y, Supplier<Item> IGetEquipment, Consumer<Item> handleInfo, ICallback handleUnequip, Boolean showPopoverTop) {
    super(x, y, handleInfo, showPopoverTop);
    this.IGetEquipment = IGetEquipment;
    this.handleUnequip = handleUnequip;
    createUnequipButton();
  }
  
  private void createUnequipButton() {
    unequip = new DangerButton(
      "Unequip",
      x + width / 2 + 14,
      showPopoverTop ? y + height /  2 - 19 : y + height / 2 + 19,
      25,
      11,
      () -> {
        closePopover();
        handleUnequip.execute();
      }
    );
    unequip.disableButtonClickSound();
  }
  
  private void checkItem() {
    if (item == null || item != IGetEquipment.get()) item = IGetEquipment.get();
  }
  @Override
  public void update() {
    super.update();
    checkItem();
    if (popoverShowing) {
      unequip.update();
    }
  }
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    if (popoverShowing) {
      unequip.render(screen, gl);
    }
  }
  
}
