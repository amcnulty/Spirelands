package com.monkeystomp.spirelands.battle.move;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import com.monkeystomp.spirelands.graphics.Screen;
import java.util.Random;
import java.util.function.Consumer;

/**
 * The move processor takes all the information about a move and processes the actions and fires appropriate actions.
 * @author Aaron Michael McNulty
 */
public class MoveProcessor {
  
  private final Random random = new Random();
  private Consumer<FlashMessage> IFlashMessage;
  private final SoundEffects sfx = new SoundEffects();
  private BattleMove currentMove;
  private BattleEntity currentTarget;

  public void setIFlashMessage(Consumer<FlashMessage> IFlashMessage) {
    this.IFlashMessage = IFlashMessage;
  }
  
  public void process(BattleEntity user, BattleEntity target, BattleMove move) {
    currentTarget = target;
    currentMove = move;
    if (move.getVariety().equals(BattleMove.OFFENSIVE)) processOffensiveMove(user, target, move);
  }
  
  private void processOffensiveMove(BattleEntity user, BattleEntity target, BattleMove move) {
    int attackPower, overallEffect;
    if (random.nextInt(100) + 1 > move.getAccuracy()) {
      if (user != target) target.playEvadeAnimation();
      sfx.playSoundEffect(SoundEffects.HIT_MISS);
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
      if (move.hasSound()) {
        sfx.playSoundEffect(move.getSound());
      }
      if (currentMove.hasTargetAnimation()) {
        currentMove.getTargetAnimation().setReadyToPlay(true);
      }
      user.getStatModel().decreaseMana(move.getManaRequired());
      target.getStatModel().decreaseHealth(overallEffect);
      FlashMessage message = new FlashMessage(target.getX() + 16, target.getY(), String.valueOf(overallEffect));
      message.floatMessageUp(true);
      IFlashMessage.accept(message);
//      System.out.println(user.getStatModel().getName() + " did " + overallEffect + " damage to " + target.getStatModel().getName() + "!");
    }
  }
  
  public void update() {
    if (currentMove != null) {
      if (currentMove.hasTargetAnimation()) {
        if (currentMove.getTargetAnimation().isReadyToPlay()) currentMove.getTargetAnimation().update();
      }
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (currentMove != null) {
      if (currentMove.hasTargetAnimation()) {
        if (currentMove.getTargetAnimation().isReadyToPlay()) screen.renderSprite(gl, currentTarget.getX() + currentTarget.getCurrentAction().getWidth() / 2 - currentMove.getTargetAnimation().getSprite().getWidth() / 2, currentTarget.getY() + currentTarget.getCurrentAction().getHeight() / 2 - currentMove.getTargetAnimation().getSprite().getHeight() / 2, currentMove.getTargetAnimation().getSprite(), false);
      }
    }
  }
  
}
