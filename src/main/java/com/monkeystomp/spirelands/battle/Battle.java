package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.battle.battlecard.BattleCard;
import com.monkeystomp.spirelands.battle.controls.BattleControls;
import com.monkeystomp.spirelands.battle.controls.TargetSelector;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.battle.message.FlashMessage;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.battle.move.MoveProcessor;
import com.monkeystomp.spirelands.battle.victory.VictoryScreen;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.view.LevelView;
import com.monkeystomp.spirelands.view.ViewManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Battle {
  
  protected SpawnCoordinate partyMemberSlot1 = new SpawnCoordinate(300, 110, 3),
                            partyMemberSlot2 = new SpawnCoordinate(320, 70, 3),
                            partyMemberSlot3 = new SpawnCoordinate(320, 150, 3);
  private final HashMap<Integer, SpawnCoordinate> slotMap = new HashMap<>();
  private final Random random = new Random();
  private final int battleCardTop = 200;
  protected Sprite background;
  protected String  battleMusic,
                    victoryMusic;
  private final MoveProcessor moveProcessor = new MoveProcessor();
  private VictoryScreen victoryScreen;
  private int tick = 0;
  private boolean gaugesFilling = true,
                  battleVictory = false;
  private final ArrayList<CharacterBattleEntity> party = new ArrayList<>();
  protected final ArrayList<EnemyBattleEntity> enemies = new ArrayList<>();
  private final ArrayList<BattleEntity> readyEntities = new ArrayList<>();
  private final ArrayList<BattleCard> battleCards = new ArrayList<>();
  private final ArrayList<FlashMessage> currentMessages = new ArrayList<>();
  private final Consumer<FlashMessage> IFlashMessage = flashMessage -> currentMessages.add(flashMessage);
  private final BattleControls battleControls = new BattleControls((move) -> handleBattleControlSelection(move));
  private BattleMove currentCharacterMove;
  private final TargetSelector targetSelector = new TargetSelector(target -> handleTargetSelection(target));
  
  public Battle() {
    setSlotMap();
    moveProcessor.setIFlashMessage(IFlashMessage);
  }
  
  public void init() {
    createPartyMembers();
    createEnemies();
    victoryScreen = new VictoryScreen(
      party.stream().map(CharacterBattleEntity::getStatModel).collect(Collectors.toList()),
      enemies.stream().map(EnemyBattleEntity::getStatModel).collect(Collectors.toList()),
      () -> endBattle()
    );
    Music.getMusicPlayer().play(battleMusic);
    for (CharacterBattleEntity partyMember: party) {
      partyMember.init();
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.init();
    }
    targetSelector.init(party, enemies);
  }

  protected void createEnemies() {};
  
  private void setSlotMap() {
    slotMap.put(0, partyMemberSlot1);
    slotMap.put(1, partyMemberSlot2);
    slotMap.put(2, partyMemberSlot3);
  }
  
  private void createPartyMembers() {
    HashMap<Integer, Character> partyMembers = CharacterManager.getCharacterManager().getPartyMembers();
    partyMembers.forEach((key, partyMember) -> {
      CharacterBattleEntity newEntity = new CharacterBattleEntity(slotMap.get(key), partyMember);
      newEntity.setBattle(this);
      party.add(newEntity);
      battleCards.add(new BattleCard(newEntity, battleCards.size()));
    });
  }
  
  private void endBattle() {
    targetSelector.destroy();
    Location lastLocation = LocationManager.getLocationManager().getCurrentLocation();
    ViewManager.getViewManager().changeView(new LevelView(LevelFactory.createLevel(lastLocation.getLevelId(), lastLocation.getCoordinate())));
  }

  public MoveProcessor getMoveProcessor() {
    return moveProcessor;
  }

  public boolean isGaugesFilling() {
    return gaugesFilling;
  }
  
  private void handleBattleControlSelection(BattleMove move) {
    currentCharacterMove = move;
    if (move.isSingleTarget()) targetSelector.selectSingleTarget(readyEntities.get(0));
    else if (move.getVariety().equals(BattleMove.OFFENSIVE)) targetSelector.selectEnemyTarget();
    else if (move.getVariety().equals(BattleMove.DEFENSIVE)) targetSelector.selectCharacterTarget();
  }
  
  private void handleTargetSelection(BattleEntity target) {
    ((CharacterBattleEntity)readyEntities.get(0)).makeMove(currentCharacterMove, target);
    battleControls.hideControls();
    targetSelector.setTargeting(false);
  }
  
  private CharacterBattleEntity getTarget() {
    CharacterBattleEntity nextTarget = party.get(random.nextInt(party.size()));
    if (nextTarget.isDead()) return getTarget();
    else return nextTarget;
  }
  
  private void checkForReadyEntities() {
    if (gaugesFilling) {
      for (CharacterBattleEntity partyMember: party) {
        if (partyMember.isReady()) {
          gaugesFilling = false;
          readyEntities.add(partyMember);
        }
      }
      for (EnemyBattleEntity enemy: enemies) {
        if (enemy.isReady()) {
          gaugesFilling = false;
          readyEntities.add(enemy);
        }
      }
    }
    if (!readyEntities.isEmpty()) {
      if (!readyEntities.get(0).isReady()) {
        readyEntities.remove(0);
        checkForReadyEntities();
      }
      if (!readyEntities.isEmpty()) {
        if (readyEntities.get(0) instanceof EnemyBattleEntity) {
          if (!(readyEntities.get(0)).isMoving()) {
            ((EnemyBattleEntity)readyEntities.get(0)).makeMove(getTarget());
          }
        }
        else if (readyEntities.get(0) instanceof CharacterBattleEntity) {
          if (!battleControls.isShowing() && !readyEntities.get(0).isMoving()) {
            battleControls.setControlsForBattleEntity((CharacterBattleEntity)readyEntities.get(0));
            ((CharacterBattleEntity)readyEntities.get(0)).setShowingControls(true);
          }
        }
      }
      else gaugesFilling = true;
    }
  }
  
  private boolean isVictory() {
    for (BattleEntity entity: enemies) {
      if (!entity.isDead()) return false;
    }
    return true;
  }
  
  private void playVictoryAnimation() {
    for (BattleEntity partyMember: party) {
      partyMember.playVictoryAnimation();
    }
    Music.getMusicPlayer().play(victoryMusic);
    setTimeout(() -> {
      showVictoryScreen();
      victoryScreen.awardParty();
    }, 6000);
  }
  
  private void showVictoryScreen() {
    victoryScreen.setShowing(true);
  }
  
  private void setTimeout(Runnable runnable, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      } catch (InterruptedException ex) {
        Logger.getLogger(Battle.class.getName()).log(Level.SEVERE, null, ex);
      }
    }).start();
  }
  
  public void update() {
    for (BattleEntity partyMember: party) {
      partyMember.update();
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.update();
    }
    for (BattleCard card: battleCards) {
      card.update();
    }
    for (int i = 0; i < currentMessages.size(); i++) {
      if (currentMessages.get(i).isVisible()) currentMessages.get(i).update();
      else currentMessages.remove(i);
    }
    if (!battleVictory) checkForReadyEntities();
    if (isVictory() && !battleVictory) {
      setTimeout(this::playVictoryAnimation, 1500);
      battleVictory = true;
      battleControls.hideControls();
    }
    if (battleControls.isShowing()) battleControls.update();
    if (victoryScreen.isShowing()) {
      victoryScreen.update();
      tick++;
      if (tick == 2100) endBattle();
    }
    targetSelector.update();
    moveProcessor.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 0, 0, background, false);
    for (BattleEntity partyMember: party) {
      partyMember.render(screen, gl);
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.render(screen, gl);
    }
    if (!victoryScreen.isShowing()) {
      for (BattleCard card: battleCards) {
        card.render(screen, gl);
      }
    }
    moveProcessor.render(screen, gl);
    for (FlashMessage message: currentMessages) {
      message.render(screen, gl);
    }
    targetSelector.render(screen, gl);
    if (battleControls.isShowing()) battleControls.render(screen, gl);
    if (victoryScreen.isShowing()) victoryScreen.render(screen, gl);
  }

}
