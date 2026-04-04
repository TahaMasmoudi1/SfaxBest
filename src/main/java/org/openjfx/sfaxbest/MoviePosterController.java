package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class MoviePosterController {

    @FXML private ImageView posterImage;
    @FXML private Label titleLabel;
    @FXML private Label genreLabel;
    @FXML private Label ratingLabel;

    public void setData(String title,String genres,String rating ,Image image){
        titleLabel.setText(title);
        genreLabel.setText(genres);
        ratingLabel.setText(rating);
        posterImage.setImage(image);
    }
    @FXML
    public void loadMovieView(){
        String title = titleLabel.getText();
        MainViewController.instance.switchView("movie-view.fxml");
        MovieViewController.instance.loadMovie(title);
    }
}
