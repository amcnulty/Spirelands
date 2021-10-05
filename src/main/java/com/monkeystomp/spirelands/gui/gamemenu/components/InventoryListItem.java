package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;

/**
 * List item to show an inventory item.
 * @author Aaron Michael McNulty
 */
public abstract class InventoryListItem {
  
  private final Sprite thumbnail;
  private final String name;
  private int amount;
  protected final int x, y;
  private final InventoryReference inventoryReference;
  protected final Item item;
  private final FontInfo  label = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          amountFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final GameMenuPrimaryButton infoButton;
  private final Consumer<Item>  IShowItemDetails;

  public InventoryListItem(InventoryReference ref, int x, int y, Consumer<Item> IShowItemDetails) {
    this.inventoryReference = ref;
    this.item = ref.getItem();
    this.amount = ref.getAmount();
    this.thumbnail = item.getThumbnail();
    this.name = item.getTitle();
    this.x = x;
    this.y = y;
    this.IShowItemDetails = IShowItemDetails;
    this.infoButton = new GameMenuPrimaryButton("Info", x + 148, y, 19, 11, () -> this.IShowItemDetails.accept(item));
    setFonts();
  }
  
  private void setFonts() {
    label.setText(name);
    label.setX(x + 12);
    label.setY(y);
    amountFont.setText("" + amount);
    amountFont.setX(x + 112);
    amountFont.setY(y);
    amountFont.rightAlignText();
  }
  
  private void checkAmount() {
    if (amount != inventoryReference.getAmount()) {
      amount = inventoryReference.getAmount();
      setFonts();
    }
  }
  
  public void update() {
    checkAmount();
    infoButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - thumbnail.getWidth() / 2, y - thumbnail.getHeight() / 2, thumbnail, false);
    screen.renderFonts(label);
    screen.renderFonts(amountFont);
    infoButton.render(screen, gl);
  }

}
