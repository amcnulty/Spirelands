package com.monkeystomp.spirelands.crafting;

import com.monkeystomp.spirelands.inventory.Item;
import java.util.Arrays;
import java.util.List;

/**
 * A crafting recipe that has more than one set of inputs for the same output.
 * @author Aaron Michael McNulty
 */
public class CompoundRecipe extends Recipe {
  
  private List<Item> alternateInputs;

  public CompoundRecipe(Item[] inputs, Item output, int craftingLevel) {
    super(inputs, output, craftingLevel);
  }

  public List<Item> getAlternateInputs() {
    return alternateInputs;
  }

  public void addAlternateInputs(Item[] alternateInputs) {
    this.alternateInputs = Arrays.asList(alternateInputs);
  }

}
