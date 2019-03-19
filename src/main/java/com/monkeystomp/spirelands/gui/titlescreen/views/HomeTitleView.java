package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataManager;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.level.TestLevel;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.view.LevelView;
import java.util.function.Consumer;
import org.json.simple.JSONObject;

/**
 *
 * @author Aaron Michael McNulty
 */
public class HomeTitleView extends TitleView {
  
  private final Sprite buttonBackdrop = new Sprite(120, 100, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final PrimaryButton newGameButton,
                              loadGameButton,
                              settingsButton,
                              exitButton;
  private final int spaceBetweenButtons = 12;
  
  public HomeTitleView(Consumer<LevelView> ILevelViewSetter, Consumer<TitleView> ITitleViewSetter, Consumer<Float> IVolumeSetter) {
    super(ILevelViewSetter, ITitleViewSetter, IVolumeSetter);
    newGameButton = new PrimaryButton("New Game", Screen.getWidth() / 2, Screen.getHeight() / 2 - 29, 60, 15, () -> handleStartButtonClick());
    loadGameButton = new PrimaryButton("Load Game", Screen.getWidth() / 2, newGameButton.getBottom() + spaceBetweenButtons, 60, 15, () -> handleLoadClick());
    settingsButton = new PrimaryButton("Settings", Screen.getWidth() / 2, loadGameButton.getBottom() + spaceBetweenButtons, 60, 15, () -> handleSettingsClick());
    exitButton = new PrimaryButton("Exit", Screen.getWidth() / 2, settingsButton.getBottom() + spaceBetweenButtons, 60, 15, () -> handleExitClick());
  }

  private void handleStartButtonClick() {
    // All required setup for starting a new game...
    SaveDataManager.getSaveDataManager().initSaveObject();
    CharacterManager.getCharacterManager().setupCharactersDetails((JSONObject)SaveDataManager.getSaveDataManager().getSaveObject().get(JSONUtil.CHARACTERS));
    InventoryManager.getInventoryManager().setInventoryData((JSONObject)SaveDataManager.getSaveDataManager().getSaveObject().get(JSONUtil.INVENTORY));
//    viewManager.setCurrentView(new LevelView(LevelFactory.createLevel("SPAWN_LEVEL", new SpawnCoordinate(550, 250, 2))));
// left of house
//    viewManager.changeView(new LevelView(LevelFactory.createLevel("TEST_LEVEL", new SpawnCoordinate(75, 425, 2))));
// top left corner
    ILevelViewSetter.accept(new LevelView(LevelFactory.createLevel(TestLevel.LEVEL_ID, new SpawnCoordinate(100, 100, 2))));
// inside house
//    viewManager.changeView(new LevelView(LevelFactory.createLevel("HOUSE_LEVEL", HouseLevel.FIRST_FLOOR_ENTRANCE)));
  }
  
  private void handleLoadClick() {
    ITitleViewSetter.accept(new LoadGameView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  private void handleSettingsClick() {
    ITitleViewSetter.accept(new SettingsView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  private void handleExitClick() {
    System.exit(0);
  }

  @Override
  public void enteringView() {
  }
  
  @Override
  public void exitingView() {}
  
  @Override
  public void update() {
    newGameButton.update();
    loadGameButton.update();
    settingsButton.update();
    exitButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - buttonBackdrop.getWidth() / 2, Screen.getHeight() / 2 - buttonBackdrop.getHeight() / 2, buttonBackdrop, false);
    newGameButton.render(screen, gl);
    loadGameButton.render(screen, gl);
    settingsButton.render(screen, gl);
    exitButton.render(screen, gl);
  }
  

}
