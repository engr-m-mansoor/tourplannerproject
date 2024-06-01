package presentation.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public void onDeleteTour(ActionEvent actionEvent) throws SQLException, IOException {
        // Show confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Delete Tour");
        confirmationAlert.setContentText("Are you sure you want to delete the tour '" + this.tourModel.getTourName() + "'?");

        // Wait for user response
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Delete the tour
                    TourEntryController.deleteTourName(this.tourModel.getTourName());
                    this.onDeleteProductConsumer.accept(this.tourModel);
                    manager.deleteTourItem(this.tourModel);
                    tourModel.deleteImg("src/main/resources/TourImages/" + this.tourModel.getTourName() + ".jpg");

                    // Show deletion confirmation
                    Alert deletionAlert = new Alert(Alert.AlertType.INFORMATION);
                    deletionAlert.setTitle("Tour Deleted");
                    deletionAlert.setHeaderText(null);
                    deletionAlert.setContentText("The tour '" + this.tourModel.getTourName() + "' has been deleted successfully.");
                    deletionAlert.showAndWait();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }}