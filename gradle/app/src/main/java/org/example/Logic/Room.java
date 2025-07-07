package org.example.Logic;
import java.util.List;

import org.example.Databases.DatabaseRoom;

public class Room {

    private DatabaseRoom databas; 

    // Standardkonstruktor
    public Room() {
        this.databas = new DatabaseRoom();
    }

    // Konstruktor för testning 
    public Room(DatabaseRoom databas) {
        this.databas = databas;
    }

    public boolean addRoom(int roomNumber, int locationID, int beds, int size, double price, String roomType) {
        return databas.addRoom(roomNumber, locationID, beds, size, price, roomType);
    }

    public boolean deleteRoom(int roomNumber, int locationID) {
        Boolean roomDeleted = false;  //Start false since its not deleted yet

        if (databas.deleteRoom(roomNumber, locationID) == 0) {
            roomDeleted = false;
        }
        else {
            roomDeleted = true;  //If we get here we succeded
        }
        return roomDeleted; //return true if successfull
    }

    public void setType(String newType, int roomNumber, int locationID) {
        databas.changeRoomType(newType, roomNumber, locationID);
    }

    public void setBeds(int roomNumber, int locationID, int newBeds) {
        databas.changeBeds(roomNumber, locationID, newBeds);
    }

    public void setPrice(float newPrice, int roomNumber, int locationID) {
        databas.changePrice(newPrice, roomNumber, locationID);
    }

    public void setSize(int newSize, int roomNumber, int locationID) {
        databas.changeSize(newSize, roomNumber, locationID);
    }

    public void setRoomNumber(int oldRoomNumber, int newRoomNumber, int locationID) {
        databas.changeRoomNumber(oldRoomNumber, newRoomNumber, locationID);
    }
    

    public List<RoomDetails> getAllRoomDetails() {
        return databas.getAllRoomDetails(); // Anropa databasklassens metod
    }

    public static class RoomDetails {
        private int roomId; 
        private int roomNumber;
        private int locationID;
        private int beds;
        private int size;
        private double price;
        private String roomType;

        public RoomDetails(int roomId, int roomNumber, int locationID, int beds, int size, double price, String roomType) {
            this.roomId = roomId;
            this.roomNumber = roomNumber;
            this.locationID = locationID;
            this.beds = beds;
            this.size = size;
            this.price = price;
            this.roomType = roomType;
        }

        public int getRoomId() {
        return roomId;
    }

        public int getRoomNumber() {
            return roomNumber;
        }

        public int getLocationID() {
            return locationID;
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

        public String getRoomType() {
            return roomType;
        }

        @Override
        public String toString() {
            return "Room-id" + roomId + "Room " + roomNumber + " (" + roomType + ") - Location: " + locationID +
                   " | Beds: " + beds + " | Size: " + size + " sqm | Price: " + price;
        }
    }
}

