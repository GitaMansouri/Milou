package gui;

import framework.SingletonSessionFactory;
import model.*;
import services.UserService;
import javax.swing.*;
import java.awt.*;

import static services.UserService.emailExists;

public class SignupForm extends BaseFrame {
    private final User user = new User();

    public SignupForm() {
        super("Sign Up", 500, 350);
        setupUI();
    }

    private void setupUI() {
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 80, 25);
        nameLabel.setFont(mainFont);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 250, 25);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 90, 80, 25);
        emailLabel.setFont(mainFont);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 90, 250, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 130, 80, 25);
        passLabel.setFont(mainFont);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 130, 250, 25);

        JButton registerBtn = createPrimaryButton("Register");
        registerBtn.setBounds(150, 180, 100, 30);

        JButton backBtn = createSecondaryButton("Back");
        backBtn.setBounds(260, 180, 100, 30);

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword());

            if (user.registerUser(name, email, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
                new LoginForm();
            } else {
                JOptionPane.showMessageDialog(this, "Email already exists!");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passLabel);
        add(passField);
        add(registerBtn);
        add(backBtn);
    }
}