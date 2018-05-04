package network;

import server.AbstractServer;
import server.ConnectionToClient;

import java.io.IOException;

public class Server extends AbstractServer {


    MessageListener listener;

    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public Server(int port) {
        super(port);
        try {
            listen();
        } catch (IOException e) {
            System.out.println("ERROR - Could not listen for clients");
        }
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        String ms = String.format("%s: %s\n",client.getName(),msg.toString());
        this.sendToAllClients(ms);
    }

    protected void serverStarted() {
        super.serverStarted();
        listener.onMessage("Start server...");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        listener.onLog(client.toString() + "has joined server");
    }


    protected void serverStopped() {
        listener.onMessage("Server has stopped.");
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }
}
