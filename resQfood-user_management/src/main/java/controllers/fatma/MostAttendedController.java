package controllers.fatma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import services.fatma.eventService;
import utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MostAttendedController {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart pieChart;


    private eventService EventService;
    @FXML
    private AnchorPane stat;

    @FXML
    public void initialize() {


        try (Connection connection = MyDataBase.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT e.id, e.name, COUNT(p.id_event) AS attendees_count " +
                     "FROM events e " +
                     "LEFT JOIN participations p ON e.id = p.id_event " +
                     "GROUP BY e.id")) {

            // Create a new series for the events
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("id");
                String eventName = resultSet.getString("name");
                int attendeesCount = resultSet.getInt("attendees_count");
                series.getData().add(new XYChart.Data<>(eventName, attendeesCount));
            }

            // Add the series to the chart
            barChart.getData().add(series);
            pieChart.setData(fetchPieChartData());

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    private ObservableList<PieChart.Data> fetchPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (Connection connection = MyDataBase.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT location, COUNT(*) AS event_count FROM events GROUP BY location")) {

            // Populate the pie chart data
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                int eventCount = resultSet.getInt("event_count");
                PieChart.Data dataPoint = new PieChart.Data(location, eventCount);
                pieChartData.add(dataPoint);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        return pieChartData;
    }
/*
    @FXML
    void backto(ActionEvent event) {
        try {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/addevent.fxml"));
            //  Parent root= loader.load();

            Parent root = FXMLLoader.load(getClass().getResource("/fatma/Showevent.fxml"));
            stat.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }

    }
    */

}






