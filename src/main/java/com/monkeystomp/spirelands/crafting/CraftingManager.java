package com.monkeystomp.spirelands.crafting;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataHydratable;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.gui.dialog.ToastLength;
import com.monkeystomp.spirelands.gui.dialog.ToastMessage;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Crafting manager is responsible for crafting save data and state management for crafting data.
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class CraftingManager implements SaveDataHydratable {
  /**
   * Slice of save object for this manager. JSON Name - CRAFTING
   */
  private JSONObject crafting;

  private final JSONUtil jsonUtil = new JSONUtil();
  /**
   * Current crafting level of save game state.
   */
  private int craftingLevel;
  /**
   * The highest possible crafting level.
   */
  private final int maxCraftingLevel = 3;
  /**
   * The amount of experience gained for discovering a new recipe.
   */
  private final int expPerDiscoveredRecipe = 5;
  /**
   * Crafting experience points of the save game data.
   */
  private int craftingExp;
  /**
   * Instance of sound effects for playing sounds.
   */
  private final SoundEffects sfx = new SoundEffects();
  /**
   * Collection of items that are currently on the crafting table.
   * This is tracked inventory quantity is taken into consideration when crafting.
   */
  private final ArrayList<Item> craftingTableItems = new ArrayList<>();
  /**
   * A map of crafting level to total experience to advance to the next level.
   */
  private static final HashMap<Integer, Integer> LEVEL_CRAFTING_EXP_MAP = new HashMap<>();
  static {
    LEVEL_CRAFTING_EXP_MAP.put(1, 30);
    LEVEL_CRAFTING_EXP_MAP.put(2, 50);
  }
  // A map of all ids to Recipe object which represents disoverd recipes.
  private final HashMap<Integer, Recipe> recipeMap = new HashMap<>();
  // The singleton instance of the class.
  private static final CraftingManager INSTANCE = new CraftingManager();
  
  private CraftingManager() {}
  /**
   * Gets the crafting manager instance.
   * @return The crafting manager instance.
   */
  public static CraftingManager getCraftingManager() {
    return INSTANCE;
  }
  /**
   * Current crafting level of save game state.
   * @return Current crafting level of save game state.
   */
  public int getCraftingLevel() {
    return craftingLevel;
  }
  /**
   * Crafting experience points of the save game data.
   * @return Crafting experience points of the save game data.
   */
  public int getCraftingExp() {
    return craftingExp;
  }
  /**
   * Gets the total amount of experience in the current level.
   * @return The total amount of experience in the current level.
   */
  public int getCraftingExpMax() {
    return LEVEL_CRAFTING_EXP_MAP.get(craftingLevel);
  }
  /**
   * Gets the total amount of experience in a given crafting level.
   * @param level The crafting level to reference.
   * @return The total amount of experience in a given crafting level.
   */
  public int getCraftingExpMax(int level) {
    return LEVEL_CRAFTING_EXP_MAP.get(level);
  }
  /**
   * Increases the crafting level by one.
   */
  private void increaseLevel() {
    craftingLevel++;
    ToastMessage.getToastMessage().addToast("Crafting level increased to level " + craftingLevel +"! You can now craft level " + craftingLevel + " recipes.", ToastLength.LONG);
    sfx.playSoundEffect(SoundEffects.STAT_UP);
  }
  /**
   * Adds the given amount of experience points to the current crafting experience.
   * @param amount Amount of crafting points to increase by.
   */
  public void addCraftingExp(int amount) {
    if (craftingLevel < maxCraftingLevel) {
      ToastMessage.getToastMessage().addToast("+ " + amount + " Crafting Experience!");
      sfx.playSoundEffect(SoundEffects.NOTIFICATION);
      craftingExpIncrease(amount);
    }
  }
  
  private void craftingExpIncrease(int amount) {
    if (craftingExp + amount >= getCraftingExpMax()) {
      int remaining = amount - (getCraftingExpMax() - craftingExp);
      increaseLevel();
      craftingExp = 0;
      craftingExpIncrease(remaining);
    }
    else {
      craftingExp += amount;
    }
  }
  /**
   * Adds a new recipe to the save game state list of discovered recipes.
   * @param recipe The recipe to be added to save game state.
   */
  private void addRecipeToMap(Recipe recipe) {
    recipeMap.put(recipe.getId(), recipe);
  }
  /**
   * A map of all the discovered recipes in the current save game state.
   * @return A map of all the discovered recipes in the current save game state.
   */
  public HashMap<Integer, Recipe> getDiscoveredRecipes() {
    return recipeMap;
  }
  /**
   * Checks given input items to see if they make up a recipe combination.
   * @param inputs Array of craftable items to check if they create a recipe.
   * @return The recipe that is created otherwise returns null.
   */
  public Recipe checkForRecipe(Item[] inputs) {
    Optional<Recipe> possibleRecipe = Recipe.getRECIPE_INDEX().entrySet().stream()
      .filter(map -> {
        if (map.getValue().getClass().equals(CompoundRecipe.class)) {
          if (compareInputsForMatch(((CompoundRecipe)map.getValue()).getAlternateInputs(), inputs)) {
            return true;
          }
        }
        return compareInputsForMatch(map.getValue().getInputs(), inputs);
      })
      .map(entry -> entry.getValue())
      .findFirst(); 
    if (possibleRecipe.isPresent()) {
      handleIfRecipeIsUndiscovered(possibleRecipe.get());
      return possibleRecipe.get();
    }
    return null;
  }
  
  private boolean compareInputsForMatch(List<Item> recipeInputs, Item[] userInputs) {
    if (recipeInputs.size() == userInputs.length) {
      return Arrays.asList(userInputs).containsAll(recipeInputs) && recipeInputs.containsAll(Arrays.asList(userInputs));
    }
      return false;
  }
  /**
   * Checks if the given recipe is undiscovered to do the appropriate actions if not.
   * @param recipe The recipe to check if it is discovered.
   */
  private void handleIfRecipeIsUndiscovered(Recipe recipe) {
    if (!recipeMap.containsValue(recipe)) {
      ToastMessage.getToastMessage().addToast("New recipe discovered: " + recipe.getName());
      addRecipeToMap(recipe);
      addCraftingExp(this.expPerDiscoveredRecipe);
    }
  }
  /**
   * A collection of items that are currently on the crafting table.
   * @return A collection of items that are currently on the crafting table.
   */
  public ArrayList<Item> getCraftingTableItems() {
    return craftingTableItems;
  }
  /**
   * Adds the given item to the collection of items on the crafting table.
   * @param item Item to be added to the crafting table.
   */
  public void addItemToTable(Item item) {
    craftingTableItems.add(item);
  }
  /**
   * Removes the given item from the collection of items on the crafting table.
   * @param item Item to be removed from the crafting table.
   */
  public void removeItemFromTable(Item item) {
    craftingTableItems.remove(item);
  }
  /**
   * Crafts the given inputs with the selected recipe.
   * This will remove the input items from inventory and create an output item in inventory.
   * @param inputs Array of craftable items to use in recipe.
   * @param recipe The recipe that is being crafted.
   */
  public void craft(Item[] inputs, Recipe recipe) {
    System.out.println("Actually removing inputs from inventory and adding newly crafted item.");
  }
  
  private void setCraftingData() {
    recipeMap.clear();
    craftingLevel = jsonUtil.getInt(crafting, JSONUtil.CRAFTING_LEVEL);
    craftingExp = jsonUtil.getInt(crafting, JSONUtil.CRAFTING_EXP);
    JSONArray discoveredRecipes = (JSONArray) crafting.get(JSONUtil.DISCOVERED_RECIPES);
    discoveredRecipes.forEach(recipe -> addRecipeToMap(Recipe.getRECIPE_INDEX().get(jsonUtil.getInt((JSONObject)recipe, JSONUtil.ID))));
  }

  @Override
  public void hydrate(JSONObject json) {
    crafting = (JSONObject) json.get(JSONUtil.CRAFTING);
    setCraftingData();
  }

  @Override
  public void populateSaveData(JSONObject saveObject) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
