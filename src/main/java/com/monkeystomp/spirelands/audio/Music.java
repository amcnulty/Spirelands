package com.monkeystomp.spirelands.audio;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Music {

  public static String  SAD_PIANO_SONG = "./resources/audio/music/sad_piano_song.wav",
                        TITLE_MUSIC = "./resources/audio/music/title_music.wav",
                        AIRSHIP_SONG = "./resources/audio/music/airship_song_remix.wav";
  
  private Clip clip;
  private long trackTime;

  public void play(String path) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
      clip = AudioSystem.getClip();
      clip.open(ais);
      ais.close();
      clip.start();
      clip.addLineListener((LineEvent e) -> {
        if (e.getType() == LineEvent.Type.STOP){
//          e.getLine().close();
        }
      });
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void stop() {
    if (clip != null) {
      clip.stop();
      clip.flush();
      clip.close();
    }
  }
  
  public void pause() {
    if (clip != null) {
      trackTime = clip.getMicrosecondPosition();
      clip.stop();
      clip.flush();
    }
  }
  
  public void resume() {
    if (clip != null) {
      clip.setMicrosecondPosition(trackTime);
      clip.start();
    }
  }
}