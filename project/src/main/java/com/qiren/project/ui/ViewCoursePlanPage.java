package com.qiren.project.ui;

import com.qiren.project.pojo.CoursePlan;
import com.qiren.project.services.CoursePlanService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.util.LoggingCenter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewCoursePlanPage extends AbstractPage {
    private JPanel planPanel;
    private JButton addFilterButton;
    private JScrollPane planListPane;

    private CoursePlanService coursePlanService =
            ServiceBuilder.getInstance().build(CoursePlanService.class);

    private List<CoursePlan> coursePlanList;
    private JFrame frame;

    @Override
    public void pageInit(JFrame frame) {
        this.frame = frame;
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        addFilterButton.addActionListener(e -> {
            FilterPlanPage filterPlanPage = new FilterPlanPage(frame);
            JOptionPane.showMessageDialog(frame, filterPlanPage.getMainPanel());
            String searchStatement = filterPlanPage.getSearchStatement();
            coursePlanList = coursePlanService.getFilteredCoursePlan(searchStatement);
            listPanel.removeAll();
            refresh(listPanel);
            listPanel.revalidate();
            listPanel.repaint();
            LoggingCenter.info(searchStatement);
        });
        coursePlanList = coursePlanService.getAllCoursePlan();
        refresh(listPanel);
        planListPane.setViewportView(listPanel);
    }

    private void refresh(JPanel listPanel) {
        for (CoursePlan coursePlan : coursePlanList) {
//            if (!coursePlanService.checkPlanAvailable(coursePlan.getFkSem())) {
//                continue;
//            }
            listPanel.add(new PlanListItem(frame, coursePlan).getMainPanel());
            listPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
    }

    private void makeCourseList() {

    }

    @Override
    public String getTitle() {
        return "View Plans";
    }

    @Override
    public JPanel getPanel() {
        return planPanel;
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
        planPanel = new JPanel();
        planPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        planPanel.add(panel1, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setText("Add Filter: ");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addFilterButton = new JButton();
        addFilterButton.setText("Add Filter");
        panel1.add(addFilterButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        planListPane = new JScrollPane();
        planPanel.add(planListPane, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return planPanel;
    }
}