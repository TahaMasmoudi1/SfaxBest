package org.openjfx.sfaxbest;

import Services.FilmService;
import entities.Film;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeViewController {

    //Hero logic
    @FXML private StackPane heroTrailer;
    @FXML private MediaView trailerView;
    @FXML private ImageView blurryBackground;


    private MediaPlayer mediaPlayer;
    private List<Image> popularPosters = new ArrayList<>();
    private List<Image> trendingPosters = new ArrayList<>();

    @FXML private HBox PopularPosterRow;
    @FXML private HBox TrendingPosterRow;



    @FXML
    public void initialize() {

        FilmService filmService = new FilmService();
        List<Film> films = filmService.listAllWithCategories();
        //For testing
        for (Film film : films) {
            popularPosters.add(new Image(film.getPathPoster()));
        }
        trendingPosters = popularPosters;

        loadBrowseRow();
        loadTrendingRow();

        String path = getClass().getResource("/videos/gladiator_trailer.mp4").toExternalForm();

        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);

        trailerView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setMute(true);

        trailerView.fitWidthProperty().bind(heroTrailer.widthProperty());

        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle();
        clip.widthProperty().bind(heroTrailer.widthProperty());
        clip.heightProperty().bind(heroTrailer.heightProperty());
        heroTrailer.setClip(clip);

        blurryBackground.fitWidthProperty().bind(heroTrailer.widthProperty());
        blurryBackground.fitHeightProperty().bind(heroTrailer.heightProperty());

        heroTrailer.hoverProperty().addListener((obs, wasHover, isHover) -> {
            if (isHover) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        });

    }


    private void loadBrowseRow() {
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
            System.out.println("Couldn't load browse posters");
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
