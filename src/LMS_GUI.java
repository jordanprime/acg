import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class LMS_GUI {
    private LMS lms;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Session session;

    public LMS_GUI(LMS lms) {
        this.lms = lms;
        frame = new JFrame("Flinders University LMS");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize GUI components
        initialize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void initialize() {
        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        mainPanel.add(loginPanel, "Login");

        // Student Dashboard Panel
        JPanel studentPanel = new JPanel(new BorderLayout());
        JTextArea studentInfo = new JTextArea();
        studentPanel.add(new JScrollPane(studentInfo), BorderLayout.CENTER);
        JButton logoutButtonStudent = new JButton("Logout");
        studentPanel.add(logoutButtonStudent, BorderLayout.SOUTH);

        // Add discussion forum components to student panel
        JTextArea forumTextAreaStudent = new JTextArea(10, 40);
        forumTextAreaStudent.setEditable(false);
        studentPanel.add(new JScrollPane(forumTextAreaStudent), BorderLayout.EAST);

        JPanel forumPanelStudent = new JPanel(new BorderLayout());
        JTextField forumInputFieldStudent = new JTextField();
        JButton postButtonStudent = new JButton("Post");
        forumPanelStudent.add(forumInputFieldStudent, BorderLayout.CENTER);
        forumPanelStudent.add(postButtonStudent, BorderLayout.EAST);
        studentPanel.add(forumPanelStudent, BorderLayout.NORTH);

        // Add exam components to student panel
        JPanel examPanelStudent = new JPanel(new BorderLayout());
        JTextArea examTextAreaStudent = new JTextArea();
        examTextAreaStudent.setEditable(false);
        examPanelStudent.add(new JScrollPane(examTextAreaStudent), BorderLayout.CENTER);
        JTextField examInputFieldStudent = new JTextField();
        JButton submitExamButtonStudent = new JButton("Submit Exam");
        examPanelStudent.add(examInputFieldStudent, BorderLayout.NORTH);
        examPanelStudent.add(submitExamButtonStudent, BorderLayout.SOUTH);
        studentPanel.add(examPanelStudent, BorderLayout.WEST);

        mainPanel.add(studentPanel, "Student");

        // Staff Dashboard Panel
        JPanel staffPanel = new JPanel(new BorderLayout());
        JTextArea staffInfo = new JTextArea();
        staffPanel.add(new JScrollPane(staffInfo), BorderLayout.CENTER);
        JButton logoutButtonStaff = new JButton("Logout");
        staffPanel.add(logoutButtonStaff, BorderLayout.SOUTH);

        // Add discussion forum components to staff panel
        JTextArea forumTextAreaStaff = new JTextArea(10, 40);
        forumTextAreaStaff.setEditable(false);
        staffPanel.add(new JScrollPane(forumTextAreaStaff), BorderLayout.EAST);

        JPanel forumPanelStaff = new JPanel(new BorderLayout());
        JTextField forumInputFieldStaff = new JTextField();
        JButton postButtonStaff = new JButton("Post");
        forumPanelStaff.add(forumInputFieldStaff, BorderLayout.CENTER);
        forumPanelStaff.add(postButtonStaff, BorderLayout.EAST);
        staffPanel.add(forumPanelStaff, BorderLayout.NORTH);

        mainPanel.add(staffPanel, "Staff");

        // Admin Dashboard Panel
        JPanel adminPanel = new JPanel(new BorderLayout());
        JTextArea adminInfo = new JTextArea();
        adminPanel.add(new JScrollPane(adminInfo), BorderLayout.CENTER);
        JButton logoutButtonAdmin = new JButton("Logout");
        JButton addUserButton = new JButton("Add User");
        JPanel adminControls = new JPanel(new GridLayout(2, 1));
        adminControls.add(addUserButton);
        adminControls.add(logoutButtonAdmin);
        adminPanel.add(adminControls, BorderLayout.SOUTH);

        mainPanel.add(adminPanel, "Admin");

        // Adding main panel to frame
        frame.add(mainPanel);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                session = lms.authenticate(username, password);
                if (session != null) {
                    User user = session.getUser();
                    if (user instanceof Student) {
                        studentInfo.setText("Welcome, " + session.getName() + "\n\n");
                        studentInfo.append("Course Materials:\n");
                        for (String material : lms.getCourseMaterials(session)) {
                            studentInfo.append(material + "\n");
                        }
                        studentInfo.append("\nAssignments:\n");
                        for (Map.Entry<String, String> entry : lms.getAssignments(session).entrySet()) {
                            if (entry.getKey().equals(session.getUsername())) {
                                studentInfo.append(entry.getValue() + "\n");
                            }
                        }
                        studentInfo.append("\nGrades:\n");
                        for (Map.Entry<String, String> entry : lms.getExamGrades(session).entrySet()) {
                            if (entry.getKey().equals(session.getUsername())) {
                                studentInfo.append(entry.getValue() + "\n");
                            }
                        }
                        // Display forum posts for students
                        forumTextAreaStudent.setText("");
                        for (ForumPost post : lms.getForumPosts(session)) {
                            forumTextAreaStudent.append(post + "\n");
                        }
                        // Display exam questions for students
                        examTextAreaStudent.setText("Exam Questions:\n");
                        for (String question : lms.getExamQuestions(session, "SampleExam")) {
                            examTextAreaStudent.append(question + "\n");
                        }
                        cardLayout.show(mainPanel, "Student");
                    } else if (user instanceof AcademicStaff) {
                        staffInfo.setText("Welcome, " + session.getName() + "\n\n");
                        staffInfo.append("Course Materials:\n");
                        for (String material : lms.getCourseMaterials(session)) {
                            staffInfo.append(material + "\n");
                        }
                        staffInfo.append("\nAcademic Calendar:\n");
                        for (Map.Entry<String, String> entry : lms.getAcademicCalendar(session).entrySet()) {
                            staffInfo.append(entry.getKey() + ": " + entry.getValue() + "\n");
                        }
                        // Display forum posts for staff
                        forumTextAreaStaff.setText("");
                        for (ForumPost post : lms.getForumPosts(session)) {
                            forumTextAreaStaff.append(post + "\n");
                        }
                        cardLayout.show(mainPanel, "Staff");
                    } else if (user instanceof LMSAdmin) {
                        adminInfo.setText("Welcome, " + session.getName() + "\n\n");
                        adminInfo.append("Admin Panel:\n");
                        cardLayout.show(mainPanel, "Admin");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutButtonStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    session.invalidate();
                    session = null;
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        logoutButtonStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    session.invalidate();
                    session = null;
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        logoutButtonAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    session.invalidate();
                    session = null;
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        postButtonStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    String message = forumInputFieldStudent.getText();
                    if (!message.isEmpty()) {
                        lms.addForumPost(session, message);
                        forumTextAreaStudent.append(new ForumPost(session.getUsername(), message) + "\n");
                        forumInputFieldStudent.setText("");
                    }
                }
            }
        });

        postButtonStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    String message = forumInputFieldStaff.getText();
                    if (!message.isEmpty()) {
                        lms.addForumPost(session, message);
                        forumTextAreaStaff.append(new ForumPost(session.getUsername(), message) + "\n");
                        forumInputFieldStaff.setText("");
                    }
                }
            }
        });

        submitExamButtonStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    String answers = examInputFieldStudent.getText();
                    if (!answers.isEmpty()) {
                        Map<String, String> answerMap = new HashMap<>();
                        answerMap.put(session.getUsername(), answers);
                        lms.submitExam(session, "SampleExam", answerMap);
                        examTextAreaStudent.append("\nExam Submitted\n");
                        examInputFieldStudent.setText("");
                    }
                }
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement user addition logic
            }
        });
    }

}
