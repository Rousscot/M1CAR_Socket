import clients.ClientTCP;

import java.io.IOException;

/**
 * Created by Cyril on 10/04/2016.
 */
public class ClientTCPMain {

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP(args[0], Integer.valueOf(args[1]));
            client.sendRandomMessage();
            client.closeSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}