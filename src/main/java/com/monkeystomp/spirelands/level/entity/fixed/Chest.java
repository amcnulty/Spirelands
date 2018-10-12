package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Chest extends Entity {
  
  private final int SPRITE_SIZE = 32;
  private final SoundEffects sfx = new SoundEffects();
  private Sprite  chestOpenSprite,
                  chestClosedSprite,
                  currentSprite;
  private final Item treasure;
  private boolean isChestOpen = false;
  
  public static final int WOODEN_CHEST = 0,
                          COMMON_METAL_CHEST = 1,
                          SPECIAL_METAL_CHEST = 2;
  
  public Chest(int x, int y, int chestType, Item treasure) {
    this.x = x;
    this.y = y;
    this.treasure = treasure;
    setSprites(chestType);
    setCurrentSprite();
    setBounds();
  }
  
  private void setSprites(int chestType) {
    chestOpenSprite = new Sprite(SPRITE_SIZE, 0, chestType, SpriteSheet.chestSheet);
    chestClosedSprite = new Sprite(SPRITE_SIZE, 1, chestType, SpriteSheet.chestSheet);
  }
  
  private void setBounds() {
    bounds[0] = y - SPRITE_SIZE / 2;
    bounds[1] = x + SPRITE_SIZE / 2;
    bounds[2] = y + SPRITE_SIZE / 2;
    bounds[3] = x - SPRITE_SIZE / 2;
  }
  
  private void setCurrentSprite() {
    if (isChestOpen) currentSprite = chestOpenSprite;
    else currentSprite = chestClosedSprite;
  }
  
  private void openChest() {
    sfx.playSoundEffect(SoundEffects.CHEST_OPENING);
    isChestOpen = true;
    setCurrentSprite();
    // show treasure modal
    treasure.addToInventory();
  }

  public boolean isIsChestOpen() {
    return isChestOpen;
  }

  public void setIsChestOpen(boolean isChestOpen) {
    this.isChestOpen = isChestOpen;
  }
  
  @Override
  public void interact() {
    if (!isChestOpen) openChest();
  }
  
  @Override
  public void update() {
    
  }

  @Override
  public void render(Screen screen) {
    screen.renderSprite(x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, currentSprite, true);
  }
}