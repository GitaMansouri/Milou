package gui;

import model.Email;
import services.EmailService;
import framework.SingletonSessionFactory;

import javax.swing.*;

public class ReadByCodeForm extends BaseFrame {
    private final Email email = new Email();

    public ReadByCodeForm() {
        super("Read Email by Code", 700, 400);

        JPanel sidebar = createSidebar();
        JButton dashboardBtn = createSidebarButton("Dashboard", 50);
        sidebar.add(dashboardBtn);

        JButton logoutBtn = createLogoutButton();
        logoutBtn.addActionListener(e -> {
            SingletonSessionFactory.logout();
            dispose();
            new LoginForm();
        });

        JLabel titleLabel = new JLabel("Read Email by Code", JLabel.LEFT);
        titleLabel.setBounds(220, 20, 400, 30);
        titleLabel.setFont(titleFont);

        JLabel codeLabel = new JLabel("Email Code:");
        codeLabel.setBounds(220, 60, 100, 25);
        codeLabel.setFont(mainFont);

        JTextField codeField = new JTextField();
        codeField.setBounds(320, 60, 200, 30);

        JButton readBtn = createPrimaryButton("Read");
        readBtn.setBounds(540, 60, 100, 30);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(mainFont);
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(220, 100, 420, 200);

        readBtn.addActionListener(e -> {
            String code = codeField.getText().trim();
            Email email = email.getEmailByCodeIfAuthorized(code, SingletonSessionFactory.getLoggedInEmail());
            if (email != null) {
                resultArea.setText("From: " + email.getSenderEmail() + "\nTo: " + email.getRecipient() +
                        "\nSubject: " + email.getSubject() + "\n\n" + email.getBody());
            } else {
                JOptionPane.showMessageDialog(this, "Email not found or access denied.");
            }
        });

        dashboardBtn.addActionListener(e -> {
            dispose();
            new DashboardForm();
        });

        add(sidebar);
        add(logoutBtn);
        add(titleLabel);
        add(codeLabel);
        add(codeField);
        add(readBtn);
        add(scroll);
        setVisible(true);
    }
}