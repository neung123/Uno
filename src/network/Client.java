package network;
import client.AbstractClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client extends AbstractClient {
    MessageListener listener;
    final public static int DEFAULT_PORT = 1;
    String clientName;

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
    }

    public void sendMessage(String msg){
        handleMessageFromClient(msg);
    }

    public void handleMessageFromClient(String message) {
        try {
            sendToServer(message);
        } catch (IOException e) {
            display("Could not send message to server.  Terminating client.");
            System.exit(0);
        }
    }

    @Override
    protected void connectionEstablished() {
        super.connectionEstablished();
        System.out.println("Connected");
    }

    public void display(String message) {
        System.out.println("> " + message);
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println(msg);
        listener.onMessage(msg.toString());
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

}
