package network;

import model.Player;
import model.Room;
import server.AbstractServer;
import server.ConnectionToClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static network.Client.*;

public class Server extends AbstractServer {
    public static int ROOM_NUMBER = 1000;
    private static boolean isConnected = false;
    public static int ID = 1;
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    private static int DEFAULT_PORT = 1000;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private static LocalTime time = LocalTime.now();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
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
        String message = msg.toString();
        System.out.println(message);
        if (message.contains("#logintoServer")) {
            handleLogin(message, client);
            return;
        }
        if (message.contains("#createRoom")) {
            handleCreateRoom(message);
            return;
        }
        if (message.contains("#joinRoom")) {
            handleJoin(message);
            return;
        }
        if(message.contains("#setUpRoom")){
            handleSetup(message);
            return;
        }if(message.contains("#roomSetMine")){
            handleSetMine(message);
            return;
        }if(message.contains("#play")){
            handlePlay(message);
        }

        String ms = String.format("%s(%s): %s\n",client.getInfo("name"),time.format(dtf),msg.toString());
        this.sendToAllClients(ms);
    }

    protected void serverStarted() {
        super.serverStarted();
        listener.onMessage("Start server...");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
    }

    protected void serverStopped() {
        listener.onMessage("Server has stopped.");
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    private void handleLogin(String message,ConnectionToClient client){
        String[] temp =  message.split(",");
        String name = temp[1];

        client.setInfo("name",name);
        client.setInfo("port",DEFAULT_PORT);
        client.setInfo("id",getID());

        String send = client.getInfo("name") + " has joined server";
        listener.onLog(send);
        this.sendToAllClients(String.format("------- %s ------\n",send));


        String setID = String.format("#id,%s,%s",client.getInfo("name"),client.getInfo("id"));
        this.sendToAllClients(setID);

        String setPort = String.format("##,%s,%s",client.getInfo("name"),client.getInfo("port"));
        this.sendToAllClients(setPort);
        getObjectFromClient();
    }

    private void handleCreateRoom(String message){

        String[] temp =  message.split(",");
        String player = temp[1];
        String roomName = temp[2];
        int ID = Integer.parseInt(temp[3]);

        Room room = new Room(roomName);

        for(Player p : players){
            if(p.getID() == ID) room.addPlayer(p);
        }
        rooms.add(room);

        this.sendToAllClients(String.format("#createRoom,%-15d %-30s room's name: %-30s\n",room.getRoomNumber(),player,room.getRoomName()));
    }

    private void handleJoin(String message){
        String[] temp =  message.split(",");
        int roomNum = Integer.parseInt(temp[1]);
        int ID = Integer.parseInt(temp[2]);

        int player1;
        int player2;

        for(Room room: rooms){
            if(room.getRoomNumber() == roomNum){
                for(Player p : players){
                    if(p.getID() == ID) {
                        room.addPlayer(p);
                        player1 = room.getPlayer1().getID();
                        player2 = room.getPlayer2().getID();
                        room.start();

                        sendToAllClients(String.format("#joinToRoom,%d,%d,%d",player1,player2,room.getRoomNumber()));
                    }

                }
                return;
            }
        }
    }

    private void handleSetup(String message) {
        String[] temp = message.split(",");
        int roomNum = Integer.parseInt(temp[1]);


        int player1;
        int player2;

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNum) {
                player1 = room.getPlayer1().getID();
                player2 = room.getPlayer2().getID();
                String card = room.getCurrentCardString();

                sendToAllClients(String.format("#roomMid,%d,%d,%s,%d", player1, player2, card, roomNum));
            }

        }
        return;
    }

    private void handleSetMine(String message){
        String[] temp = message.split(",");
        int mine = Integer.parseInt(temp[1]);

        ArrayList<String> tempCards = new ArrayList<>();
        int another = 0;

        for (Room room : rooms) {
            if(room.getPlayer1().getID() == mine){
                tempCards = room.getPlayer1().getAllCardsString();
                another = room.getPlayer2().getTotalCards();
            }else if(room.getPlayer2().getID() == mine){
                tempCards = room.getPlayer2().getAllCardsString();
                another = room.getPlayer1().getTotalCards();
            }else return;

                String allCards = "";
                for(int i = 0; i < tempCards.size(); i++){
                   if(i == 0) allCards += tempCards.get(i);
                   else allCards += String.format(",%s",tempCards.get(i));
                }

                sendToAllClients(String.format("#roomSetMine,%d,%d,%s", mine, another, allCards));
            }

        }

    private void handlePlay(String message) {
        String[] temp = message.split(",");
        int player = Integer.parseInt(temp[1]);
        String card = temp[2];
        System.out.println(player + card);

        int player1;
        int player2;

//        int mine = Integer.parseInt(temp[1]);
//
//        ArrayList<String> tempCards = new ArrayList<>();
//        int another = 0;
//
//        for (Room room : rooms) {
//            if (room.getPlayer1().getID() == mine) {
//                tempCards = room.getPlayer1().getAllCardsString();
//                another = room.getPlayer2().getTotalCards();
//            } else if (room.getPlayer2().getID() == mine) {
//                tempCards = room.getPlayer2().getAllCardsString();
//                another = room.getPlayer1().getTotalCards();
//            } else return;
//
//            String allCards = "";
//            for (int i = 0; i < tempCards.size(); i++) {
//                if (i == 0) allCards += tempCards.get(i);
//                else allCards += String.format(",%s", tempCards.get(i));
//            }
//
//            sendToAllClients(String.format("#roomSetMine,%d,%d,%s", mine, another, allCards));
//        }
    }


    public static int getRoomNumber() {
        return ROOM_NUMBER++;
    }


    private static int getDefaultPort() {
        return DEFAULT_PORT;
    }

    private static int getID(){
        return ID++;
    }

    public Object getObjectFromClient() {

        try {
            serverSocket = new ServerSocket(getDefaultPort());
            socket = serverSocket.accept();


            inputStream = new ObjectInputStream(socket.getInputStream());

            Player player = (Player) inputStream.readObject();
            players.add(player);
            DEFAULT_PORT++;
            socket.close();

        } catch (SocketException se) {
            System.exit(0);
            se.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
        return player;

    }
}
