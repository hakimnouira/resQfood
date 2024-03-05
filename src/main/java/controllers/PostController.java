package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;



import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import objects.Account;
import objects.Post;
import objects.PostAudience;
import objects.Reactions;
import okhttp3.*;
import org.json.JSONObject;
import services.CommentService;
import services.LikesService;
import services.PostService;


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.*;



import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class PostController implements Initializable {
    @FXML
    public HBox thisone;
    String jsonResponse = "{\n" +
            "  \"audioFormat\": \"mp3\",\n" +
            "  \"paragraphChunks\": [\"This is paragraph 1.\", \"This is paragraph 2.\"],\n" +
            "  \"voiceParams\": {\n" +
            "    \"name\": \"John\",\n" +
            "    \"engine\": \"neural\",\n" +
            "    \"languageCode\": \"en-US\"\n" +
            "  }\n" +
            "}";
    public HBox getThisone(){return thisone ;}

    @FXML VBox comment_section;
    public VBox get_comment_section(){return all ;


    }
    @FXML
    public VBox all;


    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbShares;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgWow;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgAngry;

    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;
    public int userid;

    @FXML
    private Label cat;
    private long startTime = 0;
    private Reactions currentReaction;
    private Post post;

    @FXML
    public VBox delete1;



    @FXML
    public ChoiceBox<String> cat1;

    @FXML
    public TextArea caption1;

    @FXML
    public TextArea img;




    public models.Post update_button_pressed() throws SQLException {
        System.out.println(cat1.getId());
        int A;

        if (title.getText() == null || title.getText().trim().isEmpty()) {
            // Display an alert indicating that the title is required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a title.");
            alert.showAndWait();
            return null;
        }

        if (caption1.getText() == null || caption1.getText().trim().isEmpty()) {
            // Display an alert indicating that the caption is required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a caption.");
            alert.showAndWait();
            return null;
        }

        // Check for inappropriate words in the caption
        boolean containsBadWords = checkForBadWords(caption1.getText());
        System.out.println(caption1.getText());
        if (containsBadWords) {
            // Display an alert indicating that the caption contains inappropriate words
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inappropriate Content");
            alert.setHeaderText(null);
            alert.setContentText("The caption contains inappropriate words.");
            alert.showAndWait();
            return null;
        }

        // Check if the img field is empty or null
        if (img.getText() == null || img.getText().trim().isEmpty()) {
            // Display an alert indicating that the img field is required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid image URL.");
            alert.showAndWait();
            return null;
        } else {
            // Validate if the URL ends with a common image file extension
            String imageUrl = img.getText().trim();
            if (!imageUrl.matches("^.*\\.(jpg|jpeg|png|gif)$")) {
                // Display an alert indicating that the provided URL is not an image
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid image URL.");
                alert.showAndWait();
                return null;
            }
        }

        switch (cat1.getValue()) {
            case "News":
                A=1;
                break;
            case "Events":
                A=2;
                break;
            case "Discussion":
                A=3;
                break;
            default:
                A=4;
        }

        models.Post p = new models.Post(data.getPostId(), data.getUserId(), A, title.getText(), caption1.getText(), img.getText());

        return p;
    }

    // Method to check for inappropriate words using Bad Words API
    public boolean checkForBadWords(String text) {
        String apiKey = "c04594c4f1msh2b4d264a498950fp12d670jsnbf94b1272a2a";
        String host = "neutrinoapi-bad-word-filter.p.rapidapi.com";
        String endpoint = "https://neutrinoapi-bad-word-filter.p.rapidapi.com/bad-word-filter";

        try {
            // Encode the text to be checked
            String encodedText = URLEncoder.encode(text, "UTF-8");

            // Construct the API request
            String requestBody = "content=" + encodedText;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", host)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send the API request and process the response
            HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            if (statusCode == 200) {
                // Check if the response contains any bad words
                System.out.println(httpResponse.body());
                System.out.println( Boolean.parseBoolean(httpResponse.body()));
                String responseBody = httpResponse.body();
                if (responseBody != null && responseBody.contains("true")) {
                    return true;
                }

                return false;
               // return Boolean.parseBoolean(httpResponse.body());

            } else {
                System.out.println("Error: " + statusCode);
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    public void onLikeContainerPressed(MouseEvent me){
        startTime = System.currentTimeMillis();

    }
    @FXML
public void img_change( ){
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        Image imga = new Image(img.getText());

    imgPost.setImage(imga);
}
    @FXML
    public void onLikeContainerMouseReleased(MouseEvent me) throws SQLException {
        if(System.currentTimeMillis() - startTime > 500){
            reactionsContainer.setVisible(true);
        }else {
            if(reactionsContainer.isVisible()){
                reactionsContainer.setVisible(false);
            }
            if(currentReaction == Reactions.NON){
                setReaction(Reactions.LIKE);
         if (user_liked==0) {
                    LS.addLike(data.getPostId(), userid, "LIKE");}//kifkif


            }else{
                setReaction(Reactions.NON);
               LS.deleteLike(data.getPostId(),userid ) ;// badlha

                user_liked=0;

            }



        }
        post.setTotalReactions(LS.getTotalLikesForPost(data.getPostId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        setmost_rection(data.getPostId());
    }

    @FXML
    Label titre;
    @FXML
    public void onReactionImgPressed(MouseEvent me) throws SQLException {
        switch (((ImageView) me.getSource()).getId()) {
            case "imgLove":
                setReaction(Reactions.LOVE);
                if (user_liked == 0) {
                    LS.addLike(data.getPostId(), userid, "LOVE");
                } else {
                    LS.updateLike(data.getPostId(), userid, "LOVE");
                }

                break;
            case "imgCare":
                setReaction(Reactions.CARE);
                if (user_liked == 0) {
                    LS.addLike(data.getPostId(), userid, "CARE");
                } else {
                    LS.updateLike(data.getPostId(), userid, "CARE");
                }
                break;
            case "imgHaha":
                setReaction(Reactions.HAHA);
                if (user_liked == 0) {
                    LS.addLike(data.getPostId(), userid, "HAHA");
                } else {
                    LS.updateLike(data.getPostId(), userid, "HAHA");
                }
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                if (user_liked == 0) {
                    LS.addLike(data.getPostId(), userid, "WOW");
                } else {
                    LS.updateLike(data.getPostId(),userid, "WOW");
                }
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                if (user_liked == 0) {
                    LS.addLike(data.getPostId(), userid, "SAD");
                } else {
                    LS.updateLike(data.getPostId(), userid, "SAD");
                }
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                if (user_liked == 0) {
                    LS.addLike(data.getPostId(),userid, "ANGRY");
                } else {
                    LS.updateLike(data.getPostId(), userid, "ANGRY");
                }
                break;
            default:
                setReaction(Reactions.LIKE); if (user_liked==0) {
                LS.addLike(data.getPostId(), userid, "LIKE");
            } else {
                LS.updateLike(data.getPostId(), userid, "LOVE");
            }
                break;
        }
        reactionsContainer.setVisible(false);
        user_liked=1;
        setmost_rection(data.getPostId());
        post.setTotalReactions(LS.getTotalLikesForPost(data.getPostId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setReaction(Reactions reaction) throws SQLException {
        Image image = new Image("file:src/main/java/"+reaction.getImgSrc());


        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));
// code hatha ma3jbnich akel 7aja ow arj3

        if(currentReaction == Reactions.NON){
          //  post.setTotalReactions(post.getTotalReactions() + 1);
        }
// as i said
        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
           // post.setTotalReactions(post.getTotalReactions() - 1);
        }

        post.setTotalReactions(LS.getTotalLikesForPost(data.getPostId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        setmost_rection(data.getPostId());
    }


@FXML
    public ImageView mostliked1 ;

    @FXML
    public ImageView mostliked2 ;

    @FXML
    public ImageView mostliked3 ;

    LikesService LS = new LikesService();
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<>(map.entrySet());

        // Sort the list based on values
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Populate a LinkedHashMap to maintain the insertion order
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

public void setmost_rection(int id) throws SQLException {


    Map<String, Integer> sortedMap = new TreeMap<> (LS.readLikesForPost(id ));
    TreeMap<Integer, String> sortedByValueMap = new TreeMap<>();
    sortedMap= sortByValues(sortedMap);

   // System.out.println( sortedByValueMap.toString() );



    int count = 0;
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
        if (count >= 3) {
            break;
        }
        Image image ;


        String imgSrc = "file:src/main/java/" + Reactions.valueOf(entry.getKey()).getImgSrc();

        switch (count+1 ) {
            case 1: mostliked1.setImage(new Image(imgSrc)); break;
            case 2 :mostliked2.setImage(new Image(imgSrc));break;
            case 3 :mostliked3.setImage(new Image(imgSrc)); break;


        }

        count++;
    }
    String imgSrc = "file:src/main/java/abyeth_mini.jpg";

    for (int i = count; i < 3 ; i++) {
        switch (count+1 ) {
            case 1: mostliked1.setImage(new Image(imgSrc)); break;
            case 2 :mostliked2.setImage(new Image(imgSrc));break;
            case 3 :mostliked3.setImage(new Image(imgSrc)); break;


        }
    }



}@FXML
    Button play;

    public static void getTextToSpeechResponse(String text) throws IOException, InterruptedException, UnirestException {
        String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNGNiZTRmMGMtZDNkNi00M2E0LWIyNzMtYmQ4M2FjZDU1Y2NkIiwidHlwZSI6ImFwaV90b2tlbiJ9.LylbBwbYfKT6Dry_gNjCRLQSW6IE-x4WyC7mHe9eT2g";
        String url = "https://api.edenai.run/v2/audio/text_to_speech";
        String payload = "{\"providers\":\"amazon,google,ibm,microsoft\",\"language\":\"fr\",\"text\":\"Bonjour Je m'appelle Jane\",\"option\":\"FEMALE\",\"fallback_providers\":\"\"}";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    JSONObject jsonResponse = new JSONObject(response);
                    String audioUrl = jsonResponse.getString("audio_resource_url");
                    Media media = new Media(audioUrl);
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                })
                .exceptionally(e -> {
                    System.err.println("Error: " + e.getMessage());
                    return null;
                });
    }


    private int user_liked=0;
public void setData(Post post) throws MalformedURLException, SQLException {
        this.post = post;
        Image img;
        setmost_rection(data.getPostId());


        // img = new Image("file:src\\main\\java\\img\\user.png");



        if ( LS.hasUserLikedPost(data.getPostId(),userid)) {
            Image image = new Image("file:src/main/java/"+Reactions.valueOf(     LS.currentRection(data.getPostId(), userid)).getImgSrc());


            imgReaction.setImage(image);
            user_liked=1;


        }

        img = new Image("file:src/main/java/" + post.getAccount().getProfileImg());
        System.out.println(img.getUrl());
        imgProfile.setImage(img);
        switch (data.getCategoryId()) {
        case 1:
        cat.setText("NEWS");
        break;
        case 2:
        cat.setText("Events");
        break;
        case 3:
        cat.setText("Discussion");
        break;
            default:     cat.setText("......... problem");
    }
        username.setText(post.getAccount().getName());
        if(post.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }

        date.setText(post.getDate());
        if(post.getAudience() == PostAudience.PUBLIC){
            img = new Image("file:src/main/java/"+PostAudience.PUBLIC.getImgSrc());
        }else{
            img = new Image("file:src/main/java/"+PostAudience.FRIENDS.getImgSrc());
        }
        audience.setImage(img);

        if(post.getCaption() != null && !post.getCaption().isEmpty()){
            caption.setText(post.getCaption());
        }else{
            caption.setManaged(false);
        }

        if(data.getImage() != null && !post.getImage().isEmpty()){
            System.out.println(data.getImage());
            img = new Image(data.getImage());
            imgPost.setImage(img);
        }else{
            img = new Image(String.valueOf(new URL("https://assets-global.website-files.com/64949e4863d96e26a1da8386/64b94c7e02162f5cc666b317_633604c562868a10ab4c7163_uMk2yhmH04IjjAHWzOeM_tATsEn6kaJHIXikeFABZPv7G2VpYt7NdACThY1yQcBUw7KQWXpOiDBqumo3FVIKMpqBNPWO_U-5gGreQMZ23EFrqbhAXMRWtPU-zY7XdyZ0HfsgQV7FcafaziA6lddfUYoOERc3k2_UJ9M90FcOpdC_iIDDZfAZqzWDmg.png")));
            imgPost.setImage(img);
           /* imgPost.setVisible(false);
            imgPost.setManaged(false);*/
        }


    post.setTotalReactions(LS.getTotalLikesForPost(data.getPostId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares()+" shares");

        currentReaction = Reactions.LIKE;
    }


    private final PostService ps = new PostService();
    private Post getPost() throws SQLException {

        Post post = new Post();
        Account account = new Account();

      //  titre.setText(data.getTitle());
        if (data.getTitle() == "") {

            account.setName("");
        }else
      account.setName(data.getTitle());

        account.setProfileImg("img/user.png");
        account.setVerified(true);
        post.setAccount(account);

        post.setDate(data.getCreated_at().toString() ) ;
        if (!Objects.equals(data.getCreated_at().toString(), data.getUpdated_at().toString())) {
        post.setDate(data.getCreated_at().toString());
            post.setCaption("updated at "+ data.getUpdated_at().toString() + "                                                                  "+ data.getContent() );
        } else {
            post.setDate(data.getCreated_at().toString()) ;
            post.setCaption(data.getContent());
        }

        post.setAudience(PostAudience.PUBLIC);
        //post.setCaption(data.getContent());
        post.setImage("/img/img2.jpg");

        post.setTotalReactions(LS.getTotalLikesForPost(data.getPostId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        post.setNbComments(CS.count_comment(data.getPostId()));
        post.setNbShares(3);

        return post;
    }

CommentService CS=new CommentService();
    private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // setData(getPost());
    }
models.Post data;
    public void receiveData(models.Post data) throws MalformedURLException, SQLException {
        this.data=data;
        setData(getPost());
        titre.setText(data.getTitle());


        share.setOnMouseClicked(event ->         {
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            String description = data.getContent();
            String imageUrl = data.getImage();


            // Encode the description and image URL
            try {
                description = URLEncoder.encode(description, "UTF-8");
                imageUrl = URLEncoder.encode(imageUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Construct the Facebook share URL with the encoded description and image URL
            String shareUrl = "https://www.facebook.com/sharer/sharer.php?u=" + imageUrl + "&quote=" + description;
            webEngine.load(shareUrl);
            openWebPage(shareUrl);
          post.setNbShares(post.getNbShares()+1);
//wiow.getChildren().add(shareUrl);
        });


    }
    @FXML
VBox wiow;


    @FXML
  public HBox  share;

    public HBox getShare() {
        return share;
    }

    public void receiveData1(models.Post data) throws SQLException {
        this.data=data;
        setData1(getPost());


    }


    public VBox getdeleteButton() {
        return delete1;
    }

@FXML


   public VBox getvboxall () {
       VBox vbox = new VBox();
       vbox.getChildren().addAll(all.getChildren());
       return vbox;

       }



    public void updateplus(){
        VBox container = getvboxall();


        for (Node node : container.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;

                // Create a text field with the same text as the label
                TextField textField = new TextField(label.getText());

                // Set the preferred width and height of the text field
                textField.setLayoutX(label.getLayoutX());
                textField.setLayoutY(label.getLayoutY());
                textField.setPrefWidth(label.getWidth());
                textField.setPrefHeight(label.getHeight());
                textField.setFont(label.getFont());
                textField.setShape((Shape) label.getGraphic());
                // Replace the label with the text field
                container.getChildren().set(container.getChildren().indexOf(node), textField);
            }
        }




    }



@FXML TextField title;

    public void setData1(Post post) {

        this.post = post;
        Image img;
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(post.getAccount().getProfileImg());
        title.setText(data.getTitle());
        // img = new Image("file:src\\main\\java\\img\\user.png");
      this.img.setWrapText(true);
this.caption1.setWrapText(true);
        img = new Image("file:src/main/java/" + post.getAccount().getProfileImg());
        System.out.println(img.getUrl());
        imgProfile.setImage(img);

        switch (data.getCategoryId()) {
            case 1:
                cat1.setValue("News");
                break;
            case 2:
                cat1.setValue("Events");
                break;
            case 3:
                cat1.setValue("Discussion");
                break;
            default:     cat1.setValue("Discussion");
        }
        username.setText(post.getAccount().getName());
        if(post.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }

        date.setText(post.getDate());
        if(post.getAudience() == PostAudience.PUBLIC){
            img = new Image("file:src/main/java/"+PostAudience.PUBLIC.getImgSrc());
        }else{
            img = new Image("file:src/main/java/"+PostAudience.FRIENDS.getImgSrc());
        }
        audience.setImage(img);

        if(post.getCaption() != null && !post.getCaption().isEmpty()){
            caption1.setText(post.getCaption());
        }else{
         //   caption1.setManaged(false);
            caption1.setText("                ");
        }
        this.img.setText(data.getImage());

        if(post.getImage() != null && !post.getImage().isEmpty() && post.getImage()!="" ){
            img = new Image(data.getImage());
            imgPost.setImage(img);
        }else{
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares()+" shares");

        currentReaction = Reactions.LIKE;
    }


    public void check_me(ActionEvent actionEvent) {
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        Image imga = new Image(img.getText());
        imgPost.setImage(imga);

    }

    public void update_press(ActionEvent actionEvent) {


    }
@FXML
Label comment;
    public Label getcomment2() {
        return comment;
    }
}
