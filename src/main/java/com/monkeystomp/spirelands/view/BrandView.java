package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BrandView extends GameView {
  
  private Sprite foot;
  private float scale = 1.5f,
                step = .5f / 20;
  private int delay = 0;
  private boolean animating = false;
  private SoundEffects SFX = new SoundEffects();

  public BrandView() {
    try {
      BufferedImage footImg = ImageIO.read(BrandView.class.getResource("/intro/monkey_foot.png"));
      int[] pixels = new int[468 * 593];
      footImg.getRGB(0, 0, 468, 593, pixels, 0, 468);
      foot = new Sprite(new Sprite(pixels, 468, 593), 100);
    } catch (IOException ex) {
      Logger.getLogger(BrandView.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @Override
  public void update() {
    if (delay++ > 120 && !animating) {
      animating = true;
      if (delay == 122) SFX.playSoundEffect(SoundEffects.MONKEY_STOMP_SOUND);
    }
    if (animating) {
      scale -= step;
      if (scale <= 1) {
        scale = 1;
        animating = false;
      }
    }
    if (delay == 300) (new TitleScreen()).setView();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderBlackBackground(gl);
    if (animating || delay > 120) screen.renderSprite(gl, Screen.getWidth() / 2 - foot.getWidth() / 2, Screen.getHeight() / 2 - foot.getHeight() / 2, foot, 1, false, scale);
  }
}
