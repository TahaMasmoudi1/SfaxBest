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

import java.util.*;
import java.util.stream.Collectors;

public class SearchViewController {

    @FXML private TextField searchField;
    @FXML private ComboBox<String> yearFilter;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private TilePane resultsGrid;
    @FXML private VBox resultsContainer;


    RatingService ratingService = new RatingService();

    @FXML
    public void initialize() {
        FilmService filmService = new FilmService();
        List<Film> films = filmService.listAllWithCategories();

        initComboBoxes(films);
        loadAllMovies(films);

    }
    private void initComboBoxes(List<Film> films) {
        Set<String> categories = new HashSet<>();
        for (Film film : films) {
            Set<Category> genres = film.getCategories();

            for(Category category : genres){
                categories.add(category.getCategorie());
            }
        }
        Set<String> uniqueYears = films.stream().map(film -> String.valueOf(film.getReleaseYear())).collect(Collectors.toSet());

        List<String> listCategories = categories.stream().sorted().toList();

        categoryFilter.getItems().clear();
        categoryFilter.getItems().add("All Categories"); // Always add an "All" option!
        categoryFilter.getItems().addAll(listCategories);
        categoryFilter.getSelectionModel().selectFirst();

        yearFilter.getItems().clear();
        yearFilter.getItems().add("All Years");
        yearFilter.getItems().addAll(uniqueYears.stream().sorted(Comparator.reverseOrder()).toList()); // Reverse order puts newest years at the top!
        yearFilter.getSelectionModel().selectFirst();
    }

    private void loadAllMovies (List<Film> films) {
        try {
            for (Film film : films) {
                Set<Category> genres = film.getCategories();
                List<String> categories = new ArrayList<>();

                for(Category category : genres){
                    categories.add(category.getCategorie());
                }

                if (categories.isEmpty() || categories.size() == 1) {
                    categories.add("None");
                    categories.add("None");
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("searched-movie-card.fxml"));
                Node cardNode = loader.load();

                SearchedMovieController cardController = loader.getController();


                String rate = Double.toString(ratingService.calculateRate(film.getId()));

                if (rate == null) {
                    rate = "N/A";
                }

                cardController.setData(new Image(getClass().getResource(film.getPathPoster()).toExternalForm()), film.getTitle(), rate,Integer.toString(film.getReleaseYear()),Integer.toString(film.getDurationSeconds()/60),categories.get(0), categories.get(1), film.getDescription());

                resultsContainer.getChildren().add(cardNode);
            }
        }catch (Exception e){
            e.printStackTrace();
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
