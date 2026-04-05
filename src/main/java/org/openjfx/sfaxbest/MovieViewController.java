package org.openjfx.sfaxbest;

import Services.FilmService;
import Services.RatingService;
import entities.CastMember;
import entities.Film;
import entities.VideoCast;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieViewController {

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
                //TODO: save rating to DB

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
                star.setTextFill(Color.web("#AAAAAA")); // Gris
            }
        }
    }

    public Film currentFilm;

    public void loadMovie(String title){
        FilmService  filmService = new FilmService();
        RatingService ratingService = new RatingService();
        List<Film> films = filmService.listAllWithCategories();
        Set<String> fullNames = new HashSet<>();
        for (Film film : films) {
            currentFilm = filmService.listFilmDetails(film.getId());;
            if (film.getTitle().equals(title)) {
                Set<CastMember> castMembers = film.getVideoCasts().stream().map(VideoCast::getCastMember).collect(Collectors.toSet());
                for (CastMember castMember : castMembers) {
                    fullNames.add(castMember.getName() + " " + castMember.getLastName());
                }
                String cast = fullNames.stream().collect(Collectors.joining(" "));
                setData(new Image(film.getPathBanner()),film.getTitle(),Double.toString(ratingService.calculateRate(film.getId())),Integer.toString(film.getDurationSeconds()%60),cast, film.getDescription());
                break;
            }
        }
    }
    @FXML ImageView bannerImage;
    @FXML Label titleLabel;
    @FXML Label ratingLabel;
    @FXML Label durationLabel;
    @FXML Label castLabel;
    @FXML Label descriptionLabel;


    @FXML
    private void setData(Image banner, String title, String rating, String duration, String cast, String description) {

        bannerImage.setImage(banner);
        titleLabel.setText(title);
        ratingLabel.setText("★ " + rating + "/5");
        durationLabel.setText(duration + " min");
        castLabel.setText("Cast members : " + cast);
        descriptionLabel.setText(description);

    }
    @FXML
    private void loadMedia(){
        MainViewController.instance.openMediaPlayerView(getClass().getResource(currentFilm.getPathVideo()).toExternalForm());
    }
}
