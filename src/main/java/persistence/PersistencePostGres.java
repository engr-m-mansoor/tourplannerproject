package persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import presentation.model.TourEntryModel;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistencePostGres implements IPersistence {

    private static final Logger log = LogManager.getLogger(PersistencePostGres.class);
    private Connection con;

    public PersistencePostGres() {
        try {
            createConnection();
        } catch (Exception e) {
            log.error("Error creating connection in constructor: " + e.getMessage());
        }
    }

//    @Override
//    public boolean createConnection() throws FileNotFoundException, SQLException {
//        String url = ConfigurationManager.GetConfigProperty("PostgresSqlConnectionString");
//        String user = ConfigurationManager.GetConfigProperty("user");
//        String password = ConfigurationManager.GetConfigProperty("pwd");
//        Properties props = new Properties();
//        props.setProperty("user", user);
//        props.setProperty("password", password);
//        con = DriverManager.getConnection(url, props);
//        return isConnected();
//    }

    @Override
    public boolean createConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/tour";
            String user = "root";
            String password = "abcd1234";
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                return true;
            } else {
                log.error("Failed to establish connection. Connection object is null.");
                return false;
            }
        } catch (SQLException e) {
            log.error("Error creating connection: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean isConnected() throws SQLException {
        if (this.con != null && !this.con.isClosed()) {
            String sql = "SELECT 1";
            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                return rs.next();
            } catch (SQLException e) {
                log.error("Error checking connection: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public int getMaxId() {
        String sql = "SELECT max(id) FROM Tours";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Could not get id from last Tour inserted!", e);
        }
        return 0;
    }

    @Override
    public int getMaxIdLog() {
        String sql = "SELECT max(id) FROM tours_logs";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Could not get id from last Tour Log inserted!", e);
        }
        return 0;
    }

    @Override
    public int getIdFromName(String name) {
        int id = 0;
        String sql = "SELECT id FROM Tours WHERE name = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   id = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            log.error("Could not get id from name of Tour", e);
        }
        return id;
    }
    @Override
    public void removeTour(TourModel tourModel) {
        String tourName = tourModel.getTourName();
        try {
            int tourId = getIdFromName(tourName);
            String sql = "DELETE FROM Tours WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, tourId);
                ps.executeUpdate();
            }
            log.info("Tour " + tourName + " deleted!");
            removeLogsForTour(tourId);
            log.info("Logs of " + tourName + " are deleted!");
        } catch (SQLException e) {
            log.error("Could not delete Tour", e);
        }
    }

    @Override
    public void dropTable() {
        try {
            String sql = "DROP TABLE IF EXISTS Tours";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.executeUpdate();
            }
            log.info("Table 'Tours' dropped successfully!");
        } catch (SQLException e) {
            log.error("Could not drop table 'Tours'", e);
        }
    }
    @Override
    public void createTableToursLogs()  {
        String sql = "CREATE TABLE IF NOT EXISTS tours_logs (" +
                "id SERIAL PRIMARY KEY," +
                "tour_id INTEGER NOT NULL," +
                "date TEXT," +
                "comment TEXT," +
                "difficulty TEXT," +
                "total_time TEXT," +
                "rating TEXT," +
                "FOREIGN KEY (tour_id) REFERENCES tours(id)" +
                ")";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (SQLException e) {
            log.error("Could not create table 'tours'", e);
        }
    }

    @Override
    public void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS tours (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "description TEXT," +
                    "from_location VARCHAR(255)," +
                    "to_location VARCHAR(255)," +
                    "transport_type VARCHAR(50)," +
                    "distance VARCHAR(50)," +
                    "estimated_time VARCHAR(50)," +
                    "route_info TEXT," +
                    "ratings INT" +
                    ")";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.executeUpdate();
            }
            log.info("Table 'tours' created successfully!");
        } catch (SQLException e) {
            log.error("Could not create table 'tours'", e);
        }
    }


    @Override
    public void dropLogTable() {
        try {
            String sql = "DROP TABLE IF EXISTS Tours_Logs";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.executeUpdate();
            }
            log.info("Table 'Tours' dropped successfully!");
        } catch (SQLException e) {
            log.error("Could not drop table 'Tours'", e);
        }
    }

    @Override
    public void updateTourDetails(String tourDesc, String tourFrom, String tourTo, String tourTransport, String tourDistance, String tourEstTime, String tourInfo, String tourName, int tourRating) throws SQLException {
        String sql = "UPDATE tours SET description = ?, from_location = ?, to_location = ?, transport_type = ?, distance = ?, estimated_time = ?, route_info = ?, ratings = ? WHERE name = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tourDesc);
            ps.setString(2, tourFrom);
            ps.setString(3, tourTo);
            ps.setString(4, tourTransport);
            ps.setString(5, tourDistance);
            ps.setString(6, tourEstTime);
            ps.setString(7, tourInfo);
            ps.setInt(8, tourRating);
            ps.setString(9, tourName);
            ps.executeUpdate();
            log.info("Tour " + tourName + " is updated!");
        } catch (SQLException e) {
            log.error("Could not update Tour", e);
        }
    }

    @Override
    public void updateTourLog(TourLogCellModel item, String tourModelName) {
        String sql = "UPDATE tours_logs SET comment = ?, difficulty = ?, total_time = ?, rating = ? WHERE date = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getComment());
            ps.setString(2, item.getDifficulty());
            ps.setString(3, item.getTotalTime());
            ps.setString(4, item.getRating());
            ps.setString(5, item.getDate());
            ps.executeUpdate();
            log.info("Tour Log is updated!");
        } catch (SQLException e) {
            log.error("Could not update Tour Log", e);
        }
    }

    @Override
    public void removeTourLog(TourLogCellModel tourLogCellModel) {
        String sql = "DELETE FROM tours_logs WHERE date = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tourLogCellModel.getDate());
            ps.executeUpdate();
            log.info("Tour Log is deleted!");
        } catch (SQLException e) {
            log.error("Could not delete Tour Log", e);
        }
    }

    @Override
    public void removeLogsForTour(int tourId) {
        String sql = "DELETE FROM tours_logs WHERE tour_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tourId);
            ps.executeUpdate();
            log.info("Logs from Tour are deleted!");
        } catch (SQLException e) {
            log.error("Could not delete Logs of this Tour", e);
        }
    }

    @Override
    public ObservableList<TourLogCellModel> getAllTourLogs(String tourName) {
        String sql = "SELECT * FROM tours_logs WHERE tour_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, getIdFromName(tourName));
            try (ResultSet rs = ps.executeQuery()) {
                ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();
                while (rs.next()) {
                    TourLogCellModel temp = new TourLogCellModel();
                    temp.setDate(rs.getString("date"));
                    temp.setComment(rs.getString("comment"));
                    temp.setDifficulty(rs.getString("difficulty"));
                    temp.setTotalTime(rs.getString("total_time"));
                    temp.setRating(rs.getString("rating"));
                    tourLogs.add(temp);
                }
                return tourLogs;
            }
        } catch (SQLException e) {
            log.error("Could not fetch all Tour Logs from DB", e);
        }
        return null;
    }

    @Override
    public ObservableList<TourModel> getAllTours() {
        String sql = "SELECT * FROM Tours";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ObservableList<TourModel> tours = FXCollections.observableArrayList();
            while (rs.next()) {
                TourModel temp = new TourModel();
                temp.setTourName(rs.getString("name"));
                temp.setTourDesc(rs.getString("description"));
                temp.setTourFrom(rs.getString("from_location"));
                temp.setTourTo(rs.getString("to_location"));
                temp.setTourTransport(rs.getString("transport_type"));
                temp.setTourDistance(rs.getString("distance"));
                temp.setTourEstTime(rs.getString("estimated_time"));
                temp.setTourInfo(rs.getString("route_info"));
                temp.setTourRating(rs.getInt("ratings"));
                tours.add(temp);
            }
            return tours;
        } catch (SQLException e) {
            log.error("Could not fetch all Tours from DB", e);
        }
        return null;
    }

    @Override
    public List<String> getAllToursNames() {
        String sql = "SELECT name FROM Tours";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<String> toursNames = new ArrayList<>();
            while (rs.next()) {
                toursNames.add(rs.getString("name"));
            }
            return toursNames;
        } catch (SQLException e) {
            log.error("Could not fetch all Tour Names from DB", e);
        }
        return null;
    }

    @Override
    public void saveTourLogs(TourLogCellModel item, String tourModelName) {
        if (tourLogExists(item.getDate())) {
            updateTourLog(item, tourModelName);
        } else {
            String sql = "INSERT INTO tours_logs (id, tour_id, date, comment, difficulty, total_time, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, getMaxIdLog() + 1);
                ps.setInt(2, getIdFromName(tourModelName));
                ps.setString(3, item.getDate());
                ps.setString(4, item.getComment());
                ps.setString(5, item.getDifficulty());
                ps.setString(6, item.getTotalTime());
                ps.setString(7, item.getRating());
                ps.executeUpdate();
                log.info("Tour Log inserted in DB");
            } catch (SQLException e) {
                log.error("Could not insert Tour Log in DB", e);
            }
        }
    }

    public boolean tourLogExists(String date) {
        String sql = "SELECT id FROM tours_logs WHERE date = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error("Could not check if Tour Log exists", e);
        }
        return false;
    }

    @Override
    public void addTour(TourEntryModel tourItem) {
        String sql = "INSERT INTO tours (name, description, from_location, to_location, transport_type, distance, estimated_time, route_info) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tourItem.getTourName());
            ps.setString(2, tourItem.getDescription());
            ps.setString(3, tourItem.getFromLocation());
            ps.setString(4, tourItem.getToLocation());
            ps.setString(5, tourItem.getTransportType());
            ps.setString(6, tourItem.getDistance());
            ps.setString(7, tourItem.getEstimatedTime());
            ps.setString(8, tourItem.getRouteInfo());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Tour " + tourItem.getTourName() + " is inserted in the DB");
            } else {
                log.error("Failed to insert Tour " + tourItem.getTourName() + " in DB");
            }
        } catch (SQLException e) {
            log.error("Could not insert Tour in DB", e);
        }
    }
}
