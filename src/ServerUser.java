import servers.ServerUDP;

import java.io.IOException;

/**
 * I am main able to use the UDP server
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
