package com.monkeyStomp.spirelands;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author Aaron Michael McNulty
 */
public class Game extends Canvas implements Runnable {
  
  private JFrame frame;
  private String  title = "Spirelands";
  private int width = 640,
              height = 450;

  public Game() {
    System.out.println("Game is being instatiated...Did I spell that right?");
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    frame = new JFrame();
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