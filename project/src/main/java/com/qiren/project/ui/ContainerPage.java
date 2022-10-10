package com.qiren.project.ui;

import com.qiren.project.exceptions.PageNotFoundException;
import com.qiren.project.services.LoginService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageBuilder;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.ui.interfaces.PageEventListener;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.DateManager;
import com.qiren.project.util.ExceptionCenter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Very Important Page.
 * This is the main page of JFrame and the container of all pages.
 * Responsible for the jump between all inner pages.
 */
public class ContainerPage extends AbstractPage {
    private JPanel containerPage;
    private JPanel mainFrame;
    private JPanel oprationPanel;
    private JLabel timerLabel;

    private String currentPage;

    @Override
    public JPanel getPanel() {
        return containerPage;
    }

    @Override
    public void pageInit(JFrame frame) {
        frame.setTitle(getTitle());
        PageControlCenter.getInstance().addPageEventListener(new PageEventListener() {
            @Override
            public void onBackToMain(Intend intend) {
                UserService userService = ServiceBuilder.getInstance().build(UserService.class);
                if (null == userService || !userService.isLogin()) {
                    CoreUtils.showErrorDialog(frame, "You must login before go to Main Page!");
                    return;
                }
                changePage(frame, PageRequest.MAIN_PAGE);
                PageControlCenter.getInstance().getPageHistory().clear();
            }

            @Override
            public void onChangePage(Intend intend) {
                String newPage = intend.getTarget();
                String lastPage = intend.getPrevious();
                changePage(frame, newPage, intend.getExtras());
                if (lastPage != null && !lastPage.isBlank()) {
                    PageControlCenter.getInstance().getPageHistory().push(lastPage);
                }
            }

            @Override
            public void onGoBack(Intend intend) {
                Stack<String> pageHistory = PageControlCenter.getInstance().getPageHistory();
                if (pageHistory.empty()) {
                    CoreUtils.showErrorDialog(frame, "Cannot go back because no page left!");
                    return;
                }
                String pageName = PageControlCenter.getInstance().getPageHistory().pop();
                changePage(frame, pageName);
            }

            @Override
            public void onPageRefresh(Intend intend) {
                String pageName = intend.getPrevious();
                changePage(frame, pageName);
            }
        });
        addListeners();
        LoginService loginService = ServiceBuilder.getInstance().build(LoginService.class);
        if (loginService.checkIfHaveLoginSession()) {
            changePage(frame, PageRequest.MAIN_PAGE);
        } else {
            changePage(frame, PageRequest.LOGIN_PAGE);
        }

        SimpleDateFormat sdf = DateManager.getTimeFormatter();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int wd = calendar.get(Calendar.DAY_OF_WEEK);
        String weekDay = DayOfWeek.of(wd).toString().toLowerCase();
        char[] carr = weekDay.toCharArray();
        carr[0] = (char) (carr[0] - 32);
        String s = new String(carr);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            String currentTime = sdf.format(new Date());
            timerLabel.setText(s + ", " + currentTime);
        }, 0, 1, TimeUnit.SECONDS);
    }

    private synchronized void changePage(JFrame frame, String target) {
        // currentPage = target;
        changePage(frame, target, null);
    }

    private synchronized void changePage(JFrame frame, String target, Map<String, String> extra) {
        PageControlCenter.getInstance().setCurrentPage(target);
        try {
            AbstractPage page = PageBuilder.createPage(target);
            if (null != extra) {
                page.pageInit(frame, extra);
            } else {
                page.pageInit(frame);
            }
            mainFrame.removeAll();
            mainFrame.revalidate();
            mainFrame.add(page.getPanel());
            frame.setTitle(page.getTitle());
        } catch (PageNotFoundException e) {
            ExceptionCenter.handleException(e);
        }
    }

    private void addListeners() {
//        mainButton.addActionListener(e ->
//                PageControlCenter.getInstance().sendPageRequest(new Intend(PageRequest.PAGE_MAIN_REQUEST, currentPage)));
//        refreshButton.addActionListener(e ->
//                PageControlCenter.getInstance().sendPageRequest(new Intend(PageRequest.PAGE_REFRESH_REQUEST, currentPage)));
//        backButton.addActionListener(e ->
//                PageControlCenter.getInstance().sendPageRequest(new Intend(PageRequest.PAGE_BACK_REQUEST, currentPage)));
    }

    @Override
    public String getTitle() {
        return "Main Page";
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        containerPage = new JPanel();
        containerPage.setLayout(new BorderLayout(0, 0));
        containerPage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, containerPage.getFont()), null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        containerPage.add(panel1, BorderLayout.CENTER);
        mainFrame = new JPanel();
        mainFrame.setLayout(new CardLayout(0, 0));
        panel1.add(mainFrame, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        oprationPanel = new JPanel();
        oprationPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1, -1));
        containerPage.add(oprationPanel, BorderLayout.SOUTH);
        oprationPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-3618616)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        timerLabel = new JLabel();
        timerLabel.setText("Label");
        oprationPanel.add(timerLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        oprationPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return containerPage;
    }
}
