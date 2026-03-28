package org.openjfx.sfaxbest;

import Services.CommentService;
import entities.Comment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class CommentController {
    @FXML private TableView<Comment> commentTable;
    @FXML private TableColumn<Comment, Integer> colIdComment;
    @FXML private TableColumn<Comment, String> colTitleFilm;
    @FXML private TableColumn<Comment, Integer> colIDuser;
    @FXML private TableColumn<Comment, String> coluserName;
    @FXML private TableColumn<Comment, String> colComment;
    @FXML private TableColumn<Comment, Integer> colNumberRep;
    private CommentService  commentService= new CommentService();
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
}
