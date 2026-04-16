package org.openjfx.sfaxbest;

import Services.FavoriteService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FavoriteCardController {
    FavoriteService favoriteService=new FavoriteService();

    Long idUser,idMultimedia;
    String title;

    @FXML private ImageView posterImageView;

    public void setData(Long idUser,Long idMultimedia,String title,Image posterImage) {
        this.idUser = idUser;
        this.idMultimedia = idMultimedia;
        this.title = title;

        posterImageView.setImage(posterImage);
    }
    @FXML
    public void loadMovie(){
        MainViewController.instance.openMovieView(title);
    }
    @FXML
    private void removeFavorite() {
        favoriteService.delete(idUser,idMultimedia);
    }

}
