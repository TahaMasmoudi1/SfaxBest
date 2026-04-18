package org.openjfx.sfaxbest;

import Services.CategoryService;
import Services.EpisodeService;
import Services.SeasonService;
import Services.SerieService;
import entities.Category;
import entities.Episode;
import entities.Season;
import entities.Serie;
import javafx.beans.property.SimpleIntegerProperty;
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

public class SerieController {

    private static Serie  selectedSerie;
    private static Season selectedSeason;

    @FXML private TableView<Serie>            movieTable;
    @FXML private TableColumn<Serie, Long>    colId;
    @FXML private TableColumn<Serie, String>  colTitle;
    @FXML private TableColumn<Serie, String>  colDescription;
    @FXML private TableColumn<Serie, Integer> colReleaseDate;
    @FXML private TableColumn<Serie, String>  colTrailer;
    @FXML private TableColumn<Serie, String>  colBanner;
    @FXML private TableColumn<Serie, String>  colPoster;
    @FXML private TableColumn<Serie, String>  colCategorie;
    @FXML private TableColumn<Serie, Integer> colDuration;

    @FXML private VBox      form;
    @FXML private TextField tfTitle;
    @FXML private TextArea  taDescription;
    @FXML private TextField tfReleaseYear;
    @FXML private TextField tfTrailer;
    @FXML private TextField tfBanner;
    @FXML private TextField tfPoster;
    @FXML private TextField tfVideo;
    @FXML private TextField tfDuration;
    @FXML private Button    add2btn;
    @FXML private Button    edit2btn;
    @FXML private GridPane  checkboxGrid;
    @FXML private ScrollPane scrollForm;

    @FXML private TableView<Season>            seasonTable;
    @FXML private TableColumn<Season, Long>    colSeasonId;
    @FXML private TableColumn<Season, Integer> colSeasonNumber;
    @FXML private TableColumn<Season, String>  colSeasonBanner;
    @FXML private TableColumn<Season, String>  colSeasonTrailer;
    @FXML private Label     lblSerieName;

    @FXML private VBox      seasonForm;
    @FXML private TextField tfSeasonNumber;
    @FXML private TextField tfSeasonBanner;
    @FXML private TextField tfSeasonTrailer;
    @FXML private Button    addSeasonBtn;
    @FXML private Button    editSeasonBtn;

    @FXML private TableView<Episode>             episodeTable;
    @FXML private TableColumn<Episode, Long>     colEpId;
    @FXML private TableColumn<Episode, Integer>  colEpNumber;
    @FXML private TableColumn<Episode, String>   colEpTitle;
    @FXML private TableColumn<Episode, Integer>  colEpDuration;
    @FXML private TableColumn<Episode, String>   colEpResume;
    @FXML private TableColumn<Episode, String>   colEpVideo;
    @FXML private TableColumn<Episode, String>   colEpThumbnail;
    @FXML private Label     lblSeasonName;

    @FXML private VBox      episodeForm;
    @FXML private TextField tfEpNumber;
    @FXML private TextField tfEpTitle;
    @FXML private TextField tfEpDuration;
    @FXML private TextArea  taEpResume;
    @FXML private TextField tfEpVideo;
    @FXML private TextField tfEpThumbnail;
    @FXML private Button    addEpBtn;
    @FXML private Button    editEpBtn;

    private static final int      COLUMNS_COUNT   = 3;
    private final List<Long>      selectedCatIds  = new ArrayList<>();
    private final SerieService    serieService    = new SerieService();
    private final CategoryService categoryService = new CategoryService();
    private final SeasonService   SeasonService   = new SeasonService();
    private final EpisodeService  EpisodeService  = new EpisodeService();

    @FXML public void goToFilms()        throws IOException { App.setRoot("mainAdmin"); }
    @FXML public void goToComments()     throws IOException { App.setRoot("commentAdmin"); }
    @FXML public void goToDocumantary()  throws IOException { App.setRoot("documantaryAdmin"); }
    @FXML public void gotoDashboard()    throws IOException { App.setRoot("dashboardAdmin"); }

    @FXML public void goToSeasons() throws IOException {
        Serie sel = movieTable != null ? movieTable.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { alert("Select a Serie first."); return; }
        selectedSerie = sel;
        App.setRoot("seasonAdmin");
    }

    @FXML public void goToEpisodes() throws IOException {
        Season sel = seasonTable != null ? seasonTable.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { alert("Select a Season first."); return; }
        selectedSeason = sel;
        App.setRoot("episodeAdmin");
    }

    @FXML public void backToSeries()  throws IOException { App.setRoot("seriesAdmin"); }
    @FXML public void backToSeasons() throws IOException { App.setRoot("seasonAdmin"); }

    @FXML
    public void initialize() {
        if (movieTable != null)  initSeriesPage();
        if (seasonTable != null) initSeasonsPage();
        if (episodeTable != null) initEpisodesPage();
    }

    private void initSeriesPage() {
        hideForm(form);
        hideBtns(add2btn, edit2btn);
        scrollForm.setManaged(false);
        scrollForm.setVisible(false);
        populateCheckboxes(categoryService.listALL());
        loadSerie();
    }

    private void initSeasonsPage() {
        hideForm(seasonForm);
        hideBtns(addSeasonBtn, editSeasonBtn);
        if (lblSerieName != null && selectedSerie != null)
            lblSerieName.setText(selectedSerie.getTitle());
        if (selectedSerie != null) loadSeasons();
    }

    private void initEpisodesPage() {
        hideForm(episodeForm);
        hideBtns(addEpBtn, editEpBtn);
        if (lblSeasonName != null && selectedSeason != null)
            lblSeasonName.setText("Season " + selectedSeason.getnSeason());
        if (selectedSeason != null) loadEpisodes();
    }

    public void loadSerie() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        colTrailer.setCellValueFactory(new PropertyValueFactory<>("pathTrailer"));
        colBanner.setCellValueFactory(new PropertyValueFactory<>("pathBanner"));
        colPoster.setCellValueFactory(new PropertyValueFactory<>("pathPoster"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("stringCategorie"));
        colDuration.setCellValueFactory(cd -> new SimpleIntegerProperty(0).asObject());
        movieTable.setItems(FXCollections.observableArrayList(serieService.listAllWithCategories()));
    }

    public void loadSeasons() {
        colSeasonId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSeasonNumber.setCellValueFactory(new PropertyValueFactory<>("nSeason"));
        colSeasonBanner.setCellValueFactory(new PropertyValueFactory<>("pathBannerSeason"));
        colSeasonTrailer.setCellValueFactory(new PropertyValueFactory<>("pathTrailerSeason"));
        if(selectedSeason != null) seasonTable.setItems(FXCollections.observableArrayList(selectedSerie.getSeasons()));
    }

    public void loadEpisodes() {
        colEpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEpNumber.setCellValueFactory(new PropertyValueFactory<>("nEpisode"));
        colEpTitle.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colEpDuration.setCellValueFactory(new PropertyValueFactory<>("dureeSeconds"));
        colEpResume.setCellValueFactory(new PropertyValueFactory<>("resume"));
        colEpVideo.setCellValueFactory(new PropertyValueFactory<>("videoPath"));
        colEpThumbnail.setCellValueFactory(new PropertyValueFactory<>("thumbnailUrl"));
        episodeTable.setItems(FXCollections.observableArrayList(selectedSeason.getEpisodes()));
    }

    @FXML
    public void addSerie() {
        clearSerieFields();
        populateCheckboxes(categoryService.listALL());
        showForm(form);
        showBtn(add2btn);
        hideBtn(edit2btn);
        scrollForm.setManaged(false);
        scrollForm.setVisible(false);
        movieTable.setOnMouseClicked(null);
    }

    @FXML
    public void editSerie() {
        Serie s = movieTable.getSelectionModel().getSelectedItem();
        if (s != null) fillSerieFields(s);
        showForm(form);
        showBtn(edit2btn);
        hideBtn(add2btn);
        movieTable.setOnMouseClicked(e -> {
            Serie sel = movieTable.getSelectionModel().getSelectedItem();
            if (sel != null) fillSerieFields(sel);
        });
    }

    @FXML
    public void addingSerieAction() {
        serieService.save(
                tfTitle.getText(),
                taDescription.getText(),
                parseIntSafe(tfReleaseYear.getText()),
                tfTrailer.getText(),
                tfBanner.getText(),
                new ArrayList<>(selectedCatIds),
                tfPoster.getText()
        );
        loadSerie();
        hideForm(form);
    }

    @FXML
    public void editingSerieAction() {
        Serie sel = movieTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        serieService.update(
                sel.getId(),
                tfTitle.getText(),
                taDescription.getText(),
                tfTrailer.getText(),
                tfBanner.getText(),
                parseIntSafe(tfReleaseYear.getText()),
                new ArrayList<>(selectedCatIds),
                tfPoster.getText()
        );
        loadSerie();
        hideForm(form);
    }

    @FXML
    public void handleDeleteSerie() {
        Serie sel = movieTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (!confirm("Delete Serie", "Delete \"" + sel.getTitle() + "\" and all its seasons/episodes?")) return;
        serieService.delete(sel.getId());
        loadSerie();
    }

    @FXML
    public void addSeasonAction() {
        clearSeasonFields();
        showForm(seasonForm);
        showBtn(addSeasonBtn);
        hideBtn(editSeasonBtn);
    }

    @FXML
    public void editSeasonAction() {
        Season sel = seasonTable.getSelectionModel().getSelectedItem();
        if (sel == null) { alert("Select a Season first."); return; }
        fillSeasonFields(sel);
        showForm(seasonForm);
        showBtn(editSeasonBtn);
        hideBtn(addSeasonBtn);
    }

    @FXML
    public void addingSeasonAction() {
        System.out.println(selectedSerie.getId());
        SeasonService.addSeasonToSerie(
                selectedSerie.getId(),
                parseIntSafe(tfSeasonNumber.getText()),
                tfSeasonBanner.getText(),
                tfSeasonTrailer.getText()
        );
        selectedSerie = serieService.listSerieDetails(selectedSerie.getId());
        loadSeasons();
        hideForm(seasonForm);
    }

    @FXML
    public void editingSeasonAction() {
        Season sel = seasonTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        SeasonService.update(
                sel.getId(),
                parseIntSafe(tfSeasonNumber.getText()),
                tfSeasonBanner.getText(),
                tfSeasonTrailer.getText()
        );
        selectedSerie = serieService.listSerieDetails(selectedSerie.getId());
        loadSeasons();
        hideForm(seasonForm);
    }

    @FXML
    public void handleDeleteSeason() {
        Season sel = seasonTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (!confirm("Delete Season", "Delete Season " + sel.getnSeason() + " and all its episodes?")) return;
        SeasonService.delete(sel.getId());
        selectedSerie = serieService.listSerieDetails(selectedSerie.getId());
        loadSeasons();
    }

    @FXML
    public void addEpAction() {
        clearEpFields();
        showForm(episodeForm);
        showBtn(addEpBtn);
        hideBtn(editEpBtn);
    }

    @FXML
    public void editEpAction() {
        Episode sel = episodeTable.getSelectionModel().getSelectedItem();
        if (sel == null) { alert("Select an Episode first."); return; }
        fillEpFields(sel);
        showForm(episodeForm);
        showBtn(editEpBtn);
        hideBtn(addEpBtn);
    }

    @FXML
    public void addingEpAction() {
        EpisodeService.addEpisodeToSeason(
                selectedSeason.getId(),
                parseIntSafe(tfEpNumber.getText()),
                tfEpTitle.getText(),
                parseIntSafe(tfEpDuration.getText()),
                taEpResume.getText(),
                tfEpThumbnail.getText(),
                tfEpVideo.getText()
        );
        //selectedSeason = SeasonService.findById(selectedSeason.getId());
        loadEpisodes();
        hideForm(episodeForm);
    }

    @FXML
    public void editingEpAction() {
        Episode sel = episodeTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        EpisodeService.update(
                sel.getId(),
                parseIntSafe(tfEpNumber.getText()),
                tfEpTitle.getText(),
                parseIntSafe(tfEpDuration.getText()),
                taEpResume.getText(),
                tfEpThumbnail.getText(),
                tfEpVideo.getText()
        );
        //selectedSeason = SeasonService.findById(selectedSeason.getId());
        loadEpisodes();
        hideForm(episodeForm);
    }

    @FXML
    public void handleDeleteEpisode() {
        Episode sel = episodeTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (!confirm("Delete Episode", "Delete Episode " + sel.getNEpisode() + " — " + sel.getTitre() + "?")) return;
        EpisodeService.delete(sel.getId(), sel.getSeason().getId());
        //selectedSeason = SeasonService.findById(selectedSeason.getId());
        loadEpisodes();
    }

    public void populateCheckboxes(List<Category> all) {
        checkboxGrid.getChildren().clear();
        selectedCatIds.clear();
        for (int i = 0; i < all.size(); i++) {
            Category cat = all.get(i);
            CheckBox cb  = new CheckBox(cat.getCategorie());
            cb.selectedProperty().addListener((obs, was, now) -> {
                if (now) selectedCatIds.add(cat.getId());
                else     selectedCatIds.remove(cat.getId());
            });
            checkboxGrid.add(cb, i % COLUMNS_COUNT, i / COLUMNS_COUNT);
        }
    }

    public void populateCheckboxesWithSelection(List<Category> all, List<Category> selected) {
        checkboxGrid.getChildren().clear();
        selectedCatIds.clear();
        List<Long> selIds = selected.stream().map(Category::getId).toList();
        for (int i = 0; i < all.size(); i++) {
            Category cat = all.get(i);
            CheckBox cb  = new CheckBox(cat.getCategorie());
            boolean pre  = selIds.contains(cat.getId());
            cb.setSelected(pre);
            if (pre) selectedCatIds.add(cat.getId());
            cb.selectedProperty().addListener((obs, was, now) -> {
                if (now) selectedCatIds.add(cat.getId());
                else     selectedCatIds.remove(cat.getId());
            });
            checkboxGrid.add(cb, i % COLUMNS_COUNT, i / COLUMNS_COUNT);
        }
    }

    private String pickFile(String title, FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.getExtensionFilters().addAll(filters);
        Window owner = null;
        if (movieTable != null && movieTable.getScene() != null)
            owner = movieTable.getScene().getWindow();
        else if (seasonTable != null && seasonTable.getScene() != null)
            owner = seasonTable.getScene().getWindow();
        else if (episodeTable != null && episodeTable.getScene() != null)
            owner = episodeTable.getScene().getWindow();
        File f = fc.showOpenDialog(owner);
        return f != null ? f.getAbsolutePath() : null;
    }

    private static final FileChooser.ExtensionFilter VIDEO_FILTER =
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi", "*.mov");
    private static final FileChooser.ExtensionFilter IMAGE_FILTER =
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp");
    private static final FileChooser.ExtensionFilter ALL_FILTER =
            new FileChooser.ExtensionFilter("All Files", "*.*");

    @FXML public void browseTrailer()       { String p = pickFile("Trailer",        VIDEO_FILTER, ALL_FILTER); if (p != null) tfTrailer.setText(p); }
    @FXML public void browseBanner()        { String p = pickFile("Banner",         IMAGE_FILTER, ALL_FILTER); if (p != null) tfBanner.setText(p); }
    @FXML public void browsePoster()        { String p = pickFile("Poster",         IMAGE_FILTER, ALL_FILTER); if (p != null) tfPoster.setText(p); }
    @FXML public void browseVideo()         { String p = pickFile("Video",          VIDEO_FILTER, ALL_FILTER); if (p != null) tfVideo.setText(p); }
    @FXML public void browseSeasonBanner()  { String p = pickFile("Season Banner",  IMAGE_FILTER, ALL_FILTER); if (p != null) tfSeasonBanner.setText(p); }
    @FXML public void browseSeasonTrailer() { String p = pickFile("Season Trailer", VIDEO_FILTER, ALL_FILTER); if (p != null) tfSeasonTrailer.setText(p); }
    @FXML public void browseEpVideo()       { String p = pickFile("Episode Video",  VIDEO_FILTER, ALL_FILTER); if (p != null) tfEpVideo.setText(p); }
    @FXML public void browseEpThumb()       { String p = pickFile("Thumbnail",      IMAGE_FILTER, ALL_FILTER); if (p != null) tfEpThumbnail.setText(p); }

    private void fillSerieFields(Serie s) {
        tfTitle.setText(s.getTitle());
        taDescription.setText(s.getDescription());
        tfReleaseYear.setText(s.getReleaseYear() != null ? String.valueOf(s.getReleaseYear()) : "");
        tfTrailer.setText(orEmpty(s.getPathTrailer()));
        tfBanner.setText(orEmpty(s.getPathBanner()));
        tfPoster.setText(orEmpty(s.getPathPoster()));
        populateCheckboxesWithSelection(categoryService.listALL(), new ArrayList<>(s.getCategories()));
    }

    private void clearSerieFields() {
        tfTitle.clear(); taDescription.clear(); tfReleaseYear.clear();
        tfTrailer.clear(); tfBanner.clear(); tfPoster.clear();
        populateCheckboxes(categoryService.listALL());
    }

    private void fillSeasonFields(Season s) {
        tfSeasonNumber.setText(s.getnSeason() != null ? String.valueOf(s.getnSeason()) : "");
        tfSeasonBanner.setText(orEmpty(s.getPathBannerSeason()));
        tfSeasonTrailer.setText(orEmpty(s.getPathTrailerSeason()));
    }

    private void clearSeasonFields() {
        tfSeasonNumber.clear(); tfSeasonBanner.clear(); tfSeasonTrailer.clear();
    }

    private void fillEpFields(Episode e) {
        tfEpNumber.setText(e.getNEpisode() != null ? String.valueOf(e.getNEpisode()) : "");
        tfEpTitle.setText(orEmpty(e.getTitre()));
        tfEpDuration.setText(e.getDureeSeconds() != null ? String.valueOf(e.getDureeSeconds()) : "");
        taEpResume.setText(orEmpty(e.getResume()));
        tfEpVideo.setText(orEmpty(e.getVideoPath()));
        tfEpThumbnail.setText(orEmpty(e.getThumbnailUrl()));
    }

    private void clearEpFields() {
        tfEpNumber.clear(); tfEpTitle.clear(); tfEpDuration.clear();
        taEpResume.clear(); tfEpVideo.clear(); tfEpThumbnail.clear();
    }

    private void showForm(VBox v)      { if (v != null) { v.setVisible(true);  v.setManaged(true);  } }
    private void hideForm(VBox v)      { if (v != null) { v.setVisible(false); v.setManaged(false); } }
    private void showBtn(Button b)     { if (b != null) { b.setVisible(true);  b.setManaged(true);  } }
    private void hideBtn(Button b)     { if (b != null) { b.setVisible(false); b.setManaged(false); } }
    private void hideBtns(Button... bs){ for (Button b : bs) hideBtn(b); }

    private int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }

    private String orEmpty(String s) { return s != null ? s : ""; }

    private boolean confirm(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(title);
        a.setContentText(msg);
        Optional<ButtonType> r = a.showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}