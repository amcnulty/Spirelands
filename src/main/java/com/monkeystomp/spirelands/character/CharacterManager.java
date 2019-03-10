package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.gamedata.saves.SaveDataManager;
import com.monkeystomp.spirelands.graphics.Sprite;
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
  
  private static final CharacterManager INSTANCE = new CharacterManager();
  
  private CharacterManager() {
    loadJSON();
    createCharacters(SaveDataManager.getSaveDataManager().getCharacters());
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
  
  private void createCharacters(JSONObject characterDetails) {
    Set<?> keys = characterBaseInformation.keySet();
    keys.forEach((key) -> {
      Character character = setupCharacter((JSONObject)characterBaseInformation.get(key), (JSONObject)characterDetails.get(key));
      gameCharacters.add(character);
      if ((boolean)((JSONObject)((JSONObject)characterDetails.get(key)).get("partyInfo")).get("inParty")) partyCharacters.add(character);
    });
    
  }
  
  private Character setupCharacter(JSONObject baseInfo, JSONObject detailInfo) {
    Character character = new Character();
    JSONObject baseDetails = (JSONObject)baseInfo.get("details");
    JSONObject baseStats = (JSONObject)baseInfo.get("stats");
    JSONObject detailStats = (JSONObject)detailInfo.get("stats");
    character.setName((String)baseDetails.get("name"));
    character.setThumbnail(new Sprite((String)baseDetails.get("thumbnail")));
    character.setWeaponType((String)baseDetails.get("weaponType"));
    character.setLevel(Integer.parseInt(detailStats.get("level").toString()));
    character.setExperience(Integer.parseInt(detailStats.get("experience").toString()));
    character.setHealth(Integer.parseInt(detailStats.get("health").toString()));
    character.setHealthMax(Integer.parseInt(detailStats.get("healthMax").toString()));
    character.setHealthWeight((String)baseStats.get("healthWeight"));
    character.setMana(Integer.parseInt(detailStats.get("mana").toString()));
    character.setManaMax(Integer.parseInt(detailStats.get("manaMax").toString()));
    character.setManaWeight((String)baseStats.get("manaWeight"));
    character.setStrength(Integer.parseInt(detailStats.get("strength").toString()));
    character.setStrengthWeight((String)baseStats.get("strengthWeight"));
    character.setDefense(Integer.parseInt(detailStats.get("defense").toString()));
    character.setDefenseWeight((String)baseStats.get("defenseWeight"));
    character.setIntellect(Integer.parseInt(detailStats.get("intellect").toString()));
    character.setIntellectWeight((String)baseStats.get("intellectWeight"));
    character.setSpirit(Integer.parseInt(detailStats.get("spirit").toString()));
    character.setSpiritWeight((String)baseStats.get("spiritWeight"));
    character.setSpeed(Integer.parseInt(detailStats.get("speed").toString()));
    character.setSpeedWeight((String)baseStats.get("speedWeight"));
    character.setLuck(Integer.parseInt(detailStats.get("luck").toString()));
    character.setLuckWeight((String)baseStats.get("luckWeight"));
    return character;
  }
  
  public ArrayList<Character> getPartyMembers() {
    return partyCharacters;
  }
  
  public ArrayList<Character> getCharacters() {
    return gameCharacters;
  }

}
