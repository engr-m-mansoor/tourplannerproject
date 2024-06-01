package presentation.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.File;

public class TourDetailsModel {

    private StringProperty tourName;
    private StringProperty tourDesc;
    private StringProperty tourFrom;
    private StringProperty tourTo;
    private StringProperty tourTransport;
    private StringProperty tourDistance;
    private StringProperty tourEstTime;
    private StringProperty tourInfo;
    private StringProperty tourNameLabel;
    private IntegerProperty tourRating;
    private StringProperty editButton;
    private BooleanProperty editMode;
    private BooleanProperty workingMode;
    private ObjectProperty<javafx.scene.image.Image> imageProperty = new SimpleObjectProperty<>();

    private TourModel tourModel;

    public TourDetailsModel(){
        tourName = new SimpleStringProperty("");
        tourDesc = new SimpleStringProperty("");
        tourFrom = new SimpleStringProperty("");
        tourTo = new SimpleStringProperty("");
        tourTransport = new SimpleStringProperty("");
        tourDistance = new SimpleStringProperty("");
        tourEstTime = new SimpleStringProperty("");
        tourInfo = new SimpleStringProperty("");
        tourRating = new SimpleIntegerProperty(0);

        tourNameLabel = new SimpleStringProperty("Dummy");

        editButton = new SimpleStringProperty("Edit");
        editMode = new SimpleBooleanProperty(false);
        workingMode = new SimpleBooleanProperty(true);
    }

    public String getTourTransport() {
        return tourTransport.get();
    }

    public String getTourDistance() {
        return tourDistance.get();
    }

    public String getTourEstTime() {
        return tourEstTime.get();
    }

    public String getTourInfo() {
        return tourInfo.get();
    }

    public String getTourName(){
        return  tourName.get();
    }

    public StringProperty getTourNameProperty() {
        return tourName;
    }

    public String getTourDesc() {
        return tourDesc.get();
    }

    public StringProperty getTourDescProperty() {
        return tourDesc;
    }

    public String getTourFrom() {
        return tourFrom.get();
    }

    public StringProperty getTourFromProperty() {
        return tourFrom;
    }

    public void setTourDistance(String tourDistance) {
        this.tourDistance.set(tourDistance);
    }

    public String getTourTo() {
        return tourTo.get();
    }

    public StringProperty getTourToProperty() {
        return tourTo;
    }

    public boolean getEditMode() {
        return editMode.get();
    }

    public BooleanProperty getEditModeProperty() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode.set(editMode);
    }

    public BooleanProperty getWorkingModeProperty() {
        return workingMode;
    }

    public void setWorkMode(boolean workMode) {
        this.workingMode.set(workMode);
    }

    public StringProperty getEditButtonProperty() {
        return editButton;
    }

    public void setEditButton(String editButton) {
        this.editButton.set(editButton);
    }

    public void setTourDetailName(String tourName) {
        this.tourName.set(tourName);
    }

    public void setTourDetailDesc(String tourDesc) {
        this.tourDesc.set(tourDesc);
    }

    private void setTourDetailInfo(String tourInfo) {
        this.tourInfo.set(tourInfo);
    }

    private void setTourDetailRating(int tourRating) {
        this.tourRating.set(tourRating);
    }

    private void setTourDetailEstTime(String tourEstTime) {
        this.tourEstTime.set(tourEstTime);
    }

    private void setTourDetailDistance(String tourDistance) {
        this.tourDistance.set(tourDistance);
    }

    private void setTourDetailTransport(String tourTransport) {
        this.tourTransport.set(tourTransport);
    }

    public void setTourDetailTo(String tourTo) {
        this.tourTo.set(tourTo);
    }

    public void setTourDetailFrom(String tourFrom) {
        this.tourFrom.set(tourFrom);
    }

    public Property<Image> getImageProperty() {
        return imageProperty;
    }

    public void setTourDetailImg(Image image) {
        this.imageProperty.set(image);
    }

    public StringProperty getTourTransportProperty() {
        return tourTransport;
    }

    public StringProperty getTourDistanceProperty() {
        return tourDistance;
    }

    public StringProperty getTourEstTimeProperty() {
        return tourEstTime;
    }

    public StringProperty getTourInfoProperty() {
        return tourInfo;
    }

    public int getTourRating() {
        return tourRating.get();
    }

    public IntegerProperty getTourRatingProperty() {
        return tourRating;
    }

    //butoni save
    public void saveTourModel() {
        //Save tourModel from the TourModelDetails
        if(tourModel != null){
            this.tourModel.setTourName(getTourName());
            this.tourModel.setTourDesc(getTourDesc());
            this.tourModel.setTourFrom(getTourFrom());
            this.tourModel.setTourTo(getTourTo());
            this.tourModel.setTourDistance(getTourDistance());
            this.tourModel.setTourInfo(getTourInfo());
            this.tourModel.setTourEstTime(getTourEstTime());
            this.tourModel.setTourTransport(getTourTransport());
            this.tourModel.setTourRating(getTourRating());
        }
    }

    //If reset is needed, the values go back to how they were
    public void resetTourModel() {
        if(tourModel != null){
            setTourDetailName(tourModel.getTourName());
            setTourDetailDesc(tourModel.getTourDesc());
            setTourDetailFrom(tourModel.getTourFrom());
            setTourDetailTo(tourModel.getTourTo());
            setTourDetailDistance(tourModel.getTourDistance());
            setTourDetailInfo(tourModel.getTourInfo());
            setTourDetailEstTime(tourModel.getTourEstTime());
            setTourDetailTransport(tourModel.getTourTransport());
        }
    }

    public void setTourModel(TourModel currentTourModel) {
        //Set Tour Model from the passed TourModel, TourModel clicked from the Listview
        setTourDetailName(currentTourModel.getTourName());
        setTourDetailDesc(currentTourModel.getTourDesc());
        setTourDetailFrom(currentTourModel.getTourFrom());
        setTourDetailTo(currentTourModel.getTourTo());
        setTourDetailTransport(currentTourModel.getTourTransport());
        setTourDetailDistance(currentTourModel.getTourDistance());
        setTourDetailEstTime(currentTourModel.getTourEstTime());
        setTourDetailInfo(currentTourModel.getTourInfo());
        setTourDetailRating(currentTourModel.getTourRating());
        File file = new File("src/main/resources/TourImages/" + getTourName() + ".jpg");
        Image image = new Image(file.toURI().toString());
        setTourDetailImg(image);
        this.tourModel = currentTourModel;
    }

    public boolean getWorkMode() {
            return workingMode.get();
        }
    public BooleanProperty workingModeProperty() {
        return workingMode;
    }

}
