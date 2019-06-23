package com.monkeystomp.spirelands.tests.character;

import com.monkeystomp.spirelands.character.Character;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterTest {
  
  private final Character testCharacter = new Character();
  private final Character expCharacter = new Character();
  private final Character levelCharacter = new Character();
  
  @Test
  public void increaseLevelTest() {
    levelCharacter.setHealthWeight(Character.VERY_LOW);
    levelCharacter.setManaWeight(Character.VERY_LOW);
    levelCharacter.setStrengthWeight(Character.VERY_LOW);
    levelCharacter.setDefenseWeight(Character.VERY_LOW);
    levelCharacter.setIntellectWeight(Character.VERY_LOW);
    levelCharacter.setSpiritWeight(Character.VERY_LOW);
    levelCharacter.setSpeedWeight(Character.VERY_LOW);
    levelCharacter.setLuckWeight(Character.VERY_LOW);
    
    levelCharacter.setLevel(1);
    levelCharacter.setStrength(10);
    levelCharacter.increaseLevel(1);
    Assert.assertEquals("After increasing one level from level 1 character should be at level 2", 2, levelCharacter.getLevel());
    Assert.assertEquals("After increasing one level strength stat should be one point higher", 11, levelCharacter.getStrength());
    levelCharacter.setLevel(1);
    levelCharacter.setStrength(10);
    levelCharacter.increaseLevel(3);
    Assert.assertEquals("After increasing three levels from level 1 character should be at level 4", 4, levelCharacter.getLevel());
    Assert.assertEquals("After increasing three levels strength stat should be three points higher", 13, levelCharacter.getStrength());
    levelCharacter.setLevel(99);
    levelCharacter.setStrength(100);
    levelCharacter.increaseLevel(5);
    Assert.assertEquals("Attempting to increase to a level beyond 100 should not set the level higher than 100", 100, levelCharacter.getLevel());
    Assert.assertEquals("Attempting to increase to a level beyond 100 should not increase the stats higher than expected", 101, levelCharacter.getStrength());
  }
  
  @Test
  public void getExpToNextLevelTest() {
    testCharacter.setLevel(1);
    Assert.assertEquals(12, testCharacter.getExpToNextLevel());
    testCharacter.setLevel(2);
    Assert.assertEquals("Experience points to get to level three should be 40", 40, testCharacter.getExpToNextLevel());
    testCharacter.setLevel(49);
    Assert.assertEquals("Experience points to get to level 50 should be 285014", 285014, testCharacter.getExpToNextLevel());
    testCharacter.setLevel(99);
    Assert.assertEquals("Experience points to get to level 100 should be 3515032", 3515032, testCharacter.getExpToNextLevel());
  }
  
  @Test
  public void addExperiencePointsTest() {
    expCharacter.setHealthWeight(Character.AVERAGE);
    expCharacter.setManaWeight(Character.AVERAGE);
    expCharacter.setStrengthWeight(Character.AVERAGE);
    expCharacter.setDefenseWeight(Character.AVERAGE);
    expCharacter.setIntellectWeight(Character.AVERAGE);
    expCharacter.setSpiritWeight(Character.AVERAGE);
    expCharacter.setSpeedWeight(Character.AVERAGE);
    expCharacter.setLuckWeight(Character.AVERAGE);
    
    expCharacter.setLevel(1);
    Assert.assertEquals("New character should have zero experience", 0, expCharacter.getExperience());
    expCharacter.addExperiencePoints(6);
    Assert.assertEquals("After adding 6 experience points character should have 6 experience points", 6, expCharacter.getExperience());
    expCharacter.setExperience(0);
    expCharacter.addExperiencePoints(20);
    Assert.assertEquals("After adding 20 exp at level 1 character should be at level 2", 2, expCharacter.getLevel());
    Assert.assertEquals("After adding 20 exp at level 1 character should have 8 exp at level 2", 8, expCharacter.getExperience());
    expCharacter.setLevel(49);
    expCharacter.setExperience(284000);
    expCharacter.addExperiencePoints(2014);
    Assert.assertEquals("After adding 2014 exp at level 49 with a total of 284000 exp character should be at level 50", 50, expCharacter.getLevel());
    Assert.assertEquals("After adding 2014 exp at level 49 with a total of 284000 exp character should have 1000 exp", 1000, expCharacter.getExperience());
  }

}
