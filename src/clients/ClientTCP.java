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
    protected BufferedReader br;
    protected DataOutputStream dos;

    public ClientTCP(String address, Integer port) throws IOException {
        super(address, port);
        this.initStreams();
    }

    public void initStreams() {
        try {
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            this.log("Error. Cannot instanciate the streams :(");
        }
    }

    public void openSocket(String address, Integer port) throws IOException {
        this.log("Address: " + address);
        this.log("Port: " + port);
        this.log("Open Socket");
        this.socket = new Socket(address, port);
    }

    public void closeSocket() throws IOException {
        this.log("Close Socket");
        this.socket.close();
    }

    public void sendRandomMessage() throws IOException {
        this.log("Select message");
        String message = this.randomMessage();
        this.log("Message selected: " + message);
        this.log("Send packet");
        dos.writeBytes(message + "\n");
        this.manageAnswer();
    }

    public void manageAnswer() throws IOException {
        this.log("Wait answer...");
        this.log(br.readLine());

    }

}
