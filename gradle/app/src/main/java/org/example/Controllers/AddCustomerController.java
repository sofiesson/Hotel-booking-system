package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.List;

import org.example.Logic.Customer;

public class AddCustomerController {

    @FXML
    private TextField addCustomerFirstName;
    @FXML
    private TextField addCustomerLastName;
    @FXML
    private ComboBox<String> addCustomerPaymentMethod;
    @FXML
    private TextField addCustomerPostCode;
    @FXML
    private TextField addCustomerAddress;
    @FXML
    private TextField addCustomerPhoneNumber;
    @FXML
    private TextField addCustomerEmail;
    @FXML
    private Text customerFeedback;

    private UserMenuController userMenuController;

    @FXML
    public void initialize() {
        addCustomerPaymentMethod.getItems().addAll("Card", "Swish", "Cash", "Bank Transfer");
    }

    @FXML
    private void handleAddSelectedCustomer() {
        try {
            String firstName = addCustomerFirstName.getText().trim();
            String lastName = addCustomerLastName.getText().trim();
            String paymentMethod = addCustomerPaymentMethod.getValue();
            String postCode = addCustomerPostCode.getText().trim();
            String address = addCustomerAddress.getText().trim();
            String phoneNumber = addCustomerPhoneNumber.getText().trim();
            String email = addCustomerEmail.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || paymentMethod == null || paymentMethod.isEmpty() || 
                postCode.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                customerFeedback.setText("All fields must be filled");
                customerFeedback.setFill(Color.RED);
                return;
            }

            if (!postCode.matches("\\d{5}")) {
                customerFeedback.setText("Invalid Post Code (5 digits required)");
                customerFeedback.setFill(Color.RED);
                return;
            }

            if (!phoneNumber.matches("\\d{10}")) {
                customerFeedback.setText("Invalid Phone Number (10 digits required)");
                customerFeedback.setFill(Color.RED);
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                customerFeedback.setText("Invalid Email Format");
                customerFeedback.setFill(Color.RED);
                return;
            }

            if (!addCustomerPaymentMethod.getItems().contains(paymentMethod)) {
                customerFeedback.setText("Invalid Payment Method");
                customerFeedback.setFill(Color.RED);
                return;
            }

            Customer customer = new Customer();
            List<Customer.CustomerDetails> existingCustomers = customer.getAllCustomerDetails();
            for (Customer.CustomerDetails existingCustomer : existingCustomers) {
                if (existingCustomer.getEmail().equalsIgnoreCase(email)) {
                    customerFeedback.setText("Email already exists");
                    customerFeedback.setFill(Color.RED);
                    return;
                }
            }

            boolean success = customer.addCustomer(firstName, lastName, paymentMethod, postCode, address, phoneNumber, email);

            if (success) {
                customerFeedback.setFill(Color.GREEN);
                customerFeedback.setText("Customer added successfully!");
                if (userMenuController != null) {
                    userMenuController.openCustomersMenu();
                }
            } else {
                customerFeedback.setFill(Color.RED);
                customerFeedback.setText("Failed to add the customer. Please check the input");
            }
        } catch (Exception e) {
            customerFeedback.setFill(Color.RED);
            customerFeedback.setText("Unexpected error occurred");
            e.printStackTrace();
        }
    }
    
    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
}