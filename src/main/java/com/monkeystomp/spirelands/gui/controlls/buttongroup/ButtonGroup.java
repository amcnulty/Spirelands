package com.monkeystomp.spirelands.gui.controlls.buttongroup;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.GroupButton;
import java.util.ArrayList;

/**
 * Used to update, render and receive values from a group of buttons.
 * @author Aaron Michael McNulty
 */
public class ButtonGroup {
  
  private final ArrayList<GroupButton> buttons = new ArrayList<>();
 
  /**
   * Adds a button to the list of buttons in this group.
   * @param newButton Button to be added to the list of GroupButton objects.
   */
  public void addButton(GroupButton newButton) {
    newButton.setIGroupNotifier(activeButton -> {
      for (GroupButton button: buttons) {
        button.resetButton();
      }
      activeButton.setActive();
    });
    buttons.add(newButton);
  }
  
  public void update() {
    for (GroupButton button: buttons) {
      button.update();
    }
  }
 
  public void render(Screen screen, GL2 gl) {
    for (GroupButton button: buttons) {
      button.render(screen, gl);
    }
  }

}
