package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.Button;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class AbilitySlot extends Button {
  
  private final String type;
  private final boolean unlocked;
  private final int level, pointsToUpgrade;
  
  public AbilitySlot(int x, int y, String type, int pointsToUnlock, ICallback clickHandler) {
    super("", x, y, 19, 19, clickHandler);
    this.type = type;
    this.level = 1;
    this.unlocked = false;
    this.pointsToUpgrade = pointsToUnlock;
    createButtonSprites();
  }
  
  public AbilitySlot(int x, int y, String type, int level, int pointsToUpgrade, ICallback clickHandler) {
    super("", x, y, 19, 19, clickHandler);
    this.type = type;
    this.level = level;
    this.unlocked = true;
    this.pointsToUpgrade = pointsToUpgrade;
    createButtonSprites();
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
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
  }

}
