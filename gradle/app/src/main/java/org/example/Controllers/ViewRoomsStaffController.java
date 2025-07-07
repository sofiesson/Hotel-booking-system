package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import org.example.Logic.*;

public class ViewRoomsStaffController {

    @FXML
    private TableView<Room.RoomDetails> roomsTable;
    @FXML
    private TableColumn<Room.RoomDetails, String> roomIDColumn;
    @FXML
    private TableColumn<Room.RoomDetails, String> roomNumberColumn;
    @FXML
    private TableColumn<Room.RoomDetails, String> roomTypeColumn;
    @FXML
    private TableColumn<Room.RoomDetails, Integer> roomBedsColumn;
    @FXML
    private TableColumn<Room.RoomDetails, Double> roomSizeColumn;
    @FXML
    private TableColumn<Room.RoomDetails, Double> roomPriceColumn;

    @FXML
    public void initialize() {
        // Initialize the table columns to display the RoomDetails properties
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomBedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));
        roomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Retrieve room data (mocked in this example)
        Room room = new Room();
        List<Room.RoomDetails> rooms = room.getAllRoomDetails();

        // Set the room details to the TableView
        roomsTable.setItems(FXCollections.observableArrayList(rooms));
    }
}

