package org.openjfx.sfaxbest;

import Services.CommentService;
import Services.UserService;
import entities.Comment;
import entities.Film;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Optional;

public class CommentController {
    @FXML private TableView<Comment> commentTable;
    @FXML private TableColumn<Comment, Integer> colIdComment;
    @FXML private TableColumn<Comment, String> colTitleFilm;
    @FXML private TableColumn<Comment, Integer> colIDuser;
    @FXML private TableColumn<Comment, String> coluserName;
    @FXML private TableColumn<Comment, String> colComment;
    @FXML private TableColumn<Comment, Integer> colNumberRep;
    private CommentService  commentService= new CommentService();
    UserService userService= new UserService();
    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }

    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }
    @FXML
    public void goToDashbaord() throws IOException{
        App.setRoot("dashboardAdmin");
    }
    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }
    public void initialize() {
        loadComments();
    }
    public void loadComments() {
        colIdComment.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitleFilm.setCellValueFactory(new PropertyValueFactory<>("title"));
        colIDuser.setCellValueFactory(new PropertyValueFactory<>("userid"));
        coluserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("content"));
        colNumberRep.setCellValueFactory(new PropertyValueFactory<>("NbrSignals"));

        commentTable.setItems(FXCollections.observableArrayList(FXCollections.observableArrayList(
                commentService.findAll()
        )));
    }
    @FXML
    private void handleDelete() {
        Comment selectedComment = commentTable.getSelectionModel().getSelectedItem();

        if (selectedComment == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete comment");
        alert.setContentText("Are you sure you want to delete this comment?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            commentService.delete(selectedComment.getId());
            loadComments();
        }
    }

    @FXML
    private void dissmiss() {
        Comment selectedComment = commentTable.getSelectionModel().getSelectedItem();

        if (selectedComment == null) {
            return;
        }
        commentService.dismiss(selectedComment.getId());
        loadComments();
        //hethi besh tafi el reports mte3ou mathalan fama cas win 3bed juste ta3mel report hakika so tnajem trod el reports lel 0
    }
    @FXML
    private void handleban() {
        Comment selectedComment = commentTable.getSelectionModel().getSelectedItem();

        if (selectedComment == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ban?");
        alert.setContentText("Are you sure you want to ban" +  selectedComment.getUsername() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            userService.BanUser(selectedComment.getId());
            // a3mel method mta3 ban true ya3ni mbani false la

        }
        loadComments();
    }
}
