package com.monkeystomp.spirelands.level.entity;

import com.monkeystomp.spirelands.level.entity.mob.Mob;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EntityTest {
  @Test
  public void EntityHereTest() {
    int[] bounds = new int[]{0, 40, 30, 0};
    Entity testEntity = new Mob();
    testEntity.bounds = bounds;
    Assert.assertTrue("Should return true if xp and yp are within the bounds array.", testEntity.entityHere(15, 15));
    Assert.assertFalse("Should return false if yp is less than bounds[0]", testEntity.entityHere(15, -1));
    Assert.assertFalse("Should return false if yp is greater than bounds[2]", testEntity.entityHere(15, 31));
    Assert.assertFalse("Should return false if xp is less than bounds[3]", testEntity.entityHere(-1, 15));
    Assert.assertFalse("Should return false if xp is greater than bounds[1]", testEntity.entityHere(41, 15));
  }
}