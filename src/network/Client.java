package network;
import client.AbstractClient;
import model.Player;
import model.Room;

import java.io.IOException;


public class Client extends AbstractClient {
    MessageListener listener;
    final public static int DEFAULT_PORT = 1000;
    String clientName;
    boolean isCreate = false;
    public static Player player;

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public Client(String host, int port, String clientName) throws IOException {
        super(host, port);
        this.clientName = clientName;
        openConnection();

        player = new Player(clientName);
    }

    public void sendMessage(String msg){
        handleMessageFromClient(msg);
    }

    public void handleMessageFromClient(String message) {
        try {
            sendToServer(message);
        } catch (IOException e) {
            System.out.println("Could not send message to server.  Terminating client.");
            System.exit(0);
        }
    }

    @Override
    protected void connectionEstablished() {
        try {
            sendToServer("#logintoServer," + clientName);
        } catch (IOException e) {
            System.out.println("Could not send message to server.  Terminating client.");
            System.exit(0);
        }
        super.connectionEstablished();
    }

    public String getClientName() {
        return clientName;
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        String message = msg.toString();

        if(message.contains("#createRoom")) {
            String[] temp =  message.split(",");;
            String msgRoom = temp[1];

            listener.onLog(msgRoom.toString());
            return;
        }

        listener.onMessage(msg.toString());
    }

    public boolean isCreate(String roomName) {

        if (isCreate) return true;
        handleMessageFromClient("#createRoom," + String.format("%s,%s", getClientName(), roomName));

        isCreate = true;
        return false;
    }

}
