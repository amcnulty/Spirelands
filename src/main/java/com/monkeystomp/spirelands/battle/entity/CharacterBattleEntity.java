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
      // 9.4 seconds
      case 0:
        readyGaugeMax = 564;
      break;
      // 8.4 seconds
      case 1:
        readyGaugeMax = 504;
      break;
      // 7.4 seconds
      case 2:
        readyGaugeMax = 444;
      break;
      // 6.6 seconds
      case 3:
        readyGaugeMax = 396;
      break;
      // 5.6 seconds
      case 4:
        readyGaugeMax = 336;
      break;
      // 4.6 seconds
      case 5:
        readyGaugeMax = 276;
      break;
      // 3.6 seconds
      case 6:
        readyGaugeMax = 216;
      break;
      // 2.8 seconds
      case 7:
        readyGaugeMax = 168;
      break;
      // 2.4 seconds
      case 8:
        readyGaugeMax = 144;
      break;
      // 2.2 second
      case 9:
        readyGaugeMax = 132;
      break;
      // 2 second
      case 10:
        readyGaugeMax = 120;
      break;
      default:
        readyGaugeMax = 120;
    }
  }

  public Character getCharacter() {
    return character;
  }

}
