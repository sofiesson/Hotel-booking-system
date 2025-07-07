package org.example.Controllers;

import org.example.Logic.Room;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;

public class ChangeRoomDetailsController {

    @FXML
    private TextField selectedRoomToChange;
    @FXML
    private ComboBox<String> changeRoomType;
    @FXML
    private TextField changeBeds;
    @FXML
    private TextField changeRoomSize;
    @FXML
    private TextField changePrice;
    @FXML
    private Text changeFeedback;

    private UserMenuController userMenuController;

    @FXML
    private void initialize() {
        changeRoomType.getItems().addAll("Standard", "Familj", "Deluxe");
    }

    @FXML
    private void handleChangeSelectedRoom() {
        changeFeedback.setFill(Color.RED);

        String roomNumberText = selectedRoomToChange.getText();
        if (roomNumberText.isEmpty()) {
            changeFeedback.setText("Please enter the Room ID to change");
            return;
        }

        try {
            int roomNumber = Integer.parseInt(roomNumberText);
            Room room = new Room();
            Room.RoomDetails roomToChange = room.getAllRoomDetails().stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);

            if (roomToChange == null) {
                changeFeedback.setText("Room with the provided ID does not exist");
                return;
            }

            int locationID = roomToChange.getLocationID();

            if (changeRoomType.getValue() != null) {
                String newRoomType = changeRoomType.getValue().trim();
            
                if (!newRoomType.equalsIgnoreCase("standard") && 
                    !newRoomType.equalsIgnoreCase("deluxe") && 
                    !newRoomType.equalsIgnoreCase("familj")) {
            
                    changeFeedback.setText("Room Type must be either 'standard', 'familj' or 'deluxe'");
                    return;
                }
            
                room.setType(newRoomType, roomNumber, locationID);
            } else {
                changeFeedback.setText("Please select a room type.");
            }
            

            if (!changeBeds.getText().isEmpty()) {
                try {
                    int newBeds = Integer.parseInt(changeBeds.getText());
                    room.setBeds(roomNumber, locationID, newBeds);
                } catch (NumberFormatException e) {
                    changeFeedback.setText("Number of Beds must be an integer");
                    return;
                }
            }

            if (!changeRoomSize.getText().isEmpty()) {
                try {
                    int newSize = Integer.parseInt(changeRoomSize.getText());
                    room.setSize(newSize, roomNumber, locationID);
                } catch (NumberFormatException e) {
                    changeFeedback.setText("Room Size must be an integer");
                    return;
                }
            }

            if (!changePrice.getText().isEmpty()) {
                try {
                    float newPrice = Float.parseFloat(changePrice.getText());
                    room.setPrice(newPrice, roomNumber, locationID);
                } catch (NumberFormatException e) {
                    changeFeedback.setText("Price must be a valid number");
                    return;
                }
            }

            changeFeedback.setFill(Color.GREEN);
            changeFeedback.setText("Room details updated!");
            userMenuController.openRoomsMenu();
        } catch (NumberFormatException e) {
            changeFeedback.setText("Invalid input: Please enter valid numbers for numeric fields");
        } catch (Exception e) {
            changeFeedback.setText("Error updating room: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setRoomDetails(Room.RoomDetails roomDetails) {
        selectedRoomToChange.setText(String.valueOf(roomDetails.getRoomNumber()));
        changeRoomType.setPromptText(roomDetails.getRoomType());
        changeBeds.setText(String.valueOf(roomDetails.getBeds()));
        changeRoomSize.setText(String.valueOf(roomDetails.getSize()));
        changePrice.setText(String.valueOf(roomDetails.getPrice()));
    }
    

    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
}
