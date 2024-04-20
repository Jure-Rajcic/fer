package hr.fer.zemris.java.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class Client {

    final BlockingQueue<Message> receivedMessages;
    final BlockingQueue<Message> sentMessages;
    final InetAddress IPAddress;
    final int port;

    long UID;
    String name;
    volatile boolean[] flags;

    public Client(InetAddress IPAddress, int port) {
        this.IPAddress = IPAddress;
        this.port = port;
        this.receivedMessages = new LinkedBlockingQueue<>();
        this.sentMessages = new LinkedBlockingQueue<>();
        this.flags = new boolean[5 + 1]; // 1 - HELLO, 2 - ACK, 3 - BYE, 4 - OUTMSG, 5 - INMSG
    }

    String getKey() { return this.IPAddress.toString() + ":" + this.port; }


    public void startListening(DatagramSocket serverSocket, Map<String, Client> db) {
        new Thread(() -> {
            while (true) {
                Message m = null;
                try { m = this.receivedMessages.take(); } catch (InterruptedException e) { continue; }
                byte[] data = m.data;
                int offset = 1;
                final long n = ByteBuffer.wrap(data, offset, Long.BYTES).getLong();
                offset += Long.BYTES;
                switch (m.type) {
                    case Message.HELLO:
                        if (!this.flags[Message.HELLO]) {
                            System.out.println("Connecting client...");
                            final int nameLength = ByteBuffer.wrap(data, offset, Short.BYTES).getShort();
                            offset += Short.BYTES;
                            this.name = new String(data, offset, nameLength, StandardCharsets.UTF_8);
                            offset += nameLength;
                            this.UID = ByteBuffer.wrap(data, offset, Long.BYTES).getLong();
                            offset += Long.BYTES;
                            this.flags[Message.HELLO] = true;
                        }
                        try { this.sendACKToClient(serverSocket, n); } catch (IOException e) { e.printStackTrace(); }
                        break;
                    case Message.ACK:
                        System.out.println("Received ACK... from " + this.getKey());
                        flags[Message.ACK] = true;
                        System.out.println(flags[Message.ACK] + " " + this.getKey());
                        break;
                    case Message.BYE:
                        if (!this.flags[Message.BYE]) {
                            System.out.println("Client disconnecting...");
                            final long UID = ByteBuffer.wrap(data, offset, Long.BYTES).getLong();
                            offset += Long.BYTES;
                            db.remove(this.getKey());
                            this.flags[Message.BYE] = true;
                        }
                        try { this.sendACKToClient(serverSocket, n); } catch (IOException e) { e.printStackTrace(); }
                        return;
                    case Message.OUTMSG:
                        System.out.println("Recived message...");
                        final long UID = ByteBuffer.wrap(data, offset, Long.BYTES).getLong();
                        offset += Long.BYTES;
                        final short messageLength = ByteBuffer.wrap(data, offset, Short.BYTES).getShort();
                        offset += Short.BYTES;
                        final String message = new String(data, offset, messageLength, StandardCharsets.UTF_8);
                        offset += messageLength;
                        final int nameLength = ByteBuffer.wrap(data, offset, Short.BYTES).getShort();
                        offset += Short.BYTES;
                        this.name = new String(data, offset, nameLength, StandardCharsets.UTF_8);
                        System.out.println("this.name = " + this.name);
                        offset += nameLength;
                        
                        for (var el : db.entrySet()) {
                            Client c = el.getValue();
                            if (el.getKey().equals(this.getKey())) {
                                try { c.sendACKToClient(serverSocket, n); } catch (IOException e) { e.printStackTrace(); }
                            } else {
                                byte[] nameBytes = this.name.getBytes(StandardCharsets.UTF_8);
                                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                                ByteBuffer bb = ByteBuffer.allocate(1 + Long.BYTES + Short.BYTES + nameBytes.length + Short.BYTES + messageBytes.length);
                                bb.put(Message.INMSG);
                                bb.putLong(n);
                                bb.putShort((short) nameBytes.length);
                                bb.put(nameBytes);
                                bb.putShort((short) messageBytes.length);
                                bb.put(messageBytes);
                                c.sentMessages.add(new Message(bb.array()));
                            }
                        }
                        break;
                    default:
                        System.out.println("Unknown message type");
                        break;
                }
            }
        }).start();
    }

    public void startSending(DatagramSocket serverSocket) {
        new Thread(() -> {
            while (true) {
                Message m = null;
                try { m = this.sentMessages.take(); } catch (InterruptedException e) { continue; }
                for (int i = 0; i < 10; i++) {
                    System.out.printf("sending message to %s [%d]\n", this.getKey(), i+1);
                    try { serverSocket.send(new DatagramPacket(m.data, m.data.length, this.IPAddress, this.port)); } catch (IOException e) { e.printStackTrace(); }
                    try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
                    if (flags[Message.ACK]) { break; }
                    System.out.println("Failed to send message to " + this.getKey());
                }
                // reset flag
                flags[Message.ACK] = false;
            }
        }).start();
    }

    public void sendACKToClient(DatagramSocket serverSocket, long number) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeByte(2);
        dos.writeLong(number);
        dos.writeLong(this.UID);
        dos.close();
        serverSocket.send(new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, this.IPAddress, this.port));
    }

}
