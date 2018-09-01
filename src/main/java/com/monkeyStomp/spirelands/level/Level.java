package com.monkeyStomp.spirelands.level;

import com.monkeystomp.spirelands.graphics.Screen;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Level {

  protected int[] tiles;
  protected ArrayList<Integer> uniqueTiles = new ArrayList<>();
  private int levelTileWidth,
              levelTileHeight;

  public Level(String path) {
    loadLevel(path);
    generateLevel();
  }
  
  protected void loadLevel(String path){};
  
  protected void generateLevel(){};
  
  protected int getLevelTileWidth() {
    return levelTileWidth;
  }
  
  protected void setLevelTileWidth(int width) {
    this.levelTileWidth = width;
  }
  
  protected int getLevelTileHeight() {
    return levelTileHeight;
  }
  
  protected void setLevelTileHeight(int height) {
    this.levelTileHeight = height;
  }
  
  public void update(){};
  
  public void render(Screen screen){};
}
