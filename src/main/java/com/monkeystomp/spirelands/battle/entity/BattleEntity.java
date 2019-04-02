package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleEntity {
  
  private int x, y, anim;
  private final SpriteSheet spriteSheet;
  private final SpawnCoordinate slot;
  private Sprite currentAction;
  private final int renderSize = 32;
  private final HashMap<String, Sprite> actionMap = new HashMap<>();
  
  public BattleEntity(SpawnCoordinate slot, SpriteSheet spriteSheet) {
    this.slot = slot;
    this.x = slot.getX();
    this.y = slot.getY();
    this.spriteSheet = spriteSheet;
    setupActionMap();
    currentAction = actionMap.get("IDLE_0");
  }
  
  private void setupActionMap() {
    actionMap.put("IDLE_0", new Sprite(64, renderSize, 0, 0, spriteSheet));
    actionMap.put("IDLE_1", new Sprite(64, renderSize, 1, 0, spriteSheet));
    actionMap.put("IDLE_2", new Sprite(64, renderSize, 2, 0, spriteSheet));
    
    actionMap.put("READY_PHYSICAL_0", new Sprite(64, renderSize, 0, 1, spriteSheet));
    actionMap.put("READY_PHYSICAL_1", new Sprite(64, renderSize, 1, 1, spriteSheet));
    actionMap.put("READY_PHYSICAL_2", new Sprite(64, renderSize, 2, 1, spriteSheet));
    
    actionMap.put("READY_MAGICAL_0", new Sprite(64, renderSize, 0, 2, spriteSheet));
    actionMap.put("READY_MAGICAL_1", new Sprite(64, renderSize, 1, 2, spriteSheet));
    actionMap.put("READY_MAGICAL_2", new Sprite(64, renderSize, 2, 2, spriteSheet));
    
    actionMap.put("GUARD_0", new Sprite(64, renderSize, 0, 3, spriteSheet));
    actionMap.put("GUARD_1", new Sprite(64, renderSize, 1, 3, spriteSheet));
    actionMap.put("GUARD_2", new Sprite(64, renderSize, 2, 3, spriteSheet));
    
    actionMap.put("DAMAGE_0", new Sprite(64, renderSize, 0, 4, spriteSheet));
    actionMap.put("DAMAGE_1", new Sprite(64, renderSize, 1, 4, spriteSheet));
    actionMap.put("DAMAGE_2", new Sprite(64, renderSize, 2, 4, spriteSheet));
    
    actionMap.put("EVADE_0", new Sprite(64, renderSize, 0, 5, spriteSheet));
    actionMap.put("EVADE_1", new Sprite(64, renderSize, 1, 5, spriteSheet));
    actionMap.put("EVADE_2", new Sprite(64, renderSize, 2, 5, spriteSheet));
    
    actionMap.put("STABBING_0", new Sprite(64, renderSize, 3, 0, spriteSheet));
    actionMap.put("STABBING_1", new Sprite(64, renderSize, 4, 0, spriteSheet));
    actionMap.put("STABBING_2", new Sprite(64, renderSize, 5, 0, spriteSheet));
    
    actionMap.put("SWINGING_0", new Sprite(64, renderSize, 3, 1, spriteSheet));
    actionMap.put("SWINGING_1", new Sprite(64, renderSize, 4, 1, spriteSheet));
    actionMap.put("SWINGING_2", new Sprite(64, renderSize, 5, 1, spriteSheet));
    
    actionMap.put("SHOOTING_0", new Sprite(64, renderSize, 3, 2, spriteSheet));
    actionMap.put("SHOOTING_1", new Sprite(64, renderSize, 4, 2, spriteSheet));
    actionMap.put("SHOOTING_2", new Sprite(64, renderSize, 5, 2, spriteSheet));
    
    actionMap.put("USE_PHYSICAL_SKILL_0", new Sprite(64, renderSize, 3, 3, spriteSheet));
    actionMap.put("USE_PHYSICAL_SKILL_1", new Sprite(64, renderSize, 4, 3, spriteSheet));
    actionMap.put("USE_PHYSICAL_SKILL_2", new Sprite(64, renderSize, 5, 3, spriteSheet));
    
    actionMap.put("USE_MAGICAL_SKILL_0", new Sprite(64, renderSize, 3, 4, spriteSheet));
    actionMap.put("USE_MAGICAL_SKILL_1", new Sprite(64, renderSize, 4, 4, spriteSheet));
    actionMap.put("USE_MAGICAL_SKILL_2", new Sprite(64, renderSize, 5, 4, spriteSheet));
    
    actionMap.put("USE_ITEM_0", new Sprite(64, renderSize, 3, 5, spriteSheet));
    actionMap.put("USE_ITEM_1", new Sprite(64, renderSize, 4, 5, spriteSheet));
    actionMap.put("USE_ITEM_2", new Sprite(64, renderSize, 5, 5, spriteSheet));
    
    actionMap.put("ESCAPE_0", new Sprite(64, renderSize, 6, 0, spriteSheet));
    actionMap.put("ESCAPE_1", new Sprite(64, renderSize, 7, 0, spriteSheet));
    actionMap.put("ESCAPE_2", new Sprite(64, renderSize, 8, 0, spriteSheet));
    
    actionMap.put("VICTORY_0", new Sprite(64, renderSize, 6, 1, spriteSheet));
    actionMap.put("VICTORY_1", new Sprite(64, renderSize, 7, 1, spriteSheet));
    actionMap.put("VICTORY_2", new Sprite(64, renderSize, 8, 1, spriteSheet));
    
    actionMap.put("LOW_HEALTH_0", new Sprite(64, renderSize, 6, 2, spriteSheet));
    actionMap.put("LOW_HEALTH_1", new Sprite(64, renderSize, 7, 2, spriteSheet));
    actionMap.put("LOW_HEALTH_2", new Sprite(64, renderSize, 8, 2, spriteSheet));
    
    actionMap.put("STATUS_0", new Sprite(64, renderSize, 6, 3, spriteSheet));
    actionMap.put("STATUS_1", new Sprite(64, renderSize, 7, 3, spriteSheet));
    actionMap.put("STATUS_2", new Sprite(64, renderSize, 8, 3, spriteSheet));
    
    actionMap.put("SLEEPING_0", new Sprite(64, renderSize, 6, 4, spriteSheet));
    actionMap.put("SLEEPING_1", new Sprite(64, renderSize, 7, 4, spriteSheet));
    actionMap.put("SLEEPING_2", new Sprite(64, renderSize, 8, 4, spriteSheet));
    
    actionMap.put("DEAD_0", new Sprite(64, renderSize, 6, 5, spriteSheet));
    actionMap.put("DEAD_1", new Sprite(64, renderSize, 7, 5, spriteSheet));
    actionMap.put("DEAD_2", new Sprite(64, renderSize, 8, 5, spriteSheet));
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
  
  private void playIdleAnimation() {
    currentAction = actionMap.get("IDLE_" + (anim % 36) / 12);
  }
  
  private void playReadyPhysicalAnimation() {
    currentAction = actionMap.get("READY_PHYSICAL_" + (anim % 36) / 12);
  }
  
  private void playReadyMagicalAnimation() {
    currentAction = actionMap.get("READY_MAGICAL_" + (anim % 36) / 12);
  }
  
  private void playGuardAnimation() {
    currentAction = actionMap.get("GUARD_" + (anim % 36) / 12);
  }
  
  private void playDamageAnimation() {
    currentAction = actionMap.get("DAMAGE_" + (anim % 36) / 12);
  }
  
  private void playEvadeAnimation() {
    currentAction = actionMap.get("EVADE_" + (anim % 36) / 12);
  }
  
  private void playStabbingAnimation() {
    currentAction = actionMap.get("STABBING_" + (anim % 36) / 12);
  }
  
  private void playSwingingAnimation() {
    currentAction = actionMap.get("SWINGING_" + (anim % 36) / 12);
  }
  
  private void playShootingAnimation() {
    currentAction = actionMap.get("SHOOTING_" + (anim % 36) / 12);
  }
  
  private void playUsePhysicalSkillAnimation() {
    currentAction = actionMap.get("USE_PHYSICAL_SKILL_" + (anim % 36) / 12);
  }
  
  private void playUseMagicalSkillAnimation() {
    currentAction = actionMap.get("USE_MAGICAL_SKILL_" + (anim % 36) / 12);
  }
  
  private void playUseItemAnimation() {
    currentAction = actionMap.get("USE_ITEM_" + (anim % 36) / 12);
  }
  
  private void playEscapeAnimation() {
    currentAction = actionMap.get("ESCAPE_" + (anim % 36) / 12);
  }
  
  private void playVictoryAnimation() {
    currentAction = actionMap.get("VICTORY_" + (anim % 36) / 12);
  }
  
  private void playLowHealthAnimation() {
    currentAction = actionMap.get("LOW_HEALTH_" + (anim % 36) / 12);
  }
  
  private void playStatusAnimation() {
    currentAction = actionMap.get("STATUS_" + (anim % 36) / 12);
  }
  
  private void playSleepingAnimation() {
    currentAction = actionMap.get("SLEEPING_" + (anim % 36) / 12);
  }
  
  private void playDeadAnimation() {
    currentAction = actionMap.get("DEAD_" + (anim % 36) / 12);
  }
  
  public void update() {
    anim++;
    if (anim < 300) {
      playIdleAnimation();
      System.out.println("idle");
    }
    else if (anim < 600) {
      playReadyPhysicalAnimation();
      System.out.println("ready physical");
    }
    else if (anim < 900) {
      playReadyMagicalAnimation();
      System.out.println("ready magical");
    }
    else if (anim < 1200) {
      playGuardAnimation();
      System.out.println("guard");
    }
    else if (anim < 1500) {
      playDamageAnimation();
      System.out.println("damage");
    }
    else if (anim < 1800) {
      playEvadeAnimation();
      System.out.println("evade");
    }
    else if (anim < 2100) {
      playStabbingAnimation();
      System.out.println("stabbing");
    }
    else if (anim < 2400) {
      playSwingingAnimation();
      System.out.println("swinging");
    }
    else if (anim < 2700) {
      playShootingAnimation();
      System.out.println("shooting");
    }
    else if (anim < 3000) {
      playUsePhysicalSkillAnimation();
      System.out.println("use physical skill");
    }
    else if (anim < 3300) {
      playUseMagicalSkillAnimation();
      System.out.println("use magical skill");
    }
    else if (anim < 3600) {
      playUseItemAnimation();
      System.out.println("use item");
    }
    else if (anim < 3900) {
      playEscapeAnimation();
      System.out.println("escape");
    }
    else if (anim < 4200) {
      playVictoryAnimation();
      System.out.println("victory");
    }
    else if (anim < 4500) {
      playLowHealthAnimation();
      System.out.println("low health");
    }
    else if (anim < 4800) {
      playStatusAnimation();
      System.out.println("status");
    }
    else if (anim < 5100) {
      playSleepingAnimation();
      System.out.println("sleeping");
    }
    else if (anim < 5400) {
      playDeadAnimation();
      System.out.println("dead");
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, currentAction, false);
  }
  
}
