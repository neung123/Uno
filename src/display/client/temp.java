////package network;
////
////import model.Player;
////import model.Room;
////import server.AbstractServer;
////import server.ConnectionToClient;
////
////import java.io.IOException;
////import java.io.ObjectInputStream;
////import java.io.ObjectOutputStream;
////import java.net.ServerSocket;
////import java.net.Socket;
////import java.net.SocketException;
////import java.time.LocalTime;
////import java.time.format.DateTimeFormatter;
////import java.util.ArrayList;
////
////import static network.Client.*;
////
////public class Server extends AbstractServer {
////    public static int ROOM_NUMBER = 1000;
////    private static boolean isConnected = false;
////    public static int ID = 1;
////    public static ArrayList<Room> rooms = new ArrayList<>();
////    public static ArrayList<Player> players = new ArrayList<>();
////    private static int DEFAULT_PORT = 1000;
////    private ServerSocket serverSocket = null;
////    private Socket socket = null;
////    private ObjectInputStream inputStream = null;
////    private ObjectOutputStream outputStream = null;
////
////    private static LocalTime time = LocalTime.now();
////    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
////    MessageListener listener;
////
////    /**
////     * Constructs a new server.
////     *
////     * @param port the port number on which to listen.
////     */
////    public Server(int port) {
////        super(port);
////        try {
////            listen();
////        } catch (IOException e) {
////            System.out.println("ERROR - Could not listen for clients");
////        }
////    }
////
////    @Override
////    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
////
////        String message = msg.toString();
////        if (message.contains("#logintoServer")) {
////            handleLogin(message, client);
////            return;
////        }
////        if (message.contains("#createRoom")) {
////            handleCreateRoom(message);
////            return;
////        }
////        if (message.contains("#joinRoom")) {
////            handleJoin(message);
////            return;
////        }
////        if(message.contains("#setUpRoom")){
////            handleSetup(message);
////            return;
////        }
////
////        String ms = String.format("%s(%s): %s\n",client.getInfo("name"),time.format(dtf),msg.toString());
////        this.sendToAllClients(ms);
////    }
////
////    protected void serverStarted() {
////        super.serverStarted();
////        listener.onMessage("Start server...");
////    }
////
////    @Override
////    protected void clientConnected(ConnectionToClient client) {
////        super.clientConnected(client);
////    }
////
////    protected void serverStopped() {
////        listener.onMessage("Server has stopped.");
////    }
////
////    public void setListener(MessageListener listener) {
////        this.listener = listener;
////    }
////
////    private void handleLogin(String message,ConnectionToClient client){
////        String[] temp =  message.split(",");;
////        String name = temp[1];
////
////        client.setInfo("name",name);
////        client.setInfo("port",DEFAULT_PORT);
////        client.setInfo("id",getID());
////
////        String send = client.getInfo("name") + " has joined server";
////        listener.onLog(send);
////        this.sendToAllClients(String.format("------- %s ------\n",send));
////
////
////        String setID = String.format("#id,%s,%s",client.getInfo("name"),client.getInfo("id"));
////        this.sendToAllClients(setID);
////
////        String setPort = String.format("##,%s,%s",client.getInfo("name"),client.getInfo("port"));
////        this.sendToAllClients(setPort);
////        getObjectFromClient();
////    }
////
////    private void handleCreateRoom(String message){
////
////        String[] temp =  message.split(",");
////        String player = temp[1];
////        String roomName = temp[2];
////        int ID = Integer.parseInt(temp[3]);
////
////        Room room = new Room(roomName);
////
////        for(Player p : players){
////            if(p.getID() == ID) room.addPlayer(p);
////        }
////        rooms.add(room);
////
////        this.sendToAllClients(String.format("#createRoom,%-15d %-30s room's name: %-30s\n",room.getRoomNumber(),player,room.getRoomName()));
////    }
////
////    private void handleJoin(String message){
////        String[] temp =  message.split(",");
////        int roomNum = Integer.parseInt(temp[1]);
////
////        int player1;
////        int player2;
////
////        for(Room room: rooms){
////            if(room.getRoomNumber() == roomNum){
////                for(Player p : players){
////                    if(p.getID() == ID) {
////                        room.addPlayer(p);
////                        player1 = room.getPlayer1().getID();
////                        player2 = room.getPlayer2().getID();
////                        room.start();
////
////                        sendToAllClients(String.format("#joinToRoom,%d,%d",player1,player2));
////                    }
////
////                }
////                return;
////            }
////        }
////    }
////
////    private void handleSetup(String message) {
////        String[] temp = message.split(",");
////        int roomNum = Integer.parseInt(temp[1]);
////
////
////        int player1;
////        int player2;
////
////        for (Room room : rooms) {
////            if (room.getRoomNumber() == roomNum) {
////                player1 = room.getPlayer1().getID();
////                player2 = room.getPlayer2().getID();
////                String card = room.getCurrentCardString();
////
////                sendToAllClients(String.format("#roomMid,%d,%d,%s", player1, player2, card));
////            }
////
////        }
////        return;
////    }
////
////    public static int getRoomNumber() {
////        return ROOM_NUMBER++;
////    }
////
////
////    private static int getDefaultPort() {
////        return DEFAULT_PORT;
////    }
////
////    private static int getID(){
////        return ID++;
////    }
////
////    public Object getObjectFromClient() {
////
////        try {
////            serverSocket = new ServerSocket(getDefaultPort());
////            socket = serverSocket.accept();
////
////
////            inputStream = new ObjectInputStream(socket.getInputStream());
////
////            Player player = (Player) inputStream.readObject();
////            players.add(player);
////            DEFAULT_PORT++;
////            socket.close();
////
////        } catch (SocketException se) {
////            System.exit(0);
////            se.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (ClassNotFoundException cn) {
////            cn.printStackTrace();
////        }
////        return player;
////
////    }
////}
//
//package network;
//import client.AbstractClient;
//import model.Player;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.net.SocketException;
//
//
//public class Client extends AbstractClient {
//    private static Socket socket = null;
//    private static ObjectOutputStream outputStream = null;
//    private static boolean isConnected = false;
//
//    MessageListener listener;
//    CardListener cardListener;
//    static String host;
//    String clientName;
//    boolean isCreate = false;
//    static int port;
//    public static Player player;
//
//    /**
//     * Constructs the client.
//     *
//     * @param host the server's host name.
//     * @param port the port number.
//     */
//    public Client(String host, int port, String clientName) throws IOException {
//        super(host, port);
//        this.host = host;
//        this.clientName = clientName;
//        openConnection();
//    }
//
//    public void sendMessage(String msg){
//        handleMessageFromClient(msg);
//    }
//
//    public void handleMessageFromClient(String message) {
//        try {
//            sendToServer(message);
//        } catch (IOException e) {
//            System.out.println("handleMessageFromClient");
//            System.out.println("Could not send message to server.  Terminating client.");
//            System.exit(0);
//        }
//    }
//
//    @Override
//    protected void connectionEstablished() {
//        try {
//            sendToServer("#logintoServer," + clientName);
//        } catch (IOException e) {
//            System.out.println("Could not send message to server.  Terminating client.");
//            System.exit(0);
//        }
//        super.connectionEstablished();
//
//    }
//
//    public String getClientName() {
//        return clientName;
//    }
//
//    public void setListener(MessageListener listener) {
//        this.listener = listener;
//    }
//
//    public void setCardListener(CardListener listener) {
//        this.cardListener = listener;
//    }
//    public static Player getPlayer() { return player; }
//
//    @Override
//    protected void handleMessageFromServer(Object msg) {
//        String message = msg.toString();
//        System.out.println(message);
//
//        if(message.contains("#createRoom")) {
//            String[] temp = message.split(",");
//            String msgRoom = temp[1];
//
//            listener.onLog(msgRoom.toString());
//            return;
//        }if (message.contains("#id")) {
//            String[] temp =  message.split(",");
//            String name = temp[1];
//            int id = Integer.parseInt(temp[2]);
//
//            if(name.equals(clientName)) {
//                player = new Player(name, id);
//            }
//            return;
//        }if (message.contains("##")) {
//            String[] temp =  message.split(",");;
//            String name = temp[1];
//
//            if(name.equals(player.getName())) {
//                port = Integer.parseInt(temp[2]);
//                sendObjectToServer(player);
//            }
//
//            return;
//        }if(message.contains("#joinToRoom")) {
//            String[] temp = message.split(",");
//            int ID1 = Integer.parseInt(temp[1]);
//            int ID2 = Integer.parseInt(temp[2]);
////            int room = Integer.parseInt(temp[3]);
//
//            if (ID1 == player.getID() || ID2 == player.getID()) {
//                if (ID2 == 0) return;
//                listener.changeTo("InUnoGame.fxml");
//
////                sendMessage( "#setUpRoom," + room);
//            }
//            return;
//        }if(message.contains("#roomMid")){
//            System.out.println("roommid");
//            String[] temp = message.split(",");
//            int ID1 = Integer.parseInt(temp[1]);
//            int ID2 = Integer.parseInt(temp[2]);
//            String[] defaultCard = temp[3].split(" ");
//            String color = defaultCard [0];
//            String value = defaultCard [1];
//            String card = String.format("/card_all/%s_%s.jpg",color,value);
//
//            if (ID1 == player.getID() || ID2 == player.getID()) {
//                System.out.println(card);
//                System.out.println(cardListener);
//                cardListener.middleCard(card);
//            }
//            return;
//        }
//
//        listener.onMessage(msg.toString());
//    }
//
//    public boolean isCreate(String roomName) {
//
//        if (isCreate) return true;
//        handleMessageFromClient("#createRoom," + String.format("%s,%s,%d", getClientName(), roomName,player.getID()));
//
//        isCreate = true;
//        return false;
//    }
//
//    private static void sendObjectToServer(Object obj){
//        while (!isConnected) {
//            try {
//                socket = new Socket(host, port);
//                isConnected = true;
//                outputStream = new ObjectOutputStream(socket.getOutputStream());
//
//                outputStream.writeObject(obj);
//                socket.shutdownOutput();
//
//            } catch (SocketException se) {
//                se.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
//
