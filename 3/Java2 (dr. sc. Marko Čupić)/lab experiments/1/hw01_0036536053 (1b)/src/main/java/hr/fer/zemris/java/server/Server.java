package hr.fer.zemris.java.server;

import java.io.IOException;
import java.net.*;

import java.util.HashMap;

public class Server {


    public static void main(String[] args) {
        // java -cp target/classes hr.fer.zemris.java.server.Server 6000 
        final int port = Integer.parseInt(args[0]);
        HashMap<String, Client> db = new HashMap<>();

        try (DatagramSocket serverSocket = new DatagramSocket(port);){
            while (true) {
                byte[] receiveData = new byte[4000];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try { serverSocket.receive(receivePacket); } catch (IOException e) {continue;}
                System.out.println("Received packet from " + receivePacket.getAddress() + ":" + receivePacket.getPort());
                final String key = receivePacket.getAddress().toString() + ":" + receivePacket.getPort();
                if (!db.containsKey(key)) {
                    final Client c = new Client(receivePacket.getAddress(), receivePacket.getPort());
                    db.put(key, c);
                    c.startListening(serverSocket, db);
                    c.startSending(serverSocket);
                } 
                final Client c = db.get(key);
                c.receivedMessages.add(new Message(receiveData));
            }
        } catch (SocketException e) { e.printStackTrace(); }
    }
}
