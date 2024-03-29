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
    Chest testChest = new Chest(0, 0, Chest.COMMON_METAL_CHEST, Item.SMALL_HP_POTION);
    int healthPotionStock = InventoryManager.getInventoryManager().getAmountById(Item.SMALL_HP_POTION.getId());
    Assert.assertEquals("Should equal zero before chest is interacted with", 0, healthPotionStock);
    testChest.interact();
    healthPotionStock = InventoryManager.getInventoryManager().getAmountById(Item.SMALL_HP_POTION.getId());
    Assert.assertEquals("Should equal 1 after interacting with chest", 1, healthPotionStock);
  }
  @Test
  public void chestOpenFlagIsWorkingAfterInteractingWithChestTest() {
    Chest testChest = new Chest(0, 0, Chest.WOODEN_CHEST, Item.BRIEFCASE);
    boolean isChestOpen = testChest.isChestOpen();
    Assert.assertFalse("Should be false before chest has been opened", isChestOpen);
    testChest.interact();
    isChestOpen = testChest.isChestOpen();
    Assert.assertTrue("Should be true after interacting with chest", isChestOpen);
  }
  @Test
  public void interactingWithGoldChestIncreasesGoldTest() {
    Chest testChest = new Chest(0, 0, Chest.WOODEN_CHEST, null);
    testChest.addGold(500);
    InventoryManager.getInventoryManager().setGold(0);
    int goldStock = InventoryManager.getInventoryManager().getGold();
    Assert.assertEquals("Should equal zero gold before interacting with chest", 0, goldStock);
    testChest.interact();
    goldStock = InventoryManager.getInventoryManager().getGold();
    Assert.assertEquals("Should equal 500 after interacting with chest", 500, goldStock);
  }
}