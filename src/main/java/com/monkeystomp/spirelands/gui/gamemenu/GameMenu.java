package com.monkeystomp.spirelands.gui.gamemenu;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameMenu {
  
  private final Sprite background = Sprite.GAME_MENU_BACKGROUND;
  
   private final SoundEffects sfx = new SoundEffects();
   
   private ICallback closeCommand;
   
   public void setCloseCommand(ICallback callback) {
    this.closeCommand = callback;
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
  
  private void handleContinueButtonClick() {
    closeCommand.execute();
  }
  
  public void update() {
    
  }
  
  public void render(Screen screen) {
    screen.renderSprite(0, 0, background, false, false);
  }
}