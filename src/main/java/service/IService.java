package service;

import presentation.model.TourEntryModel;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IService {

    //Functions for the Business Layer
    void createTourItem(TourEntryModel tourItem) throws SQLException, IOException;
    void deleteTourItem(TourModel tourModel) throws SQLException, FileNotFoundException;
    void deleteTourLogItem(TourLogCellModel tourLogCellModel) throws SQLException, FileNotFoundException;
    boolean getMap(String tourName, String start, String finish) throws IOException;
    String getRouteDistance(String start, String end);
    void updateTourDetails(String tourDesc, String tourFrom, String tourTo, String tourTransport, String tourDistance, String tourEstTime, String tourInfo, String tourName, int tourRating) throws SQLException, FileNotFoundException;
    ObservableList<TourModel> getAllTour() throws SQLException, FileNotFoundException;
    List<String> getAllTourNames() throws SQLException, FileNotFoundException;
    void saveTourLogs(TourLogCellModel tourLogs, String tourModelName);
    ObservableList<TourLogCellModel> getAllTourLogs(String tourName);
    void savePDF();
}
