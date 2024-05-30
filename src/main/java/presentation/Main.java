package presentation;

import persistence.IPersistence;
import persistence.PersistencePostGres;
import presentation.controller.ControllerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    static ControllerFactory factory = new ControllerFactory();
    static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = getFxmlLoader(factory);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("StringConcatenation.css")));
        primaryStage.setTitle("Tourplanner");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.show();
        this.scene = scene;
    }

    private FXMLLoader getFxmlLoader(ControllerFactory factory) {
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        Main.class.getResource("main.fxml"),
                        null,
                        new JavaFXBuilderFactory(),
                        controller -> {
                            try {
                                return factory.create(controller);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("returning null");
                            return null;
                        });
        return fxmlLoader;
    }

    public static WritableImage getImg() {
        WritableImage img = new WritableImage(125, 125);
        scene.snapshot(img);
        try {
            File myObj = new File("test.png");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                ImageIO.write((RenderedImage) img, "png", new File("what"));

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        PersistencePostGres persistencePostGres = new PersistencePostGres();
        persistencePostGres.createTable();
        persistencePostGres.createTableToursLogs();
        launch(args);

    }
}