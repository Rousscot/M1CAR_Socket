package udp;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * I am a simple UDP client that send random messages to the server.
 * I contains a list of random message.
 */
public class ClientUDP {

    protected static final int SIZEPACKET = 512;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected int port;

    /**
     * Some possibilities of message for the server.
     */
    protected Set<String> possibilities;

    /**
     * I initialize the server. I open a socket, set the address, the port and the possible messages for the server.
     * @param address The address of the server
     * @param port The port of the server
     * @throws SocketException Raised if there is a problem with the socket.
     * @throws UnknownHostException Raised if there is a problem with the address of the server.
     */
    public ClientUDP(String address, Integer port) throws SocketException, UnknownHostException {
        this.openSocket();
        System.out.println("Address: " + address);
        this.address = InetAddress.getByName(address);
        System.out.println("Port: " + port);
        this.port = port;
        this.initPossibilities();
    }

    /**
     * I open an UDP Socket
     * @throws SocketException Raised if there is a problem at the initialization.
     */
    public void openSocket() throws SocketException {
        System.out.println("Open Socket");
        this.socket = new DatagramSocket();
    }

    /**
     * Close the socket
     */
    public void closeSocket() {
        System.out.println("Close Socket");
        this.socket.close();
    }

    /**
     * Init the possibilities of message to send to the server
     */
    public void initPossibilities() {
        this.possibilities = new HashSet<>();
        this.possibilities.add("afficher Salut");
        this.possibilities.add("afficher Coucou");
        this.possibilities.add("afficher Comment ca va ?");
        this.possibilities.add("afficher :)");
        this.possibilities.add("effacer");
        this.possibilities.add("effacer");
        this.possibilities.add("effacer");
        this.possibilities.add("Salut");
        this.possibilities.add("Autre");
        this.possibilities.add("Error");
        this.possibilities.add(":(");
    }

    /**
     * Select a random message.
     * @return the selected message
     */
    public String randomMessage() {
        System.out.println("Select random message");
        List<String> asList = new ArrayList<>(this.possibilities);
        Collections.shuffle(asList);
        return asList.get(0);
    }

    /**
     * Send a random message to the server
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
