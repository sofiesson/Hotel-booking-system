package org.example.Logic;

import java.time.LocalDate;

public class IncompleteBooking {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    

    private int roomID;
   
    private int roomNumber;
   

    private String roomType;
  

    private int beds;
   

    private int size;
   
    private double price;

                
            
        public IncompleteBooking(LocalDate checkIn, LocalDate checkOut, int roomID, int roomNumber, String roomType, int beds, int size, double price) {
            this.checkInDate = checkIn;
            this.checkOutDate = checkOut;
            this.roomID = roomID;
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public LocalDate getCheckInDate(){
            return checkInDate;
        }

        public LocalDate getCheckOutDate() {
            return checkOutDate;
        }


        public int getRoomID() {
            return roomID;
        }
    
        public int getRoomNumber() {
            return roomNumber;
        }

        public String getRoomType() {
            return roomType;
        }

        public int getBeds() {
            return beds;
        }

        public int getSize() {
            return size;
        }
    

        public double getPrice() {
            return price;
        }
}
