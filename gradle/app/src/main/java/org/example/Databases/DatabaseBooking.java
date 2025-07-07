package org.example.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.example.Logic.Booking;
import org.example.Logic.Booking.BookingDetails;

public class DatabaseBooking {
    String connectText = "jdbc:mysql://localhost/LOCALHOST?" +
        "user=root&password=PASSWORD";

        DatabaseRoom dbRoom = new DatabaseRoom();
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DatabaseBooking dbBooking = new DatabaseBooking();
        DatabaseRoom dbRoom = new DatabaseRoom();
        DatabaseCustomer dbCustomer = new DatabaseCustomer();
        //dbCustomer.addCustomer("john", "Doe", "Card", "test street 1", "0121234563", "john.doe@gmail.com");
        //System.out.println(dbBooking.getAllBookingsDay(LocalDateTime.now().format(formatter)));
        //System.out.println(dbBooking.bookRoom(LocalDateTime.now().plusDays(34), LocalDateTime.now().plusDays(36), 10, 1, "Standard", "john.ask@gmail.com"));
        //System.out.println(dbBooking.roomsAvailableIntervall("2025-03-18", "2025-03-22"));
        System.out.println(dbBooking.getBookingID("2025-03-21", "2025-03-26", "john.doe@gmail.com"));
    }

   
    
        public ArrayList<String> getAllBookingsDay(String day){  //get all bookings for a selected day
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<String> bookingsDay = new ArrayList<>();
            String bookingID;
            
    
            try {
                conn = DriverManager.getConnection(connectText);  //connect
                stmt = conn.createStatement();  //Create statement
                rs = stmt.executeQuery("SELECT * FROM booking WHERE check_in = " + "'" + day + "'");  //execute querry and sort

                while (rs.next()) {  //itterate rs 
                    bookingID = rs.getString("booking_id");  //Save id if there is one
                    bookingsDay.add(bookingID);
                }
                return bookingsDay;
            
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return bookingsDay;
                
            }
        }

        public ArrayList<String> getAllBookingsForADay(LocalDateTime selectedDay){  //get all bookings for a selected day
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<String> bookingsDay = new ArrayList<>();
            String bookingID;            
    
            try {
                conn = DriverManager.getConnection(connectText);  //connect

                stmt = conn.createStatement();

                rs = stmt.executeQuery("SELECT * FROM booking WHERE check_in <= '" + selectedDay + "' AND check_out >= '" + selectedDay + "'");

                

                while (rs.next()) {  //itterate rs 
                    bookingID = rs.getString("booking_id");  //Save id if there is one
                    bookingsDay.add(bookingID);
                    System.out.print("Bokinings ID" + bookingID );
                }
                return bookingsDay;
            
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return bookingsDay;
                
            }
        }

public BookingDetails getBookingDetailsByID(String bookingID) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    BookingDetails bookingDetails = null;

    try {
        conn = DriverManager.getConnection(connectText); // Anslut till databasen
        stmt = conn.createStatement(); // Skapa statement
        rs = stmt.executeQuery("SELECT * FROM booking WHERE booking_id = '" + bookingID + "'");

        if (rs.next()) {
            // Hämta detaljer för bokningen
            int id = rs.getInt("booking_id");
            LocalDateTime checkIn = rs.getTimestamp("check_in").toLocalDateTime();
            LocalDateTime checkOut = rs.getTimestamp("check_out").toLocalDateTime();
            int peopleAmount = rs.getInt("people_amount");
            int roomAmount = rs.getInt("room_amount");
            String roomType = rs.getString("room_type");
            String customerEmail = rs.getString("customer_email");
            Integer roomID = rs.getInt("room_id");
            Boolean paid = rs.getBoolean("paid");

            // Skapa och returnera ett BookingDetails-objekt
            bookingDetails = new BookingDetails(id, checkIn, checkOut, peopleAmount, roomAmount, roomType, customerEmail, roomID, paid);
        }
    } catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

    return bookingDetails;
}



        public List<Booking.BookingDetails> getAllBookingsForACustomer(String customeremail){  //get all bookings for a selected day
                List<Booking.BookingDetails> bookingsCustomer = new ArrayList<>();
                            
                String sql = "SELECT booking_id, check_in, check_out, people_amount, room_amount, room_type, customer_email, room_id, paid FROM booking WHERE customer_email = ?";
            
                try (Connection conn = DriverManager.getConnection(connectText);
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    
                    stmt.setString(1, customeremail);
                    ResultSet rs = stmt.executeQuery();
            
                    while (rs.next()) {
                        int bookingId = rs.getInt("booking_id");
                        LocalDateTime checkIn = rs.getTimestamp("check_in").toLocalDateTime();
                        LocalDateTime checkOut = rs.getTimestamp("check_out").toLocalDateTime();
                        int peopleAmount = rs.getInt("people_amount");
                        int roomAmount = rs.getInt("room_amount");
                        String roomType = rs.getString("room_type");
                        String customerEmail = rs.getString("customer_email");
                        int roomId = rs.getInt("room_id");
                        boolean paid = rs.getBoolean("paid");
            
                        Booking.BookingDetails booking = new Booking.BookingDetails(bookingId, checkIn, checkOut, peopleAmount, roomAmount, roomType, customerEmail, roomId, paid);
                        bookingsCustomer.add(booking);
                    }
            
                    System.out.println("TEST Antal bokningar hämtade: " + bookingsCustomer.size());
            
                } catch (SQLException ex) {
                    System.out.println("SQL Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            
                return bookingsCustomer;
            }
            

            public Boolean bookRoom(LocalDateTime checkIn, LocalDateTime checkOut, int peopleAmount, int roomAmount, String roomType, String customerEmail, int roomID, boolean isPaid ) {
                Connection conn = null;
                Statement stmt = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedCheckIn = checkIn.format(formatter);
                String formattedCheckOut = checkOut.format(formatter);
                Boolean bookRoom = false;  //Start false since its not deleted yet

                try {
                    conn = DriverManager.getConnection(connectText);  //connect to databaase
                    stmt = conn.createStatement();  //Create statement
                    //rommvalidation check
                    stmt.executeUpdate("INSERT INTO booking (booking_id , check_in, check_out, people_amount, room_amount, room_type, customer_email, room_id, paid) VALUES ( " +  null  + " ,'" + formattedCheckIn + "' , '" + formattedCheckOut + "' , " + peopleAmount + " , " + roomAmount + " , '" + roomType + "', '" + customerEmail + "'," + roomID + "" + ",'" + isPaid + "'"  + ")");  //execute querry
                    bookRoom = true;  //If we get here we succeded
                    return bookRoom; //return true if successfull
                    
                } catch (SQLException ex) {
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    return bookRoom;
                }
            }

        //get all info from a booking id
        /* 
        public String bookRoom1(LocalDateTime checkIn, LocalDateTime checkOut, int peopleAmount, int roomAmount, String roomType, String customerEmail, int roomID, boolean isPaid){  //book a room
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            String roomToAssign;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedCheckIn = checkIn.format(formatter);
            String formattedCheckOut = checkOut.format(formatter);
            ArrayList<String> roomsList = new ArrayList<>();
            ArrayList<String> availableRooms = new ArrayList<>();
            String temp;

            try {
                conn = DriverManager.getConnection(connectText);  //connect
                roomToAssign = "000";
                stmt = conn.createStatement();  //Create statement
                rs = stmt.executeQuery("SELECT * FROM room WHERE room_type = " + "'" + roomType + "'");  //execute querrry and sort
                availableRooms = roomsAvailableIntervall(formattedCheckIn, formattedCheckOut);
                
                while (rs.next()) {  //Excisting rooms that match room type
                    temp = rs.getString("room_number");  //Save id if there is one
                    roomsList.add(temp);
                }  

                if (roomsList.size() < 1){
                    return "no available rooms";
                }

                for (int i = 0; i < availableRooms.size(); i++){
                    if (roomsList.contains(availableRooms.get(i))) {
                        roomToAssign = availableRooms.get(i);
                    } else {
                        continue;
                    }
                }

                if (roomToAssign == "000"){
                    return "no available rooms";
                }
                
                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("INSERT INTO booking (booking_id , check_in, check_out, people_amount, room_amount, room_type, customer_email, room_id, paid) VALUES ( " +  null  + " ,'" + formattedCheckIn + "' , '" + formattedCheckOut + "' , " + peopleAmount + " , " + roomAmount + " , '" + roomType + "', '" + customerEmail + "'," + roomID + "" + ",'" + isPaid + "'"  + ")");  //execute querry
                
                return "The room has been booked succesfully";
            
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return "An error has occured during the booking " + ex;
                
            }
        }
            */

        public ArrayList<String> roomsAvailableIntervall(String in, String out){  //Check what rooms are available between two dates
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<String> roomsUnavailable = new ArrayList<>();  //List of unavailable rooms
            String room_id;
            ArrayList<String> allRooms = new ArrayList<>();  //List of all rooms
            ArrayList<String> availableRooms = new ArrayList<>();  //List of available rooms
            allRooms = dbRoom.getAllRoomNumbers();  //get all room numbers

            try {
                conn = DriverManager.getConnection(connectText);  //connect
                
                stmt = conn.createStatement();  //Create statement
                rs = stmt.executeQuery("SELECT * FROM booking WHERE check_out >= " + "'" + in + "'" + " AND check_in <= " + "'" + out + "'");  //Get all bookings which would add a conflict with our desired date

                while (rs.next()) {  //itterate rs 
                    room_id = rs.getString("room_id");  //get all room ids for these rooms
                    roomsUnavailable.add(room_id);  //Add them to the list of unavailable rooms
                }
            
                if (roomsUnavailable.size() < 1) {  //If we have no rooms unavailable
                    return allRooms;  //return all rooms
                }

            for (int i = 0; i < roomsUnavailable.size(); i++){  //Loop to remove the unavailable rooms from all rooms
                if (allRooms.contains(roomsUnavailable.get(i))) {
                    allRooms.remove(roomsUnavailable.get(i));
                }
            }
            for (int j = 0; j < allRooms.size(); j++){  
                availableRooms.add(String.valueOf(allRooms.get(j)));
            }
            return availableRooms;
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return availableRooms;
                
            }
        }

        public boolean changeCheckInAndOut(String oldIn, String oldOut, String newIn, String newOut, int bookingID) {
            Connection conn = null;
            Statement stmt = null;
        
            try {
                if (roomsAvailableIntervall(newIn, newOut).size() > 0) {
                    conn = DriverManager.getConnection(connectText);
                    stmt = conn.createStatement();
                    String query = "UPDATE booking SET check_in = '" + newIn + "', check_out = '" + newOut + "' WHERE booking_id = " + bookingID;
                    stmt.executeUpdate(query);
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        
        public boolean changeRoomAmount(String newAmount, int bookingID) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String checkIn = "";
            String checkOut = "";
            String oldAmount = "";
        
            try {
                conn = DriverManager.getConnection(connectText);
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM booking WHERE booking_id = '" + bookingID + "'");
        
                if (rs.next()) {
                    checkIn = rs.getString("check_in");
                    checkOut = rs.getString("check_out");
                    oldAmount = rs.getString("room_amount");
                }
        
                if (roomsAvailableIntervall(checkIn, checkOut).size() > (Integer.parseInt(newAmount) - Integer.parseInt(oldAmount))) {
                    conn = DriverManager.getConnection(connectText);
                    stmt = conn.createStatement();
                    stmt.executeUpdate("UPDATE booking SET room_amount = '" + newAmount + "' WHERE booking_id = " + bookingID);
                    return true;
                }
                return false;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        

        public boolean changeRoomType(String newRoomType, int bookingID) {
            Connection conn = null;
            Statement stmt = null;
        
            try {
                conn = DriverManager.getConnection(connectText);
                stmt = conn.createStatement();
                String query = "UPDATE booking SET room_type = '" + newRoomType + "' WHERE booking_id = " + bookingID;
                stmt.executeUpdate(query);
                return true;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        

        public boolean changeRoomID(int newRoomID, int bookingID) {
            Connection conn = null;
            Statement stmt = null;
        
            try {
                conn = DriverManager.getConnection(connectText);
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE booking SET room_id = " + newRoomID + " WHERE booking_id = " + bookingID);
                return true;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        
        
        public boolean changePeopleAmount(int newPeopleAmount, int bookingID) {
            Connection conn = null;
            Statement stmt = null;
        
            try {
                conn = DriverManager.getConnection(connectText);
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE booking SET people_amount = " + newPeopleAmount + " WHERE booking_id = " + bookingID);
                return true;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        
        

        public boolean markRoomAsPaid(int bookingID){
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            

            try {

                
                conn = DriverManager.getConnection(connectText);  //connect to databaase
                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE booking SET paid = " + "'" + true + "' WHERE booking_id= " + "" + bookingID + "");
                System.out.println("uptadated to paid");
                return true;
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                
                return false;
            }

        }

        public boolean deleteBooking(String bookingId) {
            String query = "DELETE FROM booking WHERE booking_id = ?"; 
    
            try (Connection conn = DriverManager.getConnection(connectText);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
    
                stmt.setString(1, bookingId);
                int affectedRows = stmt.executeUpdate(); 
                
                return affectedRows > 0; 
    
            } catch (SQLException e) {
                System.out.println("Error deleting booking: " + e.getMessage());
                return false;
            }
        }

        public ArrayList<Integer> getBookingID(String in, String out, String customerEmail){
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<Integer> bookingID = new ArrayList<>();
            

            try {
                conn = DriverManager.getConnection(connectText);  //connect
                stmt = conn.createStatement();  //Create statement
                rs = stmt.executeQuery("SELECT * FROM booking WHERE check_out = " + "'" + out + "'" + " AND check_in = " + "'" + in + "'" + " AND customer_email= " + "'" + customerEmail + "'");

                while (rs.next()) {
                    bookingID.add(rs.getInt("booking_id"));
                }
                return bookingID;
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                
                return bookingID;
            }

        }

        public List<Booking.BookingDetails> getAllBookingDetails() {
            List<Booking.BookingDetails> bookings = new ArrayList<>();
            String query = "SELECT booking_id, check_in, check_out, people_amount, room_amount, room_type, customer_email, room_id, paid FROM booking";
            try (Connection conn = DriverManager.getConnection(connectText);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int bookingId = rs.getInt("booking_id");
                    LocalDateTime checkIn = rs.getTimestamp("check_in").toLocalDateTime();
                    LocalDateTime checkOut = rs.getTimestamp("check_out").toLocalDateTime();
                    int peopleAmount = rs.getInt("people_amount");
                    int roomAmount = rs.getInt("room_amount");
                    String roomType = rs.getString("room_type");
                    String customerEmail = rs.getString("customer_email");
                    int roomId = rs.getInt("room_id");
                    boolean paid = rs.getBoolean("paid");

                    Booking.BookingDetails booking = new Booking.BookingDetails(bookingId, checkIn, checkOut, peopleAmount, roomAmount, roomType, customerEmail, roomId, paid);
                    bookings.add(booking);
                }
            } catch (SQLException e) {
                System.out.println("Error fetching booking details: " + e.getMessage());
            }

            return bookings;
        }
        }

    

