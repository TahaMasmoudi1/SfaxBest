package org.openjfx.sfaxbest;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SearchViewController {

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

    @FXML private TextField searchField;
    @FXML
    private ComboBox<String> yearFilter;
    @FXML private ComboBox<String> genreFilter;
    @FXML private TilePane resultsGrid;



    @FXML
    private void onSearchChanged() {
        filter();
    }

    @FXML
    private void onFilterChanged() {
        filter();
    }

    private void filter() {

    }
}
