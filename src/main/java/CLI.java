package cli;

import framework.SingletonSessionFactory;
import model.Email;
import model.User;
import services.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final Scanner scn = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        initializeDatabase();
        showWelcomeMessage();

        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = scn.nextLine().trim().toLowerCase();

            switch (choice) {
                case "l", "login" -> handleLogin();
                case "s", "signup" -> handleSignup();
                case "e", "exit" -> running = false;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }

        shutdown();
    }

    private static void initializeDatabase() {
        SingletonSessionFactory.get();
    }

    private static void shutdown() {
        SingletonSessionFactory.close();
        scn.close();
        System.out.println("\nThank you for using Milou Email Service. Goodbye!");
    }

    private static void showWelcomeMessage() {
        System.out.println("Welcome to Milou Mail :) \nSimple & Secure Email Service!");
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu: \n[L]ogin[S]ign \nup[E]xit \nPlease enter your choice: ");
    }

    private static void handleLogin() {
        System.out.print("Email: ");
        String email = normalizeEmail(scn.nextLine());

        System.out.print("Password: ");
        String password = scn.nextLine();

        currentUser = UserService.login(email, password);
        if (currentUser != null) {
            showUserDashboard();
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private static void handleSignup() {
        System.out.print("Full Name: ");
        String name = scn.nextLine().trim();

        System.out.print("Email: ");
        String email = normalizeEmail(scn.nextLine());

        System.out.print("Password (min 8 characters): ");
        String password = scn.nextLine();

        if (UserService.register(name, email, password)) {
            System.out.println("Registration successful! Please login with your new account.");
        } else {
            System.out.println("Registration failed! Please try again.");
        }
    }

    private static void showUserDashboard() {
        System.out.printf("\nWelcome back, %s!\n", currentUser.getName());
        showUnreadEmails();

        boolean loggedIn = true;
        while (loggedIn) {
            showEmailOperationsMenu();
            String choice = scn.nextLine().trim().toLowerCase();

            switch (choice) {
                case "s", "send" -> handleSendEmail();
                case "v", "view" -> handleViewEmails();
                case "r", "reply" -> handleReply();
                case "f", "forward" -> handleForward();
                case "l", "logout" -> loggedIn = false;
                case "x", "exit" -> { loggedIn = false; shutdown(); System.exit(0); }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        currentUser = null;
    }

    private static void showUnreadEmails() {
        List<Email> unread = EmailService.unreadEmails(currentUser.getEmail());
        if (!unread.isEmpty()) {
            System.out.println("\nYou have " + unread.size() + " unread emails:");
            unread.forEach(email ->
                    System.out.printf("+ %s - %s (%s)\n",
                            email.getSender().getEmail(),
                            email.getSubject(),
                            email.getCode())
            );
        }
    }

    private static void showEmailOperationsMenu() {
        System.out.println("\nEmail Operations: \n[S]end new email \n[V]iew emails \n[R]eply to email \n[F]orward email \n[L]ogout \nE[x]it \nPlease enter your choice:\s");
    }

    private static void handleSendEmail() {
        System.out.print("Recipient(s) (comma separated): ");
        String recipientsInput = scn.nextLine();
        String[] recipients = parseRecipients(recipientsInput);

        System.out.print("Subject: ");
        String subject = scn.nextLine();

        System.out.println("Body (press Enter then Ctrl+D to finish):");
        String body = readMultilineInput();

        if (EmailService.sendEmail(recipients, subject, body, currentUser.getEmail())) {
            System.out.printf("Email sent successfully! Code: %s\n", EmailService.outputCode);
        } else {
            System.out.println("Failed to send email. Please try again.");
        }
    }

    private static void handleViewEmails() {
        System.out.println("\nView Options: \n[A]ll emails \n[U]nread emails \n[S]ent emails Read by \n[C]ode Please enter your choice:  ");

        String choice = scn.nextLine().trim().toLowerCase();
        switch (choice) {
            case "a", "all" -> displayEmails(EmailService.AllEmails(currentUser.getEmail()), "All Emails");
            case "u", "unread" -> displayEmails(EmailService.unreadEmails(currentUser.getEmail()), "Unread Emails");
            case "s", "sent" -> displayEmails(EmailService.sentEmails(currentUser.getEmail()), "Sent Emails");
            case "c", "code" -> {
                System.out.print("Enter email code: ");
                String code = scanner.nextLine().trim();
                EmailService.getEmailByCode(code, currentUser.getEmail());
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleReply() {
        System.out.print("Enter email code to reply: ");
        String code = scn.nextLine().trim();

        System.out.println("Your reply (press Enter then Ctrl+D to finish):");
        String body = readMultilineInput();

        if (EmailService.reply(currentUser.getEmail(), code, body)) {
            System.out.printf("Reply sent successfully! Code: %s\n", EmailService.outputCode);
        } else {
            System.err.println("Failed to send reply! Please check the email code and try again.");
        }
    }

    private static void handleForward() {
        System.out.print("Enter email code to forward: ");
        String code = scn.nextLine().trim();

        System.out.print("New recipient(s) (comma separated): ");
        String recipientsInput = scn.nextLine();
        String[] recipients = parseRecipients(recipientsInput);

        if (EmailService.forward(code, recipients, currentUser.getEmail())) {
            System.out.printf("Email forwarded successfully! Code: %s\n", EmailService.outputCode);
        } else {
            System.err.println("Failed to forward email! Please check the email code and try again.");
        }
    }

    private static String[] parseRecipients(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    private static String normalizeEmail(String email) {
        return email.endsWith("@Milou.com") ? email : email + "@Milou.com";
    }

    private static String readMultilineInput() {
        StringBuilder body = new StringBuilder();
        while (scn.hasNextLine()) {
            String line = scn.nextLine();
            if (line.isEmpty() && !scn.hasNextLine()) break;
            body.append(line).append("\n");
        }
        return body.toString().trim();
    }

    private static void displayEmails(List<Email> emails, String title) {
        if (emails.isEmpty()) {
            System.out.println("No emails found!");
            return;
        }

        System.out.println("\n" + title + ":");
        emails.forEach(email ->
                System.out.println("+ %s - %s (%s)\n",
                        email.getSender().getEmail(),
                        email.getSubject(),
                        email.getCode())
        );
    }
}