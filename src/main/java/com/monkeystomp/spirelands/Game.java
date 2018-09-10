package com.monkeystomp.spirelands;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.input.Mouse;
import com.monkeystomp.spirelands.view.TitleScreen;
import com.monkeystomp.spirelands.view.ViewManager;
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
  private int width = 420,
              height = width * 9 / 16,
              updatesPerSecond = 60,
              framesPerSecond = 90;
  private double  scaleX = 3.0,
                  scaleY = 3.0;
  private BufferedImage image;
  private int[] pixels;
  private BufferStrategy bufferStrategy;
  private Graphics graphics;
  private Screen screen;
  private Keyboard key = Keyboard.getKeyboard();
  private Mouse mouse = new Mouse();
  
  private ViewManager view = ViewManager.getViewManager();

  private Game() {
    // Create the window.
    Dimension size = new Dimension((int)(width * scaleX), (int)(height * scaleY));
    setPreferredSize(size);
    frame = new JFrame();
    // Set the title
    frame.setTitle(title);
    // Create the buffer strategy and pixel array.
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    // Create the screen
    screen = new Screen(width, height, scaleX, scaleY);
    // Start the title screen.
    new TitleScreen();
    // new LevelView();

    addKeyListener(key);
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
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
      // render();
      // frames++;

      if (System.currentTimeMillis() - timer >= 1000) {
        timer = System.currentTimeMillis();
        frame.setTitle(title + "  |  " + updates + " ups " + frames + " fps ");
        updates = 0;
        frames = 0;
      }
    }
    stop();
  }
  /**
   * Updates the game state.
   */
  private void update() {
    // Update the view.
    view.update();
  }
  /**
   * Handles rendering to the window.
   */
  private void render() {
    bufferStrategy = getBufferStrategy();
    if (bufferStrategy == null) {
      createBufferStrategy(3);
      return;
    }
    // Clear the screen.
    screen.clear();
    
    // Render the view.
    view.render(screen);
    
    // Copy pixels from screen class.
    System.arraycopy(Screen.getPixels(), 0, pixels, 0, pixels.length);
    // Display the pixels to the window.
    graphics = bufferStrategy.getDrawGraphics();
    graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    screen.renderFonts(graphics);
    graphics.dispose();
    bufferStrategy.show();
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.frame.setUndecorated(true);
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