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
  
  private final List<Item> inputs;
  private final Item output;
  private final int craftingLevel;
  private final int id;
  private static int nextId = 0;
  private static final HashMap<Integer, Recipe> RECIPE_INDEX = new HashMap<>();
  
  /**
   * Creates a new crafting recipe with the given input items, output item, and minimum crafting level.
   * @param inputs ArrayList of craftable items to be used in the recipe.
   * @param output Item to be created from this recipe.
   * @param craftingLevel Minimum crafting level required to craft this recipe.
   */
  public Recipe(Item[] inputs, Item output, int craftingLevel) {
    this.inputs = Arrays.asList(inputs);
    this.output = output;
    this.craftingLevel = craftingLevel;
    this.id = nextId++;
    RECIPE_INDEX.put(id, this);
  }

  public List<Item> getInputs() {
    return inputs;
  }

  public Item getOutput() {
    return output;
  }

  public int getCraftingLevel() {
    return craftingLevel;
  }

  public int getId() {
    return id;
  }

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
    new Recipe(
      new Item[]{Item.WATER_BOTTLE, Item.LV2_HEALING_POWDER, Item.LV2_HEALING_POWDER},
      Item.MEDIUM_HP_POTION,
      2
    );
  /**
   * Medium HP Potion alt
   */
    new Recipe(
      new Item[]{Item.SMALL_HP_POTION, Item.LV2_HEALING_POWDER},
      Item.MEDIUM_HP_POTION,
      2
    );
  /**
   * Mana Potion
   */
    new Recipe(
      new Item[]{Item.ENCHANTED_WATER, Item.LV2_MAGIC_POWDER, Item.LV2_MAGIC_POWDER},
      Item.MANA_POTION,
      2
    );
  /**
   * Mana Potion alt
   */
    new Recipe(
      new Item[]{Item.MANA_VILE, Item.LV2_MAGIC_POWDER},
      Item.MANA_POTION,
      2
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
    new Recipe(
      new Item[]{Item.WATER_BOTTLE, Item.LV3_HEALING_POWDER, Item.LV3_HEALING_POWDER},
      Item.HEALTH_BOTTLE,
      3
    );
  /**
   * Health Bottle alt
   */
    new Recipe(
      new Item[]{Item.MEDIUM_HP_POTION, Item.LV3_HEALING_POWDER},
      Item.HEALTH_BOTTLE,
      3
    );
  /**
   * Mana Bottle
   */
    new Recipe(
      new Item[]{Item.ENCHANTED_WATER, Item.LV3_MAGIC_POWDER, Item.LV3_MAGIC_POWDER},
      Item.MANA_BOTTLE,
      3
    );
  /**
   * Mana Bottle alt
   */
    new Recipe(
      new Item[]{Item.MANA_POTION, Item.LV3_MAGIC_POWDER},
      Item.MANA_BOTTLE,
      3
    );
  }
  
}
