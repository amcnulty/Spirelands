package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.Game;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.CheckButton;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.titlescreen.components.SliderControl;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.gamedata.settings.SettingsManager;
import com.monkeystomp.spirelands.view.LevelView;
import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SettingsView extends TitleView {
  
  private final Sprite  backdrop = new Sprite(375, 175, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final SettingsManager settingsManager = SettingsManager.getSettingsManager();
  private final FontInfo  displayFont = GameFonts.getDarkText_bold_22(),
                          informationFont = GameFonts.getGameMenuPrimaryButtonText(),
                          fullScreenFont = GameFonts.getDarkText_plain_18(),
                          warningFont = GameFonts.getGameMenuPrimaryButtonText(),
                          resolutionFont = GameFonts.getDarkText_bold_22(),
                          l1Font = GameFonts.getDarkText_plain_18(),
                          l2Font = GameFonts.getDarkText_plain_18(),
                          l3Font = GameFonts.getDarkText_plain_18(),
                          h1Font = GameFonts.getDarkText_plain_18(),
                          h2Font = GameFonts.getDarkText_plain_18(),
                          h3Font = GameFonts.getDarkText_plain_18(),
                          cursorFont = GameFonts.getDarkText_bold_22(),
                          defaultCursorFont = GameFonts.getDarkText_plain_18(),
                          soundFont = GameFonts.getDarkText_bold_22(),
                          musicFont = GameFonts.getDarkText_bold_22(),
                          sfxFont = GameFonts.getDarkText_bold_22();
  private ArrayList<FontInfo> warningLines = new ArrayList<>();
  private final int checkboxX = 40,
                    cursorCheckX = 180,
                    buttonRowY = 214,
                    buttonRowStartX = Screen.getWidth() / 2 - 46,
                    spaceBetweenButtons = 25,
                    headersY = 60,
                    soundCenterX = 320;
  private float initialSFXVolume,
                initialMusicVolume;
  private final PrimaryButton 
    acceptButton = new PrimaryButton("Accept", buttonRowStartX, buttonRowY, 40, 13, () -> handleAcceptClick()),
    applyButton = new PrimaryButton("Apply", acceptButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleApplyClick()),
    cancelButton = new PrimaryButton("Cancel", applyButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleCancelClick());
  private final CheckButton fullScreenCheck = new CheckButton(checkboxX, 80, "fullscreen", () -> resetButtons());
  private ArrayList<CheckButton>  resolutionButtons = new ArrayList<>(),
                                  cursorButtons = new ArrayList<>();
  private final SliderControl musicVolumeSlider = new SliderControl(soundCenterX, 120, value -> {
    IVolumeSetter.accept(value);
    settingsManager.setMusicVolume(value);
  });
  private final SliderControl sfxVolumeSlider = new SliderControl(soundCenterX, 180, value -> settingsManager.setSfxVolume(value));
  private Consumer<String> ICursorValueSetter = path -> {
    if (!path.equals("default cursor")) {
      settingsManager.setPathToCursor(path);
      settingsManager.setCustomCursor(true);
    }
    else {
      settingsManager.setPathToCursor(path);
      settingsManager.setCustomCursor(false);
    }
  };
  private Consumer<Integer> IResolutionSetter = width -> {
    settingsManager.setWidth(width);
    settingsManager.setHeight(width * 9 / 16);
    settingsManager.setFullScreen(false);
  };

  public SettingsView(Consumer<LevelView> ILevelViewSetter, Consumer<TitleView> ITitleViewSetter, Consumer<Float> IVolumeSetter) {
    super(ILevelViewSetter,ITitleViewSetter, IVolumeSetter);
    setupFonts();
    setupResolutionButtons();
    setupCursorButtons();
    musicVolumeSlider.setValue(settingsManager.getMusicVolume() * 100);
    sfxVolumeSlider.setValue(settingsManager.getSfxVolume() * 100);
    initialMusicVolume = settingsManager.getMusicVolume();
    initialSFXVolume = settingsManager.getSfxVolume();
  }
  
  private void setupFonts() {
    displayFont.setText("Display Settings");
    displayFont.setX(Screen.getWidth() / 5);
    displayFont.setY(headersY);
    displayFont.centerText();
    fullScreenFont.setText("Full Screen");
    fullScreenFont.setX(checkboxX + 10);
    fullScreenFont.setY(80);
    informationFont.setText("Display settings take effect on game restart.");
    informationFont.setX(checkboxX - 10);
    informationFont.setY(68);
    informationFont.setColor(new Color(GameColors.DANGER_BUTTON_RED));
    warningFont.setText("Full screen setting will resize game window to match screen resolution. Game is intended for 16:9 aspect ratio and full screen may cause graphics to become stretched.");
    ArrayList<String> lines = TextUtil.createWrappedText(warningFont.getText(), warningFont.getFont(), 120);
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGameMenuPrimaryButtonText();
      info.setText(lines.get(i));
      info.setX(checkboxX - 10);
      info.setY(90 + i * 7);
      info.setColor(new Color(GameColors.DANGER_BUTTON_RED));
      warningLines.add(info);
    }
    resolutionFont.setText("Resolution");
    resolutionFont.setX(Screen.getWidth() / 5);
    resolutionFont.setY(120);
    l1Font.setText("1024 X 576");
    l1Font.setX(checkboxX + 10);
    l1Font.setY(130);
    l2Font.setText("1152 X 648");
    l2Font.setX(checkboxX + 10);
    l2Font.setY(142);
    l3Font.setText("1280 X 720");
    l3Font.setX(checkboxX + 10);
    l3Font.setY(154);
    h1Font.setText("1366 X 768");
    h1Font.setX(checkboxX + 10);
    h1Font.setY(166);
    h2Font.setText("1600 X 900");
    h2Font.setX(checkboxX + 10);
    h2Font.setY(178);
    h3Font.setText("1920 X 1080");
    h3Font.setX(checkboxX + 10);
    h3Font.setY(190);
    resolutionFont.centerText();
    cursorFont.setText("Cursors");
    cursorFont.setX(Screen.getWidth() / 2);
    cursorFont.setY(headersY);
    cursorFont.centerText();
    defaultCursorFont.setText("System Cursor");
    defaultCursorFont.setX(cursorCheckX + 10);
    defaultCursorFont.setY(180);
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
  
  private void setupResolutionButtons() {
    if (settingsManager.isFullScreen()) fullScreenCheck.check();
    resolutionButtons.add(new CheckButton(checkboxX, 130, "1024", () -> resetButtons()));
    resolutionButtons.add(new CheckButton(checkboxX, 142, "1152", () -> resetButtons()));
    resolutionButtons.add(new CheckButton(checkboxX, 154, "1280", () -> resetButtons()));
    resolutionButtons.add(new CheckButton(checkboxX, 166, "1366", () -> resetButtons()));
    resolutionButtons.add(new CheckButton(checkboxX, 178, "1600", () -> resetButtons()));
    resolutionButtons.add(new CheckButton(checkboxX, 190, "1920", () -> resetButtons()));
    for (int i = 0; i < resolutionButtons.size(); i++) {
      if (Integer.parseInt(resolutionButtons.get(i).getValue()) == settingsManager.getWidth() && !settingsManager.isFullScreen()) resolutionButtons.get(i).check();
    }
  }
  
  private void setupCursorButtons() {
    cursorButtons.add(new CheckButton(cursorCheckX, 90, "./resources/gui/hand_cursor.png", () -> resetCursorButtons()));
    cursorButtons.add(new CheckButton(cursorCheckX, 120, "./resources/gui/white_cursor.png", () -> resetCursorButtons()));
    cursorButtons.add(new CheckButton(cursorCheckX, 150, "./resources/gui/fantasy_cursor.png", () -> resetCursorButtons()));
    cursorButtons.add(new CheckButton(cursorCheckX, 180, "default cursor", () -> resetCursorButtons()));
    for (int i = 0; i < cursorButtons.size(); i++) {
      if (cursorButtons.get(i).getValue().equals(settingsManager.getPathToCursor())) cursorButtons.get(i).check();
    }
  }
  
  private void resetButtons() {
    fullScreenCheck.uncheck();
    for (int i = 0; i < resolutionButtons.size(); i++) {
      resolutionButtons.get(i).uncheck();
    }
  }
  
  private void resetCursorButtons() {
    for (int i = 0; i < cursorButtons.size(); i++) {
      cursorButtons.get(i).uncheck();
    }
  }
  
  private void handleAcceptClick() {
    handleApplyClick();
    handleCancelClick();
  }
  
  private void handleApplyClick() {
    for (int i = 0; i < cursorButtons.size(); i++) {
      if (cursorButtons.get(i).isChecked()) {
        ICursorValueSetter.accept(cursorButtons.get(i).getValue());
        if (cursorButtons.get(i).getValue().equals("default cursor")) {
          Game.setDefaultCursor();
        }
        else Game.setCursor();
      }
    }
    for (int i = 0; i < resolutionButtons.size(); i++) {
      if (resolutionButtons.get(i).isChecked()) {
        IResolutionSetter.accept(Integer.parseInt(resolutionButtons.get(i).getValue()));
      }
    }
    if (fullScreenCheck.isChecked()) settingsManager.setFullScreen(true);
    initialMusicVolume = settingsManager.getMusicVolume();
    initialSFXVolume = settingsManager.getSfxVolume();
    settingsManager.saveSettings();
  }
  
  private void handleCancelClick() {
    exitingView();
    ITitleViewSetter.accept(new HomeTitleView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  @Override
  public void exitingView() {
    settingsManager.setMusicVolume(initialMusicVolume);
    IVolumeSetter.accept(initialMusicVolume);
    settingsManager.setSfxVolume(initialSFXVolume);
  }
  
  @Override
  public void update() {
    fullScreenCheck.update();
    for (CheckButton button: resolutionButtons) {
      button.update();
    }
    for (CheckButton button: cursorButtons) {
      button.update();
    }
    musicVolumeSlider.update();
    sfxVolumeSlider.update();
    acceptButton.update();
    applyButton.update();
    cancelButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2 + 20, backdrop, false);
    screen.renderFonts(displayFont);
    screen.renderFonts(informationFont);
    screen.renderFonts(fullScreenFont);
    screen.renderFonts(resolutionFont);
    screen.renderFonts(l1Font);
    screen.renderFonts(l2Font);
    screen.renderFonts(l3Font);
    screen.renderFonts(h1Font);
    screen.renderFonts(h2Font);
    screen.renderFonts(h3Font);
    screen.renderFonts(cursorFont);
    screen.renderFonts(defaultCursorFont);
    screen.renderFonts(soundFont);
    screen.renderFonts(musicFont);
    screen.renderFonts(sfxFont);
    fullScreenCheck.render(screen, gl);
    if (fullScreenCheck.isChecked()) {
      for (FontInfo info: warningLines) {
        screen.renderFonts(info);
      }
    }
    for (CheckButton button: resolutionButtons) {
      button.render(screen, gl);
    }
    for (CheckButton button: cursorButtons) {
      button.render(screen, gl);
    }
    screen.renderSprite(gl, cursorCheckX + 10, 82, Sprite.HAND_CURSOR, false);
    screen.renderSprite(gl, cursorCheckX + 10, 112, Sprite.WHITE_CURSOR, false);
    screen.renderSprite(gl, cursorCheckX + 10, 142, Sprite.FANTASY_CURSOR, false);
    musicVolumeSlider.render(screen, gl);
    sfxVolumeSlider.render(screen, gl);
    acceptButton.render(screen, gl);
    applyButton.render(screen, gl);
    cancelButton.render(screen, gl);
  }
}
