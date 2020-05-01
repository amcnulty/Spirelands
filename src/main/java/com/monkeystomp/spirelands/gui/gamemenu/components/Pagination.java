package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPaginationButton;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Pagination {
  
  private int listLength = 0,
              currentPageIndex = 0;
  private final int itemsPerPage,
                    minLengthToShowPagination,
                    paginationY,
                    paginationHeight = 11,
                    centerHoriz,
                    pageButtonWidth = 13;
  private GameMenuPrimaryButton lastPage;
  private final ArrayList<GameMenuPaginationButton> pageButtons = new ArrayList<>();
  private GameMenuPrimaryButton nextPage;
  private final Consumer<Integer> IPageSetter;
  
  public Pagination(int itemsPerPage, int xCenter, int yCenter, Consumer<Integer> IPageSetter) {
    this.itemsPerPage = itemsPerPage;
    this.minLengthToShowPagination = itemsPerPage + 1;
    this.centerHoriz = xCenter;
    this.paginationY = yCenter;
    this.IPageSetter = IPageSetter;
  }
  
  private void setPageButtons() {
    pageButtons.clear();
    int numberOfPages = ((listLength - 1) / itemsPerPage) + 1;
    if (numberOfPages > 1) {
      if (numberOfPages % 2 == 0) {
        for (int i = 0; i < numberOfPages; i++) {
          pageButtons.add(new GameMenuPaginationButton(
            String.valueOf(i + 1),
            centerHoriz - ((pageButtonWidth / 2) * (numberOfPages / 2)) + (i * pageButtonWidth),
            paginationY,
            pageButtonWidth,
            paginationHeight,
            i,
            pageIndex -> handlePageButtonPress(pageIndex)
          ));
        }
      }
      else {
        for (int i = 0; i < numberOfPages; i++) {
          pageButtons.add(new GameMenuPaginationButton(
            String.valueOf(i + 1),
            centerHoriz - (pageButtonWidth * ((numberOfPages - 1) / 2)) + (i * pageButtonWidth),
            paginationY,
            pageButtonWidth,
            paginationHeight,
            i,
            pageIndex -> handlePageButtonPress(pageIndex)
          ));
        }
      }
      lastPage = new GameMenuPrimaryButton(
              "\u2039 last",
              pageButtons.get(0).getLeft() - 12,
              paginationY,
              23,
              paginationHeight,
              () -> handlePageButtonPress(currentPageIndex - 1)
      );
      nextPage = new GameMenuPrimaryButton(
              "next \u203A",
              pageButtons.get(pageButtons.size() - 1).getRight() + 12,
              paginationY,
              23,
              paginationHeight,
              () -> handlePageButtonPress(currentPageIndex + 1)
      );
    }
  }
  
  private void handlePageButtonPress(Integer pageIndex) {
    resetPageButtons();
    IPageSetter.accept(pageIndex);
    highlightCurrentPage(pageIndex);
  }
  
  private void resetPageButtons() {
    for (GameMenuPaginationButton button: pageButtons) {
      button.removeBackground();
    }
  }

  public void setListLength(int listLength) {
    this.listLength = listLength;
    setPageButtons();
  }
  
  public void highlightCurrentPage(int currentPageIndex) {
    if (pageButtons.size() > currentPageIndex) {
      resetPageButtons();
      pageButtons.get(currentPageIndex).setActive();
      this.currentPageIndex = currentPageIndex;
    }
  }
  
  public void update() {
    if (listLength >= minLengthToShowPagination) {
      if (currentPageIndex != 0) lastPage.update();
      for (GameMenuPaginationButton button: pageButtons) {
        button.update();
      }
      if (currentPageIndex != pageButtons.size() - 1) nextPage.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (listLength >= minLengthToShowPagination) {
      if (currentPageIndex != 0) lastPage.render(screen, gl);
      for (GameMenuPaginationButton button: pageButtons) {
        button.render(screen, gl);
      }
      if (currentPageIndex != pageButtons.size() - 1) nextPage.render(screen, gl);
    }
  }
  
}
