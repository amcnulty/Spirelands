package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Mouse;
import java.io.File;
import java.util.function.Consumer;

/**
 * Buttons to be used on the UI.
 * @author Aaron Michael McNulty
 */
public class Button {
  
  private ICallback CALLBACK;
  private Consumer consumer;
  private Object consumable;
  private final SoundEffects SFX = new SoundEffects();
  private int mouseB,
              mouseX,
              mouseY;
  private boolean startedOffButton = false,
                  startedOnButton = false,
                  hovering = false,
                  disabled = false,
                  down = false;
  /**
   * The x pixel coordinate of the button.
   */
  protected int x;
  /**
   * The y pixel coordinate of the button.
   */
  protected int y;
  /**
   * The right edge of the button.
   */
  protected int right;
  /**
   * The bottom edge of the button.
   */
  protected int bottom;
  /**
   * The width of the button;
   */
  protected int width;
  /**
   * The height of the button.
   */
  protected int height;
  /**
   * The default state of the button.
   */
  protected Sprite button;
  /**
   * The hover state of the button.
   */
  protected Sprite buttonHover;
  /**
   * The down state of the button.
   */
  protected Sprite buttonDown;
  /**
   * The disabled state of the button.
   */
  protected Sprite disabledButton;
  /**
   * The current state of the button to render to the screen.
   */
  protected Sprite currentButton;
  /**
   * The text to render on the button.
   */
  protected String buttonText;
  /**
   * The sound when hovering over a button.
   */
  protected File hoverSound;
  /**
   * The sound after the button is clicked.
   */
  protected File clickSound;
  /**
   * Creates a Button object with a callback that gets fired when button is clicked.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public Button(String text, int x, int y, int width, int height, ICallback callback) {
    this.buttonText = text;
    this.width = width;
    this.height = height;
    this.x = x - width / 2;
    this.y = y - height / 2;
    this.CALLBACK = callback;
    right = x + width / 2;
    bottom = y + height / 2;
  }
  /**
   * Creates a Button object with a callback that gets fired when button is clicked.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param consumable The object to send with the consumer.
   * @param consumer The consumer function that fires when the button is clicked on.
   */
  public Button(String text, int x, int y, int width, int height, Object consumable, Consumer consumer) {
    this.buttonText = text;
    this.width = width;
    this.height = height;
    this.x = x - width / 2;
    this.y = y - height / 2;
    this.consumable = consumable;
    this.consumer = consumer;
    right = x + width / 2;
    bottom = y + height / 2;
  }
  
  public Button(int x, int y, Sprite defaultState, Sprite hover, Sprite down, Sprite disabled, ICallback callback) throws Error {
    if (!areAllEqual(defaultState, hover, down, disabled)) throw new Error("Dimensions of provided sprites do not match! Only sprites of matching width and height are allowed!");
    this.width = defaultState.getWidth();
    this.height = defaultState.getHeight();
    this.x = x - width / 2;
    this.y = y - height / 2;
    this.CALLBACK = callback;
    this.button = defaultState;
    this.buttonHover = hover;
    this.buttonDown = down;
    this.disabledButton = disabled;
    this.currentButton = button;
    right = x + width / 2;
    bottom = y + height / 2;
  }
  
  private boolean areAllEqual(Sprite s1, Sprite s2, Sprite s3, Sprite s4) {
    int value = s1.getWidth() + s1.getHeight();
    return (value == s2.getWidth() + s2.getHeight() && value == s3.getWidth() + s3.getHeight() && value == s4.getWidth() + s4.getHeight());
  }

  public boolean isHovering() {
    return hovering;
  }

  public boolean isDown() {
    return down;
  }
  /**
   * Fires when the button is hovered over with the cursor.
   */
  protected void hover() {
    if (!hovering && hoverSound != null) SFX.playSoundEffect(hoverSound);
    currentButton = buttonHover;
    hovering = true;
    down = false;
  }
  /**
   * Fires when the button is being pressed down.
   */
  protected void down() {
    currentButton = buttonDown;
    down = true;
  }
  /**
   * Fires when the button is clicked on. Will execute the callback that was set for this button.
   */
  @SuppressWarnings("unchecked")
  protected void click() {
    if (clickSound != null) SFX.playSoundEffect(clickSound);
    if (CALLBACK != null) CALLBACK.execute();
    else if (consumer != null) consumer.accept(consumable);
  }
  /**
   * Fires once when the button is not being hovered over or being clicked on to set the default state of the button.
   */
  protected void setDefault() {
    currentButton = button;
    hovering = false;
    down = false;
  }

  public int getTop() {
    return y;
  }
  
  public void setTop(int top) {
    this.y = top;
    bottom = y + height;
  }

  public int getRight() {
    return right;
  }

  public int getBottom() {
    return bottom;
  }

  public int getLeft() {
    return x;
  }
  
  public void setLeft(int left) {
    this.x = left;
    right = x + width / 2;
  }
  
  public boolean isDisabled() {
    return this.disabled;
  }
  
  public void disableButtonClickSound() {
    this.clickSound = null;
  }
  
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (this.disabled && disabledButton != null) currentButton = disabledButton;
    else currentButton = button;
  }
  /**
   * Updates the button.
   */
  public void update() {
    if (!disabled) {
      mouseX = (int)(Mouse.getX() / Screen.getScaleX());
      mouseY = (int)(Mouse.getY() / Screen.getScaleY());
      mouseB = Mouse.getMouseButton();
      if (mouseB != 1) {
        if (mouseX >= x && mouseX <= right && mouseY >= y && mouseY <= bottom) {
          hover();
          if (startedOnButton) click();
        }
        else {
          setDefault();
        }
        startedOnButton = false;
        startedOffButton = false;
      }
      else {
        if (mouseX >= x && mouseX <= right && mouseY >= y && mouseY <= bottom) {
          if (!startedOffButton) {
            startedOffButton = false;
            startedOnButton = true;
            down();
          }
          else hover();
        }
        else {
          setDefault();
          if (!startedOnButton) {
            startedOffButton = true;
            startedOnButton = false;
          }
        }
      }
    }
  }
  /**
   * Renders the button to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, currentButton, false);
  }
}
