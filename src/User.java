import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

abstract class User {
    private String username;  // Made private
    private String hashedPassword;  // Made private
    private String name;  // Made private

    public User(String username, String password, String name) {
        this.username = username;
        this.hashedPassword = hashPassword(password);
        this.name = name;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public boolean checkPassword(String password) {
        return this.hashedPassword.equals(hashPassword(password));
    }

    public abstract void login();

    public void logout() {
        System.out.println(name + " logged out.");
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

}

class Student extends User {
    public Student(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public void login() {
        System.out.println("Student " + this.getUsername() + " logged in.");
    }

}

class AcademicStaff extends User {
    public AcademicStaff(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public void login() {
        System.out.println("Academic Staff " + this.getUsername() + " logged in.");
    }

}

class LMSAdmin extends User {
    public LMSAdmin(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public void login() {
        System.out.println("LMS Admin " + this.getUsername() + " logged in.");
    }
}
