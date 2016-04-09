import udp.ClientUDP;

import java.io.IOException;

/**
 * Created by Cyril on 09/04/2016.
 */
public class ClientUser {

    public static void main(String[] args) {
        try {
            ClientUDP client = new ClientUDP("192.168.0.11", 8000);
            client.sendRandomMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
