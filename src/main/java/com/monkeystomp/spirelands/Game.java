package com.monkeystomp.spirelands;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.input.Mouse;
import com.monkeystomp.spirelands.view.ViewManager;
import com.monkeystomp.spirelands.graphics.EventListener;
import com.monkeystomp.spirelands.input.GameController;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.gamedata.settings.SettingsManager;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.util.Helpers;
import com.monkeystomp.spirelands.view.BrandView;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import static java.lang.System.setProperty;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 * The main class for the application. Spirelands is a 2D RPG game for PC produced by Monkey Stomp Games.
 * @author Aaron Michael McNulty
 */
public class Game extends GLCanvas implements Runnable {
  
  private static JFrame frame;
  private boolean running;
  private Thread thread;
  private String  title = "Spirelands";
  private int width = 420,
              height = width * 9 / 16,
              updatesPerSecond = 60,
              framesPerSecond = 90;
  private float scaleX = 3,
                scaleY = 3;
  private Screen screen;
  private Keyboard key = Keyboard.getKeyboard();
  private Mouse mouse = Mouse.getMouse();
  private GameController gameController = GameController.getGameController();
  
  private ViewManager view = ViewManager.getViewManager();
  private static final SettingsManager settingsManager = SettingsManager.getSettingsManager();

  private Game(GLCapabilities caps) {
    super(caps);
    // Create the window.
    frame = new JFrame();
    // Set the window size.
    if (!settingsManager.isFullScreen()) {
      scaleX = settingsManager.getWidth() / (float)width;
      scaleY = settingsManager.getHeight() / (float)height;
    }
    setSize((int)(width * scaleX), (int)(height * scaleY));
    // Set the title
    frame.setTitle(title);
    // Create the screen
    screen = new Screen(width, height, scaleX, scaleY);
    // Add GL event listner
    addGLEventListener(new EventListener(screen, (GL2)getGL()));
    // Start the title screen.
//    (new TitleScreen()).setView();
    // Start the game with the opening cut scene.
    (new BrandView()).setView();

    addKeyListener(key);
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
    
    // Temporarily add items to player inventory for testing.
//    InventoryManager manager = InventoryManager.getInventoryManager();
    // Equipment
//    Helpers.setTimeout(() -> {
//      Item.ITEM_MAP.forEach((id, item) -> manager.setInventoryReference(item, 33));
//    }, 4000);
//    manager.setGold(2019);

    if (SettingsManager.getSettingsManager().isCustomCursor()) setCursor();
    setIconImage();
  }
  
  private static Image loadImage() {
    try {
      return ImageIO.read(new File(SettingsManager.getSettingsManager().getPathToCursor()));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void setCursor() {
    frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(loadImage(), new Point(0, 0), "my custom cursor"));
  }
  
  private void setIconImage() {
    try {
      Image icon = ImageIO.read(Game.class.getResource("/icon/icon_image_clouds.png"));
      frame.setIconImage(icon);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void setDefaultCursor() {
    frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
  /**
   * {@inheritDoc}
   */
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
  // Set property for lib folder with third party libraries.
  static {
    setProperty("java.library.path", Paths.get("lib").toAbsolutePath().toString());
    try {
      Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
      fieldSysPath.setAccessible( true );
      fieldSysPath.set( null, null );
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * The main method of the application.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    GLProfile glp = GLProfile.get("GL2");
    GLCapabilities caps = new GLCapabilities(glp);
    Game game = new Game(caps);
    
    if (settingsManager.isFullScreen()) {
      game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      game.frame.setUndecorated(true);
    }
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
