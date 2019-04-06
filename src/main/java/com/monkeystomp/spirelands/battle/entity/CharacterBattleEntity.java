package com.monkeystomp.spirelands.battle.entity;

import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.character.Character;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterBattleEntity extends BattleEntity {
  
  private final Character character;
  
  public CharacterBattleEntity(SpawnCoordinate slot, Character character) {
    super(slot, character.getBattleSheet());
    this.character = character;
  }

}
