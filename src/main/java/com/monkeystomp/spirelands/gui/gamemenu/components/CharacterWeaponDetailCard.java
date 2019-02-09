package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterWeaponDetailCard extends CharacterEquipmentCard {
  
  private final Consumer<Item> IShowItemDetail;
  private final EquippedItemSlot equippedWeaponSlot = new EquippedItemSlot(
    cardCenterHoriz,
    140,
    () -> character.getEquippedWeapon(),
    () -> showEquippedItemDetails(),
    () -> character.removeEquippedWeapon()
  );
  
  public CharacterWeaponDetailCard(Consumer<Item> IShowItemDetail, Consumer<Character> ICharacterChanger) {
    super(ICharacterChanger);
    this.IShowItemDetail = IShowItemDetail;
  }
  
  private void showEquippedItemDetails() {
    IShowItemDetail.accept(character.getEquippedWeapon());
  }
  
  public void closePopover() {
    equippedWeaponSlot.closePopover();
  }
  
  @Override
  public void update() {
    super.update();
    equippedWeaponSlot.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    equippedWeaponSlot.render(screen, gl);
  }

}
