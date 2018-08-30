package com.monkeyStomp.spirelands;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 *
 * @author Aaron Michael McNulty
 */
public class Game extends Canvas implements Runnable {
  
  private JFrame frame;
  private String  title = "Spirelands";
  private int width = 940,
              height = 450;

  public Game() {
    System.out.println("Game is being instatiated...Did I spell that right?");
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    frame = new JFrame();
    // Test to see if we can pull an external image from both development and distribution
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File("./resources/test.png"));
      if (img != null) {
        System.out.println("File Exists");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("Could not find file!!");
    }
    frame.setTitle(title);
  }

  @Override
  public void run() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.frame.setResizable(false);
    game.frame.add(game);
    game.frame.pack();
    game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    game.frame.setLocationRelativeTo(null);
    game.frame.setVisible(true);
    game.requestFocus();
  }
}