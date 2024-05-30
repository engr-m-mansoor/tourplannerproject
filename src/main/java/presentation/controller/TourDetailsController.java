package presentation.controller;

import service.ServiceFactory;
import service.ConfigurationManager;
import service.IService;
import presentation.model.TourDetailsModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Rating;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class TourDetailsController implements Initializable {

    private final TourDetailsModel tourDetailsModel;

    @FXML
    private ImageView imageView;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;

    @FXML
    private TextField tourName;
    @FXML
    private TextField tourDesc;
    @FXML
    private TextField tourFrom;
    @FXML
    private TextField tourTo;
    @FXML
    private TextField tourTransport;
    @FXML
    private TextField tourEstTime;
    @FXML
    private TextField tourInfo;
    @FXML
    private Label tourNameLabel;
    @FXML
    private Label tourDescLabel;
    @FXML
    private Label tourFromLabel;
    @FXML
    private Label tourToLabel;
    @FXML
    private Label tourTransportLabel;
    @FXML
    private Label tourDistanceLabel;
    @FXML
    private Label tourEstTimeLabel;
    @FXML
    private Label tourInfoLabel;
    @FXML
    private Rating tourRating;

    private IService manager = ServiceFactory.GetManager();

    public TourDetailsController(TourDetailsModel tourDetailsModel) {
        this.tourDetailsModel = tourDetailsModel;
    }

    Logger log = LogManager.getLogger(TourDetailsController.class);

    public void editMode(ActionEvent actionEvent) {
        //Change to edit mode when the button is clicked
        if(this.tourDetailsModel.getEditMode() == true){
            this.tourDetailsModel.setEditMode(false);
            this.tourDetailsModel.setEditButton("Edit");
            this.tourDetailsModel.setWorkMode(true);
            this.tourDetailsModel.resetTourModel();
        }else{
            this.tourDetailsModel.setEditMode(true);
            this.tourDetailsModel.setEditButton("Cancel");
            this.tourDetailsModel.setWorkMode(false);
        }
        log.info("State of Tour Details is flipped");
    }

    public void saveTour(ActionEvent actionEvent) throws IOException, SQLException {
        //Save tour when the save button is clicked
        //with validation
        if(!inputAreValid()){
            log.info("Input from Fields is not valid");
            inputNotValidBox(true);
        }else{
            log.info("Update Details fron Tour");
            if(tourDetailsModel.getTourName() != null && !tourDetailsModel.getTourName().equals("") ){
                //Save tour
                this.tourDetailsModel.saveTourModel();
                if(this.tourDetailsModel.getTourTo() == null || this.tourDetailsModel.getTourFrom() == null) {
                    //Update Tour in DB
                    //Revert to Working mode
                    manager.updateTourDetails(this.tourDetailsModel.getTourDesc(), this.tourDetailsModel.getTourFrom(), this.tourDetailsModel.getTourTo(), this.tourDetailsModel.getTourTransport(), this.tourDetailsModel.getTourDistance(), this.tourDetailsModel.getTourEstTime(), this.tourDetailsModel.getTourInfo(), this.tourDetailsModel.getTourName(), this.tourDetailsModel.getTourRating());
                    this.tourDetailsModel.setEditMode(false);
                    this.tourDetailsModel.setEditButton("Edit");
                    this.tourDetailsModel.setWorkMode(true);
                }else{
                    if(manager.getMap(this.tourDetailsModel.getTourName(), this.tourDetailsModel.getTourFrom(), this.tourDetailsModel.getTourTo())){
                        //Save Map for Tour
                        String path = ConfigurationManager.GetConfigProperty("FileAccessStoragePath");
                        File file = new File(path + this.tourDetailsModel.getTourName() + ".jpg");
                        Image image = new Image(file.toURI().toString());
                        //Get distance from API
                        String distance = manager.getRouteDistance(this.tourDetailsModel.getTourFrom(), this.tourDetailsModel.getTourTo());
                        this.tourDetailsModel.setTourDistance(distance + " km");
                        this.tourDetailsModel.setTourDetailImg(image);
                        this.tourDetailsModel.setEditMode(false);
                        this.tourDetailsModel.setEditButton("Edit");
                        this.tourDetailsModel.setWorkMode(true);
                        //Update Tour in DB
                        manager.updateTourDetails(this.tourDetailsModel.getTourDesc(), this.tourDetailsModel.getTourFrom(), this.tourDetailsModel.getTourTo(), this.tourDetailsModel.getTourTransport(), this.tourDetailsModel.getTourDistance(), this.tourDetailsModel.getTourEstTime(), this.tourDetailsModel.getTourInfo(), this.tourDetailsModel.getTourName(), this.tourDetailsModel.getTourRating());
                    }else{
                        log.info("Input from Fields is not valid");
                        //Show new Screen, input was not valid
                        TourDetailsController.inputNotValidBox(false);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Binding text value
        this.tourName.textProperty().bindBidirectional(this.tourDetailsModel.getTourNameProperty());
        this.tourDesc.textProperty().bindBidirectional(this.tourDetailsModel.getTourDescProperty());
        this.tourFrom.textProperty().bindBidirectional(this.tourDetailsModel.getTourFromProperty());
        this.tourTo.textProperty().bindBidirectional(this.tourDetailsModel.getTourToProperty());
        this.tourTransport.textProperty().bindBidirectional(this.tourDetailsModel.getTourTransportProperty());
        this.tourEstTime.textProperty().bindBidirectional(this.tourDetailsModel.getTourEstTimeProperty());
        this.tourInfo.textProperty().bindBidirectional(this.tourDetailsModel.getTourInfoProperty());
        this.tourRating.ratingProperty().bindBidirectional(this.tourDetailsModel.getTourRatingProperty());

        //Binding visibility
        this.tourName.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());
        this.tourDesc.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());
        this.tourFrom.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());
        this.tourTo.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());
        this.tourTransport.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());
        this.tourEstTime.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());
        this.tourInfo.visibleProperty().bind(this.tourDetailsModel.getEditModeProperty());

        //Binding text value
        this.tourNameLabel.textProperty().bind(this.tourDetailsModel.getTourNameProperty());
        this.tourDescLabel.textProperty().bind(this.tourDetailsModel.getTourDescProperty());
        this.tourFromLabel.textProperty().bind(this.tourDetailsModel.getTourFromProperty());
        this.tourToLabel.textProperty().bind(this.tourDetailsModel.getTourToProperty());
        this.tourTransportLabel.textProperty().bindBidirectional(this.tourDetailsModel.getTourTransportProperty());
        this.tourDistanceLabel.textProperty().bindBidirectional(this.tourDetailsModel.getTourDistanceProperty());
        this.tourEstTimeLabel.textProperty().bindBidirectional(this.tourDetailsModel.getTourEstTimeProperty());
        this.tourInfoLabel.textProperty().bindBidirectional(this.tourDetailsModel.getTourInfoProperty());

        //Binding visibility
        this.tourNameLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());
        this.tourDescLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());
        this.tourFromLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());
        this.tourToLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());
        this.tourTransportLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());
        this.tourEstTimeLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());
        this.tourInfoLabel.visibleProperty().bind(this.tourDetailsModel.getWorkingModeProperty());

        //Binding image for each tour
        this.editButton.textProperty().bindBidirectional(this.tourDetailsModel.getEditButtonProperty());
        this.imageView.imageProperty().bindBidirectional(this.tourDetailsModel.getImageProperty());

    }

    //Show new scene depending on if the input is valid
    //If true  -> Inputed value not req format
    //If false -> Locations do not exist
    public static void inputNotValidBox(boolean type){
        Stage newWindow = new Stage();
        newWindow.setTitle("New Scene");
        Label title;
        if(type){
            title = new Label("Please make sure the inputed value are the required format!");
        }else{
            title = new Label("Please make sure the inputed locations exist!");
        }
        VBox container = new VBox(title);
        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);
        newWindow.setScene(new Scene(container));
        newWindow.show();
    }

    //Makes checks if the inputs are valid
    public boolean inputAreValid(){
        String time = this.tourDetailsModel.getTourEstTime();
        try{
            if(this.tourDetailsModel.getTourEstTime() != null){
                LocalTime.parse(time);
            }
            return true;
        }
        catch (DateTimeParseException | NullPointerException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
