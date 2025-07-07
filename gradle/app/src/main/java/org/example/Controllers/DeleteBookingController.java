package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.Databases.DatabaseBooking;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class DeleteBookingController {

    @FXML
    private TextField usernameField; 

    @FXML
    private Text deleteFeedback;

    @FXML
    private Button delete;

    @FXML
    private void initialize() {
        //deleteButton.setOnAction(event -> deleteBooking());
    }


    private ManageBookingsController manageBookingsController;
    public void setManageBookingsController(ManageBookingsController controller) {
        this.manageBookingsController = controller;
    }


    @FXML
    private void deleteBooking() {
        String bookingId = usernameField.getText().trim();

        if (bookingId.isEmpty()) {
            deleteFeedback.setText("Please enter a valid Booking ID.");
            deleteFeedback.setStyle("-fx-fill: red;");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this booking?");
        alert.setContentText("You cannot undo this action.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DatabaseBooking dbUser = new DatabaseBooking();
                boolean success = dbUser.deleteBooking(bookingId);

                if (success) {
                    deleteFeedback.setText("Booking deleted successfully.");
                    deleteFeedback.setStyle("-fx-fill: green;");
                    usernameField.clear();
                    if (manageBookingsController != null) {
                        manageBookingsController.refreshTableBookings();
                    }
                } else {
                    deleteFeedback.setText("Booking not found or could not be deleted.");
                    deleteFeedback.setStyle("-fx-fill: red;");
                }
            } else {
                deleteFeedback.setText("Deletion cancelled.");
                deleteFeedback.setStyle("-fx-fill: orange;");
            }
        });
    }

}