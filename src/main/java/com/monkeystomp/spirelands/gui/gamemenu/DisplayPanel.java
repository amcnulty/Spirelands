package com.monkeystomp.spirelands.gui.gamemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.views.DefaultView;
import com.monkeystomp.spirelands.gui.gamemenu.views.DisplayView;
import com.monkeystomp.spirelands.gui.gamemenu.views.ItemsView;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class DisplayPanel {
  
  public static final String DEFAULT = "default";
  public static final String ITEMS = "items";
  
   private final DisplayView DEFAULT_VIEW = new DefaultView();
   private final DisplayView ITEMS_VIEW = new ItemsView();
  // private final DisplayPanelView WEAPONS_VIEW = new DisplayPanelView();
  // private final DisplayPanelView MAGIC_VIEW = new DisplayPanelView();
   private DisplayView currentView;
   private final HashMap<String, DisplayView> VIEW_MAP = new HashMap<>();
   
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
   
   public void changeView(String viewKey) {
     setCurrentView(VIEW_MAP.get(viewKey));
   }
   
  public void update() {
    currentView.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    currentView.render(screen, gl);
  }

}
