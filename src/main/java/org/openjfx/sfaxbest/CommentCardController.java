package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class CommentCardController {

    @FXML Circle avatarCircle;
    @FXML Label usernameLabel;
    @FXML Label dateLabel;
    @FXML Label contentLabel;

    public void setData(Image profilePic,String username,String date,String content){

        avatarCircle.setFill(new ImagePattern(profilePic));
        usernameLabel.setText(username);
        dateLabel.setText(date);
        contentLabel.setText(content);
        
    }
}
