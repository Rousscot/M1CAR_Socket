package servers;

import gui.IHM;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * I am a class that implement an TCP server.
 */
public class ServerTCP extends AbstractServer {

    protected ServerSocket socket;

    protected Socket connexionSocket;


    public ServerTCP() throws IOException {
        super();
    }

    public void initSocket() throws IOException {
        this.log("Open server socket");
        this.socket = new ServerSocket(SOCKET);
    }

    public void launch() throws IOException {
        while (!this.stop) {
            this.log("Open client socket");
            this.connexionSocket = this.socket.accept();
            new Thread(new ClientHandler(connexionSocket, this.gui, this.commands)).start();
        }
    }

    public void stop() throws IOException {
        this.log("Stop server.");
        this.stop = true;
        this.socket.close();
    }

    public class ClientHandler implements Runnable {

        protected Socket connectionSocket;

        protected IHM gui;

        protected HashMap<String, BiFunction<String, IHM, String>> commands;

        public ClientHandler(Socket socket, IHM gui, HashMap<String, BiFunction<String, IHM, String>> commands) {
            this.connectionSocket = socket;
            this.gui = gui;
            this.commands = commands;
        }

        public void run() {
            this.log("Get data");
            String receivedMessages = "";
            try {
                receivedMessages = (new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()))).readLine();
            } catch (IOException e) {
                e.printStackTrace(); //TODO
            }

            String result = this.executeAction(receivedMessages);

            this.log("Send result" + result);
            try {
                (new DataOutputStream(connectionSocket.getOutputStream())).writeBytes(result + "\n");
            } catch (IOException e) {
                e.printStackTrace();//TODO
            }

            this.run();
        }

        public String executeAction(String receivedMessages) {
            this.log("Execute action : " + receivedMessages);
            String rest;
            try {
                rest = receivedMessages.split(" ")[1];
            } catch (IndexOutOfBoundsException e) {
                rest = "";
            }

            return commands.getOrDefault(receivedMessages.split(" ")[0], this.commands.get("error")).apply(rest, this.gui);
        }

        public void log(String message){
            System.out.println("Server : " + message);
        }

    }

}
