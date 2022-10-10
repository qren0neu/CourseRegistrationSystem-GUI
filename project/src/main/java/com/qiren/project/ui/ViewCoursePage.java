package com.qiren.project.ui;

import com.qiren.project.pojo.Course;
import com.qiren.project.pojo.CoursePlan;
import com.qiren.project.pojo.Semester;
import com.qiren.project.pojo.Subject;
import com.qiren.project.services.CoursePlanService;
import com.qiren.project.services.CourseService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CourseConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewCoursePage extends AbstractPage {
    private JPanel viewCoursePanel;
    private JComboBox filterBox;
    private JPanel courseListPanel;
    private JScrollPane scrollPane;

    private CourseService courseService = ServiceBuilder.getInstance().build(CourseService.class);
    private CoursePlanService coursePlanService =
            ServiceBuilder.getInstance().build(CoursePlanService.class);
    private List<Course> courseList;

    private JFrame frame;

    @Override
    public void pageInit(JFrame frame) {
        this.frame = frame;
        // courseListPanel.setLayout(new GridLayout(0, 1, 20, 20));]
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        List<Subject> subjects = coursePlanService.getSubjectList();
        for (Subject subject : subjects) {
            filterBox.addItem(subject.getSubjectId());
        }
        filterBox.addItem("None");
        filterBox.addActionListener(e -> {
            int index = filterBox.getSelectedIndex();
            if (index == 0 || index >= subjects.size()) {
                courseList = courseService.getAllCourses();
                pageRefresh();
                return;
            }
            courseList = courseService.getCoursesBySubject(subjects.get(index - 1).getSubjectId());
            pageRefresh();
        });
        courseList = courseService.getAllCourses();
        pageRefresh();
    }

    private void pageRefresh() {
        if (null != courseList) {
            courseListPanel.removeAll();
            for (Course course : courseList) {
                if (course.getCretdits() < 0) {
                    // consider closed
                    continue;
                }
                JPanel listItem = new JPanel();
                listItem.setLayout(new BoxLayout(listItem, BoxLayout.X_AXIS));
                listItem.setPreferredSize(new Dimension(frame.getWidth(), 100));
                String undone = "<html>" +
                        "<b>CRN:&nbsp;</b>" + course.getRegistrationNumber() + "<br>" +
                        "<b>Name:&nbsp;</b>" + course.getCourseName() + "<br>" +
                        "<b>Subject:&nbsp;</b>" + course.getFkSubject() + "<br>" +
                        "<b>Credits:&nbsp;</b>" + course.getCretdits() + "<br>" +
                        "</html>";
                JLabel label = new JLabel(undone);
                // label.setPreferredSize(new Dimension(300, 200));
                listItem.add(label);
                JButton button = new JButton(" View Detail  ");
                button.addActionListener(e -> {
                    Intend intend = new Intend(
                            PageRequest.PAGE_CHANGE_REQUEST,
                            PageRequest.VIEW_COURSE_PAGE,
                            PageRequest.ADD_COURSE_PAGE
                    );
                    intend.addExtra("crn", course.getRegistrationNumber());
                    PageControlCenter.getInstance().sendPageRequest(intend);
                });
                button.setPreferredSize(new Dimension(200, 50));
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                buttonPanel.add(button);
                UserService userService = ServiceBuilder.getInstance().build(UserService.class);
                if (userService.isStaff()) {
                    JButton deleteButton = new JButton("Close Course");
                    deleteButton.addActionListener(e -> {
                        int res = JOptionPane.showConfirmDialog(
                                frame, "Are you sure to delete the Course?\nAll Related Plans could be affected."
                        );
                        if (res != 0) {
                            return;
                        }
                        course.setCretdits(-1);
                        CoursePlanService coursePlanService =
                                ServiceBuilder.getInstance().build(CoursePlanService.class);
                        courseService.updateCourseInfo(frame, course);
                        coursePlanService.closeAllPlanOfCourse(
                                course.getRegistrationNumber() + " " + course.getCourseName());
                    });
                    buttonPanel.add(deleteButton);
                }

                listItem.add(buttonPanel);
                courseListPanel.add(listItem);
                courseListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            courseListPanel.revalidate();
            courseListPanel.repaint();
        }
    }

    @Override
    public String getTitle() {
        return "View Course List";
    }

    @Override
    public JPanel getPanel() {
        return viewCoursePanel;
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
        viewCoursePanel = new JPanel();
        viewCoursePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        viewCoursePanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        filterBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Filter With Subject:");
        filterBox.setModel(defaultComboBoxModel1);
        panel1.add(filterBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Course Information");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPane = new JScrollPane();
        viewCoursePanel.add(scrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        courseListPanel = new JPanel();
        courseListPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane.setViewportView(courseListPanel);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return viewCoursePanel;
    }
}
