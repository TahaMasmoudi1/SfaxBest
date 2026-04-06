package org.openjfx.sfaxbest;

import Services.CategoryService;
import Services.DocumentaryService;
import Services.FilmService;
import entities.Category;
import entities.Documentary;
import entities.Film;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumantaryController {
    @FXML
    private TableView<Documentary> movieTable;
    @FXML
    private TableColumn<Documentary, Integer> colId;
    @FXML
    private TableColumn<Documentary, String> colTitle;
    @FXML
    private TableColumn<Documentary, String> colDescription;
    @FXML
    private TableColumn<Documentary, Integer> colReleaseDate;
    @FXML
    private TableColumn<Documentary, String> colTrailer;
    @FXML
    private TableColumn<Documentary, String> colBanner;
    @FXML
    private TableColumn<Documentary, Integer> colDuration;
    @FXML
    private TableColumn<Documentary, String> colVideo;
    @FXML
    private TableColumn<Documentary, String> colPoster;
    @FXML
    private TableColumn<Documentary, List<Category>> colCategorie;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfReleaseYear;
    @FXML
    private TextField tfDuration;
    @FXML
    private TextField tfTrailer;
    @FXML
    private TextField tfBanner;
    @FXML
    private TextField tfVideo;
    @FXML
    private VBox form;
    @FXML
    private Button add2btn;
    @FXML
    private Button edit2btn;
    @FXML private ScrollPane scrollForm;
    @FXML private GridPane checkboxGrid;
    @FXML private TextField tfPoster;
    private static final int COLUMNS_COUNT = 3;
    private final List<Long> selectedLabels = new ArrayList<>();
    private DocumentaryService documentaryService = new DocumentaryService();
    private CategoryService categoryService = new CategoryService();



    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }

    @FXML
    public void goToComments() throws IOException {
        App.setRoot("commentAdmin");
    }

    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }

    @FXML
    public void gotoDashboard() throws IOException {
        App.setRoot("dashboardAdmin");
    }

    @FXML
    public void initialize() {
        form.setManaged(false);
        form.setVisible(false);
        add2btn.setVisible(false);
        edit2btn.setVisible(false);
        add2btn.setManaged(false);
        edit2btn.setManaged(false);
        scrollForm.setManaged(false);
        scrollForm.setVisible(false);
        populateCheckboxes(categoryService.listALL());
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

        movieTable.setItems(FXCollections.observableArrayList(documentaryService.listAllWithCategories()));
    }

    public void populateCheckboxes(List<Category> labels) {
        checkboxGrid.getChildren().clear();
        selectedLabels.clear();

        for (int i = 0; i < labels.size(); i++) {
            Category cat = labels.get(i);
            CheckBox cb = new CheckBox(cat.getCategorie());

            cb.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedLabels.add(cat.getId());
                } else {
                    selectedLabels.remove(cat.getId());
                }
            });

            int column = i % COLUMNS_COUNT;
            int row = i / COLUMNS_COUNT;
            checkboxGrid.add(cb, column, row);
        }
    }

    public List<Long> getSelectedLabels() {
        return new ArrayList<>(selectedLabels);
    }

    @FXML
    private void handleDelete() {
        Documentary selectedDocumentary = movieTable.getSelectionModel().getSelectedItem();

        if (selectedDocumentary == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Film");
        alert.setContentText("Are you sure you want to delete this film?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            documentaryService.delete(selectedDocumentary.getId());
            loadFilms();
        }
    }

    @FXML
    public void editDocumantary() {
        form.setManaged(true);
        form.setVisible(true);
        edit2btn.setVisible(true);
        edit2btn.setManaged(true);
        add2btn.setVisible(false);
        add2btn.setManaged(false);
        scrollForm.setManaged(true);
        scrollForm.setVisible(true);

        movieTable.setOnMouseClicked(event -> {
            Documentary newSelection = movieTable.getSelectionModel().getSelectedItem();
            if (newSelection != null) {
                tfTitle.setText(newSelection.getTitle());
                taDescription.setText(newSelection.getDescription());
                tfReleaseYear.setText(String.valueOf(newSelection.getReleaseYear()));
                tfDuration.setText(String.valueOf(newSelection.getDurationSeconds()));
                tfTrailer.setText(newSelection.getPathTrailer());
                tfBanner.setText(newSelection.getPathBanner());
                tfVideo.setText(newSelection.getPathVideo());
            }
        });
    }

    @FXML
    public void addDocumantary() {
        movieTable.setOnMouseClicked(event -> {
        });
        tfTitle.clear();
        tfBanner.clear();
        tfTrailer.clear();
        tfDuration.clear();
        tfVideo.clear();
        tfReleaseYear.clear();
        taDescription.clear();
        tfPoster.clear();
        clearAllSelections();

        form.setManaged(true);
        form.setVisible(true);
        scrollForm.setManaged(true);
        scrollForm.setVisible(true);
        add2btn.setVisible(true);
        add2btn.setManaged(true);
        edit2btn.setVisible(false);
        edit2btn.setManaged(false);
    }

    public void clearAllSelections() {
        checkboxGrid.getChildren().forEach(node -> {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
        });

        selectedLabels.clear();
    }

    @FXML
    public void adding() {
        String title = tfTitle.getText();
        String description = taDescription.getText();
        int releaseDate = Integer.parseInt(tfReleaseYear.getText());
        String trailer = tfTrailer.getText();
        String banner = tfBanner.getText();
        int duration = Integer.parseInt(tfDuration.getText());
        String video = tfVideo.getText();
        String poster = tfPoster.getText();

        documentaryService.save(title, description, trailer, banner, releaseDate, duration, video, getSelectedLabels(), poster);
        loadFilms();
    }

    @FXML
    public void editing() {
        Documentary selectedFilm = movieTable.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            String title = tfTitle.getText();
            String description = taDescription.getText();
            int releaseDate = Integer.parseInt(tfReleaseYear.getText());
            String trailer = tfTrailer.getText();
            String banner = tfBanner.getText();
            int duration = Integer.parseInt(tfDuration.getText());
            String video = tfVideo.getText();
            String poster = tfPoster.getText();

            loadFilms();
        }
    }
}