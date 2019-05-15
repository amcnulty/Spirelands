package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import java.util.Random;
import java.util.function.Consumer;

/**
 * The move processor takes all the information about a move and processes the actions and fires appropriate actions.
 * @author Aaron Michael McNulty
 */
public class MoveProcessor {
  
  private final Random random = new Random();
  private Consumer<FlashMessage> IFlashMessage;

  public void setIFlashMessage(Consumer<FlashMessage> IFlashMessage) {
    this.IFlashMessage = IFlashMessage;
  }
  
  public void process(BattleEntity user, BattleEntity target, EnemyMove move) {
    if (user instanceof EnemyBattleEntity) {
      if (target instanceof CharacterBattleEntity) {
        if (move.getVariety().equals(EnemyMove.OFFENSIVE)) processEnemyOffensiveMove((EnemyBattleEntity)user, (CharacterBattleEntity)target, move);
      }
    }
  }
  
  private void processEnemyOffensiveMove(EnemyBattleEntity user, CharacterBattleEntity target, EnemyMove move) {
    int attackPower, overallEffect;
    if (random.nextInt(100) + 1 > move.getAccuracy()) {
      target.playEvadeAnimation();
      FlashMessage message = new FlashMessage(target.getX() + 16, target.getY(), "miss");
      message.floatMessageUp(true);
      IFlashMessage.accept(message);
      System.out.println(user.getEnemy().getName() + " missed " + target.getCharacter().getName() + "!");
    }
    else {
      attackPower = (int)(move.getPowerLevel() * user.getEnemy().getStrength() * ( .1 + ( .009 * ( user.getEnemy().getLevel() ))));
      overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getCharacter().getDefense() ))));
      overallEffect = (int)( overallEffect * ( 1 + (  ( random.nextDouble() - .5 ) / 5 )));
      target.playDamageAnimation();
      target.getCharacter().decreaseHealth(overallEffect);
      FlashMessage message = new FlashMessage(target.getX() + 16, target.getY(), String.valueOf(overallEffect));
      message.floatMessageUp(true);
      IFlashMessage.accept(message);
      System.out.println(user.getEnemy().getName() + " did " + overallEffect + " damage to " + target.getCharacter().getName() + "!");
    }
  }

}
