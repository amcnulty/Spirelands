package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.level.util.ILevelChanger;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.graphics.Font;
import com.monkeystomp.spirelands.level.entity.mob.Player;
import com.monkeystomp.spirelands.level.entity.mob.GuardPlayer;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.tile.Tile;
import com.monkeystomp.spirelands.level.tile.TileData;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.dialog.DialogBox;
import com.monkeystomp.spirelands.gui.gamemenu.GameMenu;
import com.monkeystomp.spirelands.input.INotify;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.multipart.MultipartEntity;
import com.monkeystomp.spirelands.level.lightmap.LightMap;
import java.awt.event.KeyEvent;
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
  // Music player
  protected Music music = new Music();
  // LightMap Manager
  protected LightMap lightMap = new LightMap();
  // Entities
  protected ArrayList<Portal> portals = new ArrayList<>();
    // Solid Entities
    protected ArrayList<Entity> solidEntities = new ArrayList<>();
    // Multi part entities that have sections rendered over player and under player.
    protected ArrayList<MultipartEntity> multiPartEntities = new ArrayList<>();
  protected SpawnCoordinate spawnCoordinate;
  private int levelTileWidth,
              levelTileHeight,
              xScroll,
              yScroll,
              transitionOpacity = 100;
  protected Player player;
  protected float shadowLevel;
  private boolean dialogOpen = false,
                  isPortalSet = false,
                  gameMenuOpen = false,
                  transitionRunning = true,
                  transitionIn = true;
  private Portal exitPortal;
  private final GameMenu GAME_MENU = new GameMenu();
  private Keyboard keyboard = Keyboard.getKeyboard();
  private INotify notifier = (e) -> handleKeypress(e);
  private ArrayList<Tile> textureData = new ArrayList<>();
  private ArrayList<Float>  xFloat = new ArrayList<>(),
                            yFloat = new ArrayList<>();

  private ILevelChanger IChanger;

  public Level() {
    dialogBox.setCloseCommand(() -> dialogOpen = false);
    keyboard.addKeyPressNotifier(notifier);
  }
  
  public void handleKeypress(KeyEvent e) {
    if (e.getKeyCode() == Keyboard.I_KEY) {
      if (!dialogOpen) {
        if (gameMenuOpen) closeGameMenu();
        else openGameMenu();
      }
    }
  }

  /**
   * Begins the level loading process on a separate thread.
   * 
   * @param path Path to the bitmap resource for the level.
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
    // Update the player one time before starting the transition so they are facing the correct direction.
    player.update();
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
          TileData.library.get(bitmap[i]).isSolid(),
          TileData.library.get(bitmap[i]).getX(),
          TileData.library.get(bitmap[i]).getY()
        )
      );
    }
  }
  
  protected void generateLevel(){
    addPlayer();
    addPortals();
    // Additional hooks can be added here eg. addNPCs() | addChests()
    addChests();
    addNpcs();
    addSolidEntities();
    startMusic();
    finalLevelSetup();
  }

  protected void addPlayer() {
    player = new GuardPlayer(spawnCoordinate.getX(), spawnCoordinate.getY());
    player.setDirection(spawnCoordinate.getDirection());
    player.initLevel(this);
    solidEntities.add(player);
  }

  protected void addPortals() {}
  
  protected void addChests() {}
  
  protected void addNpcs() {}
  
  protected void addSolidEntities() {}

  protected void startMusic() {}

  protected void finalLevelSetup() {}
  
  private void openGameMenu() {
    GAME_MENU.openMenu();
    gameMenuOpen = true;
  }

  private void closeGameMenu() {
    GAME_MENU.closeMenu();
    gameMenuOpen = false;
  }

  public ArrayList<Portal> getPortals() {
    return portals;
  }
  
  public ArrayList<Entity> getSolidEntities() {
    ArrayList<Entity> allSolidEntities = new ArrayList<>();
    for (Entity entity: solidEntities) {
      allSolidEntities.add(entity);
    }
    for (Entity entity: multiPartEntities) {
      allSolidEntities.add(entity);
    }
    return allSolidEntities;
  }
  
  public boolean getDialogOpen() {
    return dialogOpen;
  }
  
  public void setDialogOpen(Boolean bool) {
    dialogOpen = bool;
  }

  public Player getPlayer() {
    return player;
  }
  
  public Music getMusicPlayer() {
    return music;
  }
  
  public DialogBox getDialogBox() {
    return dialogBox;
  }
  
  public LightMap getLightMap() {
    return lightMap;
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
  
  public void setExitPortal(Portal exitPortal) {
    this.exitPortal = exitPortal;
    isPortalSet = true;
  }

  public void setLevelChanger(ILevelChanger IChanger) {
    this.IChanger = IChanger;
  }
  
  public ILevelChanger getLevelChanger() {
    return IChanger;
  }
  
  public void transitionOutOfLevel() {
    transitionIn = false;
    transitionRunning = true;
  }
  
  private void exitLevel() {
    // Stop music playing
    music.stop();
    keyboard.removeKeyPressNotifier(notifier);
    if (isPortalSet) exitPortal.enterPortal();
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
  
  
  protected void renderOverPlayer(Screen screen, GL2 gl) {}
  
  protected void renderUnderPlayer(Screen screen, GL2 gl) {}
  
  protected void levelRenderOverLightMap(Screen screen, GL2 gl) {}
  
  public void update(){
    if (!loadingThread.isAlive()) {
      // Update player if dialog is closed.
      if (!dialogOpen && !gameMenuOpen && !transitionRunning) player.update();
      // Call the subclass hook for updating.
      levelUpdate();
      // Update the solid entities
      for (int i = 0; i < solidEntities.size(); i++) {
        if (solidEntities.get(i).equals(player)) continue;
        solidEntities.get(i).update();
      }
      // Update the multipart entities
      for (int i = 0; i < multiPartEntities.size(); i++) {
        multiPartEntities.get(i).update();
      }
      if (gameMenuOpen) GAME_MENU.update();
      if (transitionRunning) {
        if (transitionIn) {
          transitionOpacity -= 5;
          if (transitionOpacity == 0) transitionRunning = false;
        }
        else {
          transitionOpacity += 10;
          if (transitionOpacity == 100) exitLevel();
        }
      }
    }
  }

  public void render(Screen screen, GL2 gl){
    if (!loadingThread.isAlive()) {
      // Set screen offset
      setScreenOffset(screen);
      // Render the tiles.
      screen.bindTileTex(gl);
      textureData.clear();
      xFloat.clear();
      yFloat.clear();
      for (int y = yScroll >> 4; y < Screen.getHeight() + yScroll + Tile.TILE_SIZE >> 4; y++) {
        for (int x = xScroll >> 4; x < Screen.getWidth() + xScroll + Tile.TILE_SIZE >> 4; x++) {
//          getTile(x, y).render(x, y, screen, gl);
          textureData.add(getTile(x, y));
          xFloat.add((float)(x << 4));
          yFloat.add((float)(y << 4));
        }
      }
      screen.renderTile(textureData, xFloat, yFloat, gl);
      // Render the solid entities
      for (int i = 0; i < solidEntities.size(); i++) {
        if (solidEntities.get(i).equals(player)) continue;
        solidEntities.get(i).render(screen, gl);
      }
      // Render the multipart entites under player.
      for (int i = 0; i < multiPartEntities.size(); i++) {
        multiPartEntities.get(i).renderUnderPlayer(screen, gl);
      }
      // Call the subclass hook for rendering under player.
      renderUnderPlayer(screen, gl);
      // Render the player.
      player.render(screen, gl);
      // Call the subclass hook for rendering over the player.
      renderOverPlayer(screen, gl);
      // Render the multipart entites over player.
      for (int i = 0; i < multiPartEntities.size(); i++) {
        multiPartEntities.get(i).renderOverPlayer(screen, gl);
      }
      // Render the lightmap.
      lightMap.render(gl, screen, shadowLevel);
      // Call the subclass hook for rendering over the light map.
      levelRenderOverLightMap(screen, gl);
      if (gameMenuOpen) GAME_MENU.render(screen, gl);
      if (transitionRunning) screen.renderSprite(gl, 0, 0, Sprite.TRANSITION, transitionOpacity / (float)100, false);
    }
  }
}
