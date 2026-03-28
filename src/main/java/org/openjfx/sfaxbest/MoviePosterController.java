package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class MoviePosterController {

    @FXML private ImageView posterImage;
    @FXML private Label titleLabel;

    public void setData(Image image){
        posterImage.setImage(image);
    }
    @FXML
    public void loadMovieView(){
        titleLabel.getText();
        MainViewController.instance.switchView("movie-view.fxml");
    }
}
