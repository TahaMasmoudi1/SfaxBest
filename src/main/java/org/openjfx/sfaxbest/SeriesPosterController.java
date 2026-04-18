package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SeriesPosterController {
    @FXML private ImageView posterImage;
    @FXML private Label titleLabel;
    @FXML private Label genreLabel;
    @FXML private Label ratingLabel;

    public void setData(String title, String genres, String rating , Image image){
        titleLabel.setText(title);
        genreLabel.setText(genres);
        ratingLabel.setText("★ " + rating + "/5");
        posterImage.setImage(image);
    }
    @FXML
    public void loadSeriesView(){
        String title = titleLabel.getText();
        MainViewController.instance.openSeriesView(title);
    }
}
