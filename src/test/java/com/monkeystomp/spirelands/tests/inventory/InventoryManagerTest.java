package com.monkeystomp.spirelands.tests.inventory;

import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class InventoryManagerTest {
  private InventoryManager manager = InventoryManager.getInventoryManager();
  @Test
  public void addWeaponToInventoryTest() {
    Item testWeapon = Item.COMMON_SWORD;
    int quantity;
    try {
      quantity = manager.getItemsByType(Item.WEAPON).get(testWeapon.getId()).getAmount();
    }
    catch (NullPointerException e) {
      e.printStackTrace();
      quantity = 0;
    }
    Assert.assertEquals("Should equal zero before weapon has been added to inventory", 0, quantity);
    manager.addToInventory(testWeapon);
    quantity = manager.getItemsByType(Item.WEAPON).get(testWeapon.getId()).getAmount();
    Assert.assertEquals("Should equal 1 after adding weapon to inventory", 1, quantity);
    manager.addToInventory(testWeapon);
    manager.addToInventory(testWeapon);
    Assert.assertEquals("Should equal 3 after adding two more weapons to inventory", 1, quantity);
  }
  
  @Test
  public void addDifferentWeaponTypeTest() {
    Item testWeapon = Item.AXE_CLUB;
    int quantity;
    try {
      quantity = manager.getItemsByType(Item.WEAPON).get(testWeapon.getId()).getAmount();
    }
    catch(NullPointerException e) {
      e.printStackTrace();
      quantity = 0;
    }
    Assert.assertEquals("Should equal zero before weapon has been added to inventory", 0, quantity);
    manager.addToInventory(testWeapon);
    quantity = manager.getItemsByType(Item.WEAPON).get(testWeapon.getId()).getAmount();
    Assert.assertEquals("Should equal 1 after adding weapon to inventory", 1, quantity);
    manager.addToInventory(testWeapon);
    manager.addToInventory(testWeapon);
    Assert.assertEquals("Should equal 3 after adding two more weapons to inventory", 1, quantity);
  }
  
  @Test
  public void addArmorToInventory() {
    Item testArmor = Item.LEATHER_HELMET;
    int quantity;
    try {
      quantity = manager.getItemsByType(Item.ARMOR).get(testArmor.getId()).getAmount();
    }
    catch (NullPointerException e) {
      e.printStackTrace();
      quantity = 0;
    }
    Assert.assertEquals("Should equal zero before armor has been added to inventory", 0, quantity);
    manager.addToInventory(testArmor);
    quantity = manager.getItemsByType(Item.ARMOR).get(testArmor.getId()).getAmount();
    Assert.assertEquals("Should equal 1 after adding armor to inventory", 1, quantity);
    manager.addToInventory(testArmor);
    manager.addToInventory(testArmor);
    Assert.assertEquals("Should equal 3 after adding two more armor to inventory", 1, quantity);
  }
  
  @Test
  public void getAmountByIdTest() {
    Item testItem = Item.MESSAGE_SCROLL;
    int quantity = manager.getAmountById(testItem.getId());
    Assert.assertEquals("Should equal zero before item has been added to inventory", 0, quantity);
    manager.addToInventory(testItem);
    quantity = manager.getAmountById(testItem.getId());
    Assert.assertEquals("Should equal 1 after adding item to inventory", 1, quantity);
    manager.addToInventory(testItem);
    manager.addToInventory(testItem);
    quantity = manager.getAmountById(testItem.getId());
    Assert.assertEquals("Should equal 3 after adding two more items to inventory.", 3, quantity);
  }
  
  @Test
  public void removeItemFromInventoryTest() {
    Item testItem = Item.COOKIE;
    int quantity = manager.getAmountById(testItem.getId());
    Assert.assertEquals("Should equal 0 before item has been added to inventory", 0, quantity);
    manager.addToInventory(testItem);
    quantity = manager.getAmountById(testItem.getId());
    Assert.assertEquals("Should equal 1 after item has been added to inventory", 1, quantity);
    manager.removeFromInventory(testItem);
    quantity = manager.getAmountById(testItem.getId());
    Assert.assertEquals("Should equal 0 after item has been removed from inventory", 0, quantity);
    Map testMap = manager.getItemsByType(testItem.getType());
    Assert.assertFalse("Should be false if there is no items with this id in the response", testMap.containsKey(testItem.getId()));
  }
}