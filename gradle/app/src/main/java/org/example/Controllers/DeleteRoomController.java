package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;

import org.example.Databases.DatabaseRoom;
import org.example.Logic.Room;

public class DeleteRoomController {

    @FXML
    private TextField deleteRoomNumber;
    @FXML
    private Text deleteFeedback;

    private UserMenuController userMenuController;

    @FXML
    private void handleDeleteSelectedRoom() {
        try {
            String deleteNumberText = deleteRoomNumber.getText().trim();
            
            if (!deleteNumberText.matches("\\d+")) {
                deleteFeedback.setFill(Color.RED);
                deleteFeedback.setText("Invalid input. Enter a valid room number.");
                return;
            }
            int deleteNumber = Integer.parseInt(deleteNumberText);
            ArrayList<String> existingRoomNumbers = new DatabaseRoom().getAllRoomNumbers();
            
            if (!existingRoomNumbers.contains(String.valueOf(deleteNumber))) {
                deleteFeedback.setFill(Color.RED);
                deleteFeedback.setText("Room number does not exist.");
                return;
            }
    
            Room room = new Room();
            boolean success = room.deleteRoom(deleteNumber, 1);
    
            if (success) {
                deleteFeedback.setFill(Color.GREEN);
                deleteFeedback.setText("Room deleted successfully!");
                userMenuController.openRoomsMenu();
            } else {
                deleteFeedback.setFill(Color.RED);
                deleteFeedback.setText("Failed to delete the room. Please try again.");
            }
        } catch (Exception e) {
            deleteFeedback.setFill(Color.RED);
            deleteFeedback.setText("Unexpected error occurred.");
            e.printStackTrace();
        }
    }

    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
}
