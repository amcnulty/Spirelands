package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.CharacterAbilityCard;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gui.gamemenu.components.UpgradeAbilitySlotPanel;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;

/**
 *
 * @author amcnu
 */
public class AbilitiesView extends DisplayView {

  private final CharacterAbilityCard abilityCard =
    new CharacterAbilityCard(
      TOP,
      LEFT,
      nextCharacter -> setCharacter(nextCharacter),
      event -> handleAbilitySlotClick(event)
    );
  
  private final UpgradeAbilitySlotPanel upgradeAbilitySlotPanel =
    new UpgradeAbilitySlotPanel(LEFT, abilityCard.getCARD_BOTTOM());
  
  private void handleAbilitySlotClick(AbilitySlotClickEvent event) {
    upgradeAbilitySlotPanel.updatePanel(event);
  }

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
  }

  @Override
  public void update() {
    abilityCard.update();
    upgradeAbilitySlotPanel.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    abilityCard.render(screen, gl);
    upgradeAbilitySlotPanel.render(screen, gl);
  }
    
}
