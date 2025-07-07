package org.example.Logic;

import java.util.List;

import org.example.Databases.DatabaseUser;

public class Staff {
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String password;
    private int locationID;

    private DatabaseUser databas;


     public Staff() {
        this.databas = new DatabaseUser();
    }

    // Konstruktor för testning 
    public Staff(DatabaseUser databas) {
        this.databas = databas;
    }

        // DENNA ÄR NY
      public List<Staff> getAllStaff() {
        return databas.getAllStaff();
      }



    public String getLocation(int location){
        //int location = databas.getUserLocationID(username);

        if (location == 0){
            return "Växjö";
        }
        else if(location == 1){
            return "Stockholm";
        }
        else if(location == 2){
            return "Karlstad";
        }
        else if(location == 3){
            return "Göteborg";
        }
        else{
            return null;
        }
    }

    public boolean addStaff(String user_name, String first_name, String last_name, String role, String password, int locationdID){
        return databas.addStaff(user_name, first_name, last_name, role, password, locationdID);
    }   // Databas.addStaff returnerar true om den kunde lägga till en staff
    

    // Objekt
    public Staff(String username, String firstName, String lastname, String role, String password, int locationdID) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastname;
        this.role = role;
        this.password = password;
        this.locationID = locationdID;

        
    }

    public String getUsername() {
        return username;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole(){
        return role;
    }

    public String getPassword() {
        return password;
    }

    public int getLocationID() {
        return locationID;
    }
   
    public String getLocation(){
        if (locationID == 1){
            return "Växjö";
        }
        else if(locationID == 2){
            return "Stockholm";
        }
        else if(locationID == 3){
            return "Karlstad";
        }
        else if(locationID == 4){
            return "Göteborg";
        }
        else{
            return null;
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }
    
}
