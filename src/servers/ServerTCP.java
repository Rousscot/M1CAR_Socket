package servers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * I am a class that implement an TCP server.
 */
public class ServerTCP extends AbstractServer {

    protected ServerSocket socket;
    protected Socket connectionSocket;


    public ServerTCP() throws IOException {
        super();
    }

    public void initSocket() throws IOException {
        System.out.println("Open socket");
        this.socket = new ServerSocket(SOCKET);
    }

    public void launch() throws IOException {
        while (!this.stop) {
            System.out.println("Open connection");
            this.connectionSocket = this.socket.accept();
            this.loopTreatment();

            System.out.println("Close socket");
            this.connectionSocket.close();
        }
    }

    public void loopTreatment() throws IOException {
        System.out.println("Get data");
        String[] receivedMessages = (new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()))).readLine().split(" ", 2);

        String result = this.executeAction(receivedMessages);

        System.out.println("Send result");
        (new DataOutputStream(connectionSocket.getOutputStream())).writeBytes(result + "\n");

        //Loop
        if (!this.stop) {
            this.loopTreatment();
        }
    }

    public String executeAction(String[] receivedMessages) {
        System.out.println("Execute action");
        String rest;
        try {
            rest = receivedMessages[1];
        } catch (IndexOutOfBoundsException e) {
            rest = "";
        }

        return commands.getOrDefault(receivedMessages[0], this.getErrorLambda()).apply(rest, this.gui);
    }

    public void stop() throws IOException {
        System.out.println("Stop server.");
        this.stop = true;
        this.socket.close();
    }

}
