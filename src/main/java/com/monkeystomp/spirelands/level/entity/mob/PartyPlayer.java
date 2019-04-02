package com.monkeystomp.spirelands.level.entity.mob;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class PartyPlayer extends Mob {
  
  private String currentAction = "STANDING_2";
  private short anim = 0;
  
  private final int playerWalkingSpeed = 1,
                    playerRunningSpeed = 2,
                    spriteSize = 48,
                    renderSize = 32;
    
  private int xDir,
              yDir,
              playerVelocity,
              walkingSteps = 2,
              framesPerStep = 12,
              animMax = walkingSteps * framesPerStep - 2;
  private final Sprite shadow = new Sprite("./resources/characters/character_shadow.png");
  private final Consumer<KeyEvent> keyListener = e -> handleSpaceKeyPress(e);
  private Bounds quad = new Bounds();
  
  protected SpriteSheet characterSheet;
  protected HashMap<String, Sprite> characterActions = new HashMap<>();
  private final com.monkeystomp.spirelands.character.Character playerCharacter = CharacterManager.getCharacterManager().getPartyLeader();
  
  public PartyPlayer(int x, int y) {
    this.x = x;
    this.y = y;
    loadSpriteSheet();
    setupCharacterActions();
    setBounds();
    Keyboard.getKeyboard().addKeyListener(keyListener);
  }
  
  private void setBounds() {
    quad.setQuadBounds(
      y - 2,
      x + 10,
      y + 15,
      x - 10
    );
    bounds.add(quad);
    moveBounds[0] = 1;
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
  
  public void setDirection(int direction) {
    this.direction = direction;
    setCurrentAction();
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
  
  private void loadSpriteSheet() {
    characterSheet = playerCharacter.getOverworldSheet();
  }
  
  private void setupCharacterActions() {
    // Fill the characterActions hashmap with all the sprites associated with various string action keys.
    // Standing sprites.
    characterActions.put("STANDING_0", new Sprite(spriteSize, renderSize, 1, 4, characterSheet));
    characterActions.put("STANDING_1", new Sprite(spriteSize, renderSize, 1, 3, characterSheet));
    characterActions.put("STANDING_2", new Sprite(spriteSize, renderSize, 1, 1, characterSheet));
    characterActions.put("STANDING_3", new Sprite(spriteSize, renderSize, 1, 2, characterSheet));
    // Walking sprites.
    // Up
    characterActions.put("WALKING_0_0", new Sprite(spriteSize, renderSize, 0, 4, characterSheet));
    characterActions.put("WALKING_0_1", new Sprite(spriteSize, renderSize, 2, 4, characterSheet));
    // Right
    characterActions.put("WALKING_1_0", new Sprite(spriteSize, renderSize, 0, 3, characterSheet));
    characterActions.put("WALKING_1_1", new Sprite(spriteSize, renderSize, 2, 3, characterSheet));
    // Down
    characterActions.put("WALKING_2_0", new Sprite(spriteSize, renderSize, 0, 1, characterSheet));
    characterActions.put("WALKING_2_1", new Sprite(spriteSize, renderSize, 2, 1, characterSheet));
    // Left
    characterActions.put("WALKING_3_0", new Sprite(spriteSize, renderSize, 0, 2, characterSheet));
    characterActions.put("WALKING_3_1", new Sprite(spriteSize, renderSize, 2, 2, characterSheet));
  }
  
  private void handleSpaceKeyPress(KeyEvent e) {
    if (e.getKeyCode() == Keyboard.SPACE_KEY) fireActionButton();
  }
  
  private void fireActionButton() {
      if (level.getDialogOpen()) level.getDialogBox().handleSpaceKey();
      else checkForInteractableEntities();
  }
  
  private void checkForInteractableEntities() {
    for (int i = 0; i < level.getSolidEntities().size(); i++) {
      for (int j = 0; j < 10; j++) {
        switch (direction) {
          case 0:
            if ((level.getSolidEntities().get(i).entityHere(x - 10, y - 2 * j)
                    || level.getSolidEntities().get(i).entityHere(x, y - 2 * j)
                    || level.getSolidEntities().get(i).entityHere(x + 10, y - 2 * j))
                    && !level.getSolidEntities().get(i).equals(this)) {
              level.getSolidEntities().get(i).interact();
              return;
            } break;
          case 1:
            if ((level.getSolidEntities().get(i).entityHere(x + 8 + 2 * j, y - 1)
                    || level.getSolidEntities().get(i).entityHere(x + 8 + 2 * j, y + 7)
                    || level.getSolidEntities().get(i).entityHere(x + 8 + 2 * j, y + 15))
                    && !level.getSolidEntities().get(i).equals(this)) {
              level.getSolidEntities().get(i).interact();
              return;
            } break;
          case 2:
            if ((level.getSolidEntities().get(i).entityHere(x - 10, y + 8 + 2 * j)
                    || level.getSolidEntities().get(i).entityHere(x, y + 8 + 2 * j)
                    || level.getSolidEntities().get(i).entityHere(x + 10, y + 8 + 2 * j))
                    && !level.getSolidEntities().get(i).equals(this)) {
              level.getSolidEntities().get(i).interact();
              return;
            } break;
          case 3:
            if ((level.getSolidEntities().get(i).entityHere(x - 8 - 2 * j, y - 1)
                    || level.getSolidEntities().get(i).entityHere(x - 8 - 2 * j, y + 7)
                    || level.getSolidEntities().get(i).entityHere(x - 8 - 2 * j, y + 15))
                    && !level.getSolidEntities().get(i).equals(this)) {
              level.getSolidEntities().get(i).interact();
              return;
            } break;
          default:
            break;
        }
      }
    }
  }
  
  private void checkMovementInput() {
    xDir = 0;
    yDir = 0;
    playerVelocity = Keyboard.isKeyPressed(Keyboard.LEFT_SHIFT_KEY) ? playerRunningSpeed : playerWalkingSpeed;
    if (Keyboard.isKeyPressed(Keyboard.A_KEY) || Keyboard.isKeyPressed(Keyboard.LEFT_KEY)) xDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.D_KEY) || Keyboard.isKeyPressed(Keyboard.RIGHT_KEY)) xDir += playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.W_KEY) || Keyboard.isKeyPressed(Keyboard.UP_KEY)) yDir -= playerVelocity;
    if (Keyboard.isKeyPressed(Keyboard.S_KEY) || Keyboard.isKeyPressed(Keyboard.DOWN_KEY)) yDir += playerVelocity;
    // If moving call move method.
    if (xDir != 0 || yDir != 0) move(xDir, yDir, moveBounds);
    // Else not moving.
    else walking = false;
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
    level.transitionOutOfLevel();
  }
  
  private void destroyPlayer() {
    // Remove notifiers
    Keyboard.getKeyboard().removeKeyListener(keyListener);
  }
  
  @Override
  public void update() {
    System.out.println("X: " + getX() + " Y: " + getY());
    if (anim > animMax) anim = 0;
    else anim++;
    checkMovementInput();
    setCurrentAction();
    checkForPortals();
    updateBounds();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - shadow.getWidth() / 2, y + renderSize / 2 - shadow.getHeight() / 2, shadow, true);
    screen.renderSprite(gl, x - renderSize / 2, y - renderSize / 2, characterActions.get(currentAction), true);
  }

}
