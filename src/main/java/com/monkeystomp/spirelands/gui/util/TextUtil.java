package com.monkeystomp.spirelands.gui.util;

import com.monkeystomp.spirelands.graphics.Screen;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TextUtil {
  
  private static ArrayList<String> lines = new ArrayList<>();
  private static String[] words;
  private static Font font;
  private static double lineWidth;
  private static FontRenderContext frc = new FontRenderContext(null, true, true);
  
  public static ArrayList<String> createWrappedText(String message, Font font, double lineWidth) {
    TextUtil.font = font;
    TextUtil.words = message.split(" ");
    TextUtil.lineWidth = lineWidth;
    lines.clear();
    createLines(0, 0);
    return lines;
  }
  
  private static void createLines(int lineIndex, int wordIndex) {
    String testString;
    for (int i = wordIndex; i < TextUtil.words.length; i++) {
      if (i == wordIndex) {
        lines.add("");
        testString = words[i];
      }
      else testString = lines.get(lineIndex) + " " + words[i];
      if (font.getStringBounds(testString, frc).getWidth() / Screen.getScaleX() < TextUtil.lineWidth) lines.set(lineIndex, testString);
      else {
        if (i < words.length) createLines(++lineIndex, i);
        break;
      }
    }
  }

}
