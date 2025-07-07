package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.List;

import org.example.Databases.DatabaseRoom;
import org.example.Logic.Room;

public class AddRoomController {

    @FXML
    private Text addFeedback;
    @FXML
    private TextField selectedRoomNumber;
    @FXML
    private ComboBox<String> selectedRoomType;
    @FXML
    private TextField selectedBeds;
    @FXML
    private TextField selectedRoomSize;
    @FXML
    private TextField selectedPrice;

    private UserMenuController userMenuController;

    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }

    @FXML
    private void initialize() {
        selectedRoomType.getItems().addAll("Standard", "Deluxe", "Familj");
    }

    @FXML
    private void handleAddSelectedRoom() {
        try {
            String roomNumberText = selectedRoomNumber.getText();
            String bedsText = selectedBeds.getText();
            String sizeText = selectedRoomSize.getText();
            String priceText = selectedPrice.getText();
            String roomType = selectedRoomType.getValue();

            if (!roomNumberText.matches("\\d+")) {
                addFeedback.setText("Invalid Room Number");
                addFeedback.setFill(Color.RED);
                return;
            }
            int roomNumberDupe = Integer.parseInt(roomNumberText);

            List<Room.RoomDetails> existingRooms = new DatabaseRoom().getAllRoomDetails();
            for (Room.RoomDetails room : existingRooms) {
                if (room.getRoomNumber() == roomNumberDupe) {
                    addFeedback.setText("Duplicate Room Number");
                    addFeedback.setFill(Color.RED);
                    return;
                }
            }
            int roomNumber = Integer.parseInt(roomNumberText);

            if (!bedsText.matches("\\d+")) {
                addFeedback.setText("Invalid Beds");
                addFeedback.setFill(Color.RED);
                return;
            }
            int beds = Integer.parseInt(bedsText);

            if (!sizeText.matches("\\d+")) {
                addFeedback.setText("Invalid Room Size");
                addFeedback.setFill(Color.RED);
                return;
            }
            int size = Integer.parseInt(sizeText);

            if (!priceText.matches("\\d+(\\.\\d+)?")) {
                addFeedback.setText("Invalid Price");
                addFeedback.setFill(Color.RED);
                return;
            }
            double price = Double.parseDouble(priceText);

            Room room = new Room();
            boolean success = room.addRoom(roomNumber, 1, beds, size, price, roomType);

            if (success) {
                addFeedback.setFill(Color.GREEN);
                addFeedback.setText("Room added successfully!");
                if (userMenuController != null) {
                    userMenuController.openRoomsMenu();
                }
            } else {
                addFeedback.setFill(Color.RED);
                addFeedback.setText("Failed to add the room. Please check the input.");
            }
        } catch (Exception e) {
            addFeedback.setFill(Color.RED);
            addFeedback.setText("Unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
