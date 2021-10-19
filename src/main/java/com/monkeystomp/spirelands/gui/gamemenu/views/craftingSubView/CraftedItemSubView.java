package com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.Item;

/**
 * Screen to show the item that was just successfully crafted.
 * @author Aaron Michael McNulty
 */
public class CraftedItemSubView {
  
  private final FontInfo  pageTitle = GameFonts.getGAME_MENU_HEADLINE(),
                          itemName = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final float thumbnailScale = 1.5f;
  private final int centerHoriz = 259,
                    top = 23,
                    pageTitleY = top + 20,
                    continueButtonY = top + 120,
                    continueButtonWidth = 35,
                    thumbnailX = centerHoriz - 8,
                    thumbnailY = top + 50,
                    itemNameY = top + 90,
                    sparkle1X = thumbnailX - 7,
                    sparkle1Y = thumbnailY - 7,
                    sparkle2X = thumbnailX + 15,
                    sparkle2Y = thumbnailY - 6,
                    sparkle3X = thumbnailX - 9,
                    sparkle3Y = thumbnailY + 10,
                    sparkle4X = thumbnailX + 1,
                    sparkle4Y = thumbnailY + 18,
                    sparkle5X = thumbnailX + 9,
                    sparkle5Y = thumbnailY + 12;
  private int timer = 0;
  private Sprite craftedItem;
  private final ICallback onExit;
  private final GameMenuSecondaryButton continueButton = new GameMenuSecondaryButton("Continue", GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(), centerHoriz, continueButtonY, continueButtonWidth, 11, () -> cleanupAndExit());
  private final AnimatedSprite sparkle1 = new AnimatedSprite(48, 10, new SpriteSheet("./resources/animations/sparkle/sparkle.png"), AnimatedSprite.MEDIUM, 12);
  private final AnimatedSprite sparkle2 = new AnimatedSprite(48, 8, new SpriteSheet("./resources/animations/sparkle/sparkle.png"), AnimatedSprite.SLOW, 12);
  private final AnimatedSprite sparkle3 = new AnimatedSprite(48, 12, new SpriteSheet("./resources/animations/sparkle/sparkle.png"), AnimatedSprite.VERY_SLOW, 12);
  private final AnimatedSprite sparkle4 = new AnimatedSprite(48, 8, new SpriteSheet("./resources/animations/sparkle/sparkle.png"), AnimatedSprite.MEDIUM, 12);
  private final AnimatedSprite sparkle5 = new AnimatedSprite(48, 16, new SpriteSheet("./resources/animations/sparkle/sparkle.png"), 12, 12);
  
  public CraftedItemSubView(ICallback onExit) {
    this.onExit = onExit;
    pageTitle.setText("You Have Crafted...");
    pageTitle.setX(centerHoriz);
    pageTitle.setY(pageTitleY);
    pageTitle.centerText();
    itemName.setX(centerHoriz);
    itemName.setY(itemNameY);
  }
  
  public void setItem(Item item) {
    itemName.setText(item.getTitle());
    itemName.setX(centerHoriz);
    itemName.setY(itemNameY);
    itemName.centerText();
    craftedItem = item.getThumbnail();
  }
  
  private void cleanupAndExit() {
    exitingView();
    onExit.execute();
  }
  
  public void exitingView() {
    timer = 0;
  }
  
  public void update() {
    if (++timer == 300) cleanupAndExit();
    continueButton.update();
    sparkle1.update();
    sparkle2.update();
    sparkle3.update();
    sparkle4.update();
    sparkle5.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(pageTitle);
    screen.renderFonts(itemName);
    screen.renderSprite(gl, thumbnailX, thumbnailY, craftedItem, 1f, false, thumbnailScale);
    screen.renderSprite(gl, sparkle1X, sparkle1Y, sparkle1.getSprite(), false);
    screen.renderSprite(gl, sparkle2X, sparkle2Y, sparkle2.getSprite(), false);
    screen.renderSprite(gl, sparkle3X, sparkle3Y, sparkle3.getSprite(), false);
    screen.renderSprite(gl, sparkle4X, sparkle4Y, sparkle4.getSprite(), false);
    screen.renderSprite(gl, sparkle5X, sparkle5Y, sparkle5.getSprite(), false);
    continueButton.render(screen, gl);
  }

}
