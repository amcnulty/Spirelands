package com.monkeystomp.spirelands.level.lightmap;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public interface ILightMapRenderer {
  public void render(GL2 gl, Screen screen, float shadowLevel);
}
