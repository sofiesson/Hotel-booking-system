package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.example.Logic.Booking;
import org.example.Logic.BookingInfo;
import org.example.Logic.Customer;
import org.example.Logic.IncompleteBooking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AddBookingController {

    @FXML
    private TextField peopleAmountField;
    @FXML
    private TextField customerEmailField;
    @FXML
    private RadioButton paidYesCheckBox;
    @FXML
    private RadioButton paidNoCheckBox;

    @FXML
    private Text feedbackText;
    @FXML
    private TableView<BookingInfo> bookingTableView;
    @FXML
    private TableColumn<BookingInfo, String> rowHeaderColumn; // Titles on the side
    @FXML
    private TableColumn<BookingInfo, String> valueColumn;

    @FXML 
    private TextField checkInDate;

    @FXML
    private TextField checkOutDate;

    private Stage stage;

    private IncompleteBooking currentBooking; // Spara den aktuella bokningen


    @FXML
    public void initialize() {
        bookingTableView.setPrefWidth(265);  // Set your desired width
        bookingTableView.setPrefHeight(260);
        // Set up columns
        rowHeaderColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        rowHeaderColumn.setPrefWidth(200);  // Width for row header column
        valueColumn.setPrefWidth(400); 

        // Style the first column as row headers
        rowHeaderColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-font-weight: bold; -fx-background-color: #dcdcdc; -fx-alignment: center;");
                }
            }
        });
    }


        // Integers to string
         // Metod för att sätta bokningsdetaljer när fönstret öppnas
    public void setBookingDetails(IncompleteBooking incompleteBooking) {
        this.currentBooking = incompleteBooking; // Spara bokningen 

        String roomID = Integer.toString(incompleteBooking.getRoomID());
        String roomnumber = Integer.toString(incompleteBooking.getRoomNumber());
        String roomType = incompleteBooking.getRoomType();
        String bed = Integer.toString(incompleteBooking.getBeds());
        String siz = Integer.toString(incompleteBooking.getSize());
        String pric = Double.toString(incompleteBooking.getPrice());
        
        // Hämtar och konverterar check in- och out date 
        LocalDate checkInD = incompleteBooking.getCheckInDate();
        LocalDate checkOutD = incompleteBooking.getCheckOutDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String checkInDay = (checkInD != null) ? checkInD.format(formatter) : "N/A";
        String checkOutDay = (checkOutD != null) ? checkOutD.format(formatter) : "N/A";



        checkInDate.setText(checkInDay);
        checkOutDate.setText(checkOutDay);
        checkInDate.setEditable(false);
        checkOutDate.setEditable(false);



        // Sample Data (Replace with real booking details)
        ObservableList<BookingInfo> data = FXCollections.observableArrayList(
                new BookingInfo("Room ID", roomID),
                new BookingInfo("Number", roomnumber),
                new BookingInfo("Type", roomType),
                new BookingInfo("Beds", bed),
                new BookingInfo("Size", siz),
                new BookingInfo("Price", pric)
        );
        bookingTableView.setItems(data);

        // Set a fixed row height for each row
        double rowHeight = 40; // Adjust this value to set row height
        bookingTableView.setFixedCellSize(rowHeight);

        // Ensure the columns take up the full available width
        bookingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
    }
    

  
    @FXML
    private void confirmBooking() {
        

        int peopleAmount;
        try {
            peopleAmount = Integer.parseInt(peopleAmountField.getText());
            if (peopleAmount <= 0) {
                System.out.println("The number of people must be greater than 0");
                feedbackText.setText("The number of people must be greater than 0");
                feedbackText.setFill(Color.RED);
                return;
            }
            else if(peopleAmount > 5){
                System.out.println("The number of people must not exceed 5");
                feedbackText.setText("The number of people must not exceed 5");
                feedbackText.setFill(Color.RED);
                return;

            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number of people.");
            feedbackText.setText("Please enter a valid number of people. Use numbers");
            feedbackText.setFill(Color.RED);
            return;
        }

        String customerEmail = customerEmailField.getText();
        Customer customer = new Customer();

          if (!customerEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            feedbackText.setText("Invalid Email Format");
            feedbackText.setFill(Color.RED);
            return;
          }
            else if(!customer.findCustomer(customerEmail)) {
                feedbackText.setText("Customer not found or could not be deleted.");
                feedbackText.setFill(Color.RED);
            }
        

        if (!paidYesCheckBox.isSelected() && !paidNoCheckBox.isSelected()) {
            System.out.println("Fel: Välj om bokningen är betald eller ej.");
            feedbackText.setText("Please select wheter the booking is paid or not");
            feedbackText.setFill(Color.RED);

            return;
        }
        boolean isPaid = paidYesCheckBox.isSelected();



        // Anropa din befintliga bookRoom-metod
        //DatabaseBooking databaseBooking = new DatabaseBooking();
        Booking booking = new Booking();
        Boolean result = booking.bookRoom(
                currentBooking.getCheckInDate().atStartOfDay(), currentBooking.getCheckOutDate().atStartOfDay(), peopleAmount, 1, currentBooking.getRoomType(), 
                customerEmail, currentBooking.getRoomID(), isPaid
                
        );

        System.out.println(result);

        // Om bokningen lyckades, stäng fönstret
        if (result == true) {
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setParentController(AvailableBookingsController availableBookingsController) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setParentController'");
    }
}
