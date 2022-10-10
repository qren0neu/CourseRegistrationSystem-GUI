package com.qiren.project.ui.core;

import com.qiren.project.ui.interfaces.PageEventListener;
import com.qiren.project.util.LoggingCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Communication Center Between All Pages.
 *
 * @see PageRequest
 * @see PageBuilder
 */
public class PageControlCenter {

    private List<PageEventListener> pageEventListeners;
    private Stack<String> pageHistory;

    private String currentPage;

    private PageControlCenter() {
        pageEventListeners = new ArrayList<>();
        pageHistory = new Stack<>();
    }

    public void addPageEventListener(PageEventListener listener) {
        pageEventListeners.add(listener);
    }

    /**
     * Send Page Event Request.
     *
     * @param intend see {@link Intend}
     */
    public void sendPageRequest(Intend intend) {
        if (null == intend) {
            return;
        }
        String request = intend.getPageRequest();
        String target = intend.getTarget();
        String previousPage = intend.getPrevious();
        LoggingCenter.info("Received Page Request: " + request + " from page: " + previousPage);
        switch (request) {
            case PageRequest.PAGE_CHANGE_REQUEST -> onPageChange(target, previousPage, intend);
            case PageRequest.PAGE_MAIN_REQUEST -> onBackMain(previousPage, intend);
            case PageRequest.PAGE_REFRESH_REQUEST -> onRefreshPage(previousPage, intend);
            case PageRequest.PAGE_BACK_REQUEST -> onPageBack(previousPage, intend);
        }
    }

    public Stack<String> getPageHistory() {
        return pageHistory;
    }

    private void onPageChange(String request, String previousPage, Intend intend) {
        for (PageEventListener listener : pageEventListeners) {
            listener.onChangePage(intend);
        }
        LoggingCenter.info("Page changed from " + previousPage + " to " + request);
    }

    private void onPageBack(String pageName, Intend intend) {
        for (PageEventListener listener : pageEventListeners) {
            listener.onGoBack(intend);
        }
        LoggingCenter.info("Page backed from " + pageName);
    }

    private void onRefreshPage(String pageName, Intend intend) {
        for (PageEventListener listener : pageEventListeners) {
            listener.onPageRefresh(intend);
        }
        LoggingCenter.info("Page refreshed: " + pageName);
    }

    private void onBackMain(String pageName, Intend intend) {
        for (PageEventListener listener : pageEventListeners) {
            listener.onBackToMain(intend);
        }
        LoggingCenter.info("Page backed from " + pageName + " to Main Page.");
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public static PageControlCenter getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        static final PageControlCenter instance = new PageControlCenter();
    }
}
