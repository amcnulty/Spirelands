package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleTargetButton extends Button {
  
  private final BattleEntity entity;
  private final Sprite invisibleSprite = new Sprite(5, 5, GameColors.TRANSPARENT);
  private final AnimatedSprite selectorIconAnim = new AnimatedSprite(48, 16, new SpriteSheet("./resources/gui/battle_arrow_sheet.png"), AnimatedSprite.VERY_SLOW, 3);
  
  public BattleTargetButton(BattleEntity entity, ICallback callback) {
    super(
      "",
      entity.getX(),
      entity.getY(),
      entity.getCurrentAction().getWidth(),
      entity.getCurrentAction().getHeight(),
      callback
    );
    this.entity = entity;
    createButtonSprites();
  }
  
  private void createButtonSprites() {
    button = invisibleSprite;
    buttonHover = invisibleSprite;
    buttonDown = invisibleSprite;
    currentButton = button;
  }

  public BattleEntity getEntity() {
    return entity;
  }
  
  @Override
  public void update() {
    if (!entity.isDead()) {
      super.update();
      selectorIconAnim.update();
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (!entity.isDead()) {
      if (isDown()) screen.renderSprite(gl, x + selectorIconAnim.getSprite().getWidth() / 2, y - 15, selectorIconAnim.getSprite(), false);
      else if (isHovering()) screen.renderSprite(gl, x + selectorIconAnim.getSprite().getWidth() / 2, y - 15, selectorIconAnim.getSprite(), .5f, false);
    }
  }

}
