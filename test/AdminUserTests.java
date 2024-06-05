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

    @Test
    @DisplayName("Create a user as an admin that already exists.")
    void creatingAStudentUserThatAlreadyExists() {

        String adminUsername = "admin";
        String adminPassword = "password";
        LMS lms = new LMS();
        Session session = lms.authenticate(adminUsername, adminPassword);

        Student student1 = new Student("pretend_student", "password123", "Pretend Student");

        assertDoesNotThrow(() -> lms.addUser(session, student1));

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> lms.addUser(session, student1)
        );

        assertEquals(thrown.getMessage(), "User with username pretend_student already exists.");

    }

    @Test
    @DisplayName("Add and get a forum post as an admin user.")
    void addForumPost() {

        LMS lms = new LMS();
        Session session = lms.authenticate("admin", "password");

        IllegalStateException thrown1 = assertThrows(
                IllegalStateException.class,
                () -> lms.addForumPost(session,"A very important public post on the forum")
        );

        assertAll("Multiple assertions",
                () -> assertEquals("[]", lms.getForumPosts(session).toString()),
                () -> assertEquals(thrown1.getMessage(), "Only Academic Staff or Students can post in the forum.")
        );

    }

}
