package services;

import framework.SingletonSessionFactory;
import model.User;
import java.util.List;

public class UserService {
    public boolean registerUser(String name, String email, String password) {
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

            User User = new User(name, email, password);
            SingletonSessionFactory.get().inTransaction(session -> session.persist(newUser));

            System.out.printnln("Your new account is created. \nGo ahead and login!\n");
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


    public static User login(String email, String password) {
        try {
            if (email == null || email.trim().isEmpty()) {
                System.err.println("Your name field can not be empty!");
                return null;
            }
            if (password == null || password.trim().isEmpty()) {
                System.err.println("Your password field can not be empty!");
                return null;
            }

            User user = SingletonSessionFactory.get().fromTransaction(session ->
                    session.createNativeQuery(
                                    "select * from users where email = :email and password = :password",
                                    User.class)
                            .setParameter("email", email)
                            .setParameter("password", password)
                            .getSingleResult()
            );

            if (user == null) {
                System.err.println("Invalid email or password!");
                return null;
            }

            System.out.println("Welcome back, " + user.getName() + "!");
            return user;


        } catch (Exception e) {
            System.err.println("An unexpected error occurred during login: " + e.getMessage());
            return null;
        }
    }
}
