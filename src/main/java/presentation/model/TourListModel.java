package presentation.model;

import service.ConfigurationManager;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TourListModel {

    private ObservableList<TourModel> tours = FXCollections.observableArrayList();

    public TourListModel(){
    }

    public ObservableList<TourModel> getTours() {
        return tours;
    }

    public void setTours(ObservableList<TourModel> tours) {
        this.tours = tours;
    }

    public void addTours(TourModel tour) {
        this.tours.add(tour);
    }

    public void removeTour(TourModel product) {
        this.tours.remove(product);
    }

    public void exportData() {
        //Export All Tours and Tour Logs
        String[] header = {"logs", "name", "description", "from", "to", "transport_type", "estimated_time", "route_info", "rating", "date", "comment", "difficulty", "total_timing", "rating"};
        List<String[]> list = new ArrayList<>();
        list.add(header);
        Iterator<TourModel> allTours = getTours().iterator();
        while(allTours.hasNext()){
            TourModel temp = allTours.next();
            String[] temp_tour = {"0", temp.getTourName(), temp.getTourDesc(), temp.getTourFrom(), temp.getTourTo(), temp.getTourTransport(), temp.getTourEstTime(), temp.getTourInfo(), String.valueOf(temp.getTourRating()), "", "", "", "", ""};
            list.add(temp_tour);
            Iterator<TourModel> allLogs = temp.getTours().iterator();
            while(allLogs.hasNext()){
                TourLogCellModel temp_logs = allLogs.next();
                String[] temp_log = {"1", "", "", "", "", "", "", "", "", temp_logs.getDate(), temp_logs.getComment(), temp_logs.getDifficulty(), temp_logs.getTotalTime(), temp_logs.getRating()};
                list.add(temp_log);
            }
        }
        Logger log = LogManager.getLogger(TourListModel.class);
        try (CSVWriter writer = new CSVWriter(new FileWriter(ConfigurationManager.GetConfigProperty("CsvAccessStoragePath") + "export.csv"))) {
            writer.writeAll(list);
            exportStatus(true);
            log.info("Tours are exported in a CSV file");
        } catch (IOException e) {
            e.printStackTrace();
            exportStatus(false);
            log.error("Tours could not be exported.");
            log.error(e.getMessage());
        }
    }

    //Check if the Export is done or failed
    public static void exportStatus(boolean type){
        //Create Stage
        Stage newWindow = new Stage();
        newWindow.setTitle("New Scene");
        Label title;
        if(type){
            title = new Label("File is exported!");
        }else{
            title = new Label("Export not done!");
        }
        VBox container = new VBox(title);
        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);
        newWindow.setScene(new Scene(container));
        newWindow.show();
    }
}
