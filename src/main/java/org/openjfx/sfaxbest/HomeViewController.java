package org.openjfx.sfaxbest;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.openjfx.sfaxbest.MoviePosterController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeViewController {

    //Side menu logic
    @FXML private VBox VBsideMenu;
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

    //Hero logic


    @FXML private ImageView activeHero;

    @FXML private StackPane activeSlot;

    private List<Image> heroImages = new ArrayList<>();

    private List<Image> popularPosters = new ArrayList<>();
    private List<Image> trendingPosters = new ArrayList<>();

    @FXML private HBox PopularPosterRow;
    @FXML private HBox TrendingPosterRow;
    private int currentIndex = 0;
    private Timeline slider;


    @FXML
    public void initialize() {
        String imagePath1 = getClass().getResource("/Images/Gladiator_hero.jpg").toExternalForm();
        String imagePath2 = getClass().getResource("/Images/galacticwar_panel.png").toExternalForm();
        String imagePath3 = getClass().getResource("/Images/Venom_hero.jpg").toExternalForm();

        String posterPath1 = getClass().getResource("/Images/the_matrix_poster.jpg").toExternalForm();
        String posterPath2 = getClass().getResource("/Images/gladiator_poster.jpg").toExternalForm();
        String posterPath3 = getClass().getResource("/Images/dark_Knight_poster.jpg").toExternalForm();
        heroImages = List.of(
                new Image(imagePath1),
                new Image(imagePath2),
                new Image(imagePath3)
        );
        popularPosters = List.of(
                new Image(posterPath1),
                new Image(posterPath2),
                new Image(posterPath3)
        );
        //For testing
        trendingPosters = popularPosters;

        loadPopularRow();
        loadTrendingRow();

        activeHero.setImage(heroImages.get(currentIndex));
        slider = new Timeline(new KeyFrame(Duration.seconds(4), e -> nextBanner()));

        slider.setCycleCount(Animation.INDEFINITE);
        slider.play();
        activeSlot.setOnMouseEntered(e -> slider.pause());
        activeSlot.setOnMouseExited(e -> slider.play());

    }

    @FXML
    public void nextBanner() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), activeHero);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {

            currentIndex = (currentIndex + 1) % heroImages.size();
            activeHero.setImage(heroImages.get(currentIndex));

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400),activeHero);
            fadeIn.setToValue(1);
            fadeIn.play();
        });

        fadeOut.play();
    }
    private void loadPopularRow() {
        try{
            for (Image image : popularPosters){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();

                cardController.setData(image);

                PopularPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load Popular posters");
        }
    }
    private void loadTrendingRow() {
        try{
            for (Image image : trendingPosters){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();

                cardController.setData(image);

                TrendingPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load Trending posters");
        }
    }

}
