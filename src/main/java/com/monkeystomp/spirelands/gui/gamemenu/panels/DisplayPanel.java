package com.monkeystomp.spirelands.gui.gamemenu.panels;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.views.DefaultView;
import com.monkeystomp.spirelands.gui.gamemenu.views.DisplayView;
import com.monkeystomp.spirelands.gui.gamemenu.views.ItemsView;
import java.util.HashMap;

/**
 * The large right panel in the game menu that is used for displaying current information. This main panel switches between different views depending on what navigation button has been pressed.
 * @author Aaron Michael McNulty
 */
public class DisplayPanel {
  /**
   * The default view. This is the first view that is seen when opening the game menu.
   */
  public static final String DEFAULT = "default";
  /**
   * The items view.
   */
  public static final String ITEMS = "items";
  
  private final DisplayView DEFAULT_VIEW = new DefaultView();
  private final DisplayView ITEMS_VIEW = new ItemsView();
  // private final DisplayPanelView WEAPONS_VIEW = new DisplayPanelView();
  // private final DisplayPanelView MAGIC_VIEW = new DisplayPanelView();
  private DisplayView currentView;
  private final HashMap<String, DisplayView> VIEW_MAP = new HashMap<>();
  /**
  * Creates a DisplayPanel object used for displaying view in the game menu.
  */
  public DisplayPanel() {
    createMap();
    setCurrentView(DEFAULT_VIEW);
  }
  
  private void createMap() {
    VIEW_MAP.put(DEFAULT, DEFAULT_VIEW);
    VIEW_MAP.put(ITEMS, ITEMS_VIEW);
  }

  private void setCurrentView(DisplayView view) {
    this.currentView = view;
  }
  /**
   * Changes the current view to the given display key type.
   * @param viewKey The key for the type of view to display.
   */
  public void changeView(String viewKey) {
    currentView.exitingView();
    setCurrentView(VIEW_MAP.get(viewKey));
  }
  /**
   * Updates the current view.
   */
  public void update() {
    currentView.update();
  }
  /**
   * Renders the current view to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    currentView.render(screen, gl);
  }

}
