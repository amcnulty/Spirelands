package com.monkeystomp.spirelands.level.transitions;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.lightmap.LightMap;
import com.monkeystomp.spirelands.level.lightmap.LightMapType;
import com.monkeystomp.spirelands.util.Helpers;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleTransition {
  
  private final Sprite battleBanner = new Sprite("./resources/banners/battle_intro_banner.png");
  private final int animationMillTime = 4000;
  private int anim = 0,
              thumbnailY = Screen.getHeight() / 2,
              endThumbnailIndex = 0;
  private float shadowLevel = 0;
  private boolean animating = false,
                  showingThumbnails = false;
  private final LightMap lightmap = new LightMap();
  private final SoundEffects sfx = new SoundEffects();
  private HashMap<Integer, Character> partyMembers;
  private final ArrayList<Sprite> thumbnails = new ArrayList<>();
  
  public BattleTransition() {
    lightmap.enableLightMap(LightMapType.BLENDED);
  }
  
  public int show() {
    sfx.playSoundEffect(new File("./resources/audio/sfx/battle_start.wav"));
    animating = true;
    partyMembers = CharacterManager.getCharacterManager().getPartyMembers();
    Set<Integer> keys = partyMembers.keySet();
    keys.forEach(key -> {
      thumbnails.add(partyMembers.get(key).getThumbnail());
    });
    Helpers.setTimeout(() -> {
      showingThumbnails = true;
    }, 1700);
    try {
      Thread.sleep(animationMillTime);
      return 1;
    } catch (InterruptedException ex) {
      Logger.getLogger(BattleTransition.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 99;
  }

  public boolean isAnimating() {
    return animating;
  }
  
  public void update() {
    if (animating) {
      if (showingThumbnails) {
        if (anim % 10 == 0) {
          if (endThumbnailIndex < thumbnails.size()) {
            endThumbnailIndex++;
            sfx.playSoundEffect(new File("./resources/audio/sfx/hit_miss.wav"));
          }
        }
      }
      if (anim++ >= 210) {
        shadowLevel += .03333333333333333333333333333333333333333333333333333333333333333333;
      }
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    lightmap.render(gl, screen, shadowLevel);
    screen.renderSprite(gl, Screen.getWidth() / 2 - battleBanner.getWidth() / 2, Screen.getHeight() / 2 - battleBanner.getHeight() / 2, battleBanner, false);
    for (int i = 0; i < endThumbnailIndex; i++) {
      screen.renderSprite(gl, Screen.getWidth() / 2 + thumbnails.get(i).getWidth() * i, thumbnailY, thumbnails.get(i), false);
    }
  }

}
