package presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourLogModel {

    private StringProperty tourLog;
    private ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();
    private TourModel tourModel;

    public TourLogModel(){
        tourLog = new SimpleStringProperty("");
    }

    public StringProperty getTourLogProperty() {
        return tourLog;
    }

    public ObservableList<TourLogCellModel> getTourLogs() {
        return tourLogs;
    }

    public String getTourModelName(){
        if (tourModel != null) {
            return tourModel.getTourName();
        }
        return "";
    }

    private void clearLogs(){
        tourLogs.clear();
    }

    public void setTourModel(TourModel tourModel) {
        if (tourModel != null) {
            //clear listview
            clearLogs();
            //save tourLogs from Listview to LogListView
            setLogModel(tourModel);
        }
    }

    //set ListView to LogListView
    private void setLogModel(TourModel tourModel) {
        if (tourModel != null) {
            tourModel.getTours().forEach(tourLog -> {
                tourLogs.add(tourLog);
            });
            this.tourModel = tourModel;




        }
    }

    //save ListView to be the Same as the modified LogListView
    public void saveTourModel() {
        if(this.tourModel != null){
            this.tourModel.setTourLogs(FXCollections.observableArrayList(getTourLogs()));
        }
    }

    public void removeTour(TourLogCellModel tourLog) {
        tourLogs.remove(tourLog);
        saveTourModel();
    }
}
