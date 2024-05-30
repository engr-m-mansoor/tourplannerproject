package presentation.controller;

import presentation.model.TourListModel;
import presentation.model.TourModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    private final TourListModel tourListModel;

    @FXML
    private TourEntryController tourEntryController;
    @FXML
    private TourListController tourListController;


    public MainController(TourListModel tourListModel) {
        this.tourListModel = tourListModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add tours to the TourList
        this.tourEntryController.addListener(
                p -> this.tourListModel.addTours(TourModel.from(p)));

    }
}
