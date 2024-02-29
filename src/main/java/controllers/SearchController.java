package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Comment;
import models.Post;
import services.CommentService;
import services.PostService;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;


import atlantafx.base.util.BBCodeParser;
import atlantafx.sampler.page.ExampleBox;
import atlantafx.sampler.page.OutlinePage;
import atlantafx.sampler.page.Snippet;
import java.net.URI;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;


public class SearchController {



    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField titleKeywordField;

    @FXML
    private TextField contentKeywordField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField endDateField;

    @FXML
    private TextField minCommentCountField;

    @FXML
    private TextField maxCommentCountField;

    @FXML
    private TextField minLikeCountField;

    @FXML
    private TextField maxLikeCountField;

    @FXML
    private Button searchButton;

    @FXML
    private Label resultLabel;

    // Method to handle the search button click event
    @FXML
    void handleSearchButtonClick() {
        // Retrieve search criteria from text fields
        String titleKeyword = titleKeywordField.getText();
        String contentKeyword = contentKeywordField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        int minCommentCount = Integer.parseInt(minCommentCountField.getText());
        int maxCommentCount = Integer.parseInt(maxCommentCountField.getText());
        int minLikeCount = Integer.parseInt(minLikeCountField.getText());
        int maxLikeCount = Integer.parseInt(maxLikeCountField.getText());

        // Perform the search based on the criteria
        String searchResult = performSearch(titleKeyword, contentKeyword, startDate, endDate,
                minCommentCount, maxCommentCount,
                minLikeCount, maxLikeCount);

        // Display the search result

    }

    // Method to perform the search (you can replace this with your actual search logic)
    private String performSearch(String titleKeyword, String contentKeyword,
                                 String startDate, String endDate,
                                 int minCommentCount, int maxCommentCount,
                                 int minLikeCount, int maxLikeCount) {
        // Placeholder for actual search logic
        // You can implement your search logic here and return the result as a string
        return "Search Result: [Placeholder for search result]";
    }
@FXML
public Accordion yyy = new Accordion();

public void waaaa(){





    Supplier<Node> gen = () -> {
        var text = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        var textFlow = new TextFlow(new Text(text));
        textFlow.setMinHeight(100);
        VBox.setVgrow(textFlow, Priority.ALWAYS);
        return new VBox(textFlow);
    };

    var tp1 = new TitledPane("Item 1", gen.get());
    var tp2 = new TitledPane("Item 2", gen.get());
    var tp3 = new TitledPane("Item 3", gen.get());
    System.out.println("Accordion");
    Accordion yy = new Accordion(tp1, tp2, tp3);
    yy.setLayoutX(555);
    yy.setLayoutY(555);
    yy.setMinSize(50,50);
    yy.setMaxHeight(5000);
yy.prefWidth(800);
/*
    var cal = new Calendar();
    cal.setShowWeekNumbers(true);
cal.setLayoutX(500);
cal.setLayoutY(500);

wa.getChildrenUnmodifiable().add(cal);
wa.getChildren().add()

*/



}

@FXML
   TitledPane wa;


}
