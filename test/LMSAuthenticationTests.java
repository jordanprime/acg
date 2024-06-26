import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.ParameterizedTest;
//import org.junit.jupiter.api.CsvSource;
//import org.junit.jupiter.api.ValueSource;

class LMSAuthenticationTests {

    @Test
    @DisplayName("Authenticate with the LMS as a Student")
    void authenticateAsStudent() {

        final String username = "john_doe";
        final String name = "John Doe";
        final String password = "password123";

        LMS lms = new LMS();
        Session session = lms.authenticate(username, password);

        assertAll("Multiple Assertions",
                () -> assertNotNull(lms.authenticate(username, password), "Test authentication with correct username and password."),
                () -> assertNull(lms.authenticate(username, "incorrectpassword"), "Test authentication with correct username and incorrect password."),
                () -> assertNull(lms.authenticate("incorrectusername", password), "Test authentication with incorrect username and incorrect password."),
                () -> assertEquals(username, session.getUsername(), "The stored username in the LMS matches"),
                () -> assertEquals(name, session.getName(), "The stored name in the LMS matches")
//                () -> assertNotEquals(ploggedInUserassword, loggedInUser.hashedPassword, "God forbid the LMS returned the plain-text password"),
//                () -> assertNull(loggedInUser.hashedPassword, "God forbid the LMS returns the stored password hash! This is awful.")
        );

    }

    @Test
    @DisplayName("Authenticate with the LMS as an Academic/Staff")
    void authenticateAsAcademic() {

        String username = "prof_smith";
        String name = "Prof. Smith";
        String password = "password456";

        LMS lms = new LMS();
        Session session = lms.authenticate(username, password);

        assertAll("Multiple Assertions",
                () -> assertNotNull(lms.authenticate(username, password), "Test authentication with correct username and password."),
                () -> assertNull(lms.authenticate(username, "incorrectpassword"), "Test authentication with correct username and incorrect password."),
                () -> assertNull(lms.authenticate("incorrectusername", password), "Test authentication with correct username and incorrect password."),
                () -> assertEquals(username, session.getUsername(), "The stored username in the LMS matches."),
                () -> assertEquals(name, session.getName(), "The stored name in the LMS matches.")
//                () -> assertNotEquals(password, loggedInUser.hashedPassword, "God forbid the LMS returned the plain-text password."),
//                () -> assertNull(loggedInUser.hashedPassword, "God forbid the LMS returns the stored password hash! This is awful.")
        );

    }

    @Test
    @DisplayName("Logout of the LMS as a Student")
    void logoutAsStudent() {

        String username = "john_doe";
        String name = "John Doe";
        String password = "password123";

        LMS lms = new LMS();
        Student student1 = new Student(username, password, name);
        Session session = lms.authenticate(username, password);
        session.invalidate();

    }

    @Test
    @DisplayName("Logout of the LMS as an Academic/Staff")
    void logoutAsAcademic() {

        final String username = "prof_smith";
        final String name = "Prof. Smith";
        final String password = "password456";

        LMS lms = new LMS();
        AcademicStaff staff1 = new AcademicStaff(username, password, name);
        Session loggedInAcademic = lms.authenticate(username, password);
        Session session = lms.authenticate(username, password);
        session.invalidate();


    }

}