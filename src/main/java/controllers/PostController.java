package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import objects.Account;
import objects.Post;
import objects.PostAudience;
import objects.Reactions;
import services.CommentService;
import services.PostService;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PostController implements Initializable {
    @FXML
    public HBox thisone;
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




    public models.Post update_button_pressed() {
        System.out.println(cat1.getId());
int A;


        if (title.getText() == null || title.getText().trim().isEmpty()) {
            // Display an alert indicating that the title is required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a title.");
            alert.showAndWait();

            // Return null or handle the error as needed in your application
            return null;
        }




        if (caption1.getText() == null || caption1.getText().trim().isEmpty()) {
            // Display an alert indicating that the caption is required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a caption.");
            alert.showAndWait();

            // Return null or handle the error as needed in your application
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

            // Return null or handle the error as needed in your application
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

                // Return null or handle the error as needed in your application
                return null;
            }
        }

        switch (cat1.getValue()) {
            case "user problem":
                A=1;
                break;
            case "event problem":
                A=2;
                break;
            case "donation problem":
                A=3;
                break;
            default:     A=4;
        }

        models.Post p=new models.Post( data.getPostId() ,data.getUserId(), A,title.getText() , caption1.getText() , img.getText()          );
p.toString();
        return  p;

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
    public void onLikeContainerMouseReleased(MouseEvent me){
        if(System.currentTimeMillis() - startTime > 500){
            reactionsContainer.setVisible(true);
        }else {
            if(reactionsContainer.isVisible()){
                reactionsContainer.setVisible(false);
            }
            if(currentReaction == Reactions.NON){
                setReaction(Reactions.LIKE);
            }else{
                setReaction(Reactions.NON);
            }
        }
    }

    @FXML
    Label titre;
    @FXML
    public void onReactionImgPressed(MouseEvent me){
        switch (((ImageView) me.getSource()).getId()){
            case "imgLove":
                setReaction(Reactions.LOVE);
                break;
            case "imgCare":
                setReaction(Reactions.CARE);
                break;
            case "imgHaha":
                setReaction(Reactions.HAHA);
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
        reactionsContainer.setVisible(false);
    }

    public void setReaction(Reactions reaction){
        Image image = new Image("file:src/main/java/"+reaction.getImgSrc());
        System.out.println(reaction.getImgSrc());
        System.out.println("wiowwwwiwoiwoiwoiwowi");
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setData(Post post) throws MalformedURLException {
        this.post = post;
        Image img;
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(post.getAccount().getProfileImg());
        // img = new Image("file:src\\main\\java\\img\\user.png");

        img = new Image("file:src/main/java/" + post.getAccount().getProfileImg());
        System.out.println(img.getUrl());
        imgProfile.setImage(img);
        switch (data.getCategoryId()) {
        case 1:
        cat.setText("user problem");
        break;
        case 2:
        cat.setText("event problem");
        break;
        case 3:
        cat.setText("donation problem");
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
        post.setTotalReactions(10);
        post.setNbComments(CS.count_comment(data.getPostId()));
        post.setNbShares(3);

        return post;
    }
CommentService CS=new CommentService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // setData(getPost());
    }
models.Post data;
    public void receiveData(models.Post data) throws MalformedURLException, SQLException {
        this.data=data;
        setData(getPost());
        titre.setText(data.getTitle());

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
                cat1.setValue("user problem");
                break;
            case 2:
                cat1.setValue("event problem");
                break;
            case 3:
                cat1.setValue("donation problem");
                break;
            default:     cat1.setValue("athor problem");
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
