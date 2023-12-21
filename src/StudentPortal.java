

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentPortal {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_portal";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Establish database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Display menu
            System.out.println("Student Portal");
            System.out.println("1. Signup");
            System.out.println("2. Signin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    signup(connection);
                    break;
                case 2:
                    signin(connection);
                    break;
                case 3:
                    System.out.println("Exiting the portal. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static void signup(Connection connection) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Signup");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // TODO: Implement password strength validation

            // Check if the username is already taken
            if (isUsernameTaken(connection, username)) {
                System.out.println("Username is already taken. Please choose another.");
            } else {
                // Add the new user to the database
                addUser(connection, username, password);
                System.out.println("Signup successful! You can now sign in.");
            }
        } catch (SQLException e) {
            System.out.println("Error during signup: " + e.getMessage());
        }
    }

    private static void signin(Connection connection) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Signin");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (validateUser(connection, username, password)) {
                System.out.println("Login successful! Welcome, " + username + "!");
                // Implement additional functionalities for logged-in users
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Error during signin: " + e.getMessage());
        }
    }

    private static boolean isUsernameTaken(Connection connection, String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the username exists
            }
        }
    }

    private static void addUser(Connection connection, String username, String password) throws SQLException {
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
    }

    private static boolean validateUser(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the username and password match
            }
        }
    }
}

