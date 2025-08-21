package ui;

import gui.LoginForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new LoginForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}