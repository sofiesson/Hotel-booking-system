package org.example.Logic;

import java.util.List;
import org.example.Databases.DatabaseCustomer;
import org.example.Logic.Booking.BookingDetails;

public class Customer {
    private DatabaseCustomer database = new DatabaseCustomer();

    public static class CustomerDetails {
        private String firstName;
        private String lastName;
        private String paymentMethod;
        private String address;
        private String postCode;
        private String phoneNumber;
        private String email;

        public CustomerDetails(String firstName, String lastName, String paymentMethod, String address, String postCode, String phoneNumber, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.paymentMethod = paymentMethod;
            this.address = address;
            this.postCode = postCode;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getPaymentMethod() { return paymentMethod; }
        public String getAddress() { return address; }
        public String getPostCode() { return postCode; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getEmail() { return email; }
    }

    public  CustomerDetails getOneCustomerDetails(String email) {
        return database.getOneCustomerDetails(email);
    }

    public boolean findCustomer(String email){
        if(database.getOneCustomerDetails(email) == null){
            return false;
        }
        return true;
    }

    public boolean deleteCustomer(String email){
        return database.deleteCustomer(email);
    }

    public boolean addCustomer(String firstName, String lastName, String paymentMethod, String address, String postCode, String phoneNumber, String email) {
        return database.addCustomer(firstName, lastName, paymentMethod, address, postCode, phoneNumber, email);
    }

    public List<CustomerDetails> getAllCustomerDetails() {
        return database.getAllCustomerDetails();
    }

    public boolean changeCustomerFirstName(String email, String newFirstName) {
        return database.changeCustomerFirstName(email, newFirstName);
    }

    public boolean changeCustomerLastName(String email, String newLastName) {
        return database.changeCustomerLastName(email, newLastName);
    }

    public boolean changeCustomerPaymentMethod(String email, String newPaymentMethod) {
        return database.changeCustomerPaymentMethod(email, newPaymentMethod);
    }

    public boolean changeCustomerAddress(String email, String newAddress) {
        return database.changeCustomerAddress(email, newAddress);
    }

    public boolean changeCustomerPostCode(String email, String newPostCode) {
        return database.changeCustomerPostCode(email, newPostCode);
    }

    public boolean changeCustomerPhoneNumber(String email, String newPhoneNumber) {
        return database.changeCustomerPhoneNumber(email, newPhoneNumber);
    }

    public boolean changeCustomerEmail(String email, String newEmail) {
        return database.changeCustomerEmail(email, newEmail);
    }
}
