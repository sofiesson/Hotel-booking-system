package org.example.Controllers;

import java.io.IOException;
import java.util.List;

import org.example.StartMenu;
import org.example.Logic.Customer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;


public class CustomerController {

    @FXML
    private TableView<Customer.CustomerDetails> customersTable;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerFirstNameColumn;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerLastNameColumn;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerPaymentColumn;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerAdressColumn;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerPostCodeColumn;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer.CustomerDetails, String> customerEmailColumn;
    @FXML
    private Pane contentPane;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button customerDetailsButton;
    @FXML
    private TextField searchEmailFieldcus;

    @FXML
    private Text customerFeedback;

    private UserMenuController userMenuController;

    private String username;
    private Stage stage;
    private String userName;
    private StartMenu fx;

    public void setUsername(String username) {
        this.username = username;
    }


    @FXML
    public void initialize() {
        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        customerAdressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        Customer customer = new Customer();
        List<Customer.CustomerDetails> customers = customer.getAllCustomerDetails();
        customersTable.setItems(FXCollections.observableArrayList(customers));

        fx = new StartMenu();
    }

    @FXML
    private void handleAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/addCustomer.fxml"));
            Parent addCustomerContent = loader.load();
            AddCustomerController addCustomerController = loader.getController();
            
            addCustomerController.setUserMenuController(userMenuController);
            Stage newStage = new Stage();
            newStage.setTitle("Add Customer");
            newStage.setScene(new Scene(addCustomerContent));
            newStage.show();
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }


    @FXML
    private void handleDeleteCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/deleteCustomer.fxml")); // DELETECustomerController fxml 
            Parent deleteCustomerContent = loader.load();
            DeleteCustomerController deleteCustomerController = loader.getController();
            
            deleteCustomerController.setUserMenuController(userMenuController);
            Stage newStage = new Stage();
            newStage.setTitle("Delete Customer");
            newStage.setScene(new Scene(deleteCustomerContent));
            newStage.show();
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @FXML
    private void refreshTableCustomers(){
        Customer customer = new Customer();
        List<Customer.CustomerDetails> customers = customer.getAllCustomerDetails();
        customersTable.setItems(FXCollections.observableArrayList(customers));
    }

    @FXML
    private void handleChangeCustomer() {
        Customer.CustomerDetails selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            System.out.println("Please select a customer to change");
            customerFeedback.setText("Please select a customer to change");
            customerFeedback.setFill(Color.RED);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/changeCustomerDetails.fxml"));
            Parent changeCustomerContent = loader.load();
            ChangeCustomerController changeCustomerController = loader.getController();
            changeCustomerController.setUserMenuController(userMenuController);
            changeCustomerController.setCustomerDetails(selectedCustomer);
            Stage newStage = new Stage();
            newStage.setTitle("Change Customer");
            newStage.setScene(new Scene(changeCustomerContent));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchForACustomer(){
        String email = searchEmailFieldcus.getText().trim();
        Customer customer = new Customer();

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            customerFeedback.setText("Invalid mail format");
            customerFeedback.setFill(Color.RED);
            return;
        }
        
        if(customer.findCustomer(email)){   // email matchar
            Customer.CustomerDetails customern = customer.getOneCustomerDetails(email);
            customersTable.setItems(FXCollections.observableArrayList(customern));
            searchEmailFieldcus.clear();
        }
        else{
            String feedback = "No customer with " + email + " could be found";
            customerFeedback.setText(feedback);
            customerFeedback.setFill(Color.RED); 
            return;
        }
    }

    @FXML
    private void handleViewCustomerBookings() {
        Customer.CustomerDetails selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            System.out.println("Please select a customer to view bookings");
            customerFeedback.setText("Please select a customer to view bookings");
            customerFeedback.setFill(Color.RED);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLStaff/viewCustomerBookings.fxml"));
            Parent viewCustomerBookingContent = loader.load();
            ViewCustomerBookingsController viewCustomerBookingsController = loader.getController();
            viewCustomerBookingsController.setCustomerDetails(selectedCustomer);
            Stage stage = new Stage();
            stage.setScene(new Scene(viewCustomerBookingContent));
            stage.setTitle("View Room Bookings");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUserMenuController(UserMenuController controller) {
        this.userMenuController = controller;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
