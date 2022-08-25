package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gui.interfaces.Viewable;
import java.awt.Rectangle;
import java.util.function.Consumer;

/**
 * Abstract class for display views that is used for switching between different views in the game menu display panel.
 * @author Aaron Michael McNulty
 */
public abstract class DisplayView implements Viewable {
  /**
   * The default view. This is the first view that is seen when opening the game menu.
   */
  public static final String DEFAULT = "default";
  /**
   * The weapon view.
   */
  public static final String WEAPON = "weapon";
  /**
   * The armor view.
   */
  public static final String ARMOR = "armor";
  /**
   * The abilities view.
   */
  public static final String ABILITIES = "abilities";
  /**
   * The items view.
   */
  public static final String ITEMS = "items";
  /**
   * The quests view.
   */
  public static final String QUESTS = "quests";
  /**
   * The crafting view.
   */
  public static final String CRAFTING = "crafting";
  /**
   * The party view.
   */
  public static final String PARTY = "party";
  /**
   * Y coordinate of the top most edge in the display view in pixels.
   */
  protected static final int TOP = 23;
  /**
   * X coordinate of the right most edge in the display view in pixels.
   */
  protected static final int RIGHT = 395;
  /**
   * Y coordinate of the bottom most edge in the display view in pixels.
   */
  protected static final int BOTTOM = 178;
  /**
   * X coordinate of the left most edge in the display view in pixels.
   */
  protected static final int LEFT = 124;
  /**
   * The bounds of the display view area.
   */
  protected static final Rectangle DISPLAY_VIEW_BOUNDS = new Rectangle(LEFT, TOP, RIGHT - LEFT, BOTTOM - TOP);
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
  
}
