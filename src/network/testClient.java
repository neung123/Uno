package network;

import client.AbstractClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testClient extends AbstractClient {
    final public static int DEFAULT_PORT = 11111;

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public testClient(String host, int port) throws IOException {
        super(host, port);
        openConnection();
    }

    public void reader(){
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                handleMessageFromClient(message);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
        }
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

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println(msg.toString());
    }

    public static void main(String[] args) throws IOException {
        String host = "";
        int port = 0;  //The port number

        try {
            host = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            host = "192.168.1.47";
        }
        testClient chat = new testClient(host, DEFAULT_PORT);
        chat.reader(); //Wait for console data
    }
}
