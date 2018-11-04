package com.monkeystomp.spirelands.level.entity.fixed;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.particle.ProjectileParticle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Chest extends Entity {
  
  private final int SPRITE_SIZE = 32,
                    PARTICLE_AMOUNT = 60;
  private final SoundEffects sfx = new SoundEffects();
  private Sprite  chestOpenSprite,
                  chestClosedSprite,
                  currentSprite;
  private final Item treasure;
  private boolean isChestOpen = false,
                  showingItem = false;
  private Random random = new Random();
  private int anim = 0,
              animY = 0,
              gold = 0;
  private ArrayList<ProjectileParticle> particles = new ArrayList<>();
  
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
    if (treasure != null) {
      showingItem = true;
      // show treasure modal
      treasure.addToInventory();
    }
    if (gold > 0) {
      generateParticles();
      InventoryManager.getInventoryManager().addGold(gold);
    }
    sfx.playSoundEffect(SoundEffects.CHEST_OPENING);
    isChestOpen = true;
    setCurrentSprite();
  }
  
  private void generateParticles() {
    for (int i = 0; i < PARTICLE_AMOUNT; i++) {
      particles.add(new ProjectileParticle(getRandomX(x - 12, 24), getRandomY(y - 2, 13), Sprite.GOLD, getRandomForce(10, 3), getRandomAngle()));
      particles.get(i).setGroundLevel(y - 10 + random.nextInt(20));
    }
  }
  
  private int getRandomX(int start, int extra) {
    return start + random.nextInt(extra);
  }
  
  private int getRandomY(int start, int extra) {
    return start + random.nextInt(extra);
  }
  
  private int getRandomForce(int start, int extra) {
    return start + random.nextInt(extra);
  }

  private int getRandomAngle() {
    if (random.nextBoolean()) {
      return 65 + random.nextInt(10);
    }
    else {
      return 95 + random.nextInt(10);
    }
  }
  
  private int getRandomAngleInRange(int low, int high) {
    return low + random.nextInt(high - low);
  }
  
  public void addGold(int gold) {
    this.gold = gold;
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
    for (int i = 0; i < particles.size(); i++) {
      if (!particles.get(i).isRemoved()) particles.get(i).update();
      else particles.remove(i);
    }
    if (showingItem && anim < 20) {
      anim++;
      animY++;
    }
    else if (showingItem && anim > 19) {
      if (anim == 20) {
        for (int i = 0; i < PARTICLE_AMOUNT; i++) {
          particles.add(new ProjectileParticle(x, y - 20, Sprite.DUST, getRandomForce(4, 3), getRandomAngleInRange(0, 360)));
          particles.get(i).setGravity(false);
        }
      }
      else if (anim == 70) showingItem = false;
      anim++;
    }
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, currentSprite, true);
    for (int i = 0; i < particles.size(); i++) {
      particles.get(i).render(screen, gl);
    }
    if (showingItem) screen.renderSprite(gl, x - treasure.getThumbnail().getWidth() / 2, y - animY - treasure.getThumbnail().getHeight() / 2, treasure.getThumbnail(), true);
  }
}