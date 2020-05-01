package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import java.util.ArrayList;

/**
 * The Ability list is shown on the abilities screen and allows users to equip moves.
 * @author Aaron Michael McNulty
 */
public class AbilityList {
  
  private final int top, left,
                    itemsPerPage = 10, itemsPerRow = 5,
                    leftRowX, rightRowX,
                    listItemYStart, listItemYSpacing = 17;
  private boolean showing = false;
  private Character character;
  private AbilitySlotClickEvent currentEvent;
  private ArrayList<BattleMove> moveList;
  private ArrayList<AbilityListItem> listItems = new ArrayList<>();
  private final Pagination pagination = new Pagination(10, 260, 169, (pageIndex) -> createListItems(pageIndex));
  
  public AbilityList(int top, int left) {
    this.top = top;
    this.left = left;
    this.leftRowX = left + 5;
    this.rightRowX = left + 138;
    listItemYStart = top;
  }
  
  public void show(AbilitySlotClickEvent event) {
    this.currentEvent = event;
    if (!event.getType().equals(BattleMove.BUFF)) {
      moveList = InventoryManager.getInventoryManager().getBattleMovesByType(event.getType(), BattleMove.BUFF);
    }
    else {
      moveList = InventoryManager.getInventoryManager().getBattleMovesByAction(event.getType());
    }
    pagination.setListLength(moveList.size());
    pagination.highlightCurrentPage(0);
    createListItems(0);
    showing = true;
  }

  public void hide() {
    showing = false;
  }
  
  private void createListItems(int page) {
    listItems.clear();
    for (int i = 0; i < itemsPerPage - itemsPerRow; i++) {
      if (page * itemsPerPage + i > moveList.size() - 1)
        break;
      listItems.add(
        new AbilityListItem(listItemYStart + listItemYSpacing * i, leftRowX, moveList.get(page * itemsPerPage + i),
          () -> {},
          move -> character.equipAbilitySlotMove(currentEvent.getSlot(), move)
        )
      );
    }
    for (int i = itemsPerRow; i < itemsPerPage; i++) {
      if (page * itemsPerPage + i > moveList.size() - 1)
        break;
      listItems.add(
        new AbilityListItem(listItemYStart + listItemYSpacing * (i - itemsPerRow), rightRowX, moveList.get(page * itemsPerPage + i),
          () -> {},
          move -> character.equipAbilitySlotMove(currentEvent.getSlot(), move)
        )
      );
    }
  }
  
  public void setCharacter(Character character) {
    this.character = character;
  }
  
  public void update() {
    if (showing) {
      for (AbilityListItem listItem: listItems) {
        listItem.update();
      }
      pagination.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (showing) {
      for (AbilityListItem listItem: listItems) {
        listItem.render(screen, gl);
      }
      pagination.render(screen, gl);
    }
  }

}
