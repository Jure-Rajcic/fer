package hr.fer.zemris.java.client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import hr.fer.zemris.java.server.Message;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {

    // java -cp target/classes hr.fer.zemris.java.server.Server localhost 6000 "userx"
    long UID;
    long n;
    final DatagramSocket clientSocket;
    final String userName;
    JTextField inputField;
    JTextArea messageArea;

    Client(DatagramSocket clientSocket, String userName) {
        this.clientSocket = clientSocket;
        this.userName = userName;
        this.initGUI();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initGUI() {
         // Set up the window
         setTitle("Chat client: " + this.userName);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(400, 400);
         setLocationRelativeTo(null);
 
         // Create the input field
         inputField = new JTextField();
 
         // Create the message area
         messageArea = new JTextArea();
         messageArea.setEditable(false);
 
         // Add the components to the window
         Container contentPane = getContentPane();
         contentPane.setLayout(new BorderLayout());
         contentPane.add(inputField, BorderLayout.NORTH);
         contentPane.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    }




    boolean connect(String host, int port) {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.printf("Trying to connect to server... [%d]\n", i+1);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            try {
                dos.writeByte(1);
                dos.writeLong(0);
                dos.writeUTF(this.userName);
                dos.writeLong(rand.nextLong()); 
                dos.close();
                clientSocket.send(new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, InetAddress.getByName(host), port));
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                
                clientSocket.receive(receivePacket);
                int offset = 0;
                byte type = receiveData[offset];
                offset += 1;
                long n = ByteBuffer.wrap(receiveData, offset, Long.BYTES).getLong();
                offset += Long.BYTES;
                long UID = ByteBuffer.wrap(receiveData, offset, Long.BYTES).getLong();
                offset += Long.BYTES;
                System.out.println("From Server: Type: " + type + ", number: " + n + ", UID: " + UID);
                this.UID = UID;
                this.n = UID;
                return true;
            } 
            catch (SocketTimeoutException e) { System.out.println("Timeout"); continue; }
            catch (IOException e) { e.printStackTrace(); }
        }
        System.out.println("Cant connect to server, turning off...");
        return false;
    }

    boolean disconnect() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("Trying to disconcert from server... [%d]\n", i+1);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            try {
                dos.writeByte(3);
                dos.writeLong(++this.n);
                dos.writeLong(this.UID); 
                dos.close();
                clientSocket.send(new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, InetAddress.getByName("localhost"), 9876));
                
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                int offset = 0;
                byte type = receiveData[offset];
                offset += 1;
                long number = ByteBuffer.wrap(receiveData, offset, Long.BYTES).getLong();
                offset += Long.BYTES;
                long UID = ByteBuffer.wrap(receiveData, offset, Long.BYTES).getLong();
                offset += Long.BYTES;
                System.out.println("From Server: Type: " + type + ", number: " + number + ", UID: " + UID);
                clientSocket.close();
                return true;
            } 
            catch (SocketTimeoutException e) { System.out.println("Timeout"); continue; }
            catch (IOException e) { e.printStackTrace(); }
        }
        System.out.println("Cant connect to server, turning off...");
        return false;
    }

    public static void main(String[] args) {
        // java -cp target/classes hr.fer.zemris.java.client.Client "localhost" 6000 "userx"
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        final String userName = args[2];
        try {
            final DatagramSocket clientSocket = new DatagramSocket(); 
            clientSocket.setSoTimeout(5000);
            final Client c = new Client(clientSocket, userName);
            c.connect(host, port);
            c.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("Closing window");
                    c.disconnect();
                    System.exit(0);
                }
            });
            c.inputField.addActionListener((ae) -> {
                final String message = c.inputField.getText();
                c.messageArea.append("[Me] " + message + "\n\n");
                c.inputField.setText("");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                System.out.println(message);
                try {
                    dos.writeByte(Message.OUTMSG);
                    dos.writeLong(++c.n);
                    dos.writeLong(c.UID);
                    dos.writeUTF(message);
                    dos.writeUTF(userName);
                    dos.close();
                clientSocket.send(new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, InetAddress.getByName(host), port));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            while(true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                while(true) {
                    try { 
                        clientSocket.receive(receivePacket);
                        break;
                    } catch (IOException e) { continue; }
                }

                int offset = 0;
                byte type = receiveData[offset];
                offset += 1;
                System.out.println("From Server: Type: " + type);
                if (type == Message.INMSG) {
                    long number = ByteBuffer.wrap(receiveData, offset, Long.BYTES).getLong();
                    offset += Long.BYTES;   
                    short nameLength = ByteBuffer.wrap(receiveData, offset, Short.BYTES).getShort();
                    offset += Short.BYTES;
                    String name = new String(receiveData, offset, nameLength, StandardCharsets.UTF_8);
                    offset += nameLength;
                    System.out.println("name: " + name);
                    short messageLength = ByteBuffer.wrap(receiveData, offset, Short.BYTES).getShort();
                    offset += Short.BYTES;
                    String msg = new String(receiveData, offset, messageLength, StandardCharsets.UTF_8);
                    offset += messageLength;
                    System.out.println("msg: " + msg);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    try { sb.append(InetAddress.getByName("localhost").getHostAddress().toString()); } catch (UnknownHostException e1) {e1.printStackTrace();}
                    sb.append(":");
                    sb.append(9876);
                    sb.append("]");
                    sb.append(" ");
                    sb.append("Poruka od korisnika: " + name + "\n");
                    sb.append(msg + "\n");
                    c.messageArea.append(sb.toString() + "\n");

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(bos);
                    try {
                    dos.writeByte(2);
                    dos.writeLong(++c.n);
                    dos.writeLong(c.UID);
                    dos.close();
                        clientSocket.send(new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, InetAddress.getByName(host), port)); 
                    } catch (IOException e) { e.printStackTrace(); }
                }
            }

        } catch (SocketException e) { e.printStackTrace(); }
    }

}
