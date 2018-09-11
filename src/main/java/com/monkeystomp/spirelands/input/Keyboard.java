package com.monkeystomp.spirelands.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Keyboard implements KeyListener {

  private static Keyboard instance = new Keyboard();
  public INotify pauseNotifier;
  private ArrayList<INotify> keyPressedNotifiers = new ArrayList<>();

  private Keyboard() {}

  public static Keyboard getKeyboard() {
    return instance;
  }
  
  public static final int W_KEY = 87,
                          A_KEY = 65,
                          S_KEY = 83,
                          D_KEY = 68,
                          UP_KEY = 38,
                          LEFT_KEY = 37,
                          DOWN_KEY = 40,
                          RIGHT_KEY = 39,
                          SPACE_KEY = 32,
                          LEFT_SHIFT_KEY = 16,
                          ESCAPE_KEY = 27;
  
  private static boolean[] keys = new boolean[220];

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
    callKeyPressedNotifiers(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
    if (e.getKeyCode() == 27) {
      pauseNotifier.notify(e);
    }
  }

  public static boolean isKeyPressed(int keyCode) {
    return keys[keyCode];
  }

  public void addKeyPressNotifier(INotify notifier) {
    keyPressedNotifiers.add(notifier);
  }

  public void removeKeyPressNotifier(INotify notifier) {
    keyPressedNotifiers.remove(notifier);
  }

  private void callKeyPressedNotifiers(KeyEvent e) {
    for (int i = 0; i < keyPressedNotifiers.size(); i++) {
      keyPressedNotifiers.get(i).notify(e);
    }
  }
}