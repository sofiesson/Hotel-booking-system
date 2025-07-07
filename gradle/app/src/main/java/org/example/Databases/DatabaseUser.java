package org.example.Databases;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.Logic.*;;


public class DatabaseUser {
    String connectText = "jdbc:mysql://localhost/LOCALHOST?" +
        "user=root&password=PASSWORD";
    
    public static void main(String[] args){
        
        
        //System.out.println(login.userLocationID("test123@gmail.com"));
        //System.out.println(login.isAdmin("test123@gmail.com"));
        //System.out.println(login.addUser("qwerty", "123", "Admin", "asd", "dfg", 1));
        //System.out.println(login.userCity("asd"));
        //System.out.println(login.deleteUser("test"));
    }

     //Get user locationID 
     public int getUserLocationID(String user_name) {
        Connection conn = null; 
        PreparedStatement stmt = null; 
        ResultSet rs = null;
        int locID = -1; 
    
        try {
            conn = DriverManager.getConnection(connectText);  
            String query = "SELECT locationID FROM user WHERE user_name = ?";
            stmt = conn.prepareStatement(query);  
            stmt.setString(1, user_name);  
            rs = stmt.executeQuery();
    
            if (rs.next()) {  
                locID = rs.getInt("locationID");  
            } else {
                System.out.println("No locationID found for user: " + user_name);
            }
    
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
        return locID;  
    }
    

    public String getRole(String userID) {
        Connection conn = null; 
        Statement stmt = null;
        ResultSet rs = null;
        String role;

        try {
            conn = DriverManager.getConnection(connectText);  //connect
            
            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT role FROM user WHERE user_name = " + "'" + userID + "'" );  //execute querrry and sort

            if (rs.next()) {  // använd if sats här istället
                role = rs.getString("role");  //Save role if there is one
                return role;
            }
           
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());            
        }
        return null;
    }

    public String getUserName(String userName, String pwd) {  //mest för test
        Connection conn = null; 
        Statement stmt = null;
        ResultSet rs = null;
        String user = "";

        try {
            conn = DriverManager.getConnection(connectText);  //connect
            
            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT user_name FROM user WHERE user_name = " + "'" + userName + "'" + " AND password = " + "'" + pwd + "'");  //execute querrry and sort

            while (rs.next()) {  //itterate rs (should be 1 time only)
                user = rs.getString("user_name");  //Save user if there is one
                return user; //return userName
            }
            return "no user found";
           
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return "An error has occured";
            
        }
    }

    public Boolean userValidation(String userName, String pwd) {  //return true if user is valid
        Connection conn = null; 
        Statement stmt = null;
        ResultSet rs = null;
        Boolean validUser = false;
        String user = "";

        try {
            conn = DriverManager.getConnection(connectText);  //connect

            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT * FROM user WHERE user_name = " + "'" + userName + "'" + " AND password = " + "'" + pwd + "'");  //execute querrry and sort

            while (rs.next()) {  //itterate rs (should be 1 time only)
                user = rs.getString("user_name");  //Save user if there is one
                validUser = true; //valid user
                }
            return validUser;  //return results (true, false)
            /* PreparedStatement insertQuerry = conn.prepareStatement("INSERT INTO Users VALUES ('me@gmail.com', 'test', 'test', true, 123)");
            insertQuerry.execute(); */
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public Boolean changeUserName(String oldUserName, String newUserName) {  //Change userName
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE user SET user_name = '" + newUserName + "'" + "WHERE user_name = '" + oldUserName + "'");  //update old username to the new one
            return true;
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public Boolean changeUserPwd(String newPwd, String userName) {  //Change user password
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectText);  //connect to databaase

            stmt = conn.createStatement();  //Create statement
            stmt.executeUpdate("UPDATE user SET password = '" + newPwd + "'" + "WHERE user_name = '" + userName + "'");  //update old password to the new one
            return true;
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }


    public Boolean userNameInUse(String userName){  //Kollar om användarnamet är taget
        Connection conn = null; 
        Statement stmt = null;
        ResultSet rs = null;
        Boolean userNameInUse = false;  //variabel för om användarnamnet är in use redan
        String user = "";

        try {
            conn = DriverManager.getConnection(connectText);  //connect

            stmt = conn.createStatement();  //Create statement
            rs = stmt.executeQuery("SELECT * FROM user WHERE user_name = " + "'" + userName + "'");  //execute querrry and sort

            while (rs.next()) {  //itterate rs (should be 1 time only)
                user = rs.getString("user_name");  //Save user if there is one
                userNameInUse = true; //valid user
                }
            return userNameInUse;  //return results (true, false)
            /* PreparedStatement insertQuerry = conn.prepareStatement("INSERT INTO Users VALUES ('me@gmail.com', 'test', 'test', true, 123)");
            insertQuerry.execute(); */
        
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public boolean addStaff(String user_name, String first_name, String last_name, String role, String password, int locationID) {
        Connection conn = null;
        Statement stmt = null;
    
        try {

            conn = DriverManager.getConnection(connectText);
            stmt = conn.createStatement();
    
    
            stmt.executeUpdate("INSERT INTO user (user_name, first_name, last_name, role, password, locationID) VALUES ('"
            + user_name + "','"
            + first_name + "','"
            + last_name + "','"
            + role + "','"
            + password + "',"
            + locationID + ")");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
                return false;
          
        }
    
    
    
    
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT user_name, first_name, last_name, role, password, locationID FROM user";

        try (Connection conn = DriverManager.getConnection(connectText);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String user_name = rs.getString("user_name");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String role = rs.getString("role");
                String password = rs.getString("password");
                int locationID = rs.getInt("locationID");
    
                Staff staff = new Staff(user_name, first_name, last_name, role, password, locationID);
                staffList.add(staff);             
            }
        } catch (SQLException e) {
            System.out.println("Error fetching staff: " + e.getMessage());
        }
    
        return staffList;
    }    


    public List<String> getAllLocations(Staff staff) {
        List<String> locations = new ArrayList<>();
        String query = "SELECT DISTINCT locationID FROM user";
    
        try (Connection conn = DriverManager.getConnection(connectText);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                locations.add(staff.getLocation(rs.getInt("location")));    // kanske inte fungerar
            }
        } catch (SQLException e) {
            System.out.println("Error fetching locations: " + e.getMessage());
        }
        return locations;
    }
    
    public boolean updateStaff(Staff staff) {
        String query = "UPDATE user SET user_name = ?, first_name = ?, last_name = ?, role = ?, password = ?  WHERE user_name = ?";
    
        try (Connection conn = DriverManager.getConnection(connectText);
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, staff.getUsername());
            stmt.setString(2, staff.getFirstName());
            stmt.setString(3, staff.getLastName());
            stmt.setString(4, staff.getRole());
            stmt.setString(5, staff.getPassword());
            stmt.setString(6, staff.getUsername()); 

            //stmt.setString(6, staff.getLocation());
    
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
    
        } catch (SQLException e) {
            System.out.println("Error updating staff: " + e.getMessage());
            return false;
        }
    }      
    

    public boolean updateUsername(String oldUsername, String newUsername) {
        String query = "UPDATE staff SET user_name = ? WHERE username = ?";
    
        try (Connection conn = DriverManager.getConnection(connectText);
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
    
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
    
        } catch (SQLException e) {
            System.out.println("Error updating username: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStaffByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DriverManager.getConnection(connectText);
            String query = "DELETE FROM user WHERE user_name = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectText);
    }    
    
}