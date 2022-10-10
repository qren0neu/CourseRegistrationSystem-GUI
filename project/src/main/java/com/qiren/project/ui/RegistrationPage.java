package com.qiren.project.ui;

import com.qiren.project.pojo.RegistrationPlan;
import com.qiren.project.services.RegistrationService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import com.qiren.project.ui.core.Intend;
import com.qiren.project.ui.core.PageControlCenter;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CoreUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrationPage extends AbstractPage {
    private JPanel mainPanel;
    private JPanel innerPanel;
    private JScrollPane scrollPane;

    private RegistrationService registrationService =
            ServiceBuilder.getInstance().build(RegistrationService.class);
    private UserService userService =
            ServiceBuilder.getInstance().build(UserService.class);

    private List<RegistrationPlan> registrationPlans;
    private JFrame frame;
    private boolean isViewStudentInfo;

    @Override
    public void pageInit(JFrame frame) {
        this.frame = frame;
        registrationPlans = registrationService.getRegistration();
        initImpl();
    }

    @Override
    public void pageInit(JFrame frame, Map<String, String> extras) {
        super.pageInit(frame, extras);
        String courseId = extras.get("course");
        registrationPlans = registrationService.getRegistrationForCourse(courseId);
        isViewStudentInfo = true;
        initImpl();
    }

    private void initImpl() {
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        if (null == registrationPlans || registrationPlans.isEmpty()) {
            CoreUtils.showErrorDialog(frame, "There is no registration now!");
            return;
        }
        // add list to scroll view
        scrollPane.setViewportView(listContainer);
        for (RegistrationPlan registrationPlan : registrationPlans) {
            // add item
            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));
//            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            JLabel infoLabel = new JLabel();
            // add label
            infoLabel.setText(getLabelInfo(registrationPlan));
            listPanel.add(infoLabel);
            listPanel.add(buttonPanel);
            // button panel add button
            addButton(buttonPanel, registrationPlan);
            listContainer.add(listPanel);
            listContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private String getLabelInfo(RegistrationPlan registrationPlan) {
        String base = "";
        String person = "";
        if (userService.isStaff() && !isViewStudentInfo) {
            base = """
                    <html>
                    <b>Course Name:&nbsp;</b>%replace1%<br>
                    <b>Lecturer:&nbsp;</b>%replace2%<br>
                    <b>Semester:&nbsp;</b>%replace3%<br>
                    </html>
                    """;
            person = registrationPlan.getFkStaff();
        } else {
            base = """
                    <html>
                    <b>Course Name:&nbsp;</b>%replace1%<br>
                    <b>Student:&nbsp;</b>%replace2%<br>
                    <b>Semester:&nbsp;</b>%replace3%<br>
                    </html>
                    """;
            person = registrationPlan.getFkStudent();
        }
        return CoreUtils.customFormat(base,
                registrationPlan.getFkCourse(), person, registrationPlan.getFkSem());
    }

    private void addButton(JPanel buttonPanel, RegistrationPlan registrationPlan) {
        JButton button1;
        JButton button2;
        if (userService.isStaff()) {
            button1 = new JButton("Assign Grade");
            button2 = new JButton("Send Message");

            button1.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        frame,
                        new AssignGradePage(frame, registrationPlan).getMainPanel(),
                        "Assign Grade",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
            button2.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        frame,
                        new MessageBox(frame,
                                registrationPlan.getFkStudent(), PageRequest.REGIS_PLAN_PAGE).getMainPanel(),
                        "Leave a Message",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
        } else {
            button1 = new JButton("Drop Course");
            button1.addActionListener(e -> {
                int res = JOptionPane.showConfirmDialog(frame, "Are you sure to drop this course?");
                if (res == 0) {
                    registrationService.dropClass(registrationPlan.getRegisId());
                    PageControlCenter.getInstance().sendPageRequest(new Intend(PageRequest.PAGE_REFRESH_REQUEST, PageRequest.REGIS_PLAN_PAGE));
                }
            });
            button2 = new JButton("View Schedule");
            button2.addActionListener(e -> {
                // go to schedule page
                // go to syllabus page
                Intend intend = new Intend(
                        PageRequest.PAGE_CHANGE_REQUEST, PageRequest.REGIS_PLAN_PAGE, PageRequest.VIEW_SYLLABUS_PAGE);
                intend.addExtra("course", registrationPlan.getPlanId() + "");
                intend.addExtra("semester", registrationPlan.getFkSem());
                PageControlCenter.getInstance().sendPageRequest(intend);
            });
        }
        buttonPanel.add(button1);
        buttonPanel.add(button2);
    }

    @Override
    public String getTitle() {
        return "View Registration List";
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(innerPanel, BorderLayout.CENTER);
        scrollPane = new JScrollPane();
        innerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
