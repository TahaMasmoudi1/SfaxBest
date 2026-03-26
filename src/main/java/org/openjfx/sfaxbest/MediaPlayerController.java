package org.openjfx.sfaxbest;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaPlayerController {
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

            //TODO: Lock scroll feature to implement

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

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = true;
    @FXML private StackPane playerRoot;
    @FXML Label currentTimeLabel;
    @FXML Slider timeSlider;
    @FXML Slider volumeSlider;
    @FXML Button playPauseBtn;
    @FXML MediaView mediaView;

    public void initialize() {
        String pathVideo = getClass().getResource("/videos/gladiator_trailer.mp4").toExternalForm();
        Media media = new Media(pathVideo);
        mediaView.fitWidthProperty().bind(playerRoot.widthProperty());
        mediaView.fitHeightProperty().bind(playerRoot.heightProperty());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setVolume(volumeSlider.getValue()); // Set initial volume
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mediaPlayer.setVolume(newVal.doubleValue());
        });
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                    timeSlider.setValue(newTime.toSeconds());
                    currentTimeLabel.setText(formatTime(newTime));
                });
        mediaView.setMediaPlayer(mediaPlayer);


    }




    @FXML public void playNextEpisode(){}
    @FXML public void cancelBingeWatch(){}
    @FXML public void togglePlayPause(){
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseBtn.setText("▶");
        }else  {
            mediaPlayer.play();
            isPlaying = true;
            playPauseBtn.setText("⏸");
        }
    }
    @FXML public void seekBack(){}
    @FXML public void seekForward(){}
    @FXML public void toggleFullscreen(){}

    private String formatTime(Duration duration) {
        int totalSeconds = (int) Math.floor(duration.toSeconds());
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        if (hours > 0) return String.format("%d:%02d:%02d", hours, minutes, seconds);
        else return String.format("%02d:%02d", minutes, seconds);
    }
}
