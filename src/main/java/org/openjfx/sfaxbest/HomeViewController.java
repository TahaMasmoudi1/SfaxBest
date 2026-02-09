package org.openjfx.sfaxbest;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.List;

public class HomeViewController {

    //Side menu logic
    @FXML private VBox VBsideMenu;
    @FXML private Pane SideMenuPane;
    private boolean menuVisible = false;


    @FXML
    private void InitializeMenu() {
        toggleMenu();
    }

    @FXML
    private void toggleMenu() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(250), VBsideMenu);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), SideMenuPane);

        if (menuVisible) {
            // Slide OUT
            transition.setToX(-VBsideMenu.getPrefWidth());
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
    @FXML private HBox heroRow;
    @FXML private ImageView prevHero;
    @FXML private ImageView activeHero;
    @FXML private ImageView nextHero;
    @FXML private Label heroTitle;

    private final List<Image> heroImages = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        heroImages.add(new Image(getClass().getResource("/Images/blackpanther_hero.jpg").toExternalForm()));
        heroImages.add(new Image(getClass().getResource("/Images/galacticwar_panel.png").toExternalForm()));
        heroImages.add(new Image(getClass().getResource("/Images/Gladiator_hero.jpg").toExternalForm()));

        updateHeroes();
    }
    private void updateHeroes() {
        int prevIndex = (currentIndex - 1 + heroImages.size()) % heroImages.size();
        int nextIndex = (currentIndex + 1) % heroImages.size();

        prevHero.setImage(heroImages.get(prevIndex));
        activeHero.setImage(heroImages.get(currentIndex));
        nextHero.setImage(heroImages.get(nextIndex));

        heroTitle.setText("FEATURED " + (currentIndex + 1));
    }
    @FXML
    public void previousHero() {
        slideCarousel(1);
    }
    @FXML
    public void nextHero() {
        slideCarousel(-1);
    }
    private void slideCarousel(int direction) {
        double slideDistance = 200; // roughly width of one banner + spacing

        TranslateTransition slide = new TranslateTransition(Duration.millis(350), heroRow);
        slide.setByX(direction * slideDistance);
        slide.setInterpolator(Interpolator.EASE_BOTH);

        slide.setOnFinished(e -> {
            currentIndex = (currentIndex - direction + heroImages.size()) % heroImages.size();
            updateHeroes();
            heroRow.setTranslateX(0);
        });

        slide.play();
    }
}

