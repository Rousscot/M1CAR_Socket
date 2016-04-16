import clients.ClientUDP;

import java.io.IOException;

/**
 * Created by Cyril on 09/04/2016.
 */
public class ClientUDPMain {

    public static void main(String[] args) {
        try {
            ClientUDP client = new ClientUDP(args[0], Integer.valueOf(args[1]));
            client.sendRandomMessage();
            client.closeSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
