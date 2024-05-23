import java.util.*;

class LMS {
    private String hello;
    private Map<String, User> users;
    private List<String> courseMaterials;
    private Map<String, String> academicCalendar;
    private Map<String, String> assignments;
    private Map<String, String> examGrades;

    public LMS() {
        users = new HashMap<>();
        courseMaterials = new ArrayList<>();
        academicCalendar = new HashMap<>();
        assignments = new HashMap<>();
        examGrades = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.username, user);
    }

    public void authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            user.login();
        } else {
            System.out.println("Authentication failed for " + username);
        }
    }

    public void addCourseMaterial(String material) {
        courseMaterials.add(material);
    }

    public void viewCourseMaterials() {
        for (String material : courseMaterials) {
            System.out.println(material);
        }
    }

    public void addEventToCalendar(String date, String event) {
        academicCalendar.put(date, event);
    }

    public void viewAcademicCalendar() {
        for (Map.Entry<String, String> entry : academicCalendar.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void uploadAssignment(String studentUsername, String assignment) {
        assignments.put(studentUsername, assignment);
    }

    public void viewAssignments() {
        for (Map.Entry<String, String> entry : assignments.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void addExamGrade(String studentUsername, String grade) {
        examGrades.put(studentUsername, grade);
    }

    public void viewGrades() {
        for (Map.Entry<String, String> entry : examGrades.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
