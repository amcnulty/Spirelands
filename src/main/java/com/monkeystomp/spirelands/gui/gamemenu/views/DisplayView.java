package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import java.util.function.Consumer;

/**
 * Abstract class for display views that is used for switching between different views in the game menu display panel.
 * @author Aaron Michael McNulty
 */
public abstract class DisplayView {
  /**
   * Y coordinate of the top most edge in the display view in pixels.
   */
  protected final int TOP = 23;
  /**
   * X coordinate of the right most edge in the display view in pixels.
   */
  protected final int RIGHT = 395;
  /**
   * Y coordinate of the bottom most edge in the display view in pixels.
   */
  protected final int BOTTOM = 178;
  /**
   * X coordinate of the left most edge in the display view in pixels.
   */
  protected final int LEFT = 124;
  /**
   * Character associated with the view.
   */
  protected Character character;
  /**
   * Sets the consumer for changing views with a Character reference.
   * @param IChangeViewWithCharacter Method for calling a new view with a Character reference.
   */
  public void setPartyMemberButtonPressHandler(Consumer<Character> IChangeViewWithCharacter) {}
  
  public void setCharacter(Character character) {
    this.character = character;
  }
  /**
   * Life cycle method that is called when view is about to close.
   */
  public void exitingView() {}
  /**
   * Updates the display view.
   */
  public void update() {}
  /**
   * Renders the display view to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {}
  
}
