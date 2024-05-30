package presentation.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.PersistencePostGres;

import java.io.File;
import java.util.Iterator;

public class TourModel extends TourLogCellModel {
    private StringProperty tourName = new SimpleStringProperty("");
    private StringProperty tourDesc = new SimpleStringProperty("");
    private StringProperty tourFrom = new SimpleStringProperty("");
    private StringProperty tourTo = new SimpleStringProperty("");
    private StringProperty tourTransport = new SimpleStringProperty("");
    private StringProperty tourDistance = new SimpleStringProperty("");
    private StringProperty tourEstTime = new SimpleStringProperty("");
    private StringProperty tourInfo = new SimpleStringProperty("");
    private IntegerProperty tourRating = new SimpleIntegerProperty(0);

    private ObservableList<TourLogCellModel> tourLogs = FXCollections.observableArrayList();

    public static TourModel from(TourEntryModel source) {
        var newInstance = new TourModel();
        newInstance.tourName.set(source.getTourName());
        newInstance.tourDesc.set(source.getDescription());
        newInstance.tourFrom.set(source.getFromLocation());
        newInstance.tourTo.set(source.getToLocation());
        newInstance.tourTransport.set(source.getTransportType());
        newInstance.tourDistance.set(source.getDistance());
        newInstance.tourEstTime.set(source.getEstimatedTime());
        newInstance.tourInfo.set(source.getRouteInfo());
        newInstance.tourRating.set(source.getRating());
        return newInstance;
    }
    

    // Getters and setters for each property
    public String getTourName() {
        return tourName.get();
    }

    public void setTourName(String name) {
        this.tourName.set(name);
    }

    public StringProperty tourNameProperty() {
        return tourName;
    }

    public String getTourDesc() {
        return tourDesc.get();
    }

    public void setTourDesc(String tourDesc) {
        this.tourDesc.set(tourDesc);
    }

    public StringProperty tourDescProperty() {
        return tourDesc;
    }

    public String getTourFrom() {
        return tourFrom.get();
    }

    public void setTourFrom(String tourFrom) {
        this.tourFrom.set(tourFrom);
    }

    public StringProperty tourFromProperty() {
        return tourFrom;
    }

    public String getTourTo() {
        return tourTo.get();
    }

    public void setTourTo(String tourTo) {
        this.tourTo.set(tourTo);
    }

    public StringProperty tourToProperty() {
        return tourTo;
    }

    public String getTourTransport() {
        return tourTransport.get();
    }

    public void setTourTransport(String tourTransport) {
        this.tourTransport.set(tourTransport);
    }

    public StringProperty tourTransportProperty() {
        return tourTransport;
    }

    public String getTourDistance() {
        return tourDistance.get();
    }

    public void setTourDistance(String tourDistance) {
        this.tourDistance.set(tourDistance);
    }

    public StringProperty tourDistanceProperty() {
        return tourDistance;
    }

    public String getTourEstTime() {
        return tourEstTime.get();
    }

    public void setTourEstTime(String tourEstTime) {
        this.tourEstTime.set(tourEstTime);
    }

    public StringProperty tourEstTimeProperty() {
        return tourEstTime;
    }

    public String getTourInfo() {
        return tourInfo.get();
    }

    public void setTourInfo(String tourInfo) {
        this.tourInfo.set(tourInfo);
    }

    public StringProperty tourInfoProperty() {
        return tourInfo;
    }

    public int getTourRating() {
        return tourRating.get();
    }

    public void setTourRating(int tourRating) {
        this.tourRating.set(tourRating);
    }

    public IntegerProperty tourRatingProperty() {
        return tourRating;
    }

    public ObservableList<TourLogCellModel> getTourLogs() {
        return tourLogs;
    }

    public void setTourLogs(ObservableList<TourLogCellModel> tourLogs) {
        clearLogs();

        Iterator<TourLogCellModel> it = tourLogs.iterator();
        while (it.hasNext()) {
            TourLogCellModel value = it.next();
            this.tourLogs.add(value);
        }
    }

    protected void clearLogs() {
        this.tourLogs.clear();
    }

    // Delete image for this tour
    public void deleteImg(String img) {
        Logger log = LogManager.getLogger(TourModel.class);
        File myObj = new File(img);
        if (!myObj.exists() || myObj.delete()) {
            log.info(myObj.getName() + " is deleted");
        } else {
            log.error("Failed to delete the file.");
        }
    }

    public Property<String> getTourNameProperty() {
        return tourName;
    }

    public ObservableList<TourModel> getTours() {
        PersistencePostGres persistencePostGres = new PersistencePostGres();

       return persistencePostGres.getAllTours();
    }
}
