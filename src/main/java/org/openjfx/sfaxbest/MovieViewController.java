package org.openjfx.sfaxbest;

import Services.CommentService;
import Services.FavoriteService;
import Services.FilmService;
import Services.RatingService;
import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieViewController {

    User user = MainViewController.instance.getCurrentUser();
    CommentService commentService=new CommentService();
    FavoriteService favoriteService=new FavoriteService();

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
                ratingService.add(user.getId(), currentFilm.getId(), (byte) currentRating);
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

    public Film currentFilm;
    private RatingService ratingService = new RatingService();


    public void loadMovie(String title){
        FilmService  filmService = new FilmService();
        List<Film> films = filmService.listAllWithCategories();
        Set<String> fullNames = new HashSet<>();
        for (Film film : films) {
            if (film.getTitle().equals(title)) {
                currentFilm = filmService.listFilmDetails(film.getId());

                Set<CastMember> castMembers = currentFilm.getVideoCasts().stream().map(VideoCast::getCastMember).collect(Collectors.toSet());
                for (CastMember castMember : castMembers) {
                    fullNames.add(castMember.getName() + " " + castMember.getLastName());
                }
                String cast = fullNames.stream().collect(Collectors.joining(" | "));
                String rate;
                try{
                    rate = Double.toString(ratingService.calculateRate(film.getId()));
                }catch (NullPointerException e){
                    rate = "N/A";
                }
                setData(new Image(getClass().getResource((film.getPathBanner())).toExternalForm()),film.getTitle(),rate,Integer.toString(film.getDurationSeconds()/60),cast, film.getDescription());
                loadComments();
                break;
            }
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
        commentService.add(user.getId(), currentFilm.getId(), comment);
    }
    @FXML VBox commentsContainer;
    private void loadComments(){
        try {
            List<Comment> comments = commentService.findAllByMultimedia(currentFilm.getId());
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
        favoriteService.add(user.getId(), currentFilm.getId());
    }


    @FXML ImageView bannerImage;
    @FXML Label titleLabel;
    @FXML Label ratingLabel;
    @FXML Label durationLabel;
    @FXML Label castLabel;
    @FXML Label descriptionLabel;


    @FXML
    private void setData(Image banner, String title, String rating, String duration, String cast, String description) {

        bannerImage.setImage(banner);
        titleLabel.setText(title);
        ratingLabel.setText("★ " + rating + "/5");
        durationLabel.setText(duration + " min");
        castLabel.setText("Cast members : " + cast);
        descriptionLabel.setText(description);

    }
    @FXML
    private void loadMedia(){
        MainViewController.instance.openMediaPlayerView(getClass().getResource(currentFilm.getPathVideo()).toExternalForm());
    }
}
