package com.monkeystomp.spirelands.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Keyboard implements KeyListener {
  
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
  
  private static boolean[] keys = new boolean[120];

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  public static boolean isKeyPressed(int keyCode) {
    return keys[keyCode];
  }
}
