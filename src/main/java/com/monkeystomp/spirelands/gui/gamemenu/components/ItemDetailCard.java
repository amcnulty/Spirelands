package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ItemDetailCard {
  
  private Item item;
  private Sprite thumbnail;
  private String  title,
                  description,
                  type,
                  noSelectedItem = "Select an item to view more information about it here.";
  private FontInfo  titleFont = GameFonts.getlightText_bold_23(),
                    descriptionFont = GameFonts.getGAME_MENU_MUTED_TEXT(),
                    typeFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                    priceFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private int price,
              cardWidth = 89,
              sidePadding = 5,
              cardCenterHoriz = 351,
              cardCenterVert = 100,
              cardLeft = 307;
  private boolean itemSet = false;
  private ArrayList<FontInfo> noSelectedInfoList = new ArrayList<>(),
                              itemDescriptionList = new ArrayList<>();
  
  public ItemDetailCard() {
    ArrayList<String> lines = TextUtil.createWrappedText(noSelectedItem, GameFonts.getGAME_MENU_MUTED_TEXT().getFont(), cardWidth - (2 * sidePadding));
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(cardLeft + sidePadding);
      info.setY(cardCenterVert + i * 7);
      noSelectedInfoList.add(info);
    }
  }
  
  public void setItem(Item item) {
//    this.item = item;
    this.thumbnail = item.getThumbnail();
//    this.title = item.getTitle();
    titleFont.setText(item.getTitle());
    titleFont.setX(cardCenterHoriz);
    titleFont.setY(30);
    titleFont.centerText();
    
    typeFont.setText(item.getTypeAsString(item.getType()));
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
    
//    this.description = item.getDescription();
//    this.price = item.getPrice();
//    this.type = item.getTypeAsString(item.getType());
    itemSet = true;
  }
  
  public void clearCard() {
    itemSet = false;
  }

  public void update() {
  }
  
  public void render(Screen screen, GL2 gl) {
    if (itemSet) {
      screen.renderFonts(titleFont);
      screen.renderSprite(gl, cardCenterHoriz - thumbnail.getWidth() / 2, 40, thumbnail, false);
      screen.renderFonts(typeFont);
      for (FontInfo info: itemDescriptionList) {
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
