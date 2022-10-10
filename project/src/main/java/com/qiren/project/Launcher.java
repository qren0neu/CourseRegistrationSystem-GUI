package com.qiren.project;

import com.qiren.project.ui.ContainerPage;
import com.qiren.project.ui.MainMenu;
import com.qiren.project.util.CoreUtils;

import javax.swing.*;

public class Launcher {

    private JFrame mainFrame;

    public static void main(String[] args) {
        CoreUtils.initDatabaseProperties();
        Launcher launcher = new Launcher();
        launcher.initUI();
    }

    private void initUI() {
        mainFrame = new JFrame("MainPage");

        JFrame frame = mainFrame;
        frame.setSize(650, 500);
        frame.setLocation(300, 150);

        ContainerPage containerPage = new ContainerPage();
        containerPage.pageInit(frame);

        frame.setJMenuBar(new MainMenu(frame));
        frame.setContentPane(containerPage.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
