package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
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
  private final int thumbnailX = 20,
                    thumbnailY = 30,
                    spaceBetweenThumbnails = 20;
  private final String  fileName,
                        pathToSave = "./saves/";
  private boolean slotEmpty = true;
  private final FontInfo levelNameFont = GameFonts.getDarkText_plain_18();
  private ArrayList<Sprite> partyMembers = new ArrayList<>();
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
        setSlotDisplayData();
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    }
  }
  
  private void setSlotDisplayData() {
    JSONObject location = (JSONObject) json.get("Location");
    levelNameFont.setText((String)location.get("Level Name"));
    levelNameFont.setX(x + WIDTH / 2);
    levelNameFont.setY(y + 20);
    levelNameFont.centerText();
    JSONObject characters = (JSONObject) json.get("Characters");
    Set<?> keys = characters.keySet();
    keys.forEach(characterName -> {
      JSONObject character = (JSONObject)characters.get(characterName);
      JSONObject partyInfo = (JSONObject)character.get("partyInfo");
      if ((boolean)partyInfo.get("inParty")) {
        CharacterManager.getCharacterManager().getCharacters().forEach(gameCharacter -> {
          System.out.println(gameCharacter.getName());
          if (gameCharacter.getId().equals((String)character.get("id"))) partyMembers.add(new Sprite(gameCharacter.getThumbnail(), 50f));
        });
      }
    });
//    BiConsumer<String, JSONObject> ICharacterSetter = (key, value) -> {
//      JSONObject partyInfo = (JSONObject) value.get("partyInfo");
//      if ((boolean)partyInfo.get("inParty")) ;
//    };
//    characters.forEach(ICharacterSetter);
//    CharacterManager.getCharacterManager().getPartyMembers().forEach(character -> {
//      partyMembers.add(new Sprite(character.getThumbnail(), 25));
//    });
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
      for (int i = 0; i < partyMembers.size(); i++) {
        screen.renderSprite(gl, x + thumbnailX + i * spaceBetweenThumbnails, y + thumbnailY, partyMembers.get(i), false);
      }
    }
  }

}
