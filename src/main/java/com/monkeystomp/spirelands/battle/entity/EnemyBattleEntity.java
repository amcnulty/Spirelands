package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EnemyBattleEntity extends BattleEntity {
  
  private final Enemy enemy;
  private final Random random = new Random();
  private boolean moving = false,
                  traveling = false;
  private CharacterBattleEntity currentTarget;
  private final int leftOfTarget = 28;
  private ArrayList<int[]> travelingSteps = new ArrayList<>();
  private Method moveAnimation;
  
  public EnemyBattleEntity(SpawnCoordinate slot, Enemy enemy) {
    super(slot, enemy.getSpriteSheet());
    this.enemy = enemy;
    setReadyGaugeMax();
  }
  
  private void setReadyGaugeStart() {
    double luckModifier = (double)enemy.getLuck() / Character.STAT_MAX;
    double rand = random.nextGaussian() * ((double)readyGaugeMax / 8) + ((luckModifier * readyGaugeMax) * .75);
    readyGauge = rand > 0 ? (int)rand : 0;
  }
  
  private void setReadyGaugeMax() {
    switch (enemy.getSpeed() / 25) {
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
  
  private void moveToLocation(int x, int y) {
    traveling = true;
    int xStep, yStep;
    for (int i = 1; i < 21; i++) {
      xStep = (int)(this.x + ((double)i * ((double)(x - this.x) / 20)));
      yStep = (int)(this.y + ((double)i * ((double)(y - this.y) / 20)));
      travelingSteps.add(new int[]{xStep, yStep});
    }
  }
  
  @Override
  protected void moveFinished(boolean offensive) {
    super.moveFinished(offensive);
    if (enemy.getHealth() == 0) currentAnimation = deadAnimation;
    if (this.x != getSlot().getX() || this.y != getSlot().getY()) {
      moveToLocation(getSlot().getX(), getSlot().getY());
    }
    if (offensive) {
      setReady(false);
      moving = false;
    }
  }
  
  @Override
  public void init() {
    setReadyGaugeStart();
  }

  public boolean isMoving() {
    return moving;
  }
  
  public Enemy getEnemy() {
    return this.enemy;
  }
  
  public void makeMove(CharacterBattleEntity target) {
    this.currentTarget = target;
    moving = true;
    Class<?>[] args = null;
    moveToLocation(target.getX() - leftOfTarget, target.getY());
    try {
      moveAnimation = BattleEntity.class.getDeclaredMethod("playSwingingAnimation", args);
    } catch (NoSuchMethodException | SecurityException ex) {
      Logger.getLogger(EnemyBattleEntity.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  private void updateTravel() {
    if (traveling) {
      this.x = travelingSteps.get(0)[0];
      this.y = travelingSteps.get(0)[1];
      travelingSteps.remove(0);
      if (travelingSteps.isEmpty()) {
        traveling = false;
        if (isReady()) {
          try {
            moveAnimation.invoke(this);
            currentTarget.handleAttack(enemy);
          } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(EnemyBattleEntity.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    }
  }
  
  @Override
  public void update() {
    super.update();
    updateTravel();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFlippedSprite(gl, x, y, currentAction, false);
  }

}
