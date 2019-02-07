package display.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static int WIDTH = 1200, HEIGHT = 750;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ClientToServ.fxml"));
        Scene scene = new Scene(root/**, WIDTH,HEIGHT */);
        primaryStage.setTitle("Uno");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
