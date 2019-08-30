package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.util.Helpers;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterBattleEntity extends BattleEntity {
  
  private final int rightOfTarget = 28;
  private final Random random = new Random();
  private boolean targetingSelf = false,
                  showingControls = false;
  private final AnimatedSprite ringBack = new AnimatedSprite(128, 48, new SpriteSheet("./resources/animations/characterRing/character_ring_back.png"), AnimatedSprite.VERY_SLOW, 5);
  private final AnimatedSprite ringFront = new AnimatedSprite(128, 48, new SpriteSheet("./resources/animations/characterRing/character_ring_front.png"), AnimatedSprite.VERY_SLOW, 5);
  
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
    if (!moving && currentAnimation != damageAnimation) {
      if (statModel.getHealth() / (double)statModel.getHealthMax() < .2 && !isGuarding()) playLowHealthAnimation();
      else playIdleAnimation();
    }
  }
  
  @Override
  protected void moveFinished(boolean offensive) {
    super.moveFinished(offensive);
    checkHealthStatus();
    if (this.x != getSlot().getX() || this.y != getSlot().getY()) {
      moveToLocation(getSlot().getX(), getSlot().getY());
    }
    if (offensive || targetingSelf) {
      targetingSelf = false;
      finishedAttacking = true;
      Helpers.setTimeout(() -> {
        returnToIdleState();
      }, 500);
    }
  }

  public void makeMove(BattleMove move, BattleEntity target) {
    moving = true;
    finishedAttacking = false;
    showingControls = false;
    currentTarget = target;
    // Show the name of the move as a message on the screen by calling the battle class show move name method.
    battle.showBattleMoveName(move.getName());
    if (target instanceof CharacterBattleEntity) {
      targetingSelf = target == this;
    }
    else targetingSelf = false;
    currentMove = move;
    moveAnimation = move.getMoveAnimation();
    if (!move.isRanged() && !targetingSelf) {
      moveToLocation(target.getX() + rightOfTarget, target.getY());
    }
    else processMove();
  }
  
  @Override
  public Character getStatModel() {
    return (Character)statModel;
  }

  public void setShowingControls(boolean showingControls) {
    this.showingControls = showingControls;
  }
  
  @Override
  public void update() {
    super.update();
    ringBack.update();
    ringFront.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (showingControls) screen.renderSprite(gl, x - 10, y + 4 - 24, ringBack.getSprite(), false);
    super.render(screen, gl);
    if (showingControls) screen.renderSprite(gl, x - 10, y + 4 + 24, ringFront.getSprite(), false);
  }

}
