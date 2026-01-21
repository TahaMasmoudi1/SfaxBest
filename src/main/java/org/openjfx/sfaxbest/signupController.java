package org.openjfx.sfaxbest;

import Services.FilmService;
import Services.UserService;
import exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class signupController {

    @FXML
    TextField tfUsername;
    @FXML
    PasswordField pfPassword;
    @FXML
    TextField tfEmail;
    @FXML
    PasswordField pfConfirmPassword;
    @FXML
    Label lblEmailError;
    @FXML
    Label lblUsernameError;
    @FXML
    Label lblPassError;
    @FXML
    Label lblConfirmPassError;
    @FXML
    TextField tfVerifCode;
    @FXML
    Label lblVerifCodeError;
    private static String patternEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String patternPassword = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.]).*$";
    UserService userService = new UserService();
    static String  email;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void signUp() throws IOException {
        hidelabel(lblUsernameError);
        hidelabel(lblPassError);
        hidelabel(lblEmailError);
        hidelabel(lblConfirmPassError);
        boolean test = true;
        String username = tfUsername.getText();
        email = tfEmail.getText();
        String password = pfPassword.getText();
        String confPassword = pfConfirmPassword.getText();
        if (email.isEmpty()) {
            showlabel(lblEmailError, "Please enter email");
            test = false;
        } else if (!Pattern.matches(patternEmail, email)) {
            showlabel(lblEmailError, "Invalid email");
            test = false;
        } else if (username.isEmpty()) {
            showlabel(lblUsernameError, "Please enter username");
            test = false;
        } else if (false) {
            //jsute tshouf est ce que el user deja mawjoud wela le
        } else if (password.isEmpty()) {
            showlabel(lblPassError, "Please enter password");
            test = false;
        } else if (!Pattern.matches(patternPassword, password)) {
            showlabel(lblPassError, "Invalid password");
            test = false;
        } else if (confPassword.isEmpty()) {
            showlabel(lblConfirmPassError, "Please confirm your password");
            test = false;
        } else if (!password.equals(confPassword)) {
            showlabel(lblPassError, "Passwords do not match");
        }
        if (test) {

            userService.register(username, password, email);
            App.setRoot("verificationCode");
        }


    }
@FXML
    public void resendVerificationCode() throws IOException {
        userService.resendVerificationCode(email);
    }
    @FXML
    public void verficationCode() throws IOException {
        String code = tfVerifCode.getText();
        try{userService.emailVerification(email, code);}
        catch (ValidationException e){
            showlabel(lblVerifCodeError, "Invalid verification code");
            return;
        }
        App.setRoot("login");
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