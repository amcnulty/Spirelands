package com.monkeystomp.spirelands.level.entity.mob;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.Keyboard;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GoblinPlayer extends Player {
  
  private String currentAction = "STANDING_2";
  private short anim = 0;
  
  private final int PLAYER_WALKING_SPEED = 3,
                    PLAYER_RUNNING_SPEED = 6,
                    SPRITE_SIZE = 128;
    
  private int xDir,
              yDir,
              playerVelocity,
              walkingSteps = 6,
              framesPerStep = 6,
              animMax = walkingSteps * framesPerStep - 2;

  public GoblinPlayer(int x, int y) {
    super(x, y);
  }
  
  @Override
  protected void loadSpriteSheet() {
    characterSheet = new SpriteSheet("./resources/characters/goblin.png");
  }
    
  @Override
  protected void setupCharacterActions() {
    // Fill the characterActions hashmap with all the sprites associated with various string action keys.
    // Standing sprites.
    characterActions.put("STANDING_0", new Sprite(SPRITE_SIZE, 6, 2, characterSheet));
    characterActions.put("STANDING_1", new Sprite(SPRITE_SIZE, 6, 1, characterSheet));
    characterActions.put("STANDING_2", new Sprite(SPRITE_SIZE, 6, 0, characterSheet));
    characterActions.put("STANDING_3", new Sprite(SPRITE_SIZE, 6, 3, characterSheet));
    // Walking sprites.
    // Up
    characterActions.put("WALKING_0_0", new Sprite(SPRITE_SIZE, 0, 2, characterSheet));
    characterActions.put("WALKING_0_1", new Sprite(SPRITE_SIZE, 1, 2, characterSheet));
    characterActions.put("WALKING_0_2", new Sprite(SPRITE_SIZE, 2, 2, characterSheet));
    characterActions.put("WALKING_0_3", new Sprite(SPRITE_SIZE, 3, 2, characterSheet));
    characterActions.put("WALKING_0_4", new Sprite(SPRITE_SIZE, 4, 2, characterSheet));
    characterActions.put("WALKING_0_5", new Sprite(SPRITE_SIZE, 5, 2, characterSheet));
    // Right
    characterActions.put("WALKING_1_5", new Sprite(SPRITE_SIZE, 0, 1, characterSheet));
    characterActions.put("WALKING_1_0", new Sprite(SPRITE_SIZE, 1, 1, characterSheet));
    characterActions.put("WALKING_1_1", new Sprite(SPRITE_SIZE, 2, 1, characterSheet));
    characterActions.put("WALKING_1_2", new Sprite(SPRITE_SIZE, 3, 1, characterSheet));
    characterActions.put("WALKING_1_3", new Sprite(SPRITE_SIZE, 4, 1, characterSheet));
    characterActions.put("WALKING_1_4", new Sprite(SPRITE_SIZE, 5, 1, characterSheet));
    // Down
    characterActions.put("WALKING_2_5", new Sprite(SPRITE_SIZE, 0, 0, characterSheet));
    characterActions.put("WALKING_2_0", new Sprite(SPRITE_SIZE, 1, 0, characterSheet));
    characterActions.put("WALKING_2_1", new Sprite(SPRITE_SIZE, 2, 0, characterSheet));
    characterActions.put("WALKING_2_2", new Sprite(SPRITE_SIZE, 3, 0, characterSheet));
    characterActions.put("WALKING_2_3", new Sprite(SPRITE_SIZE, 4, 0, characterSheet));
    characterActions.put("WALKING_2_4", new Sprite(SPRITE_SIZE, 5, 0, characterSheet));
    // Left
    characterActions.put("WALKING_3_5", new Sprite(SPRITE_SIZE, 0, 3, characterSheet));
    characterActions.put("WALKING_3_0", new Sprite(SPRITE_SIZE, 1, 3, characterSheet));
    characterActions.put("WALKING_3_1", new Sprite(SPRITE_SIZE, 2, 3, characterSheet));
    characterActions.put("WALKING_3_2", new Sprite(SPRITE_SIZE, 3, 3, characterSheet));
    characterActions.put("WALKING_3_3", new Sprite(SPRITE_SIZE, 4, 3, characterSheet));
    characterActions.put("WALKING_3_4", new Sprite(SPRITE_SIZE, 5, 3, characterSheet));
  }
  
  private void checkMovementInput() {
    xDir = 0;
    yDir = 0;
    playerVelocity = Keyboard.isKeyPressed(Keyboard.LEFT_SHIFT_KEY) ? PLAYER_RUNNING_SPEED : PLAYER_WALKING_SPEED;
    if (Keyboard.isKeyPressed(Keyboard.A_KEY) || Keyboard.isKeyPressed(Keyboard.LEFT_KEY)) xDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.D_KEY) || Keyboard.isKeyPressed(Keyboard.RIGHT_KEY)) xDir += playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.W_KEY) || Keyboard.isKeyPressed(Keyboard.UP_KEY)) yDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.S_KEY) || Keyboard.isKeyPressed(Keyboard.DOWN_KEY)) yDir += playerVelocity;
    // If moving call move method.
    if (xDir != 0 || yDir != 0) move(xDir, yDir);
    // Else not moving.
    else walking = false;
  }
  
  private void setCurrentSprite() {
    // Sets the currentAction string based on action requirements. Movement, fighting, injured etc.
    if (!walking) {
      currentAction = "STANDING_" + direction;
    }
    else {
      if (anim % framesPerStep == 0) {
        currentAction = "WALKING_" + direction + "_" + anim / framesPerStep;
      }
    }
  }
  
  @Override
  public void update() {
    if (anim > animMax) anim = 0;
    else anim++;
    checkMovementInput();
    setCurrentSprite();
  }
  
  @Override
  public void render(Screen screen) {
    screen.renderSprite(x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, characterActions.get(currentAction), true);
  }
}
