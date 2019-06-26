package com.monkeystomp.spirelands.battle.entity;

import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.util.Helpers;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterBattleEntity extends BattleEntity {
  
  private final int rightOfTarget = 28;
  private final Random random = new Random();
  
  public CharacterBattleEntity(SpawnCoordinate slot, Character character) {
    super(slot, character.getBattleSheet());
    this.statModel = character;
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
  public void init() {
    if (statModel.getHealth() == 0) {
      setAsDead();
      playDeadAnimation();
    }
    else setReadyGaugeStart();
  }
  
  @Override
  protected void checkForHealth() {
    if (statModel.getHealth() == 0) setAsDead();
  }
  
  @Override
  protected void moveFinished(boolean offensive) {
    super.moveFinished(offensive);
    if (statModel.getHealth() == 0) currentAnimation = deadAnimation;
    if (this.x != getSlot().getX() || this.y != getSlot().getY()) {
      moveToLocation(getSlot().getX(), getSlot().getY());
    }
    if (offensive) {
      finishedAttacking = true;
      Helpers.setTimeout(() -> {
        returnToIdleState();
        setCurrentAnimation();
      }, 500);
    }
  }
  
  private void setCurrentAnimation() {
    if (statModel.getHealth() / (double)statModel.getHealthMax() < .2) playLowHealthAnimation();
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

  public void makeMove(BattleMove move, EnemyBattleEntity target) {
    moving = true;
    finishedAttacking = false;
    currentTarget = target;
    currentMove = move;
    moveAnimation = move.getMoveAnimation();
    if (!move.isRanged()) {
      moveToLocation(target.getX() + rightOfTarget, target.getY());
    }
    else processMove();
  }
  
  @Override
  public Character getStatModel() {
    return (Character)statModel;
  }

}
