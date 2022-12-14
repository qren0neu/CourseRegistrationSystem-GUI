package com.qiren.project.ui;

import com.qiren.project.pojo.CoursePlan;
import com.qiren.project.pojo.RegistrationPlan;
import com.qiren.project.pojo.Semester;
import com.qiren.project.services.CoursePlanService;
import com.qiren.project.services.RegistrationService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Random;

public class SchedulePage2 extends AbstractPage {
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private RegistrationService registrationService =
            ServiceBuilder.getInstance().build(RegistrationService.class);
    private List<RegistrationPlan> planList;
    private CoursePlanService coursePlanService =
            ServiceBuilder.getInstance().build(CoursePlanService.class);
    private List<CoursePlan> coursePlanList;

    private Semester semester;

    @Override
    public void pageInit(JFrame frame) {
        List<Semester> semesterList = coursePlanService.getSemesterList();
        SemesterDialog dialog = new SemesterDialog(semesterList, frame);
        JOptionPane.showMessageDialog(frame, dialog.getMainPanel());
        semester = dialog.getSelectedSemester();
        String[] columnNames = {" ", "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday"};
        Object[][] data = new Object[17][6];
        UserService userService = ServiceBuilder.getInstance().build(UserService.class);
        if (userService.isStaff()) {
            staffInit(data);
        } else {
            studentInit(data);
        }
        scrollPane.setViewportView(new JTable(data, columnNames));
    }

    private void staffInit(Object[][] data) {
        coursePlanList = coursePlanService.getSelfCoursePlanWithSemester(semester.getSemName());
        for (int i = 0; i < 17; i++) {
            String currentTime = 10 + (i / 2) + ((i % 2 == 0) ? ":00" : ":30");
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    data[i][j] = currentTime;
                    continue;
                }
                data[i][j] = "/";
                for (CoursePlan plan : coursePlanList) {
                    String weekDay = plan.getWeekDay();
                    if (weekDay.equals(DayOfWeek.of(j).toString())) {
                        String fromTime = plan.getFromTime();
                        String toTime = plan.getToTime();
                        if (equalBefore(fromTime, currentTime + "00")
                                && equalAfter(toTime, currentTime + "00")) {
                            String[] split = plan.getFkCourse().split(" ");
                            data[i][j] = split[0] + split[1];
                        }
                    }
                }
            }
        }

    }

    private void studentInit(Object[][] data) {
        planList = registrationService.getRegistrationForSem(semester.getSemName());
        for (int i = 0; i < 17; i++) {
            String currentTime = 10 + (i / 2) + ((i % 2 == 0) ? ":00" : ":30");
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    data[i][j] = currentTime;
                    continue;
                }
                data[i][j] = "/";
                for (RegistrationPlan plan : planList) {
                    String weekDay = plan.getWeekday();
                    if (weekDay.equals(DayOfWeek.of(j).toString())) {
                        String fromTime = plan.getFromTime();
                        String toTime = plan.getToTime();
                        if (equalBefore(fromTime, currentTime + "00")
                                && equalAfter(toTime, currentTime + "00")) {
                            String[] split = plan.getFkCourse().split(" ");
                            data[i][j] = split[0] + split[1];
                        }
                    }
                }
            }
        }
    }

    private boolean equalBefore(String time1, String time2) {
        time1 = time1.replaceAll(":", "");
        time2 = time2.replaceAll(":", "");
        return Integer.parseInt(time1) <= Integer.parseInt(time2);
    }

    private boolean equalAfter(String time1, String time2) {
        time1 = time1.replaceAll(":", "");
        time2 = time2.replaceAll(":", "");
        return Integer.parseInt(time1) >= Integer.parseInt(time2);
    }

    @Override
    public String getTitle() {
        return "Schedule";
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
        scrollPane = new JScrollPane();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
