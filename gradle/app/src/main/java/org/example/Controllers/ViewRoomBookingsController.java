package org.example.Controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import org.example.Logic.Booking;
import org.example.Logic.Room;

import java.util.List;

public class ViewRoomBookingsController {

    @FXML
    private Text viewRoomBookingSelected;
    @FXML
    private TableView<Booking.BookingDetails> viewRoomBookingsTable;
    @FXML
    private TableColumn<Booking.BookingDetails, String> viewRoomBookingCheckInColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, String> viewRoomBookingCheckOutColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, String> viewRoomBookingEmailColumn;
    @FXML
    private TableColumn<Booking.BookingDetails, Boolean> viewRoomBookingPaidColumn;

    public void setRoomDetails(Room.RoomDetails room) {
        if (room == null) return;

        viewRoomBookingSelected.setText("Booking Details for Room Number: " + room.getRoomNumber() + ", " + room.getBeds() + " Beds, " + room.getSize() + "m², Type: " + room.getRoomType() + ", Price: " + room.getPrice() + " SEK");

        Booking booking = new Booking();
        List<Booking.BookingDetails> bookings = booking.getAllBookingDetails();
        List<Booking.BookingDetails> filteredBookings = bookings.stream()
                .filter(b -> b.getRoomID() != null && b.getRoomID() == room.getRoomNumber())
                .toList();

        viewRoomBookingCheckInColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckIn().toString()));
        viewRoomBookingCheckOutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckOut().toString()));
        viewRoomBookingEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerEmail()));
        viewRoomBookingPaidColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getPaid()).asObject());        
        
        viewRoomBookingsTable.setItems(FXCollections.observableArrayList(filteredBookings));
    }
}