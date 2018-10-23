package com.monkeystomp.spirelands.level.entity.particle;

import com.monkeystomp.spirelands.level.tile.Tile;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ParticleOverlay {
  
  public static ArrayList<Particle> createParticleOverlay(int width, int height, int density, int type) {
    ArrayList<Particle> particleOverlay = new ArrayList<>();
    Random random = new Random();
    int nextRand = random.nextInt(10);
    for (int y = 0; y < height / Tile.TILE_SIZE * 1.2; y++) {
      for (int x = 0; x < width / Tile.TILE_SIZE * 1.2; x++) {
        if ((int)(x + y * (width / Tile.TILE_SIZE * 1.2)) % (nextRand + density) == 0) {
          particleOverlay.add(new Particle(x * Tile.TILE_SIZE + (nextRand - 5), y * Tile.TILE_SIZE + (nextRand - 5), type));
          nextRand = random.nextInt(10);
        }
      }
    }
     System.out.println("Size of particles array: " + particleOverlay.size());
    return particleOverlay;
  }
}