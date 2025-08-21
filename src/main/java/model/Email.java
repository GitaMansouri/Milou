package model;

import framework.SingletonSessionFactory;
import jakarta.persistence.*;

import java.time.LocalDate;

import static services.UserService.emailExists;

@Entity
@Table(name = "emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "EmailCode", unique = true, length = 6)
    private String code;
    @Basic(optional = false)
    @Column(name = "SenderEmail")
    private User senderEmail;
    @Basic(optional = false)
    @Column(name = "RecipientEmail")
    private User recipientEmail;
    @Basic(optional = false)
    @Column(name = "Subject")
    private String subject;
    @Basic(optional = false)
    @Column(name = "EmailBody")
    private static String emailBody;
    @Basic(optional = false)
    @Column(name = "Date")
    private LocalDate date;
    @Column(name = "isReplay")
    private boolean isReply = false;
    @Column(name = "isForward")
    private boolean isForward = false;


    public Email(){
    }
    public Email(String code, User senderEmail, User recipientEmail, String subject, String emailBody, LocalDate date, Boolean isReply, Boolean isForward){
        this.code = code;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.emailBody = emailBody;
        this.date = date;
        this.isReply = false;
        this.isForward = false;
    }

    public Email(User newSender, String replySubject, String body, LocalDate now, String generatedCode, Email originalEmail) {
    }

    public Email(String senderEmail, String subject, String body, LocalDate date, String generatedCode, Object originalEmail) {
    }

    public String getCode() {
        return code;
    }

    public User getSenderEmail() {
        return senderEmail;
    }

    public User getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public static String getEmailBody() {
        return emailBody;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSenderEmail(User senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setRecipientEmail(User recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public boolean isForward() {
        return isForward;
    }

    public void setForward(boolean forward) {
        isForward = forward;
    }

    public static boolean registerUser(String name, String email, String password) {
        try {
            if (name == null || name.trim().isEmpty()) {
                System.err.println("Your name field can not be empty!");
                return false;
            }
            if (email == null || email.trim().isEmpty()) {
                System.err.println("Your email field can not be empty!");
                return false;
            }
            if (password == null || password.trim().isEmpty()) {
                System.err.println("Your password field can not be empty!");
                return false;
            }
            if (password.length() < 8) {
                System.err.println("Your password must be stronger to be secure!");
                return false;
            }

            if (!email.endsWith("@Milou.com")) email = email + "@Milou.com";

            if (emailExists(email)) {
                System.err.println("An account with this email already exists");
                return false;
            }

            User user = new User(name, email, password);
            SingletonSessionFactory.get().inTransaction(session -> session.persist(user));

            return true;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during registration: " + e.getMessage());
            return false;
        }
    }
}
