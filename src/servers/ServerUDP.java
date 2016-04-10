package servers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * I am a class that implement an UDP server.
 */
public class ServerUDP extends AbstractServer {

    protected static final int SIZEPACKET = 512;
    protected DatagramSocket socket;

    public ServerUDP() throws IOException {
        super();
    }

    /**
     * I init the socket.
     *
     * @throws SocketException raised if I cannot open the socket.
     */
    public void initSocket() throws SocketException {
        System.out.println("Open socket");
        this.socket = new DatagramSocket(SOCKET);
    }

    /**
     * I launch the server.
     *
     * @throws IOException if there is a problem with a datagram packet.
     */
    public void launch() throws IOException {
        while (!this.stop) {
            System.out.println("Wait for a message...");
            DatagramPacket packet = new DatagramPacket(new byte[SIZEPACKET], SIZEPACKET);
            this.socket.receive(packet);

            System.out.println("Get data");
            byte[] array = packet.getData();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < packet.getLength(); i++) {
                builder.append((char) array[i]);
            }

            System.out.println("Execute action");
            String[] receivedMessages = builder.toString().split(" ", 2);
            String rest;
            try {
                rest = receivedMessages[1];
            } catch (IndexOutOfBoundsException e) {
                rest = "";
            }
            String result = this.commands.getOrDefault(receivedMessages[0], this.getErrorLambda()).apply(rest, this.gui);

            System.out.println("Send result");
            (new DatagramSocket()).send(new DatagramPacket(result.getBytes(), result.length(), packet.getAddress(), packet.getPort()));

        }
    }

    /**
     * I stop the server and close the socket.
     */
    public void stop() {
        System.out.println("Stop server.");
        this.stop = true;
        this.socket.close();
    }
}

