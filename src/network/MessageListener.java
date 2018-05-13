package network;

import model.Room;

public interface MessageListener {
    void onMessage(String message);
    void onLog(String message);
    void changeTo(String fxml);
}
