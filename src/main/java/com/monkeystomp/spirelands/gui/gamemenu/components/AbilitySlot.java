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
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Ability slots are shown in the game menu under the Abilities section and represent what battle moves are currently equipped for a particular character.
 * @author Aaron Michael McNulty
 */
public class AbilitySlot extends GroupButton {
  
  private final String type;
  private int level;
  private Consumer<AbilitySlotClickEvent> clickHandler;
  private BattleMove equippedMove;
  private final ArrayList<BattleMove> equippedItemMoves = new ArrayList<>();
  private Sprite itemIcon;
  private static final HashMap<String, Sprite> BATTLE_MOVE_SPRITE_MAP = new HashMap<>();
  
  static {
    BATTLE_MOVE_SPRITE_MAP.put("2_" + BattleMove.PHYSICAL, Sprite.BATTLE_SLOT_PHYSICAL_LV2);
    BATTLE_MOVE_SPRITE_MAP.put("3_" + BattleMove.PHYSICAL, Sprite.BATTLE_SLOT_PHYSICAL_LV3);
    BATTLE_MOVE_SPRITE_MAP.put("2_"+ BattleMove.MAGICAL, Sprite.BATTLE_SLOT_MAGICAL_LV2);
    BATTLE_MOVE_SPRITE_MAP.put("3_" + BattleMove.MAGICAL, Sprite.BATTLE_SLOT_MAGICAL_LV3);
    BATTLE_MOVE_SPRITE_MAP.put("2_" + BattleMove.BUFF, Sprite.BATTLE_SLOT_BUFF_LV2);
    BATTLE_MOVE_SPRITE_MAP.put("3_" + BattleMove.BUFF, Sprite.BATTLE_SLOT_BUFF_LV3);
    BATTLE_MOVE_SPRITE_MAP.put("2_" + BattleMove.ITEM, Sprite.BATTLE_SLOT_ITEM_LV2);
    BATTLE_MOVE_SPRITE_MAP.put("3_" + BattleMove.ITEM, Sprite.BATTLE_SLOT_ITEM_LV3);
    // Hover and down sprites
    BATTLE_MOVE_SPRITE_MAP.put("2_hover", Sprite.BATTLE_SLOT_HOVER_LV2);
    BATTLE_MOVE_SPRITE_MAP.put("3_hover", Sprite.BATTLE_SLOT_HOVER_LV3);
    BATTLE_MOVE_SPRITE_MAP.put("2_down", Sprite.BATTLE_SLOT_DOWN_LV2);
    BATTLE_MOVE_SPRITE_MAP.put("3_down", Sprite.BATTLE_SLOT_DOWN_LV3);
  }
  
  public AbilitySlot(String type, int level) {
    super("", 0, 0, 23, 23, () -> {});
    this.type = type;
    if (!this.type.equals(BattleMove.PHYSICAL) && !this.type.equals(BattleMove.MAGICAL) && !this.type.equals(BattleMove.BUFF) && !this.type.equals(BattleMove.ITEM))
      throw new IllegalArgumentException("Improper 'type' supplied. Ability slots must have a 'type' property of 'Physical' | 'Magical' | 'Buff' | 'Item'");
    if (this.type.equals(BattleMove.ITEM))
      itemIcon = new Sprite(16, 16, 0, 11, SpriteSheet.itemsSheet);
    this.level = level;
    createButtonSprites();
  }
  
  private void createButtonSprites() {
    if (level < 2) {
      button = createDefaultSprite();
      buttonHover = createHoverSprite();
      buttonDown = createDownSprite();
    }
    else {
      button = BATTLE_MOVE_SPRITE_MAP.get(level + "_" + type);
      buttonHover = BATTLE_MOVE_SPRITE_MAP.get(level + "_hover");
      buttonDown = BATTLE_MOVE_SPRITE_MAP.get(level + "_down");
    }
    currentButton = button;
    setButtonActive(buttonHover);
  }
  
  private Sprite createDefaultSprite() {
    int[] pixels = new int[width * height];
    for (int yy = 0; yy < height; yy++) {
      for (int xx = 0; xx < width; xx++) {
        if (xx == 0 || xx == 1 || xx == width - 2 || xx == width - 1 ||
                yy == 0 || yy == 1 || yy == height - 2 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.TRANSPARENT;
        }
        else if (yy == 2 || yy == height - 3) {
          pixels[xx + yy * width] = getColorForType(type);
        }
        else if (xx == 2 || xx == width - 3) {
          pixels[xx + yy * width] = getColorForType(type);
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
        if (xx == 0 || xx == 1 || xx == width - 2 || xx == width - 1 ||
                yy == 0 || yy == 1 || yy == height - 2 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.TRANSPARENT;
        }
        else if (yy == 2 || yy == height - 3) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_DEFAULT_TEXT;
        }
        else if (xx == 2 || xx == width - 3) {
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
        if (xx == 0 || xx == 1 || xx == width - 2 || xx == width - 1 ||
                yy == 0 || yy == 1 || yy == height - 2 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.TRANSPARENT;
        }
        else if (yy == 2 || yy == height - 3) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_LABEL_TEXT;
        }
        else if (xx == 2 || xx == width - 3) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_LABEL_TEXT;
        }
        else pixels[xx + yy * width] = GameColors.EQUIPPED_ITEM_SLOT_DOWN;
      }
    }
    return new Sprite(pixels, width, height);
  }
  
  private int getColorForType(String type) {
    if (type.equals(BattleMove.PHYSICAL))
      return GameColors.BATTLE_MOVE_SLOT_PHYSICAL;
    else if (type.equals(BattleMove.MAGICAL))
      return GameColors.BATTLE_MOVE_SLOT_MAGICAL;
    else if (type.equals(BattleMove.BUFF))
      return GameColors.BATTLE_MOVE_SLOT_BUFF;
    else
      return GameColors.BATTLE_MOVE_SLOT_ITEM;
  }

  public String getType() {
    return type;
  }

  public BattleMove getEquippedMove() {
    return equippedMove;
  }

  public ArrayList<BattleMove> getEquippedItemMoves() {
    return equippedItemMoves;
  }
  
  public void setEquippedItemMoves(ArrayList<BattleMove> moves) {
    equippedItemMoves.clear();
    equippedItemMoves.addAll(moves);
  }
  
  public void setMove(BattleMove move) {
    equippedMove = move;
  }
  
  public void unequip() {
    equippedMove = null;
  }
  
  public void addItemMove(BattleMove move) {
    equippedItemMoves.add(move);
  }
  /**
   * Upgrades this ability slot by one level. This will fire the click handler to reload the list view and upgrade panel component.
   */
  public void upgradeSlot() {
    level++;
    createButtonSprites();
    currentButton = buttonHover;
    clickHandler.accept(new AbilitySlotClickEvent(level, type, this));
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
      screen.renderSprite(gl, x + 7, y + 8, Sprite.LOCK, false);
    else if (equippedMove != null)
      screen.renderSprite(gl, x + 3, y + 3, equippedMove.getThumbnail(), false);
    else if (type.equals(BattleMove.ITEM))
      screen.renderSprite(gl, x + 3, y + 3, itemIcon, false);
      
  }

}
