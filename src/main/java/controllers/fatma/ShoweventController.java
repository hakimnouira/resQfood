package controllers.fatma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.fatma.event;
import services.fatma.eventService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ShoweventController {
    private Connection connection;

    private final eventService es = new eventService();
    private ObservableList<event> observableList;

    @FXML
    private TableColumn<event, Integer> capacitycol;

    @FXML
    private TableColumn<event, Date> datecol;

    @FXML
    private TableColumn<event, String> desccol;

    @FXML
    private TableView<event> eventtable;

    @FXML
    private TableColumn<event, String> locatcol;

    @FXML
    private TableColumn<event, String> namecol;

    @FXML
    private TableColumn<event, String> statuscol;

    @FXML
    private TableColumn<event, String> timecol;
    @FXML
    private TableColumn<?, ?> userjcol;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField nameField;


    private eventService Eventservice = new eventService();
    private FilteredList<event> filteredlist;
    private List<event> eventsList;



    @FXML
    void initialize() {


        try {
            eventsList = es.read();
            observableList = FXCollections.observableList(eventsList);

            // Initialize filteredlist
            filteredlist = new FilteredList<>(observableList, p -> true);

            // Bind the TableView to the filteredlist
            eventtable.setItems(filteredlist);


            List<event> eventsList = es.read();
            observableList = FXCollections.observableList(eventsList);
            eventtable.setItems(observableList);
            namecol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            datecol.setCellValueFactory(new PropertyValueFactory<>("Date"));
            timecol.setCellValueFactory(new PropertyValueFactory<>("Time"));
            locatcol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            capacitycol.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
            statuscol.setCellValueFactory(new PropertyValueFactory<>("Status"));
            desccol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            userjcol.setCellValueFactory(new PropertyValueFactory<>("users_joined"));




        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }




    }

    @FXML
    void navigate(ActionEvent event) {
        try {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/addevent.fxml"));
            //  Parent root= loader.load();

            Parent root = FXMLLoader.load(getClass().getResource("/fatma/addevent.fxml"));
            eventtable.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }


    }


    public event getSelectedEvent() {
        event selectedEvent = eventtable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            throw new IllegalArgumentException("No event selected.");
        }
        return selectedEvent;
    }


    @FXML
    void update(ActionEvent event) {
        event selectedEvent = eventtable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/addevent.fxml"));
            // Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fatma/addevent.fxml"));
                Parent root = loader.load();

               /* Parent root = FXMLLoader.load(getClass().getResource("/addevent.fxml"));
                eventtable.getScene().setRoot(root);*/
                // root = loader.load();
                addeventController addEventController = loader.getController();
                addEventController.populateFieldsWithSelectedEvent(selectedEvent);
                eventtable.getScene().setRoot(root);

            } catch (IOException e) {
                System.out.println("error" + e.getMessage());
                e.printStackTrace();
            }


        }
    }


    @FXML
    void deleteevent(ActionEvent event) {
        event selectedEvent = eventtable.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Event");
            alert.setContentText("Are you sure you want to delete this event?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Call the delete method on the instance
                    Eventservice.delete(selectedEvent.getId());
                    eventtable.getItems().remove(selectedEvent);
                    // Optionally, update your UI or perform any other actions after deletion
                } catch (SQLException e) {
                    // Handle error
                    e.printStackTrace(); // or handle it according to your application's error handling strategy
                }
            } else {
                // No event selected, handle this case accordingly (e.g., display an error message)
                System.out.println("No event selected for deletion.");
            }
        }
    }
    @FXML
    void particip(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("/fatma/ShowP.fxml"));
            eventtable.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }

    }
    @FXML
    void searchByDate(ActionEvent event) {
        System.out.println("Search by date button clicked");
        LocalDate searchDate = datePicker.getValue(); // Get search date from datePicker

        // Filter the observableList based on the search date
        ObservableList<event> filteredEvents = observableList.filtered(Event -> {
            LocalDate eventDate = Event.getDate().toLocalDate(); // Assuming getDate() returns a java.sql.Date
            return eventDate.equals(searchDate);
        });

        // Update the eventtable with the filtered list
        eventtable.setItems(filteredEvents);

    }

    @FXML
    void searchByName(ActionEvent event) {
        System.out.println("Search by name button clicked");
        String searchQuery = nameField.getText().trim().toLowerCase(); // Get search query from nameField

        // Filter the observableList based on the search query
        ObservableList<event> filteredEvents = observableList.filtered(Event -> Event.getName().toLowerCase().contains(searchQuery));

        // Update the eventtable with the filtered list
        eventtable.setItems(filteredEvents);

    }
    private void showEventsInTable(List<event> events) {
        ObservableList<event> observableEvents = FXCollections.observableArrayList(events);
        eventtable.setItems(observableEvents);
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    }

