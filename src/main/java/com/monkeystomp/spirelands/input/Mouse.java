package com.monkeystomp.spirelands.input;

import com.monkeystomp.spirelands.graphics.Screen;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The Mouse class responds to mouse events that happen on the game window.
 * @author Aaron Michael McNulty
 */
public class Mouse implements MouseListener, MouseMotionListener {

  private static int  mouseX,
                      mouseY,
                      mouseB;
	/**
   * Gets the x pixel location of the mouse adjusted for scaling.
   * @return The x pixel location of the mouse.
   */
  public static int getX() {
    return mouseX;
  }
  /**
   * Gets the y pixel location of the mouse adjusted for scaling.
   * @return The y pixel location of the mouse.
   */
  public static int getY() {
    return mouseY;
  }
  /**
   * Gets the mouse button this is being pressed.
   * @return The mouse button that is being pressed.
   */
  public static int getMouseButton() {
    return mouseB;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseClicked(MouseEvent e) {
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mousePressed(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
		mouseB = e.getButton();
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
    mouseB = -1;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseEntered(MouseEvent e) {
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseExited(MouseEvent e) {
  }

}
