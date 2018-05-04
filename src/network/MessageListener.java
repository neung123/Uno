package network;

public interface MessageListener {
    public void onMessage(String message);
    public void onLog(String message);
}
