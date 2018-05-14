package network;
import card.Card;
import client.AbstractClient;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class Client extends AbstractClient {
    private static Socket socket = null;
    private static ObjectOutputStream outputStream = null;
    private static boolean isConnected = false;

    MessageListener listener;
    CardListener cardListener;
    static String host;
    String clientName;
    boolean isCreate = false;
    static int port;
    public static Player player;
    public static int inRoom;
    private int ID;

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

    public void setCardListener(CardListener listener) {
        this.cardListener = listener;
    }
    public static Player getPlayer() { return player; }

    @Override
    protected void handleMessageFromServer(Object msg) {
        String message = msg.toString();
        System.out.println(message);

        if(message.contains("#createRoom")) {
            String[] temp = message.split(",");
            String msgRoom = temp[1];

            listener.onLog(msgRoom.toString());
            return;
        }if (message.contains("#id")) {
            String[] temp =  message.split(",");
            String name = temp[1];
            int id = Integer.parseInt(temp[2]);
            ID = id;

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
            int room = Integer.parseInt(temp[3]);

            if (ID1 == player.getID() || ID2 == player.getID()) {
                if (ID2 == 0) return;
                listener.changeTo("InUnoGame.fxml");

                inRoom = room;
            }
            return;
        }if(message.contains("#roomMid")){
            String[] temp = message.split(",");
            int ID1 = Integer.parseInt(temp[1]);
            int ID2 = Integer.parseInt(temp[2]);
            String[] defaultCard = temp[3].split(" ");
            int roomNum = Integer.parseInt(temp[4]);

            String color = defaultCard [0];
            String value = defaultCard [1];
            String card = String.format("/card_all/%s_%s.jpg",color,value);
            int mine;
            if (ID1 == player.getID() || ID2 == player.getID()) {
                cardListener.middleCard(card);

                if(ID1 == player.getID()) {
                    mine = ID1;
                }else {
                    mine = ID2;
                }
            }else return;

            /** "#roomSetMine,roomNum,this player's id,another's id" **/
            sendMessage(String.format("#roomSetMine,%d", mine));
         return;
        }if(message.contains("#roomSetMine")){
            String[] temp = message.split(",");
            int ID1 = Integer.parseInt(temp[1]);
            int anotherNum = Integer.parseInt(temp[2]);
            ArrayList<String> deck = new ArrayList<>();

            if (ID1 == player.getID()) {
            for(int i = 0; i < temp.length - 3; i++) {
                String[] defaultCard = temp[3 + i].split(" ");
                String color = defaultCard [0];
                String value = defaultCard [1];
                String card = String.format("/card_all/%s_%s.jpg",color,value);

                deck.add(card);
            }
                cardListener.showMine(deck);
                cardListener.showOther(anotherNum);
            }
            return;

        }if(message.contains("#setTurn")) {
            String[] temp = message.split(",");
            int ID1 = Integer.parseInt(temp[1]);
            boolean turn = Boolean.parseBoolean(temp[2]);


            if (ID1 == player.getID()) {
                player.setTurn(turn);
            }
            return;
        }if(message.contains("#removeCard")){
            String[] temp = message.split(",");
            int ID1 = Integer.parseInt(temp[1]);
            String[] newCard = temp[2].split(" ");
            String color = newCard[0];
            String value = newCard[1];

            Card card = new Card(color,value,0);
            System.out.println("card to remove" + card);

            player.removeCard(card);

            sendMessage(String.format("#roomSetMine,%d", ID1));
        }

        listener.onMessage(msg.toString());
    }

    public boolean isCreate(String roomName) {

        if (isCreate) return true;
        handleMessageFromClient("#createRoom," + String.format("%s,%s,%d", getClientName(), roomName,player.getID()));

        isCreate = true;
        return false;
    }

    public int getID() {
        return ID;
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

//    public Card createCard(String newCard){
//        String[] defaultCard = temp[3 + i].split(" ");
//        String color = defaultCard [0];
//        String value = defaultCard [1];
//    }

    public Card createCardUnder(String newCard){
        String[] temp = newCard.split("_");
        String color = null;
        System.out.println(temp[1]);
        String[] temp1 = temp[1].split("\\.");
        String value = temp1[0];
        switch (temp[0]){
            case "r":
                color = "red";
                break;
            case "g":
                color = "green";
                break;
            case "b":
                color = "blue";
                break;
            case "y":
                color = "yellow";
                break;
            case "w":
                color = "wild";
                break;
        }
        return new Card(color, value, 0);
    }


}
