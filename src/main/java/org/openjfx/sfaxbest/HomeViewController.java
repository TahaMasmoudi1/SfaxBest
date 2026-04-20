package org.openjfx.sfaxbest;

import Services.FilmService;
import Services.RatingService;
import Services.SerieService;
import entities.Category;
import entities.Film;
import entities.Serie;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeViewController {

    //Hero logic
    @FXML private StackPane heroTrailer;
    @FXML private MediaView trailerView;
    @FXML private ImageView blurryBackground;


    private MediaPlayer mediaPlayer;

    @FXML private HBox PopularPosterRow;
    @FXML private HBox ActionPosterRow;
    @FXML private HBox comedyPosterRow;
    @FXML private HBox adventurePosterRow;
    @FXML private HBox horrorPosterRow;
    @FXML private HBox seriesPosterRow;

    RatingService ratingService = new RatingService();
    FilmService filmService = new FilmService();
    SerieService serieService = new SerieService();
    @FXML
    public void initialize() {

        List<Film> allFilms = filmService.listAllWithCategories();
        List<Film> actionFilms = filmService.listFilmByCategoryName("Action",0,20);
        List<Film> comedyFilms = filmService.listFilmByCategoryName("Comedy",0,20);
        List<Film> adventureFilms = filmService.listFilmByCategoryName("Adventure",0,20);
        List<Film> horrorFilms = filmService.listFilmByCategoryName("Horror",0,20);
        List<Serie> series = serieService.listAllWithCategories();

        loadBrowseRow(allFilms);
        loadActionRow(actionFilms);
        loadComedyRow(comedyFilms);
        loadAdventureRow(adventureFilms);
        loadHorrorRow(horrorFilms);
        loadSeriesRow(series);

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

    @FXML
    private void loadTrailer(){
        MainViewController.instance.openMovieView("Gladiator Fight Scene");
    }

    private void loadBrowseRow(List<Film> films) {
        try{
            for (Film film : films) {

                Set<Category> genres = film.getCategories();
                Set<String> categories = new HashSet<>();

                for(Category category : genres){
                    categories.add(category.getCategorie());
                }
                String joinedCategories = String.join(" | ", categories) ;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();

                String rate = Double.toString(ratingService.calculateRate(film.getId()));

                if (rate == null) {
                    rate = "N/A";
                }

                cardController.setData(film.getTitle(),joinedCategories, rate,new Image(getClass().getResource(film.getPathPoster()).toExternalForm()));

                PopularPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load browse posters");
        }
    }
    private void loadActionRow(List<Film> films) {
        try{
            for (Film  film : films) {
                Set<Category> genres = film.getCategories();
                Set<String> categories = new HashSet<>();


                for(Category category : genres){
                    categories.add(category.getCategorie());
                }
                String joinedCategories = String.join(" | ", categories) ;


                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(film.getId()));
                }catch (NullPointerException e){
                    rate = "N/A";
                }

                cardController.setData(film.getTitle(),joinedCategories,rate,new Image(getClass().getResource(film.getPathPoster()).toExternalForm()));

                ActionPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load action");
        }
    }
    private void loadComedyRow(List<Film> films) {
        try{
            for (Film  film : films) {
                Set<Category> genres = film.getCategories();
                Set<String> categories = new HashSet<>();


                for(Category category : genres){
                    categories.add(category.getCategorie());
                }
                String joinedCategories = String.join(" | ", categories) ;


                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(film.getId()));
                }catch (NullPointerException e){
                    rate = "N/A";
                }

                cardController.setData(film.getTitle(),joinedCategories,rate,new Image(getClass().getResource(film.getPathPoster()).toExternalForm()));

                comedyPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load comedy");
        }
    }

    private void loadAdventureRow(List<Film> films) {
        try{
            for (Film  film : films) {
                Set<Category> genres = film.getCategories();
                Set<String> categories = new HashSet<>();


                for(Category category : genres){
                    categories.add(category.getCategorie());
                }
                String joinedCategories = String.join(" | ", categories) ;


                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(film.getId()));
                }catch (NullPointerException e){
                    rate = "N/A";
                }

                cardController.setData(film.getTitle(),joinedCategories,rate,new Image(getClass().getResource(film.getPathPoster()).toExternalForm()));

                adventurePosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load adventure");
        }
    }

    private void loadHorrorRow(List<Film> films) {
        try{
            for (Film  film : films) {
                Set<Category> genres = film.getCategories();
                Set<String> categories = new HashSet<>();


                for(Category category : genres){
                    categories.add(category.getCategorie());
                }
                String joinedCategories = String.join(" | ", categories) ;


                FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-poster-card.fxml"));
                Node cardNode = loader.load();

                MoviePosterController cardController = loader.getController();
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(film.getId()));
                }catch (NullPointerException e){
                    rate = "N/A";
                }

                cardController.setData(film.getTitle(),joinedCategories,rate,new Image(getClass().getResource(film.getPathPoster()).toExternalForm()));

                horrorPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load Trending horror");
        }
    }

    private void loadSeriesRow(List<Serie> series) {
        try{
            for (Serie serie : series) {
                Set<Category> genres = serie.getCategories();
                Set<String> categories = new HashSet<>();


                for(Category category : genres){
                    categories.add(category.getCategorie());
                }
                String joinedCategories = String.join(" | ", categories) ;


                FXMLLoader loader = new FXMLLoader(getClass().getResource("series-poster-card.fxml"));
                Node cardNode = loader.load();

                SeriesPosterController cardController = loader.getController();
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(serie.getId()));
                }catch (Exception e){
                    rate = "N/A";
                }

                cardController.setData(serie.getTitle(),joinedCategories,rate,new Image(getClass().getResource(serie.getPathPoster()).toExternalForm()));

                seriesPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load series");
        }
    }



}
