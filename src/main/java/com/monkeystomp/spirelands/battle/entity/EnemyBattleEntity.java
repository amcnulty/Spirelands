package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EnemyBattleEntity extends BattleEntity {
  
  private final Enemy enemy;
  
  public EnemyBattleEntity(SpawnCoordinate slot, Enemy enemy) {
    super(slot, enemy.getSpriteSheet());
    this.enemy = enemy;
    setReadyGaugeMax();
  }
  
  private void setReadyGaugeMax() {
    switch (enemy.getSpeed() / 25) {
      // 4.7 seconds
      case 0:
        readyGaugeMax = 282;
      break;
      // 4.2 seconds
      case 1:
        readyGaugeMax = 252;
      break;
      // 3.7 seconds
      case 2:
        readyGaugeMax = 222;
      break;
      // 3.3 seconds
      case 3:
        readyGaugeMax = 198;
      break;
      // 2.8 seconds
      case 4:
        readyGaugeMax = 168;
      break;
      // 2.3 seconds
      case 5:
        readyGaugeMax = 138;
      break;
      // 1.8 seconds
      case 6:
        readyGaugeMax = 108;
      break;
      // 1.4 seconds
      case 7:
        readyGaugeMax = 84;
      break;
      // 1.2 seconds
      case 8:
        readyGaugeMax = 72;
      break;
      // 1.1 second
      case 9:
        readyGaugeMax = 66;
      break;
      // 1 second
      case 10:
        readyGaugeMax = 60;
      break;
      default:
        readyGaugeMax = 60;
    }
  }
  
  @Override
  protected void playLastRepeatingAnimation() {
    super.playLastRepeatingAnimation();
    if (enemy.getHealth() == 0) currentAnimation = deadAnimation;
  }
  
  public Enemy getEnemy() {
    return this.enemy;
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFlippedSprite(gl, x, y, currentAction, false);
  }

}
