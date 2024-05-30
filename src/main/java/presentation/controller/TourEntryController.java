package presentation.controller;

import service.ServiceFactory;
import service.IService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import presentation.model.TourEntryModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TourEntryController implements Initializable {
    private TourEntryModel tourEntryModel;
    private Consumer<TourEntryModel> newTourListener;
    private static List<String> tourNames;
    private IService manager = ServiceFactory.GetManager();
    Logger log = LogManager.getLogger(TourEntryController.class);

    @FXML
    public Label toursLabel;
    @FXML
    public TextField inputTour;

    public TourEntryController(TourEntryModel tourEntryModel) {
        this.tourEntryModel = tourEntryModel;
        tourNames = new ArrayList<>();
    }

    public static void deleteTourName(String name){
        tourNames.remove(name);
    }

    //Adds a new tour to the ListView and creates the Tour in the DB
    public void addTour(ActionEvent actionEvent) throws SQLException, IOException {
        if(tourNames.contains(this.tourEntryModel.getTourName())){
            log.info("Cannot add this Tour. Tour already exists!");
        }else{
            this.newTourListener.accept(this.tourEntryModel);
            tourEntryModel.createTour(this.tourEntryModel);
            tourNames.add(this.tourEntryModel.getTourName());
        }
    }

    public void addListener(Consumer<TourEntryModel> listenToNewTour) {
        this.newTourListener = listenToNewTour;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get all tour names, so we don't create two tour with the same name
        try {
            tourNames = manager.getAllTourNames();
            if(tourNames == null){
                tourNames = new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.inputTour.textProperty().bindBidirectional(this.tourEntryModel.getTourNameProperty());
    }

}
