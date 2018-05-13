package network;
import client.AbstractClient;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


public class Client extends AbstractClient {
    private static Socket socket = null;
    private static ObjectInputStream inputStream = null;
    private static ObjectOutputStream outputStream = null;
    private static boolean isConnected = false;

    MessageListener listener;
    static String host;
    String clientName;
    boolean isCreate = false;
    static int port;
    public static Player player;

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public Client(String host, int port, String clientName) throws IOException {
        super(host, port);
        this.host = host;
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
            System.out.println("handleMessageFromClient");
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

    public static Player getPlayer() { return player; }

    @Override
    protected void handleMessageFromServer(Object msg) {
        String message = msg.toString();

        if(message.contains("#createRoom")) {
            String[] temp = message.split(",");
            ;
            String msgRoom = temp[1];

            listener.onLog(msgRoom.toString());
            return;
        }if (message.contains("#id")) {
            String[] temp =  message.split(",");
            String name = temp[1];
            int id = Integer.parseInt(temp[2]);

            if(name.equals(clientName)) {
                player = new Player(name, id);
            }
            return;
        }if (message.contains("##")) {
        String[] temp =  message.split(",");;
        String name = temp[1];

        if(name.equals(player.getName())) {
            port = Integer.parseInt(temp[2]);
            sendObjectToServer(player);
        }

        return;
    }if(message.contains("#joinToRoom")) {
            String[] temp = message.split(",");
            int ID1 = Integer.parseInt(temp[1]);
            int ID2 = Integer.parseInt(temp[2]);
            System.out.println(message);

            System.out.println("id1 =" + ID1 + " id2 =" + ID2 + player.getID() );

            if (ID1 == player.getID() || ID2 == player.getID()) {
                if (ID2 == 0) return;
                System.out.println("join");
                listener.changeTo("InUnoGame.fxml");
            }
            return;
        }

        listener.onMessage(msg.toString());
        System.out.println((msg.toString()));
    }

    public boolean isCreate(String roomName) {

        if (isCreate) return true;
        handleMessageFromClient("#createRoom," + String.format("%s,%s,%d", getClientName(), roomName,player.getID()));

        isCreate = true;
        return false;
    }

    private static void sendObjectToServer(Object obj){
        while (!isConnected) {
            try {
                socket = new Socket(host, port);
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                outputStream.writeObject(obj);
                socket.shutdownOutput();

            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
