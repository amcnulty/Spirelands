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
public class SoundEffects {
  
  public static final File  BIRDS_CHIRPING = new File("./resources/audio/sfx/birds_chirping.wav"),
                            CHEST_OPENING = new File("./resources/audio/sfx/chest_opening.wav"),
                            CLAPPING = new File("./resources/audio/sfx/clapping.wav"),
                            CONFIRM = new File("./resources/audio/sfx/confirm.wav"),
                            CONFIRM_CHORD = new File("./resources/audio/sfx/confirm_chord.wav"),
                            CREEKY_DOOR = new File("./resources/audio/sfx/creeky_door.wav"),
                            DOG_BARK = new File("./resources/audio/sfx/dog_bark.wav"),
                            EXPLOSION_FAR_AWAY = new File("./resources/audio/sfx/explosion_far_away.wav"),
                            FOOTSTEP1 = new File("./resources/audio/sfx/footstep1.wav"),
                            FOOTSTEP2 = new File("./resources/audio/sfx/footstep2.wav"),
                            KNOCK_AT_DOOR = new File("./resources/audio/sfx/knock_at_door.wav"),
                            LOCOMOTIVE = new File("./resources/audio/sfx/locomotive.wav"),
                            THINGS_BREAKING = new File("./resources/audio/sfx/things_breaking.wav"),
                            TRANSPORTER_SOUND = new File("./resources/audio/sfx/transporter_star_trek.wav"),
                            TRUCK_ENGINE = new File("./resources/audio/sfx/truck_engine.wav"),
                            // UI SOUNDS
                            BUTTON_CLICK = new File("./resources/audio/sfx/ui/button_click.wav"),
                            BUTTON_HOVER = new File("./resources/audio/sfx/ui/button_hover.wav"),
                            PAUSE = new File("./resources/audio/sfx/ui/pause.wav"),
                            RESUME = new File("./resources/audio/sfx/ui/resume.wav"),
                            VOCAL_CONFIRM = new File("./resources/audio/sfx/ui/vocal_confirm.wav");
  
  public void playSoundEffect(File file) {
    Clip clip;
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(ais);
      ais.close();
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