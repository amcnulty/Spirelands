package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import java.util.function.Consumer;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.inventory.Item;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterArmorDetailCard extends CharacterEquipmentCard {
  
  private final Consumer<Item> IShowItemDetail;
  private final EquippedItemSlot helmetSlot = new EquippedItemSlot(
    cardCenterHoriz - 12,
    135,
    () -> character.getEquippedHelmet(),
    item -> showEquippedItemDetails(item),
    () -> character.unequipHelmet(),
    true
  ),
  chestplateSlot = new EquippedItemSlot(
    cardCenterHoriz + 12,
    135,
    () -> character.getEquippedChestplate(),
    item -> showEquippedItemDetails(item),
    () -> character.unequipChestplate(),
    true
  ),
  shieldSlot = new EquippedItemSlot(
    cardCenterHoriz - 12,
    159,
    () -> character.getEquippedShield(),
    item -> showEquippedItemDetails(item),
    () -> character.unequipShield(),
    false
  ),
  bootSlot = new EquippedItemSlot(
    cardCenterHoriz + 12,
    159,
    () -> character.getEquippedBoots(),
    item -> showEquippedItemDetails(item),
    () -> character.unequipBoots(),
    false
  );
  
  public CharacterArmorDetailCard(Consumer<Item> IShowItemDetail, Consumer<Character> ICharacterChanger) {
    super(ICharacterChanger);
    this.IShowItemDetail = IShowItemDetail;
  }
  
  private void showEquippedItemDetails(Item item) {
    IShowItemDetail.accept(item);
  }
  
  public void closePopovers() {
    helmetSlot.closePopover();
    chestplateSlot.closePopover();
    shieldSlot.closePopover();
    bootSlot.closePopover();
  }
  
  @Override
  public void update() {
    super.update();
    helmetSlot.update();
    chestplateSlot.update();
    shieldSlot.update();
    bootSlot.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    shieldSlot.render(screen, gl);
    bootSlot.render(screen, gl);
    helmetSlot.render(screen, gl);
    chestplateSlot.render(screen, gl);
  }

}
