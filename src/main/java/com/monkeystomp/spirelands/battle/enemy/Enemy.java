package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.character.StatModel;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Enemy extends StatModel {
  
  // The elemental type of this enemy.
  private final String element;
  // Sprite sheet for this enemy
  private final SpriteSheet spriteSheet;
  // Loot item that this enemy is holding.
  private final Item loot;
  // The drop rate of this enemy.
  private final int dropRate;
  // The experience gained from defeating this enemy.
  private final int experienceAward;
  // The gold gained from defeating this enemy.
  private final int goldAward;
  // Moves that this enemy can perform.
  private final ArrayList<BattleMove> enemyMoves;
  // List of elemental effects for this enemy.
  private final ArrayList<ElementalEffect> elementalEffects;
  // Random class.
  private final Random random = new Random();
  /**
   * Creates a new Enemy object with the given configuration from the EnemyBuilder object.
   * @param builder Configuration object for creating this Enemy instance.
   */
  public Enemy(EnemyBuilder builder) {
    this.spriteSheet = builder.spriteSheet;
    this.name = builder.name;
    this.element = builder.element;
    this.level = builder.level;
    this.health = builder.health;
    this.healthMax = builder.healthMax;
    this.mana = builder.mana;
    this.manaMax = builder.manaMax;
    this.strength = builder.strength;
    this.defense = builder.defense;
    this.intellect = builder.intellect;
    this.spirit = builder.spirit;
    this.speed = builder.speed;
    this.luck = builder.luck;
    this.loot = builder.loot;
    this.dropRate = builder.dropRate;
    this.experienceAward = builder.experienceAward;
    this.goldAward = builder.goldAward;
    this.enemyMoves = builder.enemyMoves;
    this.elementalEffects = builder.elementalEffects;
  }

  public String getElement() {
    return element;
  }
  
  @Override
  public ArrayList<ElementalEffect> getElementalDefenses() {
    ArrayList<ElementalEffect> list = new ArrayList<>();
    // Add other resistances to the list that have been manually added to this enemy.
    return list;
  }

  public SpriteSheet getSpriteSheet() {
    return spriteSheet;
  }

  public Item getLoot() {
    return loot;
  }

  public int getDropRate() {
    return dropRate;
  }

  public int getExperienceAward() {
    return experienceAward;
  }

  public int getGoldAward() {
    return goldAward;
  }
  /**
   * Gets any random move from this enemies list of moves.
   * @return An EnemyMove object.
   */
  public BattleMove getRandomMove() {
    return enemyMoves.get(random.nextInt(enemyMoves.size()));
  }
  /**
   * Gets a random move of the specified type and action.
   * @param type Physical | Magical
   * @param action Offensive | Defensive
   * @return An EnemyMove object.
   */
  public BattleMove getRandomMove(String type, String action) {
    List<BattleMove> moves = enemyMoves.stream().
            filter(move -> move.getType().equals(type) && move.getAction().equals(action))
            .collect(Collectors.<BattleMove>toList());
    return moves.get(random.nextInt(moves.size()));
  }

  public ArrayList<ElementalEffect> getWeaknesses() {
    return elementalEffects;
  }
  
  public int effectOfElement(String element) {
    List<ElementalEffect> elEffect = elementalEffects.stream()
            .filter(effect -> effect.getElement().equals(element))
            .collect(Collectors.toList());
    if (elEffect.size() > 0) return elEffect.get(0).getPercentage();
    else return 1;
  }

}
