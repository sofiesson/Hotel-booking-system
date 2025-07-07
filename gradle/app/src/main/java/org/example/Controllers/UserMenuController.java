package org.example.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.Logic.Staff;
import javafx.scene.text.Text;

import org.example.StartMenu;
import org.example.Databases.DatabaseUser;

public class UserMenuController {

    @FXML
    private Button settingsButton;  // fx:id of the Settings button

    @FXML
    private Button manageRoomsButton; // Bind the button

    @FXML
    private Button logoutButton; 

    @FXML
    private Button adminoptionsButton;

    @FXML
    private Button ManagebookingsButton;

    @FXML
    private Button ViewRoomsButton;

    @FXML
    private Button customersDetailsButton;

    @FXML
    private Pane contentPane;
    private Stage stage;
    private String userName;
    private int locationID;
    private StartMenu fx; // Reference to the FX class for the createUserSettingsMenu method

    @FXML
    private Text locationText; // Kopplad till fx:id i FXML

    private final Staff staff = new Staff();

    // Initialize method
    @FXML
    public void initialize() {
        fx = new StartMenu();  // Initialize the FX instance
        int userLocationID = getUserLocationFromDatabase(userName); 
        String locationName = staff.getLocation(userLocationID); 

        if (locationName != null) {
            locationText.setText("Location: " + locationName);
        } else {
            locationText.setText("Location: Unknown");
        }
    }

    // This method is called when the settings button is clicked
    @FXML
    public void openUserSettingsMenu() {
        if (userName != null) {
            try {
                // Load the UserSettings screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLBoth/settings.fxml"));
                Parent settingsContent = loader.load();

                // Pass data to the UserSettingsController if needed
                UserSettingsController userSettingsController = loader.getController();
                userSettingsController.setUsername(userName);

                // Replace content in the contentPane with the settings content
                contentPane.getChildren().clear(); // Clear previous content
                contentPane.getChildren().add(settingsContent); // Add new content

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Username not set.");
        }
    }

    @FXML
    private void logout() {
        System.out.println("User logged out.");
    
        if (stage != null) {
            try {
                // Assuming invalidPassword is false when the user logs out, set it accordingly.
                boolean invalidPassword = false;

                // Call the method to create the login scene
                fx.createLoginScene(stage);
                stage.centerOnScreen();
            } catch (Exception e) {
                System.out.println("Error loading login screen: " + e.getMessage());
            }
        }
    }

    

    @FXML
    public void openRoomsMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/manageRoomsAdmin.fxml"));
            Parent roomsContent = loader.load();
            ManageRoomsController controller = loader.getController();
            controller.setUserMenuController(this);
    
            contentPane.getChildren().clear();
            contentPane.getChildren().add(roomsContent);
        } 
        catch (Exception e) {
            System.out.println("Error loading manage rooms screen: " + e.getMessage());
        }
    }

    @FXML
    private void openAdminOptionsMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/adminOptions.fxml"));
            Parent adminOptionsContent = loader.load();
            AdminOptionsController adminOptionsController = loader.getController();
        
        // Skicka med användarnamn och locationID
        adminOptionsController.setUserName(userName);  // Skicka användarnamn
        adminOptionsController.setLocationID(locationID);
            contentPane.getChildren().clear();
            contentPane.getChildren().add(adminOptionsContent);
        } catch (IOException e) {
            System.out.println("Error loading admin options screen: " + e.getMessage());
        }
    }

    @FXML
    private void openViewRoomsMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/manageRoomsStaff.fxml"));
            Parent viewroomsContent = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(viewroomsContent);
        } 
        catch (Exception e) {
            System.out.println("Error loading view rooms screen: " + e.getMessage());
        }
    }
    
    
    @FXML
    public void openManageBookings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/manageBookings.fxml"));
            Parent manageBookingsContent = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(manageBookingsContent);
        } catch (IOException e) {
            System.out.println("Error loading bookings screen: " + e.getMessage());
        }
    }

    @FXML
    private void openFindBookings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/availableBookings.fxml"));
            Parent availableBookingsContent = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(availableBookingsContent);
        } catch (IOException e) {
            System.out.println("Error loading bookings screen: " + e.getMessage());
        }
    }


    
    @FXML
    public void openCustomersMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/customersMenu.fxml"));
            Parent customerContent = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(customerContent);
        } 
        catch (Exception e) {
            System.out.println("Error loading customers screen: " + e.getMessage());
        }
    }

    void updateLocationText() {
        DatabaseUser dbUser = new DatabaseUser();
        int userLocationID = dbUser.getUserLocationID(userName);  
        if (userLocationID != -1) {  
            String locationName = staff.getLocation(userLocationID);
            if (locationName != null) {
                locationText.setText("Location: " + locationName);
            } else {
                locationText.setText("Location: Unknown");
            }
        } else {
            locationText.setText("Location: Unknown");
        }
    }
    
    
    // Method to set the stage (this can be called from MainApp or elsewhere)
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to set the username (this can be called from MainApp or elsewhere)
    public void setUserName(String userName) {
        this.userName = userName;
        System.out.println("Username set: " + userName);
    
        if (userName != null) {
            updateLocationText();
        }
    }    

    public int getUserLocationFromDatabase(String userName) {
        if (userName == null || userName.isEmpty()) {
            return -1;
        }
    
        DatabaseUser dbUser = new DatabaseUser();
        int locationID = dbUser.getUserLocationID(userName);
        return locationID;
    }
          
}