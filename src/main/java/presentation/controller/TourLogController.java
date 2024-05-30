package presentation.controller;

import service.ServiceFactory;
import service.IService;
import presentation.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.ResourceBundle;

public class TourLogController implements Initializable {

    private final TourLogModel tourLogModel;
    @FXML
    private Label logLabel = new Label();
    @FXML
    public ListView<TourLogCellModel> listView = new ListView<>();
    private IService manager = ServiceFactory.GetManager();

    public TourLogController(TourLogModel tourLogModel) {
        this.tourLogModel = tourLogModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Bind text value
        this.logLabel.textProperty().bindBidirectional(this.tourLogModel.getTourLogProperty());

        //Set up Listview of Logs
        this.listView.setItems(this.tourLogModel.getTourLogs());
        this.listView.setCellFactory(
                ToursListView -> new TourLogItemModel(p -> this.deleteProduct(p)));
    }

    private void deleteProduct(TourLogCellModel model) {
        this.tourLogModel.removeTour(model);
    }


    //Add log button clicked
    public void addLog(ActionEvent actionEvent) {
        TourLogCellModel t = new TourLogCellModel();
        LocalDateTime now = LocalDateTime.now();
        t.setDate(String.valueOf(now));
        listView.getItems().add(t);
    }

    //Save log button clicked
    //Saves the new logs and changed logs in DB
    public void saveLog(ActionEvent actionEvent){
        Iterator<TourLogCellModel> item = this.tourLogModel.getTourLogs().iterator();
        while (item.hasNext()) {
            manager.saveTourLogs(item.next(), this.tourLogModel.getTourModelName());
        }
        this.tourLogModel.saveTourModel();

    }
}
