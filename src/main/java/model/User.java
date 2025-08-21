package model;


import framework.SingletonSessionFactory;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends Email{
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    @Column(unique = true)
    private String email;
    @Basic(optional = false)
    private String password;

    public User() {
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{\n" +
              "\tname='" + name + "'\n" +
                "\temail='" + email + "'\n" +
                '}';
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

            User newUser = new User(name, email, password);
            SingletonSessionFactory.get().inTransaction(session -> session.persist(newUser));
            return true;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during registration: " + e.getMessage());
            return false;
        }
    }


    public static boolean emailExists(String email) {
        try {
            if (email == null) {
                System.err.println("Your email field can not be empty!");
                return false;
            }

            List<Integer> ids = SingletonSessionFactory.get().fromTransaction(session ->
                    session.createNativeQuery(
                                    "select id from users where email = :email",
                                    Integer.class)
                            .setParameter("email", email)
                            .getResultList()
            );

            return !ids.isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking email existence: " + e.getMessage());
            return false;
        }
    }
}
