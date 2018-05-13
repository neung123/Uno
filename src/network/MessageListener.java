package network;

import model.Room;

public interface MessageListener {
    public void onMessage(String message);
    public void onLog(String message);
}
