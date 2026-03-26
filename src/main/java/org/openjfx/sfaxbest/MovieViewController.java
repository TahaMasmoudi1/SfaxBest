package org.openjfx.sfaxbest;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MovieViewController {

    //Side menu logic
    @FXML
    private VBox VBsideMenu;
    @FXML private Pane SideMenuPane;
    private boolean menuVisible = false;

    @FXML
    private void toggleMenu() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(250), VBsideMenu);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), SideMenuPane);

        if (menuVisible) {
            // Slide OUT
            transition.setToX(-VBsideMenu.getPrefWidth() - 20);
            VBsideMenu.setMouseTransparent(true);
            // Backwards fade
            fadeTransition.setFromValue(0.7);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished(e -> SideMenuPane.setVisible(false));
            fadeTransition.play();

        } else {

            //Lock scroll feature to implement

            // Slide IN
            SideMenuPane.setVisible(true);
            transition.setToX(-640);
            VBsideMenu.setMouseTransparent(false);
            // Fade
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.7);
            fadeTransition.play();

        }

        menuVisible = !menuVisible;
        transition.play();
    }
    @FXML
    public void initialize (){

        setInteractiveStars();
    }

    @FXML private HBox starRatingBox;
    private int currentRating = 0;

    private void setInteractiveStars() {

        for (int i = 0; i < starRatingBox.getChildren().size(); i++) {
            Label star = (Label) starRatingBox.getChildren().get(i);
            final int starValue = i + 1; // 1 to 5

            // Hover effect
            star.setOnMouseEntered(e -> updateStarsVisual(starValue));
            star.setOnMouseExited(e -> updateStarsVisual(currentRating));

            // Click to lock in rating
            star.setOnMouseClicked(e -> {
                currentRating = starValue;
                System.out.println("User locked in: " + currentRating + " stars");
                //TODO : save rating to DB
            });
        }



    }

    private void updateStarsVisual(int highlightCount) {
        for (int i = 0; i < 5; i++) {
            Label star = (Label) starRatingBox.getChildren().get(i);
            if (i < highlightCount) {
                star.setText("★");
                star.setTextFill(Color.web("#FFD700")); // Gold
            } else {
                star.setText("☆");
                star.setTextFill(Color.web("#AAAAAA")); // Gray
            }
        }
    }
}
