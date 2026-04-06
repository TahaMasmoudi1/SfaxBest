package org.openjfx.sfaxbest;

import Services.FilmService;
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



    @FXML private TextField searchField;
    @FXML private ComboBox<String> yearFilter;
    @FXML private ComboBox<String> genreFilter;
    @FXML private TilePane resultsGrid;
    @FXML private VBox resultsContainer;


    @FXML
    public void initialize() {
        FilmService filmService = new FilmService();

    }
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
