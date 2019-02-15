package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.titlescreen.components.SliderControl;
import com.monkeystomp.spirelands.view.LevelView;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SettingsView extends TitleView {
  
  private final Sprite  backdrop = new Sprite(375, 175, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final FontInfo  displayFont = GameFonts.getDarkText_bold_22(),
                          cursorFont = GameFonts.getDarkText_bold_22(),
                          soundFont = GameFonts.getDarkText_bold_22(),
                          musicFont = GameFonts.getDarkText_bold_22(),
                          sfxFont = GameFonts.getDarkText_bold_22();
  private final int buttonRowY = 214,
                    buttonRowStartX = Screen.getWidth() / 2 - 46,
                    spaceBetweenButtons = 25,
                    headersY = 60,
                    soundCenterX = 320;
  private final PrimaryButton 
    acceptButton = new PrimaryButton("Accept", buttonRowStartX, buttonRowY, 40, 13, () -> handleAcceptClick()),
    applyButton = new PrimaryButton("Apply", acceptButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleApplyClick()),
    cancelButton = new PrimaryButton("Cancel", applyButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleCancelClick());
  private final SliderControl musicVolumeSlider = new SliderControl(soundCenterX, 120, value -> IVolumeSetter.accept(value));
  private final SliderControl sfxVolumeSlider = new SliderControl(soundCenterX, 180, value -> System.out.println("sfx volume set to: " + value));

  public SettingsView(Consumer<LevelView> ILevelViewSetter, Consumer<TitleView> ITitleViewSetter, Consumer<Float> IVolumeSetter) {
    super(ILevelViewSetter,ITitleViewSetter, IVolumeSetter);
    setupFonts();
    musicVolumeSlider.setValue(100);
    sfxVolumeSlider.setValue(100);
  }
  
  private void setupFonts() {
    displayFont.setText("Display Settings");
    displayFont.setX(Screen.getWidth() / 5);
    displayFont.setY(headersY);
    displayFont.centerText();
    cursorFont.setText("Cursors");
    cursorFont.setX(Screen.getWidth() / 2);
    cursorFont.setY(headersY);
    cursorFont.centerText();
    soundFont.setText("Audio Settings");
    soundFont.setX(soundCenterX);
    soundFont.setY(headersY);
    soundFont.centerText();
    musicFont.setText("Music Volume");
    musicFont.setX(soundCenterX);
    musicFont.setY(90);
    musicFont.centerText();
    sfxFont.setText("Sound Effects Volume");
    sfxFont.setX(soundCenterX);
    sfxFont.setY(150);
    sfxFont.centerText();
  }
  
  private void handleAcceptClick() {
    
  }
  
  private void handleApplyClick() {
    
  }
  
  private void handleCancelClick() {
    exitingView();
    ITitleViewSetter.accept(new HomeTitleView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  @Override
  public void exitingView() {
    // Set music volume to configured volume.
    // Set sound effects to configured volume.
  }
  
  @Override
  public void update() {
    acceptButton.update();
    applyButton.update();
    cancelButton.update();
    musicVolumeSlider.update();
    sfxVolumeSlider.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2 + 20, backdrop, false);
    screen.renderFonts(displayFont);
    screen.renderFonts(cursorFont);
    screen.renderFonts(soundFont);
    screen.renderFonts(musicFont);
    screen.renderFonts(sfxFont);
    musicVolumeSlider.render(screen, gl);
    sfxVolumeSlider.render(screen, gl);
    acceptButton.render(screen, gl);
    applyButton.render(screen, gl);
    cancelButton.render(screen, gl);
  }
}
