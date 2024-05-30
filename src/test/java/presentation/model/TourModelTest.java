package presentation.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import persistence.IPersistence;
import persistence.PersistencePostGres;

import static org.junit.jupiter.api.Assertions.*;

class TourModelTest {

    IPersistence db = new PersistencePostGres();
    TourModel tourModel = new TourModel();

    @Test
    void testTourLogAddAndClear(){
        db.createTable();
        db.createTableToursLogs();

        ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();
        if(!tourLogs.isEmpty()){
        TourLogCellModel temp = new TourLogCellModel();
        tourLogs.add(temp);
        tourModel.setTourLogs(tourLogs);
        tourModel.clearLogs();
        ObservableList<TourLogCellModel> empty = FXCollections.observableArrayList();
        assertEquals(empty, tourModel.getTours());
    }db.dropLogTable();
        db.dropTable();}
}