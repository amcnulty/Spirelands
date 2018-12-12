package com.monkeystomp.spirelands.level.scene;

import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Scene {
  
  // Entities
  private ArrayList<Portal> portals = new ArrayList<>();
  // Solid Entities
  private ArrayList<Entity> solidEntities = new ArrayList<>();
  // Lightmap setup
  private ICallback lightMapSetup;
  
  private final String BITMAP_PATH;
  
  public Scene(String bitmapPath) {
    this.BITMAP_PATH = bitmapPath;
  }
  
  public void addPortal(Portal portal) {
    portals.add(portal);
  }
  
  public void addSolidEntity(Entity entity) {
    solidEntities.add(entity);
  }
  
  public void setLightMap(ICallback lightMapSetup) {
    this.lightMapSetup = lightMapSetup;
  }
  
  public ArrayList<Portal> getPortals() {
    return portals;
  }
  
  public ArrayList<Entity> getSolidEntities() {
    return solidEntities;
  }
  
  public String getBitmapPath() {
    return BITMAP_PATH;
  }
  
  public void enableLightMap() {
    lightMapSetup.execute();
  }

}
