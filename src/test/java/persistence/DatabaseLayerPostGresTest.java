package persistence;

import presentation.model.TourEntryModel;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseLayerPostGresTest {

    IPersistence db = new PersistencePostGres();
    TourEntryModel tourEntryModel = new TourEntryModel("Naran Tour", "ffefeaf", "Lahore", "ISB", "car", "54 km", "fw", "dad", 4);

    @Test
    void testCreateConnection() throws SQLException, FileNotFoundException {
        assertTrue(db.createConnection());
    }

    @Test
    void testAddTour() throws SQLException, FileNotFoundException {
        // Create connection
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();

        // Add tour
        db.addTour(tourEntryModel);

        // Check that the tour was added and has a valid ID
        int tourId = db.getIdFromName("Naran Tour");
        assertNotEquals(0, tourId);

        // Now remove the tour
        TourModel temp = new TourModel();
        temp.setTourName("Naran Tour");
        db.removeTour(temp);

        // Verify that the tour is removed
        int removedTourId = db.getIdFromName("Naran Tour");
        assertEquals(0, removedTourId);
        
        db.dropLogTable();
        db.dropTable();
    }


    @Test
    void testRemoveTour() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        assertNotEquals(0, db.getIdFromName("Naran Tour"));
        assertEquals(0, db.getIdFromName("Naran Tour2"));
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        assertEquals(0, db.getIdFromName("Naran Tour"));
        
        db.dropLogTable();
        db.dropTable();
    }

    @Test
    void testUpdateTourDetailsDesc() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("asdf", db.getAllTours().get(db.getMaxId()-1).getTourDesc());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsFrom() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("Berlin", db.getAllTours().get(db.getMaxId()-1).getTourFrom());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsTo() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("Vienna", db.getAllTours().get(db.getMaxId()-1).getTourTo());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsTransport() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("as", db.getAllTours().get(db.getMaxId()-1).getTourTransport());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsDistance() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("2134", db.getAllTours().get(db.getMaxId()-1).getTourDistance());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsEstTime() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("01:00", db.getAllTours().get(db.getMaxId()-1).getTourEstTime());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsInfo() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);
        assertEquals("asf", db.getAllTours().get(db.getMaxId()-1).getTourInfo());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testUpdateTourDetailsRating() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);

        // Update tour details
        db.updateTourDetails("asdf", "Berlin", "Vienna", "as", "2134", "01:00", "asf", "Naran Tour", 4);

        // Retrieve the updated tour from the database
        TourModel updatedTour = db.getAllTours().stream()
                .filter(t -> t.getTourName().equals(tourEntryModel.getTourName()))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedTour, "Updated tour not found in the database");
        assertEquals(4, updatedTour.getTourRating(), "Tour rating not updated correctly");

        // Remove the tour from the database
        TourModel tm = new TourModel();
        tm.setTourName(tourEntryModel.getTourName());
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }

    @Test
    void testSaveTourLogsDate() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        TourLogCellModel log = new TourLogCellModel();
        log.setDate("01/01/1970");
        db.saveTourLogs(log,"Naran Tour");
        assertEquals("01/01/1970",db.getAllTourLogs("Naran Tour").get(0).getDate());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testSaveTourLogsDifficulty() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        TourLogCellModel log = new TourLogCellModel();
        log.setDifficulty("hard");
        db.saveTourLogs(log,"Naran Tour");
        assertEquals("hard",db.getAllTourLogs("Naran Tour").get(0).getDifficulty());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testSaveTourLogsComment() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        TourLogCellModel log = new TourLogCellModel();
        log.setComment("asdvf");
        db.saveTourLogs(log,"Naran Tour");
        assertEquals("asdvf",db.getAllTourLogs("Naran Tour").get(0).getComment());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testSaveTourLogsRating() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        TourLogCellModel log = new TourLogCellModel();
        log.setRating("dsfn");
        db.saveTourLogs(log,"Naran Tour");
        assertEquals("dsfn",db.getAllTourLogs("Naran Tour").get(0).getRating());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testSaveTourLogsExists() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        TourLogCellModel log = new TourLogCellModel();
        log.setDate("01/01/1970");
        db.saveTourLogs(log,"Naran Tour");
        assertEquals(true,db.tourLogExists("01/01/1970"));
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
    @Test
    void testRemoveLogForTours() throws SQLException, FileNotFoundException {
        db.createConnection();
        db.createTable();
        db.createTableToursLogs();
        db.addTour(tourEntryModel);
        TourLogCellModel log = new TourLogCellModel();
        log.setDate("01/01/1970");
        db.saveTourLogs(log,"Naran Tour");
        log.setDate("02/01/1970");
        db.saveTourLogs(log,"Naran Tour");
        assertEquals(2,db.getAllTourLogs("Naran Tour").size());
        db.removeLogsForTour(db.getIdFromName("Naran Tour"));
        assertEquals(0,db.getAllTourLogs("Naran Tour").size());
        TourModel tm = new TourModel();
        tm.setTourName("Naran Tour");
        db.removeTour(tm);
        db.dropLogTable();
        db.dropTable();
    }
}