package com.monkeystomp.spirelands.util;

import com.monkeystomp.spirelands.battle.Battle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper methods for the application.
 * @author Aaron Michael McNulty
 */
public class Helpers {

  public static void setTimeout(Runnable runnable, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      } catch (InterruptedException ex) {
        Logger.getLogger(Battle.class.getName()).log(Level.SEVERE, null, ex);
      }
    }).start();
    
  }
}
