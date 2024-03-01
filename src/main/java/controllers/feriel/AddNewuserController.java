package controllers.feriel;

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

public class AddNewuserController {

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
            MyTools.goTo("/fereil/LogIn.fxml",fnametf);
        }

        MyTools.goTo("/fereil/DisplayUsers.fxml",fnametf);

        //TODO: fix it so that admin can go back to his dash and user who just created acc goes to login


    }

    @FXML
    void signupBt(ActionEvent event) {
        System.out.println("in start signupBt ");

        if (fnametf.getText().isEmpty() || !fnametf.getText().matches("^[a-zA-Z]+$")) {
            fnametf.requestFocus();
            MyAnimation.shake(fnametf);

            return;
        }

        if (lnametf.getText().isEmpty()|| ! lnametf.getText().matches("^[a-zA-Z]+$")) {
            lnametf.requestFocus();
            MyAnimation.shake(lnametf);

            return;
        }
        //"^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        if (mailtf.getText().isEmpty() || mailIsUnique(mailtf.getText())== 1 || !mailtf.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$") ) {
            mailtf.requestFocus();
            MyAnimation.shake(mailtf);
            return;
        }
        if (pwdtf.getText().isEmpty()  ) {
            pwdtf.requestFocus();
            MyAnimation.shake(pwdtf);
            return;

        }if (phonetf.getText().isEmpty() || phonetf.getText().length() > 9 || !phonetf.getText().matches("[0-9]+")){
            phonetf.requestFocus();
            MyAnimation.shake(phonetf);
            return;
        }
        if (areacombobox.getSelectionModel().getSelectedItem() == null || areacombobox.getSelectionModel().getSelectedItem().equals("Area")) {
            areacombobox.requestFocus();
            MyAnimation.shake(areacombobox);
            return;
        }
        if (roleCombo.getSelectionModel().getSelectedItem() == null || roleCombo.getSelectionModel().getSelectedItem().equals("Role")) {
            roleCombo.requestFocus();
            MyAnimation.shake(roleCombo);
            return;
        }

        user.setFirstName(fnametf.getText());
        user.setlName(lnametf.getText());
        user.setEmail(mailtf.getText());
        user.setPwd(pwdtf.getText());
        user.setPhone(Integer.parseInt(phonetf.getText()));
        user.setArea(areacombobox.getSelectionModel().getSelectedItem());
        user.setRole(roleCombo.getSelectionModel().getSelectedItem());


        try {
            us.create(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


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
     * @param mail : (String) mail
     * @return : (int) 0 if its available or one if its not
     */
    public int mailIsUnique(String mail){
        try {
            allUsers=us.read();
            for (int i = 0; i < allUsers.size(); i++) {
                if (allUsers.get(i).getEmail().equals(mail)){
                    emailAvailable= 1;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);

        }
        System.out.println("this is emailAvailable :"+emailAvailable);
        return emailAvailable;
    }
}
