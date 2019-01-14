package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.gui.controlls.DangerButton;
import com.monkeystomp.spirelands.gui.controlls.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Supplier;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EquippedItemSlot extends Button {

  private final Supplier<Item> IGetEquipment;
  private final ICallback handleUnequip,
                          handleInfo;
  private Item item;
  private GameMenuPrimaryButton info;
  private DangerButton unequip;
  private boolean popoverShowing = false;
  private final Sprite  popoverBackground = new Sprite("./resources/gui/popoverBackground.png"),
                        popoverBackgroundHover = new Sprite("./resources/gui/popoverBackgroundHover.png"),
                        popoverBackgroundDown = new Sprite("./resources/gui/popoverBackgroundDown.png");
  private Sprite currentPopoverBackground = popoverBackground;
  
  public EquippedItemSlot(int x, int y, Supplier<Item> IGetEquipment, ICallback handleInfo, ICallback handleUnequip) {
    super("", x, y, 19, 19, () -> {});
    this.IGetEquipment = IGetEquipment;
    this.handleInfo = handleInfo;
    this.handleUnequip = handleUnequip;
    createButtons();
    createButtonSprites();
  }
  
  private void createButtons() {
    info = new GameMenuPrimaryButton(
      "Info",
      x + width / 2 - 14,
      y + height / 2 + 17,
      25,
      11,
      () -> {
        closePopover();
        handleInfo.execute();
      }
    );
    unequip = new DangerButton(
      "Unequip",
      x + width / 2 + 14,
      y + height / 2 + 17,
      25,
      11,
      () -> {
        closePopover();
        handleUnequip.execute();
      }
    );
  }

  private void createButtonSprites() {
    button = createDefaultSprite();
    buttonHover = createHoverSprite();
    buttonDown = createDownSprite();
    currentButton = button;
  }
  
  private Sprite createDefaultSprite() {
    int[] pixels = new int[width * height];
    for (int yy = 0; yy < height; yy++) {
      for (int xx = 0; xx < width; xx++) {
        if (yy == 0 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_MUTED_TEXT;
        }
        else if (xx == 0 || xx == width - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_MUTED_TEXT;
        }
        else pixels[xx + yy * width] = GameColors.BLACK;
      }
    }
    return new Sprite(pixels, width, height);
  }
  
  private Sprite createHoverSprite() {
    int[] pixels = new int[width * height];
    for (int yy = 0; yy < height; yy++) {
      for (int xx = 0; xx < width; xx++) {
        if (yy == 0 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_DEFAULT_TEXT;
        }
        else if (xx == 0 || xx == width - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_DEFAULT_TEXT;
        }
        else pixels[xx + yy * width] = GameColors.BLACK;
      }
    }
    return new Sprite(pixels, width, height);
  }
  
  private Sprite createDownSprite() {
    int[] pixels = new int[width * height];
    for (int yy = 0; yy < height; yy++) {
      for (int xx = 0; xx < width; xx++) {
        if (yy == 0 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_LABEL_TEXT;
        }
        else if (xx == 0 || xx == width - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_LABEL_TEXT;
        }
        else pixels[xx + yy * width] = GameColors.BLACK;
      }
    }
    return new Sprite(pixels, width, height);
  }
  
  @Override
  protected void hover() {
    super.hover();
    currentPopoverBackground = popoverBackgroundHover;
  }
  
  @Override
  protected void down() {
    super.down();
    currentPopoverBackground = popoverBackgroundDown;
  }
  
  @Override
  protected void setDefault() {
    super.setDefault();
    currentPopoverBackground = popoverBackground;
  }
  
  @Override
  protected void click() {
    super.click();
    if (item != null) togglePopover();
  }
  
  private void togglePopover() {
    popoverShowing = !popoverShowing;
  }
  /**
   * Closes the popover for this item slot.
   */
  public void closePopover() {
    popoverShowing = false;
  }
  
  private void checkItem() {
    if (item == null || item != IGetEquipment.get()) item = IGetEquipment.get();
  }
  @Override
  public void update() {
    super.update();
    checkItem();
    if (popoverShowing) {
      info.update();
      unequip.update();
    }
  }
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    if (item != null) {
      screen.renderSprite(gl, x + 1, y + 1, item.getThumbnail(), false);
    }
    if (popoverShowing) {
      screen.renderSprite(gl, x - currentPopoverBackground.getWidth() / 2 + width / 2, y + 17, currentPopoverBackground, false);
      info.render(screen, gl);
      unequip.render(screen, gl);
    }
  }
  
}
