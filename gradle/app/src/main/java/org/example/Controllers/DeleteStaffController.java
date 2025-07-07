package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;

import org.example.Databases.DatabaseUser;

public class DeleteStaffController {

    @FXML
    private TextField usernameField;

    @FXML
    private Text deleteFeedback;

    @FXML
    private Button deleteButton;

    private AdminOptionsController adminController; 

    
    public void setAdminController(AdminOptionsController adminController) {
        this.adminController = adminController;
    }

    @FXML
    private void handleDeleteButtonClick() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            deleteFeedback.setText("Enter a username to delete.");
            deleteFeedback.setStyle("-fx-fill: red;");
            return;
        }

        DatabaseUser database = new DatabaseUser();
        boolean success = database.deleteStaffByUsername(username);

        if (success) {
            deleteFeedback.setText("Staff deleted successfully.");
            deleteFeedback.setStyle("-fx-fill: green;");

            if (adminController != null) {
                adminController.updateStaffList(); 
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdmin/adminOptions.fxml"));
                Parent root = loader.load();
                AdminOptionsController adminController = loader.getController();
                adminController.updateStaffList();
            } catch (IOException e) {
                deleteFeedback.setText("Error loading Admin Options.");
                e.printStackTrace();
            }
        } else {
            deleteFeedback.setText("User not found or could not be deleted.");
            deleteFeedback.setStyle("-fx-fill: red;");
        }
    }
}
