public class Main {
    public static void main(String[] args) {
        LMS lms = new LMS();

        Student student1 = new Student("john_doe", "password123", "John Doe");
        AcademicStaff staff1 = new AcademicStaff("prof_smith", "password456", "Prof. Smith");

        lms.addUser(student1);
        lms.addUser(staff1);

        lms.authenticate("john_doe", "password123");
        lms.authenticate("prof_smith", "password456");

        lms.addCourseMaterial("Course Material 1");
        lms.addCourseMaterial("Course Material 2");
        lms.viewCourseMaterials();

        lms.addEventToCalendar("2024-06-01", "Assignment 1 Due");
        lms.addEventToCalendar("2024-06-15", "Midterm Exam");
        lms.viewAcademicCalendar();

        lms.uploadAssignment("john_doe", "Assignment 1");
        lms.viewAssignments();

        lms.addExamGrade("john_doe", "A");
        lms.viewGrades();

        student1.logout();
        staff1.logout();
    }
}
