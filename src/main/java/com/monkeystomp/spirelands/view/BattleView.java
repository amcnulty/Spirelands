package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.Battle;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleView extends GameView {
  
  private final Battle battle;
  private final Consumer<KeyEvent> IKeyListener = e -> handleKeyPress(e);
  
  public BattleView(Battle battle) {
    this.battle = battle;
    setupKeyListeners();
    this.battle.init();
  }
  
  private void setupKeyListeners() {
    Keyboard.getKeyboard().addKeyListener(IKeyListener);
  }
  
  private void handleKeyPress(KeyEvent e) {
    
  }
  
  @Override
  public void leaveView() {
    Keyboard.getKeyboard().removeKeyListener(IKeyListener);
  }
  
  @Override
  public void update() {
    battle.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    battle.render(screen, gl);
  }
}
