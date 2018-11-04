package com.monkeystomp.spirelands.level.entity.particle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ProjectileParticle extends Particle {
  
  private double  force,
                  angle;
  private boolean movingDown = false,
                  waiting = false,
                  gravityOn = true;
  private double  animX = 0,
                  animY = 0;
  private int lastY = 0,
              groundLevel,
              reboundCount = 0,
              life = 60;

  public ProjectileParticle(int x, int y, Sprite sprite, int force, int angle) {
    super(x, y, sprite);
    this.force = (double) force;
    this.angle = Math.toRadians((double)angle);
    this.groundLevel = y;
  }
  
  public void setGroundLevel(int groundLevel) {
    this.groundLevel = groundLevel;
  }
  
  public void setGravity(boolean isOn) {
    gravityOn = isOn;
  }
  
  public void setLife(int life) {
    this.life = life;
  }
  
  private void rebound() {
    if (reboundCount > 3) waiting = true;
    animY = 0;
    animX *= .7;
    force *= .5;
    movingDown = false;
    reboundCount++;
  }
  
  @Override
  public void update() {
    if (gravityOn) {
      if (waiting && animY > 30) remove();
      else if (movingDown && y > groundLevel && !waiting) rebound();
      else if (!waiting){
        lastY = y;
        x += (animX / 15) * (force * Math.cos(angle));
        y += (16 * Math.pow((animY / 15), 2.0)) - ((animY / 15) * (force * Math.sin(angle)));
        if (lastY < y) movingDown = true;
      }
    }
    else {
      if (animY == life) remove();
      x += (animX / 15) * (force * Math.cos(angle));
      y += ((animY / 15) * (force * Math.sin(angle)));
    }
      animX++;
      animY++;
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, getSprite(), true);
  }
}