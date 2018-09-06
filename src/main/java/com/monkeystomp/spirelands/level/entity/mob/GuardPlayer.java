package com.monkeystomp.spirelands.level.entity.mob;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.Keyboard;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GuardPlayer extends Player {
  
  private String currentAction = "STANDING_2";
  private short anim = 0;
  private int[] bounds = {0, 10, 15, 10};
  
  private final int PLAYER_WALKING_SPEED = 1,
                    PLAYER_RUNNING_SPEED = 2,
                    SPRITE_SIZE = 32;
    
  private int xDir,
              yDir,
              playerVelocity,
              walkingSteps = 2,
              framesPerStep = 12,
              animMax = walkingSteps * framesPerStep - 2;
  
  public GuardPlayer(int x, int y) {
    super(x, y);
  }
  
  @Override
  protected void loadSpriteSheet() {
    characterSheet = new SpriteSheet("./resources/characters/character_sheet2.png");
  }
  
  @Override
  protected void setupCharacterActions() {
    // Fill the characterActions hashmap with all the sprites associated with various string action keys.
    // Standing sprites.
    characterActions.put("STANDING_0", new Sprite(SPRITE_SIZE, 7, 7, characterSheet));
    characterActions.put("STANDING_1", new Sprite(SPRITE_SIZE, 7, 6, characterSheet));
    characterActions.put("STANDING_2", new Sprite(SPRITE_SIZE, 7, 4, characterSheet));
    characterActions.put("STANDING_3", new Sprite(SPRITE_SIZE, 7, 5, characterSheet));
    // Walking sprites.
    // Up
    characterActions.put("WALKING_0_0", new Sprite(SPRITE_SIZE, 6, 7, characterSheet));
    characterActions.put("WALKING_0_1", new Sprite(SPRITE_SIZE, 8, 7, characterSheet));
    // Right
    characterActions.put("WALKING_1_0", new Sprite(SPRITE_SIZE, 6, 6, characterSheet));
    characterActions.put("WALKING_1_1", new Sprite(SPRITE_SIZE, 8, 6, characterSheet));
    // Down
    characterActions.put("WALKING_2_0", new Sprite(SPRITE_SIZE, 6, 4, characterSheet));
    characterActions.put("WALKING_2_1", new Sprite(SPRITE_SIZE, 8, 4, characterSheet));
    // Left
    characterActions.put("WALKING_3_0", new Sprite(SPRITE_SIZE, 6, 5, characterSheet));
    characterActions.put("WALKING_3_1", new Sprite(SPRITE_SIZE, 7, 5, characterSheet));
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
    if (xDir != 0 || yDir != 0) move(xDir, yDir, bounds);
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

  private void checkForPortals() {
    for (int i = 0; i < level.getPortals().size(); i++) {
      if (level.getPortals().get(i).portalHere(getX(), getY())) level.getPortals().get(i).enterPortal();
    }
  }
  
  @Override
  public void update() {
    if (anim > animMax) anim = 0;
    else anim++;
    checkMovementInput();
    setCurrentSprite();
    checkForPortals();
  }
  
  @Override
  public void render(Screen screen) {
    screen.renderPlayer(x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, characterActions.get(currentAction), true);
  }
}