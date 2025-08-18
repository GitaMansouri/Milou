package gui;

import model.Email;
import services.EmailService;
import framework.SingletonSessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardForm extends BaseFrame {
    private final Email email = new Email();

    public DashboardForm() {
        super("Dashboard", 1000, 600);
        initializeUI();
    }

    private void initializeUI() {
        JPanel sidebar = createSidebar();
        JButton composeBtn = createSidebarButton("Compose", 50);
        JButton inboxBtn = createSidebarButton("Inbox", 100);
        JButton sentBtn = createSidebarButton("Sent", 150);
        JButton readCodeBtn = createSidebarButton("Read by Code", 200);

        sidebar.add(composeBtn);
        sidebar.add(inboxBtn);
        sidebar.add(sentBtn);
        sidebar.add(readCodeBtn);

        JButton logoutBtn = createLogoutButton();
        logoutBtn.addActionListener(e -> performLogout());

        String[] columns = {"Code", "From", "To", "Subject", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable emailTable = new JTable(tableModel);
        emailTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(emailTable);
        scrollPane.setBounds(220, 60, 750, 450);

        loadReceivedEmails(tableModel);

        composeBtn.addActionListener(e -> openEmailComposer());
        inboxBtn.addActionListener(e -> loadReceivedEmails(tableModel));
        sentBtn.addActionListener(e -> loadSentEmails(tableModel));
        readCodeBtn.addActionListener(e -> openEmailReader());

        add(sidebar);
        add(logoutBtn);
        add(scrollPane);
    }

    private void loadReceivedEmails(DefaultTableModel model) {
        model.setRowCount(0);
        String currentEmail = SingletonSessionManager.getInstance().getLoggedInEmail(); // تغییر به SingletonSessionManager
        List<Email> emails = EmailService.getAllReceivedEmails(currentEmail);

        for (Email email : emails) {
            model.addRow(new Object[]{
                    email.getCode(),
                    email.getSenderEmail().getEmail(),
                    email.getRecipientEmail().getEmail(),
                    email.getSubject(),
                    email.getDate()
            });
        }
    }

    private void loadSentEmails(DefaultTableModel model) {
        model.setRowCount(0);
        String currentEmail = SingletonSessionManager.getInstance().getLoggedInEmail(); // تغییر به SingletonSessionManager
        List<Email> emails = EmailService.getSentEmails(currentEmail);

        for (Email email : emails) {
            model.addRow(new Object[]{
                    email.getCode(),
                    email.getSenderEmail().getEmail(),
                    email.getRecipientEmail().getEmail(),
                    email.getSubject(),
                    email.getDate()
            });
        }
    }

    private void openEmailComposer() {
        dispose();
        new SendEmailForm();
    }

    private void openEmailReader() {
        dispose();
        new ReadByCodeForm();
    }

    private void performLogout() {
        SingletonSessionManager.getInstance().logout();
        dispose();
        new LoginForm();
    }
}