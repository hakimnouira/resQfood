package controllers.hakim;

import controllers.hakim.atlantafx.ModalPanePage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.hakim.Comment;
import models.hakim.Post;
//import org.json.JSONObject;
import org.json.JSONObject;
import services.hakim.CommentService;
import services.hakim.PostService;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static atlantafx.sampler.page.Page.FAKER;


public class AddUser  {
@FXML
ChoiceBox<String> lan =new ChoiceBox<>();
    @FXML
    private BorderPane mainBorderPane;
    public VBox wiow;
   public int userid=5;

    private final CommentService Cs =new CommentService();

    public   List<Parent> comment_section= new ArrayList<>();

    @FXML
   public AnchorPane all;

    public void initialize() throws IOException {
       /* FXMLLoader rechcerche = new FXMLLoader(new URL("file:src\\main\\resources\\search.fxml"));

        SearchController s;


        Parent root77 = rechcerche.load();
        s=rechcerche.getController();
        s.waaaa();
        vbox_con_recherche.getChildren().add(root77 );*/
try {

    lan.getItems().addAll("en", "es", "fr", "de", "zh", "ja", "ar", "ru", "hi", "pt"

    );


    Button button69 = new Button("search");
    var topDialog = new ModalPanePage.Dialog(843, 550);



    DatePicker fromUpdatedDatePicker = new DatePicker();

    DatePicker toUpdatedDatePicker = new DatePicker();



    Label fromUpdatedLabel = new Label("From (Updated):");
    Label toUpdatedLabel = new Label("To (Updated):");

    TextField titleTextField = new TextField();
    titleTextField.setPromptText("Search by Title");





    Label titleLabel = new Label("Search Options");

    // ComboBox for Category
    ComboBox<String> categoryComboBox = new ComboBox<>();
    categoryComboBox.getItems().addAll("News", "Events", "Discussion");
    categoryComboBox.setPromptText("Select Category");

    // Date Pickers for Date Range
    DatePicker fromDateDatePicker = new DatePicker();
    DatePicker toDateDatePicker = new DatePicker();
    Label fromDateLabel = new Label("From:");
    Label toDateLabel = new Label("To:");

    // ComboBox for Likes/Reactions
    ComboBox<String> reactionsComboBox = new ComboBox<>();
    reactionsComboBox.getItems().addAll("LIKE", "LOVE", "CARE", "HAHA", "WOW", "SAD", "ANGRY");
    reactionsComboBox.setPromptText("Select Reaction");
    Spinner<Integer> reactionsSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
    ChoiceBox<String> comparisonChoiceBox_Reaction = new ChoiceBox<>();
    comparisonChoiceBox_Reaction.getItems().addAll("Equal to", "More than", "Less than");
    comparisonChoiceBox_Reaction.setValue("Equal to");
    // Spinner for Number of Comments
    Spinner<Integer> commentsSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
    Label commentsLabel = new Label("Number of Comments:");

    // ChoiceBox for Comparison Operator
    ChoiceBox<String> comparisonChoiceBox = new ChoiceBox<>();
    comparisonChoiceBox.getItems().addAll("Equal to", "More than", "Less than");
    comparisonChoiceBox.setValue("Equal to");

    // Button to perform search
    Button searchButton = new Button("Search");

    searchButton.setOnAction( evenet -> {
        int category =0 ;
        categoryComboBox.setValue("nothing");
        switch ( categoryComboBox.getValue()) {
            case "News":
                category=1;
                break;
            case "Events":
                category=2;
                break;
            case "Discussion":
                category=3;
                break;
            default:
                category=0;
        }

        Date fromDate = (fromDateDatePicker.getValue() != null) ?
                java.sql.Date.valueOf(fromDateDatePicker.getValue()) : null;
        Date toDate = (toDateDatePicker.getValue() != null) ?
                java.sql.Date.valueOf(toDateDatePicker.getValue()) : null;

        String reaction = reactionsComboBox.getValue();
        int reactionCount = reactionsSpinner.getValue();
        String reactionComparison = comparisonChoiceBox_Reaction.getValue();
        int commentCount = commentsSpinner.getValue();
        String commentComparison = comparisonChoiceBox.getValue();
        String title = titleTextField.getText();
        Date fromDate_updated = (fromUpdatedDatePicker.getValue() != null) ?
                java.sql.Date.valueOf(fromUpdatedDatePicker.getValue()) : null;
        Date toDate_updated = (toUpdatedDatePicker.getValue() != null) ?
                java.sql.Date.valueOf(toUpdatedDatePicker.getValue()) : null;
        wiow.getChildren().clear();

        try {
            wiow.getChildren().removeAll();

            load_post1(ps.search(category, (java.sql.Date) fromDate, (java.sql.Date) toDate, reaction, reactionCount, reactionComparison, commentCount, commentComparison, title, (java.sql.Date) fromDate_updated, (java.sql.Date) toDate_updated));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        all.getChildren().remove(topDialog);
    });


    // Set up grid pane for layout
    GridPane gridPane = new GridPane();

    gridPane.setVgap(10);
    gridPane.setHgap(10);

    gridPane.add(titleLabel, 0, 0, 2, 1);
    gridPane.add(new Label("Category:"), 0, 1);
    gridPane.add(categoryComboBox, 1, 1);
    gridPane.add(new Label("Date Range:"), 0, 2);
    gridPane.add(fromDateLabel, 1, 2);
    gridPane.add(fromDateDatePicker, 2, 2);
    gridPane.add(toDateLabel, 3, 2);
    gridPane.add(toDateDatePicker, 4, 2);
    gridPane.add(new Label("Reactions:"), 0, 3);
    gridPane.add(reactionsComboBox, 1, 3);
    gridPane.add(reactionsSpinner, 2, 3);
    gridPane.add(comparisonChoiceBox_Reaction,3,3);
    gridPane.add(commentsLabel, 0, 4);
    gridPane.add(commentsSpinner, 1, 4);
    gridPane.add(comparisonChoiceBox, 2, 4);

    gridPane.add(fromUpdatedLabel, 0, 5);
    gridPane.add(fromUpdatedDatePicker, 1, 5);
    gridPane.add(toUpdatedLabel, 2, 5);
    gridPane.add(toUpdatedDatePicker, 3, 5);
    gridPane.add(new Label("Title:"), 0, 6);
    gridPane.add(titleTextField, 1, 6);
    gridPane.add(searchButton, 1, 8);



    topDialog.getChildren().setAll(gridPane);




    var closeBtn = new Button("Close");
    closeBtn.setOnAction(evt -> all.getChildren().remove(topDialog));
    topDialog.getChildren().add(closeBtn);



    ModalPanePage modalPane= new ModalPanePage(1);
  //  all.getChildren().add(topDialog);
    vbox_con_recherche.getChildren().add(button69);
    //vbox_con_recherche.getChildren().add(lan);
    lan.setOnAction( event -> {
wiow.getChildren().clear();

        List<Post> usersList = null;
        try {
            usersList = ps.read();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < usersList.size(); i++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/text"))
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("X-RapidAPI-Key", "a9f2669049msh72c5dbce14e8059p15cc99jsn07406a61ed08")
                        .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                        .method("POST", HttpRequest.BodyPublishers.ofString("from=auto&to="+lan.getValue()+"&text="+usersList.get(i).getContent()))
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.body());
                System.out.println(jsonResponse);
                String translation = jsonResponse.getString("trans");
                usersList.get(i).setContent(translation) ;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }


        }

        load_post1( usersList          ) ;
    });
  //  vbox_con_recherche.getChildren().add( modalPane1.contentPositionExample());
    button69.setOnAction(event -> {





        all.getChildren().add(topDialog);


            }

    );



        Button button6 = new Button("create");
    button6.setStyle("-fx-background-color: orange;");
    button6.setStyle("");
    button6.setPrefSize(517, 100);

    button6.setOnAction(event -> {
            // Handle button click event
            FXMLLoader loader1 = null;
            try {
                loader1 = new FXMLLoader(new URL("file:src/main/resources/hakim/post2.fxml"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            try {
                Parent root1 = loader1.load();
                wiow.getChildren().add(root1);
                wiow.getChildren().remove(button6);

                PostController c = loader1.getController();
                c.receiveData1( new Post(userid));
                c.userid=userid;
                HBox hbox1;
                hbox1=c.getThisone();
                Button button5 = new Button("create");
                button5.setOnAction(eve -> {
                            // Handle button click event
                            System.out.println("create button clicked!");
                            try {
                                ps.create(c.update_button_pressed());
                                Post p =c.update_button_pressed();
                                wiow.getChildren().remove(root1);


                               List<Post> o=new ArrayList<>();
                               o=ps.read();
loadFXML("file:src/main/resources/hakim/post1.fxml", (o.get(o.size()-1)));



                               // wiow.getChildren().add(root1);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                });
                        // Add style to the button
                        button5.setStyle("-fx-background-color: orange;");
                button5.setPrefSize(517, 26);


                // Create an HBox to contain the Button
                HBox innerHBox = new HBox(button5);
                innerHBox.setAlignment(Pos.CENTER);
                innerHBox.setSpacing(10);
                HBox.setHgrow(innerHBox, Priority.ALWAYS);

                // Add the inner HBox to the provided HBox (hbox) in the FXML
                hbox1.getChildren().add(innerHBox);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    });

        // Add style to the button


        // Create an HBox to contain the Button


        wiow.getChildren().add(button6);















            List<Post> usersList = ps.read();
    load_post1( usersList          ) ;

        }  catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }



    }
    @FXML
    VBox vbox_con_recherche;

    public void   load_post1 ( List<Post>    usersList          ) {

        for (int i = 0; i < usersList.size(); i++) {


            loadFXML("file:src/main/resources/hakim/post1.fxml", usersList.get(i));
        }
    }


    public void   comment_read_delete (Label comment ,Post id,Parent root, Button b ,VBox vbox) {
        System.out.println("comment pressed!");



vbox.getChildren().remove(b);

        for (int i = 0; i < comment_section.size(); i++) {

            vbox.getChildren().remove(comment_section.get(i));
            wiow.getChildren().remove(spacer.get(i));
        }
        spacer.clear();
      //  wiow.getChildren().add(root);


        comment.setOnMouseClicked(event998 -> {

            System.out.println(
                    "pppp  eeeeeeeeeeeeeeeeeeeeee"
            );
            try {
                comment_read(comment ,id, root,vbox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }    );


    }
    public List<Region> spacer=new ArrayList<>();















    public void comment_read(Label comment ,Post id,Parent root ,VBox vbox) throws IOException {
        System.out.println("comment pressed!");


       // wiow.setSpacing(2000);




Button create_comment_button =new Button("Create comment");
        create_comment_button.setOnAction(event -> {

            FXMLLoader loader1 = null;
            try {
                loader1 = new FXMLLoader(new URL(" file:src/main/resources/hakim/comment.fxml"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            try {
                Parent root1 = loader1.load();
                controllers.hakim.Comment C;
                C=loader1.getController();
                Button submit;
                submit=C.get_submit();
                submit.setOnAction(event5 -> {
                    Comment cc= new Comment();
                    String message = C.get_submit_information();
                    if (message == null) {
                        return; // Exit the event handler if the message is empty
                    }
                    cc.setContent(message);
                    cc.setUserId(userid); // hathi badlha ba3ed
                    cc.setPostId(id.getPostId());

                    try {
                        Cs.create(cc);
                        vbox.getChildren().remove(root1);
                        FXMLLoader loader7 ;
                        loader7 = new FXMLLoader(new URL("file:src/main/resources/hakim/comment_sum.fxml"));
                        Parent root7 = loader7.load();
                        controllers.hakim.comment_sum csum;
                        csum=loader7.getController();
                        Comment cm=(Cs.read(id.getPostId())  ).get(Cs.read(id.getPostId()) .size()-1  );
                        csum.initialize(cm    );
                        comment_update_button_fn( root7 ,vbox, cm , csum );
                        delete_button_comment_fn(vbox,csum,cm,root7 );

                        vbox.getChildren().add(root7);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
                Region spacer1 = new Region();
                spacer1.setMinHeight(666);
                spacer1.setDisable(true);
                spacer.add(spacer1);
                wiow.getChildren().add(    wiow.getChildren().indexOf(root) +1,  spacer1        );

                vbox.getChildren().add(root1);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
        vbox.getChildren().add(create_comment_button);
        List<Comment> usersList = null;
        try {
            usersList = Cs.read(id.getPostId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(usersList);
        for (int i = 0; i < usersList.size(); i++) {

            FXMLLoader loader1 = new FXMLLoader(new URL("file:src/main/resources/hakim/comment_sum"));

            Parent root1 = loader1.load();

            comment_section.add(root1);
           comment_sum c;
            c=loader1.getController();
            c.initialize(usersList.get(i));

           // comment_update_button_fn(c,root1,usersList.get(i),wiow);









          /*  controllers.Comment c;
            c=loader1.getController();
            c.initialize(usersList.get(i));*/


           Region spacer = new Region();
            spacer.setMinHeight(root1.getScaleY()+200);
 spacer.setDisable(true);
this. spacer.add(spacer);

           wiow.getChildren().add(    wiow.getChildren().indexOf(root) +1,  spacer        );
            vbox.getChildren().add(root1);


            comment_update_button_fn(root1 ,vbox,usersList.get(i) ,c);
            delete_button_comment_fn(vbox,c,usersList.get(i),root1 );







        }

      //  wiow.getChildren().add(root);

        comment.setOnMouseClicked(event2 -> {
            System.out.println("rrrrrrrrrrrrrrrr");
            comment_read_delete(comment,id, root,create_comment_button,vbox);

        });



    }



    public void  comment_update_button_fn(Parent root1 ,VBox vbox, Comment cm , comment_sum c){

           /* Comment cm;
            cm=usersList.get(i);*/
        Button comment_update_button;
        comment_update_button = c.get_update_button();
        comment_update_button.setOnAction(event -> {
            cm.setContent(c.get_content()   );
            vbox.getChildren().remove(root1);
            try {
                FXMLLoader loader_comment_update = new FXMLLoader(new URL(" file:src/main/resources/hakim/comment.fxml"));
                Parent root_comment_update=  loader_comment_update.load();
                controllers.hakim.Comment c_update ;

                c_update=loader_comment_update.getController();
                c_update.initialize(cm);
                vbox.getChildren().add(root_comment_update);

                c_update.get_submit().setOnAction(event_update -> {
                    String message =c_update.get_submit_information();
                    if (message == null) {
                        return; // Exit the event handler if the message is empty
                    }

                    cm.setContent( c_update.get_submit_information());
                    try {
                        Cs.update(cm);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    c.initialize(cm);

                    vbox.getChildren().add(root1);
                    vbox.getChildren().remove(root_comment_update);
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });





    }













    public void loadFXML(String fxmlPath, Post id) {

        try {

            PostController p = new PostController();
//p.receiveData(id);
            FXMLLoader loader = new FXMLLoader(new URL(fxmlPath));


            //  loader.setController(p);





            Parent root = loader.load();

            p = loader.getController();
VBox vbox=p.get_comment_section();
            p.userid=userid;
            p.receiveData(id);
            VBox v;
            v = p.getdeleteButton();

            Label comment;
            comment=p.getcomment2();

            comment.setOnMouseClicked(event998 -> {

                System.out.println(
                        "pppp  eeeeeeeeeeeeeeeeeeeeee"
                );
                try {
                    comment_read(comment ,id, root,vbox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }    );

            Button button = new Button("delete!");
            button.setOnAction(e -> {
                try {
                    ps.delete(id.getPostId());
                    wiow.getChildren().remove(root);
                    //root.getScene().getWindow().hide();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            });

            Button button1 = new Button("update!");


            button1.setOnAction(e -> {

                        try {
                            FXMLLoader loader1 = new FXMLLoader(new URL("file:src/main/resources/hakim/post2.fxml"));

                            Parent root1 = loader1.load();
                            PostController c = loader1.getController();
                            c.userid=userid;
                            c.receiveData1(id);
                            HBox hbox;
                            hbox=c.getThisone();
                            Button button5 = new Button("update");
                            button5.setOnAction(event -> {
                                // Handle button click event
                                System.out.println("Update button clicked!");
                                try {
                                    ps.update(c.update_button_pressed());
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                                wiow.getChildren().remove(root1);
                                /*
                                 FXMLLoader loader8 = new FXMLLoader(new URL(fxmlPath));


                                 //  loader.setController(p);


                                 Parent root8 = loader8.load();*/
                                try {
                                    loadFXML("file:src/main/resources/hakim/post1.fxml",ps.readOne( c.update_button_pressed().getPostId()));
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                            });

                            // Add style to the button
                            button5.setStyle("-fx-background-color: orange;");
                            button5.setPrefSize(517, 26);


                            // Create an HBox to contain the Button
                            HBox innerHBox = new HBox(button5);
                            innerHBox.setAlignment(Pos.CENTER);
                            innerHBox.setSpacing(10);
                            HBox.setHgrow(innerHBox, Priority.ALWAYS);

                            // Add the inner HBox to the provided HBox (hbox) in the FXML
                            hbox.getChildren().add(innerHBox);












                            wiow.getChildren().remove(root);
                            wiow.getChildren().add(root1);

















                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }


                    }


            );





            // p.receiveData(id);
            v.getChildren().add(button1);
            v.getChildren().add(button);
            wiow.getChildren().add(root);

        } catch (IOException e) {
            System.out.println("rrrrrrrrrrrrrrrrrr");
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public void delete_button_comment_fn(VBox vbox,comment_sum cm ,Comment c,Parent root ){

        cm.get_delete_button().setOnAction(actionEvent -> {

            vbox.getChildren().remove(root);
            try {
                Cs.delete(c.getCommentId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });



    }

  public List<VBox> vboxlist=new ArrayList<>();
    private final PostService ps = new PostService();
    @FXML
    private TextField ageTf;

    @FXML
    private TextField firstNameTf;

    @FXML
    private TextField lastNameTf;

    @FXML
    void addUser(ActionEvent event) {
        try {
            ps.create(new Post(1,1,1,firstNameTf.getText(),firstNameTf.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
            ageTf.setText("");
            firstNameTf.setText("");
            lastNameTf.setText("");

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }




    @FXML
    void navigate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowUser.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(getClass().getResource("/ShowUser.fxml"));
            lastNameTf.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error4564654654" + e.getMessage());
        }
    }



}