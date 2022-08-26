package com.monkeystomp.spirelands.gamedata.saves;

import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.crafting.CraftingManager;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.level.util.LevelStateManager;
import com.monkeystomp.spirelands.quest.QuestManager;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 * Singleton class in charge of making request to save game data.
 * Holds a list of all the state managers classes that are in charge of specific slices of the save data.
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class SaveDataManager {
  
  private SaveGameSlot slot;
  private final SaveLoadTransformer saveLoadTransformer = new SaveLoadTransformer();
  private static final ArrayList<SaveDataHydratable> SAVE_DATA_MANAGERS = new ArrayList<>();
  private static final SaveDataManager INSTANCE = new SaveDataManager();
  
  public static SaveDataManager getSaveDataManager() {
    if (SAVE_DATA_MANAGERS.isEmpty()) {
      SAVE_DATA_MANAGERS.add(CharacterManager.getCharacterManager());
      SAVE_DATA_MANAGERS.add(LevelStateManager.getLevelStateManager());
      SAVE_DATA_MANAGERS.add(LocationManager.getLocationManager());
      SAVE_DATA_MANAGERS.add(InventoryManager.getInventoryManager());
      SAVE_DATA_MANAGERS.add(CraftingManager.getCraftingManager());
      SAVE_DATA_MANAGERS.add(QuestManager.getQuestManager());
    }
    return INSTANCE;
  }
  
  public ArrayList<SaveDataHydratable> getSaveDataManagers() {
    return SAVE_DATA_MANAGERS;
  }

  public void setSlot(SaveGameSlot slot) {
    this.slot = slot;
  }
  /**
   * Main save game method.
   * @throws java.io.IOException
   */
  public void saveGame() throws Exception {
    JSONObject json = new JSONObject();
    for (SaveDataHydratable manager: getSaveDataManagers()) {
      manager.populateSaveData(json);
    }
    saveLoadTransformer.save(json, slot);
  }

}
