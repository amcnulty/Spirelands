package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TitleScreen extends GameView {

  private Button startButton;
  private Music music = new Music();
  
//  private LevelFactory levelFactory = new LevelFactory();
  
  public TitleScreen() {
    loadAssets();
    startMusic();
  }
  
  private void loadAssets() {
    // Using this to set the button temporarily
    startButton = new PrimaryButton("Start Game", Screen.getWidth() / 2, Screen.getHeight() / 2, 50, 25, () -> handleStartButtonClick());
  }
  
  private void startMusic() {
    music.play(Music.TITLE_MUSIC);
  }
  
  private void stopMusic() {
    music.stop();
  }

  private void handleStartButtonClick() {
//    viewManager.setCurrentView(new LevelView(LevelFactory.createLevel("SPAWN_LEVEL", new SpawnCoordinate(550, 250, 2))));
    viewManager.changeView(new LevelView(LevelFactory.createLevel("TEST_LEVEL", new SpawnCoordinate(128, 128, 0))));
  }
  
  @Override
  public void leaveView() {
    stopMusic();
  }
  
  @Override
  public void update() {
    startButton.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderTitleScreenBackground(gl);
    startButton.render(screen, gl);
//    screen.renderColor(0xff0000);
  }
}