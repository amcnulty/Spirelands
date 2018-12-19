package com.monkeystomp.spirelands.gui.gamemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.GameMenuNavButton;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ButtonPanel {
  private ArrayList<GameMenuNavButton> navButtons = new ArrayList<>();
    
  public ButtonPanel() {
    createNavButtons();
  }

  private void createNavButtons() {
    navButtons.add(new GameMenuNavButton("Weapons", 70, 39, () -> {
      System.out.println("Item Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Armor", 70, 59, () -> {
      System.out.println("Armor Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Spells", 70, 79, () -> {
      System.out.println("Spells Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Items", 70, 99, () -> {
      System.out.println("Items Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Collectables", 70, 119, () -> {
      System.out.println("Collectables Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Quests", 70, 139, () -> {
      System.out.println("Quests Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Crafting", 70, 159, () -> {
      System.out.println("Crafting Button Clicked");
      resetNavButtons();
    }));
  }
  /**
   * Resets the navigation buttons to unselected state.
   */
  public void resetNavButtons() {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).removeBackground();
    }
  }
  
  public void update() {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).render(screen, gl);
    }
  }
  
}
