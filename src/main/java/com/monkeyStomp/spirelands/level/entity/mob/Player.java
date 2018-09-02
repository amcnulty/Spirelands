package com.monkeyStomp.spirelands.level.entity.mob;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.Keyboard;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Player extends Mob {
  
  private SpriteSheet characterSheet = new SpriteSheet("./resources/characters/darkSuit.png");
  private Sprite character = new Sprite(128, 1, 2, characterSheet);
  private HashMap<String, Sprite> characterActions = new HashMap<>();
  private String currentAction;
  
  private final int PLAYER_WALKING_SPEED = 3,
                    PLAYER_RUNNING_SPEED = 6;
  
  private int xDir,
              yDir,
              playerVelocity;
  
  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    setupCharacterActions();
  }
  
  private void setupCharacterActions() {
    // Fill the characterActions hashmap with all the sprites associated with various string action keys.
  }
  
  private void checkMovementInput() {
    xDir = 0;
    yDir = 0;
    playerVelocity = Keyboard.isKeyPressed(Keyboard.LEFT_SHIFT_KEY) ? PLAYER_RUNNING_SPEED : PLAYER_WALKING_SPEED;
    if (Keyboard.isKeyPressed(Keyboard.A_KEY) || Keyboard.isKeyPressed(Keyboard.LEFT_KEY)) xDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.D_KEY) || Keyboard.isKeyPressed(Keyboard.RIGHT_KEY)) xDir += playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.W_KEY) || Keyboard.isKeyPressed(Keyboard.UP_KEY)) yDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.S_KEY) || Keyboard.isKeyPressed(Keyboard.DOWN_KEY)) yDir += playerVelocity;
    if (xDir != 0 || yDir != 0) move(xDir, yDir);
  }
  
  private void setCurrentSprite() {
    // Sets the currentAction string based on action requirements. Movement, fighting, injured etc.
  }
  
  public void update() {
    checkMovementInput();
    setCurrentSprite();
  }
  
  public void render(Screen screen) {
//    screen.renderSprite(x - character.getWidth() / 2, y - character.getHeight() / 2, characterActions.get(currentAction), true);
    screen.renderSprite(x - character.getWidth() / 2, y - character.getHeight() / 2, character, true);
  }
}