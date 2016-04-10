package clients;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * I am the UDP version of the client.
 */
public class ClientUDP extends AbstractClient {

    protected static final int SIZEPACKET = 512;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected int port;

    public ClientUDP(String address, Integer port) throws IOException {
        super(address, port);
    }

    public void openSocket(String address, Integer port) throws IOException {
        System.out.println("Address: " + address);
        this.address = InetAddress.getByName(address);
        System.out.println("Port: " + port);
        this.port = port;
        System.out.println("Open Socket");
        this.socket = new DatagramSocket();
    }

    public void closeSocket() {
        System.out.println("Close Socket");
        this.socket.close();
    }

    /**
     * Send a random message to the server
     *
     * @throws IOException Raised if there is a problem during the sending or reception of the message.
     */
    public void sendRandomMessage() throws IOException {
        System.out.println("Create packet");
        String message = this.randomMessage();
        System.out.println("Message selected: " + message);
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), this.address, this.port);
        System.out.println("Send packet");
        this.socket.send(packet);
        this.manageAnswer();
    }

    /**
     * After sending a message I wait for the answer.
     *
     * @throws IOException Raised if there is a problem during the reception.
     */
    public void manageAnswer() throws IOException {
        System.out.println("Wait answer...");
        DatagramPacket packet = new DatagramPacket(new byte[SIZEPACKET], SIZEPACKET);
        this.socket.receive(packet);
        System.out.println("Print packet");
        byte[] array = packet.getData();
        for (byte bit : array) {
            System.out.print(Character.toString((char) bit));
        }
        System.out.println();
    }

}
