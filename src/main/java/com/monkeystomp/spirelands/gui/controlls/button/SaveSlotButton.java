package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.gamedata.saves.DataLoader;
import com.monkeystomp.spirelands.gamedata.saves.SaveGameSlot;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Shows a preview of the level, party members, and gold as a large button.
 * This is used on the save game screens for selecting which slot you want to save to.
 * @author Aaron Michael McNulty
 */
public class SaveSlotButton extends Button {
  
  private static final int  WIDTH = 100,
                            HEIGHT = 100;
  private final int thumbnailX = 14,
                    thumbnailY = 20,
                    spaceBetweenThumbnails = 20;
  private final SaveGameSlot slot;
  private final Sprite  separator = new Sprite(40, 1, GameColors.GAME_MENU_MUTED_TEXT),
                        smallGoldIndicator = new Sprite(Sprite.GOLD_INDICATOR, 75.0);
  private boolean slotEmpty = true;
  private final FontInfo  levelNameFont = GameFonts.getDarkText_plain_18(),
                          levelFontInfo = GameFonts.getDarkText_plain_18(),
                          goldFontInfo = GameFonts.getDarkText_plain_18();
  private final List<Sprite> partyMembers = Arrays.asList(new Sprite[3]);
  private JSONObject  json,
                      characters,
                      location;
  private final JSONParser parser = new JSONParser();
  private final JSONUtil jsonUtil = new JSONUtil();
  private final DataLoader loader = new DataLoader();
  
  public SaveSlotButton(int x, int y, SaveGameSlot slot, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, callback);
    this.slot = slot;
    checkIfFileExists();
    createButtonSprites();
    setButtonSounds();
  }
  
  private void checkIfFileExists() {
    try {
      json = loader.getSaveDataForSlot(slot);
      slotEmpty = false;
    }
    catch (Exception e) {
      slotEmpty = true;
    }
    if (!slotEmpty) {
      characters = (JSONObject) json.get(JSONUtil.CHARACTERS);
      location = (JSONObject) json.get(JSONUtil.LOCATION);
      createButtonSprites();
      setSlotDisplayData();
    }
  }

  private void setSlotDisplayData() {
    levelNameFont.setText((String)location.get(JSONUtil.LEVEL_NAME));
    levelNameFont.setX(x + WIDTH / 2);
    levelNameFont.setY(y + 10);
    levelNameFont.centerText();
    levelFontInfo.setText("Level: " + jsonUtil.getNestedString(characters, new String[]{JSONUtil.LUKE, JSONUtil.STATS, JSONUtil.LEVEL_STAT}));
    levelFontInfo.setX(x + width / 5);
    levelFontInfo.setY(levelNameFont.getY() + 45);
    goldFontInfo.setText(String.valueOf(jsonUtil.getNestedInt(json, new String[]{JSONUtil.INVENTORY, JSONUtil.GOLD})));
    goldFontInfo.setX(x + width / 5 + 17);
    goldFontInfo.setY(levelFontInfo.getY() + 10);
    partyMembers.replaceAll(e -> null);
    Set<?> keys = characters.keySet();
    keys.forEach(characterName -> {
      JSONObject character = (JSONObject)characters.get(characterName);
      JSONObject partyInfo = (JSONObject)character.get(JSONUtil.PARTY_INFO);
      if (jsonUtil.getBoolean(partyInfo, JSONUtil.IN_PARTY)) {
        CharacterManager.getCharacterManager().getCharacters().forEach(gameCharacter -> {
          if (gameCharacter.getId().equals((String)character.get(JSONUtil.ID))) partyMembers.set(jsonUtil.getInt(partyInfo, JSONUtil.PARTY_POSITION), gameCharacter.getThumbnail());
        });
      }
    });
  }
  /**
   * Initializes this save slot by checking if the file exists and if it does it will update the label.
   */
  public void initSlot() {
    checkIfFileExists();
  }

  public boolean isSlotEmpty() {
    return slotEmpty;
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
      screen.renderFonts(levelFontInfo);
      screen.renderSprite(gl, goldFontInfo.getX() - 17, goldFontInfo.getY() - 3, Sprite.GOLD_INDICATOR, false);
      screen.renderFonts(goldFontInfo);
      screen.renderSprite(gl, x + width / 2 - separator.getWidth() / 2, levelNameFont.getY() + 10, separator, false);
      for (int i = 0; i < partyMembers.size(); i++) {
        if (partyMembers.get(i) != null)
          screen.renderSprite(gl, x + thumbnailX + i * spaceBetweenThumbnails, y + thumbnailY, partyMembers.get(i), 1f, false, .5f);
      }
    }
  }

}
