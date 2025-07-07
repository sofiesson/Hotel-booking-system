package org.example.Controllers;

import org.example.Logic.Staff;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddStaffController {

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private CheckBox receptionStaffCheckBox;

    @FXML
    private TextField firstnameField;
    
    @FXML 
    private TextField lastnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button addButton;

    @FXML
    private TextField usernameTextField;


    @FXML
    private Text feedbackText;

    private AdminOptionsController adminController; 
    private int locationID;

    @FXML
    public void initialize() {
    }

    public void setAdminController(AdminOptionsController adminController) {
        this.adminController = adminController;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
        System.out.println("Location ID set in AddStaffController: " + locationID);  
    }    
    

    @FXML
    private void handleAddButtonAction() {
        String username = usernameTextField.getText().trim();
        String firstname = firstnameField.getText().trim();
        String lastname = lastnameField.getText().trim();
        String password = passwordField.getText().trim();

        Staff staff = new Staff();
        
        int location = locationID;
        boolean isAdmin = adminCheckBox.isSelected();
        boolean isReceptionStaff = receptionStaffCheckBox.isSelected();

        if (username.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
            feedbackText.setText("All fields must be filled in.");
            feedbackText.setStyle("-fx-fill: red;");
            return;
        }

        if (!isValidName(firstname) || !isValidName(lastname)) {
            feedbackText.setText("Firstname and Lastname can only contain letters.");
            feedbackText.setStyle("-fx-fill: red;");
            return;
        }

        if (!isAdmin && !isReceptionStaff) {
            feedbackText.setText("Select at least one role.");
            feedbackText.setStyle("-fx-fill: red;");
            return;
        }
        

        String role;
        if (isReceptionStaff){
            role = "Receptionist";
        }
        else {
            role = "Admin";
        }

        boolean staffAdded = staff.addStaff(username, firstname, lastname, role, password, location);

        if (staffAdded) {
            usernameTextField.clear();
            firstnameField.clear();
            lastnameField.clear();
            passwordField.clear();
            adminCheckBox.setSelected(false);
            receptionStaffCheckBox.setSelected(false);
            feedbackText.setText("Staff has been added!");
            feedbackText.setStyle("-fx-fill: green;");

            if (adminController != null) {
                adminController.updateStaffList();
            }
        } else {
            feedbackText.setText("Could not add staff.");
            feedbackText.setStyle("-fx-fill: red;");
        }
    }
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
