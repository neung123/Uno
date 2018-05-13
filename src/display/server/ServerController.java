package display.server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import network.MessageListener;
import network.Server;

public class ServerController implements MessageListener {

    @FXML
    TextField textField;
    @FXML
    Button button;
    @FXML
    TextArea textArea;
    @FXML
    Label massageLabel;

    Server server = null;

    public void initialize(){
        textArea.setEditable(false);
    }

    public void handleStart(){
        String num = textField.getText();
        int port;
        try{
            port = Integer.parseInt(num);
        }catch (NumberFormatException e){
            textField.setText("");
            return;
        }

        if(server == null){
            server = new Server(port);
            server.setListener(this);
        }
    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(
                () -> {
                    massageLabel.setText(message);
                }
        );
    }

    @Override
    public void onLog(String message) {
        textArea.appendText(message + "\n");
    }

    @Override
    public void changeTo(String fxml) {

    }
}
