package com.monkeyStomp.spirelands.level.entity.mob.npc;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BasicNPC extends NPC {
  
  private final int SPRITE_SIZE = 32;
  public static final int MALE_REDHAIR = 0,
                          FEMALE_GREENHAIR = 1,
                          FEMALE_PURPLEHAIR = 2,
                          FEMALE_GREYHAIR = 3,
                          FEMALE_DARKREDHAIR = 4,
                          FEMALE_BLONDEHAIR = 5,
                          MALE_DARKBLUEHAIR = 6,
                          MALE_ELF = 7,
                          FEMALE_ELF = 8,
                          MALE_GUARD = 9,
                          FEMALE_CATLADY = 10,
                          MALE_BLONDE = 11,
                          FEMALE_LIGHTREDHAIR = 12,
                          MALE_YELLOWJACKET = 13;
  
  private SpriteSheet characterSheet = new SpriteSheet("./resources/characters/basicNpcs.png");
  private int randTime = random.nextInt(240),
              walkingSteps = 2,
              framesPerStep = 12;

  public BasicNPC(NPCConfig config, int character) {
    super(config);
    spriteSize = SPRITE_SIZE;
    generateCharacter(character);
  }
  
  private void generateCharacter(int character) {
    int sheetX = character % 7 * 3,
        sheetY = (character / 7) * 4;
    
    // Fill the characterActions hashmap with all the sprites associated with various string action keys.
    // Standing sprites.
    characterActions.put("STANDING_0", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY, characterSheet));
    characterActions.put("STANDING_1", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY + 1, characterSheet));
    characterActions.put("STANDING_2", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY + 2, characterSheet));
    characterActions.put("STANDING_3", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY + 3, characterSheet));
    // Walking sprites.
    // Up
    characterActions.put("WALKING_0_0", new Sprite(SPRITE_SIZE, sheetX, sheetY, characterSheet));
    characterActions.put("WALKING_0_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY, characterSheet));
    // Right
    characterActions.put("WALKING_1_0", new Sprite(SPRITE_SIZE, sheetX, sheetY + 1, characterSheet));
    characterActions.put("WALKING_1_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY + 1, characterSheet));
    // Down
    characterActions.put("WALKING_2_0", new Sprite(SPRITE_SIZE, sheetX, sheetY + 2, characterSheet));
    characterActions.put("WALKING_2_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY + 2, characterSheet));
    // Left
    characterActions.put("WALKING_3_0", new Sprite(SPRITE_SIZE, sheetX, sheetY + 3, characterSheet));
    characterActions.put("WALKING_3_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY + 3, characterSheet));
    setBounds();
  }
    
  protected void setBounds() {
    bounds[0] = y - SPRITE_SIZE / 2;
    bounds[1] = x + SPRITE_SIZE / 2;
    bounds[2] = y + SPRITE_SIZE / 2;
    bounds[3] = x - SPRITE_SIZE / 2;
    moveBounds[0] = SPRITE_SIZE / 2;
    moveBounds[1] = SPRITE_SIZE / 2;
    moveBounds[2] = SPRITE_SIZE / 2;
    moveBounds[3] = SPRITE_SIZE / 2;
  }
  
  protected void setCurrentAction() { 
    if (!walking) {
      currentAction = "STANDING_" + direction;
      if (anim > 120 + randTime) {
        anim = 0;
        randTime = random.nextInt(240);
        direction = random.nextInt(4);
      }
    }
    else {
      currentAction = "WALKING_" + direction + "_" + stepIndex;
      if (anim % framesPerStep == 0) {
        stepIndex = (anim % (walkingSteps * framesPerStep)) / framesPerStep;
      }
    }
  }
}