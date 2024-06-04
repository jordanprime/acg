import java.util.*;

public class LMS {
    private Map<String, User> users;
    private List<String> courseMaterials;
    private Map<String, String> academicCalendar;
    private Map<String, String> assignments;
    private Map<String, String> examGrades;
    private Map<String, Session> activeSessions;

    public LMS() {
        users = new HashMap<>();
        courseMaterials = new ArrayList<>();
        academicCalendar = new HashMap<>();
        assignments = new HashMap<>();
        examGrades = new HashMap<>();
        activeSessions = new HashMap<>();
        users.put("admin", new LMSAdmin("admin", "password", "Admin Name")); // Initial admin user
        users.put("john_doe", new Student("john_doe", "password123", "John Doe")); // Initial test student
        users.put("prof_smith", new AcademicStaff("prof_smith", "password456", "Prof. Smith")); // Initial test academic/staff
    }

    public void addUser(Session session, User user) {
        if (session != null && session.getUser() instanceof LMSAdmin) {
            if (!activeSessions.containsKey(session.getUsername())) {
                throw new RuntimeException("Admin session has expired.");
            }
            String username = user.getUsername();
            if (users.containsKey(username)) {
                throw new IllegalArgumentException("User with username " + username + " already exists.");
            }
            users.put(username, user);
            System.out.println("User " + username + " added by admin " + session.getUsername());
        } else {
            throw new IllegalStateException("Only LMS Admin can add users.");
        }
    }

    public Session authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            user.login();
            Session session = new Session(user);
            activeSessions.put(username, session);
            return session;
        } else {
            return null;
        }
    }

    private boolean validateSession(Session session) {
        if (session == null) {
            return false;
        }
        session.resetInactivityTimer();
        return true;
    }

    public void addCourseMaterial(Session session, String material) {
        if (validateSession(session)) {
            courseMaterials.add(material);
        } else {
            System.out.println("Invalid session.");
        }
    }

    public List<String> getCourseMaterials(Session session) {
        if (validateSession(session)) {
            return courseMaterials;
        } else {
            System.out.println("Invalid session.");
            return Collections.emptyList();
        }
    }

    public void addEventToCalendar(Session session, String date, String event) {
        if (validateSession(session)) {
            academicCalendar.put(date, event);
        } else {
            System.out.println("Invalid session.");
        }
    }

    public Map<String, String> getAcademicCalendar(Session session) {
        if (validateSession(session)) {
            return academicCalendar;
        } else {
            System.out.println("Invalid session.");
            return Collections.emptyMap();
        }
    }

    public void uploadAssignment(Session session, String studentUsername, String assignment) {
        if (validateSession(session)) {
            assignments.put(studentUsername, assignment);
        } else {
            System.out.println("Invalid session.");
        }
    }

    public Map<String, String> getAssignments(Session session) {
        if (validateSession(session)) {
            return assignments;
        } else {
            System.out.println("Invalid session.");
            return Collections.emptyMap();
        }
    }

    public void addExamGrade(Session session, String studentUsername, String grade) {
        if (validateSession(session)) {
            examGrades.put(studentUsername, grade);
        } else {
            System.out.println("Invalid session.");
        }
    }

    public Map<String, String> getExamGrades(Session session) {
        if (validateSession(session)) {
            return examGrades;
        } else {
            System.out.println("Invalid session.");
            return Collections.emptyMap();
        }
    }
}
