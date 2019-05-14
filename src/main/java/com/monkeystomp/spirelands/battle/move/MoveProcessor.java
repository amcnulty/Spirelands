package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import java.util.Random;

/**
 * The move processor takes all the information about a move and processes the actions and fires appropriate actions.
 * @author Aaron Michael McNulty
 */
public class MoveProcessor {
  
  private final Random random = new Random();
  
  public void process(BattleEntity user, BattleEntity target, EnemyMove move) {
    if (user instanceof EnemyBattleEntity) {
      if (target instanceof CharacterBattleEntity) {
        System.out.println(move.getType());
        if (move.getVariety().equals(EnemyMove.OFFENSIVE)) processEnemyOffensiveMove((EnemyBattleEntity)user, (CharacterBattleEntity)target, move);
      }
    }
  }
  
  private void processEnemyOffensiveMove(EnemyBattleEntity user, CharacterBattleEntity target, EnemyMove move) {
    int attackPower, overallEffect;
    if (random.nextInt(100) + 1 > move.getAccuracy()) {
      target.playEvadeAnimation();
      System.out.println(user.getEnemy().getName() + " missed " + target.getCharacter().getName() + "!");
    }
    else {
      attackPower = (int)(move.getPowerLevel() * user.getEnemy().getStrength() * ( .1 + ( .009 * ( user.getEnemy().getLevel() ))));
      overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getCharacter().getDefense() ))));
      target.playDamageAnimation();
      target.getCharacter().decreaseHealth(overallEffect);
      System.out.println(user.getEnemy().getName() + " did " + overallEffect + " damage to " + target.getCharacter().getName() + "!");
    }
  }

}
