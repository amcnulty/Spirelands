package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.Button;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import java.util.function.Consumer;

/**
 * Ability slots are shown in the game menu under the Abilities section and represent what battle moves are currently equipped for a particular character.
 * @author Aaron Michael McNulty
 */
public class AbilitySlot extends Button {
  
  private final String type;
  private final int level;
  private final Consumer<AbilitySlotClickEvent> clickHandler;
  
  public AbilitySlot(int x, int y, String type, Consumer<AbilitySlotClickEvent> clickHandler) {
    super("", x, y, 19, 19, () -> {});
    this.type = type;
    if (!this.type.equals(BattleMove.PHYSICAL) && !this.type.equals(BattleMove.MAGICAL) && !this.type.equals(BattleMove.BUFF) && !this.type.equals(BattleMove.ITEM))
      throw new IllegalArgumentException("Improper 'type' supplied. Ability slots must have a 'type' property of 'Physical' | 'Magical' | 'Buff' | 'Item'");
    this.level = 0;
    this.clickHandler = clickHandler;
    createButtonSprites();
  }
  
  public AbilitySlot(int x, int y, String type, int level, Consumer<AbilitySlotClickEvent> clickHandler) {
    super("", x, y, 19, 19, () -> {});
    this.type = type;
    if (!this.type.equals(BattleMove.PHYSICAL) && !this.type.equals(BattleMove.MAGICAL) && !this.type.equals(BattleMove.BUFF) && !this.type.equals(BattleMove.ITEM))
      throw new IllegalArgumentException("Improper 'type' supplied. Ability slots must have a 'type' property of 'Physical' | 'Magical' | 'Buff' | 'Item'");
    this.level = level;
    this.clickHandler = clickHandler;
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
  protected void click() {
    super.click();
    clickHandler.accept(new AbilitySlotClickEvent(level, type, this));
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
