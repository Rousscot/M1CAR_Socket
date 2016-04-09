import udp.ServerUDP;

import java.io.IOException;

/**
 * I am main able to user the UDP server
 */
public class ServerUser {

    public static void main(String[] args) {
        try {
            ServerUDP server = new ServerUDP();
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
