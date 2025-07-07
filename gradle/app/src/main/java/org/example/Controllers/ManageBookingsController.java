package org.example.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.StartMenu;
import org.example.Logic.Booking;
import org.example.Logic.Booking.BookingDetails;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.Databases.DatabaseBooking;

public class ManageBookingsController {

    @FXML
    private TableView<BookingDetails> bookingsTable;
    @FXML
    private TableColumn<BookingDetails, Integer> bookingIDColumn;
    @FXML
    private TableColumn<BookingDetails, String> checkInColumn;
    @FXML
    private TableColumn<BookingDetails, String> checkOutColumn;
    @FXML
    private TableColumn<BookingDetails, Integer> peopleAmountColumn;
    @FXML
    private TableColumn<BookingDetails, Integer> roomAmountColumn;
    @FXML
    private TableColumn<BookingDetails, String> roomTypeColumn;
    @FXML
    private TableColumn<BookingDetails, String> customerEmailColumn;
    @FXML
    private TableColumn<BookingDetails, String> bookingRoomNumberColumn;
    @FXML
    private TableColumn<BookingDetails, Boolean> paidColumn;
    @FXML
    private TextField searchEmailField;
    @FXML
    private Pane contentPane;

     @FXML
    private DatePicker datePicker;  // Lägg till DatePicker från FXML

    private DatabaseBooking database = new DatabaseBooking();

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
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        checkInColumn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        peopleAmountColumn.setCellValueFactory(new PropertyValueFactory<>("peopleAmount"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        bookingRoomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));

        Booking booking = new Booking();
        List<BookingDetails> bookings = booking.getAllBookingDetails();
        bookingsTable.setItems(FXCollections.observableArrayList(bookings));

        fx = new StartMenu();
    }

    @FXML
    private void searchForEmail(){
        String email = searchEmailField.getText().trim();
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        checkInColumn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        peopleAmountColumn.setCellValueFactory(new PropertyValueFactory<>("peopleAmount"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        bookingRoomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));

        Booking booking = new Booking();
        List<BookingDetails> bookings = booking.getAllBookingsForCustomer(email);
        bookingsTable.setItems(FXCollections.observableArrayList(bookings));

        fx = new StartMenu();
    }


    @FXML
    private void showBookingsForDay() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            //String day = selectedDate.toString(); // Konvertera till YYYY-MM-DD format
            LocalDateTime localDateTime = selectedDate.atStartOfDay();
            List<String> bookingIDs = database.getAllBookingsForADay(localDateTime);
    
            if (bookingIDs != null && !bookingIDs.isEmpty()) {
                List<BookingDetails> bookingsDetails = new ArrayList<>();
    
                // För varje boknings-ID, hämta detaljerna
                for (String bookingID : bookingIDs) {

                    BookingDetails bookingDetail = database.getBookingDetailsByID(bookingID);
                    if (bookingDetail != null) {
                        bookingsDetails.add(bookingDetail);
                    }
                }
  
                bookingsTable.setItems(FXCollections.observableArrayList(bookingsDetails));
            } else {
                bookingsTable.getItems().clear();
                System.out.println("Inga bokningar hittades för den valda dagen.");
            }
        } else {
            bookingsTable.getItems().clear();
            System.out.println("Välj ett datum för att se bokningar.");
        }
    }
/* 
    @FXML
    private void 

*/
    @FXML
    private void openDeleteBookingWindow() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/DeleteBooking.fxml"));
            Parent root = loader.load();
    
            DeleteBookingController deleteBookingController = loader.getController();
            deleteBookingController.setManageBookingsController(this);
            Stage deleteStage = new Stage();
            deleteStage.setTitle("Delete Booking");
            deleteStage.setScene(new Scene(root));
            deleteStage.show();
        } catch (IOException e) {
            System.out.println("Error loading DeleteBooking.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void openChangeBookingWindow() {
        Booking.BookingDetails selectedBooking = bookingsTable.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/changeBookingDetails.fxml"));
            Parent changeBookingDetailsContent = loader.load();
            ChangeBookingController changeBookingController = loader.getController();
            changeBookingController.setUserMenuController(userMenuController);
            changeBookingController.setBookingDetails(selectedBooking);
            Stage stage = new Stage();
            stage.setScene(new Scene(changeBookingDetailsContent));
            stage.setTitle("Change Booking Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void refreshTableBookings(){
        Booking booking = new Booking();
        List<BookingDetails> bookings = booking.getAllBookingDetails();
        bookingsTable.setItems(FXCollections.observableArrayList(bookings));
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