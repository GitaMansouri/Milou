package gui;

import model.Email;
import services.EmailService;
import framework.SingletonSessionFactory;

import javax.swing.*;
import java.awt.*;

import static services.EmailService.generateRandomCode;

public class SendEmailForm extends BaseFrame {
    private final Email email = new Email();

    public SendEmailForm() {
        super("Compose Email", 800, 500);

        JPanel sidebar = createSidebar();
        JButton dashboardBtn = createSidebarButton("Dashboard", 50);
        sidebar.add(dashboardBtn);

        JButton logoutBtn = createLogoutButton();
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        JLabel titleLabel = new JLabel("Compose New Email", JLabel.LEFT);
        titleLabel.setBounds(220, 20, 400, 30);
        titleLabel.setFont(titleFont);

        JLabel toLabel = new JLabel("To (comma separated):");
        toLabel.setBounds(220, 60, 200, 25);
        toLabel.setFont(mainFont);

        JTextField toField = new JTextField();
        toField.setBounds(220, 90, 500, 30);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(220, 130, 100, 25);
        subjectLabel.setFont(mainFont);

        JTextField subjectField = new JTextField();
        subjectField.setBounds(220, 160, 500, 30);

        JLabel bodyLabel = new JLabel("Body:");
        bodyLabel.setBounds(220, 200, 100, 25);
        bodyLabel.setFont(mainFont);

        JTextArea bodyArea = new JTextArea();
        bodyArea.setFont(mainFont);
        JScrollPane bodyScroll = new JScrollPane(bodyArea);
        bodyScroll.setBounds(220, 230, 500, 150);

        JButton sendBtn = createPrimaryButton("Send");
        sendBtn.setBounds(220, 400, 100, 35);

        JButton backBtn = createSecondaryButton("Back");
        backBtn.setBounds(340, 400, 100, 35);

        sendBtn.addActionListener(e -> {
            String[] recipients = toField.getText().trim().split(",");
            String subject = subjectField.getText().trim();
            String body = bodyArea.getText().trim();
            if (recipients.length == 0 || subject.isEmpty() || body.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            String code = generateRandomCode();
            JOptionPane.showMessageDialog(this, "Email sent! Code: " + code);
            dispose();
            new DashboardForm();
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
        add(subjectLabel);
        add(subjectField);
        add(bodyLabel);
        add(bodyScroll);
        add(sendBtn);
        add(backBtn);
        setVisible(true);
    }

}