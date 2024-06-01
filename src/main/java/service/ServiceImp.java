package service;

import persistence.IPersistence;
import persistence.PersistenceFactory;
import presentation.Main;
import presentation.model.TourEntryModel;
import presentation.model.TourLogCellModel;
import presentation.model.TourModel;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ServiceImp implements IService {

    //DB Layer
    private IPersistence dataLayer;
    //MapQuest
    private MapQuestManager map = new MapQuestManager();
    Logger log = LogManager.getLogger(ServiceImp.class);

    public ServiceImp(){
        dataLayer = PersistenceFactory.getDatabase();
    }

    @Override
    public void createTourItem(TourEntryModel tourItem) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.addTour(tourItem);
    }

    @Override
    public void deleteTourItem(TourModel tourModel) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.removeTour(tourModel);
    }

    @Override
    public void deleteTourLogItem(TourLogCellModel tourLogCellModel) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.removeTourLog(tourLogCellModel);
    }

    @Override
    public boolean getMap(String tourName, String start, String finish) throws IOException {
        try {
            File outputFile = new File("src/main/resources/TourImages/" + tourName + ".jpg");
            BufferedImage img = MapQuestManager.requestRouteImage(start, finish);
            if(img != null){
                log.info("Img is saved!");
                ImageIO.write(img, "jpg", outputFile);
                return true;
            }else{
                log.error("Could not save img for this Tour!");
                return false;
            }
        } catch (Exception e) {
            log.error("Could not save img for this Tour!");
            return false;
        }
    }

    @Override
    public String getRouteDistance(String start, String end) {
        return map.getRouteDistance(start, end);
    }

    @Override
    public void updateTourDetails(String tourDesc, String tourFrom, String tourTo, String tourTransport, String tourDistance, String tourEstTime, String tourInfo, String tourName, int tourRating) throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        dataLayer.updateTourDetails(tourDesc, tourFrom, tourTo, tourTransport, tourDistance, tourEstTime, tourInfo, tourName, tourRating);
    }

    @Override
    public ObservableList<TourModel> getAllTour() throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        return dataLayer.getAllTours();
    }

    @Override
    public List<String> getAllTourNames() throws SQLException, FileNotFoundException {
        dataLayer.createConnection();
        return dataLayer.getAllToursNames();
    }

    @Override
    public void saveTourLogs(TourLogCellModel tourLogs, String tourModelName) {
        try {
            this.dataLayer.createConnection();
            this.dataLayer.saveTourLogs(tourLogs, tourModelName);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public ObservableList<TourLogCellModel> getAllTourLogs(String tourName) {
        try {
            this.dataLayer.createConnection();
            return this.dataLayer.getAllTourLogs(tourName);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void savePDF() {
        PDDocument document = new PDDocument();
        try {
            PDPage page = new PDPage();
            document.addPage(page);
            document.save("test.pdf");
            document.close();
            Main.getUserAgentStylesheet();
            log.info("PDF is saved");
        } catch (IOException e) {
            log.error("PDF could not be saved");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String searchtour(String searchQuery) {
        return "";
    }
}

