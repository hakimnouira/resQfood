package controllers.feriel;

import controllers.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import models.feriel.User;
import services.feriel.UserService;
import toolkit.MyTools;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class StatsFerielController extends Controller {
    @FXML
    private PieChart PieChart;

    @FXML
    private PieChart pieChartArea;

    @FXML
    private PieChart pieChartAreaDon;

    @FXML
    private PieChart pieChartAreaVol;


    @FXML
    private Button btn_testim;

    @FXML
    private Button btn_users;

    @FXML
    private Text fnametxt;

    @FXML
    private Pane inner_panel;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private AnchorPane side_ankerpane;

    UserService us=new UserService();
    List<User> listUsers= new ArrayList<>();

    public void initialize(){

        Map<String, Long> map = calculateRoleStatistics();
        //System.out.println(map);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        map.forEach((role, count) -> pieChartData.add(new PieChart.Data(role+"  :  "+count, count)));

        // update PieChart
        PieChart.setData(pieChartData);
        PieChart.setTitle("Users per Role");

        displayPie(pieChartArea,"Participant");
        displayPie(pieChartAreaVol,"Volunteer");
        displayPie(pieChartAreaDon,"Donor");


    }
    public void logoutDash(ActionEvent actionEvent) {
        UserService.loggedIn=null;
       MyTools.goTo("/feriel/LogIn.fxml",btn_users);
    }

    public void eventsBt(ActionEvent actionEvent) {
    }

    public void stockBt(ActionEvent actionEvent) {
    }

    public void forumBt(ActionEvent actionEvent) {
    }

    public void donationBt(ActionEvent actionEvent) {
    }
    List<User> getListUsers(){
        try {
            listUsers= us.read();
            System.out.println(listUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(listUsers);
    }


    /**
     *
      * @return Map<String, Long> role: nbr of users
     */
    public  Map<String, Long> calculateRoleStatistics() {
        return getListUsers().stream()
                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
    }

    /**
     *
     * @param pieChart (PieChart) init chart
     * @param role (String) partici,donor volunteer
     */
    void displayPie(PieChart pieChart,String role){
    // Calc statistics per areas
    Map<String, Long> areaStatistics = calculateAreaStatistics(role);

    // Sort the areas by participant count in descending order and limit to top 5
    List<Map.Entry<String, Long>> sortedAreas = areaStatistics.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .toList();

    // Create data for the pie chart
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    sortedAreas.forEach(entry -> pieChartData.add(new PieChart.Data(entry.getKey().concat(":").concat(String.valueOf(entry.getValue())), entry.getValue())));

    // Set data to the pie chart
    pieChart.setData(pieChartData);
    pieChart.setTitle("Top 5 Areas with Most "+role);
    }


    /**
     *
     * @param role String
     * @return Map<String, Long> : nb of ppl role by area
     */
    private Map<String, Long> calculateAreaStatistics(String role) {
        return getListUsers().stream()
                .filter(user -> user.getRole().equals(role))
                .collect(Collectors.groupingBy(User::getArea, Collectors.counting()));
    }




    public void goUsers(ActionEvent actionEvent) {
        MyTools.goTo("/feriel/DisplayUsers.fxml",btn_testim);
    }
}
