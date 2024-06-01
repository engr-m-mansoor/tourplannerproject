package presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import presentation.model.TourListModel;
import presentation.model.TourSearchModel;
import service.IService;
import service.ServiceFactory;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TourSearchController implements Initializable {

    @FXML
    private TextField searchInput;
    @FXML
    private Button pdfButton;
    private final IService manager = ServiceFactory.GetManager();
    private final TourSearchModel tourSearchModel;
    private final TourListModel tourListModeL;

    public TourSearchController(TourSearchModel tourSearchModel, TourListModel tourListModeL) {
        this.tourSearchModel = tourSearchModel;
        this.tourListModeL = tourListModeL;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind text value
        this.searchInput.textProperty().bindBidirectional(this.tourSearchModel.getSearchProperty());
    }

    // Save PDF button
    public void savePDF(ActionEvent actionEvent) {
        manager.savePDF();
    }

    public void searchTours(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
        String searchQuery = searchInput.getText();
        List<String> allTours = manager.getAllTourNames();

        // Implement search logic
        List<String> searchResults = new ArrayList<>();
        for (String tour : allTours) {
            if (tour.toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(tour);
            }
        }

        // Display search results in a dialog
        if (!searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setHeaderText("Search Results for '" + searchQuery + "'");
            alert.setContentText(String.join("\n", searchResults));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setHeaderText("No results found for '" + searchQuery + "'");
            alert.showAndWait();
        }
    }
}