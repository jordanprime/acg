import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    ///////////

//    @Test
//    @DisplayName("Adding an exam grade for a student without authentication.")
//    void anonymousAddExamGrade() {
//
//        String username = "john_doe";
//        LMS lms = new LMS();
//
//        IllegalStateException thrown = assertThrows(
//                IllegalStateException.class,
//                () -> lms.addExamGrade(null, username, "HD")
//        );
//
//        assertEquals(thrown.getMessage(), "Invalid session.");
//
//    }
//
//    @Test
//    @DisplayName("Getting all exam grades without authentication.")
//    void anonymousGetGrades() {
//
//        LMS lms = new LMS();
//
//        IllegalStateException thrown = assertThrows(
//                IllegalStateException.class,
//                () -> lms.getExamGrades(null)
//        );
//
//        assertEquals(thrown.getMessage(), "Invalid session.");
//
//    }
//
//    @Test
//    @DisplayName("Getting all assignments without authentication.")
//    void anonymousGetAssignments() {
//
//        LMS lms = new LMS();
//
//        IllegalStateException thrown = assertThrows(
//                IllegalStateException.class,
//                () -> lms.getAssignments(null)
//        );
//
//        assertEquals(thrown.getMessage(), "Invalid session.");
//
//    }

    @Test
    @DisplayName("Uploading an assignment with student authentication.")
    void studentUploadAssignment() {

        LMS lms = new LMS();
        Session session = lms.authenticate("john_doe", "password123");
        String assignmentforUsername = "john_doe";

        assertDoesNotThrow(() -> lms.uploadAssignment(session, assignmentforUsername, "Assignment 6"));

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

}
