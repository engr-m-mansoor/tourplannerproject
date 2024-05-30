package presentation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import service.IService;
import service.ServiceFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class TourEntryModel {

    private IService manager = ServiceFactory.GetManager();
    private StringProperty tourName;
    private StringProperty description;
    private StringProperty fromLocation;
    private StringProperty toLocation;
    private StringProperty transportType;
    private StringProperty distance;
    private StringProperty estimatedTime;
    private StringProperty routeInfo;
    private IntegerProperty rating;

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating.set(rating);
    }
    public TourEntryModel(String tourName, String description, String fromLocation, String toLocation, String transportType, String distance, String estimatedTime, String routeInfo, int rating) {
        this.tourName = new SimpleStringProperty(tourName);
        this.description = new SimpleStringProperty(description);
        this.fromLocation = new SimpleStringProperty(fromLocation);
        this.toLocation = new SimpleStringProperty(toLocation);
        this.transportType = new SimpleStringProperty(transportType);
        this.distance = new SimpleStringProperty(distance);
        this.estimatedTime = new SimpleStringProperty(estimatedTime);
        this.routeInfo = new SimpleStringProperty(routeInfo);
        this.rating = new SimpleIntegerProperty(rating);
    }
    public TourEntryModel() {
        tourName = new SimpleStringProperty("");
        description = new SimpleStringProperty("");
        fromLocation = new SimpleStringProperty("");
        toLocation = new SimpleStringProperty("");
        transportType = new SimpleStringProperty("");
        distance = new SimpleStringProperty("");
        estimatedTime = new SimpleStringProperty("");
        routeInfo = new SimpleStringProperty("");
        rating = new SimpleIntegerProperty(0);
    }

    public String getTourName() {
        return tourName.get();
    }

    public StringProperty getTourNameProperty() {
        return tourName;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFromLocation() {
        return fromLocation.get();
    }

    public StringProperty fromLocationProperty() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation.get();
    }

    public StringProperty toLocationProperty() {
        return toLocation;
    }

    public String getTransportType() {
        return transportType.get();
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getEstimatedTime() {
        return estimatedTime.get();
    }

    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public String getRouteInfo() {
        return routeInfo.get();
    }

    public StringProperty routeInfoProperty() {
        return routeInfo;
    }

    // When add is clicked, the new Tour will be added to the DB
    public boolean createTour() throws SQLException, IOException {
        // Create a new TourEntryModel object with the current values
        TourEntryModel newTour = new TourEntryModel();
        newTour.setTourName(tourName.get());
        newTour.setDescription(description.get());
        newTour.setFromLocation(fromLocation.get());
        newTour.setToLocation(toLocation.get());
        newTour.setTransportType(transportType.get());
        newTour.setDistance(distance.get());
        newTour.setEstimatedTime(estimatedTime.get());
        newTour.setRouteInfo(routeInfo.get());

        // Call the service to create the tour
        manager.createTourItem(newTour);

        Logger log = LogManager.getLogger(TourEntryModel.class);
        log.info("Tour " + getTourName() + " is added (to the DB as well)");

        return true;
    }
    public boolean createTour(TourEntryModel tourEntryModel) throws SQLException, IOException {
        manager.createTourItem(tourEntryModel);
        Logger log = LogManager.getLogger(TourEntryModel.class);
        log.info("Tour " + getTourName() + " is added (to the DB as well)");
        return true;
    }

    // Add setter methods for other properties
    public void setTourName(String tourName) {
        this.tourName.set(tourName);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation.set(fromLocation);
    }

    public void setToLocation(String toLocation) {
        this.toLocation.set(toLocation);
    }

    public void setTransportType(String transportType) {
        this.transportType.set(transportType);
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime.set(estimatedTime);
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo.set(routeInfo);
    }
}
