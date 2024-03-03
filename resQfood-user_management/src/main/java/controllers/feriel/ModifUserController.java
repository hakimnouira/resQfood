package controllers.feriel;

import controllers.Controller;
import toolkit.MyAnimation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.feriel.User;
import services.feriel.UserService;
import toolkit.MyTools;
import toolkit.PasswordEncryptor;

import java.io.IOException;
import java.sql.SQLException;

import static controllers.feriel.AddNewuserController.mailIsUnique;
import static controllers.feriel.AddNewuserController.showInputIncorect;

public class ModifUserController extends Controller {
    User user= new User();

    UserService us= new UserService();

    String[] roles={"Participant","Volunteer","Donor"};
    String[] areas={"Ariana",
            "Béja",
            "Ben Arous",
            "Bizerte",
            "Gabès",
            "Gafsa",
            "Jendouba",
            "Kairouan",
            "Kasserine",
            " Kébili",
            " Le Kef",
            " Mahdia",
            "La Manouba",
            "Médenine",
            "Monastir",
            " Nabeul",
            "Sfax",
            "Sidi Bouzid",
            "Siliana",
            "Sousse",
            "Tataouine",
            "Tozeur",
            "Tunis",
            " Zaghouan"};

    @FXML
    private ComboBox<String> areacombobox_modif;

    @FXML
    private TextField fnametf_modif;

    @FXML
    private TextField lnametf_modif;

    @FXML
    private TextField mailtf_modif;

    @FXML
    private TextField phonetf_modif;

    @FXML
    private TextField pwdtf_modif;

    @FXML
    private ComboBox<String> rolecbox;

    String caller= "";
    int emailAvailable=0;




    @FXML
    void navigate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/ModifUser.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(getClass().getResource("/ShowUser.fxml"));
            // ageTf.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }


    public void initialize(){
        areacombobox_modif.getItems().addAll(areas);
            rolecbox.getItems().addAll(roles);

    }

    /**
     * For normal usr only
     */
    public void initData(User olduser){
        fnametf_modif.setText(olduser.getFirstName());
        lnametf_modif.setText(olduser.getLName());
        mailtf_modif.setText(olduser.getEmail());
        phonetf_modif.setText( Integer.toString(olduser.getPhone()));
        pwdtf_modif.setText(olduser.getPwd());

        areacombobox_modif.setValue(olduser.getArea());
         rolecbox.setValue(olduser.getRole());
         user=olduser;// 10H15
         caller="Other";


    }

    /**
     * For admin only
     */
    public void initSelectedUser(User selectedUsr){
        initData(selectedUsr);
        caller="Admin";

    }

    @FXML
    void cancel(ActionEvent event) {
        System.out.println("caller cancel"+caller);
       // assert user!= null;
        if (caller.equals("Admin")) {
            System.out.println("user ds if ad"+caller);

            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/DisplayUsers.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LogIn.fxml")));

            fnametf_modif.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

        }else {
            System.out.println("user ds if other"+caller);


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/ParticipDash.fxml"));
                Parent root = loader.load();

                // Get the controller instance
                ParticipDashController controller = loader.getController();
                controller.initData(user);

                phonetf_modif.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void modifybt(ActionEvent event) {

        if (fnametf_modif.getText().isEmpty() || ! (fnametf_modif.getText().matches("^[a-zA-Z]+$"))) {
            showInputIncorect(fnametf_modif);
            return;
        }

        if (lnametf_modif.getText().isEmpty()|| ! lnametf_modif.getText().matches("^[a-zA-Z]+$")) {
            showInputIncorect(lnametf_modif);
            return;
        }

        if (mailtf_modif.getText().isEmpty() || !mailtf_modif.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$") ) {
            showInputIncorect(mailtf_modif);
            return;
        }

        if (!mailtf_modif.getText().equals(user.getEmail())) {
            if ( mailIsUnique(mailtf_modif.getText(),us,emailAvailable) == 1) {
     //  PUT it in modif user FXML >  mailTaken.setText("email already exists");
                showInputIncorect(mailtf_modif);
     //           mailTaken.setText("email already exists");
                return;
            }
        }

        if (pwdtf_modif.getText().isEmpty()){
            showInputIncorect(pwdtf_modif);
            return;

        }if (phonetf_modif.getText().isEmpty() || phonetf_modif.getText().length() !=8 || !phonetf_modif.getText().matches("[0-9]+")){
            showInputIncorect(phonetf_modif);

            return;
        }
        if (areacombobox_modif.getSelectionModel().getSelectedItem() == null || areacombobox_modif.getSelectionModel().getSelectedItem().equals("Area")) {
            showInputIncorect(areacombobox_modif);

            return;
        }
        if (rolecbox.getSelectionModel().getSelectedItem() == null || rolecbox.getSelectionModel().getSelectedItem().equals("Role")) {
            showInputIncorect(rolecbox);

            return;
        }

        user.setFirstName(fnametf_modif.getText());
        user.setlName(lnametf_modif.getText());
        user.setEmail(mailtf_modif.getText());
        user.setPwd(PasswordEncryptor.encrypt( pwdtf_modif.getText()));
        user.setPhone(Integer.parseInt(phonetf_modif.getText()));
        user.setArea(areacombobox_modif.getSelectionModel().getSelectedItem());
        user.setRole(rolecbox.getSelectionModel().getSelectedItem());
//TODO notes on encryp ds modif il crypte le mdp deja crypte, genre il crypte le code qui est ds la bd

        emailAvailable=0;

        try {
            us.update(user);
            MyTools.showAlertInfo("Success","User added successfully");
            cancel(event);
        } catch (SQLException e) {
            System.out.println("l 182 modifusrcontr"+e.getMessage());
            throw new RuntimeException(e);

        }


    }
}
