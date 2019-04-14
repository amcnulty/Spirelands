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
    setReadyGaugeMax();
  }
  
  private void setReadyGaugeMax() {
    switch (character.getSpeed() / 25) {
      // 4.7 seconds
      case 0:
        readyGaugeMax = 282;
      break;
      // 4.2 seconds
      case 1:
        readyGaugeMax = 252;
      break;
      // 3.7 seconds
      case 2:
        readyGaugeMax = 222;
      break;
      // 3.3 seconds
      case 3:
        readyGaugeMax = 198;
      break;
      // 2.8 seconds
      case 4:
        readyGaugeMax = 168;
      break;
      // 2.3 seconds
      case 5:
        readyGaugeMax = 138;
      break;
      // 1.8 seconds
      case 6:
        readyGaugeMax = 108;
      break;
      // 1.4 seconds
      case 7:
        readyGaugeMax = 84;
      break;
      // 1.2 seconds
      case 8:
        readyGaugeMax = 72;
      break;
      // 1.1 second
      case 9:
        readyGaugeMax = 66;
      break;
      // 1 second
      case 10:
        readyGaugeMax = 60;
      break;
      default:
        readyGaugeMax = 60;
    }
  }

  public Character getCharacter() {
    return character;
  }

}
