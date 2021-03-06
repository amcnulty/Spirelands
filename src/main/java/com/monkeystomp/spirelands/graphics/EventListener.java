package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.monkeystomp.spirelands.view.ViewManager;

/**
 * The EventListener class is used to create the instance of GLEventListener for handling OpenGL drawing events.
 * @author Aaron Michael McNulty
 */
public class EventListener implements GLEventListener {
  
  private Screen screen;
  private GL2 gl;
  private ViewManager view = ViewManager.getViewManager();
  /**
   * Creates an EventListener object with the associated Screen and GL2 instances.
   * @param screen Instance of the Screen class for passing down to child render methods.
   * @param gl Instance of the GL2 class for passing down to child render methods.
   */
  public EventListener(Screen screen, GL2 gl) {
    this.screen = screen;
    this.gl = gl;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void init(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();

    gl.glClearColor(0, 0, 0, 1);

    gl.glEnable(GL2.GL_TEXTURE_2D);
    gl.glDisable(GL2.GL_DEPTH_TEST);
    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
    gl.glEnable(GL2.GL_BLEND);
    
    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose(GLAutoDrawable drawable) {
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void display(GLAutoDrawable drawable) {
    GL2 gl = drawable.getGL().getGL2();
    
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    view.render(screen, gl);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL2 gl = drawable.getGL().getGL2();
    Screen.setScaleX(drawable.getSurfaceWidth() / (float)Screen.getWidth());
    Screen.setScaleY(drawable.getSurfaceHeight() / (float)Screen.getHeight());
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrthof(0, 420, 420*9/16, 0, -1, 1);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  }

}
