package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataManager;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.gamemenu.components.AbilitySlot;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * CharacterManager is a singleton class that is used to perform operations on Character objects.
 * @author Aaron Michael McNulty
 */
public class CharacterManager {
  
  private final ArrayList<Character> gameCharacters = new ArrayList<>();
  private final ArrayList<Character> partyCharacters = new ArrayList<>();
  private final HashMap<Integer, Character> partyMap = new HashMap<>();
  private Character partyLeader;
  private JSONObject characterBaseInformation;
  private final JSONParser parser = new JSONParser();
  private final JSONUtil jsonUtil = new JSONUtil();
  
  private static final CharacterManager INSTANCE = new CharacterManager();
  
  private CharacterManager() {
    loadJSON();
    createBaseCharacters();
  }
  
  private void loadJSON() {
    try {
      characterBaseInformation = (JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream("/gameData/characters/characters.json")));
    }
    catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
  /**
   * Gets the singleton instance of this class.
   * @return The singleton instance of CharacterManager.
   */
  public static CharacterManager getCharacterManager() {
    return INSTANCE;
  }
  
  private void createBaseCharacters() {
    Set<?> keys = characterBaseInformation.keySet();
    keys.forEach(key -> {
      Character character = setupBaseCharacter((JSONObject)characterBaseInformation.get(key));
      gameCharacters.add(character);
    });
  }
  
  private Character setupBaseCharacter(JSONObject baseInfo) {
    Character character = new Character();
    character.setId(jsonUtil.getNestedString(baseInfo, new String[]{"id"}));
    character.setName(jsonUtil.getNestedString(baseInfo, new String[]{"details", "name"}));
    character.setThumbnail(new Sprite(jsonUtil.getNestedString(baseInfo, new String[]{"details", "thumbnail"}), 32));
    character.setOverworldSheet(new SpriteSheet(jsonUtil.getNestedString(baseInfo, new String[]{"details", "overworldSheet"})));
    character.setBattleSheet(new SpriteSheet(jsonUtil.getNestedString(baseInfo, new String[]{"details", "battleSheet"})));
    character.setWeaponType(jsonUtil.getNestedString(baseInfo, new String[]{"details", "weaponType"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "healthWeight"}));
    character.setManaWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "manaWeight"}));
    character.setStrengthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "strengthWeight"}));
    character.setDefenseWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "defenseWeight"}));
    character.setIntellectWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "intellectWeight"}));
    character.setSpiritWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "spiritWeight"}));
    character.setSpeedWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "speedWeight"}));
    character.setLuckWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "luckWeight"}));
    return character;
  }
  /**
   * Sets up character details from the given JSON object.
   * @param characterDetails JSON object containing the details for all game characters.
   */
  public void setupCharactersDetails(JSONObject characterDetails) {
    // Initializing the Item class
    String itemInitilizer = Item.ANCIENT_STAFF.getTitle();
    partyCharacters.clear();
    Set<?> keys = characterDetails.keySet();
    setPartyLeader(
      CharacterManager.getCharacterManager().getCharacters().stream().filter(
        character -> character.getId().equals(
          jsonUtil.getNestedString(SaveDataManager.getSaveDataManager().getSaveObject(), new String[]{JSONUtil.PARTY_LEADER})
        )
      ).findAny().orElse(null)
    );
    keys.forEach(key -> {
      JSONObject character = (JSONObject)characterDetails.get(key);
      gameCharacters.forEach(gameCharacter -> {
        if (gameCharacter.getId().equals((String)character.get(JSONUtil.ID))) {
          setupCharacterDetails(gameCharacter, (JSONObject)characterDetails.get(key));
          if (jsonUtil.getNestedBoolean(character, new String[]{JSONUtil.PARTY_INFO, JSONUtil.IN_PARTY})) {
            addPartyMember(gameCharacter, jsonUtil.getNestedInt(character, new String[]{JSONUtil.PARTY_INFO, JSONUtil.PARTY_POSITION}));
          }
        }
      });
    });
  }
  
  @SuppressWarnings("unchecked")
  private void setupCharacterDetails(Character character, JSONObject detailInfo) {
    JSONObject detailStats = (JSONObject)detailInfo.get(JSONUtil.STATS);
    JSONObject equipment = (JSONObject) detailInfo.get(JSONUtil.EQUIPMENT);
    character.setLevel(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.LEVEL_STAT}));
    character.setExperience(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.EXPERIENCE}));
    character.setHealth(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.HEALTH}));
    character.setHealthMax(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.HEALTH_MAX}));
    character.setMana(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.MANA}));
    character.setManaMax(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.MANA_MAX}));
    character.setStrength(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.STRENGTH}));
    character.setDefense(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.DEFENSE}));
    character.setIntellect(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.INTELLECT}));
    character.setSpirit(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.SPIRIT}));
    character.setSpeed(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.SPEED}));
    character.setLuck(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.STATS, JSONUtil.LUCK}));
    if (equipment.get(JSONUtil.WEAPON) != null)
      character.setEquippedWeapon((WeaponItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.EQUIPMENT, JSONUtil.WEAPON})));
    else
      character.setEquippedWeapon(null);
    if (equipment.get(JSONUtil.HELMET) != null)
      character.setEquippedHelmet((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.EQUIPMENT, JSONUtil.HELMET})));
    else
      character.setEquippedHelmet(null);
    if (equipment.get(JSONUtil.CHESTPLATE) != null)
      character.setEquippedChestplate((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.EQUIPMENT, JSONUtil.CHESTPLATE})));
    else
      character.setEquippedChestplate(null);
    if (equipment.get(JSONUtil.SHIELD) != null)
      character.setEquippedShield((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.EQUIPMENT, JSONUtil.SHIELD})));
    else
      character.setEquippedShield(null);
    if (equipment.get(JSONUtil.BOOTS) != null)
      character.setEquippedBoots((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{JSONUtil.EQUIPMENT, JSONUtil.BOOTS})));
    else
      character.setEquippedBoots(null);
    JSONArray slots = jsonUtil.getNestedArray(detailInfo, new String[]{JSONUtil.ABILITY_SLOTS});
    ArrayList<AbilitySlot> characterSlots = new ArrayList<>();
    slots.forEach(i -> {
      JSONObject slot = (JSONObject)i;
      AbilitySlot newSlot = new AbilitySlot(jsonUtil.getString(slot, JSONUtil.TYPE), jsonUtil.getInt(slot, JSONUtil.LEVEL));
      characterSlots.add(newSlot);
      if (!slot.get(JSONUtil.TYPE).equals(BattleMove.ITEM)) {
        if (slot.get(JSONUtil.MOVE) != null && BattleMove.MOVE_MAP.containsKey(jsonUtil.getInt(slot, JSONUtil.MOVE))) {
          newSlot.setMove(BattleMove.MOVE_MAP.get(jsonUtil.getInt(slot, JSONUtil.MOVE)));
        }
      }
      else {
        if (slot.get(JSONUtil.ITEM_MOVES) != null) {
          JSONArray itemMoves = (JSONArray)slot.get(JSONUtil.ITEM_MOVES);
          itemMoves.forEach(itemMove -> {
            JSONObject move = (JSONObject)itemMove;
            if (BattleMove.MOVE_MAP.containsKey(jsonUtil.getInt(move, JSONUtil.ID))) {
              newSlot.addItemMove(BattleMove.MOVE_MAP.get(jsonUtil.getInt(move, JSONUtil.ID)));
            }
          });
        }
      }
    });
    character.setAbilitySlots(characterSlots);
    character.updateEquippedMoves();
  }
  /**
   * Adds a character to the party at the specified position. Positions start at index 0;
   * @param character Character to add to the party.
   * @param position Position to put the character in the party at starting at index 0;
   * @return The character that was previously in this position otherwise returns null.
   */
  public Character addPartyMember(Character character, int position) {
    return partyMap.put(position, character);
  }
  /**
   * Removes the given character from the party and returns true if successful.
   * @param character Character to remove from party.
   * @return True if successful otherwise returns false.
   */
  public boolean removePartyMember(Character character) {
    return partyMap.remove(getPartyMemberPosition(character), character);
  }

  public Character getPartyLeader() {
    return partyLeader;
  }

  public void setPartyLeader(Character partyLeader) {
    this.partyLeader = partyLeader;
  }
  /**
   * Gets the position the given character is set to in the party. If character is not in party this method will return -1;
   * @param character Character to check party position.
   * @return The position the character is in the party. If character is not in party returns -1;
   */
  public int getPartyMemberPosition(Character character) {
    for (Entry<Integer, Character> entry: partyMap.entrySet()) {
      if (entry.getValue().equals(character)) return entry.getKey();
    }
    return -1;
  }
  
  public HashMap<Integer, Character> getPartyMembers() {
    return partyMap;
  }
  
  public ArrayList<Character> getCharacters() {
    return gameCharacters;
  }

}
