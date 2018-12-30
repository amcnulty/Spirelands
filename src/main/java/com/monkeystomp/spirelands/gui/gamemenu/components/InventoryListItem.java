package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;

/**
 *
 * @author Aaron Michael McNulty
 */
public class InventoryListItem {
  
  private final Sprite thumbnail;
  private final String  name,
                        description;
  private int amount;
  private final int X = 140,
                    Y;
  private final InventoryReference inventoryReference;
  private final Item item;
  private final FontInfo  label = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          amountFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private GameMenuPrimaryButton infoButton;
  private PrimaryButton useButton;

  public InventoryListItem(InventoryReference ref, int y) {
    this.inventoryReference = ref;
    this.item = ref.getItem();
    this.amount = ref.getAmount();
    this.thumbnail = item.getThumbnail();
    this.name = item.getTitle();
    this.description = item.getDescription();
    this.Y = y;
    setFonts();
    addButtons();
  }
  
  private void setFonts() {
    label.setText(name);
    label.setX(X + 12);
    label.setY(Y);
    amountFont.setText("" + amount);
    if (amount < 10) amountFont.setX(X + 108);
    else amountFont.setX(X + 105);
    amountFont.setY(Y);
  }
  
  private void addButtons() {
    infoButton = new GameMenuPrimaryButton("Info", X + 148, Y, 18, 11, () -> {item.useItem();});
    useButton = new PrimaryButton("Use", X + 126, Y, 18, 11, () -> {item.useItem();});
  }
  
  private void checkAmount() {
    if (amount != inventoryReference.getAmount()) {
      amount = inventoryReference.getAmount();
      setFonts();
    }
  }
  
  public void update() {
    checkAmount();
    useButton.update();
    infoButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, X - thumbnail.getWidth() / 2, Y - thumbnail.getHeight() / 2, thumbnail, false);
    screen.renderFonts(label);
    screen.renderFonts(amountFont);
    useButton.render(screen, gl);
    infoButton.render(screen, gl);
  }

}
