package com.qiren.project.ui;

import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CoreUtils;

import javax.swing.*;

public class MainMenu extends JMenuBar {

    UserService userService = ServiceBuilder.getInstance().build(UserService.class);

    public MainMenu(JFrame frame) {
        init(frame);
    }

    private void init(JFrame frame) {
        JMenu opMenu = new JMenu("Operation");
        JMenu accountMenu = new JMenu("Account");
        JMenu messageMenu = new JMenu("Message");
        JMenu aboutMenu = new JMenu("About");
        // create items
        JMenuItem backMenuItem = new JMenuItem(
                "Back", CoreUtils.createImageIcon(this, "back.jpg"));
        JMenuItem mainMenuItem = new JMenuItem(
                "Main", CoreUtils.createImageIcon(this, "main.jpg"));
        JMenuItem refreshMenuItem = new JMenuItem(
                "Refresh", CoreUtils.createImageIcon(this, "refresh.jpg"));
        JMenuItem closeMenuItem = new JMenuItem(
                "Close", CoreUtils.createImageIcon(this, "close.jpg"));

        JMenuItem logoutItem = new JMenuItem(
                "Log Out", CoreUtils.createImageIcon(this, "logout.jpg"));
        JMenuItem accountItem = new JMenuItem(
                "Account", CoreUtils.createImageIcon(this, "account.jpg"));

        JMenuItem aboutItem = new JMenuItem(
                "About", CoreUtils.createImageIcon(this, "about.jpg"));

        JMenuItem msgItem = new JMenuItem(
                "View Message", CoreUtils.createImageIcon(this, "message.png"));
        // add listeners to operation menu item
        backMenuItem.addActionListener(e ->
                PageControlCenter.getInstance().sendPageRequest(
                        new Intend(PageRequest.PAGE_BACK_REQUEST, PageControlCenter.getInstance().getCurrentPage())));
        mainMenuItem.addActionListener(e ->
                PageControlCenter.getInstance().sendPageRequest(
                        new Intend(PageRequest.PAGE_MAIN_REQUEST, PageControlCenter.getInstance().getCurrentPage())));
        refreshMenuItem.addActionListener(e ->
                PageControlCenter.getInstance().sendPageRequest(
                        new Intend(PageRequest.PAGE_REFRESH_REQUEST, PageControlCenter.getInstance().getCurrentPage())));
        closeMenuItem.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(frame, "Are you sure to exit the program?");
            if (res == 0) {
                System.exit(0);
            }
        });
        // add listener to account menu
        accountItem.addActionListener(e -> {
            // jump to account page
            if (!userService.isLogin()) {
                CoreUtils.showErrorDialog(frame, "You cannot see account info without login!");
                return;
            }
            PageControlCenter.getInstance().sendPageRequest(
                    new Intend(PageRequest.PAGE_CHANGE_REQUEST,
                            PageControlCenter.getInstance().getCurrentPage(), PageRequest.ACCOUNT_PAGE)
            );
        });
        logoutItem.addActionListener(e -> {
            // jump to log in page
            if (!userService.isLogin()) {
                CoreUtils.showErrorDialog(frame, "You cannot logout before login!");
                return;
            }
            PageControlCenter.getInstance().sendPageRequest(
                    new Intend(PageRequest.PAGE_CHANGE_REQUEST,
                            PageControlCenter.getInstance().getCurrentPage(), PageRequest.LOGIN_PAGE)
            );
            userService.clearLoginInfo();
            PageControlCenter.getInstance().getPageHistory().clear();
        });
        msgItem.addActionListener(e -> {
            if (!userService.isLogin()) {
                CoreUtils.showErrorDialog(frame, "You cannot check message before login!");
                return;
            }
            PageControlCenter.getInstance().sendPageRequest(
                    new Intend(PageRequest.PAGE_CHANGE_REQUEST,
                            PageControlCenter.getInstance().getCurrentPage(), PageRequest.VIEW_MESSAGE_PAGE)
            );
        });
        // add listener to about menu
        aboutItem.addActionListener(e -> {
            String s = "<html>" +
                    "<h2 align=\"center\">Course Registration System</h2>" +
                    "<p>Author:&emsp;&nbsp;Qi Ren</p>" +
                    "<p>Version:&emsp;1.0.0</p>" +
                    "</html>";
            JLabel label = new JLabel(s);
            JOptionPane.showMessageDialog(
                    frame, label, "About", JOptionPane.INFORMATION_MESSAGE);
        });
        // add item to menu
        opMenu.add(backMenuItem);
        opMenu.add(mainMenuItem);
        opMenu.add(refreshMenuItem);
        opMenu.add(closeMenuItem);

        accountMenu.add(accountItem);
        accountMenu.add(logoutItem);

        aboutMenu.add(aboutItem);

        messageMenu.add(msgItem);

        add(opMenu);
        add(accountMenu);
        add(messageMenu);
        add(aboutMenu);
    }
}
