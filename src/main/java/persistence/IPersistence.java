package persistence;

import javafx.collections.ObservableList;
import presentation.model.TourEntryModel;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface defining the persistence operations for the Tour Planner application.
 */
public interface IPersistence {

    /**
     * Establishes a connection to the database.
     *
     * @return true if the connection is successful, false otherwise.
     * @throws FileNotFoundException if the configuration file is not found.
     * @throws SQLException          if a database access error occurs.
     */
    boolean createConnection() throws FileNotFoundException, SQLException;

    void addTour(TourEntryModel tourItem);

    int getMaxId();

    void removeTour(TourModel tourModel);

    void dropTable();

    void createTableToursLogs();

    void createTable();

    void dropLogTable();

    void updateTourDetails(String tourDesc, String tourFrom, String tourTo, String tourTransport, String tourDistance, String tourEstTime, String tourInfo, String tourName, int tourRating) throws SQLException;

    ObservableList<TourModel> getAllTours();

    List<String> getAllToursNames();

    void saveTourLogs(TourLogCellModel tourLogs, String tourModelName);

    int getMaxIdLog();

    int getIdFromName(String name);

    boolean tourLogExists(String date);

    void updateTourLog(TourLogCellModel item, String tourModelName);

    void removeTourLog(TourLogCellModel tourLogCellModel);

    ObservableList<TourLogCellModel> getAllTourLogs(String tourName);

    boolean isConnected() throws SQLException;

    void removeLogsForTour(int tourId) throws SQLException;
}
