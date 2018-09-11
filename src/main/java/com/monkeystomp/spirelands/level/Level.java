package com.monkeystomp.spirelands.level;

import com.monkeystomp.spirelands.graphics.Font;
import com.monkeystomp.spirelands.level.entity.mob.Player;
import com.monkeystomp.spirelands.level.entity.mob.GuardPlayer;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.tile.Tile;
import com.monkeystomp.spirelands.level.tile.TileData;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.dialog.DialogBox;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Level implements Runnable {
  
  protected Font font = new Font();
  private String path;
  protected Thread loadingThread = new Thread(this, "Tile Loader");
  protected int[] bitmap;
  protected DialogBox dialogBox = new DialogBox();
  protected ArrayList<Integer> uniqueTiles = new ArrayList<>();
  protected ArrayList<Tile> tiles = new ArrayList<>();
  protected ArrayList<Portal> portals = new ArrayList<>();
  protected SpawnCoordinate spawnCoordinate;
  private int levelTileWidth,
              levelTileHeight,
              xScroll,
              yScroll;
  protected Player player;
  protected boolean dialogOpen = false;
  private ILevelChanger IChanger;

  public Level() {
    dialogBox.setCloseCommand(() -> dialogOpen = false);
  }

  /**
   * Begins the level loading process on a separate thread.
   */
  protected void loadLevel(String path){
    this.path = path;
    loadingThread.start();
  }

  @Override
  public void run() {
    loadBitmap();
    createTiles();
    generateLevel();
  }

  protected void loadBitmap() {
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
    addPlayer();
    addPortals();
    // Additional hooks can be added here eg. addNPCs() | addChests()
    finalLevelSetup();
  }

  protected void addPlayer() {
    player = new GuardPlayer(spawnCoordinate.getX(), spawnCoordinate.getY());
    player.setDirection(spawnCoordinate.getDirection());
    player.initLevel(this);
  }

  protected void addPortals() {}

  protected void finalLevelSetup() {}

  public ArrayList<Portal> getPortals() {
    return portals;
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

  public void setLevelChanger(ILevelChanger IChanger) {
    this.IChanger = IChanger;
  }
  
  public ILevelChanger getLevelChanger() {
    return IChanger;
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
  
  protected void levelUpdate() {}
  
  protected void levelRender(Screen screen) {}

  protected void levelRenderOverLightMap(Screen screen) {}
  
  public void update(){
    if (!loadingThread.isAlive()) {
      if (!dialogOpen) player.update();
      levelUpdate();
    }
  }
  
  public void render(Screen screen){
    if (!loadingThread.isAlive()) {
      // Set screen offset
      setScreenOffset(screen);
      // Render the tiles.
      for (int y = yScroll >> 4; y < Screen.getHeight() + yScroll + Tile.TILE_SIZE >> 4; y++) {
        for (int x = xScroll >> 4; x < Screen.getWidth() + xScroll + Tile.TILE_SIZE >> 4; x++) {
          getTile(x, y).render(x, y, screen);
        }
      }
      // Render the player.
      player.render(screen);
      // Call the subclass hook for rendering.
      levelRender(screen);
      // Overlay the lightmap on the level.
      screen.overlayLightMap();
      // Call the subclass hook for rendering over the light map.
      levelRenderOverLightMap(screen);
    }
  }
}
