package udp;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Created by Cyril on 09/04/2016.
 */
public class ClientUDP {

    protected static final int SIZEPACKET = 512;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected int port;
    protected Set<String> possibilities;

    public ClientUDP(String address, Integer port) throws SocketException, UnknownHostException {
        this.openSocket();
        this.address = InetAddress.getByName(address);
        this.port = port;
        this.initPossibilities();
    }

    public void openSocket() throws SocketException {
        System.out.println("Open Socket");
        this.socket = new DatagramSocket();
    }

    public void closeSocket() {
        System.out.println("Close Socket");
        this.socket.close();
    }

    public void initPossibilities() {
        this.possibilities = new HashSet<>();
        this.possibilities.add("affiche Salut");
        this.possibilities.add("affiche Coucou");
        this.possibilities.add("affiche Comment ca va ?");
        this.possibilities.add("affiche :)");
        this.possibilities.add("effacer");
        this.possibilities.add("effacer");
        this.possibilities.add("effacer");
        this.possibilities.add("Salut");
        this.possibilities.add("Autre");
        this.possibilities.add("Error");
        this.possibilities.add(":(");
    }

    public String randomMessage() {
        System.out.println("Select random message");
        List<String> asList = new ArrayList<>(this.possibilities);
        Collections.shuffle(asList);
        return asList.get(0);
    }

    public void sendRandomMessage() throws IOException {
        System.out.println("Create packet");
        String message = this.randomMessage();
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), this.address, this.port);
        System.out.println("Send packet");
        this.socket.send(packet);
        this.manageAnswer();
    }

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
