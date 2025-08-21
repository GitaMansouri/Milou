package gui;

import framework.SingletonSessionFactory;
import model.User;
import services.UserService;

import javax.swing.*;


public class LoginForm extends BaseFrame {
    private final User user = new User();

    public LoginForm() {
        super("Login", 500, 300);
        initializeUI();
    }

    private void initializeUI() {

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 50, 80, 25);
        emailLabel.setFont(mainFont);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 50, 250, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 90, 80, 25);
        passLabel.setFont(mainFont);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 90, 250, 25);

        JButton loginBtn = createPrimaryButton("Login");
        loginBtn.setBounds(150, 130, 100, 30);

        JButton signupBtn = createSecondaryButton("Sign Up");
        signupBtn.setBounds(260, 130, 100, 30);

        loginBtn.addActionListener(e -> handleLogin(emailField, passField));
        signupBtn.addActionListener(e -> openSignupForm());

        add(emailLabel);
        add(emailField);
        add(passLabel);
        add(passField);
        add(loginBtn);
        add(signupBtn);
    }

    private void handleLogin(JTextField emailField, JPasswordField passField) {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());

        if (User.registerUser(user.getName(), user.getEmail(), user.getPassword())) {
            SingletonSessionFactory.get();
            dispose();
            new DashboardForm();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    private void openSignupForm() {
        dispose();
        new SignupForm();
    }
}