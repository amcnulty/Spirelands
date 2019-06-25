package com.monkeystomp.spirelands.battle.victory;

import com.monkeystomp.spirelands.character.Character;
import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Aaron Michael McNulty
 */
public class AnimatedExpBar {
  
  private final FontInfo levelInfo = GameFonts.getDarkText_22();
  private boolean animating = false;
  private final Sprite emptyBar = new Sprite(120, 10, GameColors.EXP_GAUGE_EMPTY);
  private Sprite fullBar;
  private final int x, y;
  private int expGained;
  private double tick = 0;
  private final Character dummyCharacter = new Character();
  
  public AnimatedExpBar(int x, int y, int expGained, Character character) {
    this.x = x;
    this.y = y;
    this.expGained = expGained;
    dummyCharacter.setHealthWeight(Character.VERY_LOW);
    dummyCharacter.setManaWeight(Character.VERY_LOW);
    dummyCharacter.setStrengthWeight(Character.VERY_LOW);
    dummyCharacter.setDefenseWeight(Character.VERY_LOW);
    dummyCharacter.setIntellectWeight(Character.VERY_LOW);
    dummyCharacter.setSpiritWeight(Character.VERY_LOW);
    dummyCharacter.setSpeedWeight(Character.VERY_LOW);
    dummyCharacter.setLuckWeight(Character.VERY_LOW);
    dummyCharacter.setLevel(character.getLevel());
    dummyCharacter.setExperience(character.getExperience());
    tick = dummyCharacter.getExperience();
    setFullBar();
    setFonts();
  }
  
  private void setFullBar() {
    if (dummyCharacter.getExpToNextLevel() / 120 < 1) {
      fullBar = new Sprite((int)(120 * tick / (double)dummyCharacter.getExpToNextLevel()), 10, GameColors.EXP_GAUGE_FILL);
    }
    else fullBar = new Sprite((int)(120 * dummyCharacter.getExperience() / (double)dummyCharacter.getExpToNextLevel()), 10, GameColors.EXP_GAUGE_FILL);
  }
  
  private void setFonts() {
    levelInfo.setText("Level: " + dummyCharacter.getLevel());
    levelInfo.setX(x);
    levelInfo.setY(y + 15);
  }

  public boolean isAnimating() {
    return animating;
  }

  public void setAnimating(boolean animating) {
    this.animating = animating;
  }
  
  private double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();
 
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
  
  private void addExp() {
    if (expGained > 0) {
      if (dummyCharacter.getExpToNextLevel() / 120 < 1) {
        if (round(tick, 2) == dummyCharacter.getExpToNextLevel()) {
          expGained -= dummyCharacter.getExpToNextLevel() - dummyCharacter.getExperience();
          dummyCharacter.addExperiencePoints(dummyCharacter.getExpToNextLevel() - dummyCharacter.getExperience());
          tick = 0;
        }
      }
      else {
        if (expGained - dummyCharacter.getExpToNextLevel() / 120 < 0) {
          dummyCharacter.addExperiencePoints(expGained);
          expGained -= expGained;
        }
        else {
          dummyCharacter.addExperiencePoints(dummyCharacter.getExpToNextLevel() / 120);
          expGained -= dummyCharacter.getExpToNextLevel() / 120;
        }
      }
    }
  }
  
  public void update() {
    if (animating) {
      addExp();
      setFullBar();
      setFonts();
      if (tick > ((expGained + dummyCharacter.getExperience()) / (double)dummyCharacter.getExpToNextLevel()) * dummyCharacter.getExpToNextLevel()) animating = false;
      else tick += dummyCharacter.getExpToNextLevel() / 120.0;
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, emptyBar, false);
    screen.renderFonts(levelInfo);
    if (fullBar.getWidth() > 0) screen.renderSprite(gl, x, y, fullBar, false);
  }

}
