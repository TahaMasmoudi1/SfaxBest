package org.openjfx.sfaxbest;

import entities.User;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class MainViewController {

    //Side menu logic
    @FXML
    private VBox VBsideMenu;
    @FXML private Pane SideMenuPane;
    private boolean menuVisible = false;

    @FXML
    private void toggleMenu() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(250), VBsideMenu);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), SideMenuPane);

        if (menuVisible) {
            // Slide OUT
            transition.setToX(-VBsideMenu.getPrefWidth() - 20);
            VBsideMenu.setMouseTransparent(true);
            // Backwards fade
            fadeTransition.setFromValue(0.7);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished(e -> SideMenuPane.setVisible(false));
            fadeTransition.play();

        } else {

            //Lock scroll feature to implement

            // Slide IN
            SideMenuPane.setVisible(true);
            transition.setToX(-640);
            VBsideMenu.setMouseTransparent(false);
            // Fade
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.7);
            fadeTransition.play();

        }

        menuVisible = !menuVisible;
        transition.play();
    }

    public static MainViewController instance;

    @FXML public void initialize() {

        instance = this;

        switchView("home-view.fxml");
    }

    @FXML BorderPane mainBorderPane;

    @FXML
    public void searchMenu(){
        switchView("search-view.fxml");
    }
    @FXML
    public void userPage(){
        switchView("user-home-page.fxml");
    }
    @FXML
    public void homePage(){
        switchView("home-view.fxml");
    }
    @FXML

    public void switchView(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            System.out.println("Couldn't load FXML from path: " + fxmlPath);
            e.printStackTrace();
        }
    }
    public void openMovieView(String title){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("movie-view.fxml"));
            Node view = loader.load();

            MovieViewController movieController = loader.getController();

            movieController.loadMovie(title);

            mainBorderPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openMediaPlayerView(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("media-player-view.fxml"));
            Node view = loader.load();

            MediaPlayerController mediaPlayerController = loader.getController();

            mediaPlayerController.loadVideo(path);

            mainBorderPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    User currentUser = new User();
    public void setCurrentUser(User user){
        currentUser=user;
    }
}
