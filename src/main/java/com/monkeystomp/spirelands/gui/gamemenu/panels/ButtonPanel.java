package com.monkeystomp.spirelands.gui.gamemenu.panels;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuNavButton;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * The left panel in the game menu that is used for displaying the navigation buttons.
 * @author Aaron Michael McNulty
 */
public class ButtonPanel {
  
  private Consumer<String> viewChanger;
  private Consumer<String> nextViewChanger;
  private final ArrayList<GameMenuNavButton> navButtons = new ArrayList<>();
  /**
   * Creates a ButtonPanel object.
   */
  public ButtonPanel() {
    createNavButtons();
  }

  private void createNavButtons() {
    navButtons.add(new GameMenuNavButton("Weapons", 70, 39, () -> {
      nextViewChanger.accept(DisplayPanel.WEAPON);
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Armor", 70, 59, () -> {
      nextViewChanger.accept(DisplayPanel.ARMOR);
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Spells", 70, 79, () -> {
      System.out.println("Spells Button Clicked");
      resetNavButtons();
    }));
    navButtons.add(new GameMenuNavButton("Items", 70, 99, () -> {
      viewChanger.accept(DisplayPanel.ITEMS);
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
   * Sets the view changer used to call the outer class with view changing instructions.
   * @param changer The method to call in the outer class.
   */
  public void setViewChanger(Consumer<String> changer) {
    this.viewChanger = changer;
  }
  /**
   * Sets the next view changer used to call the outer class with view changing instructions. This method is used when the next view to be changed cannot be rendered until the party member button is clicked on to send the character information.
   * @param changer The method to call in the outer class.
   */
  public void setNextViewChanger(Consumer<String> changer) {
    this.nextViewChanger = changer;
  }
  /**
   * Resets the navigation buttons to unselected state.
   */
  public void resetNavButtons() {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).removeBackground();
    }
  }
  /**
   * Updates the button panel.
   */
  public void update() {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).update();
    }
  }
  /**
   * Renders the button panel to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).render(screen, gl);
    }
  }
  
}
