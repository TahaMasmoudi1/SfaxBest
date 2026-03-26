package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;


public class MoviePosterController {
    @FXML private ImageView posterImage;

    public void setData(Image image){
        posterImage.setImage(image);
    }
    @FXML
    public void loadMovieView(){
        MainViewController.instance.switchView("movie-view.fxml");    }
}
