package com.monkeystomp.spirelands.gamedata.values;


import com.monkeystomp.spirelands.battle.move.BattleMove;
import java.util.HashMap;

/**
 * The GameValues class is used to hold various game related values for easy access for game balancing.
 * @author Aaron Michael McNulty
 */
public class GameValues {
  /**
   * Used for getting the ability points needed to upgrade or unlock an ability slot based on the type and level of the slot.
   * <p>Keys are represented as a compound String with the following format:</p>
   * <pre>{level of slot}_{type of slot}</pre>
   * <h3>Example:</h3>
   * <p>The key for accessing the ability points needed to upgrade a level 2 slot with the type of 'Magical':</p>
   * <pre>'2_Magical'</pre>
   */
  public static final HashMap<String, Integer> ABILITY_SLOT_UPGRADE_POINTS_MAP = new HashMap<>();
  static {
    // Physical type slots
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("0_" + BattleMove.PHYSICAL, 75);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("1_" + BattleMove.PHYSICAL, 150);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("2_" + BattleMove.PHYSICAL, 300);
    // Magical type slots
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("0_" + BattleMove.MAGICAL, 75);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("1_" + BattleMove.MAGICAL, 150);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("2_" + BattleMove.MAGICAL, 300);
    // Butt type slots
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("0_" + BattleMove.BUFF, 75);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("1_" + BattleMove.BUFF, 150);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("2_" + BattleMove.BUFF, 300);
    // Item type slots
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("1_" + BattleMove.ITEM, 150);
    ABILITY_SLOT_UPGRADE_POINTS_MAP.put("2_" + BattleMove.ITEM, 300);
  }
  /**
   * Used to get the total number of item moves that can be equipped in the item slot based on the level of the slot.
   * <p>Keys are the level of the item slot</p>
   */
  public static final HashMap<Integer, Integer> AVAILABLE_ITEM_SLOTS_PER_LEVEL_MAP = new HashMap<>();
  static {
    AVAILABLE_ITEM_SLOTS_PER_LEVEL_MAP.put(1, 1);
    AVAILABLE_ITEM_SLOTS_PER_LEVEL_MAP.put(2, 3);
    AVAILABLE_ITEM_SLOTS_PER_LEVEL_MAP.put(3, 5);
  }

}
