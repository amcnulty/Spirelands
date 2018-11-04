package com.monkeystomp.spirelands.gui.gamemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.GameMenuNavButton;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameMenu {
  
  private final Sprite background = Sprite.GAME_MENU_BACKGROUND;
  private ArrayList<GameMenuNavButton> navButtons = new ArrayList<>();
  
  private final SoundEffects sfx = new SoundEffects();

  private ICallback closeCommand;
  
  public GameMenu() {
    createNavButtons();
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

  public void openMenu() {
    playMenuOpenSound();
  }
  
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
  
  public void update() {
    // Update the nav buttons
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    // Render the background 
    screen.renderSprite(gl, 0, 0, background, false);
    // Render the nav buttons
    for (int i = 0; i < navButtons.size(); i++) {
      navButtons.get(i).render(screen, gl);
    }
  }
}