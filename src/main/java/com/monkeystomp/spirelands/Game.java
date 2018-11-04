package com.monkeystomp.spirelands;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.input.Mouse;
import com.monkeystomp.spirelands.view.TitleScreen;
import com.monkeystomp.spirelands.view.ViewManager;
import com.monkeystomp.spirelands.graphics.EventListener;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
/**
 *
 * @author Aaron Michael McNulty
 */
public class Game extends GLCanvas implements Runnable {
  
  private JFrame frame;
  private boolean running;
  private Thread thread;
  private String  title = "Spirelands";
  private int width = 420,
              height = width * 9 / 16,
              updatesPerSecond = 60,
              framesPerSecond = 90;
  private int scaleX = 3,
              scaleY = 3;
  private BufferedImage image;
  private int[] pixels;
  private BufferStrategy bufferStrategy;
  private Graphics graphics;
  private Screen screen;
  private Keyboard key = Keyboard.getKeyboard();
  private Mouse mouse = new Mouse();
  
  private ViewManager view = ViewManager.getViewManager();

  private Game(GLCapabilities caps) {
    super(caps);
    // Create the window.
    frame = new JFrame();
    // Set the window size.
    setSize(width * scaleX, height * scaleY);
    // Set the title
    frame.setTitle(title);
    // Create the screen
    screen = new Screen(width, height, scaleX, scaleY);
    // Add GL event listner
    addGLEventListener(new EventListener(screen, (GL2)getGL()));
    // Start the title screen.
    (new TitleScreen()).setView();

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
//      while (renderDelta >= 1) {
//        render();
//        frames++;
//        renderDelta--;
//      }
       render();
       frames++;

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
    // Display the GLCanvas
    display();
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    GLProfile glp = GLProfile.get("GL2");
    GLCapabilities caps = new GLCapabilities(glp);
    Game game = new Game(caps);
    
//    game.frame.setUndecorated(true);
    game.frame.setResizable(true);
    game.frame.add(game);
    game.frame.pack();
    game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    game.frame.setLocationRelativeTo(null);
    game.frame.setVisible(true);
    game.requestFocus();
    
    game.start();
  }
}