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
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.stream.Collectors;

public class SearchViewController {

    @FXML private TextField searchField;
    @FXML private ComboBox<String> yearFilter;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private VBox resultsContainer;


    RatingService ratingService = new RatingService();

    @FXML
    public void initialize() {
        FilmService filmService = new FilmService();
        List<Film> films = filmService.listAllWithCategories();

        initFilters(films);

        loadMovies(films);

    }
    private void initFilters(List<Film> films) {
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
        categoryFilter.getItems().add("All Categories");
        categoryFilter.getItems().addAll(listCategories);
        categoryFilter.getSelectionModel().selectFirst();

        yearFilter.getItems().clear();
        yearFilter.getItems().add("All Years");
        yearFilter.getItems().addAll(uniqueYears.stream().sorted(Comparator.reverseOrder()).toList());
        yearFilter.getSelectionModel().selectFirst();

        categoryFilter.setOnAction(event -> applyFilters(films));
        yearFilter.setOnAction(event -> applyFilters(films));
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters(films));

    }
    private void applyFilters(List<Film> films) {
        String selectedCategory = categoryFilter.getValue();
        String selectedYear = yearFilter.getValue();
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();

        List<Film> filteredList = films.stream()
                .filter(film -> {

                    boolean matchesCategory = selectedCategory.equals("All Categories") || film.getCategories().stream().anyMatch(category -> category.getCategorie().equals(selectedCategory));
                    boolean matchesYear = selectedYear.equals("All Years") || String.valueOf(film.getReleaseYear()).equals(selectedYear);
                    boolean matchesSearch = searchText.isEmpty() || film.getTitle().toLowerCase().contains(searchText);

                    return matchesCategory && matchesYear && matchesSearch;
                })
                .toList();
        renderMovies(filteredList);
    }

    private void loadMovies (List<Film> films) {
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
    private void renderMovies (List<Film> films) {
        resultsContainer.getChildren().clear();
        loadMovies(films);
    }

}
