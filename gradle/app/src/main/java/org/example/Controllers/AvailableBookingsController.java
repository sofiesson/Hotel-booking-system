package org.example.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.StartMenu;
import org.example.Logic.IncompleteBooking;
import org.example.Logic.Room;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import org.example.Databases.DatabaseBooking;


public class AvailableBookingsController {

    @FXML
    private TableView<Room.RoomDetails> availableRoomsTable;
    @FXML
    private TableColumn<Room.RoomDetails, String> availableRoomIDColumn;
    @FXML
    private TableColumn<Room.RoomDetails, String> availableRoomNumberColumn;
    @FXML
    private TableColumn<Room.RoomDetails, String> availableRoomTypeColumn;
    @FXML
    private TableColumn<Room.RoomDetails, Integer> availableRoomBedsColumn;
    @FXML
    private TableColumn<Room.RoomDetails, Double> availableRoomSizeColumn;
    @FXML
    private TableColumn<Room.RoomDetails, Double> availableRoomPriceColumn;
    @FXML
    private Pane contentPane;
    @FXML   
    private TextField peopleAmountField;
    @FXML
    private TextField customerEmailField;
    @FXML
    private CheckBox paidYesCheckBox;
    @FXML
    private CheckBox paidNoCheckBox;

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
        availableRoomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        availableRoomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        availableRoomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        availableRoomBedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));
        availableRoomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        availableRoomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));



        Room room = new Room();
        List<Room.RoomDetails> rooms = room.getAllRoomDetails();
        availableRoomsTable.setItems(FXCollections.observableArrayList(rooms));
        fx = new StartMenu();
    } 
    
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private String peopleAmountText;

    @FXML
    private Text feedback;

        
        @FXML
        private void showAvailableRooms() {
            System.out.println("showAvailableRooms()");
            if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                System.out.println("Error: One or both DatePickers have no value.");
                feedback.setText("Choose start date and end date from the calander");
                feedback.setFill(Color.RED);
                return;
            }
    
            String startDate = startDatePicker.getValue().toString();
            String endDate = endDatePicker.getValue().toString();
            System.out.println("Searching rooms from " + startDate + " to " + endDate);
        
    
            DatabaseBooking databasebooking = new DatabaseBooking();
            ArrayList<String> availableRoomNumbers = databasebooking.roomsAvailableIntervall(startDate, endDate);
        
            if (availableRoomNumbers.isEmpty()) {
                System.out.println("No available rooms for the selected dates.");

                availableRoomsTable.setItems(FXCollections.observableArrayList());
                feedback.setText("No available rooms for the selected dates");
                feedback.setFill(Color.RED);
                return;
            }
    
            List<Room.RoomDetails> availableRooms = new ArrayList<>();
    
            Room room = new Room();
        
            for (String roomNumber : availableRoomNumbers) {
                List<Room.RoomDetails> allRoomDetails = room.getAllRoomDetails();
        
                for (Room.RoomDetails roomDetails : allRoomDetails) {
                    if (String.valueOf(roomDetails.getRoomNumber()).equals(roomNumber)) {
                        availableRooms.add(roomDetails);
                        break;
                    }
                }
            }
    
            availableRoomsTable.setItems(FXCollections.observableArrayList(availableRooms));
            System.out.println("Rooms loaded: " + availableRooms.size());
        }


        @FXML
        private void checkBookButton(ActionEvent event){
            Room.RoomDetails selectedRoom =  availableRoomsTable.getSelectionModel().getSelectedItem();
            if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                System.out.println("Error: One or both DatePickers have no value.");
                feedback.setText("Choose start date and end date from the calander");
                feedback.setFill(Color.RED);
                return;
            }

            if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                System.out.println("Error: One or both DatePickers have no value.");
                feedback.setText("Select an appropriate date range");
                feedback.setFill(Color.RED);
                return;
            }

            if (selectedRoom == null){
                feedback.setText("No selected room");
                feedback.setFill(Color.RED);
                return;
            }

            openAddBookingWindow();
        }

        
        @FXML
private void openAddBookingWindow() {
    

    try {
        // Ladda FXML-filen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/addbooking.fxml"));
        Parent root = loader.load();

        // Hämta AddBookingController
        AddBookingController addBookingController = loader.getController();

        // Skapa ett nytt fönster
        Stage stage = new Stage();
        stage.setTitle("Add Booking");
        stage.setScene(new Scene(root));

        // Skicka bokningsinformation (om det behövs)
        Room.RoomDetails selectedRoom =  availableRoomsTable.getSelectionModel().getSelectedItem();
        LocalDate checkIn = startDatePicker.getValue();
        LocalDate checkOut = endDatePicker.getValue();

        int roomid = selectedRoom.getRoomId();
        int roomNumber = selectedRoom.getRoomNumber();
        String roomType = selectedRoom.getRoomType();
        int beds = selectedRoom.getBeds();
        int size = selectedRoom.getSize();
        double price = selectedRoom.getPrice();
        IncompleteBooking incompleteBooking = new IncompleteBooking(checkIn, checkOut, roomid, roomNumber, roomType, beds, size, price);

       // IncompleteBooking booking = new IncompleteBooking(checkIn, checkOut, roomid, roomNumber, roomType, beds, size, price);
        addBookingController.setBookingDetails(incompleteBooking);
        addBookingController.setStage(stage);

        // Visa fönstret
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading Add Booking window: " + e.getMessage());
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