package presentation.model;

import presentation.controller.TourItemController;
import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.function.Consumer;

public class TourItemModel extends javafx.scene.control.ListCell<TourModel>{

    private Consumer<TourModel> onDeleteProductCallBack;

    public TourItemModel(Consumer<TourModel> callback) {
        this.onDeleteProductCallBack = callback;
    }

    Logger log = LogManager.getLogger(TourItemModel.class);

    //Load TourItem.fxml to the ListView
    @Override
    public void updateItem(TourModel product, boolean empty)
    {
        super.updateItem(product, empty);
        if (empty || product == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TourItem.fxml"));
        try
        {
            fxmlLoader.load();
            log.info("Item is added to the ListView of Tour");
        }
        catch (IOException e) {
            log.error("Could not create Item TourItem.fxml");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        var controller = (TourItemController)fxmlLoader.getController();
        controller.setProduct(product);
        controller.addListenerForDeleteTour(this.onDeleteProductCallBack);
        setGraphic(controller.getProductItemBox());
    }
}
