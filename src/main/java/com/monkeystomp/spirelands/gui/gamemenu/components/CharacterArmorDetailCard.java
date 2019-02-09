package com.monkeystomp.spirelands.gui.gamemenu.components;

import java.util.function.Consumer;
import com.monkeystomp.spirelands.character.Character;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterArmorDetailCard extends CharacterEquipmentCard {
  
  public CharacterArmorDetailCard(Consumer<Character> ICharacterChanger) {
    super(ICharacterChanger);
  }
  
  public void closePopovers() {
    System.out.println("Closing popovers here!");
  }

}
