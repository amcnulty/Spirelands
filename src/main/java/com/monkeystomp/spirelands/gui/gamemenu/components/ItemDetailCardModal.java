package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import java.util.function.Consumer;

/**
 * A centered item detail card with a dark backdrop that covers the main area of the game menu.
 * @author Aaron Michael McNulty
 */
public class ItemDetailCardModal extends ItemDetailCard {
  
  private final Sprite backdrop = new Sprite(271, 155, GameColors.BLACK);
  private Sprite background;
  
  public ItemDetailCardModal(Consumer<ItemDetailCard> ICloseOperation) {
    super(ICloseOperation);
    cardLeft = 215;
    cardCenterHoriz = cardLeft + cardWidth / 2;
    cardRight = cardLeft + cardWidth;
    exitButton.setLeft(cardLeft);
    createBackground();
  }
  
  private void createBackground() {
    background = new Sprite(cardWidth, cardHeight, GameColors.GAME_MENU_BACKGROUND);
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 124, 23, backdrop, .7f, false);
    screen.renderSprite(gl, cardLeft, cardTop, background, false);
    super.render(screen, gl);
  }

}
