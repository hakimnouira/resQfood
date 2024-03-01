package controllers.fatma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import services.fatma.eventService;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MostAttendedController {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart pieChart;
    private Connection connection;

    private eventService EventService;

    @FXML
    public void initialize() {
        connection = MyDataBase.getInstance().getConnection();

        // Clear existing data from the chart
        barChart.getData().clear();

        try (Statement statement = connection.createStatement();
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
}






