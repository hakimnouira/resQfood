package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.event;
import services.ParticipationService;
import services.eventService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class eventCardController {

    @FXML
    private AnchorPane event_card;

    @FXML
    private ImageView event_imageview;

    @FXML
    private Label event_name;
    @FXML
    private Label event_location;
    @FXML
    private Label event_date;
    @FXML
    private Label event_time;
    @FXML
    private Label event_id;


    private Image image;
    private event currentevent;
    private ShowUsersController showUsersController;

    // Set method for ShowUsersController
    public void setShowUsersController(ShowUsersController showUsersController) {
        this.showUsersController = showUsersController;
    }

    public void setEventData(event event) {
        this.currentevent = event;
        setdata(event);
    }

    public void setdata(event eventdata) {


        try {
            event_name.setText(eventdata.getName());
            event_location.setText(eventdata.getLocation());
            event_id.setText(String.valueOf(eventdata.getId()));

            Date sqlDate = eventdata.getDate();
            LocalDate date = sqlDate.toLocalDate();

            // Format the parsed date according to your preferred format
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Set the formatted date to the event_date Label
            event_date.setText(formattedDate);
            event_time.setText(eventdata.getTime());
            String path = "file:///" + eventdata.getImage().replace("\\", "/");
          //  String path = eventdata.getImage();
            if (path != null && !path.isEmpty()) {
                image = new Image(path, 223, 122, false, true);
                event_imageview.setImage(image);
            } else {
                System.err.println("Image path is empty or null.");
            }


       /* String path = eventdata.getImage();
       // String path = "File :" + eventdata.getImage();
        image = new Image (path,223,122 , false , true);
        event_imageview.setImage(image);*/
        } catch (Exception e) {
            // Handle image loading error
            e.printStackTrace();
        }

    }
    /*public int getEventId() {
        return eventD != null ? eventD.getId() : -1; // Assuming 'getId()' returns the event ID
    }*/

    @FXML
    void joinevent(ActionEvent event) {
        if (this.currentevent != null) {

            int id_event = this.currentevent.getId();
            try {
                if (this.currentevent.getUsers_joined() >= this.currentevent.getCapacity()) {
                    // Show an alert indicating that the event is full
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Event Full");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry, this event is already at full capacity.");
                    alert.showAndWait();
                    return; // Exit the method
                }
                int currentUsersJoined = this.currentevent.getUsers_joined();
                this.currentevent.setUsers_joined(currentUsersJoined + 1);

                // Update the event in the database with the incremented users_joined count
                eventService EventService = new eventService();
                EventService.update(this.currentevent);
                ParticipationService participationService = new ParticipationService();
                participationService.addParticipation(id_event);
                System.out.println("Successfully joined event with ID: " + id_event);
                // Optionally update UI to reflect user's participation
                if (showUsersController != null) {
                    showUsersController.generateQRCode(this.currentevent);
                } else {
                    System.err.println("ShowUsersController is not set.");
                }

        } catch (SQLException e) {
                System.err.println("Error joining event: " + e.getMessage());
                // Handle SQL exception appropriately
            }
        } else {
            System.err.println("No event data available to join.");
            // Handle case where event data is not set
        }
        /*if (eventD != null) {
            int id_event = eventD.getId();
            System.out.println("Event ID: " + id_event + " joined.");
            // Call your service method to handle event joining
        } else {
            System.out.println("Invalid event data.");
        }*/

        /*try {
            // Get the user ID and event ID associated with this event card
            int id_event = getEventId(); // Implement this method to retrieve the event ID
            if (id_event <= 0) {
                System.out.println("Invalid event ID: " + id_event);
                return;
            }

            // Add participation entry to the database
            ParticipationService participationService = new ParticipationService();

            participationService.addParticipation( id_event);

            // Optionally, update UI to reflect the user's participation

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }*/
    }

}