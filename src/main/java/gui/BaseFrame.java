package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame {
    protected final Color primaryBlue = new Color(0, 102, 204);
    protected final Color white = Color.WHITE;
    protected final Font mainFont = new Font("Tahoma", Font.PLAIN, 14);
    protected final Font titleFont = new Font("Tahoma", Font.BOLD, 16);

    public BaseFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(white);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(primaryBlue);
        sidebar.setBounds(0, 0, 200, getHeight());
        sidebar.setLayout(null);
        return sidebar;
    }

    protected JButton createSidebarButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(20, y, 160, 40);
        btn.setBackground(white);
        btn.setForeground(primaryBlue);
        btn.setFocusPainted(false);
        return btn;
    }

    protected JButton createLogoutButton() {
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(getWidth() - 120, 10, 100, 30);
        logoutBtn.setBackground(primaryBlue);
        logoutBtn.setForeground(white);
        return logoutBtn;
    }

    protected JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(primaryBlue);
        btn.setForeground(white);
        btn.setFont(mainFont);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    protected JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(white);
        btn.setForeground(primaryBlue);
        btn.setFont(mainFont);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(primaryBlue));
        return btn;
    }
}