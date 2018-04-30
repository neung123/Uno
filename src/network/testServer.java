package network;

import server.AbstractServer;
import server.ConnectionToClient;

import java.io.IOException;

public class testServer extends AbstractServer {
    final public static int DEFAULT_PORT = 11111;
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public testServer(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        client.getInfo("name");
        String ms = String.format("Received from %s: %s\n",client.toString(),msg.toString());
        this.sendToAllClients(ms);
        System.out.println(ms);
    }

    protected void serverStarted() {
        super.serverStarted();
        System.out.println("Start server");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
    }


    protected void serverStopped() {
        System.out.println("Server has stopped.");
    }

    public static void main(String[] args){
        int port = DEFAULT_PORT;

        testServer testServer = new testServer(port);

        try {
            testServer.listen();
        } catch (IOException e) {
            System.out.println("ERROR - Could not listen for clients");
        }


    }
}
