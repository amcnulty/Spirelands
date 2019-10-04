package com.monkeystomp.spirelands.battle.controls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.BattleTargetButton;
import com.monkeystomp.spirelands.input.Keyboard;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TargetSelector {
  
  private int x, y;
  private final AnimatedSprite selectorIconAnim = new AnimatedSprite(48, 16, new SpriteSheet("./resources/gui/battle_arrow_sheet.png"), AnimatedSprite.VERY_SLOW, 3);
  private ArrayList<CharacterBattleEntity>  party = new ArrayList<>();
  private ArrayList<EnemyBattleEntity> enemies = new ArrayList<>();
  private List<BattleEntity> entities;
  private final ArrayList<BattleTargetButton> mouseTargetButtons = new ArrayList<>();
  private BattleEntity currentTarget;
  private final Consumer<BattleEntity> IBattleEntitySelector;
  private final Supplier<BattleMove> ICurrentBattleMoveSupplier;
  private final Consumer<KeyEvent> keyListener = event -> handleKeyEvent(event);
  private boolean targeting = false,
                  singleTargetOnly = false;
  
  public TargetSelector(Consumer<BattleEntity> IBattleEntitySelector, Supplier<BattleMove> ICurrentBattleMoveSupplier) {
    this.IBattleEntitySelector = IBattleEntitySelector;
    this.ICurrentBattleMoveSupplier = ICurrentBattleMoveSupplier;
  }
  
  public void init(ArrayList<CharacterBattleEntity> party, ArrayList<EnemyBattleEntity> enemies) {
    this.party = party;
    this.enemies = enemies;
    setMouseTargetButtons();
    Keyboard.getKeyboard().addKeyListener(keyListener);
  }
  
  private void setMouseTargetButtons() {
    entities = Stream.concat(party.stream(), enemies.stream()).collect(Collectors.toList());
    for (BattleEntity entity: entities) {
      mouseTargetButtons.add(new BattleTargetButton(
        entity,
        ICurrentBattleMoveSupplier,
        () -> {
          currentTarget = entity;
          IBattleEntitySelector.accept(entity);
        }
      ));
    }
  }
  
  private void handleKeyEvent(KeyEvent event) {
    if (targeting) {
      if (event.getKeyCode() == Keyboard.SPACE_KEY || event.getKeyCode() == Keyboard.ENTER_KEY) {
        IBattleEntitySelector.accept(currentTarget);
      }
      if (!singleTargetOnly) {
        if (event.getKeyCode() == Keyboard.W_KEY || event.getKeyCode() == Keyboard.UP_KEY) {
          BattleEntity nextTarget = getClosestEntityAbove();
          if (nextTarget != null) currentTarget = nextTarget;
        }
        else if (event.getKeyCode() == Keyboard.A_KEY || event.getKeyCode() == Keyboard.LEFT_KEY) {
          BattleEntity nextTarget = getClosestEntityLeft();
          if (nextTarget != null) currentTarget = nextTarget;
        }
        else if (event.getKeyCode() == Keyboard.S_KEY || event.getKeyCode() == Keyboard.DOWN_KEY) {
          BattleEntity nextTarget = getClosestEntityBelow();
          if (nextTarget != null) currentTarget = nextTarget;
        }
        else if (event.getKeyCode() == Keyboard.D_KEY || event.getKeyCode() == Keyboard.RIGHT_KEY) {
          BattleEntity nextTarget = getClosestEntityRight();
          if (nextTarget != null) currentTarget = nextTarget;
        }
      }
    }
  }
  
  private BattleEntity getClosestEntityAbove() {
    List<BattleEntity> choices = entities.stream().filter(entity -> entity.getY() < currentTarget.getY() && TargetSelector.canSetTarget(entity, ICurrentBattleMoveSupplier.get())).collect(Collectors.toList());
    if (choices.size() > 0) return closestEntity(choices);
    return null;
  }
  
  private BattleEntity getClosestEntityRight() {
    List<BattleEntity> choices = entities.stream().filter(entity -> entity.getX() > currentTarget.getX() && TargetSelector.canSetTarget(entity, ICurrentBattleMoveSupplier.get())).collect(Collectors.toList());
    if (choices.size() > 0) return closestEntity(choices);
    return null;
  }
  
  private BattleEntity getClosestEntityBelow() {
    List<BattleEntity> choices = entities.stream().filter(entity -> entity.getY() > currentTarget.getY() && TargetSelector.canSetTarget(entity, ICurrentBattleMoveSupplier.get())).collect(Collectors.toList());
    if (choices.size() > 0) return closestEntity(choices);
    return null;
  }
  
  private BattleEntity getClosestEntityLeft() {
    List<BattleEntity> choices = entities.stream().filter(entity -> entity.getX() < currentTarget.getX() && TargetSelector.canSetTarget(entity, ICurrentBattleMoveSupplier.get())).collect(Collectors.toList());
    if (choices.size() > 0) return closestEntity(choices);
    return null;
  }
  
  private BattleEntity closestEntity(List<BattleEntity> entities) {
    BattleEntity returnValue = entities.get(0);
    for (BattleEntity entity: entities) {
      if (getDistance(entity, currentTarget) < getDistance(returnValue, currentTarget)) returnValue = entity;
    }
    return returnValue;
  }
  
  private double getDistance(BattleEntity a, BattleEntity b) {
    return Math.sqrt(Math.pow(Math.abs(a.getX() - b.getX()), 2) + Math.pow(Math.abs(a.getY() - b.getY()), 2));
  }
  
  public static boolean canSetTarget(BattleEntity entity, BattleMove move) {
    return !(entity.isDead() && move.getAction().equals(BattleMove.OFFENSIVE));
  }
  
  public void destroy() {
    Keyboard.getKeyboard().removeKeyListener(keyListener);
  }
  
  public void selectSingleTarget(BattleEntity target) {
    currentTarget = target;
    targeting = true;
    singleTargetOnly = true;
  }
  
  public void selectEnemyTarget() {
    singleTargetOnly = false;
    if (currentTarget != null) {
      if (!currentTarget.isDead() && currentTarget instanceof EnemyBattleEntity) {
        targeting = true;
        return;
      }
    }
    for (EnemyBattleEntity enemy: enemies) {
      if (!enemy.isDead()) {
        currentTarget = enemy;
        break;
      }
    }
    targeting = true;
  }
  
  public void selectCharacterTarget() {
    singleTargetOnly = false;
    if (currentTarget != null) {
      if (!currentTarget.isDead() && currentTarget instanceof CharacterBattleEntity) {
        targeting = true;
        return;
      }
    }
    for (CharacterBattleEntity character: party) {
      if (!character.isDead()) {
        currentTarget = character;
        break;
      }
    }
    targeting = true;
  }

  public void setTargeting(boolean targeting) {
    this.targeting = targeting;
  }
  
  public void update() {
    selectorIconAnim.update();
    if (targeting && singleTargetOnly) {
      mouseTargetButtons.stream()
        .filter(button -> button.getEntity() == currentTarget)
        .findAny()
        .get()
        .update();
    }
    else if (targeting && !singleTargetOnly) {
      for (BattleTargetButton button: mouseTargetButtons) {
        button.update();
      }
    }
  }

  public void render(Screen screen, GL2 gl) {
    if (targeting) {
      screen.renderSprite(gl, currentTarget.getX() - selectorIconAnim.getSprite().getWidth() / 2, currentTarget.getY() - currentTarget.getCurrentAction().getHeight() / 2 - 15, selectorIconAnim.getSprite(), false);
      if (targeting && singleTargetOnly) {
        mouseTargetButtons.stream()
          .filter(button -> button.getEntity() == currentTarget)
          .findAny()
          .get()
          .render(screen, gl);
      }
      else if (targeting && !singleTargetOnly) {
        for (BattleTargetButton button: mouseTargetButtons) {
          if (button.getEntity() != currentTarget) button.render(screen, gl);
        }
      }
    }
  }
  
}
