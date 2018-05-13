package network;

import model.Room;
import server.AbstractServer;
import server.ConnectionToClient;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server extends AbstractServer {
    public static int ROOM_NUMBER = 1000;
    public static ArrayList<Room> rooms = new ArrayList<>();

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
        if(message.contains("#logintoServer")){
            handleLogin(message,client);
            return;
        }if(message.contains("#createRoom")){
            handleCreateRoom(message);
            return;
        }if(message.contains("#joinRoom")){
            handleJoin(message,client);
            return;

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
        String[] temp =  message.split(",");;
        String name = temp[1];

        client.setInfo("name",name);

        String send = client.getInfo("name") + " has joined server";
        listener.onLog(send);
        this.sendToAllClients(String.format("------- %s ------\n",send));
    }

    private void handleCreateRoom(String message){

        String[] temp =  message.split(",");;
        String player = temp[1];
        String roomName = temp[2];

        Room room = new Room(roomName);

        rooms.add(room);

        this.sendToAllClients("#createRoom," + String.format("%d %-30s room's name: %-30s",room.getRoomNumber(),player,room.getRoomName()));
    }

    private void handleJoin(String message,ConnectionToClient client){
        String[] temp =  message.split(",");
        int roomNum = Integer.parseInt(temp[0]);

        for(Room room: rooms){
            if(room.getRoomNumber() == roomNum)
        }
    }
    }

    public static int getRoomNumber() {
        return ROOM_NUMBER++;
    }
}
