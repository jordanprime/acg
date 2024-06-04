import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnonymousUserTests {

    @Test
    @DisplayName("Adding a user without authentication.")
    void anonymousAddUser() {

        String username = "john_doe";
        String name = "John Doe";
        String password = "password123";

        LMS lms = new LMS();
        Student student1 = new Student(username, password, name);

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addUser(null, student1)
        );

        assertEquals(thrown.getMessage(), "Only LMS Admin can add users.");

    }

    @Test
    @DisplayName("Adding an exam grade for a student without authentication.")
    void anonymousAddExamGrade() {

        String username = "john_doe";
        LMS lms = new LMS();

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.addExamGrade(null, username, "HD")
        );

        assertEquals(thrown.getMessage(), "Invalid session.");

    }

    @Test
    @DisplayName("Getting all exam grades without authentication.")
    void anonymousGetGrades() {

        LMS lms = new LMS();

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.getExamGrades(null)
        );

        assertEquals(thrown.getMessage(), "Invalid session.");

    }

    @Test
    @DisplayName("Getting all assignments without authentication.")
    void anonymousGetAssignments() {

        LMS lms = new LMS();

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.getAssignments(null)
        );

        assertEquals(thrown.getMessage(), "Invalid session.");

    }

    @Test
    @DisplayName("Uploading an assignment without authentication.")
    void anonymousUploadAssignment() {

        LMS lms = new LMS();
        String assignmentforUsername = "john_doe";

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> lms.uploadAssignment(null, assignmentforUsername, "Assignment 6")
        );

        assertEquals(thrown.getMessage(), "Invalid session.");

    }

    @Test
    @DisplayName("Add and get academic calendar without authentication.")
    void anonymousAcademicCalendar() {

        LMS lms = new LMS();

        IllegalStateException thrown1 = assertThrows(
                IllegalStateException.class,
                () -> lms.addEventToCalendar(null,"2024-06-03", "A really great event")
        );

        IllegalStateException thrown2 = assertThrows(
                IllegalStateException.class,
                () -> lms.getAcademicCalendar(null)
        );

        assertAll("Multiple assertions",
                () -> assertEquals(thrown1.getMessage(), "Invalid session."),
                () -> assertEquals(thrown2.getMessage(), "Invalid session.")
        );

    }

    @Test
    @DisplayName("Add and get course materials without authentication.")
    void anonymousCourseMaterials() {

        LMS lms = new LMS();

        IllegalStateException thrown1 = assertThrows(
                IllegalStateException.class,
                () -> lms.addCourseMaterial(null,"Excellent course material")
        );

        IllegalStateException thrown2 = assertThrows(
                IllegalStateException.class,
                () -> lms.getCourseMaterials(null)
        );

        assertAll("Multiple assertions",
                () -> assertEquals(thrown1.getMessage(), "Invalid session."),
                () -> assertEquals(thrown2.getMessage(), "Invalid session.")
        );

    }

}
