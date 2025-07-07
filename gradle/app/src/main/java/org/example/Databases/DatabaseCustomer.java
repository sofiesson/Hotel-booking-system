package org.example.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.Logic.Customer;

public class DatabaseCustomer {
    String connectText = "jdbc:mysql://localhost/LOCALHOST?" +
        "user=root&password=PASSWORD";

    public static void main(String[] args){
    
        DatabaseCustomer dbCustomer = new DatabaseCustomer();
        //dbCustomer.addCustomer("john", "Doe", "Card", "test street 1", "0121234563", "john.doe@gmail.com");
        //System.out.println(dbCustomer.getCustomerIDEmail("john.doe@gmail.com"));
        //System.out.println(dbCustomer.getCustomerIDPhoneNumber("01212312363"));
        dbCustomer.changeCustomerAddress("john.ask@gmail.com", "Storgatan 1");
        //dbCustomer.addCustomer("john", "ask", "Card", "12345", "test street 2" , "01212312363", "john.ask@gmail.com");
    }

    public Boolean addCustomer(String firstName, String lastName,String paymentMethod, String postCode, String address, String phoneNumber, String email){  //add a customer
            Connection conn = null; 
            Statement stmt = null;
    
            try {
                conn = DriverManager.getConnection(connectText);  //connect
                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("INSERT INTO customer (first_name, last_name, payment_method, address, post_code , phone_number, email) VALUES ( '" + firstName + "' , '" + lastName + "' , '" + paymentMethod + "' , '" + address + "' , '" + postCode + "' , '" + phoneNumber + "' , '" + email + "'" + ")");  //execute querry
                return true;  //Custommer was added without error 
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
                
            }
        }

        public boolean deleteCustomer(String customer_email) {
            String query = "DELETE FROM customer WHERE email = ?";
            
            try (Connection conn = DriverManager.getConnection(connectText);    // connect
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.setString(1, customer_email);
                int affectedRows = pstmt.executeUpdate();
        
                return affectedRows > 0; // Returnerar true om en rad togs bort
        
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        

      
        // TA BORT, EMAIL ÄR NYCKEL
        public String getCustomer(String phoneNumber){  //get customerid from phone number
            Connection conn = null; 
            Statement stmt = null;
            ResultSet rs = null;
            String customerEmail = "";

            try {
                conn = DriverManager.getConnection(connectText);  //connect
                
                stmt = conn.createStatement();  //Create statement
                rs = stmt.executeQuery("SELECT * FROM customer WHERE phone_number = " + "'" + phoneNumber + "'");  //execute querrry and sort

                if (rs.next()) {  //itterate rs (should be 1 time only)
                    customerEmail = rs.getString("customer_email");  //Save id if there is one
                    return customerEmail; //return customer email
                }
                return null;
            
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return "An error has occured";
                
            }
        }

        public List<Customer.CustomerDetails> getAllCustomerDetails() {
            List<Customer.CustomerDetails> customers = new ArrayList<>();
            String query = "SELECT first_name, last_name, payment_method, address, post_code, phone_number, email FROM customer";

            try (Connection conn = DriverManager.getConnection(connectText);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String paymentMethod = rs.getString("payment_method");
                    String address = rs.getString("address");
                    String postCode = rs.getString("post_code");
                    String phoneNumber = rs.getString("phone_number");
                    String email = rs.getString("email");

                    Customer.CustomerDetails customer = new Customer.CustomerDetails(firstName, lastName, paymentMethod, address, postCode, phoneNumber, email);
                    customers.add(customer);
                }
            } catch (SQLException e) {
                System.out.println("Error fetching customer details: " + e.getMessage());
            }

            return customers;
        }
        
        public Customer.CustomerDetails getOneCustomerDetails(String email) {
            
            String query = "SELECT first_name, last_name, payment_method, address, post_code, phone_number, email FROM customer WHERE email= " + "'" + email + "'";
            Customer.CustomerDetails customer = null;

            try (Connection conn = DriverManager.getConnection(connectText);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String paymentMethod = rs.getString("payment_method");
                    String address = rs.getString("address");
                    String postCode = rs.getString("post_code");
                    String phoneNumber = rs.getString("phone_number");

                    customer = new Customer.CustomerDetails(firstName, lastName, paymentMethod, address, postCode, phoneNumber, email);
                }
            } catch (SQLException e) {
                System.out.println("Error fetching customer details: " + e.getMessage());
            }
            return customer;
        } 



        public Boolean changeCustomerFirstName(String email, String newFirstName){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET first_name = " + "'" + newFirstName + "' WHERE email= " + "'" + email + "'");  //update old first name to the new one

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        public Boolean changeCustomerLastName(String email, String newLastName){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET last_name = " + "'" + newLastName + "' WHERE email= " + "'" + email + "'");  //update old last name to the new one

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        public Boolean changeCustomerPaymentMethod(String email, String newPaymentMethod){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET payment_method = " + "'" + newPaymentMethod + "' WHERE email= " + "'" + email + "'");  //update old payment method to the new one

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        public Boolean changeCustomerAddress(String email, String newAddress){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET address = " + "'" + newAddress + "' WHERE email= " + "'" + email + "'");  

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        public Boolean changeCustomerPostCode(String email, String newPostCode){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET post_code = " + "'" + newPostCode + "' WHERE email= " + "'" + email + "'");  

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        public Boolean changeCustomerPhoneNumber(String email, String newPhoneNumber){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET phone_number = " + "'" + newPhoneNumber + "' WHERE email= " + "'" + email + "'");  

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        public Boolean changeCustomerEmail(String email, String newEmail){
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(connectText);  //connect to databaase

                stmt = conn.createStatement();  //Create statement
                stmt.executeUpdate("UPDATE customer SET email = " + "'" + newEmail + "' WHERE email= " + "'" + email + "'");  

                return true;
            
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }

        

    }

