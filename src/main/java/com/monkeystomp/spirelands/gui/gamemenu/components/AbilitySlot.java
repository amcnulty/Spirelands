package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.GroupButton;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Ability slots are shown in the game menu under the Abilities section and represent what battle moves are currently equipped for a particular character.
 * @author Aaron Michael McNulty
 */
public class AbilitySlot extends GroupButton {
  
  private final String type;
  private final int level;
  private Consumer<AbilitySlotClickEvent> clickHandler;
  private BattleMove equippedMove;
  private final ArrayList<BattleMove> equippedItemMoves = new ArrayList<>();
  private Sprite itemIcon;
  
  public AbilitySlot(String type, int level) {
    super("", 0, 0, 19, 19, () -> {});
    this.type = type;
    if (!this.type.equals(BattleMove.PHYSICAL) && !this.type.equals(BattleMove.MAGICAL) && !this.type.equals(BattleMove.BUFF) && !this.type.equals(BattleMove.ITEM))
      throw new IllegalArgumentException("Improper 'type' supplied. Ability slots must have a 'type' property of 'Physical' | 'Magical' | 'Buff' | 'Item'");
    if (this.type.equals(BattleMove.ITEM))
      itemIcon = new Sprite(16, 16, 0, 11, SpriteSheet.itemsSheet);
    this.level = level;
    createButtonSprites();
  }
  
  private void createButtonSprites() {
    button = createDefaultSprite();
    buttonHover = createHoverSprite();
    buttonDown = createDownSprite();
    currentButton = button;
    setButtonActive(buttonHover);
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
  
  public void addMove(BattleMove move) {
    equippedMove = move;
  }
  
  public void addItemMove(BattleMove move) {
    equippedItemMoves.add(move);
  }

  public void setClickHandler(Consumer<AbilitySlotClickEvent> clickHandler) {
    this.clickHandler = clickHandler;
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
    if (level == 0)
      screen.renderSprite(gl, x + 5, y + 6, Sprite.LOCK, false);
    else if (equippedMove != null)
      screen.renderSprite(gl, x + 1, y + 1, equippedMove.getThumbnail(), false);
    else if (type.equals(BattleMove.ITEM))
      screen.renderSprite(gl, x + 1, y + 1, itemIcon, false);
      
  }

}
