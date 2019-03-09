package com.monkeystomp.spirelands.gui.pausemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.pausemenu.views.ConfirmExitView;
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
  
  private final Consumer<String> IPauseViewSetter = view -> handleViewChange(view);
  private final PauseView homeView = new HomeView(IPauseViewSetter),
                          saveView = new SaveView(IPauseViewSetter),
                          confirmExitView = new ConfirmExitView(IPauseViewSetter);
  private final HashMap<String, PauseView> pauseViewMap = new HashMap<>();
  private PauseView currentView = new HomeView(IPauseViewSetter);
  private final SoundEffects sfx = new SoundEffects();

  public PauseMenu() {
    pauseViewMap.put(HOME_VIEW, homeView);
    pauseViewMap.put(SAVE_VIEW, saveView);
    pauseViewMap.put(CONFIRM_EXIT_VIEW, confirmExitView);
  }
  /**
   * Sets the callback method that fires when the pause menu gets closed.
   * @param callback The callback method that will get fired when the dialog closes.
   */
  public void setCloseCommand(ICallback callback) {
    homeView.setCloseCommand(callback);
  }
  /**
   * Sets the callback method that fires when the user presses quit to main menu.
   * @param callback The callback method that will get fired when the quit to main menu button is pressed.
   */
  public void setQuitToMenuCommand(ICallback callback) {
    homeView.setQuitToMenuCommand(callback);
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
