package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HistoryCardController {
    @FXML ImageView moviePoster;
    @FXML ProgressBar progressBar;
    @FXML Label titleLabel;

    public void setData(Image image, String title) {
        moviePoster.setImage(image);
        titleLabel.setText(title);

    }
}
