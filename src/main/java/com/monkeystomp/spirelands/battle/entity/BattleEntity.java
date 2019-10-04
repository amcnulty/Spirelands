package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.Battle;
import com.monkeystomp.spirelands.battle.elemental.Elemental;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.battle.move.Buff;
import com.monkeystomp.spirelands.character.StatModel;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.util.Helpers;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleEntity {
  
  protected int x, y, anim, readyGaugeMax;
  protected int readyGauge = 0;
  private final SpriteSheet spriteSheet;
  private final SpawnCoordinate slot;
  protected Battle battle;
  protected Sprite currentAction;
  private final int renderSize = 32;
  private boolean ready = false,
                  isDead = false;
  /**
   * An entity is moving if it has been assigned make move. This is used to prevent multiple calls to make move.
   */
  protected boolean moving = false;
  protected boolean finishedAttacking = true;
  protected boolean traveling = false;
  protected boolean guarding = false;
  protected BattleEntity currentTarget;
  protected BattleMove currentMove;
  protected Method moveAnimation;
  protected StatModel statModel;
  private Buff buff = new Buff();
  private final Sprite buffBarEmpty = new Sprite(50, 1, GameColors.MANA_BAR_EMPTY);
  private Sprite buffBarFilled;
  private final ArrayList<int[]> travelingSteps = new ArrayList<>();
  protected final HashMap<String, Sprite> actionMap = new HashMap<>();
  protected final ICallback
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
      if (anim == 36) moveFinished(false);
      else currentAction = actionMap.get("DAMAGE_" + anim  / 12);
    },
    evadeAnimation = () -> {
      if (anim == 0) x = x + 5;
      if (anim == 36) {
        x = x - 5;
        moveFinished(false);
      }
      else currentAction = actionMap.get("EVADE_" + anim / 12);
    },
    stabbingAnimation = () -> {
      if (anim == 36) moveFinished(true);
      else currentAction = actionMap.get("STABBING_" + anim / 12);
    },
    swingingAnimation = () -> {
      if (anim == 36) moveFinished(true);
      else currentAction = actionMap.get("SWINGING_" + anim / 12);
    },
    shootingAnimation = () -> {
      if (anim == 1) x = x - 5;
      if (anim == 36) {
        x = x + 5;
        moveFinished(true);
      }
      else currentAction = actionMap.get("SHOOTING_" + anim / 12);
    },
    usePhysicalSkillAnimation = () -> {
      if (anim == 36) moveFinished(true);
      else currentAction = actionMap.get("USE_PHYSICAL_SKILL_" + anim / 12);
    },
    useMagicalSkillAnimation = () -> {
      if (anim == 108) moveFinished(true);
      else currentAction = actionMap.get("USE_MAGICAL_SKILL_" + anim / 36);
    },
    useItemAnimation = () -> {
      if (anim == 36) moveFinished(true);
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

  protected ICallback currentAnimation = idleAnimation,
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
    int rawSize = spriteSheet.getWidth() / 9;
    actionMap.put("IDLE_0", new Sprite(rawSize, renderSize, 2, 0, spriteSheet));
    actionMap.put("IDLE_1", new Sprite(rawSize, renderSize, 1, 0, spriteSheet));
    actionMap.put("IDLE_2", new Sprite(rawSize, renderSize, 0, 0, spriteSheet));
    
    actionMap.put("READY_PHYSICAL_0", new Sprite(rawSize, renderSize, 0, 1, spriteSheet));
    actionMap.put("READY_PHYSICAL_1", new Sprite(rawSize, renderSize, 1, 1, spriteSheet));
    actionMap.put("READY_PHYSICAL_2", new Sprite(rawSize, renderSize, 2, 1, spriteSheet));
    
    actionMap.put("READY_MAGICAL_0", new Sprite(rawSize, renderSize, 0, 2, spriteSheet));
    actionMap.put("READY_MAGICAL_1", new Sprite(rawSize, renderSize, 1, 2, spriteSheet));
    actionMap.put("READY_MAGICAL_2", new Sprite(rawSize, renderSize, 2, 2, spriteSheet));
    
    actionMap.put("GUARD_0", new Sprite(rawSize, renderSize, 0, 3, spriteSheet));
    actionMap.put("GUARD_1", new Sprite(rawSize, renderSize, 1, 3, spriteSheet));
    actionMap.put("GUARD_2", new Sprite(rawSize, renderSize, 2, 3, spriteSheet));
    
    actionMap.put("DAMAGE_0", new Sprite(rawSize, renderSize, 0, 4, spriteSheet));
    actionMap.put("DAMAGE_1", new Sprite(rawSize, renderSize, 1, 4, spriteSheet));
    actionMap.put("DAMAGE_2", new Sprite(rawSize, renderSize, 2, 4, spriteSheet));
    
    actionMap.put("EVADE_0", new Sprite(rawSize, renderSize, 0, 5, spriteSheet));
    actionMap.put("EVADE_1", new Sprite(rawSize, renderSize, 1, 5, spriteSheet));
    actionMap.put("EVADE_2", new Sprite(rawSize, renderSize, 2, 5, spriteSheet));
    
    actionMap.put("STABBING_0", new Sprite(rawSize, renderSize, 3, 0, spriteSheet));
    actionMap.put("STABBING_1", new Sprite(rawSize, renderSize, 4, 0, spriteSheet));
    actionMap.put("STABBING_2", new Sprite(rawSize, renderSize, 5, 0, spriteSheet));
    
    actionMap.put("SWINGING_0", new Sprite(rawSize, renderSize, 3, 1, spriteSheet));
    actionMap.put("SWINGING_1", new Sprite(rawSize, renderSize, 4, 1, spriteSheet));
    actionMap.put("SWINGING_2", new Sprite(rawSize, renderSize, 5, 1, spriteSheet));
    
    actionMap.put("SHOOTING_0", new Sprite(rawSize, renderSize, 3, 2, spriteSheet));
    actionMap.put("SHOOTING_1", new Sprite(rawSize, renderSize, 4, 2, spriteSheet));
    actionMap.put("SHOOTING_2", new Sprite(rawSize, renderSize, 5, 2, spriteSheet));
    
    actionMap.put("USE_PHYSICAL_SKILL_0", new Sprite(rawSize, renderSize, 3, 3, spriteSheet));
    actionMap.put("USE_PHYSICAL_SKILL_1", new Sprite(rawSize, renderSize, 4, 3, spriteSheet));
    actionMap.put("USE_PHYSICAL_SKILL_2", new Sprite(rawSize, renderSize, 5, 3, spriteSheet));
    
    actionMap.put("USE_MAGICAL_SKILL_0", new Sprite(rawSize, renderSize, 3, 4, spriteSheet));
    actionMap.put("USE_MAGICAL_SKILL_1", new Sprite(rawSize, renderSize, 4, 4, spriteSheet));
    actionMap.put("USE_MAGICAL_SKILL_2", new Sprite(rawSize, renderSize, 5, 4, spriteSheet));
    
    actionMap.put("USE_ITEM_0", new Sprite(rawSize, renderSize, 3, 5, spriteSheet));
    actionMap.put("USE_ITEM_1", new Sprite(rawSize, renderSize, 4, 5, spriteSheet));
    actionMap.put("USE_ITEM_2", new Sprite(rawSize, renderSize, 5, 5, spriteSheet));
    
    actionMap.put("ESCAPE_0", new Sprite(rawSize, renderSize, 6, 0, spriteSheet));
    actionMap.put("ESCAPE_1", new Sprite(rawSize, renderSize, 7, 0, spriteSheet));
    actionMap.put("ESCAPE_2", new Sprite(rawSize, renderSize, 8, 0, spriteSheet));
    
    actionMap.put("VICTORY_0", new Sprite(rawSize, renderSize, 6, 1, spriteSheet));
    actionMap.put("VICTORY_1", new Sprite(rawSize, renderSize, 7, 1, spriteSheet));
    actionMap.put("VICTORY_2", new Sprite(rawSize, renderSize, 8, 1, spriteSheet));
    
    actionMap.put("LOW_HEALTH_0", new Sprite(rawSize, renderSize, 6, 2, spriteSheet));
    actionMap.put("LOW_HEALTH_1", new Sprite(rawSize, renderSize, 7, 2, spriteSheet));
    actionMap.put("LOW_HEALTH_2", new Sprite(rawSize, renderSize, 8, 2, spriteSheet));
    
    actionMap.put("STATUS_0", new Sprite(rawSize, renderSize, 6, 3, spriteSheet));
    actionMap.put("STATUS_1", new Sprite(rawSize, renderSize, 7, 3, spriteSheet));
    actionMap.put("STATUS_2", new Sprite(rawSize, renderSize, 8, 3, spriteSheet));
    
    actionMap.put("SLEEPING_0", new Sprite(rawSize, renderSize, 6, 4, spriteSheet));
    actionMap.put("SLEEPING_1", new Sprite(rawSize, renderSize, 7, 4, spriteSheet));
    actionMap.put("SLEEPING_2", new Sprite(rawSize, renderSize, 8, 4, spriteSheet));
    
    actionMap.put("DEAD_0", new Sprite(rawSize, renderSize, 6, 5, spriteSheet));
    actionMap.put("DEAD_1", new Sprite(rawSize, renderSize, 7, 5, spriteSheet));
    actionMap.put("DEAD_2", new Sprite(rawSize, renderSize, 8, 5, spriteSheet));
  }
  
  public void init() {}
  
  protected void moveFinished(boolean offensive) {
    if (isDead) playDeadAnimation();
    else currentAnimation = repeatingAnimation;
  }
  
  public void setBattle(Battle battle) {
    this.battle = battle;
  }

  public Sprite getCurrentAction() {
    return currentAction;
  }

  public BattleMove getCurrentMove() {
    return currentMove;
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

  public StatModel getStatModel() {
    return statModel;
  }

  public SpawnCoordinate getSlot() {
    return slot;
  }

  public boolean isReady() {
    return ready;
  }

  public boolean isDead() {
    return isDead;
  }

  public boolean isMoving() {
    return moving;
  }

  public boolean isGuarding() {
    return guarding;
  }

  public void setGuarding(boolean guarding) {
    this.guarding = guarding;
  }

  public void setReady(boolean ready) {
    this.ready = ready;
  }

  public void playIdleAnimation() {
    if (!isGuarding()) currentAnimation = repeatingAnimation = idleAnimation;
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
  
  public void returnToIdleState() {
    setReady(false);
    moving = false;
    readyGauge = 0;
  }
  
  public double getAttackModifier() {
    return (buff.isActive()) ? buff.getAttackModifier() : 0;
  }
  
  public double getDefenseModifier() {
    return (buff.isActive()) ? buff.getDefenseModifier() : 0;
  }
  
  public double getIntellectModifier() {
    return (buff.isActive()) ? buff.getIntellectModifier() : 0;
  }
  
  public double getSpiritModifier() {
    return (buff.isActive()) ? buff.getSpiritModifier() : 0;
  }
  
  public double getElementalBuffModifier(String element) {
    return (buff.isActive()) ? buff.getElementalModifier(element) : 0;
  }
  
  public void setBuff(Buff buff) {
    try {
      this.buff = (Buff)buff.clone();
      setBuffBar();
    } catch (CloneNotSupportedException ex) {
      Logger.getLogger(BattleEntity.class.getName()).log(Level.SEVERE, null, ex);
    }
  }  

  private int getBuffBarX() {
    return x - 25;
  }
  
  private int getBuffBarY() {
    return y - currentAction.getHeight() / 2 - 1;
  }
  
  protected void checkHealthStatus() {
    if (statModel.getHealth() == 0) currentAnimation = deadAnimation;
    else if (statModel.getHealth() / (double)statModel.getHealthMax() < .2 && !isGuarding()) playLowHealthAnimation();
    else playIdleAnimation();
  }
  
  protected void moveToLocation(int x, int y) {
    playIdleAnimation();
    traveling = true;
    int xStep, yStep;
    for (int i = 1; i < 21; i++) {
      xStep = (int)(this.x + ((double)i * ((double)(x - this.x) / 20)));
      yStep = (int)(this.y + ((double)i * ((double)(y - this.y) / 20)));
      travelingSteps.add(new int[]{xStep, yStep});
    }
  }
  
  private void updateTravel() {
    if (traveling) {
      if (!travelingSteps.isEmpty() && travelingSteps.get(0) != null) {
        this.x = travelingSteps.get(0)[0];
        this.y = travelingSteps.get(0)[1];
        travelingSteps.remove(0);
      }
      else {
        traveling = false;
        if (isReady() && !finishedAttacking) {
          processMove();
        }
      }
    }
  }
  
  protected void processMove() {
    try {
      moveAnimation.invoke(this);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      Logger.getLogger(EnemyBattleEntity.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (currentMove.getTargetAnimationDelay() > 0) {
      Helpers.setTimeout(() -> battle.getMoveProcessor().process(this, currentTarget, currentMove), currentMove.getTargetAnimationDelay());
    }
    else battle.getMoveProcessor().process(this, currentTarget, currentMove);
  }
  
  private void fillGauge() {
    if (readyGauge++ == readyGaugeMax) {
      ready = true;
      setGuarding(false);
      this.playReadyPhysicalAnimation();
    }
  }
  
  public double getReadyPercent() {
    return ((double)readyGauge / readyGaugeMax > 1) ? 1 : (double)readyGauge / readyGaugeMax;
  }
  
  protected void setAsDead() {
    isDead = true;
    ready = false;
    setGuarding(false);
    readyGauge = 0;
  }
  
  protected void checkForHealth() {}
  
  private void setBuffBar() {
    buffBarFilled = new Sprite((int)(buff.getTimeRemaining() / (float)buff.getBuffTime() * 50), 1, GameColors.MANA_BAR_FILL);
  }
  
  public void update() {
    if (!isDead) checkForHealth();
    currentAnimation.execute();
    anim++;
    if (battle.isGaugesFilling() && !ready && !isDead) {
      fillGauge();
      if (buff.isActive()) {
        buff.reduceTime();
        if (buff.getBuffTime() % 60 == 0) setBuffBar();
      }
    }
    updateTravel();
  }
  
  public void render(Screen screen, GL2 gl) {
    if (buff.isActive()) {
      screen.renderSprite(gl, getBuffBarX(), getBuffBarY(), buffBarEmpty, false);
      if (buffBarFilled.getWidth() > 0) screen.renderSprite(gl, getBuffBarX(), getBuffBarY(), buffBarFilled, false);
    }
  }
  
}
