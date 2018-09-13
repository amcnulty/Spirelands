package com.monkeystomp.spirelands.level.entity.mob;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class NPC extends Mob {
  
  private SpriteSheet characterSheet = new SpriteSheet("./resources/characters/character_sheet2.png");
  private String currentAction = "STANDING_2";
  private final int RANDOM_CHARACTER_SPRITE_SIZE = 32;
  private HashMap<String, Sprite> characterActions = new HashMap<>();
  private Random random = new Random();
  private int randTime = random.nextInt(240),
              anim = 0;

  public NPC(int x, int y) {
    this.x = x;
    this.y = y;
    generateRandomCharacter();
  }
  
  private void generateRandomCharacter() {
    int randomInt = random.nextInt(8),
        sheetX = randomInt % 4 * 3,
        sheetY = (randomInt / 4) * 4;
    
    // Fill the characterActions hashmap with all the sprites associated with various string action keys.
    // Standing sprites.
    characterActions.put("STANDING_0", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 1, sheetY + 3, characterSheet));
    characterActions.put("STANDING_1", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 1, sheetY + 2, characterSheet));
    characterActions.put("STANDING_2", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 1, sheetY, characterSheet));
    characterActions.put("STANDING_3", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 1, sheetY + 1, characterSheet));
    // Walking sprites.
    // Up
    characterActions.put("WALKING_0_0", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX, sheetY + 3, characterSheet));
    characterActions.put("WALKING_0_1", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 2, sheetY + 3, characterSheet));
    // Right
    characterActions.put("WALKING_1_0", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX, sheetY + 2, characterSheet));
    characterActions.put("WALKING_1_1", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 2, sheetY + 2, characterSheet));
    // Down
    characterActions.put("WALKING_2_0", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX, sheetY, characterSheet));
    characterActions.put("WALKING_2_1", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 2, sheetY, characterSheet));
    // Left
    characterActions.put("WALKING_3_0", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX, sheetY + 1, characterSheet));
    characterActions.put("WALKING_3_1", new Sprite(RANDOM_CHARACTER_SPRITE_SIZE, sheetX + 2, sheetY + 1, characterSheet));
    setBounds();
  }
    
  private void setBounds() {
    bounds[0] = y - RANDOM_CHARACTER_SPRITE_SIZE / 2;
    bounds[1] = x + RANDOM_CHARACTER_SPRITE_SIZE / 2;
    bounds[2] = y + RANDOM_CHARACTER_SPRITE_SIZE / 2;
    bounds[3] = x - RANDOM_CHARACTER_SPRITE_SIZE / 2;
    moveBounds[0] = y;
    moveBounds[1] = x + 10;
    moveBounds[2] = y + 15;
    moveBounds[3] = x - 10;
  }
  
  public void speak() {
    String[] words = {"Why did you run into me?", "I'm a gangster", "You are a dead man!"};
    level.getDialogBox().openDialog(words);
    level.setDialogOpen(true);
  }
  
  @Override
  public void update() {
    if (anim > 120 + randTime) {
      anim = 0;
      randTime = random.nextInt(240);
      currentAction = "STANDING_" + random.nextInt(4);
    }
    anim++;
  }
  
  @Override
  public void render(Screen screen) {
    screen.renderSprite(x - RANDOM_CHARACTER_SPRITE_SIZE / 2, y - RANDOM_CHARACTER_SPRITE_SIZE / 2, characterActions.get(currentAction), true);
  }
}
