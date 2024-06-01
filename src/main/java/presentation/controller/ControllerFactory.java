package presentation.controller;

import presentation.model.*;

public class ControllerFactory {
    //create Models
    private final TourEntryModel tourEntryModel;
    private final TourLogModel tourLogModel;
    private final TourListModel tourListModel;
    private final TourDetailsModel tourDetailsModel;
    private final TourSearchModel tourSearchModel;


    public ControllerFactory(){
        //initialize Models
        this.tourEntryModel = new TourEntryModel();
        this.tourLogModel = new TourLogModel();
        this.tourListModel = new TourListModel();
        this.tourDetailsModel = new TourDetailsModel();
        this.tourSearchModel = new TourSearchModel();
    }

    public Object create(Class controllerClass) throws Exception {
        //for each controller, assign the Model
        if (controllerClass == TourEntryController.class) {
            return new TourEntryController(this.tourEntryModel);
        }else if(controllerClass == TourListController.class){
            return new TourListController(this.tourListModel, this.tourDetailsModel, this.tourLogModel);
        }else if(controllerClass == TourLogController.class){
            return new TourLogController(this.tourLogModel);
        }else if(controllerClass == TourDetailsController.class){
            return new TourDetailsController(this.tourDetailsModel);
        }else if(controllerClass == MainController.class){
            return new MainController(this.tourListModel);
        }else if(controllerClass == TourSearchController.class){
            return new TourSearchController(this.tourSearchModel, this.tourListModel);
        }else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }


}
