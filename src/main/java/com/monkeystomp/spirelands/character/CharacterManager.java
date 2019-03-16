package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * CharacterManager is a singleton class that is used to perform operations on Character objects.
 * @author Aaron Michael McNulty
 */
public class CharacterManager {
  
  private final ArrayList<Character> gameCharacters = new ArrayList<>();
  private final ArrayList<Character> partyCharacters = new ArrayList<>();
  private JSONObject characterBaseInformation;
  private final JSONParser parser = new JSONParser();
  private final JSONUtil jsonUtil = new JSONUtil();
  
  private static final CharacterManager INSTANCE = new CharacterManager();
  
  private CharacterManager() {
    loadJSON();
    createBaseCharacters();
//    createCharacters(SaveDataManager.getSaveDataManager().getCharacters());
  }
  
  private void loadJSON() {
    try {
      characterBaseInformation = (JSONObject) parser.parse(new FileReader(getClass().getResource("/gameData/characters/characters.json").getFile()));
    }
    catch (Exception e) {
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
    JSONObject baseDetails = (JSONObject)baseInfo.get("details");
    JSONObject baseStats = (JSONObject)baseInfo.get("stats");
    character.setId(jsonUtil.getNestedString(baseInfo, new String[]{"id"}));
    character.setName(jsonUtil.getNestedString(baseInfo, new String[]{"details", "name"}));
    character.setThumbnail(new Sprite(jsonUtil.getNestedString(baseInfo, new String[]{"details", "thumbnail"})));
    character.setWeaponType(jsonUtil.getNestedString(baseInfo, new String[]{"details", "weaponType"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "healthWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "manaWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "strengthWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "defenseWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "intellectWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "spiritWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "speedWeight"}));
    character.setHealthWeight(jsonUtil.getNestedString(baseInfo, new String[]{"stats", "luckWeight"}));
    return character;
  }
  
  public void setupCharactersDetails(JSONObject characterDetails) {
    partyCharacters.clear();
    Set<?> keys = characterDetails.keySet();
    keys.forEach(key -> {
      JSONObject character = (JSONObject)characterDetails.get(key);
      gameCharacters.forEach(gameCharacter -> {
        if (gameCharacter.getId().equals((String)character.get("id"))) {
          setupCharacterDetails(gameCharacter, (JSONObject)characterDetails.get(key));
          if (jsonUtil.getNestedBoolean(character, new String[]{"partyInfo", "inParty"})) partyCharacters.add(gameCharacter);
        }
      });
    });
  }
  
  private void setupCharacterDetails(Character character, JSONObject detailInfo) {
    JSONObject detailStats = (JSONObject)detailInfo.get("stats");
    JSONObject equipment = (JSONObject) detailInfo.get("equipment");
    character.setLevel(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "level"}));
    character.setExperience(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "experience"}));
    character.setHealth(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "health"}));
    character.setHealthMax(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "healthMax"}));
    character.setMana(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "mana"}));
    character.setManaMax(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "manaMax"}));
    character.setStrength(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "strength"}));
    character.setDefense(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "defense"}));
    character.setIntellect(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "intellect"}));
    character.setSpirit(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "spirit"}));
    character.setSpeed(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "speed"}));
    character.setLuck(jsonUtil.getNestedInt(detailInfo, new String[]{"stats", "luck"}));
    if (equipment.get("weapon") != null)
      character.setEquippedWeapon((WeaponItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{"equipment", "weapon"})));
    else
      character.setEquippedWeapon(null);
    if (equipment.get("helmet") != null)
      character.setEquippedHelmet((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{"equipment", "helmet"})));
    else
      character.setEquippedHelmet(null);
    if (equipment.get("chestplate") != null)
      character.setEquippedChestplate((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{"equipment", "chestplate"})));
    else
      character.setEquippedChestplate(null);
    if (equipment.get("shield") != null)
      character.setEquippedShield((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{"equipment", "chestplate"})));
    else
      character.setEquippedShield(null);
    if (equipment.get("boots") != null)
      character.setEquippedBoots((ArmorItem)Item.ITEM_MAP.get(jsonUtil.getNestedInt(detailInfo, new String[]{"equipment", "boots"})));
    else
      character.setEquippedBoots(null);
  }
  
  public ArrayList<Character> getPartyMembers() {
    return partyCharacters;
  }
  
  public ArrayList<Character> getCharacters() {
    return gameCharacters;
  }

}
