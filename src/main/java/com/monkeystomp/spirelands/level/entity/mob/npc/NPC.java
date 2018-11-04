package com.monkeystomp.spirelands.level.entity.mob.npc;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.mob.Mob;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class NPC extends Mob {
  
  private int routeX,
              routeY,
              delayUntilNextPoint;
  private boolean movingToPoint = false,
                  atDestination = false;
  private NPCConfig config;
  
  protected String[] messages;
  protected String currentAction = "STANDING_2";
  protected int spriteSize,
                anim = 0;
  protected HashMap<String, Sprite> characterActions = new HashMap<>();
  protected Random random = new Random();
  protected boolean fixedDirection,
                    speaking = false;

  public NPC(NPCConfig config) {
    this.config = config;
    this.x = config.getX();
    this.y = config.getY();
    this.direction = config.getDirection();
    this.fixedDirection = config.isFixedDirection();
    this.messages = config.getMessages();
  }
  
  public void speak() {
    if (messages != null) {
      level.getDialogBox().setCloseCommand(() -> finishSpeaking());
      level.getDialogBox().openDialog(messages);
      level.setDialogOpen(true);
      speaking = true;
    }
  }
  
  private void finishSpeaking() {
    speaking = false;
    level.setDialogOpen(false);
  }
  
  public void startAtRandomRoutePoint() {
    int randomIndex = random.nextInt(config.getNextRoutePoint().length * 2);
    Integer[] points;
    for (int i = 0; i < randomIndex; i++) {
      if (i == randomIndex - 1) {
        points = config.getNextRoutePoint();
        x = points[0];
        y = points[1];
        delayUntilNextPoint = points[2];
      }
      else config.getNextRoutePoint();
    }
    moveAlongRoute();
  }
  
  public void moveAlongRoute() {
    Integer[] routeInfo = config.getNextRoutePoint();
    routeX = routeInfo[0];
    routeY = routeInfo[1];
    delayUntilNextPoint = routeInfo[2] * 60;
    movingToPoint = true;
  }
  
  private void moveToPoint() {
    if (atDestination) {
      if (--delayUntilNextPoint == 0) {
        moveAlongRoute();
        atDestination = false;
      }
    }
    else {
      if (routeX == x && routeY == y) {
        atDestination = true;
        walking = false;
        return;
      }
      int xDir = (routeX < x) ? -1 : 1;
      if (routeX == x) xDir = 0;
      int yDir = (routeY < y) ? -1 : 1;
      if (routeY == y) yDir = 0;
      move(xDir, yDir, moveBounds);
    }
  }
  
  protected void setCurrentAction() {}
  
  protected void setBounds() {}
  
  @Override
  public void update() {
    if (!fixedDirection && !speaking) {
      if (movingToPoint) {
        moveToPoint();
      }
      setCurrentAction();
      setBounds();
      anim++;
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - spriteSize / 2, y - spriteSize / 2, characterActions.get(currentAction), true);
  }
}