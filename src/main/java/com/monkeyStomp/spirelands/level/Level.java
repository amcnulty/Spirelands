package com.monkeyStomp.spirelands.level;

import com.monkeyStomp.spirelands.graphics.Font;
import com.monkeyStomp.spirelands.level.entity.mob.Player;
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
  
  protected Font font = new Font();

  protected Thread loadingThread = new Thread(this, "Tile Loader");
  protected int[] bitmap;
  protected ArrayList<Integer> uniqueTiles = new ArrayList<>();
  protected ArrayList<Tile> tiles = new ArrayList<>();
  private int levelTileWidth,
              levelTileHeight,
              xScroll,
              yScroll;
  private String path;
  protected Player player;

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
            Tile.TILE_SIZE,
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
  
  public Tile getTile(int x, int y) {
    if (x < 0 || y < 0 || x >= levelTileWidth || y >= levelTileHeight) return Tile.VOID_TILE;
    return tiles.get(x + y * getLevelTileWidth());
  }
  
  protected int getLevelTileWidth() {
    return levelTileWidth;
  }
  
  protected int getLevelPixelWidth() {
    return levelTileWidth * Tile.TILE_SIZE;
  }
  
  protected void setLevelTileWidth(int width) {
    this.levelTileWidth = width;
  }
  
  protected int getLevelTileHeight() {
    return levelTileHeight;
  }
  
  protected int getLevelPixelHeight() {
    return levelTileHeight * Tile.TILE_SIZE;
  }
  
  protected void setLevelTileHeight(int height) {
    this.levelTileHeight = height;
  }
  
  private void setScreenOffset(Screen screen) {
    xScroll = player.getX() - Screen.getWidth() / 2;
    yScroll = player.getY() - Screen.getHeight() / 2;
    if (Screen.getWidth() < getLevelPixelWidth()) {
      if (player.getX() <= Screen.getWidth() / 2) xScroll = 0;
      else if (player.getX() >= getLevelPixelWidth() - Screen.getWidth() / 2) xScroll = getLevelPixelWidth() - Screen.getWidth();
    }
    if (Screen.getHeight() < getLevelPixelHeight()) {
      if (player.getY() <= Screen.getHeight() / 2) yScroll = 0;
      else if (player.getY() >= getLevelPixelHeight() - Screen.getHeight() / 2) yScroll = getLevelPixelHeight() - Screen.getHeight();
    }
    screen.setOffset(xScroll, yScroll);
  }
  
  public void update(){
    if (!loadingThread.isAlive()) {
      player.update();
    }
  }
  
  public void render(Screen screen){
    if (!loadingThread.isAlive()) {
      // Set screen offset
      setScreenOffset(screen);
      // Render the tiles.
      for (int y = yScroll >> 6; y < Screen.getHeight() + yScroll + Tile.TILE_SIZE >> 6; y++) {
        for (int x = xScroll >> 6; x < Screen.getWidth() + xScroll + Tile.TILE_SIZE >> 6; x++) {
          getTile(x, y).render(x, y, screen);
        }
      }
      // Render the player.
      player.render(screen);
    }
  }
}
