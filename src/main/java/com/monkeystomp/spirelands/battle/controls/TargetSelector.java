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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TargetSelector {
  
  private int x, y;
  private final TargetInformation targetInfo = new TargetInformation();
  private final AnimatedSprite selectorIconAnim = new AnimatedSprite(48, 16, new SpriteSheet("./resources/gui/battle_arrow_sheet.png"), AnimatedSprite.VERY_SLOW, 3);
  private ArrayList<CharacterBattleEntity>  party = new ArrayList<>();
  private ArrayList<EnemyBattleEntity> enemies = new ArrayList<>();
  private List<BattleEntity> entities;
  private final ArrayList<BattleTargetButton> mouseTargetButtons = new ArrayList<>();
  private BattleEntity currentTarget;
  private final Consumer<TargetInformation> IBattleEntitySelector;
  private final Supplier<BattleMove> ICurrentBattleMoveSupplier;
  private final Consumer<KeyEvent> keyListener = event -> handleKeyEvent(event);
  private boolean targeting = false,
                  singleTargetOnly = false;
  
  public TargetSelector(Consumer<TargetInformation> IBattleEntitySelector, Supplier<BattleMove> ICurrentBattleMoveSupplier) {
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
          TargetInformation info = new TargetInformation();
          if (ICurrentBattleMoveSupplier.get().isMultiTarget()) {
            if (entity instanceof CharacterBattleEntity) info.setTargets(party);
            else info.setTargets(enemies);
            IBattleEntitySelector.accept(info);
          }
          else {
            info = new TargetInformation();
            info.setTarget(entity);
            IBattleEntitySelector.accept(info);
          }
        }
      ));
    }
  }
  
  private void handleKeyEvent(KeyEvent event) {
    if (targeting) {
      if (event.getKeyCode() == Keyboard.SPACE_KEY || event.getKeyCode() == Keyboard.ENTER_KEY) {
        if (!isMultiTargetMove()) {
          targetInfo.setMultiTarget(false);
          targetInfo.setTarget(currentTarget);
        }
        IBattleEntitySelector.accept(targetInfo);
      }
      if (!singleTargetOnly) {
        if (event.getKeyCode() == Keyboard.W_KEY || event.getKeyCode() == Keyboard.UP_KEY) {
          BattleEntity nextTarget = getClosestEntityAbove();
          if (nextTarget != null) currentTarget = nextTarget;
        }
        else if (event.getKeyCode() == Keyboard.A_KEY || event.getKeyCode() == Keyboard.LEFT_KEY) {
          if (isMultiTargetMove()) selectAllEnemies();
          else {
            BattleEntity nextTarget = getClosestEntityLeft();
            if (nextTarget != null) currentTarget = nextTarget;
          }
        }
        else if (event.getKeyCode() == Keyboard.S_KEY || event.getKeyCode() == Keyboard.DOWN_KEY) {
          BattleEntity nextTarget = getClosestEntityBelow();
          if (nextTarget != null) currentTarget = nextTarget;
        }
        else if (event.getKeyCode() == Keyboard.D_KEY || event.getKeyCode() == Keyboard.RIGHT_KEY) {
          if (isMultiTargetMove()) selectAllCharacters();
          else {
            BattleEntity nextTarget = getClosestEntityRight();
            if (nextTarget != null) currentTarget = nextTarget;
          }
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
  
  private boolean isMultiTargetMove() {
    if (ICurrentBattleMoveSupplier.get() != null) {
      return ICurrentBattleMoveSupplier.get().isMultiTarget();
    }
    return false;
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
  
  public void selectAllEnemies() {
    singleTargetOnly = false;
    targetInfo.setMultiTarget(true);
    targetInfo.setTargets(
      (ArrayList<? extends BattleEntity>)enemies.stream()
      .filter(
        character -> TargetSelector.canSetTarget(character, ICurrentBattleMoveSupplier.get()))
      .collect(Collectors.toList())
    );
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
  
  public void selectAllCharacters() {
    singleTargetOnly = false;
    targetInfo.setMultiTarget(true);
    targetInfo.setTargets(
      (ArrayList<? extends BattleEntity>)party.stream()
      .filter(
        character -> TargetSelector.canSetTarget(character, ICurrentBattleMoveSupplier.get()))
      .collect(Collectors.toList())
    );
    targeting = true;
  }

  public void setTargeting(boolean targeting) {
    this.targeting = targeting;
  }
  
  public void update() {
    selectorIconAnim.update();
    if (targeting && singleTargetOnly) {
      for (BattleTargetButton button: mouseTargetButtons) {
        if (button.getEntity() == currentTarget) button.update();
      }
    }
    else if (targeting && !singleTargetOnly) {
      for (BattleTargetButton button: mouseTargetButtons) {
        button.update();
        button.hide();
      }
    }
    if (isMultiTargetMove()) {
      for (BattleTargetButton button: mouseTargetButtons) {
        if (button.isHovering()) {
          Predicate<BattleTargetButton> predicate;
          if (button.getEntity() instanceof CharacterBattleEntity) {
            predicate = but -> but.getEntity() instanceof CharacterBattleEntity;
          }
          else {
            predicate = but -> but.getEntity() instanceof EnemyBattleEntity;
          }
          for (BattleTargetButton charBut: mouseTargetButtons.stream().filter(predicate).collect(Collectors.toList())) {
            charBut.show();
          }
          break;
        }
      }
    }
  }

  public void render(Screen screen, GL2 gl) {
    if (targeting) {
      if (isMultiTargetMove()) {
        for (BattleEntity entity: targetInfo.getTargets()) {
          screen.renderSprite(gl, entity.getX() - selectorIconAnim.getSprite().getWidth() / 2, entity.getY() - entity.getCurrentAction().getHeight() / 2 - 15, selectorIconAnim.getSprite(), false);    
        }
      }
      else {
        screen.renderSprite(gl, currentTarget.getX() - selectorIconAnim.getSprite().getWidth() / 2, currentTarget.getY() - currentTarget.getCurrentAction().getHeight() / 2 - 15, selectorIconAnim.getSprite(), false);
        if (targeting && singleTargetOnly) {
          for (BattleTargetButton button: mouseTargetButtons) {
            if (button.getEntity() != currentTarget) button.render(screen, gl);
          }
        }
      }
      if (targeting && !singleTargetOnly) {
        for (BattleTargetButton button: mouseTargetButtons) {
          if (button.getEntity() != currentTarget) button.render(screen, gl);
        }
      }
    }
  }
  
}
