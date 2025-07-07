package org.example.Controllers;

import org.example.Logic.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;



public class DeleteCustomerController {

    @FXML
    private TextField usernameField;

    @FXML
    private Text deleteFeedback;

    @FXML
    private Button deleteButton;
    
    private UserMenuController userMenuController;

    @FXML
    private void initialize() {
        deleteButton.setOnAction(event -> deleteCustomer());
    }

    @FXML
    private void deleteCustomer() {
        
        String email = usernameField.getText().trim();

         if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            deleteFeedback.setText("Invalid Email Format");
            deleteFeedback.setFill(Color.RED);
            return;
        }

        Customer customer = new Customer();

        if (customer.deleteCustomer(email)) {
            deleteFeedback.setText("Customer deleted successfully.");
            deleteFeedback.setFill(Color.GREEN);
            usernameField.clear();
        } else {
            deleteFeedback.setText("Customer not found or could not be deleted.");
            deleteFeedback.setFill(Color.RED);
        }
    }

    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
}
