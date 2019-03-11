package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SaveSlotButton extends Button {
  
  private static final int  WIDTH = 100,
                            HEIGHT = 100;
  private final String  fileName,
                        pathToSave = "./saves/";
  private boolean slotEmpty = true;
  private FontInfo levelNameFont = GameFonts.getDarkText_plain_18();
  private JSONObject json;
  private final JSONParser parser = new JSONParser();
  
  public SaveSlotButton(int x, int y, String fileName, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, callback);
    this.fileName = fileName;
    checkIfFileExists();
    createButtonSprites();
    setButtonSounds();
  }
  
  private void checkIfFileExists() {
    File saveFile = new File(pathToSave + fileName);
    slotEmpty = !saveFile.exists();
    if (!slotEmpty) {
      try {
        json = (JSONObject) parser.parse(new FileReader(saveFile));
        JSONObject location = (JSONObject) json.get("Location");
        levelNameFont.setText((String)location.get("Level Name"));
        levelNameFont.setX(x + WIDTH / 2);
        levelNameFont.setY(y + 20);
        levelNameFont.centerText();
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    }
  }
  
  private void createButtonSprites() {
    if (slotEmpty) {
      button = new Sprite(new Sprite("./resources/gui/empty_save_slot.png"), 25f);
      buttonHover = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
      buttonDown = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
    }
    else {
      button = new Sprite(new Sprite("./resources/gui/save_slot_border.png"), 25f);
      buttonHover = new Sprite(new Sprite("./resources/gui/save_slot_border_selected.png"), 25f);
      buttonDown =  new Sprite(new Sprite("./resources/gui/save_slot_border_selected.png"), 25f);
    }
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  @Override
  protected void click() {
    super.click();
    button = buttonDown;
    buttonHover = buttonDown;
  }
  
  public void reset() {
    if (slotEmpty) {
      button = new Sprite(new Sprite("./resources/gui/empty_save_slot.png"), 25f);
      buttonHover = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
      buttonDown = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
    }
    else {
      button = new Sprite(new Sprite("./resources/gui/save_slot_border.png"), 25f);
      buttonHover = new Sprite(new Sprite("./resources/gui/save_slot_border_selected.png"), 25f);
      buttonDown =  new Sprite(new Sprite("./resources/gui/save_slot_border_selected.png"), 25f);
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    if (!slotEmpty) {
      screen.renderFonts(levelNameFont);
    }
  }

}
