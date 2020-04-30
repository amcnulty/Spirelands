package com.monkeystomp.spirelands.gui.controlls.button;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.function.Consumer;

/**
 * A type of button to use in a button group. Extend this class instead of Button to create a button within a ButtonGroup object.
 * @author Aaron Michael McNulty
 */
public class GroupButton extends Button {
  
  private Consumer<GroupButton> IGroupNotifier;

  /**
   * Creates a Button object with a callback that gets fired when button is clicked.
   * The active state must be set after creation by calling the setButtonActive() method.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public GroupButton(String text, int x, int y, int width, int height, ICallback callback) {
    super(text, x, y, width, height, callback);
    this.disabledButton = buttonHover;
  }
  
  /**
   * Creates a Button object with a callback that gets fired when button is clicked.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param buttonActive The sprite to show when the button is active.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public GroupButton(String text, int x, int y, int width, int height, Sprite buttonActive, ICallback callback) {
    super(text, x, y, width, height, callback);
    this.disabledButton = buttonActive;
  }
  
  /**
   * Creates a Button object with a callback that gets fired when button is clicked.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param buttonActive The sprite to show when the button is active.
   * @param consumable The object to send with the consumer.
   * @param consumer The consumer function that fires when the button is clicked on.
   */
  public GroupButton(String text, int x, int y, int width, int height, Sprite buttonActive, Object consumable, Consumer consumer) {
    super(text, x, y, width, height, consumable, consumer);
    this.disabledButton = buttonActive;
  }
  /**
   * Sets the active button state for this group button.
   * @param buttonActive Sprite that the button will show when active.
   */
  public void setButtonActive(Sprite buttonActive) {
    this.disabledButton = buttonActive;
  }
   
  @Override
  protected void click() {
    super.click();
    if (IGroupNotifier == null) {
      System.err.println("Consumer<GroupButton> IGroupNotifier is not set for this button. Please use setIGroupNotifier() method to set the consumer.");
    }
    else IGroupNotifier.accept(this);
  }
  /**
   * Set this button active in the group so it will show active state and not respond to hover and click events.
   */
  public void setActive() {
    setDisabled(true);
  }
  /**
   * Reset this button so it will respond to hover and click events.
   */
  public void resetButton() {
    setDisabled(false);
  }

  public void setIGroupNotifier(Consumer<GroupButton> IGroupNotifier) {
    this.IGroupNotifier = IGroupNotifier;
  }
  
}
