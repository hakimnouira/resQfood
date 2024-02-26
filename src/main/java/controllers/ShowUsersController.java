package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.event;
import utils.MyDataBase;
import javafx.scene.control.ScrollPane;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShowUsersController {
    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridpane;

    @FXML
    private ScrollPane menu_scrollPane;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;


    private ObservableList<event> listData = FXCollections.observableArrayList() ;

    public ObservableList<event> GetData() {

        String sql = "SELECT * FROM events";

        ObservableList<event> ClistData = FXCollections.observableArrayList() ;
        connect = MyDataBase.getInstance().getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            event e;

            while (result.next()) {
                e = new event(

                        result.getInt("id"),
                        result.getInt("capacity"),
                        result.getString("name"),
                        result.getString("location"),
                        result.getString("status"),
                        result.getString("description"),
                        result.getString("time"),

                        result.getDate("date"),





                        result.getString("image")
                );

                ClistData.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ClistData ;
    }
    public void menuDisplayCard() {

        listData.clear();
        listData.addAll(GetData());

        int row = 0;
        int column = 0;

        menu_gridpane.getChildren().clear();
        menu_gridpane.getRowConstraints().clear();
        menu_gridpane.getColumnConstraints().clear();
        for (int q = 0; q < listData.size(); q++) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/eventcard.fxml"));
                AnchorPane pane = loader.load();
                eventCardController eventC = loader.getController();
                eventC.setdata(listData.get(q));


                // Set event data for each EventCardController
                eventC.setEventData(listData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridpane.add(pane, column++, row);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }










   public  void initialize(){
        menuDisplayCard();


    }

}
