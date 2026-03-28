package org.openjfx.sfaxbest;

import Services.DocumentaryService;
import entities.Category;
import entities.Documentary;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class DocumantaryController {
    @FXML private TableView<Documentary> movieTable;
    @FXML private TableColumn<Documentary, Integer> colId;
    @FXML private TableColumn<Documentary, String> colTitle;
    @FXML private TableColumn<Documentary, String> colDescription;
    @FXML private TableColumn<Documentary, Integer> colReleaseDate;
    @FXML private TableColumn<Documentary, String> colTrailer;
    @FXML private TableColumn<Documentary, String> colBanner;
    @FXML private TableColumn<Documentary, Integer> colDuration;
    @FXML private TableColumn<Documentary, String> colVideo;
    @FXML private TableColumn<Documentary, String> colPoster;
    @FXML private TableColumn<Documentary, List<Category>> colCategorie;
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


    DocumentaryService documentaryService = new DocumentaryService();
    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }
    @FXML
    public void goToComments() throws IOException{
        App.setRoot("commentAdmin");
    }
    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }
    @FXML
    public void gotoDashboard()throws IOException{
        App.setRoot("dashboardAdmin");
    }
    @FXML
    public void initialize() {
        form.setManaged(false);
        add2btn.setVisible(false);
        edit2btn.setVisible(false);
        add2btn.setManaged(false);
        edit2btn.setManaged(false);
        colId.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.06)); // 6%
        colTitle.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.18)); // 18%
        colDescription.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.30)); // 30%
        colReleaseDate.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.08));
        colTrailer.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.12));
        colBanner.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.12));
        colDuration.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.07));
        colVideo.prefWidthProperty().bind(movieTable.widthProperty().multiply(0.07));

        loadDocumantary();
    }

    public void loadDocumantary() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        colTrailer.setCellValueFactory(new PropertyValueFactory<>("pathTrailer"));
        colBanner.setCellValueFactory(new PropertyValueFactory<>("pathBanner"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationSeconds"));
        colVideo.setCellValueFactory(new PropertyValueFactory<>("pathVideo"));
        //colPoster.setCellValueFactory(new PropertyValueFactory<>("pathPoster"));
        //colCategorie.setCellValueFactory(new PropertyValueFactory<>("Category"));

        movieTable.setItems(FXCollections.observableArrayList(FXCollections.observableArrayList(
                documentaryService.findAll()
        )));
    }
    @FXML
    public void editDocumantary() {
        form.setManaged(true);
        form.setVisible(true);
        edit2btn.setVisible(true);
        edit2btn.setManaged(true);
        add2btn.setVisible(false);
        add2btn.setManaged(false);

        movieTable.setOnMouseClicked(event->{
            Documentary newSelection = movieTable.getSelectionModel().getSelectedItem();
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
        loadDocumantary();


    }
    @FXML
    public void addDocumantary() {
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
    @FXML
    public void adding(){
        String title = tfTitle.getText();
        String description = taDescription.getText();
        int releaseDate = Integer.parseInt(tfReleaseYear.getText());
        String trailer = tfTrailer.getText();
        String banner = tfBanner.getText();
        int duration = Integer.parseInt(tfDuration.getText());
        String video = tfVideo.getText();
        //DocumentaryService.save(title,description,trailer,banner,releaseDate,duration,video,1,2);
        loadDocumantary();
    }
    public void deleteDocumantary() {
        movieTable.setOnMouseClicked(event->{
            Documentary newSelection = movieTable.getSelectionModel().getSelectedItem();
            if(newSelection!=null){
                Long id = newSelection.getId();
                documentaryService.delete(id);
                loadDocumantary();
            }
        });

    }



}
