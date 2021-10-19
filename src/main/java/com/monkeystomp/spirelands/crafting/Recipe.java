package com.monkeystomp.spirelands.crafting;

import com.monkeystomp.spirelands.inventory.Item;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A recipe is a combination of craftable items (inputs) that are consumed to create a new item (output).
 * All recipes have a crafting level associated with them that must be satisfied in order to craft.
 * @author Aaron Michael McNulty
 */
public class Recipe {
  /**
   * Recipe name which is equal to the title of the output item.
   */
  private final String name;
  /**
   * List of craftable items that make up the inputs of this recipe.
   */
  private final List<Item> inputs;
  /**
   * Output item that will be created from input item.
   */
  private final Item output;
  /**
   * Minimum crafting level needed to craft this recipe.
   */
  private final int craftingLevel;
  /**
   * Flag for if this item has ever been crafted in the current save game.
   */
  private boolean crafted = false;
  /**
   * Unique id of this recipe.
   */
  private final int id;
  /**
   * Next id to be given to instance of Recipe.
   */
  private static int nextId = 1000;
  /**
   * All recipe instances mapped by their id.
   */
  private static final HashMap<Integer, Recipe> RECIPE_INDEX = new HashMap<>();
  
  /**
   * Creates a new crafting recipe with the given input items, output item, and minimum crafting level.
   * @param inputs ArrayList of craftable items to be used in the recipe.
   * @param output Item to be created from this recipe.
   * @param craftingLevel Minimum crafting level required to craft this recipe.
   */
  public Recipe(Item[] inputs, Item output, int craftingLevel) {
    this.name = output.getTitle();
    this.inputs = Arrays.asList(inputs);
    this.output = output;
    this.craftingLevel = craftingLevel;
    this.id = nextId++;
    RECIPE_INDEX.put(id, this);
  }

  public String getName() {
    return name;
  }
  /**
   * List of craftable items that make up the inputs of this recipe.
   * @return List of craftable items that make up the inputs of this recipe.
   */
  public List<Item> getInputs() {
    return inputs;
  }
  /**
   * Output item that will be created from input item.
   * @return Output item that will be created from input item.
   */
  public Item getOutput() {
    return output;
  }
  /**
   * Minimum crafting level needed to craft this recipe.
   * @return Minimum crafting level needed to craft this recipe.
   */
  public int getCraftingLevel() {
    return craftingLevel;
  }
  /**
   * Flag for if this item has ever been crafted in the current save game.
   * @return Flag for if this item has ever been crafted in the current save game.
   */
  public boolean hasBeenCrafted() {
    return crafted;
  }
  /**
   * Setting a recipe to crafted means it has been crafted once during the current save game.
   */
  public void setCrafted(boolean crafted) {
    this.crafted = crafted;
  }
  /**
   * Unique id of this recipe.
   * @return Unique id of this recipe.
   */
  public int getId() {
    return id;
  }
  /**
   * All recipe instances mapped by their id.
   * @return All recipe instances mapped by their id.
   */
  public static HashMap<Integer, Recipe> getRECIPE_INDEX() {
    return RECIPE_INDEX;
  }
  
  /**
   * Put list of recipes starts here.
   */
  static {
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!          LV 1 Recipes          !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Small HP Potion
   */
    new Recipe(
      new Item[]{Item.WATER_BOTTLE, Item.LV1_HEALING_POWDER, Item.LV1_HEALING_POWDER},
      Item.SMALL_HP_POTION,
      1
    );
  /**
   * Mana Vile
   */
    new Recipe(
      new Item[]{Item.ENCHANTED_WATER, Item.LV1_MAGIC_POWDER, Item.LV1_MAGIC_POWDER},
      Item.MANA_VILE,
      1
    );
  /**
   * Fire Bottle
   */
    new Recipe(
      new Item[]{Item.EMPTY_BOTTLE, Item.WOOD_TAR, Item.CLOTH},
      Item.FIRE_BOTTLE,
      1
    );
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!          LV 2 Recipes          !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Medium HP Potion
   */
    new CompoundRecipe(
      new Item[]{Item.WATER_BOTTLE, Item.LV2_HEALING_POWDER, Item.LV2_HEALING_POWDER},
      Item.MEDIUM_HP_POTION,
      2
    ).addAlternateInputs(
      new Item[]{Item.SMALL_HP_POTION, Item.LV2_HEALING_POWDER}
    );
  /**
   * Mana Potion
   */
    new CompoundRecipe(
      new Item[]{Item.ENCHANTED_WATER, Item.LV2_MAGIC_POWDER, Item.LV2_MAGIC_POWDER},
      Item.MANA_POTION,
      2
    ).addAlternateInputs(
      new Item[]{Item.MANA_VILE, Item.LV2_MAGIC_POWDER}
    );
  /**
   * Omelette
   */
    new Recipe(
      new Item[]{Item.MEAT, Item.CHEESE, Item.THROWING_EGG},
      Item.OMELETTE,
      2
    );
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!          LV 2 Recipes          !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Health Bottle
   */
    new CompoundRecipe(
      new Item[]{Item.WATER_BOTTLE, Item.LV3_HEALING_POWDER, Item.LV3_HEALING_POWDER},
      Item.HEALTH_BOTTLE,
      3
    ).addAlternateInputs(
      new Item[]{Item.MEDIUM_HP_POTION, Item.LV3_HEALING_POWDER}
    );
  /**
   * Mana Bottle
   */
    new CompoundRecipe(
      new Item[]{Item.ENCHANTED_WATER, Item.LV3_MAGIC_POWDER, Item.LV3_MAGIC_POWDER},
      Item.MANA_BOTTLE,
      3
    ).addAlternateInputs(
      new Item[]{Item.MANA_POTION, Item.LV3_MAGIC_POWDER}
    );
  }
  
}
