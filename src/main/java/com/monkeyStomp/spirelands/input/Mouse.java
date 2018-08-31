package com.monkeystomp.spirelands.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Mouse implements MouseListener, MouseMotionListener {

  private static int  mouseX,
                      mouseY,
                      mouseB;
	
	public static int getX() {
		return mouseX;
	}
	
	public static int getY() {
		return mouseY;
	}

  public static int getMouseButton() {
    return mouseB;
  }
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
		mouseB = e.getButton();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
    mouseB = -1;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

}
