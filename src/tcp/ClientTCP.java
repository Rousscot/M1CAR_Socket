package tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

/**
 * Created by Cyril on 10/04/2016.
 */
public class ClientTCP {

    protected Socket socket;
    protected Set<String> possibilities;

    public ClientTCP(String address, Integer port) throws IOException {
        this.openSocket(address, port);
        this.initPossibilities();

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

    public void initPossibilities() {
        this.possibilities = new HashSet<>();
        this.possibilities.add("afficher Salut");
        this.possibilities.add("afficher Coucou");
        this.possibilities.add("afficher Comment ca va ?");
        this.possibilities.add("afficher :)");
        this.possibilities.add("effacer");
        this.possibilities.add("effacer");
        this.possibilities.add("effacer");
        this.possibilities.add("Salut");
        this.possibilities.add("Autre");
        this.possibilities.add("Error");
        this.possibilities.add(":(");
    }

    public String randomMessage() {
        System.out.println("Select random message");
        List<String> asList = new ArrayList<>(this.possibilities);
        Collections.shuffle(asList);
        return asList.get(0);
    }

    public void sendRandomMessage() throws IOException {
        System.out.println("Select message");
        String message = this.randomMessage();
        System.out.println("Message selected: " + message);
        System.out.println("Send packet");
        (new DataOutputStream(socket.getOutputStream())).writeBytes(message);
        this.manageAnswer();
    }

    public void manageAnswer() throws IOException {
        System.out.println("Wait answer...");
        System.out.println("Print packet");
        System.out.println((new BufferedReader(new InputStreamReader(System.in))).readLine());

    }

}
