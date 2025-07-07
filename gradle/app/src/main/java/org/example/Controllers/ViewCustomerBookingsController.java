package org.example.Controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import org.example.Logic.Booking;
import org.example.Logic.Customer;

import java.util.List;

public class ViewCustomerBookingsController {

    @FXML
    private Text viewCustomerBookingSelected;
    @FXML
    private TableView<Booking.BookingDetails> viewCustomerBookingsTable;
    @FXML
    private TableColumn<Booking.BookingDetails, String> viewCustomerBookingCheckInColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, String> viewCustomerBookingCheckOutColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, String> viewCustomerBookingEmailColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, Integer> viewCustomerBookingRoomNumberColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, Boolean> viewCustomerBookingPaidColumn1;

    @FXML
    private Text feedbackText;

    private Booking bookingLogic = new Booking();
    private Customer.CustomerDetails currentCustomer; 

    public void setCustomerDetails(Customer.CustomerDetails customer) {
        if (customer == null) return;

        currentCustomer = customer; 
        viewCustomerBookingSelected.setText("Booking Details for Customer: " + customer.getFirstName() + " " + customer.getLastName());

        updateTable(); 
    }

    private void updateTable() {
        if (currentCustomer == null) return;

        List<Booking.BookingDetails> bookings = bookingLogic.getAllBookingDetails();
        List<Booking.BookingDetails> filteredBookings = bookings.stream()
                .filter(b -> b.getCustomerEmail().equals(currentCustomer.getEmail()))
                .toList();

        viewCustomerBookingCheckInColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckIn().toString()));
        viewCustomerBookingCheckOutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckOut().toString()));
        viewCustomerBookingEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerEmail()));
        viewCustomerBookingPaidColumn1.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getPaid()).asObject());
        viewCustomerBookingRoomNumberColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoomID()));

        viewCustomerBookingsTable.setItems(FXCollections.observableArrayList(filteredBookings));
    }

    @FXML
    private void refreshTableViewCustomerBookings(){
        List<Booking.BookingDetails> bookings = bookingLogic.getAllBookingDetails();
      //List<Booking.BookingDetails> filteredBookings = bookings.stream().filter(b -> b.getCustomerEmail().equals(customer.getEmail())).toList();

       //iewCustomerBookingsTable.setItems(FXCollections.observableArrayList(filteredBookings));
    }

    @FXML
    private void handleMarkAsPaid() {
        Booking.BookingDetails selectedBooking = viewCustomerBookingsTable.getSelectionModel().getSelectedItem();

        if (selectedBooking == null) {
            feedbackText.setText("Please select a booking to mark as paid.");
            feedbackText.setFill(Color.RED);
            return;
        }

        Booking booking = new Booking();
        boolean success = booking.markBookingAsPaid(selectedBooking.getBookingID());

        if (success) {
            feedbackText.setText("Booking marked as paid successfully.");
            feedbackText.setFill(Color.GREEN);

            updateTable(); 
        } else {
            feedbackText.setText("Failed to mark booking as paid.");
            feedbackText.setFill(Color.RED);
        }
    }
}
