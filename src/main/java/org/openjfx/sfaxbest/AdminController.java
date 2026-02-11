package org.openjfx.sfaxbest;

import Services.FilmService;
import entities.Film;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AdminController {
    @FXML private TableView<Film> movieTable;
    @FXML private TableColumn<Film, Integer> colId;
    @FXML private TableColumn<Film, String> colTitle;
    @FXML private TableColumn<Film, String> colDescription;
    @FXML private TableColumn<Film, Integer> colReleaseDate;
    @FXML private TableColumn<Film, String> colTrailer;
    @FXML private TableColumn<Film, String> colBanner;
    @FXML private TableColumn<Film, Integer> colDuration;
    @FXML private TableColumn<Film, String> colVideo;
    FilmService filmService= new FilmService();
    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }
    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }
    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        colTrailer.setCellValueFactory(new PropertyValueFactory<>("pathTrailer"));
        colBanner.setCellValueFactory(new PropertyValueFactory<>("pathBanner"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationSeconds"));
        colVideo.setCellValueFactory(new PropertyValueFactory<>("pathVideo"));

        movieTable.setItems(FXCollections.observableArrayList(new Film(
                        "Inception",
                        "A thief who steals corporate secrets through dream-sharing technology.",
                        2010,
                        "trailer/inception.mp4",
                        "banner/inception.jpg",
                        8880,
                        "videos/inception.mp4",
                        "poster/inception.jpg"
                ),

                new Film(
                        "Interstellar",
                        "Explorers travel through a wormhole in space.",
                        2014,
                        "trailer/interstellar.mp4",
                        "banner/interstellar.jpg",
                        10140,
                        "videos/interstellar.mp4",
                        "poster/interstellar.jpg"
                ),

                new Film(
                        "The Matrix",
                        "A hacker discovers reality is a simulation.",
                        1999,
                        "trailer/matrix.mp4",
                        "banner/matrix.jpg",
                        8160,
                        "videos/matrix.mp4",
                        "poster/matrix.jpg"
                ),

                new Film(
                        "Avatar",
                        "A marine on an alien planet.",
                        2009,
                        "trailer/avatar.mp4",
                        "banner/avatar.jpg",
                        9720,
                        "videos/avatar.mp4",
                        "poster/avatar.jpg"
                )
        ));
    }



}
