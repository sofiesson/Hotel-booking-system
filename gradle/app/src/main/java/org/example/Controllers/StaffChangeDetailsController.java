package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Databases.DatabaseUser;
import javafx.scene.control.*;

import org.example.Logic.*;

public class StaffChangeDetailsController {

    @FXML
    private TextField usernameField, firstnameField, lastnameField, passwordField;

    //@FXML
    //private ComboBox<String> locationComboBox;

    @FXML
    private CheckBox isAdminCheckBox, isReceptionCheckBox;

    @FXML
    private Text feedbackText;


    private Staff currentStaff;
    private AdminOptionsController adminOptionsController;

    public void initializeWithStaff(Staff staff, AdminOptionsController adminController) {
        this.currentStaff = staff;
        this.adminOptionsController = adminController;

        // Fyll i fälten med befintliga detaljer
        usernameField.setText(staff.getUsername());
        firstnameField.setText(staff.getFirstName());
        lastnameField.setText(staff.getLastName());
        passwordField.setText(staff.getPassword());
        //locationComboBox.setValue(staff.getLocation());
        //locationComboBox.setVisible(false);

      

        // Hämta och fyll ComboBox med platser från databasen
        //DatabaseUser database = new DatabaseUser();
        //List<String> locations = database.getAllLocations(staff); // Implementera denna metod i DatabaseUser
        //locationComboBox.setItems(FXCollections.observableArrayList(locations));
    }

    @FXML
    private void handleSaveChanges() {
        DatabaseUser database = new DatabaseUser();

        String oldUsername = currentStaff.getUsername(); // Det ursprungliga användarnamnet
        String newUsername = usernameField.getText();    // Det nya användarnamnet
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String password = passwordField.getText();
        int location = currentStaff.getLocationID();
        boolean isAdmin = isAdminCheckBox.isSelected();
        boolean isReceptionStaff = isReceptionCheckBox.isSelected();

        String role;
        if (isReceptionStaff){
            role = "Receptionist";
        }
        else {  // vi har bara två roller på hotellet
            role = "Admin";
        }

        boolean usernameUpdated = true;

        
        if (!oldUsername.equals(newUsername)) {
            usernameUpdated = database.updateUsername(oldUsername, newUsername);
        }

        if (usernameUpdated) {
            currentStaff.setUsername(newUsername);

            Staff updatedStaff = new Staff(newUsername, firstname, lastname, role, password, location);
            boolean detailsUpdated = database.updateStaff(updatedStaff);

            if (detailsUpdated) {
                feedbackText.setText("Update successful!");
                feedbackText.setStyle("-fx-fill: green;"); 
                adminOptionsController.updateStaffList(); 
                Stage stage = (Stage) usernameField.getScene().getWindow();
                
            } else {
                feedbackText.setText("Update unsuccessful");
                feedbackText.setStyle("-fx-fill: red;");
                System.out.println("Failed to update staff details.");
            }
        } else {
            feedbackText.setText("Update unsuccessful");
            feedbackText.setStyle("-fx-fill: red;");
            System.out.println("Failed to update username.");
        }
    }
}