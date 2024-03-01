package controllers.fatma;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.fatma.event;
import utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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


    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private Button back;
    @FXML
    private Button print;
    private eventCardController EventCardController;
    public void setEventCardController(eventCardController EventCardController) {
        this.EventCardController = EventCardController;
    }
    private ObservableList<event> listData = FXCollections.observableArrayList();


    public ObservableList<event> GetData() {

        String sql = "SELECT * FROM events";

        ObservableList<event> ClistData = FXCollections.observableArrayList();
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

        return ClistData;
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
                loader.setLocation(getClass().getResource("/fatma/eventcard.fxml"));
                AnchorPane pane = loader.load();

                eventCardController eventC = loader.getController();
                eventC.setShowUsersController(this);
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


    @FXML
    void filter(ActionEvent event) {
        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Filter events for future dates
        List<event> futureEvents = listData.stream()
                .filter(Event -> Event.getDate().toLocalDate().isAfter(currentDate))
                .collect(Collectors.toList());

        // Clear existing event cards from the GridPane
        menu_gridpane.getChildren().clear();

        // Add event cards for filtered events to the GridPane
        int row = 0;
        int column = 0;
        for (event Event : futureEvents) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fatma/eventcard.fxml"));
            try {
                AnchorPane pane = loader.load();
                eventCardController eventCardController = loader.getController();
                eventCardController.setShowUsersController(this);
                eventCardController.setEventData(Event);
                menu_gridpane.add(pane, column++, row);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                print.setVisible(false);
                back.setVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void printQRCode(Image qrImage) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(menu_form.getScene().getWindow())) {
            ImageView imageView = new ImageView(qrImage);
            boolean success = printerJob.printPage(imageView);
            if (success) {
                printerJob.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        }
    }

    @FXML
    void printQRCode(ActionEvent event) {
        if (qrCodeImageView.getImage() == null) {
            showAlert(Alert.AlertType.ERROR, "Please generate QR Code first.");
            return;
        }

        // Print the QR code image
        printQRCode(qrCodeImageView.getImage());
    }

       /* // Check if QR Code has been generated
        if (qrCodeImageView.getImage() == null) {
            showAlert(Alert.AlertType.ERROR, "Please generate QR Code first.");
            return;
        }

        // Allow user to choose file location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save QR Code Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(new Stage());

        // Save QR Code image to file
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(qrCodeImageView.getImage(), null), "png", file);
                showAlert(Alert.AlertType.INFORMATION, "QR Code saved successfully.");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Failed to save QR Code.");
            }
        }
    }

        */

    public void generateQRCode(event joinedEvent) {


        // Generate QR Code for each event

        try {
            String eventData = joinedEvent.toString();
            // Generate QR Code for the current event

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(eventData, BarcodeFormat.QR_CODE, 250, 250, hints);

            // Convert BitMatrix to Image
            Image qrImage = createFXImageFromBitMatrix(bitMatrix);

            // Display QR Code
            qrCodeImageView.setImage(qrImage);
            print.setVisible(true);
            back.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to generate QR Code for event: " + joinedEvent.getName());
        }
    }



    private Image createFXImageFromBitMatrix(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        javafx.scene.image.WritableImage image = new javafx.scene.image.WritableImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.getPixelWriter().setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return image;
    }


    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void goback(ActionEvent event) {
        qrCodeImageView.setImage(null);
        print.setVisible(false);
        back.setVisible(false);

    }

    public void initialize() {
        menuDisplayCard();
        print.setVisible(false);
        back.setVisible(false);


    }
}



