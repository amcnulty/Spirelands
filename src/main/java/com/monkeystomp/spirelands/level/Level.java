package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.Battle;
import com.monkeystomp.spirelands.level.util.ILevelChanger;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataManager;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.tile.Tile;
import com.monkeystomp.spirelands.level.tile.TileData;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.dialog.DialogBox;
import com.monkeystomp.spirelands.gui.gamemenu.GameMenu;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.fixed.Chest;
import com.monkeystomp.spirelands.level.entity.mob.Player;
import com.monkeystomp.spirelands.level.lightmap.LightMap;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.level.util.TransitionFader;
import com.monkeystomp.spirelands.view.BattleView;
import com.monkeystomp.spirelands.view.ViewManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Level implements Runnable {
  /**
   * Infrequent random battle encounters.
   */
  public static final int LIGHT_ENCOUNTERS = 2400;
  /**
   * Normal amount of random battle encounters.
   */
  public static final int NORMAL_ENCOUNTERS = 1200;
  /**
   * Frequent random battle encounters.
   */
  public static final int HEAVY_ENCOUNTERS = 600;
  /**
   * The display name of the level.
   */
  protected String levelName;
  /**
   * The id of the level.
   */
  protected String levelId;
  /**
   * Flag for checking if this level has random battles.
   */
  protected boolean hasBattles = false;
  /**
   * Sets the average battle encounter rate for this level.
   */
  protected int encounterRate;
  private String path;
  protected Thread loadingThread = new Thread(this, "Tile Loader");
  protected int[] bitmap;
  protected Battle battle;
  protected DialogBox dialogBox = new DialogBox();
  protected ArrayList<Integer> uniqueTiles = new ArrayList<>();
  protected ArrayList<Tile> tiles = new ArrayList<>();
  // LightMap Manager
  protected LightMap lightMap = new LightMap();
  // Entities
  protected ArrayList<Portal> portals = new ArrayList<>();
  // Solid Entities
  protected ArrayList<Entity> solidEntities = new ArrayList<>();
  // Chests
  protected List<Entity> chests;
  protected SpawnCoordinate spawnCoordinate;
  private int levelTileWidth,
              levelTileHeight,
              xScroll,
              yScroll,
              ticksSinceLastBattle = 0,
              randomEncounterModifier;
  private Random random = new Random();
  private final TransitionFader transitionFader = new TransitionFader();
  protected Player player;
  protected float shadowLevel;
  private boolean dialogOpen = false,
                  isPortalSet = false,
                  gameMenuOpen = false;
  private Portal exitPortal;
  private final GameMenu GAME_MENU = new GameMenu(() -> closeGameMenu());
  private final Keyboard keyboard = Keyboard.getKeyboard();
  private final Consumer<KeyEvent> keyListener = e -> handleKeypress(e);
  private final ArrayList<Tile> textureData = new ArrayList<>();
  private final ArrayList<Float>  xFloat = new ArrayList<>(),
                                  yFloat = new ArrayList<>();

  private ILevelChanger IChanger;

  public Level() {
    dialogBox.setCloseCommand(() -> dialogOpen = false);
    keyboard.addKeyListener(keyListener);
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
    loadingThread = new Thread(this, "Level Loader");
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
          TileData.library.get(bitmap[i]).isSolid(),
          TileData.library.get(bitmap[i]).getX(),
          TileData.library.get(bitmap[i]).getY()
        )
      );
    }
  }
  
  protected void generateLevel(){
    setLevelName(GAME_MENU);
    addPortals();
    // Additional hooks can be added here eg. addNPCs() | addChests()
    addChests();
    chests = solidEntities.stream().filter(entity -> entity.getClass() == Chest.class)
            .collect(Collectors.<Entity>toList());
    addNpcs();
    addSolidEntities();
    addPlayer();
    startMusic();
    finalLevelSetup();
    setChestState();
    transitionFader.startTransitionIn();
  }

  protected void addPlayer() {
    player = new Player(spawnCoordinate.getX(), spawnCoordinate.getY());
    player.setDirection(spawnCoordinate.getDirection());
    player.initLevel(this);
    solidEntities.add(player);
  }
  
  protected void setLevelName(GameMenu menu) {}

  protected void addPortals() {}
  
  protected void addChests() {}
  
  protected void addNpcs() {}
  
  protected void addSolidEntities() {}

  protected void startMusic() {}

  protected void finalLevelSetup() {}
  
  private void setChestState() {
    boolean[] openedChests = SaveDataManager.getSaveDataManager().getChests(levelId);
    if (openedChests != null) {
      for (int i = 0; i < openedChests.length; i++) {
        Chest chest = (Chest) chests.get(i);
        chest.setChestOpen(openedChests[i]);
      }
    }
  }
  
  private void openGameMenu() {
    GAME_MENU.openMenu();
    gameMenuOpen = true;
  }

  private void closeGameMenu() {
    GAME_MENU.closeMenu();
    gameMenuOpen = false;
  }
  /**
   * Saves the current state of the level to the save data manager.
   */
  public void saveLevelState() {
    savePlayerLocation();
    saveChestState();
  }
  
  private void savePlayerLocation() {
    LocationManager.getLocationManager().setCurrentLocation(
      new Location(
        new SpawnCoordinate(
          player.getX(),
          player.getY(),
          player.getDirection()
        ),
        levelName,
        levelId
      )
    );
  }
  
  private void saveChestState() {
    boolean[] chestData = new boolean[chests.size()];
    for (int i = 0; i < chestData.length; i++) {
      Chest chest = (Chest) chests.get(i);
      chestData[i] = chest.isChestOpen();
    }
    SaveDataManager.getSaveDataManager().setChests(levelId, chestData);
  }

  public ArrayList<Portal> getPortals() {
    return portals;
  }
  
  public ArrayList<Entity> getSolidEntities() {
    return solidEntities;
  }

  public String getLevelName() {
    return levelName;
  }

  public String getLevelId() {
    return levelId;
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
  /**
   * Starts the transition fader when switch between levels. Use this when staying on LevelView but only changing levels.
   */
  public void transitionOutOfLevel() {
    transitionFader.startTransitionOut(() -> {exitLevel();});
  }
  /**
   * Cleans up everything before leaving this level. Called by the view manager. Do not call this method directly for changing levels.
   */
  public void exitLevel() {
    keyboard.removeKeyListener(keyListener);
    player.destroyPlayer();
    saveLevelState();
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
  
  private void checkForBattle() {
    if (player.isWalking()) {
      if (ticksSinceLastBattle == 0) {
        encounterRate += (int)(encounterRate * .3);
        encounterRate -= 2 * random.nextInt((int)(encounterRate * .3));
      }
      if (ticksSinceLastBattle++ == encounterRate) ViewManager.getViewManager().changeView(new BattleView(battle));
    }
  }
  
  private void sortSolidEntities() {
    Collections.sort(solidEntities, (Entity a, Entity b) -> (a.getOverlapY() > b.getOverlapY()) ? 1 : a.getOverlapY() < b.getOverlapY() ? -1 : 0);
  }
  
  protected void levelUpdate() {}
  
  protected void renderOverSolidEntities(Screen screen, GL2 gl) {}
  
  protected void renderUnderSolidEntities(Screen screen, GL2 gl) {}
  
  protected void levelRenderOverLightMap(Screen screen, GL2 gl) {}
  
  public void update(){
    if (!loadingThread.isAlive()) {
      // Update player if dialog is closed.
      if (!dialogOpen && !gameMenuOpen && !transitionFader.isTransitionRunning()) {
        player.update();
        if (hasBattles) checkForBattle();
      }
      // Call the subclass hook for updating.
      levelUpdate();
      // Sort the solid entities
      sortSolidEntities();
      // Update the solid entities
      for (int i = 0; i < solidEntities.size(); i++) {
        if (solidEntities.get(i).equals(player)) continue;
        solidEntities.get(i).update();
      }
      if (gameMenuOpen) GAME_MENU.update();
      transitionFader.update();
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
          textureData.add(getTile(x, y));
          xFloat.add((float)(x << 4));
          yFloat.add((float)(y << 4));
        }
      }
      screen.renderTile(textureData, xFloat, yFloat, gl);
      // Call the subclass hook for rendering under solid entities.
      renderUnderSolidEntities(screen, gl);
      // Render the solid entities
      for (int i = 0; i < solidEntities.size(); i++) {
        solidEntities.get(i).render(screen, gl);
      }
      // Call the subclass hook for rendering over the solid entities.
      renderOverSolidEntities(screen, gl);
      // Render the lightmap.
      lightMap.render(gl, screen, shadowLevel);
      // Call the subclass hook for rendering over the light map.
      levelRenderOverLightMap(screen, gl);
      if (gameMenuOpen) GAME_MENU.render(screen, gl);
      transitionFader.render(screen, gl);
    }
  }
}
