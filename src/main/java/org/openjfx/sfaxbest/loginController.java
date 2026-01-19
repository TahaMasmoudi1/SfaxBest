package org.openjfx.sfaxbest;

import javafx.fxml.FXML;

import java.io.IOException;

public class loginController {
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("signup");
    }

    @FXML
    private void login() throws IOException {
        //na3mlou verif 3ali da5lou el utilsateur lezemna data base besh najem ne5dem
        //ba3ed el verif na3mlou setroot lel dashborad
        //APP.setRoot("dashborad");
    }

}
