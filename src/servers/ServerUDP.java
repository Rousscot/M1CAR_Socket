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
        this.log("Open socket");
        this.socket = new DatagramSocket(SOCKET);
    }

    /**
     * I launch the server.
     *
     * @throws IOException if there is a problem with a datagram packet.
     */
    public void launch() throws IOException {
        while (!this.stop) {
            this.log("Wait for a message...");
            DatagramPacket packet = new DatagramPacket(new byte[SIZEPACKET], SIZEPACKET);
            this.socket.receive(packet);

            this.log("Read data");
            byte[] array = packet.getData();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < packet.getLength(); i++) {
                builder.append((char) array[i]);
            }

            this.log("Execute action : " + builder.toString());
            String[] receivedMessages = builder.toString().split(" ", 2);
            String rest;
            try {
                rest = receivedMessages[1];
            } catch (IndexOutOfBoundsException e) {
                rest = "";
            }
            String result = this.commands.getOrDefault(receivedMessages[0], this.commands.get("error")).apply(rest);

            this.log("Send result : " + result);
            (new DatagramSocket()).send(new DatagramPacket(result.getBytes(), result.length(), packet.getAddress(), packet.getPort()));

        }
    }

    /**
     * I stop the server and close the socket.
     */
    public void stop() {
        this.log("Stop server.");
        this.stop = true;
        this.socket.close();
    }
}

