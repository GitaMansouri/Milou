package gui;

import model.Email;
import services.EmailService;
import framework.SingletonSessionFactory;

import javax.swing.*;
import java.awt.*;

import static services.EmailService.generateRandomCode;

public class ReplyForm extends BaseFrame {
    private final Email email = new Email();

    public ReplyForm(String originalCode) {
        super("Reply to Email", 700, 400);

        JPanel sidebar = createSidebar();
        JButton dashboardBtn = createSidebarButton("Dashboard", 50);
        sidebar.add(dashboardBtn);

        JButton logoutBtn = createLogoutButton();
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        JLabel titleLabel = new JLabel("Reply to Email", JLabel.LEFT);
        titleLabel.setBounds(220, 20, 400, 30);
        titleLabel.setFont(titleFont);

        JLabel bodyLabel = new JLabel("Reply Message:");
        bodyLabel.setBounds(220, 60, 150, 25);
        bodyLabel.setFont(mainFont);

        JTextArea bodyArea = new JTextArea();
        bodyArea.setFont(mainFont);
        JScrollPane scrollPane = new JScrollPane(bodyArea);
        scrollPane.setBounds(220, 90, 420, 200);

        JButton sendBtn = createPrimaryButton("Send Reply");
        sendBtn.setBounds(220, 310, 120, 35);

        JButton backBtn = createSecondaryButton("Back");
        backBtn.setBounds(350, 310, 100, 35);

        sendBtn.addActionListener(e -> {
            String body = bodyArea.getText().trim();
            if (body.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Message cannot be empty.");
                return;
            }
            try {
                String newCode = generateRandomCode();
                JOptionPane.showMessageDialog(this, "Reply sent! Code: " + newCode);
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
        add(bodyLabel);
        add(scrollPane);
        add(sendBtn);
        add(backBtn);
        setVisible(true);
    }
}