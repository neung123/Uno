package display.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ServerUI.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Server");
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

