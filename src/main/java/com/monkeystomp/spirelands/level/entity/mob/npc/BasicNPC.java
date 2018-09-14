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
              anim = 0;

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
    characterActions.put("STANDING_0", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY + 3, characterSheet));
    characterActions.put("STANDING_1", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY + 2, characterSheet));
    characterActions.put("STANDING_2", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY, characterSheet));
    characterActions.put("STANDING_3", new Sprite(SPRITE_SIZE, sheetX + 1, sheetY + 1, characterSheet));
    // Walking sprites.
    // Up
    characterActions.put("WALKING_0_0", new Sprite(SPRITE_SIZE, sheetX, sheetY + 3, characterSheet));
    characterActions.put("WALKING_0_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY + 3, characterSheet));
    // Right
    characterActions.put("WALKING_1_0", new Sprite(SPRITE_SIZE, sheetX, sheetY + 2, characterSheet));
    characterActions.put("WALKING_1_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY + 2, characterSheet));
    // Down
    characterActions.put("WALKING_2_0", new Sprite(SPRITE_SIZE, sheetX, sheetY, characterSheet));
    characterActions.put("WALKING_2_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY, characterSheet));
    // Left
    characterActions.put("WALKING_3_0", new Sprite(SPRITE_SIZE, sheetX, sheetY + 1, characterSheet));
    characterActions.put("WALKING_3_1", new Sprite(SPRITE_SIZE, sheetX + 2, sheetY + 1, characterSheet));
    setBounds();
  }
    
  private void setBounds() {
    bounds[0] = y - SPRITE_SIZE / 2;
    bounds[1] = x + SPRITE_SIZE / 2;
    bounds[2] = y + SPRITE_SIZE / 2;
    bounds[3] = x - SPRITE_SIZE / 2;
    moveBounds[0] = y;
    moveBounds[1] = x + 10;
    moveBounds[2] = y + 15;
    moveBounds[3] = x - 10;
  }
  
  @Override
  public void update() {
    if (!fixedDirection) {
      if (anim > 120 + randTime) {
        anim = 0;
        randTime = random.nextInt(240);
        currentAction = "STANDING_" + random.nextInt(4);
      }
      anim++;
    }
  }
}