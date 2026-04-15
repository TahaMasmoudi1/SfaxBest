package org.openjfx.sfaxbest;

import Services.UserService;
import entities.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.StandardCopyOption;

public class UserHomeController {

    UserService userService = new UserService();
    User user = MainViewController.instance.getCurrentUser();

    @FXML ImageView avatarImageView;
    @FXML Circle avatarClip;
    @FXML Label usernameLabel;
    @FXML Label emailLabel;

    public void initialize() {
        setData();
    }
    @FXML
    public void changeAvatar() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setTitle("Choose an Avatar");

        File selectedFile = fileChooser.showOpenDialog(avatarClip.getScene().getWindow());


        if (selectedFile != null) {
            try {

                String fileName = selectedFile.getName();

                File imagesFolder = new File("src/main/resources/Images");

                Path destinationPath = Path.of(imagesFolder.getAbsolutePath(), fileName);

                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                setAvatar(new Image(destinationPath.toUri().toString()));

                userService.changeAvatar("/Images/" + fileName,user.getId());

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void setData() {
        setAvatar(new Image(getClass().getResource(user.getAvatarUrl()).toExternalForm()));
        usernameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
    }
    private void setAvatar(Image image) {
        avatarClip.setFill(new ImagePattern(image));
    }
}
