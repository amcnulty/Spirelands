package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EnemyBattleEntity extends BattleEntity {
  
  private final Random random = new Random();
  private final int leftOfTarget = 28;
  private float alpha = 1;
  private int disapearDelay = 120;
  
  public EnemyBattleEntity(SpawnCoordinate slot, Enemy enemy) {
    super(slot, enemy.getSpriteSheet());
    this.statModel = enemy;
    setReadyGaugeMax();
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
  
  @Override
  protected void moveFinished(boolean offensive) {
    super.moveFinished(offensive);
    if (statModel.getHealth() == 0) currentAnimation = deadAnimation;
    if (this.x != getSlot().getX() || this.y != getSlot().getY()) {
      moveToLocation(getSlot().getX(), getSlot().getY());
    }
    if (offensive) {
      returnToIdleState();
      setCurrentAnimation();
    }
  }
  
  private void setCurrentAnimation () {
    if (statModel.getHealth() / (double)statModel.getHealthMax() < .2) playLowHealthAnimation();
  }
  
  @Override
  public void init() {
    setReadyGaugeStart();
  }
  
  public void makeMove(CharacterBattleEntity target) {
    currentTarget = target;
    moving = true;
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
  protected void processMove() {
    try {
      moveAnimation.invoke(this);
      battle.getMoveProcessor().process(this, currentTarget, currentMove);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      Logger.getLogger(EnemyBattleEntity.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @Override
  public Enemy getStatModel() {
    return (Enemy)statModel;
  }
  
  @Override
  protected void checkForHealth() {
    if (statModel.getHealth() == 0) setAsDead();
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
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFlippedSprite(gl, x, y, currentAction, alpha, false);
  }

}
