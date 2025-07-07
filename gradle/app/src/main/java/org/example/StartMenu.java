package org.example;


import org.example.Controllers.UserLoginController;
import org.example.Logic.Login;
import org.example.Logic.Room;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StartMenu extends Application {
    Login login = new Login();
    Room room = new Room();
    String userLoggedIn;

    @Override
    public void start(Stage primaryStage) {
        createLoginScene(primaryStage);
    }

    public void createLoginScene(Stage stage) {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBoth/loginScene.fxml"));
        Parent root = loader.load();
    
        UserLoginController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}