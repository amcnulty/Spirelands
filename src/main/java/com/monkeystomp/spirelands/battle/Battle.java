package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
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

/**
 *
 * @author Aaron Michael McNulty
 */
public class Battle {
  
  protected SpawnCoordinate partyMemberSlot1 = new SpawnCoordinate(300, 110, 3),
                            partyMemberSlot2 = new SpawnCoordinate(320, 70, 3),
                            partyMemberSlot3 = new SpawnCoordinate(320, 150, 3);
  private final HashMap<Integer, SpawnCoordinate> slotMap = new HashMap<>();
  protected Sprite background;
  protected String battleMusic;
  private int tick = 0;
  private boolean gaugesFilling = true;
  private final ArrayList<CharacterBattleEntity> party = new ArrayList<>();
  protected final ArrayList<EnemyBattleEntity> enemies = new ArrayList<>();
  
  public Battle() {
    setSlotMap();
    createPartyMembers();
  }
  
  public void init() {
    Music.getMusicPlayer().play(battleMusic);
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
      
    });
  }
  
  private void endBattle() {
    Location lastLocation = LocationManager.getLocationManager().getCurrentLocation();
    ViewManager.getViewManager().changeView(new LevelView(LevelFactory.createLevel(lastLocation.getLevelId(), lastLocation.getCoordinate())));
  }

  public boolean isGaugesFilling() {
    return gaugesFilling;
  }
  
  private void checkForReadyEntites() {
    for (CharacterBattleEntity partyMember: party) {
//      if (partyMember.isReady()) System.out.println(partyMember.getCharacter().getName() + " is ready!");
    }
    for (EnemyBattleEntity enemy: enemies) {
    }
  }
  
  public void update() {
    for (BattleEntity partyMember: party) {
      partyMember.update();
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.update();
    }
    checkForReadyEntites();
    if (tick++ == 5700) endBattle();
    else if (tick % 300 == 0) {
      party.get(0).playUseMagicalSkillAnimation();
      party.get(1).playShootingAnimation();
      party.get(2).playEvadeAnimation();
      enemies.get(0).getEnemy().decreaseHealth(40);
      enemies.get(0).playDamageAnimation();
      enemies.forEach(enemy -> System.out.println(enemy.getEnemy().getHealth()));
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 0, 0, background, false);
    for (BattleEntity partyMember: party) {
      partyMember.render(screen, gl);
    }
    for (EnemyBattleEntity enemy: enemies) {
      enemy.render(screen, gl);
    }
  }

}
