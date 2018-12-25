package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.graphics.Sprite;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterManager {
  
  private ArrayList<Character> gameCharacters = new ArrayList<>();
  private ArrayList<Character> partyCharacters = new ArrayList<>();
  private JSONObject json;
  private JSONParser parser = new JSONParser();
  
  private static final CharacterManager INSTANCE = new CharacterManager();
  
  private CharacterManager() {
    loadJSON();
    createCharacters();
  }
  
  private void loadJSON() {
    try {
      json = (JSONObject) parser.parse(new FileReader(getClass().getResource("/gameData/characters/characters.json").getFile()));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static CharacterManager getCharacterManager() {
    return INSTANCE;
  }
  
  private void createCharacters() {
    Set<?> keys = json.keySet();
    keys.forEach((key) -> {
      Character character = setupCharacter((JSONObject)json.get(key));
      gameCharacters.add(character);
      if ((boolean)((JSONObject)((JSONObject)json.get(key)).get("partyInfo")).get("inParty")) partyCharacters.add(character);
    });
    
  }
  
  private Character setupCharacter(JSONObject data) {
    Character character = new Character();
    JSONObject stats = (JSONObject)data.get("stats");
    character.setName((String)stats.get("name"));
    character.setLevel(Integer.parseInt(stats.get("level").toString()));
    character.setExperience(Integer.parseInt(stats.get("experience").toString()));
    character.setHealth(Integer.parseInt(stats.get("health").toString()));
    character.setHealthMax(Integer.parseInt(stats.get("healthMax").toString()));
    character.setMana(Integer.parseInt(stats.get("mana").toString()));
    character.setManaMax(Integer.parseInt(stats.get("manaMax").toString()));
    character.setStrength(Integer.parseInt(stats.get("strength").toString()));
    character.setDefense(Integer.parseInt(stats.get("defense").toString()));
    character.setIntellect(Integer.parseInt(stats.get("intellect").toString()));
    character.setSpirit(Integer.parseInt(stats.get("spirit").toString()));
    character.setSpeed(Integer.parseInt(stats.get("speed").toString()));
    character.setLuck(Integer.parseInt(stats.get("luck").toString()));
    character.setThumbnail(new Sprite((String)stats.get("thumbnail")));
    return character;
  }
  
  public ArrayList<Character> getPartyMembers() {
    return partyCharacters;
  }

}
