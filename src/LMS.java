import java.util.*;

public class LMS {
    private Map<String, User> users;
    private List<String> courseMaterials;
    private Map<String, String> academicCalendar;
    private Map<String, String> assignments;
    private Map<String, String> examGrades;
    private Map<String, Session> activeSessions;
    private Map<String, List<String>> examQuestions;
    private Map<String, Map<String, String>> examSubmissions;
    private List<ForumPost> forumPosts;

    public LMS() {
        users = new HashMap<>();
        courseMaterials = new ArrayList<>();
        academicCalendar = new HashMap<>();
        assignments = new HashMap<>();
        examGrades = new HashMap<>();
        activeSessions = new HashMap<>();
        examQuestions = new HashMap<>();
        examSubmissions = new HashMap<>();
        forumPosts = new ArrayList<>();
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
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        if (!(session.getUser() instanceof LMSAdmin) && !(session.getUser() instanceof AcademicStaff)) {
            throw new IllegalStateException("Only LMS Admin or Academic Staff can add course materials.");
        }
        courseMaterials.add(material);
    }

    public void addEventToCalendar(Session session, String date, String event) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        if (!(session.getUser() instanceof LMSAdmin) && !(session.getUser() instanceof AcademicStaff)) {
            throw new IllegalStateException("Only LMS Admin or Academic Staff can add events to the calendar.");
        }
        academicCalendar.put(date, event);
    }

    public void addExamGrade(Session session, String studentUsername, String grade) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        if (!(session.getUser() instanceof LMSAdmin) && !(session.getUser() instanceof AcademicStaff)) {
            throw new IllegalStateException("Only LMS Admin or Academic Staff can add exam grades.");
        }
        examGrades.put(studentUsername, grade);
    }

    public List<String> getCourseMaterials(Session session) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return courseMaterials;
    }

    public Map<String, String> getAcademicCalendar(Session session) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return academicCalendar;
    }

    public void uploadAssignment(Session session, String studentUsername, String assignment) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        assignments.put(studentUsername, assignment);
    }

    public Map<String, String> getAssignments(Session session) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return assignments;
    }

    public Map<String, String> getExamGrades(Session session) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return examGrades;
    }

    // New methods for exams
    public void addExam(Session session, String examName, List<String> questions) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        if (!(session.getUser() instanceof LMSAdmin) && !(session.getUser() instanceof AcademicStaff)) {
            throw new IllegalStateException("Only LMS Admin or Academic Staff can add exams.");
        }
        examQuestions.put(examName, questions);
    }

    public List<String> getExamQuestions(Session session, String examName) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return examQuestions.getOrDefault(examName, new ArrayList<>());
    }

    public void submitExam(Session session, String examName, Map<String, String> answers) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        if (!(session.getUser() instanceof Student)) {
            throw new IllegalStateException("Only students can submit exams.");
        }
        String studentUsername = session.getUsername();
        examSubmissions.putIfAbsent(examName, new HashMap<>());
        examSubmissions.get(examName).put(studentUsername, answers.toString());
    }

    public Map<String, String> getExamSubmissions(Session session, String examName) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return examSubmissions.getOrDefault(examName, new HashMap<>());
    }

    public void addForumPost(Session session, String message) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        if (!(session.getUser() instanceof AcademicStaff) && !(session.getUser() instanceof Student)) {
            throw new IllegalStateException("Only Academic Staff or Students can post in the forum.");
        }
        forumPosts.add(new ForumPost(session.getUsername(), message));
    }

    public List<ForumPost> getForumPosts(Session session) {
        if (!validateSession(session)) {
            throw new IllegalStateException("Invalid session.");
        }
        return forumPosts;
    }

}
