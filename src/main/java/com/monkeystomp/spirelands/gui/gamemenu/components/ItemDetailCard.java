package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.inventory.ItemAttribute;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ItemDetailCard {
  
  private Sprite thumbnail;
  private final String  noSelectedItem = "Select an item to view more information about it here.",
                        times = "\u2A2F";
  private final FontInfo  titleFont = GameFonts.getlightText_bold_23(),
                          descriptionFont = GameFonts.getGAME_MENU_MUTED_TEXT(),
                          typeFont = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final int price = 0,
                    cardWidth = 89,
                    sidePadding = 5,
                    cardTop = 23,
                    cardCenterHoriz = 351,
                    cardCenterVert = 100,
                    cardLeft = 307,
                    cardRight = cardLeft + cardWidth,
                    attributeListStartingY = 130;
  private boolean itemSet = false;
  private final Consumer<ItemDetailCard> ICloseOperation;
  private final ArrayList<FontInfo> noSelectedInfoList = new ArrayList<>(),
                                    itemDescriptionList = new ArrayList<>(),
                                    attributeLabelList = new ArrayList<>(),
                                    attributeValueList = new ArrayList<>();
  private final GameMenuSecondaryButton exitButton = new GameMenuSecondaryButton(
    times,
    cardLeft + sidePadding,
    cardTop + sidePadding,
    9,
    9,
    () -> {handleExitButtonClick();}
  );
  
  public ItemDetailCard(Consumer<ItemDetailCard> ICloseOperation) {
    ArrayList<String> lines = TextUtil.createWrappedText(noSelectedItem, GameFonts.getGAME_MENU_MUTED_TEXT().getFont(), cardWidth - (2 * sidePadding));
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(cardLeft + sidePadding);
      info.setY(cardCenterVert + i * 7);
      noSelectedInfoList.add(info);
    }
    this.ICloseOperation = ICloseOperation;
  }
  
  private void handleExitButtonClick() {
    ICloseOperation.accept(this);
  }
  
  public void setItem(Item item) {
    this.thumbnail = item.getThumbnail();
    titleFont.setText(item.getTitle());
    titleFont.setX(cardCenterHoriz);
    titleFont.setY(30);
    titleFont.centerText();
    
    switch (item.getType()) {
      case Item.WEAPON:
        typeFont.setText(((WeaponItem)item).getWeaponType());
        break;
      case Item.ARMOR:
        typeFont.setText(((ArmorItem)item).getArmorType());
        break;
      default:
        typeFont.setText(item.getType());
        break;
    }
    typeFont.setX(cardCenterHoriz);
    typeFont.setY(65);
    typeFont.centerText();
    
    descriptionFont.setText(item.getDescription());
    descriptionFont.setX(cardLeft + sidePadding);
    descriptionFont.setY(75);
    
    ArrayList<String> lines = TextUtil.createWrappedText(item.getDescription(), descriptionFont.getFont(), cardWidth - 2 * sidePadding);
    itemDescriptionList.clear();
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(cardLeft + sidePadding);
      info.setY(75 + i * 7);
      itemDescriptionList.add(info);
    }
    
    ArrayList<ItemAttribute> attributes = item.getAttributes();
    attributeLabelList.clear();
    attributeValueList.clear();
    for (int i = 0; i < attributes.size(); i++) {
      FontInfo labelInfo = GameFonts.getGAME_MENU_LABEL_TEXT();
      labelInfo.setText(attributes.get(i).getLabel() + ":");
      labelInfo.setX(cardLeft + sidePadding);
      labelInfo.setY(attributeListStartingY + i * 7);
      attributeLabelList.add(labelInfo);
      
      FontInfo valueInfo = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
      valueInfo.setText(attributes.get(i).getValue());
      valueInfo.setX(cardRight - sidePadding);
      valueInfo.setY(attributeListStartingY + i * 7);
      valueInfo.rightAlignText();
      attributeValueList.add(valueInfo);
    }
    
    itemSet = true;
  }
  
  public void clearCard() {
    itemSet = false;
  }

  public void update() {
    if (itemSet) exitButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    if (itemSet) {
      exitButton.render(screen, gl);
      screen.renderFonts(titleFont);
      screen.renderSprite(gl, cardCenterHoriz - thumbnail.getWidth() / 2, 40, thumbnail, false);
      screen.renderFonts(typeFont);
      for (FontInfo info: itemDescriptionList) {
        screen.renderFonts(info);
      }
      for (FontInfo info: attributeLabelList) {
        screen.renderFonts(info);
      }
      for (FontInfo info: attributeValueList) {
        screen.renderFonts(info);
      }
    }
    else {
      for (FontInfo info: noSelectedInfoList) {
        screen.renderFonts(info);
      }
    }
  }
}
