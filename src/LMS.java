import java.util.*;

class LMS {
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

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        } else {
            return null;
        }
    }

    public void addCourseMaterial(String material) {
        courseMaterials.add(material);
    }

    public List<String> getCourseMaterials() {
        return courseMaterials;
    }

    public void addEventToCalendar(String date, String event) {
        academicCalendar.put(date, event);
    }

    public Map<String, String> getAcademicCalendar() {
        return academicCalendar;
    }

    public void uploadAssignment(String studentUsername, String assignment) {
        assignments.put(studentUsername, assignment);
    }

    public Map<String, String> getAssignments() {
        return assignments;
    }

    public void addExamGrade(String studentUsername, String grade) {
        examGrades.put(studentUsername, grade);
    }

    public Map<String, String> getExamGrades() {
        return examGrades;
    }
}