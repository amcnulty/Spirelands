package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.battle.battlecard.BattleCard;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
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
  protected String battleMusic;
  private int tick = 0;
  private boolean gaugesFilling = true;
  private final ArrayList<CharacterBattleEntity> party = new ArrayList<>();
  protected final ArrayList<EnemyBattleEntity> enemies = new ArrayList<>();
  private ArrayList<BattleEntity> readyEntities = new ArrayList<>();
  private final ArrayList<BattleCard> battleCards = new ArrayList<>();
  
  public Battle() {
    setSlotMap();
    createPartyMembers();
  }
  
  public void init() {
    Music.getMusicPlayer().play(battleMusic);
    for (CharacterBattleEntity partyMember: party) {
      partyMember.init();
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.init();
    }
  }
  
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
    Location lastLocation = LocationManager.getLocationManager().getCurrentLocation();
    ViewManager.getViewManager().changeView(new LevelView(LevelFactory.createLevel(lastLocation.getLevelId(), lastLocation.getCoordinate())));
  }

  public boolean isGaugesFilling() {
    return gaugesFilling;
  }
  
  private CharacterBattleEntity getTarget() {
    return party.get(random.nextInt(party.size()));
  }
  
  private void checkForReadyEntites() {
    if (gaugesFilling) {
      for (CharacterBattleEntity partyMember: party) {
        if (partyMember.isReady()) {
          System.out.println(partyMember.getCharacter().getName() + " is ready!");
          gaugesFilling = false;
          readyEntities.add(partyMember);
        }
      }
      for (EnemyBattleEntity enemy: enemies) {
        if (enemy.isReady()) {
          System.out.println(enemy.getEnemy().getName() + " is ready!");
          gaugesFilling = false;
          readyEntities.add(enemy);
        }
      }
    }
    if (!readyEntities.isEmpty()) {
      if (!readyEntities.get(0).isReady()) readyEntities.remove(0);
      if (!readyEntities.isEmpty()) {
        if (readyEntities.get(0) instanceof EnemyBattleEntity) {
          if (!((EnemyBattleEntity)readyEntities.get(0)).isMoving()) {
            ((EnemyBattleEntity)readyEntities.get(0)).makeMove(getTarget());
          }
        }
      }
      else gaugesFilling = true;
    }
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
    checkForReadyEntites();
    if (tick++ == 5700) endBattle();
//    else if (tick % 300 == 0) {
//      party.get(0).playUseMagicalSkillAnimation();
//      party.get(1).playShootingAnimation();
//      party.get(2).playEvadeAnimation();
//      enemies.get(0).getEnemy().decreaseHealth(40);
//      enemies.get(0).playDamageAnimation();
//      enemies.forEach(enemy -> System.out.println(enemy.getEnemy().getHealth()));
//    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 0, 0, background, false);
    for (BattleEntity partyMember: party) {
      partyMember.render(screen, gl);
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.render(screen, gl);
    }
    for (BattleCard card: battleCards) {
      card.render(screen, gl);
    }
  }

}
