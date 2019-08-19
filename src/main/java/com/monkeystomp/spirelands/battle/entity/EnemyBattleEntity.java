package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EnemyBattleEntity extends BattleEntity {
  
  private final Random random = new Random();
  private final int leftOfTarget = 28,
                    healthBarAnimationDuration = 60;
  private float alpha = 1,
                healthBarAlpha = 0f;
  private int disapearDelay = 120,
              healthBarDisapearDelay = 120,
              healthBarAnimationTick = 0,
              lastHealthBarWidth,
              currentHealth,
              healthBarX,
              healthBarY;
  private boolean animatingHealthBar = false;
  private final Sprite healthBarEmpty = new Sprite(50, 1, GameColors.HEALTH_BAR_EMPTY);
  private Sprite healthBarFilled;
  
  public EnemyBattleEntity(SpawnCoordinate slot, Enemy enemy) {
    super(slot, enemy.getSpriteSheet());
    this.statModel = enemy;
    this.currentHealth = statModel.getHealth();
    setReadyGaugeMax();
    calculateHealthBar();
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
    if (!currentMove.isRanged()) {
      moveToLocation(target.getSlot().getX() - leftOfTarget, target.getSlot().getY());
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
    return healthBarX = x + currentAction.getWidth() / 2 - 25;
  }
  
  private int getHealthBarY() {
    return healthBarY = y - 3;
  }
  
  @Override
  protected void checkForHealth() {
    if (statModel.getHealth() == 0) setAsDead();
    if (currentHealth != statModel.getHealth()) {
//      calculateHealthBar();
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
    int difference = (int)((lastHealthBarWidth - barWidth) * (healthBarAnimationTick / (float)60));
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
    screen.renderFlippedSprite(gl, x, y, currentAction, alpha, false);
    screen.renderSprite(gl, getHealthBarX(), getHealthBarY(), healthBarEmpty, healthBarAlpha, false);
    if (healthBarFilled != null && healthBarFilled.getWidth() > 0) screen.renderSprite(gl, getHealthBarX(), getHealthBarY(), healthBarFilled, healthBarAlpha, false);
  }

}
