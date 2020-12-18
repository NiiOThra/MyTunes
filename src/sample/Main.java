package sample;

import GUI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private final static String APP_TITLE = "MyTunes";
    private final static int APP_WIDTH = 900;
    private final static int APP_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.refreshUI();
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(new Scene(root, APP_WIDTH, APP_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
