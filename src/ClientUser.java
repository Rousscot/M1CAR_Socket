import udp.ClientUDP;

import java.io.IOException;

/**
 * Created by Cyril on 09/04/2016.
 */
public class ClientUser {

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
