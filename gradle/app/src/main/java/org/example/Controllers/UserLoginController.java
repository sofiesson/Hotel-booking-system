package org.example.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.example.Logic.Login;

public class UserLoginController {
    private final Login login = new Login();
    private Stage stage;
    private String username;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputUsername;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setUserName(String username){
        this.username = username;
    }


    @FXML
    void onBtnClick(ActionEvent event) {
        // && login.isAdmin(inputUsername.getText()
        if(login.userValidation(inputUsername.getText(), inputPassword.getText()) && login.isAdmin(inputUsername.getText())){
             
             try {
            // Load the original user menu scene (assuming you have it saved in a variable, e.g., originalScene)

            int locationID = login.getUserLocationID(inputUsername.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLAdmin/menuAdmin.fxml")); // Ensure path is correct
            Parent root = loader.load();

            // Get the controller for UserMenu (UserMenuController)
            UserMenuController userMenuController = loader.getController();
            userMenuController.setStage(stage);
            userMenuController.setUserName(inputUsername.getText()); // Pass username if necessary
            userMenuController.updateLocationText();

            // Set the scene back to the original User Menu
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        else if(login.userValidation(inputUsername.getText(), inputPassword.getText()) && login.isReceptionist(inputUsername.getText())){
            try {
                int locationID = login.getUserLocationID(inputUsername.getText());
                // Load the original user menu scene (assuming you have it saved in a variable, e.g., originalScene)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/menuStaff.fxml")); // ÄNDRA TILL RÄTT FXML
                Parent root = loader.load();
    
                // Get the controller for UserMenu (UserMenuController)
                UserMenuController userMenuController = loader.getController();
                userMenuController.setStage(stage);
                userMenuController.setUserName(inputUsername.getText()); // Pass username if necessary
                userMenuController.updateLocationText();
                // Set the scene back to the original User Menu
                Scene scene = new Scene(root);
                stage.setScene(scene);
    
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            errorMsg.setText("Wrong username or password!");
            errorMsg.setStyle("-fx-text-fill: red;");
            errorMsg.setVisible(true);
        }
    }
    

}