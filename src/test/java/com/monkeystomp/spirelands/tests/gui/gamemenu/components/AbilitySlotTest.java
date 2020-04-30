package com.monkeystomp.spirelands.tests.gui.gamemenu.components;

import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.gui.gamemenu.components.AbilitySlot;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class AbilitySlotTest {

  @Test
  public void createAbilitySlotTest() {
    AbilitySlot slot1 = null;
    AbilitySlot slot2 = null;
    AbilitySlot slot3 = null;
    AbilitySlot slot4 = null;
    AbilitySlot failSlot = null;
    try {
      slot1 = new AbilitySlot(BattleMove.PHYSICAL, 1);
      slot2 = new AbilitySlot(BattleMove.MAGICAL, 1);
      slot3 = new AbilitySlot(BattleMove.BUFF, 1);
      slot4 = new AbilitySlot(BattleMove.ITEM, 1);
      failSlot = new AbilitySlot("my bad type argument", 1);
    }
    catch (IllegalArgumentException e) {
      Assert.assertTrue("Should throw an error if provided with bad arguments", true);
    }
    Assert.assertTrue("Passing a type of 'Physical' should create an ability slot", slot1 != null);
    Assert.assertTrue("Passing a type of 'Magical' should create an ability slot", slot2 != null);
    Assert.assertTrue("Passing a type of 'Buff' should create an ability slot", slot3 != null);
    Assert.assertTrue("Passing a type of 'Item' should create an ability slot", slot4 != null);
    Assert.assertEquals("Passing a type of 'my bad type argument' should not create an ability slot", null, failSlot);
  }
}
