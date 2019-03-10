package com.monkeystomp.spirelands.audio;

import com.monkeystomp.spirelands.gamedata.settings.SettingsManager;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * <p>
 * The Music class is used to play background music for the game. There is a set of static songs in the class to be used for the game and game menus. All sound resources are loopable and will loop by default.
 * </p>
 * <h3>
 * Example Usage:
 * </h3>
 * <pre>
 * {@code
 * Music music = new Music();
 * music.play(Music.TOWN_JINGLE);}
 * </pre>
 * @author Aaron Michael McNulty
 */
public class Music {
  /**
   * A slow sad piano song.
   */
  public static String SAD_PIANO_SONG = "./resources/audio/music/sad_piano_song.wav";
  /**
   * Music loop for the title menu.
   */
  public static String TITLE_MUSIC = "./resources/audio/music/title_music.wav";
  /**
   * A song that would be fitting for walking around a castle.
   */
  public static String INSIDE_CASTLE_WALLS = "./resources/audio/music/inside_castle_walls.wav";
  /**
   * A heroic upbeat tune that will be great for introducing a hero or getting excited for some action.
   */
  public static String INTRODUCE_HERO = "./resources/audio/music/introduct_hero.wav";
  /**
   * A calm short music loop that can be played inside of a townspersons' home.
   */
  public static String NEUTRAL_IN_A_HOUSE = "./resources/audio/music/neutral_in_a_house.wav";
  /**
   * Music that can be played while plotting with your allies.
   */
  public static String PLOTTING_WITH_COMRADES = "./resources/audio/music/plotting_with_comrades.wav";
  /**
   * Short music loop that would be fitting to play in a shop.
   */
  public static String SHORT_MERCHANT_LOOP = "./resources/audio/music/short_merchant_loop.wav";
  /**
   * Good background music for a town or village with not much going on.
   */
  public static String SIMPLE_TOWN = "./resources/audio/music/simple_town.wav";
  /**
   * Rain, thunder, and wind loop good for a storm condition.
   */
  public static String STORM = "./resources/audio/music/storm.wav";
  /**
   * Simple tune for playing in a town or village.
   */
  public static String TOWN_JINGLE = "./resources/audio/music/town_jingle.wav";
  /**
   * Music for when action is heating up and your party is under pressure.
   */
  public static String UNDER_PRESSURE = "./resources/audio/music/under_pressure.wav";
  /**
   * Music that sounds like it could be played on an airship traveling the winds.
   */
  public static String AIRSHIP_SONG = "./resources/audio/music/airship_song_remix.wav";
  
  private Clip clip;
  private long trackTime;
  /**
   * Plays a song on loop.
   * @param path Path to the audio file to load and play.
   */
  public void play(String path) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
      clip = AudioSystem.getClip();
      clip.open(ais);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      ais.close();
      setVolume(SettingsManager.getSettingsManager().getMusicVolume());
      clip.start();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * Stops the music that is playing and closes the clip. User cannot resume audio play after calling stop method.
   */
  public void stop() {
    if (clip != null) {
      clip.stop();
      clip.flush();
      clip.close();
    }
  }
  /**
   * Pauses the audio playback. User may resume the playback by calling resume method or stop the playback by calling stop method.
   */
  public void pause() {
    if (clip != null) {
      trackTime = clip.getMicrosecondPosition();
      if (trackTime > clip.getMicrosecondLength()) trackTime -= clip.getMicrosecondLength();
      clip.stop();
      clip.flush();
    }
  }
  /**
   * Resumes audio playback after the pause method has been called.
   */
  public void resume() {
    if (clip != null) {
      clip.setMicrosecondPosition(trackTime);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
  }
  
  public void setVolume(float volume) {
    FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    float range = control.getMaximum() - control.getMinimum();
    float gain = (range * volume) + control.getMinimum();
    control.setValue(gain);
  }
  
  public boolean isPlaying() {
    if (clip != null) return clip.isRunning();
    else return false;
  }
}
