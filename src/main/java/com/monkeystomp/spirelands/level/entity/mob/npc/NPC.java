package com.monkeyStomp.spirelands.level.entity.mob.npc;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.mob.Mob;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class NPC extends Mob {
  
  protected String[] messages;
  protected String currentAction = "STANDING_2";
  protected int spriteSize;
  protected HashMap<String, Sprite> characterActions = new HashMap<>();
  protected Random random = new Random();
  protected boolean fixedDirection;

  public NPC(NPCConfig config) {
    this.x = config.getX();
    this.y = config.getY();
    this.direction = config.getDirection();
    this.fixedDirection = config.isFixedDirection();
    this.messages = config.getMessages();
  }
  
  public void speak() {
    level.getDialogBox().openDialog(messages);
    level.setDialogOpen(true);
  }
  
  @Override
  public void update() {
  }
  
  @Override
  public void render(Screen screen) {
    screen.renderSprite(x - spriteSize / 2, y - spriteSize / 2, characterActions.get(currentAction), true);
  }
}
