package presentation.controller;

import presentation.model.TourLogCellModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Consumer;

public class TourLogCellController {

    private TourLogCellModel tourLogCellModel;
    private Consumer<TourLogCellModel> onDeleteProductConsumer;


    @FXML
    private Label date;
    @FXML
    private Label comment;
    @FXML
    private Label difficulty;
    @FXML
    private Label totalTime;
    @FXML
    private Label rating;
    @FXML
    private TextField commentInput;
    @FXML
    private TextField difficultyInput;
    @FXML
    private TextField totalTimeInput;
    @FXML
    private TextField ratingInput;

    @FXML
    private Node box = new HBox();

    public Node getProductItemBox() {
        return box;
    }

    public void setProduct(TourLogCellModel tourLogCellModel) {
        this.tourLogCellModel = tourLogCellModel;

        //Bind text value
        this.date.textProperty().bindBidirectional(this.tourLogCellModel.dateProperty());
        this.comment.textProperty().bindBidirectional(this.tourLogCellModel.commentProperty());
        this.difficulty.textProperty().bindBidirectional(this.tourLogCellModel.difficultyProperty());
        this.totalTime.textProperty().bindBidirectional(this.tourLogCellModel.totalTimeProperty());
        this.rating.textProperty().bindBidirectional(this.tourLogCellModel.ratingProperty());

        //Bind visibility
        this.comment.visibleProperty().bind(this.tourLogCellModel.getWorkingModeProperty());
        this.difficulty.visibleProperty().bind(this.tourLogCellModel.getWorkingModeProperty());
        this.totalTime.visibleProperty().bind(this.tourLogCellModel.getWorkingModeProperty());
        this.rating.visibleProperty().bind(this.tourLogCellModel.getWorkingModeProperty());

        //Bind text value
        this.commentInput.textProperty().bindBidirectional(this.tourLogCellModel.commentProperty());
        this.difficultyInput.textProperty().bindBidirectional(this.tourLogCellModel.difficultyProperty());
        this.totalTimeInput.textProperty().bindBidirectional(this.tourLogCellModel.totalTimeProperty());
        this.ratingInput.textProperty().bindBidirectional(this.tourLogCellModel.ratingProperty());

        //Bind visibility
        this.commentInput.visibleProperty().bind(this.tourLogCellModel.getEditModeProperty());
        this.difficultyInput.visibleProperty().bind(this.tourLogCellModel.getEditModeProperty());
        this.totalTimeInput.visibleProperty().bind(this.tourLogCellModel.getEditModeProperty());
        this.ratingInput.visibleProperty().bind(this.tourLogCellModel.getEditModeProperty());
    }

    public void addListenerForDeleteTour(Consumer<TourLogCellModel> listener) {
        this.onDeleteProductConsumer = listener;
    }

    //Button delete
    public void onDeleteTourLog(ActionEvent actionEvent) throws SQLException, IOException {
        this.onDeleteProductConsumer.accept(this.tourLogCellModel);
        tourLogCellModel.deleteTourLog(this.tourLogCellModel);
    }

    //Button edit
    public void onEditTour(ActionEvent actionEvent) {
        if(this.tourLogCellModel.getEditMode() == true){
            this.tourLogCellModel.setEditMode(false);
            this.tourLogCellModel.setWorkMode(true);
            //this.tourLogCellModel.resetTourModel();
        }else{
            this.tourLogCellModel.setEditMode(true);
            this.tourLogCellModel.setWorkMode(false);
        }
    }
}
