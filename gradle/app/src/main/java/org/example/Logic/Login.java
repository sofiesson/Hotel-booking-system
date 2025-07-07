package org.example.Logic;

import org.example.Databases.DatabaseUser;

public class Login {

     private DatabaseUser databas; 

    // Standardkonstruktor
    public Login() {
        this.databas = new DatabaseUser();
    }

    // Konstruktor för testning 
    public Login(DatabaseUser databas) {
        this.databas = databas;
    }

    public Boolean userValidation(String userName, String passwd) {
        Boolean valid = false;
        valid = databas.userValidation(userName, passwd);
        return valid;
    }

    public boolean newUserName(String oldUserName, String newUserName) {
        databas.changeUserName(oldUserName, newUserName);
        return databas.changeUserName(oldUserName, newUserName);
        
    }

    public void newPwd(String userName, String newPwd) {
        databas.changeUserPwd(newPwd, userName);
    }

    public boolean isAdmin(String userID){
        if ("Admin".equals(databas.getRole(userID))){
            return true;
        }
        return false;
    }

    public boolean isReceptionist(String userID){
        if ("Receptionist".equals(databas.getRole(userID))){
            return true;
        }
        return false;
    }

    public int getUserLocationID(String username) {
        return 3; 
    }
    
    
}
