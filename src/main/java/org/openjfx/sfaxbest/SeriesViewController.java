package org.openjfx.sfaxbest;

import Services.*;
import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeriesViewController {

   User user = MainViewController.instance.getCurrentUser();

    RatingService ratingService = new RatingService();
    SerieService serieService = new SerieService();
    CommentService commentService=new CommentService();
    FavoriteService favoriteService=new FavoriteService();

    public Serie currentSerie;

    @FXML
    public void initialize (){
        setInteractiveStars();
    }

    @FXML private HBox starRatingBox;
    private int currentRating = 0;

    private void setInteractiveStars() {

        for (int i = 0; i < starRatingBox.getChildren().size(); i++) {
            Label star = (Label) starRatingBox.getChildren().get(i);
            final int starValue = i + 1; // 1 to 5

            // Hover effect
            star.setOnMouseEntered(e -> updateStarsVisual(starValue));
            star.setOnMouseExited(e -> updateStarsVisual(currentRating));

            // Click to lock in rating
            star.setOnMouseClicked(e -> {
                currentRating = starValue;
                System.out.println("User locked in: " + currentRating + " stars");
                ratingService.add(user.getId(), currentSerie.getId(), (byte) currentRating);
            });
        }
    }

    private void updateStarsVisual(int highlightCount) {
        for (int i = 0; i < 5; i++) {
            Label star = (Label) starRatingBox.getChildren().get(i);
            if (i < highlightCount) {
                star.setText("★");
                star.setTextFill(Color.web("#FFD700")); // Gold
            } else {
                star.setText("☆");
                star.setTextFill(Color.web("#AAAAAA")); // Gris
            }
        }
    }

    public void loadSeries(String title){
        List<Serie> series = serieService.listAllWithCategories();
        Set<String> fullNames = new HashSet<>();
        for (Serie serie : series) {
            if (serie.getTitle().equals(title)) {
                currentSerie = serieService.listSerieDetails(serie.getId());

                Set<CastMember> castMembers = currentSerie.getVideoCasts().stream().map(VideoCast::getCastMember).collect(Collectors.toSet());
                for (CastMember castMember : castMembers) {
                    fullNames.add(castMember.getName() + " " + castMember.getLastName());
                }
                String cast = fullNames.stream().collect(Collectors.joining(" | "));
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(currentSerie.getId()));
                }catch (Exception e){
                    rate = "N/A";
                }
                List<Season> seasons = currentSerie.getSeasons();


                setData(new Image(getClass().getResource((serie.getPathBanner())).toExternalForm()),serie.getTitle(),rate,Integer.toString(serie.getDurationSeconds()/60),cast, serie.getDescription(),seasons);
                loadComments();
                break;
            }
        }
    }


    @FXML HBox episodesPosterRow;
    SeasonService seasonService = new SeasonService();
    private void loadEpisodes(Season season) {
        try {
            List<Episode> episodes = seasonService.findByIdWithEpisodes(season.getId()).getEpisodes();
            for (Episode episode : episodes) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("episode-poster-card.fxml"));
                Node cardNode = loader.load();

                EpisodePosterController cardController = loader.getController();
                String rate;
                try {
                    rate = Double.toString(ratingService.calculateRate(episode.getId()));
                } catch (NullPointerException e) {
                    rate = "N/A";
                }

                cardController.setData(episode,episode.getTitre(), "N/A", rate, new Image(getClass().getResource(episode.getThumbnailUrl()).toExternalForm()));

                episodesPosterRow.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load episodes");
        }
    }

    @FXML TextArea commentInput;
    @FXML
    private void addComment(){
        //TODO: rate limit comments
        String comment = commentInput.getText().trim();
        if (comment.isEmpty()){
            return;
        }
        commentService.add(user.getId(), currentSerie.getId(), comment);
    }
    @FXML VBox commentsContainer;
    private void loadComments(){
        try {
            List<Comment> comments = commentService.findAllByMultimedia(currentSerie.getId());
            for (Comment comment : comments) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("comment-card.fxml"));
                Node cardNode = loader.load();

                CommentCardController cardController = loader.getController();

                String avatarPath = getClass().getResource(comment.getUser().getAvatarUrl()).toExternalForm();

                if (avatarPath == null) {
                    avatarPath = getClass().getResource("Default_pfp.jpg").toExternalForm();
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
                Instant commentTime = comment.getCommentDate();
                String formattedCommentTime = formatter.format(commentTime);

                cardController.setData(new Image(avatarPath),comment.getUser().getUsername(), formattedCommentTime,comment.getContent(),comment.getId());

                commentsContainer.getChildren().add(cardNode);
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Could not load comments");
        }
    }
    @FXML
    public void addToFavorites(){
       favoriteService.add(user.getId(), currentSerie.getId());
    }

    @FXML ImageView bannerImage;
    @FXML ComboBox seasonFilter;
    @FXML Label titleLabel;
    @FXML Label ratingLabel;
    @FXML Label durationLabel;
    @FXML Label castLabel;
    @FXML Label descriptionLabel;


    @FXML
    private void setData(Image banner, String title, String rating, String duration, String cast, String description,List<Season> seasons) {

        bannerImage.setImage(banner);
        titleLabel.setText(title);
        ratingLabel.setText("★ " + rating + "/5");
        durationLabel.setText(duration + " min");
        castLabel.setText("Cast members : " + cast);
        descriptionLabel.setText(description);
        seasonFilter.getItems().addAll(seasons);
        seasonFilter.setConverter(new StringConverter<Season>() {
            @Override
            public String toString(Season season) {
                if (season == null) return "";
                return "Season " + season.getNSeason();
            }
            @Override
            public Season fromString(String string) {
                return null;
            }
        });
        if (!seasons.isEmpty()) {
            seasonFilter.getSelectionModel().selectFirst();
        }
        seasonFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldSeason, newSeason) -> {
            if (newSeason != null) {
                loadEpisodes((Season) newSeason);
            }
        });


    }

}
