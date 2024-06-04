import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class LMS_GUI {
    private LMS lms;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

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

        mainPanel.add(studentPanel, "Student");

        // Staff Dashboard Panel
        JPanel staffPanel = new JPanel(new BorderLayout());
        JTextArea staffInfo = new JTextArea();
        staffPanel.add(new JScrollPane(staffInfo), BorderLayout.CENTER);
        JButton logoutButtonStaff = new JButton("Logout");
        staffPanel.add(logoutButtonStaff, BorderLayout.SOUTH);

        mainPanel.add(staffPanel, "Staff");

        // Adding main panel to frame
        frame.add(mainPanel);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = lms.authenticate(username, password);
                if (user != null) {
                    if (user instanceof Student) {
                        studentInfo.setText("Welcome, " + user.getName() + "\n\n");
                        studentInfo.append("Course Materials:\n");
                        for (String material : lms.getCourseMaterials()) {
                            studentInfo.append(material + "\n");
                        }
                        studentInfo.append("\nAssignments:\n");
                        for (Map.Entry<String, String> entry : lms.getAssignments().entrySet()) {
                            if (entry.getKey().equals(user.getUsername())) {
                                studentInfo.append(entry.getValue() + "\n");
                            }
                        }
                        studentInfo.append("\nGrades:\n");
                        for (Map.Entry<String, String> entry : lms.getExamGrades().entrySet()) {
                            if (entry.getKey().equals(user.getUsername())) {
                                studentInfo.append(entry.getValue() + "\n");
                            }
                        }
                        cardLayout.show(mainPanel, "Student");
                    } else if (user instanceof AcademicStaff) {
                        staffInfo.setText("Welcome, " + user.getName() + "\n\n");
                        staffInfo.append("Course Materials:\n");
                        for (String material : lms.getCourseMaterials()) {
                            staffInfo.append(material + "\n");
                        }
                        staffInfo.append("\nAcademic Calendar:\n");
                        for (Map.Entry<String, String> entry : lms.getAcademicCalendar().entrySet()) {
                            staffInfo.append(entry.getKey() + ": " + entry.getValue() + "\n");
                        }
                        cardLayout.show(mainPanel, "Staff");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutButtonStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        logoutButtonStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

    }

    public static void main(String[] args) {
        // Sample usage
        LMS lms = new LMS();
        User student = new Student("student1", "password123", "John Doe");
        lms.addUser(student);

        // Create the GUI and show it
        LMS_GUI gui = new LMS_GUI(lms);
    }
}
