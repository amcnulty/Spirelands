package com.monkeystomp.spirelands.tests.gui.gamemenu.components;

import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.gui.gamemenu.components.AbilitySlot;
import com.monkeystomp.spirelands.gui.gamemenu.components.UpgradeAbilitySlotPanel;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class UpgradeAbilitySlotPanelTest {
  UpgradeAbilitySlotPanel slotPanel = new UpgradeAbilitySlotPanel(0,0);
  @Test
  public void buttonTextTest() {
    slotPanel.updatePanel(new AbilitySlotClickEvent(0, BattleMove.PHYSICAL, new AbilitySlot(0, 0, BattleMove.PHYSICAL, null)));
    Assert.assertEquals("Level zero ability slot event should show 'Unlock' for the button text", "Unlock", slotPanel.getButtonText());
    slotPanel.updatePanel(new AbilitySlotClickEvent(1, BattleMove.PHYSICAL, new AbilitySlot(0, 0, BattleMove.PHYSICAL, null)));
    Assert.assertEquals("Level zero ability slot event should show 'Upgrade' for the button text", "Upgrade", slotPanel.getButtonText());
  }
  
  @Test
  public void panelVisibilityTest() {
    slotPanel.updatePanel(new AbilitySlotClickEvent(1, BattleMove.PHYSICAL, new AbilitySlot(0, 0, BattleMove.PHYSICAL, null)));
    Assert.assertTrue("Level one ability slots should render the panel", slotPanel.isShowing());
    slotPanel.updatePanel(new AbilitySlotClickEvent(100, BattleMove.PHYSICAL, new AbilitySlot(0, 0, BattleMove.PHYSICAL, null)));
    Assert.assertFalse("Level that is out of range for example 100 will not show the panel", slotPanel.isShowing());
  }
}
