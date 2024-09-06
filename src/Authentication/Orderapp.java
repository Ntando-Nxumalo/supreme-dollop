package Authentication;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 * Order management system similar to the Student Management Application.
 * @author Ntando Nxumalo
 */
public class Orderapp {

    // List to store Order objects
    static final List<Order> orders = new ArrayList<>();
    static int orderCount = 0;

    public static void main(String[] args) {
        // Show welcome message
        JOptionPane.showMessageDialog(null, "Welcome To Spaceman's Donut");

        // Display the main menu for order management
        displayMenu();
    }

    // Display the main menu
    private static void displayMenu() {
        while (true) {
            // Show menu options to the user
            String input = JOptionPane.showInputDialog(null, """
                                                             Spaceman's Donut order application
                                                             ****************************************
                                                             (1) Add an order on donut
                                                             (2) Search for an order
                                                             (3) Delete an order
                                                             (4) Print order report
                                                             (5) Exit Application
                                                             Enter your choice:""");

            // Handle case where user clicks cancel
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Please order again next time.");
                return;
            }

            try {
                int option = Integer.parseInt(input);

                // Perform actions based on user's input
                switch (option) {
                    case 1 -> saveOrder();
                    case 2 -> searchOrder();
                    case 3 -> deleteOrder();
                    case 4 -> orderReport();
                    case 5 -> {
                        JOptionPane.showMessageDialog(null, "Please order again next time.");
                        return;
                    }
                    default -> JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }

    // Capture new order details and save
    private static void saveOrder() {
        orderCount++;  // Increment the order counter

        // Collect order details using JOptionPane
        String orderIdInput = JOptionPane.showInputDialog("Enter the order number:");
        int orderId = Integer.parseInt(orderIdInput);

        String donutType = JOptionPane.showInputDialog("Enter the donut type:");

        // Validate the order quantity
        int quantity = 0;
        while (true) {
            String quantityInput = JOptionPane.showInputDialog("Enter the quantity of donuts (must be at least 1):");
            try {
                quantity = Integer.parseInt(quantityInput);
                if (quantity >= 1) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity must be 1 or more. Please re-enter.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid quantity entered. Please re-enter.");
            }
        }

        String customerName = JOptionPane.showInputDialog("Enter the customer's name:");
        String customerEmail = JOptionPane.showInputDialog("Enter the customer's email:");

        // Create a new Order object and add it to the list
        Order order = new Order(orderId, donutType, quantity, customerName, customerEmail);
        orders.add(order);

        // Confirm the order has been saved
        JOptionPane.showMessageDialog(null, "Your order details have been saved successfully.");
    }

    // Search for an order by ID
    private static void searchOrder() {
        String searchIdInput = JOptionPane.showInputDialog("Enter the order number to search:");
        int searchId = Integer.parseInt(searchIdInput);

        // Search for the order in the list
        for (Order order : orders) {
            if (order.getOrderId() == searchId) {
                // Display the order details if found
                JOptionPane.showMessageDialog(null,
                        "ORDER NUMBER: " + order.getOrderId() + "\n" +
                        "DONUT TYPE: " + order.getDonutType() + "\n" +
                        "QUANTITY: " + order.getQuantity() + "\n" +
                        "CUSTOMER NAME: " + order.getCustomerName() + "\n" +
                        "CUSTOMER EMAIL: " + order.getCustomerEmail());
                return;
            }
        }
        // If order not found, display a message
        JOptionPane.showMessageDialog(null, "Order Number " + searchId + " not found.");
    }

    // Delete an order by ID
    private static void deleteOrder() {
        String deleteIdInput = JOptionPane.showInputDialog("Enter the order number to delete:");
        int deleteId = Integer.parseInt(deleteIdInput);

        // Search for the order to delete
        for (Order order : orders) {
            if (order.getOrderId() == deleteId) {
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete order " + order.getOrderId() + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                // If confirmed, delete the order
                if (confirmation == JOptionPane.YES_OPTION) {
                    orders.remove(order);
                    JOptionPane.showMessageDialog(null, "Order with Number " + deleteId + " has been deleted.");
                }
                return;
            }
        }
        // If order not found, display a message
        JOptionPane.showMessageDialog(null, "Order with Number " + deleteId + " not found.");
    }

    // Display all order details in a report
    private static void orderReport() {
        StringBuilder report = new StringBuilder();
        report.append("ORDER REPORT\n");
        report.append("****************************************\n");

        // Append each order's details to the report
        for (Order order : orders) {
            report.append("ORDER Number: ").append(order.getOrderId()).append("\n");
            report.append("DONUT TYPE: ").append(order.getDonutType()).append("\n");
            report.append("QUANTITY: ").append(order.getQuantity()).append("\n");
            report.append("CUSTOMER NAME: ").append(order.getCustomerName()).append("\n");
            report.append("CUSTOMER EMAIL: ").append(order.getCustomerEmail()).append("\n\n");
        }

        // Show the report in a dialog
        JOptionPane.showMessageDialog(null, report.toString());
    }
}

// Order class to represent a donut order
class Order {
    private final int orderId;
    private final String donutType;
    private final int quantity;
    private final String customerName;
    private final String customerEmail;

    public Order(int orderId, String donutType, int quantity, String customerName, String customerEmail) {
        this.orderId = orderId;
        this.donutType = donutType;
        this.quantity = quantity;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDonutType() {
        return donutType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
