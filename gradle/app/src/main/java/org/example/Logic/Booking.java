package org.example.Logic;

import org.example.Databases.DatabaseBooking;

import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private DatabaseBooking database = new DatabaseBooking();

    public static class BookingDetails {
        private int bookingID;
        private LocalDateTime checkIn;
        private LocalDateTime checkOut;
        private int peopleAmount;
        private int roomAmount;
        private String roomType;
        private String customerEmail;
        private Integer roomID;
        private Boolean paid;

        public BookingDetails(int bookingID, LocalDateTime checkIn, LocalDateTime checkOut, int peopleAmount, int roomAmount, String roomType, String customerEmail, Integer roomID, Boolean paid) {
            this.bookingID = bookingID;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.peopleAmount = peopleAmount;
            this.roomAmount = roomAmount;
            this.roomType = roomType;
            this.customerEmail = customerEmail;
            this.roomID = roomID;
            this.paid = paid;
        }

        public int getBookingID() { return bookingID; }
        public LocalDateTime getCheckIn() { return checkIn; }
        public LocalDateTime getCheckOut() { return checkOut; }
        public int getPeopleAmount() { return peopleAmount; }
        public int getRoomAmount() { return roomAmount; }
        public String getRoomType() { return roomType; }
        public String getCustomerEmail() { return customerEmail; }
        public Integer getRoomID() { return roomID; } // Nullable
        public Boolean getPaid() { return paid; }
    }

    public Boolean bookRoom(LocalDateTime checkIn, LocalDateTime checkOut, int peopleAmount, int roomAmount, String roomType, String customerEmail, int roomID, boolean isPaid) {
        return database.bookRoom(checkIn, checkOut, peopleAmount, roomAmount, roomType, customerEmail, roomID, isPaid);
    }

    public List<String> getAllBookingsDay(String day) {
        return database.getAllBookingsDay(day);
    }

    public List<BookingDetails> getAllBookingsForCustomer(String email) {
        return database.getAllBookingsForACustomer(email);
    }

    public Boolean changeCheckInAndOut(int bookingID, String newCheckIn, String newCheckOut) {
        return database.changeCheckInAndOut("", "", newCheckIn, newCheckOut, bookingID);
    }

    public Boolean changeRoomAmount(int bookingID, String newAmount) {
        return database.changeRoomAmount(newAmount, bookingID);
    }

    public Boolean changeRoomType(int bookingID, String newRoomType) {
        return database.changeRoomType(newRoomType, bookingID);
    }

    public Boolean changeRoomID(int newRoomID, int bookingID) {
        return database.changeRoomID(newRoomID, bookingID);
    }
    
    public Boolean changePeopleAmount(int newPeopleAmount, int bookingID) {
        return database.changePeopleAmount(newPeopleAmount, bookingID);
    }

    public Boolean markBookingAsPaid(int bookingID) {
        return database.markRoomAsPaid(bookingID);
    }

    public List<BookingDetails> getAllBookingDetails() {
        return database.getAllBookingDetails();
    }

    /* 
    @Override
    public String toString() {
        return "BookingId" + bookingID + "| CheckIn: " + checkIn + "| CheckOut:" + checkOut + "| peopleAmount:" + peopleAmount + "| roomAmount" + roomAmount +
               " | roomType: " + roomType + " | customerEmail: " + customerEmail + " | roomID: " + roomID + " | paid: " + paid;
    }
               */
}

