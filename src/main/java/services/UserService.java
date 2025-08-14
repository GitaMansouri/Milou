package services;

public class UserService {
    public boolean registerUser(String name, String email, String password) {
            if (name == null) {
                System.out.println("Your name field can not be empty!");
                return false;
            }
            if (email == null) {
                System.out.println("Your email field can not be empty!");
                return false;
            }
            if (password.length() < 8){
                System.out.println("Your password must be stronger to be secure!");
                return false;
            }
            if (!email.endsWith("@Milou.com")) email = email + "@Milou.com";
            // reject same emails

        System.out.println("Your new account is created. \n" + "Go ahead and login!");
        return true;
    }
}
