package com.monkeystomp.spirelands.gui.pausemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.pausemenu.views.BattleHomeView;
import com.monkeystomp.spirelands.gui.pausemenu.views.ConfirmExitView;
import com.monkeystomp.spirelands.gui.pausemenu.views.ConfirmQuitToMenuView;
import com.monkeystomp.spirelands.gui.pausemenu.views.ConfirmSaveView;
import com.monkeystomp.spirelands.gui.pausemenu.views.HomeView;
import com.monkeystomp.spirelands.gui.pausemenu.views.PauseView;
import com.monkeystomp.spirelands.gui.pausemenu.views.SaveView;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.HashMap;
import java.util.function.Consumer;
/**
 * The Pause Menu is displayed when the user pauses the game.
 * @author Aaron Michael McNulty
 */
public class PauseMenu {
  
  /**
   * The home view of the pause menu.
   */
  public static final String HOME_VIEW = "Home View";
  /**
   * The save view of the pause menu.
   */
  public static final String SAVE_VIEW = "Save View";
  /**
   * The confirmation pop up before exiting the game from the pause menu.
   */
  public static final String CONFIRM_EXIT_VIEW = "Confirm Exit";
  /**
   * The confirmation pop up before saving game.
   */
  public static final String CONFIRM_SAVE_VIEW = "Confirm Save";
  /**
   * The confirmation pop up before quitting to the main menu from the pause menu.
   */
  public static final String CONFIRM_QUIT_TO_MENU_VIEW = "Quit To Menu";
  
  private final Consumer<String> IPauseViewSetter = view -> handleViewChange(view);
  private final PauseView homeView = new HomeView(IPauseViewSetter),
                          battleHomeView = new BattleHomeView(IPauseViewSetter),
                          saveView = new SaveView(IPauseViewSetter),
                          confirmExitView = new ConfirmExitView(IPauseViewSetter),
                          confirmSaveView = new ConfirmSaveView(IPauseViewSetter),
                          confirmQuitToMenuView = new ConfirmQuitToMenuView(IPauseViewSetter);
  private final HashMap<String, PauseView> pauseViewMap = new HashMap<>();
  private PauseView currentView = homeView;
  private final SoundEffects sfx = new SoundEffects();

  public PauseMenu() {
    pauseViewMap.put(HOME_VIEW, homeView);
    pauseViewMap.put(SAVE_VIEW, saveView);
    pauseViewMap.put(CONFIRM_EXIT_VIEW, confirmExitView);
    pauseViewMap.put(CONFIRM_SAVE_VIEW, confirmSaveView);
    pauseViewMap.put(CONFIRM_QUIT_TO_MENU_VIEW, confirmQuitToMenuView);
  }
  /**
   * Set the current view to the battle home view. Use this when pausing from the battle view.
   */
  public void useBattleHomeView() {
    currentView = battleHomeView;
    pauseViewMap.replace(HOME_VIEW, battleHomeView);
  }
  /**
   * Sets the callback method that fires when the pause menu gets closed.
   * @param callback The callback method that will get fired when the dialog closes.
   */
  public void setCloseCommand(ICallback callback) {
    homeView.setCloseCommand(callback);
    battleHomeView.setCloseCommand(callback);
  }
  /**
   * Sets the callback method that fires when the user presses quit to main menu.
   * @param callback The callback method that will get fired when the quit to main menu button is pressed.
   */
  public void setQuitToMenuCommand(ICallback callback) {
    confirmQuitToMenuView.setQuitToMenuCommand(callback);
  }
  /**
   * Opens the pause menu.
   */
  public void openMenu() {
    playMenuOpenSound();
  }
  /**
   * Closes the pause menu.
   */
  public void closeMenu() {
    handleViewChange(HOME_VIEW);
    playMenuCloseSound();
  }

  private void playMenuOpenSound() {
    sfx.playSoundEffect(SoundEffects.PAUSE);
  }
  
  private void playMenuCloseSound() {
    sfx.playSoundEffect(SoundEffects.RESUME);
  }
  
  private void handleViewChange(String view) {
    currentView.exitingView();
    currentView = pauseViewMap.get(view);
    currentView.enteringView();
  }
  /**
   * Updates the pause menu.
   */
  public void update() {
    currentView.update();
  }
  /**
   * Renders the pause menu to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    currentView.render(screen, gl);
  }
}
