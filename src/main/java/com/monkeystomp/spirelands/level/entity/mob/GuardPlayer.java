package com.monkeystomp.spirelands.level.entity.mob;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.INotify;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GuardPlayer extends Player {
  
  private String currentAction = "STANDING_2";
  private short anim = 0;
  
  private final int PLAYER_WALKING_SPEED = 1,
                    PLAYER_RUNNING_SPEED = 2,
                    SPRITE_SIZE = 32;
    
  private int xDir,
              yDir,
              playerVelocity,
              walkingSteps = 2,
              framesPerStep = 12,
              animMax = walkingSteps * framesPerStep - 2;
  private Sprite shadow = new Sprite("./resources/characters/character_shadow.png");
  private INotify notifier = (KeyEvent e) -> handleSpaceKeyPress(e);
  private Bounds quad = new Bounds();
  
  public GuardPlayer(int x, int y) {
    super(x, y);
    setBounds();
    Keyboard.getKeyboard().addKeyPressNotifier(notifier);
  }
  
  private void setBounds() {
    quad.setQuadBounds(
      y - 2,
      x + 10,
      y + 15,
      x - 10
    );
    bounds.add(quad);
    moveBounds[0] = 0;
    moveBounds[1] = 10;
    moveBounds[2] = 15;
    moveBounds[3] = 10;
  }
  
  private void updateBounds() {
    quad.setQuadBounds(
      y - 2,
      x + 10,
      y + 15,
      x - 10
    );
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
    characterActions.put("WALKING_3_1", new Sprite(SPRITE_SIZE, 8, 5, characterSheet));
  }
  
  private void handleSpaceKeyPress(KeyEvent e) {
    if (e.getKeyCode() == Keyboard.SPACE_KEY && !level.getDialogOpen()) {
      checkForInteractableEntities();
    }
    else if (level.getDialogOpen()) level.getDialogBox().handleSpaceKey();
  }
  
  private void checkForInteractableEntities() {
    int dy = ((direction + 3) % 2) * 2 - (4 * (((direction + 3) % 4) / 3)),
        dx = (direction % 2) * 2 - (4 * (direction / 3));
    for (int i = 0; i < level.getSolidEntities().size(); i++) {
      for (int j = 0; j < 10; j++) {
        if (level.getSolidEntities().get(i).entityHere(x + dx * j, y + dy * j) && !level.getSolidEntities().get(i).equals(this)) {
          level.getSolidEntities().get(i).interact();
          return;
        }
      }
    }
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
    if (xDir != 0 || yDir != 0) move(xDir, yDir, moveBounds);
    // Else not moving.
    else walking = false;
  }
  /**
   * Sets the currentAction string based on action requirements. Movement, fighting, injured etc.
   */
  private void setCurrentAction() { 
    if (!walking) {
      currentAction = "STANDING_" + direction;
    }
    else {
      currentAction = "WALKING_" + direction + "_" + stepIndex;
      if (anim % framesPerStep == 0) {
        stepIndex = anim / framesPerStep;
      }
    }
  }

  private void checkForPortals() {
    for (int i = 0; i < level.getPortals().size(); i++) {
      if (level.getPortals().get(i).entityHere(getX(), getY())) {
        enterPortal(level.getPortals().get(i));
      }
    }
  }
  
  private void enterPortal(Portal exitPortal) {
    level.setExitPortal(exitPortal);
    destroyPlayer();
    // Show some cool animation that you are leaving the map
    level.transitionOutOfLevel();
  }
  
  private void destroyPlayer() {
    // Remove notifiers
    Keyboard.getKeyboard().removeKeyPressNotifier(notifier);
  }
  
  @Override
  public void update() {
    if (anim > animMax) anim = 0;
    else anim++;
    checkMovementInput();
    setCurrentAction();
    checkForPortals();
    updateBounds();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - shadow.getWidth() / 2, y + SPRITE_SIZE / 2 - shadow.getHeight() / 2, shadow, true);
    screen.renderSprite(gl, x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, characterActions.get(currentAction), true);
  }
}
