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

        mainPanel.add(studentPanel, "Student");

        // Staff Dashboard Panel
        JPanel staffPanel = new JPanel(new BorderLayout());
        JTextArea staffInfo = new JTextArea();
        staffPanel.add(new JScrollPane(staffInfo), BorderLayout.CENTER);
        JButton logoutButtonStaff = new JButton("Logout");
        staffPanel.add(logoutButtonStaff, BorderLayout.SOUTH);

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
                        cardLayout.show(mainPanel, "Staff");
                    } else if (user instanceof LMSAdmin) {
                        adminInfo.setText("Welcome, " + session.getName() + "\n\n");
                        adminInfo.append("Admin Panel:\n");
                        // Additional admin-specific info can be added here
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
                }
                cardLayout.show(mainPanel, "Login");
            }
        });

        logoutButtonStaff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    session.invalidate();
                    session = null;
                }
                cardLayout.show(mainPanel, "Login");
            }
        });

        logoutButtonAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null) {
                    session.invalidate();
                    session = null;
                }
                cardLayout.show(mainPanel, "Login");
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (session != null && session.getUser() instanceof LMSAdmin) {
                    String newUsername = JOptionPane.showInputDialog(frame, "Enter new user's username:");
                    String newPassword = JOptionPane.showInputDialog(frame, "Enter new user's password:");
                    String newName = JOptionPane.showInputDialog(frame, "Enter new user's name:");
                    String userType = JOptionPane.showInputDialog(frame, "Enter user type (student, staff, admin):");
                    User newUser;
                    switch (userType.toLowerCase()) {
                        case "student":
                            newUser = new Student(newUsername, newPassword, newName);
                            break;
                        case "staff":
                            newUser = new AcademicStaff(newUsername, newPassword, newName);
                            break;
                        case "admin":
                            newUser = new LMSAdmin(newUsername, newPassword, newName);
                            break;
                        default:
                            JOptionPane.showMessageDialog(frame, "Invalid user type", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    lms.addUser(session, newUser);
                } else {
                    JOptionPane.showMessageDialog(frame, "Only LMS Admin can add users", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        // Sample usage
        LMS lms = new LMS();
        User admin = new LMSAdmin("admin1", "adminpassword", "Admin Name");
        lms.addUser(null, admin); // Initial admin creation, no session required

        // Create the GUI
    }
}