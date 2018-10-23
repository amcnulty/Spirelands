package com.monkeystomp.spirelands.tests.level.entity.particle;

import com.monkeystomp.spirelands.level.entity.particle.Particle;
import com.monkeystomp.spirelands.level.entity.particle.ParticleOverlay;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ParticleOverlayTest {
  @Test
  public void createParticleOverlayTest() {
    ArrayList<Particle> particles = ParticleOverlay.createParticleOverlay(640, 640, 100, Particle.DUST);
    int size = particles.size();
    Assert.assertTrue("Should have returned an array list with size greater than zero.", size > 0);
  }
}