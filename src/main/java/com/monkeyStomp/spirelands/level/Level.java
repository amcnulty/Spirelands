package com.monkeyStomp.spirelands.level;

import com.monkeyStomp.spirelands.level.tile.Tile;
import com.monkeyStomp.spirelands.level.tile.TileData;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Level implements Runnable {

  private Thread loadingThread = new Thread(this, "Tile Loader");
  protected int[] bitmap;
  protected ArrayList<Integer> uniqueTiles = new ArrayList<>();
  protected ArrayList<Tile> tiles = new ArrayList<>();
  private int levelTileWidth,
              levelTileHeight;
  private String path;

  public Level(String path) {
    this.path = path;
    loadLevel();
  }
  /**
   * Begins the level loading process on a separate thread.
   */
  private void loadLevel(){
    loadingThread.start();
  }

  @Override
  public void run() {
    loadBitmap(path);
    createTiles();
    generateLevel();
  }
  
  private void loadBitmap(String path) {
    try {
      BufferedImage image = ImageIO.read(new File(path));
      setLevelTileWidth(image.getWidth());
      setLevelTileHeight(image.getHeight());
      bitmap = new int[getLevelTileWidth() * getLevelTileHeight()];
      image.getRGB(0, 0, getLevelTileWidth(), getLevelTileHeight(), bitmap, 0, getLevelTileWidth());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could not upload file at path: " + path);
    }
  }
  
  private void createTiles() {
    for (int i = 0; i < bitmap.length; i++) {
      tiles.add(
        new Tile(
          new Sprite(
            64,
            (int) TileData.library.get(bitmap[i]).get(0),
            (int) TileData.library.get(bitmap[i]).get(1),
            (SpriteSheet) TileData.library.get(bitmap[i]).get(2)
          ),
          (boolean) TileData.library.get(bitmap[i]).get(3)
        )
      );
    }
  }
  
  protected void generateLevel(){
  }
  
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
  
  public void update(){}
  
  public void render(Screen screen){
    if (!loadingThread.isAlive()) {
      for (int y = 0; y < Screen.getHeight() + 64 >> 6; y++) {
        for (int x = 0; x < Screen.getWidth() + 64 >> 6; x++) {
          tiles.get(x + y * getLevelTileWidth()).render(x, y, screen);
        }
      }
    }
  }
}
