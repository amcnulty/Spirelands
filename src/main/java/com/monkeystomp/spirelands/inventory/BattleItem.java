package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import java.io.File;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleItem extends Item {

  private final SoundEffects sfx = new SoundEffects();
  private File useSound;
  private int powerLevel;
  private String  elementalType,
                  battleMoveType;

  public BattleItem(ItemBuilder builder) {
    super(builder.type(Item.BATTLE));
  }
  
  @Override
  public int getId() {
    return super.getId();
  }
  
  public void setUseItemSound(File soundEffect) {
    this.useSound = soundEffect;
  }
  
  public void setPhysicalDamage(int amount) {
    powerLevel = amount;
    battleMoveType = BattleMove.PHYSICAL;
    attributes.add(new ItemAttribute(ItemAttribute.ATTACK_POWER, amount));
  }
  
  public void setFireDamage(int amount) {
    powerLevel = amount;
    elementalType = "Fire";
    battleMoveType = BattleMove.MAGICAL;
    attributes.add(new ItemAttribute(ItemAttribute.FIRE_DAMAGE, amount));
  }
  
  public int getPowerLevel() {
    return powerLevel;
  }

  public String getElementalType() {
    return elementalType;
  }

  public String getBattleMoveType() {
    return battleMoveType;
  }
  
  public void useItem() {
    if (INVENTORY_MANAGER.getInventoryReferenceById(getId()) != null) {
      if (useSound != null) sfx.playSoundEffect(useSound);
      INVENTORY_MANAGER.removeFromInventory(this);
    }
  }

}
