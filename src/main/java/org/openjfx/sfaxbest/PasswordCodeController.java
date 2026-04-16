package org.openjfx.sfaxbest;

import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class PasswordCodeController {

    UserService userService = new UserService();
    ForgotPasswordController  forgotPasswordController = new ForgotPasswordController();
    String email = forgotPasswordController.getEmailV();
    String password = forgotPasswordController.getPasswordV();

    @FXML Hyperlink resendHyperLink;
    @FXML TextField verifCodeField;

    @FXML
    public void resendVerificationCode() throws IOException {
        userService.resendVerificationCode(email);
    }
    @FXML
    private void verifyCode() throws IOException {
        try{
            userService.resetPassword(email,password,verifCodeField.getText());
            App.setRoot("Login");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}
