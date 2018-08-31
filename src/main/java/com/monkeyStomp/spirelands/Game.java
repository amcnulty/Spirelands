package com.monkeyStomp.spirelands;

import com.monkeystomp.spirelands.graphics.Screen;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
/**
 *
 * @author Aaron Michael McNulty
 */
public class Game extends Canvas implements Runnable {
  
  private JFrame frame;
  private boolean running;
  private Thread thread;
  private String  title = "Spirelands";
  private int width = 940,
              height = 450,
              updatesPerSecond = 60,
              framesPerSecond = 90;
  private BufferedImage image;
  private int[] pixels;
  private BufferStrategy bufferStrategy;
  private Graphics graphics;
  private Screen screen;

  private Game() {
    // Create the window.
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    frame = new JFrame();
    // Set the title
    frame.setTitle(title);
    // Create the buffer strategy and pixel array.
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    // Create the screen
    screen = new Screen(width, height);
  }
  
  private synchronized void start() {
    running = true;
    thread = new Thread(this, "Display");
    thread.start();
  }

  private synchronized void stop() {
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    long  lastTime = System.nanoTime(),
          timer = System.currentTimeMillis();
    double  updateNS = 1000000000.0 / updatesPerSecond,
            renderNS = 1000000000.0 / framesPerSecond,
            updateDelta = 0,
            renderDelta = 0;
    int frames = 0,
        updates = 0;
    while (running) {
      long now = System.nanoTime();
      updateDelta += (now - lastTime) / updateNS;
      renderDelta += (now - lastTime) / renderNS;
      lastTime = now;
      while (updateDelta >= 1) {
        update();
        updates++;
        updateDelta--;
      }
      while (renderDelta >= 1) {
        render();
        frames++;
        renderDelta--;
      }

      if (System.currentTimeMillis() - timer >= 1000) {
        timer = System.currentTimeMillis();
        frame.setTitle(title + "  |  " + updates + " ups " + frames + " fps ");
        updates = 0;
        frames = 0;
      }
    }
    stop();
  }
  
  private void update() {
    // Update the view.
  }
  
  private void render() {
    bufferStrategy = getBufferStrategy();
    if (bufferStrategy == null) {
      createBufferStrategy(3);
      return;
    }
    // Clear the screen.
    screen.clear();
    
    // Render the view.
    screen.renderColor(0xFF00FF);
    
    
    // Copy pixels from screen class.
    System.arraycopy(screen.getPixels(), 0, pixels, 0, pixels.length);
    // Display the pixels to the window.
    graphics = bufferStrategy.getDrawGraphics();
    graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    graphics.dispose();
    bufferStrategy.show();
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
    
    game.start();
  }
}