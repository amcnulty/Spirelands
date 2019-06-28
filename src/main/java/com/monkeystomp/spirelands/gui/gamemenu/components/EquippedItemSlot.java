package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.Button;
import com.monkeystomp.spirelands.gui.controlls.button.DangerButton;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Mouse;
import com.monkeystomp.spirelands.inventory.Item;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EquippedItemSlot extends Button {

  private final Supplier<Item> IGetEquipment;
  private final ICallback handleUnequip;
  private final Consumer<Item> handleInfo;
  private Item item;
  private GameMenuPrimaryButton info;
  private DangerButton unequip;
  private boolean popoverShowing = false,
                  markPopoverForClose = false,
                  mouseListenerSet = false;
  private final boolean showPopoverTop;
  private final Sprite  popoverBackground = new Sprite("./resources/gui/popoverBackground.png"),
                        popoverBackgroundHover = new Sprite("./resources/gui/popoverBackgroundHover.png"),
                        popoverBackgroundDown = new Sprite("./resources/gui/popoverBackgroundDown.png"),
                        popoverBackgroundTop = new Sprite("./resources/gui/popoverBackground_top.png"),
                        popoverBackgroundHoverTop = new Sprite("./resources/gui/popoverBackgroundHover_top.png"),
                        popoverBackgroundDownTop = new Sprite("./resources/gui/popoverBackgroundDown_top.png");
  private Sprite currentPopoverBackground = popoverBackground;
  private final Consumer<MouseEvent> mouseListener = event -> {if (popoverShowing) checkForClickOutside(event);};
  
  public EquippedItemSlot(int x, int y, Supplier<Item> IGetEquipment, Consumer<Item> handleInfo, ICallback handleUnequip, Boolean showPopoverTop) {
    super("", x, y, 19, 19, () -> {});
    this.IGetEquipment = IGetEquipment;
    this.handleInfo = handleInfo;
    this.handleUnequip = handleUnequip;
    this.showPopoverTop = showPopoverTop;
    createButtons();
    createButtonSprites();
  }
  
  private void createButtons() {
    info = new GameMenuPrimaryButton(
      "Info",
      x + width / 2 - 14,
      showPopoverTop ? y + height /  2 - 19 : y + height / 2 + 19,
      25,
      11,
      () -> {
        closePopover();
        handleInfo.accept(item);
      }
    );
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
        else pixels[xx + yy * width] = GameColors.EQUIPPED_ITEM_SLOT_BACKGROUND;
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
        else pixels[xx + yy * width] = GameColors.EQUIPPED_ITEM_SLOT_HOVER;
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
        else pixels[xx + yy * width] = GameColors.EQUIPPED_ITEM_SLOT_DOWN;
      }
    }
    return new Sprite(pixels, width, height);
  }
  
  private void addMouseListener() {
    Mouse.getMouse().addMouseClickListener(mouseListener);
    mouseListenerSet = true;
  }
  
  private void removeMouseListener() {
    Mouse.getMouse().removeMouseClickListener(mouseListener);
    mouseListenerSet = false;
  }
  
  private void checkForClickOutside(MouseEvent event) {
    int top = showPopoverTop ? y - 19 : y + 17,
        right = x - currentPopoverBackground.getWidth() / 2 + width / 2 + currentPopoverBackground.getWidth(),
        bottom = showPopoverTop ? y - 19 + currentPopoverBackground.getHeight() : y + 17 + currentPopoverBackground.getHeight(),
        left = x - currentPopoverBackground.getWidth() / 2 + width / 2;
    if (!(
        (   event.getX() / Screen.getScaleX() >= left
        &&  event.getX() / Screen.getScaleX() <= right
        &&  event.getY() / Screen.getScaleY() >= top
        &&  event.getY() / Screen.getScaleY() <= bottom
        )
        ||
        (
            event.getX() / Screen.getScaleX() >= x
        &&  event.getX() / Screen.getScaleX() <= x + width
        &&  event.getY() / Screen.getScaleY() >= y
        &&  event.getY() / Screen.getScaleY() <= y + height
        )
        )) {
      markPopoverForClose = true;
    }
  }
  
  @Override
  protected void hover() {
    super.hover();
    currentPopoverBackground = showPopoverTop ? popoverBackgroundHoverTop : popoverBackgroundHover;
  }
  
  @Override
  protected void down() {
    super.down();
    currentPopoverBackground = showPopoverTop ? popoverBackgroundDownTop : popoverBackgroundDown;
  }
  
  @Override
  protected void setDefault() {
    super.setDefault();
    currentPopoverBackground = showPopoverTop ? popoverBackgroundTop : popoverBackground;
  }
  
  @Override
  protected void click() {
    super.click();
    if (item != null) togglePopover();
    if (popoverShowing && !mouseListenerSet) addMouseListener();
    else if (!popoverShowing && mouseListenerSet) removeMouseListener();
  }
  
  private void togglePopover() {
    popoverShowing = !popoverShowing;
  }
  /**
   * Closes the popover for this item slot.
   */
  public void closePopover() {
    popoverShowing = false;
    markPopoverForClose = false;
    removeMouseListener();
  }
  
  private void checkItem() {
    if (item == null || item != IGetEquipment.get()) item = IGetEquipment.get();
  }
  @Override
  public void update() {
    super.update();
    checkItem();
    if (markPopoverForClose) closePopover();
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
      screen.renderSprite(gl, x - currentPopoverBackground.getWidth() / 2 + width / 2, showPopoverTop ? y - 19 : y + 17, currentPopoverBackground, false);
      info.render(screen, gl);
      unequip.render(screen, gl);
    }
  }
  
}
