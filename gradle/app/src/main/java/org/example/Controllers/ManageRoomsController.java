package org.example.Controllers;

import java.io.IOException;
import java.util.List;

import org.example.StartMenu;
import org.example.Logic.Room;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;




public class ManageRoomsController {

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
    private Button addRoomButton;
    @FXML
    private Button deleteRoomButton;
    @FXML
    private Button changeDetailsButton;
    @FXML
    private Pane contentPane;

    private UserMenuController userMenuController;

    private String username;
    private Stage stage;
    private String userName;
    private StartMenu fx;

    public void setUsername(String username) {
        this.username = username;
    }


    @FXML
    public void initialize() {
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomBedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));
        roomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        Room room = new Room();
        List<Room.RoomDetails> rooms = room.getAllRoomDetails();
        roomsTable.setItems(FXCollections.observableArrayList(rooms));
        fx = new StartMenu();
    }


    @FXML
    private void handleAddRoom() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/addRoom.fxml"));
            Parent addRoomContent = loader.load();

            AddRoomController addRoomController = loader.getController();
            addRoomController.setUserMenuController(userMenuController);
            Stage newStage = new Stage();
            newStage.setTitle("Add Room");
            newStage.setScene(new Scene(addRoomContent));
            newStage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }


    @FXML
    private void handleDeleteRoom() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/deleteRoom.fxml"));
            Parent deleteRoomContent = loader.load();
            
            DeleteRoomController deleteRoomController = loader.getController();
            deleteRoomController.setUserMenuController(userMenuController);

            Stage newStage = new Stage();
            newStage.setTitle("Delete Room");
            newStage.setScene(new Scene(deleteRoomContent));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleChangeDetails() {
        Room.RoomDetails selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            System.out.println("Please select a room to change");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/changeRoomDetails.fxml"));
            Parent changeRoomDetailsContent = loader.load();
            ChangeRoomDetailsController changeRoomDetailsController = loader.getController();
            changeRoomDetailsController.setUserMenuController(userMenuController);
            changeRoomDetailsController.setRoomDetails(selectedRoom);
            Stage stage = new Stage();
            stage.setScene(new Scene(changeRoomDetailsContent));
            stage.setTitle("Change Room Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewRoomBookings() {
        Room.RoomDetails selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            System.out.println("Please select a room to view bookings");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/viewRoomBookings.fxml"));
            Parent root = loader.load();

            ViewRoomBookingsController controller = loader.getController();
            controller.setRoomDetails(selectedRoom);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("View Room Bookings");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
