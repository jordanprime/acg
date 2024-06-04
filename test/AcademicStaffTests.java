import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AcademicStaffTests {

    @Test
    @DisplayName("Testing a staff feature")
    void feature1() {

    }

    @Test
    @DisplayName("Adding an exam grade with an incorrect grade using Academic/Staff authentication.")
    void academicAddIncorrectExamGrade() {

        LMS lms = new LMS();
        Session session = lms.authenticate("prof_smith", "password456");

        try {
            lms.addExamGrade(session, "john_doe", "mistakeinGrade!%$!#");
        } catch (Exception e) {
            return;
        }

        fail("An academic/staff member has entered an incorrect exam grade that has been accepted.");

    }

}
