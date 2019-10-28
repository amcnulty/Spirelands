/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import java.util.ArrayList;

/**
 * Interface intended for filtering moves with party, enemy and move user information provided.
 * @author Aaron Michael McNulty
 */
public interface IMoveCriteria {
  boolean filter(ArrayList<CharacterBattleEntity> party, ArrayList<EnemyBattleEntity> enemies, BattleEntity user);
}
