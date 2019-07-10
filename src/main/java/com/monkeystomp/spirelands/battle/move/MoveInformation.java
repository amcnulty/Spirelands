package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;

/**
 * Used to hold information about a move to be passed before move processing.
 * @author Aaron Michael McNulty
 */
public class MoveInformation {

  private final BattleEntity user;
  private final BattleEntity target;
  private final BattleMove move;
  
  public MoveInformation(BattleEntity user, BattleEntity target, BattleMove move) {
    this.user = user;
    this.target = target;
    this.move = move;
  }

  public BattleEntity getUser() {
    return user;
  }

  public BattleEntity getTarget() {
    return target;
  }

  public BattleMove getMove() {
    return move;
  }
  
}
