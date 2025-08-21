package gui;

import model.Email;
import services.EmailService;
import framework.SingletonSessionFactory;

import javax.swing.*;
import java.awt.*;

public class ForwardForm extends BaseFrame {
    private final Email email = new Email();

    public ForwardForm(String originalCode) {
        super("Forward Email", 700, 450);

        JPanel sidebar = createSidebar();
        JButton dashboardBtn = createSidebarButton("Dashboard", 50);
        sidebar.add(dashboardBtn);

        JButton logoutBtn = createLogoutButton();
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        JLabel titleLabel = new JLabel("Forward Email", JLabel.LEFT);
        titleLabel.setBounds(220, 20, 400, 30);
        titleLabel.setFont(titleFont);

        JLabel toLabel = new JLabel("To (comma separated):");
        toLabel.setBounds(220, 60, 200, 25);
        toLabel.setFont(mainFont);

        JTextField toField = new JTextField();
        toField.setBounds(220, 90, 420, 30);

        JButton sendBtn = createPrimaryButton("Forward");
        sendBtn.setBounds(220, 130, 100, 35);

        JButton backBtn = createSecondaryButton("Back");
        backBtn.setBounds(330, 130, 100, 35);

        sendBtn.addActionListener(e -> {
            String[] recipients = toField.getText().trim().split(",");
            if (recipients.length == 0) {
                JOptionPane.showMessageDialog(this, "At least one recipient is required.");
                return;
            }
            try {
                String newCode = email.getCode();
                JOptionPane.showMessageDialog(this, "Email forwarded! Code: " + newCode);
                dispose();
                new DashboardForm();
            } catch (SecurityException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new DashboardForm();
        });

        dashboardBtn.addActionListener(e -> {
            dispose();
            new DashboardForm();
        });

        add(sidebar);
        add(logoutBtn);
        add(titleLabel);
        add(toLabel);
        add(toField);
        add(sendBtn);
        add(backBtn);
        setVisible(true);
    }
}