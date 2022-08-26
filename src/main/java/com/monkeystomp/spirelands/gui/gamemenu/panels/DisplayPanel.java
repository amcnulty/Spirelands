package com.monkeystomp.spirelands.gui.gamemenu.panels;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.views.AbilitiesView;
import com.monkeystomp.spirelands.gui.gamemenu.views.ArmorView;
import com.monkeystomp.spirelands.gui.gamemenu.views.CraftingView;
import com.monkeystomp.spirelands.gui.gamemenu.views.DefaultView;
import com.monkeystomp.spirelands.gui.gamemenu.views.DisplayView;
import com.monkeystomp.spirelands.gui.gamemenu.views.ItemsView;
import com.monkeystomp.spirelands.gui.gamemenu.views.PartyView;
import com.monkeystomp.spirelands.gui.gamemenu.views.QuestsView;
import com.monkeystomp.spirelands.gui.gamemenu.views.WeaponView;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.HashMap;

/**
 * The large right panel in the game menu that is used for displaying current information. This main panel switches between different views depending on what navigation button has been pressed.
 * @author Aaron Michael McNulty
 */
public class DisplayPanel {
  
  private final DisplayView defaultView = new DefaultView();
  private final DisplayView weaponView = new WeaponView();
  private final DisplayView armorView = new ArmorView();
  private final DisplayView abilitiesView = new AbilitiesView();
  private final DisplayView itemsView = new ItemsView();
  private final DisplayView questsView = new QuestsView();
  private final DisplayView craftingView = new CraftingView();
  private final DisplayView partyView = new PartyView();
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
    currentView.enteringView();
    defaultView.setPartyMemberButtonPressHandler(character -> {
      viewMap.get(nextViewKey).setCharacter(character);
      changeView(nextViewKey);
    });
  }
  
  private void createMap() {
    viewMap.put(DisplayView.DEFAULT, defaultView);
    viewMap.put(DisplayView.WEAPON, weaponView);
    viewMap.put(DisplayView.ARMOR, armorView);
    viewMap.put(DisplayView.ABILITIES, abilitiesView);
    viewMap.put(DisplayView.ITEMS, itemsView);
    viewMap.put(DisplayView.QUESTS, questsView);
    viewMap.put(DisplayView.CRAFTING, craftingView);
    viewMap.put(DisplayView.PARTY, partyView);
  }
  /**
   * WARNING! DO NOT CALL THIS METHOD DIRECTLY!! Use changeView() method because it will call exitingView() on the current view.
   * @param view The view to change to.
   */
  private void setCurrentView(DisplayView view) {
    currentView = view;
    IViewChanged.execute();
  }
  
  private void setNextViewKey(String key) {
    this.nextViewKey = key;
  }
  
  public void prepareNextViewWithCharacter(String key) {
    setNextViewKey(key);
    changeView(DisplayView.DEFAULT);
    DefaultView view = (DefaultView)viewMap.get(DisplayView.DEFAULT);
    view.activateCharacterButtons();
  }
  /**
   * Changes the current view to the given display key type.
   * @param viewKey The key for the type of view to display.
   */
  public void changeView(String viewKey) {
    currentView.exitingView();
    setCurrentView(viewMap.get(viewKey));
    currentView.enteringView();
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
