package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EnemyBattleEntity extends BattleEntity {
  
  private final Random random = new Random();
  private final float healthBarAnimationDuration = 60f;
  private float alpha = 1,
                healthBarAlpha = 0f;
  private int disapearDelay = 120,
              healthBarDisapearDelay = 120,
              healthBarAnimationTick = 0,
              lastHealthBarWidth,
              currentHealth;
  private boolean animatingHealthBar = false;
  private final Sprite healthBarEmpty = new Sprite(50, 1, GameColors.HEALTH_BAR_EMPTY);
  private Sprite healthBarFilled;
  
  public EnemyBattleEntity(SpawnCoordinate slot, Enemy enemy) {
    super(slot, enemy.getSpriteSheet());
    this.statModel = enemy;
    this.currentHealth = statModel.getHealth();
    if (enemy.getRenderSize() > 0) setupActionMap(enemy.getSpriteSheet(), enemy.getRenderSize());
    setReadyGaugeMax();
    calculateHealthBar();
  }
    
  private void setupActionMap(SpriteSheet spriteSheet, int renderSize) {
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
  
  private void setReadyGaugeStart() {
    double luckModifier = (double)statModel.getLuck() / Character.STAT_MAX;
    double rand = random.nextGaussian() * ((double)readyGaugeMax / 8) + ((luckModifier * readyGaugeMax) * .75);
    readyGauge = rand > 0 ? (int)rand : 0;
  }
  
  private void setReadyGaugeMax() {
    switch (statModel.getSpeed() / 25) {
      // 9.4 seconds
      case 0:
        readyGaugeMax = 564;
      break;
      // 8.4 seconds
      case 1:
        readyGaugeMax = 504;
      break;
      // 7.4 seconds
      case 2:
        readyGaugeMax = 444;
      break;
      // 6.6 seconds
      case 3:
        readyGaugeMax = 396;
      break;
      // 5.6 seconds
      case 4:
        readyGaugeMax = 336;
      break;
      // 4.6 seconds
      case 5:
        readyGaugeMax = 276;
      break;
      // 3.6 seconds
      case 6:
        readyGaugeMax = 216;
      break;
      // 2.8 seconds
      case 7:
        readyGaugeMax = 168;
      break;
      // 2.4 seconds
      case 8:
        readyGaugeMax = 144;
      break;
      // 2.2 second
      case 9:
        readyGaugeMax = 132;
      break;
      // 2 second
      case 10:
        readyGaugeMax = 120;
      break;
      default:
        readyGaugeMax = 120;
    }
  }
  
  private void calculateHealthBar() {
    int barWidth = (int)(((float)statModel.getHealth() / (float) statModel.getHealthMax()) * 50);
    healthBarFilled = new Sprite(barWidth, 1, GameColors.HEALTH_BAR_FILL);
  }
  
  @Override
  protected void moveFinished(boolean offensive) {
    super.moveFinished(offensive);
    checkHealthStatus();
    if (this.x != getSlot().getX() || this.y != getSlot().getY()) {
      moveToLocation(getSlot().getX(), getSlot().getY());
    }
    if (offensive) {
      finishedAttacking = true;
      returnToIdleState();
    }
  }
  
  @Override
  public void init() {
    setReadyGaugeStart();
  }
  
  public void makeMove(CharacterBattleEntity target) {
    currentTarget = target;
    moving = true;
    finishedAttacking = false;
    setNextMove();
    // Show the name of the move as a message on the screen by calling the battle class show move name method.
    battle.showBattleMoveName(currentMove.getName());
    if (!currentMove.isRanged()) {
      moveToLocation(target.getSlot().getX() - target.getCurrentAction().getWidth() / 2 - currentAction.getWidth() / 2 - 4, target.getSlot().getY());
    }
    else processMove();
  }
  
  private void setNextMove() {
    currentMove = ((Enemy)statModel).getRandomMove();
    moveAnimation = currentMove.getMoveAnimation();
  }
  
  @Override
  public Enemy getStatModel() {
    return (Enemy)statModel;
  }
  
  private int getHealthBarX() {
    return x - 25;
  }
  
  private int getHealthBarY() {
    return y - currentAction.getHeight() / 2 - 3;
  }
  
  @Override
  protected void checkForHealth() {
    if (statModel.getHealth() == 0) setAsDead();
    if (!moving && currentAnimation != damageAnimation) {
      if (statModel.getHealth() / (double)statModel.getHealthMax() < .2 && !isGuarding()) playLowHealthAnimation();
      else playIdleAnimation();
    }
    if (currentHealth != statModel.getHealth()) {
      currentHealth = statModel.getHealth();
      healthBarAlpha = 1;
      healthBarDisapearDelay = 120;
      animatingHealthBar = true;
      healthBarAnimationTick = 60;
      lastHealthBarWidth = healthBarFilled.getWidth();
    }
  }
  
  private void animateHealthBar() {
    int barWidth = (int)(((float)statModel.getHealth() / (float) statModel.getHealthMax()) * 50);
    int difference = (int)((lastHealthBarWidth - barWidth) * (healthBarAnimationTick / healthBarAnimationDuration));
    healthBarFilled = new Sprite(barWidth + difference, 1, GameColors.HEALTH_BAR_FILL);
  }
  
  @Override
  public void update() {
    super.update();
    if (isDead() && disapearDelay > -1) {
      disapearDelay--;
    }
    if (isDead() && alpha > 0 && disapearDelay < 0) {
      alpha -= .01f;
    }
    if (healthBarDisapearDelay > -1) {
      healthBarDisapearDelay--;
    }
    else if (healthBarDisapearDelay < 0 && healthBarAlpha > 0) {
      healthBarAlpha -= .05f;
    }
    if (animatingHealthBar && healthBarAnimationTick-- > -1) animateHealthBar();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFlippedSprite(gl, x - currentAction.getWidth() / 2, y - currentAction.getHeight() / 2, currentAction, alpha, false);
    screen.renderSprite(gl, getHealthBarX(), getHealthBarY(), healthBarEmpty, healthBarAlpha, false);
    if (healthBarFilled != null && healthBarFilled.getWidth() > 0) screen.renderSprite(gl, getHealthBarX(), getHealthBarY(), healthBarFilled, healthBarAlpha, false);
  }

}
