package hr.fer.zemris.java.server;

public class Message {
      
    public static final byte HELLO = 1;
    public static final byte ACK = 2;
    public static final byte BYE = 3;
    public static final byte OUTMSG = 4;
    public static final byte INMSG = 5;

    
    final byte[] data;
    final byte type;
    int offset;
  

    public Message(byte[] data) {
        this.data = data;
        this.type = data[0];
        this.offset = 1;
    }
}


