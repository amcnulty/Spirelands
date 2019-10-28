package com.monkeystomp.spirelands.battle.controls;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TargetInformation {

  private boolean multiTarget = false;
  private ArrayList<? extends BattleEntity> targets = new ArrayList<>();
  private BattleEntity target;

  public boolean isMultiTarget() {
    return multiTarget;
  }

  public void setMultiTarget(boolean multiTarget) {
    this.multiTarget = multiTarget;
  }

  public ArrayList<? extends BattleEntity> getTargets() {
    return targets;
  }

  public void setTargets(ArrayList<? extends BattleEntity> targets) {
    this.targets = targets;
    setMultiTarget(true);
  }

  public BattleEntity getTarget() {
    return target;
  }

  public void setTarget(BattleEntity target) {
    this.target = target;
  }

}
