package com.monkeyStomp.spirelands.level;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends Level implements Runnable {
  
  private Thread loadingThread = new Thread(this, "Tile Loader");
  private ArrayList<Sprite> sprites = new ArrayList<>();
  
  public SpawnLevel(String path) {
    super(path);
    loadingThread.start();
  }
  
  @Override
  protected void loadLevel(String path) {
    try {
      BufferedImage image = ImageIO.read(new File(path));
      setLevelTileWidth(image.getWidth());
      setLevelTileHeight(image.getHeight());
      tiles = new int[getLevelTileWidth() * getLevelTileHeight()];
      image.getRGB(0, 0, getLevelTileWidth(), getLevelTileHeight(), tiles, 0, getLevelTileWidth());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could not upload file at path: " + path);
    }
  }
  
  @Override
  protected void generateLevel() {
    for (int i = 0; i < tiles.length; i++) {
      if (!uniqueTiles.contains(tiles[i])) {
        uniqueTiles.add(tiles[i]);
      }
    }
  }

  @Override
  public void run() {
    for (int i = 0; i < uniqueTiles.size(); i++) {
      sprites.add(
        new Sprite(
          64,
          (int) Sprite.tileData.get(uniqueTiles.get(i)).get(1),
          (int) Sprite.tileData.get(uniqueTiles.get(i)).get(2),
          (SpriteSheet) Sprite.tileData.get(uniqueTiles.get(i)).get(0)
        )
      );
    }
  }
  
  @Override
  public void update() {
    
  }
  
  @Override
  public void render(Screen screen) {
    for (int i = 0; i < sprites.size(); i++) {
      screen.renderSprite(
        i * sprites.get(i).getWidth(),
        0,
        sprites.get(i)
      );
    }
  }
}