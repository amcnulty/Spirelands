package com.monkeystomp.spirelands.tests.level.entity.fixed;

import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.level.entity.fixed.Chest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ChestTest {
  @Test
  public void interactingWithChestIncreasesInventoryTest() {
    Chest testChest = new Chest(0, 0, Chest.COMMON_METAL_CHEST, Item.HEALTH_POTION);
    int healthPotionStock = InventoryManager.getInventoryManager().getAmountById(Item.HEALTH_POTION.getId());
    Assert.assertEquals("Should equal zero before chest is interacted with", 0, healthPotionStock);
    testChest.interact();
    healthPotionStock = InventoryManager.getInventoryManager().getAmountById(Item.HEALTH_POTION.getId());
    Assert.assertEquals("Should equal 1 after interacting with chest", 1, healthPotionStock);
  }
  @Test
  public void chestOpenFlagIsWorkingAfterInteractingWithChestTest() {
    Chest testChest = new Chest(0, 0, Chest.WOODEN_CHEST, Item.BRIEFCASE);
    boolean isChestOpen = testChest.isIsChestOpen();
    Assert.assertFalse("Should be false before chest has been opened", isChestOpen);
    testChest.interact();
    isChestOpen = testChest.isIsChestOpen();
    Assert.assertTrue("Should be true after interacting with chest", isChestOpen);
  }
}