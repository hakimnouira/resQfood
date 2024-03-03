package controllers.feriel;

import controllers.Controller;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import toolkit.MyAnimation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import models.feriel.User;
import services.feriel.UserService;
import toolkit.MyTools;

import java.sql.SQLException;
import java.util.List;

public class AddNewuserController extends Controller {

    User user= new User();
    UserService us= new UserService();

    List<User> allUsers;
    //TODO/ indicator whether this email is available
    //indicator whether this email is available
    int emailAvailable= 0;



    @FXML
    private ComboBox<String> areacombobox;
    @FXML
    private ComboBox<String> roleCombo;

    String[] roles={"Participant","Volunteer","Donor"};

    String[] areas={"Ariana",
            "Béja",
            "BenArous",
            "Bizerte",
            "Gabès",
            "Gafsa",
            "Jendouba",
            "Kairouan",
            "Kasserine",
           " Kébili",
           " LeKef",
           " Mahdia",
           "La Manouba",
            "Médenine",
            "Monastir",
           " Nabeul",
            "Sfax",
            "SidiBouzid",
            "Siliana",
            "Sousse",
          "Tataouine",
            "Tozeur",
            "Tunis",
           " Zaghouan"};

    @FXML
    private TextField fnametf;

    @FXML
    private TextField lnametf;

    @FXML
    private TextField mailtf;

    @FXML
    private TextField phonetf;

    @FXML
    private TextField pwdtf;
    @FXML
    private Label mailTaken;



    public void initialize(){
        areacombobox.getItems().addAll(areas);
        roleCombo.getItems().addAll(roles);


    }





    /**
     *canc el adding depends on where user was
     * if he came from login page
     * or from admin dash
     */
    @FXML
    public void canceladd(ActionEvent event) {
        if (UserService.loggedIn== null){
            MyTools.goTo("/feriel/LogIn.fxml",fnametf);
        }

        MyTools.goTo("/feriel/DisplayUsers.fxml",fnametf);


    }

    @FXML
    void signupBt(ActionEvent event) {
        System.out.println("in start signupBt ");
        //resetMailIndicator();
        emailAvailable=0;


        if (fnametf.getText().isEmpty() || !fnametf.getText().matches("^[a-zA-Z]+$")) {
            showInputIncorect(fnametf);

            return;
        }

        if (lnametf.getText().isEmpty()|| ! lnametf.getText().matches("^[a-zA-Z]+$")) {
            showInputIncorect(lnametf);

            return;
        }
        if (mailtf.getText().isEmpty()  || !mailtf.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$") ) {
            showInputIncorect(mailtf);
            return;
        }
        if (mailIsUnique(mailtf.getText(),us,emailAvailable) == 1) {
            //TODO/ PUT it in modif user
            showInputIncorect(mailtf);
            mailTaken.setText("email already exists");
            return;
        }
        if (pwdtf.getText().isEmpty()){
            showInputIncorect(pwdtf);
            return;

        }if (phonetf.getText().isEmpty() || phonetf.getText().length() > 9 || !phonetf.getText().matches("[0-9]+")){
            showInputIncorect(phonetf);
            return;
        }
        if (areacombobox.getSelectionModel().getSelectedItem() == null || areacombobox.getSelectionModel().getSelectedItem().equals("Area")) {
//            areacombobox.requestFocus();
//            MyAnimation.shake(areacombobox);
            showInputIncorect(areacombobox);
            return;
        }
        if (roleCombo.getSelectionModel().getSelectedItem() == null || roleCombo.getSelectionModel().getSelectedItem().equals("Role")) {
            showInputIncorect(areacombobox);
            return;
        }

        user.setFirstName(fnametf.getText());
        user.setlName(lnametf.getText());
        user.setEmail(mailtf.getText());
        user.setPwd(pwdtf.getText());
        user.setPhone(Integer.parseInt(phonetf.getText()));
        user.setArea(areacombobox.getSelectionModel().getSelectedItem());
        user.setRole(roleCombo.getSelectionModel().getSelectedItem());
        //resetMailIndicator();
        emailAvailable=0;


        try {
            us.create(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            mailTaken.setText("");
            alert.showAndWait();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }

    public static void showInputIncorect(Node n) {
        n.requestFocus();
        MyAnimation.shake(n);
    }

    @FXML
    public String getAreac(ActionEvent actionEvent) {
       return areacombobox.getSelectionModel().getSelectedItem();
    }

    @FXML
    public String getRolecb(ActionEvent event) {
        return roleCombo.getSelectionModel().getSelectedItem();
    }


    

    /**
     *
     * @param mail: (String)
     * @param us : (UserService)
     * @param indicator: (int)
     * @return : (int) 0 if its available or one if it's not
     */
    public static int mailIsUnique(String mail,UserService us, int indicator){

        try {
            List<User> allUsers=us.read();
            System.out.println("called mailIsUnique after read");
            for (int i = 0; i < allUsers.size(); i++) {
                if (allUsers.get(i).getEmail().equals(mail)){
                    indicator= 1;
                    return 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("this is emailAvailable :"+indicator);
        return 0;
    }


    void resetMailIndicator(){
        emailAvailable=0;
    }
}
