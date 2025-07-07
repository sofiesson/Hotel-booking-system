# Hotel-booking-system
Running the program is done through the App.java file at hotel/gradle/app/src/main/java/org/example/App.java.

You will also need to connect your local database to the program by importing the "nyDump.sql" file and changing the LOCALHOST (The name of the table in your database) and PASSWORD (The password you use to connect to your local database) fields at the top of the 4 database files that are found in hotel/gradle/app/src/main/java/org/example/Databases/*

If you have correctly setup the database, you can run the program through the App.java file and login with the username and passwords below

"adm1" + "123", logs in as an Admin
"rec1" + "123", logs in as a Receptionist

Both logins have different menus when logged in. 
The Admin has access to:
- Manage Rooms
    * A table to see all the rooms in the database
    * Adding a room by pressing the Add Room button and  inputting the appropriate information
    * Changing a room by highlighting a room in the table and pressing the Change Details and changing any of the appropriate information
    * Deleting a room by pressing the Delete Room button and inputting a room number

- Staff Details
    * A table to see every staff and their details in the database
    * Adding a new staff member by pressing the Add Staff button and inputting the appropriate information
    * Changing a staff members details by highlighting a staff member in the table and pressing the Change Details button and changing any of the appropriate information
    * Deleting a staff members by pressing the Delete Staff button and inputting a staff username

- Options (Shared with Receptionist)
    * Changing the username of the person who is logged in
    * Changing the password of the person who is logged in

- Log out (Shared with Receptionist)
    * Logs out the user from the application

The Receptionist has access to:
- New Booking
    * A table with all the rooms that are available to book
    * Filtering an available date range by selected two dates and pressing the Show button
    * Booking a room by selecting a room from the table with a filtered date range and pressing the Book button and inputting the remaining appropriate information

- Manage Bookings
    * A table with all the bookings and their details
    * Finding the bookings for a specific day by selecting a date and pressing the Show button
    * Searching for a customers email to find all their bookings by inputting a customer email into the search bar and pressing the Search button
    * Changing the details of a booking by highlighting a booking in the table and pressing the Change Details button and changing any of the appropriate information
    * Deleting a booking by pressing the Delete Booking button and entering a Booking ID

- Customer Details
    * A table with all the customers and their details
    * Searching for a specific customer by entering a customer email into the search bar and pressing the Search button
    * Adding a customer by pressing the Add Customer button and inputting all the appropriate information
    * Changing a customers details by highlighting a customer in the table and pressing the Customer Details button and changing any of the appropriate information
    * Finding all the booking made by a specific customer by highlighting a customer in the table and pressing the Bookings button
    * Deleting a customer by pressing the Delete Customer button and inputting a customers email

- View Rooms
    * A table with all the rooms in the hotel
    * Viewing all the bookings for a specific room by highlighting a room in the table and pressing the Room Bookings button


All of the code is concentrated in the path: hotel/gradle/app/src/main/java/org/example. In this folder you are already familiar with the Databases and the App.java files.

StartMenu.java is the first file that opens the program and shows the welcome screen.
The Controllers folder holds all of the controllers for the different windows of the application. Almost every controller is a new window in the application. These controller files do not directly communicate with the Databases, instead they use methods from the Logic folder.

In the Logic folder are methods that assist the controllers with methods needed for computing, showing, changing information from the database. All the files in the Logic folder are connected to the files in the Databases folder.

As previously said, the Databases folder is responsible for connecting you to your local database, as well as holding different methods that directly view and alter the information in the database. 

The controllers also use .fxml and .png files to show the application. These files can be found at hotel/gradle/app/src/main/resources/org/example
In this folder are 4 different folders. 3 of them hold the .fxml files for the different windows. FXMLAdmin and FXMLStaff are specific windows for Admin and Receptionist (Staff) that are logged in. FXMLBoth holds shared windows that both the Admin and the Receptionist use.
There is also an Images folder that holds all the .png files that are needed for certain windows.