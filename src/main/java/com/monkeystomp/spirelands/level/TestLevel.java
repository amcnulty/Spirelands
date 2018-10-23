package com.monkeystomp.spirelands.level;

import com.monkeystomp.spirelands.level.entity.fixed.StreetLamp;
import com.monkeystomp.spirelands.level.entity.mob.npc.BasicNPC;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPCConfig;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.level.entity.fixed.Chest;
import com.monkeystomp.spirelands.level.entity.particle.Particle;
import com.monkeystomp.spirelands.level.entity.particle.ParticleOverlay;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TestLevel extends Level {

  private final String BITMAP_PATH = "./resources/textures/worlds/testLevel.png";
  private int time = 0,
              shadowLevel = 0;
  private ArrayList<Particle> particles;
  public static SpawnCoordinate westEntrance = new SpawnCoordinate(48, 256, 1);
  
  // NPCs
  private NPCConfig lonelyMaleElf = new NPCConfig();
  
  public TestLevel(SpawnCoordinate coordinate) {
    this.spawnCoordinate = coordinate;
    loadLevel(BITMAP_PATH);
    shadowLevel = 7;
  }
  
  @Override
  protected void addPortals() {
    portals.add(new Portal(0, 224, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    portals.add(new Portal(0, 240, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    portals.add(new Portal(0, 256, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addChests() {
    
  }
  
  @Override
  protected void addNpcs() {
    NPC npc;
    // Lonely Male Elf setup
    lonelyMaleElf.setX(160);
    lonelyMaleElf.setY(160);
    lonelyMaleElf.setRoutePoint(200, 160, 2);
    lonelyMaleElf.setRoutePoint(200, 250, 1);
    lonelyMaleElf.setRoutePoint(190, 130, 4);
    lonelyMaleElf.setRoutePoint(160, 160, 1);
    lonelyMaleElf.setMessages(new String[] {"Hi how are you? I'm all alone up here.. I haven't seen anyone around for quite some time.", "Not many people come this way, so I'm guessing that you're a traveler!", "My wife and I used to be travelers until one day she left on what she said was an important adventure. I haven't seen her since she left several months ago. I'm lonely without her."});
    npc = new BasicNPC(lonelyMaleElf, BasicNPC.MALE_ELF);
    solidEntities.add(npc);
    npc.initLevel(this);
    npc.startAtRandomRoutePoint();
  }

  @Override
  protected void startMusic() {
    music.play(Music.SIMPLE_TOWN);
  }
  
  @Override
  protected void finalLevelSetup() {
    Entity entity = new StreetLamp(210, 75);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(360, 75);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(510, 75);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(605, 150);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(605, 300);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(605, 450);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(530, 570);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new StreetLamp(380, 570);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    entity = new Chest(64, 64, Chest.COMMON_METAL_CHEST, Item.HEALTH_POTION);
    solidEntities.add(entity);
    entity.initLevel(this);
    
    particles = ParticleOverlay.createParticleOverlay(getLevelPixelWidth(), getLevelPixelHeight(), 60, Particle.EMBER);
  }

  @Override
  protected void levelUpdate() {
    if (getDialogOpen()) {
      dialogBox.update();
    }
    // 7 pm
    if (time == 420) shadowLevel = 1;
    // 7:30 pm
    else if (time == 450) shadowLevel = 2;
    // 8 pm
    else if (time == 480) shadowLevel = 3;
    // 8:15 pm
    else if (time == 495) shadowLevel = 4;
    // 8:30 pm
    else if (time == 510) shadowLevel = 5;
    // 8:45 pm
    else if (time == 525) shadowLevel = 6;
    // 9 pm
    else if (time == 540) shadowLevel = 7;
    // 9:30 pm
    else if (time == 570) shadowLevel = 8;
    // 10 pm
    else if (time == 700) shadowLevel = 9;
    // 4 am
    else if (time == 1060) shadowLevel = 8;
    // 4:30 am
    else if (time == 1090) shadowLevel = 7;
    // 5 am
    else if (time == 1120) shadowLevel = 6;
    // 5:15 am
    else if (time == 1135) shadowLevel = 5;
    // 5:30 am
    else if (time == 1150) shadowLevel = 4;
    // 5:45 am
    else if (time == 1165) shadowLevel = 3;
    // 6 am
    else if (time == 1180) shadowLevel = 2;
    // 6:30 am
    else if (time == 1210) shadowLevel = 1;
    // 7 am
    else if (time == 1240) shadowLevel = 0;

    if (time == 1440) {
      time = 0;
//      IChanger.change(new SpawnLevel());
    }
//    else time++;
  }
  
  @Override
  protected void renderOverPlayer(Screen screen) {
    for (int i = 0; i < particles.size(); i++) {
      screen.renderSpriteUpperLevel(particles.get(i).getX(), particles.get(i).getY(), particles.get(i).getSprite(), 4, true, true);
    }
  }
  
  @Override
  protected void renderUnderPlayer(Screen screen) {
    screen.setLightMap(0x121212, shadowLevel);
//    font.renderText(200, 200, "This is a test!", screen);
//    font.renderText(200, 300, "Demo version no. #1234567890", screen);
  }

  @Override
  protected void levelRenderOverLightMap(Screen screen) {
    if (getDialogOpen()) dialogBox.render(screen);
  }
}