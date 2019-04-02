package com.monkeystomp.spirelands.level.entity.mob;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Player extends Mob {
  
  protected SpriteSheet characterSheet;
  protected HashMap<String, Sprite> characterActions = new HashMap<>();
  private final Character playerCharacter = CharacterManager.getCharacterManager().getPartyLeader();
  
  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    loadSpriteSheet();
    setupCharacterActions();
  }
  
  public void setDirection(int direction) {
    this.direction = direction;
  }
  
  protected void loadSpriteSheet() {}
  
  protected void setupCharacterActions() {}
  
  @Override
  public void update() {}
  
  public void render(Screen screen, GL2 gl) {}
}