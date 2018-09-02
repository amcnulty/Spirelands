package com.monkeyStomp.spirelands.level.entity.mob;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.Keyboard;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Player extends Mob {
  
  private SpriteSheet characterSheet = new SpriteSheet("./resources/characters/darkSuit.png");
  private Sprite character = new Sprite(128, 1, 2, characterSheet);
  
  private int xDir,
              yDir,
              playerVelocity,
              playerWalkingSpeed = 3,
              playerRunningSpeed = 6;
  
  public Player(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  private void checkMovementInput() {
    xDir = 0;
    yDir = 0;
    playerVelocity = Keyboard.isKeyPressed(Keyboard.LEFT_SHIFT_KEY) ? playerRunningSpeed : playerWalkingSpeed;
    if (Keyboard.isKeyPressed(Keyboard.A_KEY) || Keyboard.isKeyPressed(Keyboard.LEFT_KEY)) xDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.D_KEY) || Keyboard.isKeyPressed(Keyboard.RIGHT_KEY)) xDir += playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.W_KEY) || Keyboard.isKeyPressed(Keyboard.UP_KEY)) yDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.S_KEY) || Keyboard.isKeyPressed(Keyboard.DOWN_KEY)) yDir += playerVelocity;
  }
  
  public void update() {
    checkMovementInput();
    if (xDir != 0 || yDir != 0) move(xDir, yDir);
  }
  
  public void render(Screen screen) {
    screen.renderSprite(x - character.getWidth() / 2, y - character.getHeight() / 2, character, true);
  }
}