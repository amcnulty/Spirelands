package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestDetailModal;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.QuestTabs;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.quest.Quest;
import com.monkeystomp.spirelands.quest.QuestManager;
import com.monkeystomp.spirelands.quest.QuestStatus;
import com.monkeystomp.spirelands.quest.QuestType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The game menu view for when users click on the quests button.
 * @author Aaron Michael McNulty
 */
public class QuestsView extends DisplayView {
  
  private final int questListItemX = 130,
                    questListItemStartingY = 40,
                    spaceBetweenItems = 20,
                    itemsPerPage = 6;
  private int currentPageIndex = 0;
  private boolean isShowingQuestDetails = false;
  private static final HashMap<String, ICallback> TAB_CLICK_HANDLERS = new HashMap<>();
  private final QuestTabs questTabs = new QuestTabs((clickedTab) -> TAB_CLICK_HANDLERS.get(clickedTab).execute());
  private final ArrayList<ArrayList<QuestListItem>> mainPages = new ArrayList<>();
  private final ArrayList<ArrayList<QuestListItem>> secondaryPages = new ArrayList<>();
  private final ArrayList<ArrayList<QuestListItem>> collectiblesPages = new ArrayList<>();
  private final ArrayList<ArrayList<QuestListItem>> completedPages = new ArrayList<>();
  private ArrayList<ArrayList<QuestListItem>> activePages = mainPages;
  private final Pagination pagination = new Pagination(itemsPerPage, 260, 169, pageIndex -> currentPageIndex = pageIndex);
  private final Consumer<Quest> handleListItemClick = (quest) -> showQuestDetails(quest);
  private final QuestDetailModal questDetailModal = new QuestDetailModal(() -> isShowingQuestDetails = false);
  private final HashMap<Integer, Quest> acceptedQuestsMap = QuestManager.getQuestManager().getAcceptedQuests();
  
  public QuestsView() {
    TAB_CLICK_HANDLERS.put(QuestTabs.MAIN_TAB, () -> {
        activePages = mainPages;
        initPagination();
      });
    TAB_CLICK_HANDLERS.put(QuestTabs.SECONDARY_TAB, () -> {
        activePages = secondaryPages;
        initPagination();
      });
    TAB_CLICK_HANDLERS.put(QuestTabs.COLLECTIBLES_TAB, () -> {
        activePages = collectiblesPages;
        initPagination();
      });
    TAB_CLICK_HANDLERS.put(QuestTabs.COMPLETED_TAB, () -> {
        activePages = completedPages;
        initPagination();
      });
  }

  private void createListItems() {
    // Clear all the pages
    mainPages.clear();
    secondaryPages.clear();
    collectiblesPages.clear();
    completedPages.clear();
    // Filter the quest types into different lists
    List<Quest> mainQuests = acceptedQuestsMap.values().stream()
      .filter(quest -> !quest.getStatus().equals(QuestStatus.Complete) && quest.getType().equals(QuestType.Main))
      .collect(Collectors.toList());
    List<Quest> secondaryQuests = acceptedQuestsMap.values().stream()
      .filter(quest -> !quest.getStatus().equals(QuestStatus.Complete) && quest.getType().equals(QuestType.Secondary))
      .collect(Collectors.toList());
    List<Quest> collectiblesQuests = acceptedQuestsMap.values().stream()
      .filter(quest -> !quest.getStatus().equals(QuestStatus.Complete) && quest.getType().equals(QuestType.Collectible))
      .collect(Collectors.toList());
    List<Quest> completedQuests = acceptedQuestsMap.values().stream()
      .filter(quest -> quest.getStatus().equals(QuestStatus.Complete)).collect(Collectors.toList());
    // Create the pages for each quest type
    for (int i = 0; i < mainQuests.size() / itemsPerPage + 1; i++) {
      ArrayList<QuestListItem> newPage = new ArrayList<>();
      for (int j = i * itemsPerPage; j < itemsPerPage + (i * itemsPerPage); j++) {
        if (mainQuests.size() - 1 < j) break;
        newPage.add(new QuestListItem(
          questListItemX,
          questListItemStartingY + newPage.size() * spaceBetweenItems,
          (Quest)mainQuests.get(j),
          handleListItemClick
        ));
      }
      if (newPage.size() > 0) mainPages.add(newPage);
    }
    for (int i = 0; i < secondaryQuests.size() / itemsPerPage + 1; i++) {
      ArrayList<QuestListItem> newPage = new ArrayList<>();
      for (int j = i * itemsPerPage; j < itemsPerPage + (i * itemsPerPage); j++) {
        if (secondaryQuests.size() - 1 < j) break;
        newPage.add(new QuestListItem(
          questListItemX,
          questListItemStartingY + newPage.size() * spaceBetweenItems,
          (Quest)secondaryQuests.get(j),
          handleListItemClick
        ));
      }
      if (newPage.size() > 0) secondaryPages.add(newPage);
    }
    for (int i = 0; i < collectiblesQuests.size() / itemsPerPage + 1; i++) {
      ArrayList<QuestListItem> newPage = new ArrayList<>();
      for (int j = i * itemsPerPage; j < itemsPerPage + (i * itemsPerPage); j++) {
        if (collectiblesQuests.size() - 1 < j) break;
        newPage.add(new QuestListItem(
          questListItemX,
          questListItemStartingY + newPage.size() * spaceBetweenItems,
          (Quest)collectiblesQuests.get(j),
          handleListItemClick
        ));
      }
      if (newPage.size() > 0) collectiblesPages.add(newPage);
    }
    for (int i = 0; i < completedQuests.size() / itemsPerPage + 1; i++) {
      ArrayList<QuestListItem> newPage = new ArrayList<>();
      for (int j = i * itemsPerPage; j < itemsPerPage + (i * itemsPerPage); j++) {
        if (completedQuests.size() - 1 < j) break;
        newPage.add(new QuestListItem(
          questListItemX,
          questListItemStartingY + newPage.size() * spaceBetweenItems,
          (Quest)completedQuests.get(j),
          handleListItemClick
        ));
      }
      if (newPage.size() > 0) completedPages.add(newPage);
    }
    initPagination();
  }
  
  private void initPagination() {
    currentPageIndex = 0;
    pagination.setListLength(getListLengthOfActivePage());
    pagination.highlightCurrentPage(currentPageIndex);
  }
  
  private int getListLengthOfActivePage() {
    return itemsPerPage * (activePages.size() - 1) + activePages.get(activePages.size() - 1).size();
  }
  
  private void showQuestDetails(Quest quest) {
    questDetailModal.setQuest(quest);
    isShowingQuestDetails = true;
  }
  
  @Override
  public void enteringView() {
    createListItems();
    questTabs.resetTabs();
    isShowingQuestDetails = false;
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
    if (isShowingQuestDetails) {
      questDetailModal.update();
    }
    else {
      questTabs.update();
      for (QuestListItem questListItem: activePages.get(currentPageIndex)) {
        questListItem.update();
      }
      pagination.update();
    }
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    questTabs.render(screen, gl);
    for (QuestListItem questListItem: activePages.get(currentPageIndex)) {
      questListItem.render(screen, gl);
    }
    pagination.render(screen, gl);
    if (isShowingQuestDetails) {
      questDetailModal.render(screen, gl);
    }
  }

}
