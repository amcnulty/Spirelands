package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterWeaponDetailCard {
  
  private Character character;
  private final FontInfo  name = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final ArrayList<FontInfo> statLabels = new ArrayList<>(),
                                    statValues = new ArrayList<>();
  private final String[] labelNames = {
    Character.ATTACK,
    Character.DEFENSE,
    Character.INTELLECT,
    Character.SPIRIT,
    Character.SPEED,
    Character.LUCK
  };
  private final Consumer<Item> IShowItemDetail;
  private final Consumer<Character> ICharacterChanger;
  private final Supplier[] ILabelValueGetters = {
    () -> {
      attackStat = character.getCombinedAttack();
      return String.valueOf(character.getCombinedAttack());
    },
    () -> {
      defenseStat = character.getCombinedDefense();
      return String.valueOf(character.getCombinedDefense());
    },
    () -> {
      intellectStat = character.getCombinedIntellect();
      return String.valueOf(character.getCombinedIntellect());
    },
    () -> {
      spiritStat = character.getCombinedSpirit();
      return String.valueOf(character.getCombinedSpirit());
    },
    () -> {
      speedStat = character.getCombinedSpeed();
      return String.valueOf(character.getCombinedSpeed());
    },
    () -> {
      luckStat = character.getCombinedLuck();
      return String.valueOf(character.getCombinedLuck());
    }
  };
  private final int cardWidth = 89,
                    sidePadding = 5,
                    cardTop = 23,
                    cardCenterHoriz = 351,
                    cardCenterVert = 100,
                    cardLeft = 307,
                    cardRight = cardLeft + cardWidth,
                    spaceBetweenStatRow = 7;
  private int attackStat,
              defenseStat,
              intellectStat,
              spiritStat,
              speedStat,
              luckStat,
              characterIndex;
  private Sprite thumbnail;
  private final EquippedItemSlot equippedWeaponSlot = new EquippedItemSlot(
    cardCenterHoriz,
    140,
    () -> character.getEquippedWeapon(),
    () -> showEquippedItemDetails(),
    () -> character.removeEquippedWeapon()
  );
  private final GameMenuSecondaryButton
    previousButton = new GameMenuSecondaryButton(
      "\u2039",
      cardLeft + sidePadding * 2,
      cardTop + 30,
      9,
      29,
      () -> setPreviousCharacter()
    ),
    nextButton = new GameMenuSecondaryButton(
      "\u203A",
      cardRight - sidePadding * 2,
      cardTop + 30,
      9,
      29,
      () -> setNextCharacter()
    );
  
  public CharacterWeaponDetailCard(Consumer<Item> IShowItemDetail, Consumer<Character> ICharacterChanger) {
    this.IShowItemDetail = IShowItemDetail;
    this.ICharacterChanger = ICharacterChanger;
    name.setY(cardTop + sidePadding + 1);
  }
  
  private void showEquippedItemDetails() {
    IShowItemDetail.accept(character.getEquippedWeapon());
  }
  
  private void setDetails() {
    statLabels.clear();
    thumbnail = character.getThumbnail();
    name.setText(character.getName());
    name.setX(cardCenterHoriz);
    name.centerText();
    for (int i = 0; i < labelNames.length; i++) {
      FontInfo info = GameFonts.getGAME_MENU_LABEL_TEXT();
      info.setText(labelNames[i] + ":");
      info.setX(cardLeft + 22);
      info.setY(cardTop + 55 + spaceBetweenStatRow * i);
      statLabels.add(info);
    }
    setStatValues();
  }
  
  private void setStatValues() {
    statValues.clear();
    int i = 0;
    for (Supplier supplier: ILabelValueGetters) {
      FontInfo info = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
      info.setText((String)supplier.get());
      info.setX(cardRight - 22);
      info.setY(cardTop + 55 + spaceBetweenStatRow * i++);
      info.rightAlignText();
      statValues.add(info);
    }
  }
  
  private void checkStats() {
    if (attackStat != character.getCombinedAttack()) setStatValues();
  }
  
  private void setPreviousCharacter() {
    characterIndex--;
    if (characterIndex == -1) characterIndex = CharacterManager.getCharacterManager().getPartyMembers().size() - 1;
    ICharacterChanger.accept(CharacterManager.getCharacterManager().getPartyMembers().get(characterIndex));
  }
  
  private void setNextCharacter() {
    characterIndex++;
    if (characterIndex == CharacterManager.getCharacterManager().getPartyMembers().size()) characterIndex = 0;
    ICharacterChanger.accept(CharacterManager.getCharacterManager().getPartyMembers().get(characterIndex));
  }
  
  public void closePopover() {
    equippedWeaponSlot.closePopover();
  }
  
  public Character getCharacter() {
    return this.character;
  }
  
  public void setCharacter(Character character) {
    this.character = character;
    characterIndex = CharacterManager.getCharacterManager().getPartyMembers().indexOf(character);
    setDetails();
  }
  
  public void update() {
    checkStats();
    equippedWeaponSlot.update();
    previousButton.update();
    nextButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    if (character != null) {
      screen.renderFonts(name);
      previousButton.render(screen, gl);
      screen.renderSprite(gl, cardCenterHoriz - thumbnail.getWidth() / 2, cardTop + 14, thumbnail, false);
      nextButton.render(screen, gl);
      for (FontInfo info: statLabels) {
        screen.renderFonts(info);
      }
      for (FontInfo info: statValues) {
        screen.renderFonts(info);
      }
      equippedWeaponSlot.render(screen, gl);
    }
  }

}
