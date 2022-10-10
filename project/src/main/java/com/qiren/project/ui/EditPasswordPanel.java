package com.qiren.project.ui;

import com.qiren.project.services.ServiceBuilder;
import com.qiren.project.services.UserService;
import com.qiren.project.util.CoreUtils;

import javax.swing.*;
import java.awt.*;

public class EditPasswordPanel extends JPanel {

    public EditPasswordPanel(JFrame frame) {
        JPasswordField originPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton saveButton = new JButton("Save Password");

        setLayout(new GridLayout(4, 2));
        add(new JLabel("Please Enter Original Password: "));
        add(originPasswordField);
        add(new JLabel("Please Enter New Password: "));
        add(newPasswordField);
        add(new JLabel("Please Confirm New Password: "));
        add(confirmPasswordField);
        add(new JPanel());
        add(saveButton);

        saveButton.addActionListener(e -> {
            String oldPassword = new String(originPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confrimPassword = new String(confirmPasswordField.getPassword());

            UserService userService = ServiceBuilder.getInstance().build(UserService.class);
            boolean res = userService.changeUserPassword(frame, oldPassword, newPassword, confrimPassword);
            if (res) {
                CoreUtils.showInfoDialog(frame, "Successfully changed password!");
            }
        });
    }
}
