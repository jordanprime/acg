abstract class User {
    String username;
    String password;
    String name;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public abstract void login();

    public void logout() {
        System.out.println(name + " logged out.");
    }
}

class Student extends User {
    public Student(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public void login() {
        System.out.println("Student " + name + " logged in.");
    }
}

class AcademicStaff extends User {
    public AcademicStaff(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public void login() {
        System.out.println("Academic Staff " + name + " logged in.");
    }
}
