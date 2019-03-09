package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.monkeystomp.spirelands.gui.interfaces.Viewable;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class PauseView implements Viewable {

  protected ICallback closeCommand,
                      quitToMenuCommand;
  protected final Consumer<String> IPauseViewSetter;
  
  public PauseView(Consumer<String> IPauseViewSetter) {
    this.IPauseViewSetter = IPauseViewSetter;
  }
  /**
   * Sets the callback method that fires when the pause menu gets closed.
   * @param callback The callback method that will get fired when the dialog closes.
   */
  public void setCloseCommand(ICallback callback) {
    this.closeCommand = callback;
  }
  /**
   * Sets the callback method that fires when the user presses quit to main menu.
   * @param callback The callback method that will get fired when the quit to main menu button is pressed.
   */
  public void setQuitToMenuCommand(ICallback callback) {
    this.quitToMenuCommand = callback;
  }
}
