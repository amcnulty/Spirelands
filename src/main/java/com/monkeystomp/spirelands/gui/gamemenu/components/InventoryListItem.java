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
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class InventoryListItem {
  
  private final Sprite thumbnail;
  private final String  name,
                        primaryButtonText,
                        description;
  private int amount;
  private final int x = 140,
                    y;
  private final InventoryReference inventoryReference;
  private final Item item;
  private final FontInfo  label = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          amountFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private GameMenuPrimaryButton infoButton;
  private PrimaryButton useButton;
  private final Consumer<Item>  IShowItemDetails,
                                IHandlePrimaryClick;

  public InventoryListItem(InventoryReference ref, int y, String primaryButtonText, Consumer<Item> IHandlePrimaryClick, Consumer<Item> IShowItemDetails) {
    this.inventoryReference = ref;
    this.item = ref.getItem();
    this.amount = ref.getAmount();
    this.thumbnail = item.getThumbnail();
    this.name = item.getTitle();
    this.primaryButtonText = primaryButtonText;
    this.description = item.getDescription();
    this.y = y;
    this.IHandlePrimaryClick = IHandlePrimaryClick;
    this.IShowItemDetails = IShowItemDetails;
    setFonts();
    addButtons();
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
  
  private void addButtons() {
    infoButton = new GameMenuPrimaryButton("Info", x + 148, y, 19, 11, () -> IShowItemDetails.accept(item));
    useButton = new PrimaryButton(primaryButtonText, x + 126, y, 19, 11, () -> IHandlePrimaryClick.accept(item));
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
    screen.renderSprite(gl, x - thumbnail.getWidth() / 2, y - thumbnail.getHeight() / 2, thumbnail, false);
    screen.renderFonts(label);
    screen.renderFonts(amountFont);
    useButton.render(screen, gl);
    infoButton.render(screen, gl);
  }

}
