package org.openjfx.sfaxbest;

import Services.UserService;
import entities.User;
import entities.UserRole;
import exceptions.AuthException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {
    UserService userService = new UserService();
    @FXML
    TextField tfUsername;
    @FXML
    PasswordField pfPassword;
    @FXML
    Label lblLoginError;
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("signup");
    }

    @FXML
    private void login() throws IOException {
        //na3mlou verif 3ali da5lou el utilsateur lezemna data base besh najem ne5dem
        //ba3ed el verif na3mlou setroot lel dashborad
        //APP.setRoot("dashborad");
        hidelabel(lblLoginError);
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        User user = null;
       try{
           user =userService.login(username, password);} catch (AuthException e) {
            showlabel(lblLoginError,e.getMessage());
        }
       if(user!=null){
           if(user.getRole()==UserRole.ADMIN){
               App.setRoot("mainAdmin");
           }
           if(user.getRole()==UserRole.USER){
               //App.setRoot("mainUser");
           }
       }


    }
    private void showlabel(Label label, String message) throws IOException {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
    }

    private void hidelabel(Label label) throws IOException {
        label.setVisible(false);
        label.setManaged(false);
    }

}
