package controllers.fatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.fatma.event;
import services.fatma.eventService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.List;


public class CalendarController {
    ZonedDateTime dateFocus;
    ZonedDateTime today;
    @FXML
    private FlowPane calendar;

    @FXML
    private Text month;

    @FXML
    private Text year;
    private YearMonth currentYearMonth;

    private eventService EventService ;
    public CalendarController() {
        EventService = new eventService();
        currentYearMonth = YearMonth.now();
    }
    @FXML
    void forwardOneMonth(ActionEvent event) {
        currentYearMonth = currentYearMonth.plusMonths(1);
        try {
          drawCalendar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void backOneMonth(ActionEvent event) {
        currentYearMonth = currentYearMonth.minusMonths(1);
        try {
            drawCalendar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    private void updateCalendar() throws SQLException {
        year.setText(String.valueOf(currentYearMonth.getYear()));
        month.setText(currentYearMonth.getMonth().toString());

        calendar.getChildren().clear();

        int daysInMonth = currentYearMonth.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            Text dayLabel = new Text(String.valueOf(i));
            eventService service = new eventService();
            List<event> events = service.getEventsByDate(Date.valueOf(currentYearMonth.atDay(i)));
            for (event e : events) {
                dayLabel.setText(dayLabel.getText() + "\n" + e.getName() + " - " + e.getTime());
            }
            calendar.getChildren().add(dayLabel);
        }
    }*/
    private void drawCalendar() throws SQLException {
        year.setText(String.valueOf(currentYearMonth.getYear()));
        month.setText(currentYearMonth.getMonth().toString());

        calendar.getChildren().clear();

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = currentYearMonth.lengthOfMonth();
        int dateOffset = ZonedDateTime.of(currentYearMonth.getYear(), currentYearMonth.getMonthValue(), 1, 0, 0, 0, 0, ZonedDateTime.now().getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        LocalDate currentDateLocalDate = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonth(), currentDate);
                        eventService service = new eventService();
                        List<event> events = service.getEventsByDate(Date.valueOf(currentDateLocalDate));
                        if (!events.isEmpty()) {
                            Text eventText = new Text();
                            for (event e : events) {
                                eventText.setText(eventText.getText() + e.getName() + " - " + e.getTime() + "\n");
                            }
                            eventText.setStyle("-fx-font-weight: bold"); // Make the text bold
                            double eventTextTranslationY = (rectangleHeight / 2) * 0.75;
                            eventText.setTranslateY(eventTextTranslationY);
                            stackPane.getChildren().add(eventText);
                            rectangle.setFill(Color.RED);
                        }
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private boolean hasEvents(LocalDate date) {
        try {
            // Assuming you have a method in your eventService to get events by date
            eventService eservice = new eventService();
            List<event> events = eservice.getEventsByDate(Date.valueOf(date));

            // Check if there are any events for the given date
            return !events.isEmpty();
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    void initialize() {

        try {

            drawCalendar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        }




