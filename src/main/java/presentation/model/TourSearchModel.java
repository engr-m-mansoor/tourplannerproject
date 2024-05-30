package presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourSearchModel {

    private StringProperty search;

    public TourSearchModel(){
        this.search = new SimpleStringProperty("");
    }

    public StringProperty getSearchProperty() {
        return search;
    }
}
