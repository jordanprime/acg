public class Main {

    // Learning Management System
    //Over a period of last 50 years, Flinders University has gained the trust and satisfaction of
    //a large student base in SA. To cope with the expectation and satisfaction of a large number
    //of Students, Flinders wants to introduce an effective Learning Management System (LMS).
    //Though it will be integrated into the Flinders website, every student and academic staff should
    //be authenticated while entering the LMS. Students will be able to access all their course material
    //through the LMS. An academic calendar will highlight all the assignment and exam schedules
    //for convenience and time management. Students will be able to upload assignments, take part
    //in online exams and check their grades through the LMS. Academics should be able to upload
    //contents and communicate with students through open student discussion forums. For privacy
    //protection, the LMS will request authentication after any inactivity of ten minutes after opening
    //the LMS page. A user will be able to logout at any time from the LMS page.
    //HINT: The problem described above mentions various actors and use cases to be
    //considered for the new system. If required, you are allowed to make assumptions
    //in order to outline the detailed working of your solution.

    public static void main(String[] args) {
        LMS lms = new LMS();

        Student student1 = new Student("john_doe", "password123", "John Doe");
        AcademicStaff staff1 = new AcademicStaff("prof_smith", "password456", "Prof. Smith");

        lms.addUser(student1);
        lms.addUser(staff1);

        lms.addCourseMaterial("Course Material 1");
        lms.addCourseMaterial("Course Material 2");

        lms.addEventToCalendar("2024-06-01", "Assignment 1 Due");
        lms.addEventToCalendar("2024-06-15", "Midterm Exam");

        lms.uploadAssignment("john_doe", "Assignment 1");

        lms.addExamGrade("john_doe", "A");

        new LMS_GUI(lms);
    }
}
