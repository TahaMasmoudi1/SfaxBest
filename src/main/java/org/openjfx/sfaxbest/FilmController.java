package org.openjfx.sfaxbest;

import Services.FilmService;
import entities.Category;
import entities.Film;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmController {
    @FXML private TableView<Film> movieTable;
    @FXML private TableColumn<Film, Integer> colId;
    @FXML private TableColumn<Film, String> colTitle;
    @FXML private TableColumn<Film, String> colDescription;
    @FXML private TableColumn<Film, Integer> colReleaseDate;
    @FXML private TableColumn<Film, String> colTrailer;
    @FXML private TableColumn<Film, String> colBanner;
    @FXML private TableColumn<Film, Integer> colDuration;
    @FXML private TableColumn<Film, String> colVideo;
    @FXML private TableColumn<Film, String> colPoster;
    @FXML private TableColumn<Film, List<String>> colCategorie;
    @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private TextField tfReleaseYear;
    @FXML private TextField tfDuration;
    @FXML private TextField tfTrailer;
    @FXML private TextField tfBanner;
    @FXML private TextField tfVideo;
    @FXML private VBox form;
    @FXML private Button add2btn;
    @FXML private Button edit2btn;

    FilmService filmService= new FilmService();
    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }
    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }
    @FXML
    public void goToDashbaord() throws IOException{
        App.setRoot("dashboardAdmin");
    }
    @FXML
    public void goToComments() throws IOException{
        App.setRoot("commentAdmin");
    }
    @FXML
    public void initialize() {
        form.setManaged(false);
        add2btn.setVisible(false);
        edit2btn.setVisible(false);
        add2btn.setManaged(false);
        edit2btn.setManaged(false);



        loadFilms();
    }

    public void loadFilms() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        colTrailer.setCellValueFactory(new PropertyValueFactory<>("pathTrailer"));
        colBanner.setCellValueFactory(new PropertyValueFactory<>("pathBanner"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationSeconds"));
        colVideo.setCellValueFactory(new PropertyValueFactory<>("pathVideo"));
        colPoster.setCellValueFactory(new PropertyValueFactory<>("pathPoster"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("StringCategorie"));

        movieTable.setItems(FXCollections.observableArrayList(FXCollections.observableArrayList(
                filmService.listAllWithCategories()
        )));
    }
    @FXML
    private void handleDelete() {

        Film selectedFilm = movieTable.getSelectionModel().getSelectedItem();

        if (selectedFilm == null) {
            System.out.println("No film selected!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Film");

        alert.setContentText("Are you sure you want to delete this film?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            filmService.delete(selectedFilm.getId());
        }
        loadFilms();
    }
    @FXML
    public void editFilms() {
        form.setManaged(true);
        form.setVisible(true);
        edit2btn.setVisible(true);
        edit2btn.setManaged(true);
        add2btn.setVisible(false);
        add2btn.setManaged(false);

        movieTable.setOnMouseClicked(event->{
            Film newSelection = movieTable.getSelectionModel().getSelectedItem();
            if(newSelection!=null){
                String id = String.valueOf(newSelection.getId());
                String title = newSelection.getTitle();
                String description = newSelection.getDescription();
                String releaseDate = String.valueOf(newSelection.getReleaseYear());
                String trailer = newSelection.getPathTrailer();
                String banner = newSelection.getPathBanner();
                String duration = String.valueOf(newSelection.getDurationSeconds());
                String video = newSelection.getPathVideo();
                tfTitle.setText(title);
                tfBanner.setText(banner);
                tfTrailer.setText(duration);
                tfTrailer.setText(trailer);
                tfBanner.setText(banner);
                taDescription.setText(description);
                tfVideo.setText(video);
                tfReleaseYear.setText(releaseDate);
                tfDuration.setText(duration);
                //ne9sin el poster & category

            }
        });
        loadFilms();


    }
    @FXML
    public void addFilms() {
        movieTable.setOnMouseClicked(event->{});
        tfTitle.setText(null);
        tfBanner.setText(null);
        tfTrailer.setText(null);
        tfDuration.setText(null);
        tfVideo.setText(null);
        tfReleaseYear.setText(null);
        taDescription.setText(null);

        form.setManaged(true);
        form.setVisible(true);
        add2btn.setVisible(true);
        add2btn.setManaged(true);
        edit2btn.setVisible(false);
        edit2btn.setManaged(false);

    }
    @FXML public void adding(){
        String title = tfTitle.getText();
        String description = taDescription.getText();
        int releaseDate = Integer.parseInt(tfReleaseYear.getText());
        String trailer = tfTrailer.getText();
        String banner = tfBanner.getText();
        int duration = Integer.parseInt(tfDuration.getText());
        String video = tfVideo.getText();
        List<Long> l = new ArrayList<>();
        Long a = 100L;
        l.add(a);
        filmService.save(title,description,trailer,banner,releaseDate,duration,video,l,"111");
        loadFilms();
    }
    @FXML public void editing(){
        Film selectedFilm = movieTable.getSelectionModel().getSelectedItem();
        String title = tfTitle.getText();
        String description = taDescription.getText();
        int releaseDate = Integer.parseInt(tfReleaseYear.getText());
        String trailer = tfTrailer.getText();
        String banner = tfBanner.getText();
        int duration = Integer.parseInt(tfDuration.getText());
        String video = tfVideo.getText();
        //filmService.update(selectedFilm.getId(),title,description,trailer,banner,releaseDate,duration,video,1,2);
        loadFilms();
    }



}
