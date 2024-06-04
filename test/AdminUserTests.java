import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminUserTests {

    @Test
    @DisplayName("Create a user as an admin.")
    void createStudent() {

        String adminUsername = "admin";
        String adminPassword = "password";
        LMS lms = new LMS();
        Session session = lms.authenticate(adminUsername, adminPassword);

        Student student1 = new Student("pretend_student", "password123", "Pretend Student");

        assertDoesNotThrow(() -> lms.addUser(session, student1));

    }

}
