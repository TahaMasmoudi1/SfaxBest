package org.openjfx.sfaxbest;

import entities.Episode;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EpisodePosterController {
    @FXML
    private ImageView posterImage;
    @FXML private Label titleLabel;
    @FXML private Label genreLabel;
    @FXML private Label ratingLabel;

    public Episode episode;

    public void setData(Episode episode, String title, String genres, String rating , Image image){
        titleLabel.setText(title);
        genreLabel.setText(genres);
        ratingLabel.setText("★ " + rating + "/5");
        posterImage.setImage(image);
        this.episode = episode;
    }
    @FXML
    private void loadEpisode(){
        MainViewController.instance.openMediaPlayerView(getClass().getResource(episode.getVideoPath()).toExternalForm());
    }
}
