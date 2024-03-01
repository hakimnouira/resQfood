package controllers.feriel;

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

import java.io.IOException;
import java.sql.SQLException;

public class ModifUserController {
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




    @FXML
    void navigate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fereil/ModifUser.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fereil/DisplayUsers.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LogIn.fxml")));

            fnametf_modif.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

        }else {
            System.out.println("user ds if other"+caller);


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fereil/ParticipDash.fxml"));
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
            fnametf_modif.requestFocus();
            MyAnimation.shake(fnametf_modif);

            return;
        }

        if (lnametf_modif.getText().isEmpty()|| ! lnametf_modif.getText().matches("^[a-zA-Z]+$")) {
            lnametf_modif.requestFocus();
            MyAnimation.shake(lnametf_modif);

            return;
        }
        //"^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        if (mailtf_modif.getText().isEmpty() || !mailtf_modif.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$") ) {
            mailtf_modif.requestFocus();
            MyAnimation.shake(mailtf_modif);
            return;
        }
        if (pwdtf_modif.getText().isEmpty()  ) {
            pwdtf_modif.requestFocus();
            MyAnimation.shake(pwdtf_modif);
            return;

        }if (phonetf_modif.getText().isEmpty() || phonetf_modif.getText().length() > 9 || !phonetf_modif.getText().matches("[0-9]+")){
            phonetf_modif.requestFocus();
            MyAnimation.shake(phonetf_modif);
            return;
        }
        if (areacombobox_modif.getSelectionModel().getSelectedItem() == null || areacombobox_modif.getSelectionModel().getSelectedItem().equals("Area")) {
            areacombobox_modif.requestFocus();
            MyAnimation.shake(areacombobox_modif);
            return;
        }
        if (rolecbox.getSelectionModel().getSelectedItem() == null || rolecbox.getSelectionModel().getSelectedItem().equals("Role")) {
            rolecbox.requestFocus();
            MyAnimation.shake(rolecbox);
            return;
        }

        user.setFirstName(fnametf_modif.getText());
        user.setlName(lnametf_modif.getText());
        user.setEmail(mailtf_modif.getText());
        user.setPwd(pwdtf_modif.getText());
        user.setPhone(Integer.parseInt(phonetf_modif.getText()));
        user.setArea(areacombobox_modif.getSelectionModel().getSelectedItem());
        user.setRole(rolecbox.getSelectionModel().getSelectedItem());


        try {
            us.update(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User modified successfully");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("l 182 modifusrcontr"+e.getMessage());
            throw new RuntimeException(e);

        }


    }
}
