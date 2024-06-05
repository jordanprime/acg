import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcademicStaffTests {

    @Test
    @DisplayName("Adding an exam grade with an incorrect grade using Academic/Staff authentication. Input not sanitized.")
    void academicAddIncorrectExamGrade() {

        LMS lms = new LMS();
        Session session = lms.authenticate("prof_smith", "password456");

        try {
            lms.addExamGrade(session, "john_doe", "mistakeInGrade!%$!#");
        } catch (Exception e) {
            return;
        }

        fail("An academic/staff member has entered an incorrect exam grade that has been accepted.");

    }

    @Test
    @DisplayName("Adding an exam grade for a non-existent student.")
    void academicAddExamGradeForNonExistentStudent() {

        LMS lms = new LMS();
        Session session = lms.authenticate("prof_smith", "password456");

        try {
            lms.addExamGrade(session, "this_student_doesnt_exist", "HD");
        } catch (Exception e) {
            return;
        }

        fail("An academic/staff member has entered an exam grade for a student that does not exist.");

    }

    @Test
    @DisplayName("Add and get course materials with Academic/Staff authentication.")
    void academicStaffAddCourseMaterials() {

        LMS lms = new LMS();
        Session session = lms.authenticate("prof_smith", "password456");

        assertAll("Multiple assertions",
                () -> assertDoesNotThrow(() -> lms.addCourseMaterial(session,"Excellent course material")),
                () -> assertDoesNotThrow(() -> lms.getCourseMaterials(session)),
                () -> assertEquals("[Excellent course material]", lms.getCourseMaterials(session).toString())
        );

    }

    @Test
    @DisplayName("Add and get academic calendar with academic/staff authentication.")
    void academicStaffAddAcademicCalendar() {

        LMS lms = new LMS();
        Session session = lms.authenticate("prof_smith", "password456");

        assertAll("Multiple assertions",
                () -> assertDoesNotThrow(() -> lms.addEventToCalendar(session,"2024-06-03", "A really great event")),
                () -> assertDoesNotThrow(() -> lms.getAcademicCalendar(session)),
                () -> assertEquals("{2024-06-03=A really great event}", lms.getAcademicCalendar(session).toString())
        );

    }

    @Test
    @DisplayName("Add to the academic calendar with an invalid date using academic/staff authentication.")
    void academicStaffAddAcademicCalendarInvalidDate() {

        LMS lms = new LMS();
        Session session = lms.authenticate("prof_smith", "password456");

        try {
            lms.addEventToCalendar(session,"2024-THIS-IS-NOT-A-DATE-AT-ALL-06-03", "A really great event");
        } catch (Exception e) {
            return;
        }

        fail("Currently accepts invalid dates which is not at all desirable.");

    }

}
