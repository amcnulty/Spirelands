package com.monkeystomp.spirelands.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Keyboard implements KeyListener {

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("Key Pressed: " + e.getKeyChar());
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

}
