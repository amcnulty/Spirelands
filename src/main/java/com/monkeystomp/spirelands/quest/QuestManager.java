package com.monkeystomp.spirelands.quest;

import com.monkeystomp.spirelands.gamedata.saves.SaveDataHydratable;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Quest manager is responsible for quest save data and state management for quest data.
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class QuestManager implements SaveDataHydratable {
  /**
   * Slice of save object for this manager. JSON Name - CRAFTING
   */
  private JSONArray quests;

  private final JSONUtil jsonUtil = new JSONUtil();
  
  private final HashMap<Integer, Quest> acceptedQuests = new HashMap<>();
  
  private static final HashMap<String, QuestStatus> SAVE_VALUE_TO_QUEST_STATUS_MAP = new HashMap<>();
  
  static {
    SAVE_VALUE_TO_QUEST_STATUS_MAP.put(QuestStatus.New.toString(), QuestStatus.New);
    SAVE_VALUE_TO_QUEST_STATUS_MAP.put(QuestStatus.Incomplete.toString(), QuestStatus.Incomplete);
    SAVE_VALUE_TO_QUEST_STATUS_MAP.put(QuestStatus.Complete.toString(), QuestStatus.Complete);
  }
  
  // The singleton instance of the class.
  private static final QuestManager INSTANCE = new QuestManager();
  
  private QuestManager() {}
  /**
   * Gets the quest manager instance.
   * @return The quest manager instance.
   */
  public static QuestManager getQuestManager() {
    return INSTANCE;
  }

  /**
   * Gets a map of all the accepted quests in the current save game.
   * @return A map of all the accepted quests in the current save game.
   */
  public HashMap<Integer, Quest> getAcceptedQuests() {
    return acceptedQuests;
  }

  private void setQuestData() {
    acceptedQuests.clear();
    quests.forEach(savedQuest -> {
      Quest quest = Quest.getQUEST_INDEX().get((Integer)jsonUtil.getInt((JSONObject)savedQuest, JSONUtil.ID));
      quest.setStatus(
        SAVE_VALUE_TO_QUEST_STATUS_MAP.get(
          jsonUtil.getString((JSONObject)savedQuest, JSONUtil.STATUS)
        )
      );
      acceptedQuests.put(
        jsonUtil.getInt((JSONObject)savedQuest, JSONUtil.ID),
        quest
      );
    });
  }

  @Override
  public void hydrate(JSONObject json) {
    quests = (JSONArray) json.get(JSONUtil.QUESTS);
    setQuestData();
  }

  @Override
  public void populateSaveData(JSONObject saveObject) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
