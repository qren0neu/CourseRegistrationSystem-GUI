package com.qiren.project.ui;

import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;

import javax.swing.*;
import java.util.Map;

/**
 * This is the super class of all pages,
 * If you want to show pages in the Main Frame,
 * you must extend this page.
 */
public abstract class AbstractPage {

    /**
     * This method is called when a Page is open.
     *
     * @param frame The Main Frame that contains all pages.
     */
    public abstract void pageInit(JFrame frame);

    public void pageInit(JFrame frame, Map<String, String> extras) {
        // empty method
    }

    /**
     * Get the title of this page which can show on the frame.
     *
     * @return the title
     */
    public abstract String getTitle();

    /**
     * Get the panel to show on the screen.
     *
     * @return {@link JPanel}
     */
    public abstract JPanel getPanel();

    /**
     * A simple method to trigger change page event.
     *
     * @param currentPage the page that is showing
     * @param targetPage  the page to show next
     */
    protected void sendPageChangeRequest(String currentPage, String targetPage) {
        PageControlCenter.getInstance().sendPageRequest(
                new Intend(PageRequest.PAGE_CHANGE_REQUEST, currentPage, targetPage)
        );
    }
}
