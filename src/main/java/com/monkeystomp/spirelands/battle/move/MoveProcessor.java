package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
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
  
  public void process(BattleEntity user, BattleEntity target, BattleMove move) {
    if (move.getVariety().equals(BattleMove.OFFENSIVE)) processOffensiveMove(user, target, move);
  }
  
  private void processOffensiveMove(BattleEntity user, BattleEntity target, BattleMove move) {
    int attackPower, overallEffect;
    if (random.nextInt(100) + 1 > move.getAccuracy()) {
      if (user != target) target.playEvadeAnimation();
      FlashMessage message = new FlashMessage(target.getX() + 16, target.getY(), "miss");
      message.floatMessageUp(true);
      IFlashMessage.accept(message);
//      System.out.println(user.getStatModel().getName() + " missed " + target.getStatModel().getName() + "!");
    }
    else {
      if (move.getType().equals(BattleMove.PHYSICAL)) {
        attackPower = (int)(move.getPowerLevel() * user.getStatModel().getStrength() * ( .1 + ( .009 * ( user.getStatModel().getLevel() ))));
        overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getStatModel().getDefense() ))));
      }
      else {
        attackPower = (int)(move.getPowerLevel() * user.getStatModel().getIntellect() * ( .1 + ( .009 * ( user.getStatModel().getLevel() ))));
        overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getStatModel().getSpirit() ))));
      }
      overallEffect = (int)( overallEffect * ( 1 + (  ( random.nextDouble() - .5 ) / 5 )));
      if (user != target) target.playDamageAnimation();
      target.getStatModel().decreaseHealth(overallEffect);
      FlashMessage message = new FlashMessage(target.getX() + 16, target.getY(), String.valueOf(overallEffect));
      message.floatMessageUp(true);
      IFlashMessage.accept(message);
//      System.out.println(user.getStatModel().getName() + " did " + overallEffect + " damage to " + target.getStatModel().getName() + "!");
    }
  }
  
}
