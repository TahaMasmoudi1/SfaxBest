package org.openjfx.sfaxbest;

import Services.FavoriteService;
import Services.UserService;
import entities.Multimedia;
import entities.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class UserHomeController {

    UserService userService = new UserService();
    FavoriteService favoriteService = new FavoriteService();
    User user = MainViewController.instance.getCurrentUser();

    @FXML ImageView avatarImageView;
    @FXML Circle avatarClip;
    @FXML Label usernameLabel;
    @FXML Label emailLabel;
    @FXML HBox favoritesContainer;


    public void initialize() {
        setData();
        loadFavorites();
    }
    @FXML
    private void changeAvatar() throws IOException {

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
    public void loadFavorites(){
        try{
            List<Multimedia> favorites = favoriteService.getFavorites(user.getId());
            for (Multimedia multimedia : favorites){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("favorite-card.fxml"));
                Node cardNode = loader.load();

                FavoriteCardController cardController = loader.getController();

                cardController.setData(user.getId(), multimedia.getId(), new Image(getClass().getResource(multimedia.getPathPoster()).toExternalForm()));

                favoritesContainer.getChildren().add(cardNode);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setData() {
        try {
            setAvatar(new Image(getClass().getResource(user.getAvatarUrl()).toExternalForm()));
        }catch (Exception e){
            e.printStackTrace();
        }
        usernameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
    }
    private void setAvatar(Image image) {
        try {
            avatarClip.setFill(new ImagePattern(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
