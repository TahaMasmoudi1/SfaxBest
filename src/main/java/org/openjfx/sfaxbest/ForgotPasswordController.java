package org.openjfx.sfaxbest;

import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class ForgotPasswordController {

    private static String emailV ;
    private static String passwordV ;

    UserService userService =  new UserService();
    private final String patternEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String patternPassword = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.]).*$";

    @FXML TextField emailTextField;
    @FXML Label errorLabel;
    @FXML PasswordField passwordField;
    @FXML PasswordField confirmPasswordField;

    @FXML
    private void sendVerificationCode() throws IOException {

        System.out.println("Sending verification code...");

        String email = emailTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        boolean test = true;

        if (email.isEmpty()) {
            errorLabel.setText("Please enter email");
            test = false;
        } else if (!Pattern.matches(patternEmail, email)) {
            errorLabel.setText("Invalid email!");
            test = false;
        }else if (password.isEmpty()) {
            errorLabel.setText( "Please enter password");
            test = false;
        } else if (!Pattern.matches(patternPassword, password)) {
            errorLabel.setText("Invalid password");
            test = false;
        } else if (confirmPassword.isEmpty()) {
            errorLabel.setText("Please confirm your password");
            test = false;
        } else if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            test = false;
        }
        if (test) {
            userService.forgetPassword(email);
            emailV = email;
            passwordV = password;
            App.setRoot("verificationPasswordCode");
        }
    }
    public String getEmailV() {
        return emailV;
    }
    public String getPasswordV() {
        return passwordV;
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}
