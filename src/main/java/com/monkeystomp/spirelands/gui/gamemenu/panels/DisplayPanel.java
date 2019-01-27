package com.monkeystomp.spirelands.gui.gamemenu.panels;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.views.DefaultView;
import com.monkeystomp.spirelands.gui.gamemenu.views.DisplayView;
import com.monkeystomp.spirelands.gui.gamemenu.views.ItemsView;
import com.monkeystomp.spirelands.gui.gamemenu.views.WeaponView;
import com.monkeystomp.spirelands.input.ICallback;
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
  /**
   * The weapon view.
   */
  public static final String WEAPON = "weapon";
  
  private final DisplayView defaultView = new DefaultView();
  private final DisplayView itemsView = new ItemsView();
   private final DisplayView weaponView = new WeaponView();
  // private final DisplayPanelView MAGIC_VIEW = new DisplayPanelView();
  private DisplayView currentView;
  private final ICallback IViewChanged;
  private String nextViewKey;
  private final HashMap<String, DisplayView> viewMap = new HashMap<>();
  /**
  * Creates a DisplayPanel object used for displaying view in the game menu.
  * @param IViewChanged Consumer that notifies when view changes.
  */
  public DisplayPanel(ICallback IViewChanged) {
    this.IViewChanged = IViewChanged;
    createMap();
    setCurrentView(defaultView);
    defaultView.setPartyMemberButtonPressHandler(character -> {
      viewMap.get(nextViewKey).setCharacter(character);
      changeView(nextViewKey);
    });
  }
  
  private void createMap() {
    viewMap.put(DEFAULT, defaultView);
    viewMap.put(ITEMS, itemsView);
    viewMap.put(WEAPON, weaponView);
  }
  /**
   * WARNING! DO NOT CALL THIS METHOD DIRECTLY!! Use changeView() method because it will call exitingView() on the current view.
   * @param view The view to change to.
   */
  private void setCurrentView(DisplayView view) {
    this.currentView = view;
    IViewChanged.execute();
  }
  
  private void setNextViewKey(String key) {
    this.nextViewKey = key;
  }
  
  public void prepareNextViewWithCharacter(String key) {
    setNextViewKey(key);
    changeView(DEFAULT);
    DefaultView view = (DefaultView)viewMap.get(DEFAULT);
    view.activateCharacterButtons();
  }
  /**
   * Changes the current view to the given display key type.
   * @param viewKey The key for the type of view to display.
   */
  public void changeView(String viewKey) {
    currentView.exitingView();
    setCurrentView(viewMap.get(viewKey));
  }
  /**
   * Checks if the default view is the current view.
   * @return True if current view is the default view false if not.
   */
  public boolean isDefaultView() {
    return currentView.equals(defaultView);
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
