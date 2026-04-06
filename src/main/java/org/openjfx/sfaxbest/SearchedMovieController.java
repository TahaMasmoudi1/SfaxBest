package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SearchedMovieController {
    @FXML private ImageView posterImage;
    @FXML private Label titleLabel;
    @FXML private Label rateLabel;
    @FXML private Label releaseDateLabel;
    @FXML private Label durationLabel;
    @FXML private Label mainCategoryLabel;
    @FXML private Label secondaryCategoryLabel;
    @FXML private Label descriptionLabel;

    private void setData (Image image,String title,String rate,String releaseDate,String duration,String mainCategory,String secondaryCategory,String description){

        posterImage.setImage(image);
        titleLabel.setText(title);
        rateLabel.setText(rate);
        releaseDateLabel.setText(releaseDate);
        durationLabel.setText(duration);
        mainCategoryLabel.setText(mainCategory);
        secondaryCategoryLabel.setText(secondaryCategory);
        descriptionLabel.setText(description);

    }
}
