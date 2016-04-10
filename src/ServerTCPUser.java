import servers.ServerTCP;

import java.io.IOException;

/**
 * Created by Cyril on 10/04/2016.
 */
public class ServerTCPUser {

    public static void main(String[] args) {
        try {
            ServerTCP server = new ServerTCP();
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
