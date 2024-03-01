package controllers.fatma;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.fatma.event;
import services.fatma.eventService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.AnchorPane;

public class addeventController {
    private final eventService es = new eventService();

    @FXML
    private TextField capacitytf;

    @FXML
    private DatePicker datetf;

    @FXML
    private TextField descriptiontf;

    @FXML
    private TextField locationtf;

    @FXML
    private TextField nametf;




    @FXML
    private TextField timetf;

    @FXML
    private ComboBox<String> combo;
    @FXML
    private ImageView event_imageview;
    @FXML
    private AnchorPane main;

    @FXML
    private Button import_btn;
    private event selectedEvent;
    private eventService Eventservice = new eventService() ;
    private Image image ;
    public void Importbtn(){
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));
        File file = openFile.showOpenDialog(main.getScene().getWindow());
        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 127, 128, false, true);

            event_imageview.setImage(image);
        }


    }

    public void populateFieldsWithSelectedEvent(event selectedEvent) {
        if (selectedEvent != null) {

            this.selectedEvent = selectedEvent;
            capacitytf.setText(String.valueOf(selectedEvent.getCapacity()));
            datetf.setValue(selectedEvent.getDate().toLocalDate());
            descriptiontf.setText(selectedEvent.getDescription());
            locationtf.setText(selectedEvent.getLocation());
            nametf.setText(selectedEvent.getName());
            combo.getSelectionModel().select(selectedEvent.getStatus());
            timetf.setText(selectedEvent.getTime());


        }else {
            // Handle case where selectedEvent is null
            System.out.println("Selected event is null.");
        }


    }

    @FXML
    void addevent(ActionEvent event) {
        try {
            String imagePath = data.path;
            if (capacitytf.getText().isEmpty() || datetf.getValue() == null || descriptiontf.getText().isEmpty()
                    || timetf.getText().isEmpty() || locationtf.getText().isEmpty() || nametf.getText().isEmpty() || data.path == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return; // Exit the method if any field is empty
            }

            // Check if imagePath is not null or empty
            if (imagePath != null && !imagePath.isEmpty()) {
                // Load the image from the imagePath

            es.create(new event(Integer.parseInt(capacitytf.getText()), nametf.getText(), locationtf.getText() , combo.getSelectionModel().getSelectedItem()/*getItems().toString()*/, descriptiontf.getText(), Date.valueOf(datetf.getValue()), timetf.getText(),imagePath));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("event added successfully");
            alert.showAndWait();
            capacitytf.setText("");
            datetf.setValue(null);
            descriptiontf.setText("");
            locationtf.setText("");
            nametf.setText("");

            timetf.setText("");
            data.path = null ;
                import_btn.setVisible(true);

            }



        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            System.err.println(e.getMessage());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void showevent(ActionEvent event) {
        try {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/addevent.fxml"));
            //  Parent root= loader.load();

            Parent root = FXMLLoader.load(getClass().getResource("/fatma/Showevent.fxml"));
            nametf.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }


    }
    @FXML
    void submitupdate(ActionEvent event) throws SQLException {
        String imagePath = data.path;
        if (selectedEvent != null) {
            // Update the selected event with the new details
            selectedEvent.setCapacity(Integer.parseInt(capacitytf.getText()));
            selectedEvent.setDate(Date.valueOf(datetf.getValue()));
            selectedEvent.setDescription(descriptiontf.getText());
            selectedEvent.setTime(timetf.getText());
            selectedEvent.setLocation(locationtf.getText());
            selectedEvent.setName(nametf.getText());
            if (imagePath != null && !imagePath.isEmpty()) {
                // Update the event's image path
                selectedEvent.setImage(imagePath);
            }





            // Save the changes to the database

            Eventservice.update(selectedEvent);
        } Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("event updated successfully");
        alert.showAndWait();

    }







    @FXML
    void initialize() {
        List<String> list = new ArrayList<>();
        list.add("upcoming");
        list.add("ongoing");
        list.add("completed");
        combo.setItems(FXCollections.observableList(list));
        populateFieldsWithSelectedEvent(selectedEvent);
        datetf.setValue(LocalDate.now());

        // Restrict date selection to start from the present date
        datetf.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });


    }
}
