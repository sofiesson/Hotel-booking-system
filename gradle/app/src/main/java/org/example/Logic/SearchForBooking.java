package org.example.Logic;

import org.example.Databases.DatabaseBooking;

public class SearchForBooking {
    
    private DatabaseBooking databas; 

    // Standardkonstruktor
    public SearchForBooking() {
        this.databas = new DatabaseBooking();
    }

    // Konstruktor för testning 
    public SearchForBooking(DatabaseBooking databas) {
        this.databas = databas;
    }

    

    

}
