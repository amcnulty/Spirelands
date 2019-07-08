package com.monkeystomp.spirelands.audio;

import com.monkeystomp.spirelands.gamedata.settings.SettingsManager;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

/**
 * <p>
 * The SoundEffects class is used to create short playable sounds in the game. These sounds can be used for one time quick play when user is interacting with UI or used as sound effects during gameplay.
 * </p>
 * <h3>
 * Example Usage:
 * </h3>
 * <pre>
 * {@code
 * SoundEffects sfx = new SoundEffects();
 * sfx.play(SoundEffects.DOG_BARK);}
 * </pre>
 * @author Aaron Michael McNulty
 */
public class SoundEffects {
  /**
   *          !!######################################!!
   *          !!                                      !!
   *          !!             Misc Sounds              !!
   *          !!                                      !!
   *          !!######################################!!
   */
  /**
   * Monkey stomp sound.
   */
  public static final File MONKEY_STOMP_SOUND = new File("./resources/audio/sfx/monkey_stomp_sound.wav");
  /**
   * Leveling up sound.
   */
  public static final File LEVEL_UP = new File("./resources/audio/sfx/level_up.wav");
  /**
   * The sound of birds chirping.
   */
  public static final File BIRDS_CHIRPING = new File("./resources/audio/sfx/birds_chirping.wav");
  /**
   * A treasure chest opening.
   */
  public static final File CHEST_OPENING = new File("./resources/audio/sfx/chest_opening.wav");
  /**
   * An audience applauding.
   */
  public static final File CLAPPING = new File("./resources/audio/sfx/clapping.wav");
  /**
   * Sound of a creeky door opening;
   */
  public static final File CREEKY_DOOR = new File("./resources/audio/sfx/creeky_door.wav");
  /**
   * A dog barking.
   */
  public static final File DOG_BARK = new File("./resources/audio/sfx/dog_bark.wav");
  /**
   * A far away explosion and destruction.
   */
  public static final File EXPLOSION_FAR_AWAY = new File("./resources/audio/sfx/explosion_far_away.wav");
  /**
   * A quick footstep variation one.
   */
  public static final File FOOTSTEP1 = new File("./resources/audio/sfx/footstep1.wav");
  /**
   * A quick footstep variation two.
   */
  public static final File FOOTSTEP2 = new File("./resources/audio/sfx/footstep2.wav");
  /**
   * Knocking on a door or other hard surface.
   */
  public static final File KNOCK_AT_DOOR = new File("./resources/audio/sfx/knock_at_door.wav");
  /**
   * The sound of a train engine passing by.
   */
  public static final File LOCOMOTIVE = new File("./resources/audio/sfx/locomotive.wav");
  /**
   * Sound effect for items breaking.
   */
  public static final File THINGS_BREAKING = new File("./resources/audio/sfx/things_breaking.wav");
  /**
   * A futuristic transporter sound similar to that of Star Trek
   */
  public static final File TRANSPORTER_SOUND = new File("./resources/audio/sfx/transporter_star_trek.wav");
  /**
   * An automobile engine starting then running for a short amount of time.
   */
  public static final File TRUCK_ENGINE = new File("./resources/audio/sfx/truck_engine.wav");
  /**
   *          !!######################################!!
   *          !!                                      !!
   *          !!            Battle Sounds             !!
   *          !!                                      !!
   *          !!######################################!!
   */
  /**
   * A quick hitting sound.
   */
  public static final File QUICK_HIT = new File("./resources/audio/sfx/quick_hit.wav");
  /**
   * A quick smacking sound.
   */
  public static final File QUICK_SMACK = new File("./resources/audio/sfx/quick_smack.wav");
  /**
   * A mugging sound.
   */
  public static final File MUG_ATTACK = new File("./resources/audio/sfx/mug.wav");
  /**
   * A missed hit.
   */
  public static final File HIT_MISS = new File("./resources/audio/sfx/hit_miss.wav");
  /**
   * A strong attack performed by a male character.
   */
  public static final File STRONG_MALE_ATTACK = new File("./resources/audio/sfx/male_strong_attack.wav");
  /**
   * Magical energy move sound.
   */
  public static final File MAGICAL_ENERGY = new File("./resources/audio/sfx/magic_energy.wav");
  /**
   *          !!######################################!!
   *          !!                                      !!
   *          !!             Item Sounds              !!
   *          !!                                      !!
   *          !!######################################!!
   */
  /**
   * A healing item sound.
   */
  public static final File HEALING_SOUND = new File("./resources/audio/sfx/items/item_healing.wav");
  /**
   * The sound of earning money.
   */
  public static final File COIN = new File("./resources/audio/sfx/items/coin.wav");
  /**
   * Positive stat increasing sound.
   */
  public static final File STAT_UP = new File("./resources/audio/sfx/items/stat_up.wav");
  /**
   * A sound of eating something crunchy.
   */
  public static final File CRUNCH_EAT = new File("./resources/audio/sfx/items/crunch_eat.wav");
  /**
   * A gulping eating sound.
   */
  public static final File GULP_EAT = new File("./resources/audio/sfx/items/gulp_eat.wav");
  /**
   * A quick generic eating sound.
   */
  public static final File QUICK_EAT = new File("./resources/audio/sfx/items/quick_eat.wav");
  /**
   * An armor equip sound.
   */
  public static final File EQUIP_ARMOR = new File("./resources/audio/sfx/items/equip_armor.wav");
  /**
   * An armor unequip sound.
   */
  public static final File UNEQUIP_ARMOR = new File("./resources/audio/sfx/items/unequip_armor.wav");
  /**
   *          !!######################################!!
   *          !!                                      !!
   *          !!              UI Sounds               !!
   *          !!                                      !!
   *          !!######################################!!
   */
  /**
   * UI sound to confirm a selection.
   */
  public static final File CONFIRM = new File("./resources/audio/sfx/confirm.wav");
  /**
   * UI sound to confirm a batch selection or more pronounced action.
   */
  public static final File CONFIRM_CHORD = new File("./resources/audio/sfx/confirm_chord.wav");
  /**
   * UI sound for button clicks.
   */
  public static final File BUTTON_CLICK = new File("./resources/audio/sfx/ui/button_click.wav");
  /**
   * UI sound for buttons being hovered by cursor.
   */
  public static final File BUTTON_HOVER = new File("./resources/audio/sfx/ui/button_hover.wav");
  /**
   * UI sound to play when game is paused.
   */
  public static final File PAUSE = new File("./resources/audio/sfx/ui/pause.wav");
  /**
   * UI sound to play when game is resumed.
   */
  public static final File RESUME = new File("./resources/audio/sfx/ui/resume.wav");
  /**
   * UI sound for confirming selection (vocal variant).
   */
  public static final File VOCAL_CONFIRM = new File("./resources/audio/sfx/ui/vocal_confirm.wav");
  /**
   * UI sound for when the game menu is opened.
   */
  public static final File OPEN_GAME_MENU = new File("./resources/audio/sfx/ui/open_game_menu.wav");
  /**
   * UI sound for when the game menu is closed.
   */
  public static final File CLOSE_GAME_MENU = new File("./resources/audio/sfx/ui/close_game_menu.wav");
  /**
   * Plays a sound effect of the given file.
   * @param file The file for the sound to be played.
   */
  public void playSoundEffect(File file) {
    Clip clip;
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(ais);
      ais.close();
      
      
      // volume settings
      FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float range = control.getMaximum() - control.getMinimum();
      // volume from settings
      float volume = SettingsManager.getSettingsManager().getSfxVolume();
      float gain = (range * volume) + control.getMinimum();
      control.setValue(gain);
      
      clip.start();
      clip.addLineListener((LineEvent e) -> {
        if (e.getType() == LineEvent.Type.STOP){
          e.getLine().close();
        }
      });
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
