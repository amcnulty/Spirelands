package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.Mouse;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Consumer;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BrandView extends GameView {
  
  private Sprite  foot,
                  impactFoot,
                  currentFoot,
                  monkeyStomp,
                  games;
  private float startingScale = 3f,
                scale = startingScale,
                endingScale = 1f;
  private double  animationDuration = 24.0,
                  animationFrame = 0.0;
  private int ticks = 0,
              animationDelay = 120,
              impactTime = 8,
              totalViewLength = 300;
  private boolean animating = false;
  private final Consumer<MouseEvent> listener = event -> exitView();
  private SoundEffects SFX = new SoundEffects();

  public BrandView() {
    try {
      BufferedImage img = ImageIO.read(BrandView.class.getResource("/intro/monkey_foot.png"));
      int[] pixels = new int[667 * 615];
      img.getRGB(0, 0, 667, 615, pixels, 0, 667);
      foot = new Sprite(new Sprite(pixels, 667, 615), 100);
      img = ImageIO.read(BrandView.class.getResource("/intro/monkey_foot_impact.png"));
      img.getRGB(0, 0, 667, 615, pixels, 0, 667);
      impactFoot = new Sprite(new Sprite(pixels, 667, 615), 100);
      currentFoot = foot;
      
      pixels = new int[6565 * 789];
      img = ImageIO.read(BrandView.class.getResource("/intro/monkey_stomp.png"));
      img.getRGB(0, 0, 6565, 789, pixels, 0, 6565);
      monkeyStomp = new Sprite(new Sprite(pixels, 6565, 789), 6.0);
      
      pixels = new int[2975 * 789];
      img = ImageIO.read(BrandView.class.getResource("/intro/games.png"));
      img.getRGB(0, 0, 2975, 789, pixels, 0, 2975);
      games = new Sprite(new Sprite(pixels, 2975, 789), 6.0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Mouse.getMouse().addMouseClickListener(listener);
  }
  
  private void exitView() {
    (new TitleScreen()).changeView();
  }
  
  private void updateAnimation() {
    if (ticks == animationDelay) {
      animating = true;
      SFX.playSoundEffect(SoundEffects.MONKEY_STOMP_SOUND);
    }
    if (animating) {
      if (animationFrame <= animationDuration) scale = startingScale - ((startingScale - endingScale) * (float)(Math.pow(animationFrame++ / animationDuration, 3.0)));
      else if (ticks == animationDelay + animationFrame) currentFoot = impactFoot;
      else if (ticks == animationDelay + animationFrame + impactTime) currentFoot = foot;
    }
    if (ticks == totalViewLength) {
//      Uncomment to loop beginning animation.
//      scale = startingScale;
//      animationFrame = 0.0;
//      ticks = 0;
//      animating = false;
      exitView();
    }
    ticks++;
  }
  
  @Override
  public void leaveView() {
    Mouse.getMouse().removeMouseClickListener(listener);
  }
  
  @Override
  public void update() {
    updateAnimation();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderBlackBackground(gl);
    if (animating || ticks > 120) screen.renderSprite(gl, Screen.getWidth() / 2 - currentFoot.getWidth() / 2, Screen.getHeight() / 2 - currentFoot.getHeight() / 2, currentFoot, 1, false, scale);
    screen.renderSprite(gl, Screen.getWidth() / 2 - monkeyStomp.getWidth() / 2, 10, monkeyStomp, false);
    screen.renderSprite(gl, Screen.getWidth() / 2 - games.getWidth() / 2, Screen.getHeight() - 50, games, false);
  }
  
}
