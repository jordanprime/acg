import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentFeatureTests {

    @Test
    @DisplayName("A malicious student tries to create a new student user and cause havoc.")
    void createStudent() {

        String studentUsername = "john_doe";
        String studentPassword = "password123";
        LMS lms = new LMS();
        Session session = lms.authenticate(studentUsername, studentPassword);

        Student student1 = new Student("pretend_student", "password123", "Pretend Student");

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addUser(session, student1)
        );

        assertEquals(thrown.getMessage(), "Only LMS Admin can add users.");

    }

    @Test
    @DisplayName("A malicious student tries to create a AcademicStaff user and cause havoc.")
    void createAcademicStaff() {

        String studentUsername = "john_doe";
        String studentPassword = "password123";
        LMS lms = new LMS();
        Session session = lms.authenticate(studentUsername, studentPassword);

        AcademicStaff staff1 = new AcademicStaff("pretend_staff", "password456", "Pretend Academic/Staff");

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addUser(session, staff1)
        );

        assertEquals(thrown.getMessage(), "Only LMS Admin can add users.");

    }

    @Test
    @DisplayName("A malicious student tries to create an LMSAdmin user and cause havoc.")
    void createAdmin() {

        String studentUsername = "john_doe";
        String studentPassword = "password123";
        LMS lms = new LMS();
        Session session = lms.authenticate(studentUsername, studentPassword);

        AcademicStaff admin1 = new AcademicStaff("pretend_admin", "password789", "Pretend Admin");

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addUser(session, admin1)
        );

        assertEquals(thrown.getMessage(), "Only LMS Admin can add users.");

    }

    @Test
    @DisplayName("Adding an exam grade for a student with student authentication.")
    void studentAddExamGrade() {

        String username = "john_doe";
        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addExamGrade(session, username, "HD")
        );

        assertEquals(thrown.getMessage(), "Only LMS Admin or Academic Staff can add exam grades.");

    }

    @Test
    @DisplayName("Getting all assignments with student authentication.")
    void studentGetAssignments() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        try {
            lms.getAssignments(session);
        } catch (Exception e) {
            return;
        }

        fail("Currently retrieves all student assignments (not just the authenticated student) which is not desirable.");

    }

    @Test
    @DisplayName("Uploading an assignment with student authentication.")
    void studentUploadAssignment() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");
        String assignmentforUsername = "john_doe";

        assertDoesNotThrow(() -> lms.uploadAssignment(session, assignmentforUsername, "Assignment 6"));

    }

    @Test
    @DisplayName("A mischievous student overrides the username with a different student and uploads an assignment for them. Ohh ohhhh.")
    void studentUploadAssignmentForOtherStudent() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        try {
            lms.uploadAssignment(session, "naughtystudentsabotagingsomeotherstudenthere", "Assignment 6");
        } catch (Exception e) {
            return;
        }

        fail("Student can upload an assignment for another student by overriding the student username parameter. Ohhh ohhhh.");

    }

    @Test
    @DisplayName("Add and get academic calendar with student authentication.")
    void studentAcademicCalendar() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addEventToCalendar(session,"2024-06-03", "A really great event")
        );

        assertAll("Multiple assertions",
                () -> assertEquals(thrown.getMessage(), "Only LMS Admin or Academic Staff can add events to the calendar."),
                () -> assertDoesNotThrow(() -> lms.getAcademicCalendar(session)),
                () -> assertEquals("{}", lms.getAcademicCalendar(session).toString())
        );

    }

    @Test
    @DisplayName("Add and get course materials with Student authentication.")
    void studentCourseMaterials() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addCourseMaterial(session,"Excellent course material")
        );

        assertAll("Multiple assertions",
                () -> assertEquals(thrown.getMessage(), "Only LMS Admin or Academic Staff can add course materials."),
                () -> assertDoesNotThrow(() -> lms.getCourseMaterials(session)),
                () -> assertEquals("[]", lms.getCourseMaterials(session).toString())
        );

    }

    @Test
    @DisplayName("get forum post details as a student.")
    void getForumPostDetails() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        assertAll("Multiple assertions",
                () -> assertEquals("[]", lms.getForumPosts(session).toString()),
                () -> assertDoesNotThrow(() -> lms.addForumPost(session,"A very important public post on the forum"))
        );

    }

    @Test
    @DisplayName("Add and get a forum post as a student.")
    void addForumPost() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        lms.addForumPost(session,"A very important public post on the forum");
        List<ForumPost> posts = lms.getForumPosts(session);
        ForumPost post = posts.get(0);

        assertAll("Multiple assertions",
                () -> assertEquals("john_doe", post.getUsername()),
                () -> assertEquals("A very important public post on the forum", post.getMessage())
        );

    }


    @Test
    @DisplayName("Get exam questions from an exam that doesn't exist.")
    void getExamQuestions() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        try {
            lms.getExamQuestions(session, "A Fake Exam Name");
        } catch (Exception e) {
            return;
        }

        fail("A student has tried to retrieve an exam that doesn't exist");

    }

    @Test
    @DisplayName("Get exam submissions as a student (shouldnt be able to get other students)")
    void getExamSubmissions() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");

        try {
            lms.getExamSubmissions(session, "A Fake Exam Name");
        } catch (Exception e) {
            return;
        }

        fail("A student has tried to retrieve all exam submissions and this has succeeded without an error being thrown");

    }

}
