package com.qiren.project.ui;

import com.qiren.project.pojo.Course;
import com.qiren.project.pojo.Subject;
import com.qiren.project.services.CoursePlanService;
import com.qiren.project.services.CourseService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CoreUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

/**
 * Add course, View Course and Edit Course Information.
 */
public class AddCoursePage extends AbstractPage {

    private CourseService courseService = ServiceBuilder.getInstance().build(CourseService.class);
    private CoursePlanService coursePlanService = ServiceBuilder.getInstance().build(CoursePlanService.class);
    private UserService userService = ServiceBuilder.getInstance().build(UserService.class);

    private JTextField courseNameField;
    private JTextField crnField;
    private JTextArea descField;
    private JButton addButton;
    private JPanel coursePanel;
    private JComboBox creditsList;
    private JComboBox subjectList;
    private List<Subject> subjects;

    public AddCoursePage() {
        descField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                descField.setText(CoreUtils.stringSeperation(descField.getText()));
            }
        });
        subjects = coursePlanService.getSubjectList();
        subjects.forEach(subject -> {
            subjectList.addItem(subject.getSubjectId());
        });
    }

    @Override
    public void pageInit(JFrame frame) {
        addButton.addActionListener(e -> {
            Object credit = creditsList.getSelectedItem();
            Object subject = subjectList.getSelectedItem();
            boolean res = courseService.addCourse(
                    frame, crnField.getText(), courseNameField.getText(),
                    null == credit ? "" : credit.toString(),
                    descField.getText(),
                    null == subject ? "" : subject.toString()
            );
            if (res) {
                CoreUtils.showInfoDialog(frame, "Add Course Success!");
                PageControlCenter.getInstance().sendPageRequest(
                        new Intend(PageRequest.PAGE_BACK_REQUEST, PageRequest.ADD_COURSE_PAGE)
                );
            }
        });
    }

    @Override
    public void pageInit(JFrame frame, Map<String, String> extras) {
        super.pageInit(frame, extras);
        frame.setTitle("View Course Details");
        String crn = extras.get("crn");
        Course course = courseService.getCourseByCrn(crn);
        if (null == course) {
            return;
        }
        crnField.setText(course.getRegistrationNumber());
        crnField.setEnabled(false);
        int index = 0;
        for (; index < subjects.size(); index++) {
            if (course.getFkSubject().equals(subjects.get(index).getSubjectId())) {
                break;
            }
        }
        subjectList.setSelectedIndex(index);
        // subjectList.setEnabled(false);
        creditsList.setSelectedIndex(course.getCretdits() - 1);
        descField.setLineWrap(false);
        descField.setText(CoreUtils.stringSeperation(course.getDescription()));
        courseNameField.setText(course.getCourseName());
        if (userService.isStaff()) {
            addButton.setText("Save");
            addButton.addActionListener(e -> {
                course.setCourseName(courseNameField.getText());
                course.setDescription(descField.getText().replaceAll("\\n", ""));
                course.setCretdits(creditsList.getSelectedIndex() + 1);
                course.setFkSubject(subjectList.getSelectedItem().toString());
                boolean res = courseService.updateCourseInfo(frame, course);
                if (res) {
                    CoreUtils.showInfoDialog(frame, "Edit Course Success!");
                    PageControlCenter.getInstance().sendPageRequest(
                            new Intend(PageRequest.PAGE_BACK_REQUEST, PageRequest.ADD_COURSE_PAGE)
                    );
                }
            });
        } else {
            courseNameField.setEnabled(false);
            descField.setEnabled(false);
            subjectList.setEnabled(false);
            creditsList.setEnabled(false);
            addButton.setText("Back");
            addButton.addActionListener(e ->
                    PageControlCenter.getInstance().sendPageRequest(
                            new Intend(PageRequest.PAGE_BACK_REQUEST, PageRequest.ADD_COURSE_PAGE)
                    ));
        }
    }

    @Override
    public String getTitle() {
        return "Add Course Page";
    }

    @Override
    public JPanel getPanel() {
        return coursePanel;
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
        coursePanel = new JPanel();
        coursePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        coursePanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Course Name:");
        coursePanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseNameField = new JTextField();
        courseNameField.setText("");
        coursePanel.add(courseNameField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("CRN:");
        coursePanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        crnField = new JTextField();
        coursePanel.add(crnField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Credits:");
        coursePanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Description:");
        coursePanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descField = new JTextArea();
        descField.setLineWrap(false);
        coursePanel.add(descField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Subject: ");
        coursePanel.add(label5, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        coursePanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add Course");
        coursePanel.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        creditsList = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1");
        defaultComboBoxModel1.addElement("2");
        defaultComboBoxModel1.addElement("3");
        defaultComboBoxModel1.addElement("4");
        creditsList.setModel(defaultComboBoxModel1);
        coursePanel.add(creditsList, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        subjectList = new JComboBox();
        coursePanel.add(subjectList, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return coursePanel;
    }
}
