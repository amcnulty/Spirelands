package com.monkeyStomp.spirelands.level.entity.mob.npc;

/**
 *
 * @author Aaron Michael McNulty
 */
public class NPCConfig {
  
  private int x,
              y,
              direction = 2;
  private boolean fixedDirection = false;
  private String[] messages;

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

  public boolean isFixedDirection() {
    return fixedDirection;
  }

  public void setFixedDirection(boolean fixedDirection) {
    this.fixedDirection = fixedDirection;
  }

  public String[] getMessages() {
    return messages;
  }

  public void setMessages(String[] messages) {
    this.messages = messages;
  }
}