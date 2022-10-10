package com.qiren.project.ui;

import com.qiren.project.pojo.Course;
import com.qiren.project.pojo.CoursePlan;
import com.qiren.project.pojo.Semester;
import com.qiren.project.pojo.Subject;
import com.qiren.project.services.CoursePlanService;
import com.qiren.project.services.CourseService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CoreUtils;
import com.qiren.project.util.CourseConstants;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddCoursePlanPage extends AbstractPage {
    private JPanel planPanel;
    private JPanel innerPanel;
    private JComboBox semBox;
    private JComboBox subjBox;
    private JComboBox courseBox;
    private JButton selectButton;
    private JButton confirmButton;
    private JButton deleteButton;

    private CourseService courseService = ServiceBuilder.getInstance().build(CourseService.class);
    private CoursePlanService planService = ServiceBuilder.getInstance().build(CoursePlanService.class);

    private CoursePlan coursePlan = new CoursePlan();

    private List<Course> courses;
    private List<Semester> semesters;
    private List<String> weekDayList = new ArrayList<>();
    private List<String> fromTimeList = new ArrayList<>();
    private List<String> toTimeList = new ArrayList<>();

    @Override
    public void pageInit(JFrame frame) {
        deleteButton.setVisible(false);
        semesters = planService.getSemesterList();
        for (Semester sem : semesters) {
            semBox.addItem(sem.toString());
        }
        List<Subject> subjects = planService.getSubjectList();
        for (Subject subject : subjects) {
            subjBox.addItem(subject.getSubjectId());
        }
        subjBox.addActionListener(e -> {
            int index = subjBox.getSelectedIndex();
            if (index > 0) {
                String subj = subjBox.getSelectedItem().toString();
                courses = courseService.getCoursesBySubject(subj);
                courseBox.removeAllItems();
                courseBox.addItem("None");
                for (Course course : courses) {
                    courseBox.addItem(course.toString());
                }
            }
        });
        courses = courseService.getAllCourses();
        for (Course course : courses) {
            courseBox.addItem(course);
        }
        selectButton.addActionListener(e -> {
            DatePickerPanel datePickerPanel = new DatePickerPanel(frame);
            datePickerPanel.setListener(new DatePickerPanel.OnSaveTimeListener() {
                @Override
                public void onSaveDateTime(String weekDay, LocalTime fromTime, LocalTime toTime) {
                    weekDayList.add(weekDay);
                    fromTimeList.add(fromTime.toString());
                    toTimeList.add(toTime.toString());
                }

                @Override
                public void onReset() {
                    weekDayList.clear();
                    fromTimeList.clear();
                    toTimeList.clear();
                }
            });
            JOptionPane.showMessageDialog(frame,
                    new JScrollPane(datePickerPanel),
                    "Please Pick Weekday and Time", JOptionPane.QUESTION_MESSAGE);
            if (fromTimeList.isEmpty() || toTimeList.isEmpty()) {
                return;
            }
        });
        confirmButton.addActionListener(e -> {
            // get the index to relate to the course, 0 means "None"
            int courseIndex = courseBox.getSelectedIndex();
            int semIndex = semBox.getSelectedIndex();
            boolean res = planService.addNewCoursePlan(
                    frame, courseIndex <= 0 ? null : courses.get(courseIndex - 1),
                    semIndex <= 0 ? null : semesters.get(semIndex - 1), fromTimeList,
                    toTimeList, weekDayList);
            if (res) {
                // success
                CoreUtils.showInfoDialog(frame, "Successfully Add a Plan!");
                PageControlCenter.getInstance().sendPageRequest(
                        new Intend(PageRequest.PAGE_BACK_REQUEST, PageRequest.ADD_PLAN_PAGE)
                );
            }
        });
    }

    @Override
    public String getTitle() {
        return "Add Course Plan";
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
        innerPanel = new JPanel();
        innerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        planPanel.add(innerPanel, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("Select a semester:");
        innerPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("None");
        semBox.setModel(defaultComboBoxModel1);
        innerPanel.add(semBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Select a subject");
        innerPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        subjBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("None");
        subjBox.setModel(defaultComboBoxModel2);
        innerPanel.add(subjBox, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Select a course");
        innerPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("None");
        courseBox.setModel(defaultComboBoxModel3);
        innerPanel.add(courseBox, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Select Weekday and Time");
        innerPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectButton = new JButton();
        selectButton.setText("Select");
        innerPanel.add(selectButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        innerPanel.add(confirmButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        innerPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        innerPanel.add(deleteButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return planPanel;
    }
}
