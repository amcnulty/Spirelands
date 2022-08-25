/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.spirelands.quest;

/**
 * Used to describe the status of a quest.
 * @author Aaron Michael McNulty
 */
public enum QuestStatus {
  /**
   * A new quest is one that has been given to the player but hasn't been opened in the game menu.
   */
  New,
  /**
   * An incomplete quest is an active quest that has been opened in the game menu but not finished.
   */
  Incomplete,
  /**
   * Complete quests are ones that have been finished.
   */
  Complete
}
