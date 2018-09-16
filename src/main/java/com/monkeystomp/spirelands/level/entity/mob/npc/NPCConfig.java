package com.monkeyStomp.spirelands.level.entity.mob.npc;

import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class NPCConfig {
  
  private int x,
              y,
              direction = 2,
              routeIndex = 0;
  private boolean fixedDirection = false;
  private String[] messages;
  private ArrayList<Integer[]> routes = new ArrayList<>();

  public void setRoutePoint(int x, int y, int delay) {
    routes.add(new Integer[]{x, y, delay});
  }
  
  public Integer[] getNextRoutePoint() {
    if (!(routeIndex < routes.size())) {
      routeIndex = 0;
    }
    return routes.get(routeIndex++);
  }

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