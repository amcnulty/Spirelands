package com.monkeystomp.spirelands.input;

import java.util.ArrayList;
import java.util.function.Consumer;
import net.java.games.input.Controller;
import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameController {
  
  private Controller gamePad;
  private static final GameController INSTANCE = new GameController();
  private int xAxis = 0,
              yAxis = 0,
              x = 0,
              prevX = 0,
              start = 0,
              r1 = 0;
  private long  lastTime = 0,
                debounceTime = 1000000000;
  private ArrayList<Consumer<Event>> listeners = new ArrayList<>();
  
  private GameController() {
    for (Controller controller: ControllerEnvironment.getDefaultEnvironment().getControllers()) {
      if (controller.getType() == Controller.Type.STICK) {
        gamePad = controller;
      }
    }
  }
  
  public static GameController getGameController() {
    return INSTANCE;
  }
  
  public void addControllerListener(Consumer<Event> listener) {
    listeners.add(listener);
  }
  
  public void callListeners(Event event) {
    if (event.getNanos() > lastTime + debounceTime) {
      for (Consumer<Event> listener: listeners) {
        listener.accept(event);
      }
      lastTime = System.nanoTime();
    }
  }
  
  public int getXAxis() {
    return xAxis;
  }
  
  public int getYAxis() {
    return yAxis;
  }
  
  public int getX() {
    return x;
  }
  
  public int getStart() {
    return start;
  }
  
  public int getR1() {
    return r1;
  }
  
  public void update() {
    gamePad.poll();
    for (Component component: gamePad.getComponents()) {
      if (component.getName().equals("Button 5")) r1 = Math.abs(component.getPollData() - component.getDeadZone()) > .5f ? (int)Math.ceil(component.getPollData()) : 0;
      if (component.getName().equals("Button 1")) x = Math.abs(component.getPollData() - component.getDeadZone()) > .5f ? (int)Math.ceil(component.getPollData()) : 0;
      if (component.getName().equals("Button 9")) start = Math.abs(component.getPollData() - component.getDeadZone()) > .5f ? (int)Math.ceil(component.getPollData()) : 0;
      if (Math.abs(component.getPollData() - component.getDeadZone()) > .5f) {
//        System.out.println(component.getName());
        if (component.getName().equals("Button 5") && r1 == 1) {
          Event event = new Event();
          event.set(component, 1f, System.nanoTime());
          callListeners(event);
        }
        else if (component.getName().equals("Button 1") && x ==1 ) {
          Event event = new Event();
          event.set(component, 1f, System.nanoTime());
          callListeners(event);
        }
        else if (component.getName().equals("Button 9") && start == 1) {
          Event event = new Event();
          event.set(component, 1f, System.nanoTime());
          callListeners(event);
        }
        else if (component.getName().equals("X Axis")) {
          xAxis = component.getPollData() > .5f ? 1 : -1;
        }
        else if (component.getName().equals("Y Axis")) {
          yAxis = component.getPollData() > .5f ? 1 : -1;
        }
      }
      else {
        if (component.getName().equals("X Axis")) xAxis = 0;
        else if (component.getName().equals("Y Axis")) yAxis = 0;
      }
    }
  }

}
