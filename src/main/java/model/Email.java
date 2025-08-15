package model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private String emailBody;
    @Basic(optional = false)
    @Column(name = "Date")
    private LocalDate date;

    public Email(){
    }
    public Email(String code, User senderEmail, User recipientEmail, String subject, String emailBody, LocalDate date){
        this.code = code;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.emailBody = emailBody;
        this.date = date;
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

    public String getEmailBody() {
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
}
