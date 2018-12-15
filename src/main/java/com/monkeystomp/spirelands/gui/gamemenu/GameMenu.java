package com.monkeystomp.spirelands.gui.gamemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.GameMenuNavButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

/**
 * The Game Menu is the menu screen that opens to display party information, inventory, level information and other game information.
 * @author Aaron Michael McNulty
 */
public class GameMenu {
  
  private final Sprite background = Sprite.GAME_MENU_BACKGROUND;
  private ArrayList<GameMenuNavButton> navButtons = new ArrayList<>();
  private final FontInfo levelName = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 23), Color.WHITE);
  private final FontInfo goldLabel = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 23), Color.GRAY, "Gold:", 225, 199);
  private final FontInfo goldAmount = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 23), Color.WHITE, "190", 248, 199);
  
  private final SoundEffects sfx = new SoundEffects();

  /**
   * Creates a GameMenu object.
   */
  public GameMenu() {
    createNavButtons();
  }
  
  public void setLevelName(String text) {
    this.levelName.setText(text);
    this.levelName.setX(35);
    this.levelName.setY(199);
  }
  
  private void createNavButtons() {
    navButtons.add(new GameMenuNavButton("Weapons", 70, 39, () -> {
      System.out.println("Item Button Clicked");
      handleNavButtonClick();
            }));
    navButtons.add(new GameMenuNavButton("Armor", 70, 59, () -> {
      System.out.println("Armor Button Clicked");
      handleNavButtonClick();
            }));
    navButtons.add(new GameMenuNavButton("Spells", 70, 79, () -> {
      System.out.println("Spells Button Clicked");
      handleNavButtonClick();
            }));
    navButtons.add(new GameMenuNavButton("Items", 70, 99, () -> {
      System.out.println("Items Button Clicked");
      handleNavButtonClick();
            }));
    navButtons.add(new GameMenuNavButton("Collectables", 70, 119, () -> {
      System.out.println("Collectables Button Clicked");
      handleNavButtonClick();
            }));
    navButtons.add(new GameMenuNavButton("Quests", 70, 139, () -> {
      System.out.println("Quests Button Clicked");
      handleNavButtonClick();
            }));
    navButtons.add(new GameMenuNavButton("Crafting", 70, 159, () -> {
      System.out.println("Crafting Button Clicked");
      handleNavButtonClick();
            }));
  }
  /**
   * Opens the game menu.
   */
  public void openMenu() {
    playMenuOpenSound();
  }
  /**
   * Closes the game menu.
   */
  public void closeMenu() {
    playMenuCloseSound();
  }

  private void playMenuOpenSound() {
    sfx.playSoundEffect(SoundEffects.OPEN_GAME_MENU);
  }
  
  private void playMenuCloseSound() {
    sfx.playSoundEffect(SoundEffects.CLOSE_GAME_MENU);
  }
  
  private void handleNavButtonClick() {
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).removeBackground();
    }
  }
  /**
   * Updates the game menu.
   */
  public void update() {
    // Update the nav buttons
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).update();
    }
  }
  /**
   * Renders the game menu to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    // Render the background 
    screen.renderSprite(gl, 0, 0, background, false);
    // Render the nav buttons
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).render(screen, gl);
    }
    screen.renderFonts(levelName);
    screen.renderFonts(goldLabel);
    // Update gold amount
    goldAmount.setText(String.valueOf(InventoryManager.getInventoryManager().getGold()));
    screen.renderFonts(goldAmount);
  }
}
