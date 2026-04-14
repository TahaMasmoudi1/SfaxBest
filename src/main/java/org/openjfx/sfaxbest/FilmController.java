package org.openjfx.sfaxbest;

import Services.CategoryService;
import Services.FilmService;
import entities.Category;
import entities.Film;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
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
    @FXML private TextField tfPoster;
    @FXML private VBox form;
    @FXML private Button add2btn;
    @FXML private Button edit2btn;
    @FXML private ScrollPane scrollForm;
    @FXML private GridPane checkboxGrid;

    private static final int COLUMNS_COUNT = 3;
    private final List<Long> selectedLabels = new ArrayList<>();
    private FilmService filmService = new FilmService();
    private CategoryService categoryService = new CategoryService();

    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }

    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }

    @FXML
    public void goToDashbaord() throws IOException {
        App.setRoot("dashboardAdmin");
    }

    @FXML
    public void goToComments() throws IOException {
        App.setRoot("commentAdmin");
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

        movieTable.setItems(FXCollections.observableArrayList(filmService.listAllWithCategories()));
    }

    public void populateCheckboxes(List<Category> allCategories) {
        checkboxGrid.getChildren().clear();
        selectedLabels.clear();

        for (int i = 0; i < allCategories.size(); i++) {
            Category cat    = allCategories.get(i);
            CheckBox cb     = new CheckBox(cat.getCategorie());

            cb.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedLabels.add(cat.getId());
                } else {
                    selectedLabels.remove(cat.getId());
                }
            });

            checkboxGrid.add(cb, i % COLUMNS_COUNT, i / COLUMNS_COUNT);
        }
    }
    public void populateCheckboxesWithSelection(List<Category> allCategories,
                                                List<Category> filmCategories) {
        checkboxGrid.getChildren().clear();
        selectedLabels.clear();


        List<Long> filmCatIds = filmCategories.stream()
                .map(Category::getId)
                .toList();

        for (int i = 0; i < allCategories.size(); i++) {
            Category cat = allCategories.get(i);
            CheckBox cb  = new CheckBox(cat.getCategorie());


            boolean alreadySelected = filmCatIds.contains(cat.getId());
            cb.setSelected(alreadySelected);
            if (alreadySelected) {
                selectedLabels.add(cat.getId());
            }

            cb.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedLabels.add(cat.getId());
                } else {
                    selectedLabels.remove(cat.getId());
                }
            });

            checkboxGrid.add(cb, i % COLUMNS_COUNT, i / COLUMNS_COUNT);
        }
    }
    public List<Long> getSelectedLabels() {
        return new ArrayList<>(selectedLabels);
    }

    @FXML
    private void handleDelete() {
        Film selectedFilm = movieTable.getSelectionModel().getSelectedItem();

        if (selectedFilm == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Film");
        alert.setContentText("Are you sure you want to delete this film?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            filmService.delete(selectedFilm.getId());
            loadFilms();
        }
    }

    @FXML
    public void editFilms() {
        form.setManaged(true);
        form.setVisible(true);
        edit2btn.setVisible(true);
        edit2btn.setManaged(true);
        add2btn.setVisible(false);
        add2btn.setManaged(false);
        scrollForm.setManaged(true);
        scrollForm.setVisible(true);


        clearAllSelections();

        movieTable.setOnMouseClicked(event -> {
            Film selected = movieTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;


            tfTitle.setText(selected.getTitle());
            taDescription.setText(selected.getDescription());
            tfReleaseYear.setText(String.valueOf(selected.getReleaseYear()));
            tfDuration.setText(String.valueOf(selected.getDurationSeconds()));
            tfTrailer.setText(selected.getPathTrailer());
            tfBanner.setText(selected.getPathBanner());
            tfVideo.setText(selected.getPathVideo());
            tfPoster.setText(selected.getPathPoster() != null
                    ? selected.getPathPoster() : "");

            populateCheckboxesWithSelection(
                    categoryService.listALL(),
                    selected.getCategories().stream().toList()
            );
        });
    }
    private String pickFile(String title, FileChooser.ExtensionFilter... filters) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().addAll(filters);

        Window owner = movieTable.getScene() != null
                ? movieTable.getScene().getWindow()
                : null;

        File chosen = chooser.showOpenDialog(owner);
        return chosen != null ? chosen.getAbsolutePath() : null;
    }

    @FXML
    public void browseTrailer() {
        String path = pickFile("Select Trailer",
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi", "*.mov"),
                new FileChooser.ExtensionFilter("All Files",   "*.*"));
        if (path != null) tfTrailer.setText(path);
    }

    @FXML
    public void browseBanner() {
        String path = pickFile("Select Banner",
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp"),
                new FileChooser.ExtensionFilter("All Files",   "*.*"));
        if (path != null) tfBanner.setText(path);
    }

    @FXML
    public void browseVideo() {
        String path = pickFile("Select Video",
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi", "*.mov"),
                new FileChooser.ExtensionFilter("All Files",   "*.*"));
        if (path != null) tfVideo.setText(path);
    }

    @FXML
    public void browsePoster() {
        String path = pickFile("Select Poster",
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp"),
                new FileChooser.ExtensionFilter("All Files",   "*.*"));
        if (path != null) tfPoster.setText(path);
    }
    @FXML
    public void addFilms() {

        movieTable.setOnMouseClicked(event -> {});

        tfTitle.clear();
        tfBanner.clear();
        tfTrailer.clear();
        tfDuration.clear();
        tfVideo.clear();
        tfReleaseYear.clear();
        taDescription.clear();
        tfPoster.clear();

        populateCheckboxes(categoryService.listALL());

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

        filmService.save(title, description, trailer, banner, releaseDate, duration, video, getSelectedLabels(), poster);
        loadFilms();
    }

    @FXML
    public void editing() {
        Film selectedFilm = movieTable.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            String title = tfTitle.getText();
            String description = taDescription.getText();
            int releaseDate = Integer.parseInt(tfReleaseYear.getText());
            String trailer = tfTrailer.getText();
            String banner = tfBanner.getText();
            int duration = Integer.parseInt(tfDuration.getText());
            String video = tfVideo.getText();
            String poster = tfPoster.getText();


            filmService.update(selectedFilm.getId(),title,description,trailer,banner,releaseDate, duration, video, getSelectedLabels(), poster);
            loadFilms();
        }

    }
}