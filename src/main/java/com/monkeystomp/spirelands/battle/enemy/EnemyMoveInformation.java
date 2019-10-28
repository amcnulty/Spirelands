package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import java.util.function.Predicate;

/**
 * Holds information about a battle move and its associated filter criteria and targeting criteria.
 * @author Aaron Michael McNulty
 */
public class EnemyMoveInformation {
  /**
   * Target filter to include all character battle entities.
   */
  public static final Predicate<BattleEntity> TARGET_CHARACTERS = entity -> entity instanceof CharacterBattleEntity;
  /**
   * Target filter to include all enemy battle entities.
   */
  public static final Predicate<BattleEntity> TARGET_ENEMIES = entity -> entity instanceof EnemyBattleEntity;
  /**
   * Represents double the chance this move will be used over standard weighted moves.
   */
  public static final int DOUBLE_WEIGHT = 2;
  /**
   * Represents triple the chance this move will be used over standard weighted moves.
   */
  public static final int TRIPLE_WEIGHT = 3;
  /**
   * Represents quadruple the chance this move will be used over standard weighted moves.
   */
  public static final int QUAD_WEIGHT = 4;
  /**
   * Represents eight times the chance this move will be used over standard weighted moves.
   */
  public static final int EIGHT_WEIGHT = 8;
  /**
   * Represents ten times the chance this move will be used over standard weighted moves.
   */
  public static final int TEN_WEIGHT = 10;
  
  private final BattleMove move;
  private IMoveCriteria moveCriteria;
  private Predicate<BattleEntity> targetFilter = TARGET_CHARACTERS;
  private int moveWeight = 1;
  
  public EnemyMoveInformation(BattleMove move) {
    this.move = move;
  }
  
  public EnemyMoveInformation(BattleMove move, int moveWeight) {
    this.move = move;
    this.moveWeight = moveWeight;
  }
  
  public EnemyMoveInformation(BattleMove move, IMoveCriteria moveCriteria) {
    this.move = move;
    this.moveCriteria = moveCriteria;
  }
  
  public EnemyMoveInformation(BattleMove move, IMoveCriteria moveCriteria, int moveWeight) {
    this.move = move;
    this.moveCriteria = moveCriteria;
    this.moveWeight = moveWeight;
  }
  
  public EnemyMoveInformation(BattleMove move, Predicate<BattleEntity> targetFilter) {
    this.move = move;
    this.targetFilter = targetFilter;
  }
  
  public EnemyMoveInformation(BattleMove move, Predicate<BattleEntity> targetFilter, int moveWeight) {
    this.move = move;
    this.targetFilter = targetFilter;
    this.moveWeight = moveWeight;
  }
  
  public EnemyMoveInformation(BattleMove move, IMoveCriteria moveCriteria, Predicate<BattleEntity> targetFilter) {
    this.move = move;
    this.moveCriteria = moveCriteria;
    this.targetFilter = targetFilter;
  }
  
  public EnemyMoveInformation(BattleMove move, IMoveCriteria moveCriteria, Predicate<BattleEntity> targetFilter, int moveWeight) {
    this.move = move;
    this.moveCriteria = moveCriteria;
    this.targetFilter = targetFilter;
    this.moveWeight = moveWeight;
  }

  public BattleMove getMove() {
    return move;
  }
  
  public boolean hasMoveCriteria() {
    return moveCriteria != null;
  }

  public IMoveCriteria getMoveCriteria() {
    return moveCriteria;
  }

  public Predicate<BattleEntity> getTargetFilter() {
    return targetFilter;
  }

  public int getMoveWeight() {
    return moveWeight;
  }

}
