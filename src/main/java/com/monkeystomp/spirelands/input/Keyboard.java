package com.monkeystomp.spirelands.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Keyboard class is used to respond to key events that happen during the game. This is a singleton class and that any other class can get reference to.
 * @author Aaron Michael McNulty
 */
public class Keyboard implements KeyListener {

  private final static Keyboard INSTANCE = new Keyboard();
  private final ArrayList<Consumer<KeyEvent>> keyListeners = new ArrayList<>();

  private Keyboard() {}
  /**
   * Gets the singleton instance of this class.
   * @return The singleton instance of the Keyboard class.
   */
  public static Keyboard getKeyboard() {
    return INSTANCE;
  }
  /**
   * The 1 key
   */
  public static final int NUMBER_ROW_1 = 49;
  /**
   * The 2 key
   */
  public static final int NUMBER_ROW_2 = 50;
  /**
   * The 3 key
   */
  public static final int NUMBER_ROW_3 = 51;
  /**
   * The 4 key
   */
  public static final int NUMBER_ROW_4 = 52;
  /**
   * The 5 key
   */
  public static final int NUMBER_ROW_5 = 53;
  /**
   * The 6 key
   */
  public static final int NUMBER_ROW_6 = 54;
  /**
   * The 7 key
   */
  public static final int NUMBER_ROW_7 = 55;
  /**
   * The 8 key
   */
  public static final int NUMBER_ROW_8 = 56;
  /**
   * The 'W' key.
   */
  public static final int W_KEY = 87;
  /**
   * The 'A' key.
   */
  public static final int A_KEY = 65;
  /**
   * The 'S' key.
   */
  public static final int S_KEY = 83;
  /**
   * The 'D' key.
   */
  public static final int D_KEY = 68;
  /**
   * The up arrow key.
   */
  public static final int UP_KEY = 38;
  /**
   * The left arrow key.
   */
  public static final int LEFT_KEY = 37;
  /**
   * The down arrow key.
   */
  public static final int DOWN_KEY = 40;
  /**
   * The right arrow key.
   */
  public static final int RIGHT_KEY = 39;
  /**
   * The space key.
   */
  public static final int SPACE_KEY = 32;
  /**
   * The enter keys.
   */
  public static final int ENTER_KEY = 10;
  /**
   * The left shift key key.
   */
  public static final int LEFT_SHIFT_KEY = 16;
  /**
   * The escape key.
   */
  public static final int ESCAPE_KEY = 27;
  /**
   * The 'I' key.
   */
  public static final int I_KEY = 73;
  
  private static boolean[] keys = new boolean[220];
  /**
   * {@inheritDoc}
   */
  @Override
  public void keyTyped(KeyEvent e) {
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
    callKeyListeners(e);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }
  /**
   * Checks if a particular key is being pressed.
   * @param keyCode The key code to check.
   * @return True if key is being pressed otherwise false.
   */
  public static boolean isKeyPressed(int keyCode) {
    return keys[keyCode];
  }
  /**
   * Adds a callback notifier for when any key is pressed.
   * @param listener The INotify callback to be called when key is typed.
   */
  public void addKeyListener(Consumer<KeyEvent> listener) {
    keyListeners.add(listener);
  }
  /**
   * Removes a callback notifier from the list of notifiers that get called when any key is pressed.
   * @param listener The INotify instance to remove.
   */
  public void removeKeyListener(Consumer<KeyEvent> listener) {
    keyListeners.remove(listener);
  }

  private void callKeyListeners(KeyEvent e) {
    for (int i = 0; i < keyListeners.size(); i++) {
      keyListeners.get(i).accept(e);
    }
  }
}
