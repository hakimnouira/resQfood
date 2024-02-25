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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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

public class AddUser  {

    @FXML
    private BorderPane mainBorderPane;
    public VBox wiow;

    private final CommentService Cs =new CommentService();

    public   List<Parent> comment_section= new ArrayList<>();

    public void initialize(){
try {

        Button button6 = new Button("create");
    button6.setStyle("-fx-background-color: orange;");
    button6.setStyle("");
    button6.setPrefSize(517, 100);

    button6.setOnAction(event -> {
            // Handle button click event
            FXMLLoader loader1 = null;
            try {
                loader1 = new FXMLLoader(new URL("file:src\\main\\resources\\post2.fxml"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            try {
                Parent root1 = loader1.load();
                wiow.getChildren().add(root1);
                wiow.getChildren().remove(button6);

                PostController c = loader1.getController();
                c.receiveData1( new Post(1));
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
loadFXML("file:src\\main\\resources\\post1.fxml", (o.get(o.size()-1)));



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
            }




    });

        // Add style to the button


        // Create an HBox to contain the Button


        wiow.getChildren().add(button6);















            List<Post> usersList = ps.read();
            System.out.println(usersList);
            for (int i = 0; i < usersList.size(); i++) {



                loadFXML("file:src\\main\\resources\\post1.fxml", usersList.get(i));
            }
        }  catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }



    }
    public void   comment_read_delete (Label comment ,Post id,Parent root, Button b ,VBox vbox) {
        System.out.println("comment pressed!");



vbox.getChildren().remove(b);

        for (int i = 0; i < comment_section.size(); i++) {

            vbox.getChildren().remove(comment_section.get(i));
            //wiow.getChildren().remove(spacer.get(i));
        }
      //  spacer.clear();
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
                loader1 = new FXMLLoader(new URL("file:src\\main\\resources\\comment.fxml"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            try {
                Parent root1 = loader1.load();
                controllers.Comment C;
                C=loader1.getController();
                Button submit;
                submit=C.get_submit();
                submit.setOnAction(event5 -> {
                    Comment cc= new Comment();
                    cc.setContent(C.get_submit_information());
                    cc.setUserId(id.getUserId());
                    cc.setPostId(id.getPostId());

                    try {
                        Cs.create(cc);
                        vbox.getChildren().remove(root1);
                        FXMLLoader loader7 ;
                        loader7 = new FXMLLoader(new URL("file:src\\main\\resources\\comment_sum.fxml"));
                        Parent root7 = loader7.load();
                        controllers.comment_sum csum;
                        csum=loader7.getController();
                        Comment cm=(Cs.read(id.getPostId())  ).get(Cs.read(id.getPostId()) .size()-1  );
                        csum.initialize(cm    );
                        comment_update_button_fn( root7 ,vbox, cm , csum );
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

            FXMLLoader loader1 = new FXMLLoader(new URL("file:src\\main\\resources\\comment_sum.fxml"));

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


           wiow.getChildren().add(    wiow.getChildren().indexOf(root) +1,  spacer        );
            vbox.getChildren().add(root1);


            comment_update_button_fn(root1 ,vbox,usersList.get(i) ,c);







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
                FXMLLoader loader_comment_update = new FXMLLoader(new URL("file:src\\main\\resources\\comment.fxml"));
                Parent root_comment_update=  loader_comment_update.load();
                controllers.Comment c_update ;

                c_update=loader_comment_update.getController();
                c_update.initialize(cm);
                vbox.getChildren().add(root_comment_update);

                c_update.get_submit().setOnAction(event_update -> {
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
                            FXMLLoader loader1 = new FXMLLoader(new URL("file:src\\main\\resources\\post2.fxml"));

                            Parent root1 = loader1.load();
                            PostController c = loader1.getController();
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
                                    loadFXML("file:src\\main\\resources\\post1.fxml",ps.readOne( c.update_button_pressed().getPostId()));
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
        }

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