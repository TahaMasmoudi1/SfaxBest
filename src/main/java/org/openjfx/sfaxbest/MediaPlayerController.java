package org.openjfx.sfaxbest;

import entities.User;
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


    private MediaPlayer mediaPlayer;
    private boolean isPlaying = true;
    @FXML
    private StackPane playerRoot;
    @FXML
    Label currentTimeLabel;
    @FXML
    Slider timeSlider;
    @FXML
    Slider volumeSlider;
    @FXML
    Button playPauseBtn;
    @FXML
    MediaView mediaView;
    @FXML
    Label totalTimeLabel;


    public void initialize() {
        String pathVideo = getClass().getResource("/videos/gladiator_trailer.mp4").toExternalForm();
        loadVideo(pathVideo);
    }

    public void loadVideo(String pathVideo) {
        Media media = new Media(pathVideo);
        mediaView.fitWidthProperty().bind(playerRoot.widthProperty());
        mediaView.fitHeightProperty().bind(playerRoot.heightProperty());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setVolume(volumeSlider.getValue()); // Set initial volume
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mediaPlayer.setVolume(newVal.doubleValue());
        });
        mediaPlayer.setOnReady(() -> {

            Duration totalDuration = mediaPlayer.getTotalDuration();

            timeSlider.setMax(totalDuration.toSeconds());

            totalTimeLabel.setText(
                    formatTime(totalDuration)
            );
        });


// Update slider while playing
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                    if (!timeSlider.isValueChanging()) {
                        timeSlider.setValue(newTime.toSeconds());}
                    currentTimeLabel.setText(
                            formatTime(newTime)
                    );
                }
        );
        timeSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                    if (!isChanging) {
                        mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                    }
                }
        );
        timeSlider.setOnMousePressed(e -> mediaPlayer.seek(Duration.seconds(timeSlider.getValue())
                )
        );

        timeSlider.setOnMouseDragged(e ->
                mediaPlayer.seek(
                        Duration.seconds(timeSlider.getValue())));
        mediaView.setMediaPlayer(mediaPlayer);
    }


    @FXML
    public void playNextEpisode() {
    }

    @FXML
    public void cancelBingeWatch() {
    }

    @FXML
    public void togglePlayPause() {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseBtn.setText("▶");
        } else {
            mediaPlayer.play();
            isPlaying = true;
            playPauseBtn.setText("⏸");
        }
    }

    @FXML
    public void seekBack() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentTime.subtract(Duration.seconds(10)));
        }
    }

    @FXML
    public void seekForward() {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentTime.add(Duration.seconds(10)));
        }
    }

    @FXML
    public void toggleFullscreen() {
    }

    private String formatTime(Duration duration) {
        int totalSeconds = (int) Math.floor(duration.toSeconds());
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        if (hours > 0) return String.format("%d:%02d:%02d", hours, minutes, seconds);
        else return String.format("%02d:%02d", minutes, seconds);
    }
}
