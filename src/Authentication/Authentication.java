/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Authentication;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ntando nxumalo st10456704
 */
public class Authentication {

    String usernameInput;
    String passwordInput;
    String firstName;
    String lastName;
    String password;
    String username;

    // Method to check if the username is valid
    public boolean isUsernameValid() {
        boolean isValid = false;
        if (username.length() <= 5) { // Username should be no more than 5 characters
            for (int i = 0; i < username.length(); i++) {
                if (username.charAt(i) == '_') { // Username should contain an underscore
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    // Method to check if the password is complex
    public boolean isPasswordComplex() {
        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        if (password.length() >= 8) { // Password should be at least 8 characters long
            for (int i = 0; i < password.length(); i++) {
                char ch = password.charAt(i);
                if (Character.isUpperCase(ch)) {
                    hasCapitalLetter = true;
                } else if (Character.isDigit(ch)) {
                    hasNumber = true;
                } else if (!Character.isLetterOrDigit(ch)) {
                    hasSpecialChar = true;
                }
            }
        }
        return hasCapitalLetter && hasNumber && hasSpecialChar;
    }

    // Method to register a user
    public String registerUser() {
        StringBuilder message = new StringBuilder();
        if (isUsernameValid()) {
            message.append("Username successfully captured.\n");
        } else {
            message.append("Username is not correctly formatted, please ensure that your Username contains an underscore and is no more than 5 characters in length.\n");
        }
        if (isPasswordComplex()) {
            message.append("Password successfully captured.\n");
        } else {
            message.append("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.\n");
        }
        if (isUsernameValid() && isPasswordComplex()) {
            message.append("The two above conditions have been met and the user has been registered successfully.\n");
        } else {
            if (!isPasswordComplex()) {
                message.append("The Password does not meet the complexity requirements.\n");
            }
            if (!isUsernameValid()) {
                message.append("The username is incorrectly formatted.\n");
            }
        }
        return message.toString();
    }

    // Method to login a user
    public boolean loginUser() {
        return username.equals(usernameInput) && password.equals(passwordInput);
    }

    // Method to return the login status
    public String returnLoginStatus() {
        if (loginUser()) {
            return "Successful login\nWelcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Failed login\nUsername or Password incorrect, please try again";
        }
    }

    public static void main(String[] args) {
        Authentication auth = new Authentication();
        TaskManager taskManager = new TaskManager();

        // User registration
        JOptionPane.showMessageDialog(null, "Register..........");

        auth.firstName = JOptionPane.showInputDialog("Enter First Name:");
        auth.lastName = JOptionPane.showInputDialog("Enter Last Name:");

        do {
            auth.username = JOptionPane.showInputDialog("Enter Username:");
            auth.password = JOptionPane.showInputDialog("Enter Password:");
            JOptionPane.showMessageDialog(null, auth.registerUser());
        } while (!auth.isUsernameValid() || !auth.isPasswordComplex());

        // User login
        JOptionPane.showMessageDialog(null, "Login..........");

        do {
            auth.usernameInput = JOptionPane.showInputDialog("Enter Username:");
            auth.passwordInput = JOptionPane.showInputDialog("Enter Password:");
            JOptionPane.showMessageDialog(null, auth.returnLoginStatus());
        } while (!auth.loginUser());

        // Post-login actions
        JOptionPane.showMessageDialog(null, "Welcome To EasyKanban");

        int choice;
        do {
            String userInput = JOptionPane.showInputDialog("Choose an option:\n1. Add tasks\n2. Show report\n3. Quit");
            choice = Integer.parseInt(userInput);

            switch (choice) {
                case 1:
                    int numTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks:"));
                    int totalHours = 0;
                    for (int i = 0; i < numTasks; i++) {
                        String taskName = JOptionPane.showInputDialog("Enter task name:");
                        String taskDescription = JOptionPane.showInputDialog("Enter task description:");
                        String devFirstName = JOptionPane.showInputDialog("Enter developer's first name:");
                        String devLastName = JOptionPane.showInputDialog("Enter developer's last name:");
                        int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter task duration:"));
                        String taskID = taskManager.createTaskID(taskName, i, devLastName);
                        String taskStatus = "";
                        int option = Integer.parseInt(JOptionPane.showInputDialog("Please choose the status of this task from the three options.\n1.To Do\n2.Doing\n3.Done"));
                        switch (option) {
                            case 1:
                                taskStatus = "To Do";
                                break;
                            case 2:
                                taskStatus = "Doing";
                                break;
                            case 3:
                                taskStatus = "Done";
                                break;
                        }
                        taskManager.addTask(taskName, taskDescription, devFirstName + " " + devLastName, taskID, taskDuration, taskStatus);
                        String taskDetails = taskManager.printTaskDetails(taskStatus, devFirstName, devLastName, i, taskName, taskDescription, taskID, taskDuration);
                        JOptionPane.showMessageDialog(null, taskDetails);
                        JOptionPane.showMessageDialog(null, "Task successfully captured.");
                        totalHours += taskDuration;
                    }
                    JOptionPane.showMessageDialog(null, "Total hours: " + totalHours);
                    break;
                case 2:
                    int reportChoice;
                    do {
                        reportChoice = Integer.parseInt(JOptionPane.showInputDialog("Show Report Options:\n1. Display all tasks\n2. Display done tasks\n3. Display longest task\n4. Search task by name\n5. Search tasks by developer\n6. Delete task\n7. Go back"));
                        switch (reportChoice) {
                            case 1:
                                taskManager.showTaskReport();
                                break;
                            case 2:
                                taskManager.displayDoneTasks();
                                break;
                            case 3:
                                taskManager.displayLongestTask();
                                break;
                            case 4:
                                String searchTaskName = JOptionPane.showInputDialog("Enter task name to search:");
                                taskManager.searchTaskByName(searchTaskName);
                                break;
                            case 5:
                                String searchDeveloper = JOptionPane.showInputDialog("Enter developer name to search tasks:");
                                taskManager.searchTasksByDeveloper(searchDeveloper);
                                break;
                            case 6:
                                String deleteTaskName = JOptionPane.showInputDialog("Enter task name to delete:");
                                taskManager.deleteTask(deleteTaskName);
                                break;
                            case 7:
                                JOptionPane.showMessageDialog(null, "Returning to main menu.");
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                                break;
                        }
                    } while (reportChoice != 7);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Exiting the application.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }
}

// TaskManager class to manage tasks
class TaskManager {
    private final List<String> developers = new ArrayList<>();
    private final List<String> taskNames = new ArrayList<>();
    private final List<String> taskIDs = new ArrayList<>();
    private final List<Integer> taskDurations = new ArrayList<>();
    private final List<String> taskStatuses = new ArrayList<>();

    // Method to create a task ID
    public String createTaskID(String taskName, int taskNumber, String developerLastName) {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerLastName.substring(developerLastName.length() - 3).toUpperCase();
    }

    // Method to print task details
    public String printTaskDetails(String taskStatus, String devFirstName, String devLastName, int taskNumber, String taskName, String taskDescription, String taskID, int taskDuration) {
        StringBuilder taskDetails = new StringBuilder();
        taskDetails.append("Task Status: ").append(taskStatus).append("\n");
        taskDetails.append("Developer Details: ").append(devFirstName).append(" ").append(devLastName).append("\n");
        taskDetails.append("Task Number: ").append(taskNumber).append("\n");
        taskDetails.append("Task Name: ").append(taskName).append("\n");
        taskDetails.append("Task Description: ").append(taskDescription).append("\n");
        taskDetails.append("Task ID: ").append(taskID).append("\n");
        taskDetails.append("Task Duration: ").append(taskDuration).append(" hours\n");
        return taskDetails.toString();
    }

    // Method to add a task
    public void addTask(String taskName, String taskDescription, String developer, String taskID, int taskDuration, String taskStatus) {
        taskNames.add(taskName);
        developers.add(developer);
        taskIDs.add(taskID);
        taskDurations.add(taskDuration);
        taskStatuses.add(taskStatus);
    }

    // Method to show task report
    public void showTaskReport() {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < taskNames.size(); i++) {
            report.append("Task Name: ").append(taskNames.get(i)).append("\n");
            report.append("Developer: ").append(developers.get(i)).append("\n");
            report.append("Task ID: ").append(taskIDs.get(i)).append("\n");
            report.append("Task Duration: ").append(taskDurations.get(i)).append(" hours\n");
            report.append("Task Status: ").append(taskStatuses.get(i)).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }

    // Method to display tasks with status 'Done'
    public void displayDoneTasks() {
        StringBuilder doneTasks = new StringBuilder();
        doneTasks.append("Tasks with status 'Done':\n");
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskStatuses.get(i).equals("Done")) {
                doneTasks.append("Developer: ").append(developers.get(i)).append(", ");
                doneTasks.append("Task Name: ").append(taskNames.get(i)).append(", ");
                doneTasks.append("Task Duration: ").append(taskDurations.get(i)).append(" hours\n");
            }
        }
        JOptionPane.showMessageDialog(null, doneTasks.toString());
    }

    // Method to display the longest task
    public void displayLongestTask() {
        int maxDuration = 0;
        int maxIndex = 0;
        for (int i = 0; i < taskDurations.size(); i++) {
            if (taskDurations.get(i) > maxDuration) {
                maxDuration = taskDurations.get(i);
                maxIndex = i;
            }
        }
        JOptionPane.showMessageDialog(null, "Task with longest duration:\nDeveloper: " + developers.get(maxIndex) + ", Duration: " + maxDuration + " hours");
    }

    // Method to search a task by name
    public void searchTaskByName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskNames.get(i).equals(name)) {
                result.append("Task Name: ").append(taskNames.get(i)).append("\n");
                result.append("Developer: ").append(developers.get(i)).append("\n");
                result.append("Task Status: ").append(taskStatuses.get(i)).append("\n");
                break;
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    // Method to search tasks by developer
    public void searchTasksByDeveloper(String developer) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < developers.size(); i++) {
            if (developers.get(i).contains(developer)) {
                result.append("Task Name: ").append(taskNames.get(i)).append(", ");
                result.append("Task Status: ").append(taskStatuses.get(i)).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    // Method to delete a task by name
    public void deleteTask(String taskName) {
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskNames.get(i).equals(taskName)) {
                taskNames.remove(i);
                developers.remove(i);
                taskIDs.remove(i);
                taskDurations.remove(i);
                taskStatuses.remove(i);
                JOptionPane.showMessageDialog(null, "Task '" + taskName + "' deleted successfully.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Task '" + taskName + "' not found.");
    }
} 
    
    

