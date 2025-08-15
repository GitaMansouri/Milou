package model;

import jakarta.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Entity
@Table(name = "recipients")
public class Recipient extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "senderEmail")
    private User senderEmail;

    @JoinColumn(name = "recipientEmail")
    private User recipientEmail;

    public Recipient() {}

    public Recipient(User senderEmail, User recipientEmail) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
    }

    public int getId() {
        return id;
    }

    public User getSenderEmail() {
        return senderEmail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderEmail(User senderEmail) {
        this.senderEmail = senderEmail;
    }

    public User getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(User recipientEmail) {
        this.recipientEmail = recipientEmail;
    }


    public void SaveRecipients(Session session) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter recipients' emails and Separate names with commas. ");
        String recipientsInput = scanner.nextLine();

        List<User> recipients = Arrays.stream(recipientsInput.split("\\s*,\\s*"))
                .filter(email -> !email.trim().isEmpty())
                .map(email -> new User("recipient", email.trim(), ""))
                .collect(Collectors.toList());

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            for (User recipient : recipients) {
                session.persist(recipient);
            }
            transaction.commit();

        } catch (Exception e) {
            System.err.println("Something went wrong! Please try again." + e.getMessage());
        } finally {
            scanner.close();
        }

    }
}