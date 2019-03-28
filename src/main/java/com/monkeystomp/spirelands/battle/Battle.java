package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.mob.npc.BasicNPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPCConfig;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.view.LevelView;
import com.monkeystomp.spirelands.view.ViewManager;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Battle {
  
  protected SpawnCoordinate partyMemberSlot1 = new SpawnCoordinate(300, 210, 3),
                            partyMemberSlot2 = new SpawnCoordinate(320, 170, 3),
                            partyMemberSlot3 = new SpawnCoordinate(320, 250, 3);
  protected Sprite background;
  private int tick = 0;
  private ArrayList<NPC> party = new ArrayList<>();
  
  public Battle() {
    createPartymembers();
  }
  
  private void createPartymembers() {
    NPCConfig config = new NPCConfig();
    NPC npc;
    
    config.setX(partyMemberSlot1.getX());
    config.setY(partyMemberSlot1.getY());
    config.setFixedDirection(true);
    config.setDirection(partyMemberSlot1.getDirection());
    
    npc = new BasicNPC(config, BasicNPC.MALE_GUARD);
    party.add(npc);
    
    config.setX(partyMemberSlot2.getX());
    config.setY(partyMemberSlot2.getY());
    config.setFixedDirection(true);
    config.setDirection(partyMemberSlot2.getDirection());
    
    npc = new BasicNPC(config, BasicNPC.MALE_DARKBLUEHAIR);
    party.add(npc);
    
    config.setX(partyMemberSlot3.getX());
    config.setY(partyMemberSlot3.getY());
    config.setFixedDirection(true);
    config.setDirection(partyMemberSlot3.getDirection());
    
    npc = new BasicNPC(config, BasicNPC.FEMALE_CATLADY);
    party.add(npc);
  }
  
  private void endBattle() {
    Location lastLocation = LocationManager.getLocationManager().getCurrentLocation();
    ViewManager.getViewManager().changeView(new LevelView(LevelFactory.createLevel(lastLocation.getLevelId(), lastLocation.getCoordinate())));
  }
  
  public void update() {
    if (tick++ == 600) endBattle();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 0, 0, background, false);
    party.forEach(partyMember -> partyMember.render(screen, gl));
  }

}
