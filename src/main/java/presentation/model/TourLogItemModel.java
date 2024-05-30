package presentation.model;

import presentation.controller.TourLogCellController;
import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.function.Consumer;

public class TourLogItemModel extends javafx.scene.control.ListCell<TourLogCellModel>{

    private Consumer<TourLogCellModel> onDeleteProductCallBack;

    public TourLogItemModel(Consumer<TourLogCellModel> callback) {
        this.onDeleteProductCallBack = callback;
    }

    Logger log = LogManager.getLogger(TourLogItemModel.class);

    //Load TourLogItem.fxml to the ListView
    @Override
    public void updateItem(TourLogCellModel product, boolean empty)
    {
        super.updateItem(product, empty);
        if (empty || product == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TourLogItem.fxml"));
        try
        {
            log.info("Item is added to the ListView of Tour");
            fxmlLoader.load();
        }
        catch (IOException e) {
            log.error("Could not create Item TourItem.fxml");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        var controller = (TourLogCellController)fxmlLoader.getController();
        controller.setProduct(product);
        controller.addListenerForDeleteTour(this.onDeleteProductCallBack);
        setGraphic(controller.getProductItemBox());
    }
}
