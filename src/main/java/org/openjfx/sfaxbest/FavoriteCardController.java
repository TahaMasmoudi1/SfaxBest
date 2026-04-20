package org.openjfx.sfaxbest;

import Services.FavoriteService;
import entities.Multimedia;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FavoriteCardController {
    FavoriteService favoriteService=new FavoriteService();

    Long idUser,idMultimedia;
    String title;
    Multimedia  multimedia;

    @FXML private ImageView posterImageView;

    public void setData(Multimedia selectedMedia ,Long idUser,Long idMultimedia,String title,Image posterImage) {
        this.idUser = idUser;
        this.idMultimedia = idMultimedia;
        this.title = title;
        this.multimedia = selectedMedia;


        posterImageView.setImage(posterImage);
    }
    @FXML
    public void loadMovie(){
        if (multimedia.getMedia_type().equals("FILM")){
            MainViewController.instance.openMovieView(title);
        } else if (multimedia.getMedia_type().equals("SERIES")) {
            MainViewController.instance.openSeriesView(title);
        }
    }
    @FXML
    private void removeFavorite() {
        favoriteService.delete(idUser,idMultimedia);
    }

}
