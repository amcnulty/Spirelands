package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleEntity {
  
  protected int x, y, anim;
  private final SpriteSheet spriteSheet;
  private final SpawnCoordinate slot;
  protected Sprite currentAction;
  private final int renderSize = 32;
  private final HashMap<String, Sprite> actionMap = new HashMap<>();
  private final ICallback
    idleAnimation = () -> {
      currentAction = actionMap.get("IDLE_" + (anim % 36) / 12);
    },
    readyPhysicalAnimation = () -> {
      currentAction = actionMap.get("READY_PHYSICAL_" + (anim % 36) / 12);
    },
    readyMagicalAnimation = () -> {
      currentAction = actionMap.get("READY_MAGICAL_" + (anim % 36) / 12);
    },
    guardAnimation = () -> {
      currentAction = actionMap.get("GUARD_" + (anim % 36) / 12);
    },
    damageAnimation = () -> {
      if (anim == 36) playLastRepeatingAnimation();
      else currentAction = actionMap.get("DAMAGE_" + anim  / 12);
    },
    evadeAnimation = () -> {
      if (anim == 0) x = x + 5;
      if (anim == 36) {
        x = x - 5;
        playLastRepeatingAnimation();
      }
      else currentAction = actionMap.get("EVADE_" + anim / 12);
    },
    stabbingAnimation = () -> {
      if (anim == 36) playLastRepeatingAnimation();
      else currentAction = actionMap.get("STABBING_" + anim / 12);
    },
    swingingAnimation = () -> {
      if (anim == 36) playLastRepeatingAnimation();
      else currentAction = actionMap.get("SWINGING_" + anim / 12);
    },
    shootingAnimation = () -> {
      if (anim == 1) x = x - 5;
      if (anim == 36) {
        x = x + 5;
        playLastRepeatingAnimation();
      }
      else currentAction = actionMap.get("SHOOTING_" + anim / 12);
    },
    usePhysicalSkillAnimation = () -> {
      if (anim == 36) playLastRepeatingAnimation();
      else currentAction = actionMap.get("USE_PHYSICAL_SKILL_" + anim / 12);
    },
    useMagicalSkillAnimation = () -> {
      if (anim == 72) playLastRepeatingAnimation();
      else currentAction = actionMap.get("USE_MAGICAL_SKILL_" + anim / 24);
    },
    useItemAnimation = () -> {
      if (anim == 36) playLastRepeatingAnimation();
      else currentAction = actionMap.get("USE_ITEM_" + anim / 12);
    },
    escapeAnimation = () -> {
      currentAction = actionMap.get("ESCAPE_" + (anim % 36) / 12);
    },
    victoryAnimation = () -> {
      currentAction = actionMap.get("VICTORY_" + (anim % 36) / 12);
    },
    lowHealthAnimation = () -> {
      currentAction = actionMap.get("LOW_HEALTH_" + (anim % 36) / 12);
    },
    statusAnimation = () -> {
      currentAction = actionMap.get("STATUS_" + (anim % 36) / 12);
    },
    sleepingAnimation = () -> {
      currentAction = actionMap.get("SLEEPING_" + (anim % 36) / 12);
    },
    deadAnimation = () -> {
      currentAction = actionMap.get("DEAD_" + (anim % 36) / 12);
    };

  private ICallback currentAnimation = idleAnimation,
                    repeatingAnimation = idleAnimation;
  
  public BattleEntity(SpawnCoordinate slot, SpriteSheet spriteSheet) {
    this.slot = slot;
    this.spriteSheet = spriteSheet;
    this.x = slot.getX();
    this.y = slot.getY();
    setupActionMap();
    currentAction = actionMap.get("IDLE_0");
  }
  
  private void setupActionMap() {
    actionMap.put("IDLE_0", new Sprite(64, renderSize, 2, 0, spriteSheet));
    actionMap.put("IDLE_1", new Sprite(64, renderSize, 1, 0, spriteSheet));
    actionMap.put("IDLE_2", new Sprite(64, renderSize, 0, 0, spriteSheet));
    
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
  
  private void playLastRepeatingAnimation() {
    currentAnimation = repeatingAnimation;
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
  
  public void playIdleAnimation() {
    currentAnimation = repeatingAnimation = idleAnimation;
  }
  
  public void playReadyPhysicalAnimation() {
    currentAnimation = repeatingAnimation = readyPhysicalAnimation;
  }
  
  public void playReadyMagicalAnimation() {
    currentAnimation = repeatingAnimation = readyMagicalAnimation;
  }
  
  public void playGuardAnimation() {
    currentAnimation = repeatingAnimation = guardAnimation;
  }
  
  public void playDamageAnimation() {
    anim = 0;
    currentAnimation = damageAnimation;
  }
  
  public void playEvadeAnimation() {
    anim = 0;
    currentAnimation = evadeAnimation;
  }
  
  public void playStabbingAnimation() {
    anim = 0;
    currentAnimation = stabbingAnimation;
  }
  
  public void playSwingingAnimation() {
    anim = 0;
    currentAnimation = swingingAnimation;
  }
  
  public void playShootingAnimation() {
    anim = 0;
    currentAnimation = shootingAnimation;
  }
  
  public void playUsePhysicalSkillAnimation() {
    anim = 0;
    currentAnimation = usePhysicalSkillAnimation;
  }
  
  public void playUseMagicalSkillAnimation() {
    anim = 0;
    currentAnimation = useMagicalSkillAnimation;
  }
  
  public void playUseItemAnimation() {
    anim = 0;
    currentAnimation = useItemAnimation;
  }
  
  public void playEscapeAnimation() {
    anim = 0;
    currentAnimation = escapeAnimation;
  }
  
  public void playVictoryAnimation() {
    currentAnimation = repeatingAnimation = victoryAnimation;
  }
  
  public void playLowHealthAnimation() {
    currentAnimation = repeatingAnimation = lowHealthAnimation;
  }
  
  public void playStatusAnimation() {
    currentAnimation = repeatingAnimation = statusAnimation;
  }
  
  public void playSleepingAnimation() {
    currentAnimation = repeatingAnimation = sleepingAnimation;
  }
  
  public void playDeadAnimation() {
    currentAnimation = repeatingAnimation = deadAnimation;
  }
  
  public void update() {
    currentAnimation.execute();
    anim++;
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, currentAction, false);
  }
  
}
