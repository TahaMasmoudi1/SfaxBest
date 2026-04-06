package org.openjfx.sfaxbest;

import Services.FilmService;
import Services.RatingService;
import entities.Category;
import entities.Film;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchViewController {

    @FXML private TextField searchField;
    @FXML private ComboBox<String> yearFilter;
    @FXML private ComboBox<String> genreFilter;
    @FXML private TilePane resultsGrid;
    @FXML private VBox resultsContainer;


    RatingService ratingService;

    @FXML
    public void initialize() {
        FilmService filmService = new FilmService();
        List<Film> films = filmService.listAllWithCategories();

        loadAllMovies(films);


    }

    private void loadAllMovies (List<Film> films) {
        try {
            for (Film film : films) {
                Set<Category> genres = film.getCategories();
                List<String> categories = new ArrayList<>();

                for(Category category : genres){
                    categories.add(category.getCategorie());
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("searched-movie-card.fxml"));
                Node cardNode = loader.load();

                SearchedMovieController cardController = loader.getController();

                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(film.getId()));
                }catch (NullPointerException e){
                    rate = "N/A";
                }

                cardController.setData(new Image(getClass().getResource(film.getPathPoster()).toExternalForm()), film.getTitle(), rate,Integer.toString(film.getReleaseYear()),Integer.toString(film.getDurationSeconds()/60),categories.get(0), categories.get(1), film.getDescription());

                resultsContainer.getChildren().add(cardNode);
            }
        }catch (Exception e){
            System.out.println("Could not load movies");
        }

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
