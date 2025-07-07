package org.example.Controllers;

import org.example.Logic.Booking;
import org.example.Logic.Room;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChangeBookingController {

    @FXML
    private TextField bookingIdField;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private ComboBox<String> peopleComboBox;
    @FXML
    private TextField roomNumberField;
    @FXML
    private Text changeBookingFeedback;

    private UserMenuController userMenuController;

    @FXML
    private void initialize() {
        peopleComboBox.getItems().addAll("1", "2", "3", "4", "5");
    }

    @FXML
    private void handleChangeSelectedBooking() {
        changeBookingFeedback.setFill(Color.RED);

        String bookingIdText = bookingIdField.getText();
        if (bookingIdText.isEmpty()) {
            changeBookingFeedback.setText("Please enter a Booking ID to change");
            return;
        }

        try {
            int bookingID = Integer.parseInt(bookingIdText);
            Booking booking = new Booking();
            Booking.BookingDetails bookingDetails = booking.getAllBookingDetails().stream()
                    .filter(b -> b.getBookingID() == bookingID)
                    .findFirst()
                    .orElse(null);

            if (bookingDetails == null) {
                changeBookingFeedback.setText("Booking with the provided ID does not exist");
                return;
            }

            boolean allChangesSuccessful = true;

            final int newRoomID;
            if (!roomNumberField.getText().isEmpty()) {
                try {
                    newRoomID = Integer.parseInt(roomNumberField.getText());
                    Room room = new Room();
                    Room.RoomDetails roomDetails = room.getAllRoomDetails().stream()
                            .filter(r -> r.getRoomId() == newRoomID)
                            .findFirst()
                            .orElse(null);

                    if (roomDetails == null) {
                        changeBookingFeedback.setText("Room with the provided number does not exist");
                        return;
                    }
                } catch (NumberFormatException e) {
                    changeBookingFeedback.setText("Room ID must be a number.");
                    return;
                }
            } else {
                newRoomID = bookingDetails.getRoomID();
            }

            final LocalDateTime newCheckInDateTime;
            final LocalDateTime newCheckOutDateTime;
            if (checkInDatePicker.getValue() != null && checkOutDatePicker.getValue() != null) {
                LocalDate newCheckIn = checkInDatePicker.getValue();
                LocalDate newCheckOut = checkOutDatePicker.getValue();

                if (!newCheckOut.isAfter(newCheckIn)) {
                    changeBookingFeedback.setText("Check-out date must be after check-in date");
                    return;
                }

                newCheckInDateTime = newCheckIn.atStartOfDay();
                newCheckOutDateTime = newCheckOut.atStartOfDay();

                boolean isOverlapping = booking.getAllBookingDetails().stream()
                        .anyMatch(existingBooking -> existingBooking.getRoomID() != null &&
                                existingBooking.getRoomID().equals(newRoomID) &&
                                existingBooking.getBookingID() != bookingID &&
                                (newCheckInDateTime.isBefore(existingBooking.getCheckOut()) &&
                                        newCheckOutDateTime.isAfter(existingBooking.getCheckIn())));

                if (isOverlapping) {
                    changeBookingFeedback.setText("This room is already booked for the selected dates");
                    return;
                }

                boolean success = booking.changeCheckInAndOut(bookingID, newCheckIn.toString(), newCheckOut.toString());
                allChangesSuccessful = allChangesSuccessful && success;
            } else {
                newCheckInDateTime = bookingDetails.getCheckIn();
                newCheckOutDateTime = bookingDetails.getCheckOut();
            }

            if (peopleComboBox.getValue() != null) {
                try {
                    int newPeopleAmount = Integer.parseInt(peopleComboBox.getValue());
                    boolean success = booking.changePeopleAmount(newPeopleAmount, bookingID);
                    allChangesSuccessful = allChangesSuccessful && success;
                } catch (NumberFormatException e) {
                    changeBookingFeedback.setText("People amount must be a number.");
                    return;
                }
            }

            if (!roomNumberField.getText().isEmpty()) {
                boolean success = booking.changeRoomID(newRoomID, bookingID);
                allChangesSuccessful = allChangesSuccessful && success;

                String roomType = new Room().getAllRoomDetails().stream().filter(r -> r.getRoomId() == newRoomID).map(Room.RoomDetails::getRoomType).findFirst().orElse(null);

                if (roomType == null) {
                    changeBookingFeedback.setText("Failed to retrieve room type for the new room");
                    return;
                }

                success = booking.changeRoomType(bookingID, roomType);
                allChangesSuccessful = allChangesSuccessful && success;
            }

            if (allChangesSuccessful) {
                changeBookingFeedback.setFill(Color.GREEN);
                changeBookingFeedback.setText("Booking details updated!");
                if (userMenuController != null) {
                    userMenuController.openManageBookings();
                }
            } else {
                changeBookingFeedback.setText("Some updates failed, please check and try again.");
            }

        } catch (NumberFormatException e) {
            changeBookingFeedback.setText("Invalid input: Please enter valid numbers for numeric fields");
        } catch (Exception e) {
            changeBookingFeedback.setText("Error updating booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setBookingDetails(Booking.BookingDetails bookingDetails) {
        bookingIdField.setText(String.valueOf(bookingDetails.getBookingID()));
        roomNumberField.setText(String.valueOf(bookingDetails.getRoomID()));
        peopleComboBox.setPromptText(Integer.toString(bookingDetails.getPeopleAmount()));
        checkInDatePicker.setValue(bookingDetails.getCheckIn().toLocalDate());
        checkOutDatePicker.setValue(bookingDetails.getCheckOut().toLocalDate());
    }

    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }
}
