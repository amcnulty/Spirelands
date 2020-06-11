package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.battle.move.BattleMove;
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
  /**
   * Id for the Luke character.
   */
  public static final String LUKE = "luke01";
  /**
   * Id for the Luke character.
   */
  public static final String SARA = "sara02";
  /**
   * Id for the Luke character.
   */
  public static final String WILLIAM = "will03";
  /**
   * Id for the Luke character.
   */
  public static final String AARON = "aaron04";
  /**
   * Id for the Luke character.
   */
  public static final String MILEY = "miley05";
  /**
   * Id for the Luke character.
   */
  public static final String JUSTIN = "justin06";
  
  private final ArrayList<Character> gameCharacters = new ArrayList<>();
  private final ArrayList<Character> availableCharacters = new ArrayList<>();
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
    Set<?> keys = characterDetails.keySet();
    keys.forEach(key -> {
      JSONObject character = (JSONObject)characterDetails.get(key);
      gameCharacters.forEach(gameCharacter -> {
        if (gameCharacter.getId().equals((String)character.get(JSONUtil.ID))) {
          setupCharacterDetails(gameCharacter, character);
        }
      });
    });
  }
  
  @SuppressWarnings("unchecked")
  private void setupCharacterDetails(Character character, JSONObject detailInfo) {
    JSONObject stats = (JSONObject)detailInfo.get(JSONUtil.STATS);
    JSONObject equipment = (JSONObject) detailInfo.get(JSONUtil.EQUIPMENT);
    JSONArray abilitySlots = (JSONArray) detailInfo.get(JSONUtil.ABILITY_SLOTS);
    JSONObject partyInfo = (JSONObject) detailInfo.get(JSONUtil.PARTY_INFO);
    // Stats
    character.setLevel(jsonUtil.getInt(stats, JSONUtil.LEVEL_STAT));
    character.setExperience(jsonUtil.getInt(stats, JSONUtil.EXPERIENCE));
    character.setHealth(jsonUtil.getInt(stats, JSONUtil.HEALTH));
    character.setHealthMax(jsonUtil.getInt(stats, JSONUtil.HEALTH_MAX));
    character.setMana(jsonUtil.getInt(stats, JSONUtil.MANA));
    character.setManaMax(jsonUtil.getInt(stats, JSONUtil.MANA_MAX));
    character.setStrength(jsonUtil.getInt(stats, JSONUtil.STRENGTH));
    character.setDefense(jsonUtil.getInt(stats, JSONUtil.DEFENSE));
    character.setIntellect(jsonUtil.getInt(stats, JSONUtil.INTELLECT));
    character.setSpirit(jsonUtil.getInt(stats, JSONUtil.SPIRIT));
    character.setSpeed(jsonUtil.getInt(stats, JSONUtil.SPEED));
    character.setLuck(jsonUtil.getInt(stats, JSONUtil.LUCK));
    // Equipment
    if (equipment.get(JSONUtil.WEAPON) != null)
      character.setEquippedWeapon((WeaponItem)Item.ITEM_MAP.get(jsonUtil.getInt(equipment, JSONUtil.WEAPON)));
    else
      character.setEquippedWeapon(null);
    if (equipment.get(JSONUtil.HELMET) != null)
      character.setEquippedHelmet((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getInt(equipment, JSONUtil.HELMET)));
    else
      character.setEquippedHelmet(null);
    if (equipment.get(JSONUtil.CHESTPLATE) != null)
      character.setEquippedChestplate((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getInt(equipment, JSONUtil.CHESTPLATE)));
    else
      character.setEquippedChestplate(null);
    if (equipment.get(JSONUtil.SHIELD) != null)
      character.setEquippedShield((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getInt(equipment, JSONUtil.SHIELD)));
    else
      character.setEquippedShield(null);
    if (equipment.get(JSONUtil.BOOTS) != null)
      character.setEquippedBoots((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getInt(equipment, JSONUtil.BOOTS)));
    else
      character.setEquippedBoots(null);
    // Ability Slots
    ArrayList<AbilitySlot> characterSlots = new ArrayList<>();
    abilitySlots.forEach(i -> {
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
    // Party Info
    if (jsonUtil.getBoolean(partyInfo, JSONUtil.IN_PARTY)) {
      addPartyMember(character, jsonUtil.getInt(partyInfo, JSONUtil.PARTY_POSITION));
    }
    if (jsonUtil.getBoolean(partyInfo, JSONUtil.AVAILABLE)) {
      addAvailableCharacter(character);
    }
  }
  /**
   * Adds a character to the party at the specified position. Positions start at index 0.
   * If character is at position 0 they will also be added as the party leader.
   * @param character Character to add to the party.
   * @param position Position to put the character in the party at starting at index 0;
   * @return The character that was previously in this position otherwise returns null.
   */
  public Character addPartyMember(Character character, int position) {
    if (position == 0)
      setPartyLeader(character);
    return partyMap.put(position, character);
  }
  /**
   * Adds a character to the party at the lowest open position.
   * @param character Character to add to the party.
   * @return False if all slots are taken up true if success.
   */
  public boolean addPartyMemberAtLowestPosition(Character character) {
    for (Integer i = 0; i < 3; i++) {
      if (partyMap.get(i) == null) {
        partyMap.put(i, character);
        return true;
      }
    }
    return false;
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
   * Adds a character to the list of available characters.
   * @param character Character to add to list of available characters.
   */
  public void addAvailableCharacter(Character character) {
    if (!availableCharacters.contains(character))
      availableCharacters.add(character);
  }
  /**
   * Sets a character as unavailable.
   * @param character Character to set unavailable.
   */
  public void setCharacterUnavailable(Character character) {
    availableCharacters.remove(character);
  }
  /**
   * Checks if the given character is available.
   * @param character Character to check availability.
   * @return True if character is available.
   */
  public boolean isCharacterAvailable(Character character) {
    return availableCharacters.contains(character);
  }
  /**
   * Checks if the given character is in the party.
   * @param character Character to check party status.
   * @return True if character is in party.
   */
  public boolean isCharacterInParty(Character character) {
    return getPartyMemberPosition(character) != -1;
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

  public ArrayList<Character> getAvailableCharacters() {
    return availableCharacters;
  }

}
