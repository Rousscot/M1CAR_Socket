package clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * I am the TCP version of the client.
 */
public class ClientTCP extends AbstractClient {

    protected Socket socket;

    public ClientTCP(String address, Integer port) throws IOException {
        super(address, port);
    }

    public void openSocket(String address, Integer port) throws IOException {
        System.out.println("Address: " + address);
        System.out.println("Port: " + port);
        System.out.println("Open Socket");
        this.socket = new Socket(address, port);
    }

    public void closeSocket() throws IOException {
        System.out.println("Close Socket");
        this.socket.close();
    }

    public void sendRandomMessage() throws IOException {
        System.out.println("Select message");
        String message = this.randomMessage();
        System.out.println("Message selected: " + message);
        System.out.println("Send packet");
        (new DataOutputStream(socket.getOutputStream())).writeBytes(message + "\n");
        this.manageAnswer();
    }

    public void manageAnswer() throws IOException {
        System.out.println("Wait answer...");
        System.out.println((new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine()));

    }

}
