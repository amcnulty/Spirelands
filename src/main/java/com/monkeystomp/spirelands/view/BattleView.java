package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.battle.Battle;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Keyboard;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleView extends GameView {
  
  private final Battle battle;
  private boolean gamePaused = false;
  private final PauseMenu pauseMenu = new PauseMenu();
  private final Consumer<KeyEvent> IKeyListener = e -> handleKeyPress(e);
  private final ICallback pauseCallback = () -> pauseLevel();
  
  public BattleView(Battle battle) {
    this.battle = battle;
    this.battle.init();
    setupNotifiers();
  }
  
  private void setupNotifiers() {
    Keyboard.getKeyboard().addKeyListener(IKeyListener);
    battle.setPauseCommand(pauseCallback);
    pauseMenu.setCloseCommand(() -> resumeLevel());
    pauseMenu.setQuitToMenuCommand(() -> ViewManager.getViewManager().changeView(new TitleScreen()));
  }
  
  private void handleKeyPress(KeyEvent e) {
    if (e.getKeyCode() == Keyboard.ESCAPE_KEY) {
      if (gamePaused) resumeLevel();
      else pauseLevel();
    }
  }
  
  private void pauseLevel() {
    pauseMenu.openMenu();
    pauseMenu.useBattleHomeView();
    Music.getMusicPlayer().pause();
    gamePaused = true;
  }
  
  private void resumeLevel() {
    pauseMenu.closeMenu();
    Music.getMusicPlayer().resume();
    gamePaused = false;
  }
  
  @Override
  public void leaveView() {
    Keyboard.getKeyboard().removeKeyListener(IKeyListener);
  }
  
  @Override
  public void update() {
    if (!gamePaused) {
      battle.update();
    }
    else {
      pauseMenu.update();
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    battle.render(screen, gl);
    if (gamePaused) {
      pauseMenu.render(screen, gl);
    }
  }
}
