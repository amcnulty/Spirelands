package com.monkeystomp.spirelands.battle.move;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.elemental.Elemental;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.BattleItem;
import com.monkeystomp.spirelands.inventory.EquipmentItem;
import com.monkeystomp.spirelands.inventory.ItemAttribute;
import com.monkeystomp.spirelands.util.Helpers;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
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
    if (move.getType().equals(BattleMove.ITEM)) {
      if (move.getItem() instanceof EquipmentItem) processEquipmentItemMove(user, target, move);
      else if (move.getItem() instanceof BattleItem) processBattleItemMove(user, target, move);
    }
    else if (move.getAction().equals(BattleMove.OFFENSIVE)) processOffensiveMove(user, target, move);
    else if (move.getAction().equals(BattleMove.DEFENSIVE)) processDefensiveMove(user, target, move);
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
        attackPower = (int)(move.getPowerLevel() * user.getStatModel().getCombinedAttack() * ( .1 + ( .009 * ( user.getStatModel().getLevel() ))));
        if (user instanceof CharacterBattleEntity && random.nextInt(100) + 1 > 95) {
          attackPower *= 1.5;
          FlashMessage message = new FlashMessage(user.getX() - 5, user.getY() + 8, "Critical!");
          message.floatMessageUp(true);
          IFlashMessage.accept(message);
        }
        if (target.isGuarding()) {
          overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( (target.getStatModel().getCombinedDefense() * 1.5) ))));
        }
        else overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getStatModel().getCombinedDefense() ))));
      }
      else {
        attackPower = (int)(move.getPowerLevel() * user.getStatModel().getCombinedIntellect() * ( .1 + ( .009 * ( user.getStatModel().getLevel() ))));
        if (user instanceof CharacterBattleEntity) attackPower *= ((CharacterBattleEntity)user).getStatModel().getWeaponElementalModifier(move.getElement());
        if (target instanceof EnemyBattleEntity) attackPower *= Elemental.getModifierForEnemyElement(move.getElement(), (Enemy)target.getStatModel());
        overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getStatModel().getCombinedSpirit() ))));
        if (target instanceof CharacterBattleEntity) overallEffect -= overallEffect * ((CharacterBattleEntity)target).getStatModel().getArmorElementalResistance(move.getElement());
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
      FlashMessage message = new FlashMessage(target, String.valueOf(overallEffect));
      message.floatMessageUp(true);
      IFlashMessage.accept(message);
//      System.out.println(user.getStatModel().getName() + " did " + overallEffect + " damage to " + target.getStatModel().getName() + "!");
    }
  }
  
  private void processDefensiveMove(BattleEntity user, BattleEntity target, BattleMove move) {
      if (move.hasSound()) {
        sfx.playSoundEffect(move.getSound());
      }
      if (currentMove.hasTargetAnimation()) {
        currentMove.getTargetAnimation().setReadyToPlay(true);
      }
      user.getStatModel().decreaseMana(move.getManaRequired());
      FlashMessage message = move.getDefensiveAction().apply(new MoveInformation(user, target, move));
      if (message != null) IFlashMessage.accept(message);
  }
  
  private void processEquipmentItemMove(BattleEntity user, BattleEntity target, BattleMove move) {
    if (currentMove.hasTargetAnimation()) {
      currentMove.getTargetAnimation().setReadyToPlay(true);
    }
    move.getItem().setStatModel(target.getStatModel());
    ((EquipmentItem)move.getItem()).useItem();
    ArrayList<FlashMessage> messages = new ArrayList<>();
    for (ItemAttribute attribute: move.getItem().getAttributes()) {
      if (attribute.getLabel().equals(ItemAttribute.HEALTH_RESTORE)) {
        FlashMessage message = new FlashMessage(target, "HP: " + attribute.getValue());
        message.setColor(new Color(GameColors.ATB_GAUGE_BAR_FILLED));
        message.floatMessageUp(true);
        messages.add(message);
      }
      if (attribute.getLabel().equals(ItemAttribute.MANA_RESTORE)) {
        FlashMessage message = new FlashMessage(target, "MP: " + attribute.getValue());
        message.setColor(new Color(GameColors.MANA_BAR_FILL));
        message.floatMessageUp(true);
        messages.add(message);
      }
    }
    for (int i = 0; i < messages.size(); i++) {
      FlashMessage message = messages.get(i);
      Helpers.setTimeout(() -> IFlashMessage.accept(message), i * 1000);
    }
  }
  
  public void processBattleItemMove(BattleEntity user, BattleEntity target, BattleMove move) {
    int attackPower, overallEffect;
    if (currentMove.hasTargetAnimation()) {
      currentMove.getTargetAnimation().setReadyToPlay(true);
    }
    ((BattleItem)move.getItem()).useItem();
    if (((BattleItem)move.getItem()).getBattleMoveType().equals(BattleMove.PHYSICAL)) {
      int averagePartyLevel = 0, averagePartyStrength = 0;
      HashMap<Integer, Character> partyMembers = CharacterManager.getCharacterManager().getPartyMembers();
      for (int i = 0; i < partyMembers.size(); i++) {
        averagePartyLevel += partyMembers.get(i).getLevel();
        averagePartyStrength += partyMembers.get(i).getCombinedAttack();
      }
      averagePartyLevel /= partyMembers.size();
      averagePartyStrength /= partyMembers.size();
      attackPower = (int)(move.getPowerLevel() * averagePartyStrength * ( .1 + ( .009 * ( averagePartyLevel ))));
      if (target.isGuarding()) {
        overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( (target.getStatModel().getCombinedDefense() * 1.5) ))));
      }
      else overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getStatModel().getCombinedDefense() ))));
    }
    else {
      int averagePartyLevel = 0, averagePartyIntellect = 0;
      HashMap<Integer, Character> partyMembers = CharacterManager.getCharacterManager().getPartyMembers();
      for (int i = 0; i < partyMembers.size(); i++) {
        averagePartyLevel += partyMembers.get(i).getLevel();
        averagePartyIntellect += partyMembers.get(i).getCombinedIntellect();
      }
      averagePartyLevel /= partyMembers.size();
      averagePartyIntellect /= partyMembers.size();
      attackPower = (int)(move.getPowerLevel() * averagePartyIntellect * ( .1 + ( .009 * ( averagePartyLevel ))));
      if (target instanceof EnemyBattleEntity) attackPower *= Elemental.getModifierForEnemyElement(move.getElement(), (Enemy)target.getStatModel());
      overallEffect = (int)(attackPower - ( attackPower * ( .002 * ( target.getStatModel().getCombinedSpirit() ))));
      if (target instanceof CharacterBattleEntity) overallEffect -= overallEffect * ((CharacterBattleEntity)target).getStatModel().getArmorElementalResistance(move.getElement());
    }
    overallEffect = (int)( overallEffect * ( 1 + (  ( random.nextDouble() - .5 ) / 5 )));
    if (user != target) target.playDamageAnimation();
    user.getStatModel().decreaseMana(move.getManaRequired());
    target.getStatModel().decreaseHealth(overallEffect);
    FlashMessage message = new FlashMessage(target, String.valueOf(overallEffect));
    message.floatMessageUp(true);
    IFlashMessage.accept(message);
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
