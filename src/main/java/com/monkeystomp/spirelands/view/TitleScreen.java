package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.titlescreen.views.HomeTitleView;
import com.monkeystomp.spirelands.gui.titlescreen.views.TitleView;
import java.awt.Color;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TitleScreen extends GameView {

  private final Sprite titleScreenBackground = new Sprite("./resources/backgrounds/title_screen.jpg");
  private final Sprite logo = new Sprite(new Sprite("./resources/logo/spirelands_logo.png"), 25.0);
  private final FontInfo copyright = GameFonts.getPrimaryButtonText();
  private final Music music = new Music();
  private float backgroundX = 0,
              backgroundY = 0,
              deltaX = 0,
              deltaY = 0;
  private TitleView currentView = new HomeTitleView(levelView -> viewManager.changeView(levelView));
  
  public TitleScreen() {
    startMusic();
    copyright.setColor(new Color(GameColors.DARK_TEXT));
    copyright.setText("\u00a9 Monkey Stomp Games 2019");
    copyright.setY(Screen.getHeight() - 5);
    copyright.setX(Screen.getWidth() - 5);
    copyright.rightAlignText();
  }
  
  private void startMusic() {
    music.play(Music.TITLE_MUSIC);
  }
  
  private void stopMusic() {
    music.stop();
  }
  
  private void updateBackgroundCoords() {
    if ((-backgroundX + Screen.getWidth()) > (titleScreenBackground.getWidth() - .2f)) deltaX = .2f;
    else if (backgroundX > -.2f) deltaX = -.2f;
    if ((-backgroundY + Screen.getHeight()) > (titleScreenBackground.getHeight() - .2f)) deltaY = .2f;
    else if (backgroundY > -.2f) deltaY = -.2f;
    backgroundX += deltaX;
    backgroundY += deltaY;
  }
  
  public void changeTitleView(TitleView view) {
    currentView.exitingView();
    currentView = view;
  }
  
  @Override
  public void leaveView() {
    stopMusic();
  }
  
  @Override
  public void update() {
    updateBackgroundCoords();
    currentView.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderTitleScreenBackground(gl, backgroundX, backgroundY, titleScreenBackground);
    screen.renderSprite(gl, 15, 15, logo, false);
    screen.renderFonts(copyright);
    currentView.render(screen, gl);
  }
}
