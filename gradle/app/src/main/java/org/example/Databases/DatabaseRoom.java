package org.example.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.Logic.Room;


public class DatabaseRoom {
    
    public DatabaseRoom(){}
    
    String connectText = "jdbc:mysql://localhost/LOCALHOST?" +
        "user=root&password=PASSWORD";
        /* 
    public static void main(String[] args){
        
        DatabaseRoom data = new DatabaseRoom();
        data.getAllRoomNumbers();
        System.out.println(data.changeRoomNumber(101, 102, 1));
        System.out.println(data.changeBeds(102, 1, 3));
        System.out.println(data.changeSize(20, 102, 1));
        System.out.println(data.changePrice(199, 102, 1));
        System.out.println(data.changeRoomType("Double", 102, 1));
    } 
        */

    

    public int deleteRoom(int roomNumber, int locationID) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("DELETE FROM room WHERE room_id = " + locationID + "" + roomNumber);  //delete room
            return stmt.getUpdateCount();
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return -2;
        }
    }


    //get one room number takes input to validate

    public Boolean addRoom(int roomNumber, int locationID, int beds, int size, double price, String roomType ) {
        Connection conn = null;
        Statement stmt = null;
        Boolean roomAdded = false;  //Start false since its not deleted yet
        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase
            stmt = conn.createStatement();  //Create statement
            //rommvalidation check
            stmt.executeUpdate("INSERT INTO room (room_id, beds, size, location_id, price, room_number, room_type) VALUES (" + locationID + "" + roomNumber + "," + beds + "," + size + "," + locationID + "," + price + "," + roomNumber + "," + "'" + roomType + "'" +  ")");  //insert room
            roomAdded = true;  //If we get here we succeded
            return roomAdded; //return true if successfull
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return roomAdded;
        }
    }
    
    public Boolean roomUnique(int roomNumber) {
        Connection conn = null; 
        Statement stmt = null;
        ResultSet rs = null;
        String room = "";

        try {
            conn = DriverManager.getConnection(connectText);  //connect

            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT * FROM room WHERE room_number = " + roomNumber );  //execute querrry and sort
            while (rs.next()) {  //itterate rs (should be 1 time only)
                room = rs.getString("room_number");  //Save room if there is one
                if (room.equals(null)) {
                    return true;
                }
                else {
                    return false;
                }
                }
            return true;
            
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

    }

    public ArrayList<String> getAllRoomNumbers() {
        ArrayList<String> allRooms = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase
            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT room_number FROM room ");
            while(rs.next()) {
                String roomNumberAsString = rs.getString("room_number");
                allRooms.add(roomNumberAsString);
            }
            
            return allRooms;
            

            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return allRooms;
        }
    }


    public List<Room.RoomDetails> getAllRoomDetails() {
        List<Room.RoomDetails> rooms = new ArrayList<>();
        String query = "SELECT room_id, room_number, location_id, beds, size, price, room_type FROM room"; // Lägg till room_id i SQL-frågan
        
        try (Connection conn = DriverManager.getConnection(connectText);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
        
            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                int roomNumber = rs.getInt("room_number");
                int locationID = rs.getInt("location_id");
                int beds = rs.getInt("beds");
                int size = rs.getInt("size");
                double price = rs.getDouble("price");
                String roomType = rs.getString("room_type");
        
                Room.RoomDetails room = new Room.RoomDetails(roomId, roomNumber, locationID, beds, size, price, roomType); // Skapa RoomDetails med room_id
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room details: " + e.getMessage());
        }
        
        return rooms;
    }
    

    //Ändra room details en och en 
    public Boolean changeRoomNumber(int oldRoomNumber, int newRoomNumber, int location_id){  //Update room number and room id since they are connected
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE room SET room_id = " + location_id + newRoomNumber + " WHERE " + "room_id = " + location_id + oldRoomNumber);  //update old roomID to the new one

            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE room SET room_number = " + newRoomNumber + " WHERE " + "room_number = " + oldRoomNumber);  //update old room number to the new one

            return true;
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public Boolean changeBeds(int roomNumber, int location_id, int newAmountOfBeds) {  //Update the amount of beds
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            if (roomUnique(roomNumber)){
                return false;
            }
            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE room SET beds = " + newAmountOfBeds + " WHERE " + " room_id = " + location_id + roomNumber);  //update old bed count to the new one


            return true;
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public Boolean changeSize(int newSize, int roomNumber, int location_id){  //Update size of room
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            if (roomUnique(roomNumber)){
                return false;
            }
            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE room SET size = " + newSize + " WHERE " + " room_id = " + location_id + roomNumber);  //update old size to the new one
            return true;
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public Boolean changePrice(float newPrice, int roomNumber, int location_id){  //Update price
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            if (roomUnique(roomNumber)){
                return false;
            }
            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE room SET price = " + newPrice + " WHERE " + " room_id = " + location_id + roomNumber);  //update old price to the new one
            return true;
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public Boolean changeRoomType(String newRoomType, int roomNumber, int location_id ){
        ArrayList<String> roomTypes = new ArrayList<String>();  //Room types existing
        roomTypes.add("Standard");  //Add room types
        roomTypes.add("Deluxe");
        roomTypes.add("Familj");
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            if (roomUnique(roomNumber)){
                return false;
            }
            if (!roomTypes.contains(newRoomType)) {
                return false;
            }
            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE room SET room_type = " + "'" + newRoomType + "'" + " WHERE " + " room_id = " + location_id + roomNumber);  //update old price to the new one
            return true;
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    
    public String getRoomType(int roomNumber, int locationID){
        Connection conn = null; 
        Statement stmt = null;
        ResultSet rs = null;
        String roomType;

        try {
            conn = DriverManager.getConnection(connectText);  //connect
            
            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT room_type FROM room WHERE room_id = " + "'" + locationID + "" + roomNumber + "'" );  //execute querrry and sort

            while (rs.next()) {  // osäker om while behövs här
                roomType = rs.getString("role");  //Save role if there is one
                return roomType;
            }
           
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());            
        }
        return null;
    }





}
