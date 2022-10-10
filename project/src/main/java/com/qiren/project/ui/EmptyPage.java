package com.qiren.project.ui;

import com.qiren.project.ui.core.PageRequest;

import javax.swing.*;

public class EmptyPage extends AbstractPage{
    @Override
    public void pageInit(JFrame frame) {

    }

    @Override
    public String getTitle() {
        return "Blank Page";
    }

    @Override
    public JPanel getPanel() {
        return new JPanel();
    }
}
