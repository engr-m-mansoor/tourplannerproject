package presentation.controller;

import service.ServiceFactory;
import service.IService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import presentation.model.TourModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Consumer;

public class TourItemController {
    private TourModel tourModel;
    private Consumer<TourModel> onDeleteProductConsumer;
    private IService manager = ServiceFactory.GetManager();

    @FXML
    public Label name;
    @FXML
    private Node box = new HBox();

    public Node getProductItemBox() {
        return box;
    }

    public void setProduct(TourModel TourModel) {
        this.tourModel = TourModel;
        this.name.textProperty().bindBidirectional(this.tourModel.getTourNameProperty());
    }

    public void addListenerForDeleteTour(Consumer<TourModel> listener) {
        this.onDeleteProductConsumer = listener;
    }

    //Deletes the Tour from the Listview and from the DB
    //Deletes IMG and TourLogs as well
    public void onDeleteTour(ActionEvent actionEvent) throws SQLException, IOException {
        TourEntryController.deleteTourName(this.tourModel.getTourName());
        this.onDeleteProductConsumer.accept(this.tourModel);
        manager.deleteTourItem(this.tourModel);
        tourModel.deleteImg("src/main/resources/TourImages/" + this.tourModel.getTourName() + ".jpg");
    }
}
