package org.example.Controllers;

import org.example.Logic.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ChangeCustomerController {

    @FXML
    private TextField selectedCustomerToChange;
    @FXML
    private TextField changeCustomerFirstName;
    @FXML
    private TextField changeCustomerLastName;
    @FXML
    private ComboBox<String> changeCustomerPaymentMethod;
    @FXML
    private TextField changeCustomerPostCode;
    @FXML
    private TextField changeCustomerAddress;
    @FXML
    private TextField changeCustomerPhoneNumber;
    @FXML
    private TextField changeCustomerEmail;
    @FXML
    private Text changeCustomerFeedback;
    @FXML
    private Button changeCustomerDetailsButton;

    private UserMenuController userMenuController;

    @FXML
    private void initialize() {
        changeCustomerPaymentMethod.getItems().addAll("Card", "Swish", "Cash", "Bank Transfer");
    }

    @FXML
    private void handleChangeCustomerDetails() {
        changeCustomerFeedback.setFill(Color.RED);
        
        String customerEmail = selectedCustomerToChange.getText().trim();
        if (customerEmail.isEmpty()) {
            changeCustomerFeedback.setText("Please enter the customer's email to change");
            return;
        }
        
        Customer customer = new Customer();
        Customer.CustomerDetails customerToChange = customer.getAllCustomerDetails().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(customerEmail))
                .findFirst()
                .orElse(null);
        
        if (customerToChange == null) {
            changeCustomerFeedback.setText("Customer with the provided email does not exist");
            return;
        }
        
        if (!changeCustomerFirstName.getText().isEmpty()) {
            customer.changeCustomerFirstName(customerEmail, changeCustomerFirstName.getText().trim());
        }
        
        if (!changeCustomerLastName.getText().isEmpty()) {
            customer.changeCustomerLastName(customerEmail, changeCustomerLastName.getText().trim());
        }
        
        if (changeCustomerPaymentMethod.getValue() != null) {
            customer.changeCustomerPaymentMethod(customerEmail, changeCustomerPaymentMethod.getValue());
        }
        
        if (!changeCustomerPostCode.getText().isEmpty()) {
            if (!changeCustomerPostCode.getText().matches("\\d{5}")) {
                changeCustomerFeedback.setText("Invalid Post Code (5 digits required)");
                return;
            }
            customer.changeCustomerPostCode(customerEmail, changeCustomerPostCode.getText().trim());
        }
        
        if (!changeCustomerAddress.getText().isEmpty()) {
            customer.changeCustomerAddress(customerEmail, changeCustomerAddress.getText().trim());
        }
        
        if (!changeCustomerPhoneNumber.getText().isEmpty()) {
            if (!changeCustomerPhoneNumber.getText().matches("\\d{10}")) {
                changeCustomerFeedback.setText("Invalid Phone Number (10 digits required)");
                return;
            }
            customer.changeCustomerPhoneNumber(customerEmail, changeCustomerPhoneNumber.getText().trim());
        }
        
        if (!changeCustomerEmail.getText().isEmpty()) {
            if (!changeCustomerEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                changeCustomerFeedback.setText("Invalid Email Format");
                return;
            }
            customer.changeCustomerEmail(customerEmail, changeCustomerEmail.getText().trim());
        }
        
        changeCustomerFeedback.setFill(Color.GREEN);
        changeCustomerFeedback.setText("Customer details updated!");
        
        if (userMenuController != null) {
            userMenuController.openCustomersMenu();
        }
    }
    
    public void setCustomerDetails(Customer.CustomerDetails customerDetails) {
        selectedCustomerToChange.setText(customerDetails.getEmail());
        changeCustomerFirstName.setText(customerDetails.getFirstName());
        changeCustomerLastName.setText(customerDetails.getLastName());
        changeCustomerPaymentMethod.setPromptText(customerDetails.getPaymentMethod());
        changeCustomerPostCode.setText(customerDetails.getPostCode());
        changeCustomerAddress.setText(customerDetails.getAddress());
        changeCustomerPhoneNumber.setText(customerDetails.getPhoneNumber());
        changeCustomerEmail.setText(customerDetails.getEmail());
    }
    
    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
}
