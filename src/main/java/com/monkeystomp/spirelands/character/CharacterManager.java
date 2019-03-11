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
    character.setId((String)baseInfo.get("id"));
    character.setName((String)baseDetails.get("name"));
    character.setThumbnail(new Sprite((String)baseDetails.get("thumbnail")));
    character.setWeaponType((String)baseDetails.get("weaponType"));
    character.setHealthWeight((String)baseStats.get("healthWeight"));
    character.setManaWeight((String)baseStats.get("manaWeight"));
    character.setStrengthWeight((String)baseStats.get("strengthWeight"));
    character.setDefenseWeight((String)baseStats.get("defenseWeight"));
    character.setIntellectWeight((String)baseStats.get("intellectWeight"));
    character.setSpiritWeight((String)baseStats.get("spiritWeight"));
    character.setSpeedWeight((String)baseStats.get("speedWeight"));
    character.setLuckWeight((String)baseStats.get("luckWeight"));
    return character;
  }
  
  public void setupCharactersDetails(JSONObject characterDetails) {
    Set<?> keys = characterDetails.keySet();
    keys.forEach(key -> {
      JSONObject character = (JSONObject)characterDetails.get(key);
      gameCharacters.forEach(gameCharacter -> {
        if (gameCharacter.getId().equals((String)character.get("id"))) {
          setupCharacterDetails(gameCharacter, (JSONObject)characterDetails.get(key));
          System.out.println(character.get("partyInfo"));
          if ((boolean)((JSONObject)((JSONObject)characterDetails.get(key)).get("partyInfo")).get("inParty")) partyCharacters.add(gameCharacter);
        }
      });
    });
  }
  
  private void setupCharacterDetails(Character character, JSONObject detailInfo) {
    JSONObject detailStats = (JSONObject)detailInfo.get("stats");
    character.setLevel(Integer.parseInt(detailStats.get("level").toString()));
    character.setExperience(Integer.parseInt(detailStats.get("experience").toString()));
    character.setHealth(Integer.parseInt(detailStats.get("health").toString()));
    character.setHealthMax(Integer.parseInt(detailStats.get("healthMax").toString()));
    character.setMana(Integer.parseInt(detailStats.get("mana").toString()));
    character.setManaMax(Integer.parseInt(detailStats.get("manaMax").toString()));
    character.setStrength(Integer.parseInt(detailStats.get("strength").toString()));
    character.setDefense(Integer.parseInt(detailStats.get("defense").toString()));
    character.setIntellect(Integer.parseInt(detailStats.get("intellect").toString()));
    character.setSpirit(Integer.parseInt(detailStats.get("spirit").toString()));
    character.setSpeed(Integer.parseInt(detailStats.get("speed").toString()));
    character.setLuck(Integer.parseInt(detailStats.get("luck").toString()));
  }
  
  public ArrayList<Character> getPartyMembers() {
    return partyCharacters;
  }
  
  public ArrayList<Character> getCharacters() {
    return gameCharacters;
  }

}
