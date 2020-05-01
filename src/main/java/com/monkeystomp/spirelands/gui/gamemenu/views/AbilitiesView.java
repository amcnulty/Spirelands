package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.CharacterAbilityCard;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gui.gamemenu.components.AbilityList;
import com.monkeystomp.spirelands.gui.gamemenu.components.UpgradeAbilitySlotPanel;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;

/**
 * The abilities view is shown in the game menu when users click the Abilities button.
 * @author amcnu
 */
public class AbilitiesView extends DisplayView {
  // The abilities card with the character thumbnail and ability slots.
  private final CharacterAbilityCard abilityCard =
    new CharacterAbilityCard(
      top,
      left,
      nextCharacter -> setCharacter(nextCharacter),
      event -> handleAbilitySlotClick(event)
    );
  // The panel below the abilities card for showing the upgrade button.
  private final UpgradeAbilitySlotPanel upgradeAbilitySlotPanel =
    new UpgradeAbilitySlotPanel(left, abilityCard.getCARD_BOTTOM());
  
  private void handleAbilitySlotClick(AbilitySlotClickEvent event) {
    upgradeAbilitySlotPanel.updatePanel(event);
    if (event.getLevel() > 0)
      abilityList.show(event);
    else
      abilityList.hide();
  }
  // The ability list.
  private final AbilityList abilityList = new AbilityList(upgradeAbilitySlotPanel.getPanelBottom(), left);

  @Override
  public void enteringView() {  
  }

  @Override
  public void exitingView() {
  }
  
  @Override
  public void setCharacter(Character character) {
    super.setCharacter(character);
    abilityCard.setCharacter(character);
    abilityList.hide();
    abilityList.setCharacter(character);
  }

  @Override
  public void update() {
    abilityCard.update();
    upgradeAbilitySlotPanel.update();
    abilityList.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    abilityCard.render(screen, gl);
    upgradeAbilitySlotPanel.render(screen, gl);
    abilityList.render(screen, gl);
  }
    
}
