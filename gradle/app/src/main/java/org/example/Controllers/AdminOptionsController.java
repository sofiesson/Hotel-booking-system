package org.example.Controllers;

import java.io.IOException;
import java.util.List;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import org.example.Databases.DatabaseUser;
import org.example.Logic.Staff;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.text.Text;



public class AdminOptionsController {

    @FXML
    private Button addStaffButton;

    @FXML
    private Button changeStaffButton;

    @FXML
    private Button deleteStaffButton;

    @FXML
    private ScrollBar verticalScrollBar;

    @FXML
    private Text staffText;

    @FXML
    private Text locationText;

    @FXML
    private VBox staffContainer;

    @FXML
    private Text nameText;

    @FXML
    private Text passwordText;

    @FXML
    private Text emailText;

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, String> usernameColumn;

    @FXML
    private TableColumn<Staff, String> firstnameColumn;

    @FXML
    private TableColumn<Staff, String> lastnameColumn;

    @FXML
    private TableColumn<Staff, String> roleColumn;

    @FXML
    private TableColumn<Staff, String> passwordColumn;


    @FXML
    private ListView<Staff> staffListView;

    private String userName;
    private int locationID;


    public void setUserName(String userName) {
        this.userName = userName;
        if (userName != null) {
            UserMenuController userMenuController = new UserMenuController();
            int currentLocationID = userMenuController.getUserLocationFromDatabase(userName);
            setLocationID(currentLocationID);
        } else {
            System.out.println("AdminOptionsController - userName is null, cannot fetch locationID.");
        }
    }
    
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        updateStaffList();

        if (userName != null) {
            UserMenuController userMenuController = new UserMenuController();
            int currentLocationID = userMenuController.getUserLocationFromDatabase(userName);
            setLocationID(currentLocationID);
        } else {
            System.out.println("AdminOptionsController - userName is null, cannot fetch locationID.");
        }

        addStaffButton.setOnAction(event -> handleAddStaff());
    }

    @FXML
    private void handleAddStaff() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/addStaff.fxml"));
            Parent root = loader.load();
            AddStaffController addStaffController = loader.getController();
            addStaffController.setAdminController(this);
            UserMenuController userMenuController = new UserMenuController();
            int currentLocationID = userMenuController.getUserLocationFromDatabase(userName);  // Make sure you pass the correct locationID

            addStaffController.setLocationID(currentLocationID);

            Stage addStaffStage = new Stage();
            addStaffStage.setTitle("");
            addStaffStage.setScene(new Scene(root));
            addStaffStage.initModality(Modality.APPLICATION_MODAL); // Blockera andra fönster medan detta är öppet
            addStaffStage.show();
        } catch (IOException e) {
            System.err.println("Error opening Add Staff window: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void handleChangeStaff() {
    Staff selectedStaff = staffTableView.getSelectionModel().getSelectedItem();

    if (selectedStaff != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/changeStaffDetails.fxml"));
            Parent changeStaffRoot = loader.load();

            StaffChangeDetailsController staffDetailsController = loader.getController();
            staffDetailsController.initializeWithStaff(selectedStaff, this); // Skicka referens

            Stage changeStaffStage = new Stage();
            changeStaffStage.setTitle("");
            changeStaffStage.setScene(new Scene(changeStaffRoot));
            changeStaffStage.initModality(Modality.APPLICATION_MODAL);
            changeStaffStage.show();
        } catch (IOException e) {
            System.err.println("Error opening Change Staff window: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("No staff selected.");
    }
}

public void refreshStaffList() {
    updateStaffList(); 
}




    @FXML
    private void handleDeleteStaff() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/deleteStaff.fxml"));
            Parent addStaffRoot = loader.load();

            DeleteStaffController deleteStaffController = loader.getController();
            deleteStaffController.setAdminController(this);

            Stage addStaffStage = new Stage();
            addStaffStage.setTitle("");
            addStaffStage.setScene(new Scene(addStaffRoot));
            addStaffStage.initModality(Modality.APPLICATION_MODAL); 
            addStaffStage.show();
        } catch (IOException e) {
            System.err.println("Error opening Add Staff window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateStaffList() {
        DatabaseUser database = new DatabaseUser();
        List<Staff> staffList = database.getAllStaff();

        staffTableView.setItems(FXCollections.observableArrayList(staffList));
    }
}
