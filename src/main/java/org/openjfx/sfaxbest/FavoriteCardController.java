package org.openjfx.sfaxbest;

import Services.FavoriteService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FavoriteCardController {
    FavoriteService favoriteService=new FavoriteService();

    Long idUser,idMultimedia;

    @FXML private ImageView posterImageView;

    public void setData(Long idUser,Long idMultimedia,Image posterImage) {
        this.idUser = idUser;
        this.idMultimedia = idMultimedia;

        posterImageView.setImage(posterImage);
    }
    @FXML
    private void removeFavorite() {
        favoriteService.delete(idUser,idMultimedia);
    }

}
