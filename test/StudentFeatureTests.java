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

}
