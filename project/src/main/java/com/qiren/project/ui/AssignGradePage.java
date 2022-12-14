package com.qiren.project.ui;

import com.qiren.project.pojo.RegistrationPlan;
import com.qiren.project.services.MessageService;
import com.qiren.project.services.RegistrationService;
import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.ui.core.PageRequest;
import com.qiren.project.util.CoreUtils;

import javax.swing.*;
import java.awt.*;

public class AssignGradePage {
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField gradeField;
    private JButton confirmButton;

    private JFrame frame;
    private RegistrationPlan registrationPlan;
    private RegistrationService registrationService =
            ServiceBuilder.getInstance().build(RegistrationService.class);
    private MessageService messageService =
            ServiceBuilder.getInstance().build(MessageService.class);

    public AssignGradePage(JFrame frame, RegistrationPlan registrationPlan) {
        this.frame = frame;

        nameLabel.setText(registrationPlan.getFkStudent());
        gradeField.setText(registrationPlan.getGrade());
        confirmButton.addActionListener(e -> {
            String text = gradeField.getText();
            if (!text.matches("[0-9]{1,3}")) {
                CoreUtils.showErrorDialog(frame, "Please enter a number!");
                return;
            }
            if (registrationService.updateGrade(registrationPlan.getRegisId(), text)) {
                CoreUtils.showInfoDialog(frame, "Assign Grade Success!");
                registrationPlan.setGrade(text);
                messageService.sendSystemMessage(
                        frame, "Grade Assignment",
                        "You've assigned a new grade",
                        registrationPlan.getFkStudent(),
                        PageRequest.VIEW_GRADE_PAGE
                );
            }
        });
    }

    public JPanel getMainPanel() {
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
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Student Name:");
        mainPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("Label");
        mainPanel.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Enter Grade:");
        mainPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeField = new JTextField();
        mainPanel.add(gradeField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        mainPanel.add(confirmButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
