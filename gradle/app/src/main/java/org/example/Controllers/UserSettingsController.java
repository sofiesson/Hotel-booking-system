package org.example.Controllers;

import org.example.Logic.Login;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UserSettingsController {

    private Stage stage;
    private String username;
    private final Login login = new Login();

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label usernameStatusLabel;

    @FXML
    private Label passwordStatusLabel;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void handleConfirmUsername() {
        String newUsername = newUsernameField.getText().trim();
        if (!newUsername.isEmpty()) {
            if (login.newUserName(username, newUsername)) {
                username = newUsername;
                usernameStatusLabel.setText("Username updated successfully!");
                usernameStatusLabel.setStyle("-fx-text-fill: green;");
            } else {
                usernameStatusLabel.setText("Failed to update username.");
                usernameStatusLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            usernameStatusLabel.setText("Username cannot be empty.");
            usernameStatusLabel.setStyle("-fx-text-fill: red;");
        }
        usernameStatusLabel.setVisible(true);
    }

    @FXML
    private void handleConfirmPassword() {
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        if (!newPassword.isEmpty() && newPassword.equals(confirmPassword)) {
            login.newPwd(username, newPassword);
            passwordStatusLabel.setText("Password updated successfully!");
            passwordStatusLabel.setStyle("-fx-text-fill: green;");
        } else {
            passwordStatusLabel.setText("Passwords do not match or are empty.");
            passwordStatusLabel.setStyle("-fx-text-fill: red;");
        }
        passwordStatusLabel.setVisible(true);
    }
}
