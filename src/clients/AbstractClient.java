package clients;

import java.io.IOException;
import java.util.*;

/**
 * I am an abstract client that send random messages to the server.
 * I contains a list of random message.
 */
public abstract class AbstractClient {

    /**
     * Some possibilities of message for the server.
     */
    protected Set<String> possibilities;

    /**
     * I initialize the server. I open a socket, set the address, the port and the possible messages for the server.
     * @param address The address of the server
     * @param port The port of the server
     * @throws IOException Raised if there is a problem with the socket or the address of the server.
     */
    public AbstractClient(String address, Integer port)  throws IOException {
        this.openSocket(address, port);
        this.initPossibilities();
    }

    /**
     * I open a socket, set the address and the port.
     * @param address The address of the server
     * @param port The port of the server
     * @throws IOException Raised if there is a problem with the socket or the address of the server.
     */
    public abstract void openSocket(String address, Integer port) throws IOException;

    /**
     * Close the socket
     */
    public abstract void closeSocket()  throws IOException ;

    /**
     * Init the possibilities of message to send to the server
     */
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

    /**
     * Select a random message.
     * @return the selected message
     */
    public String randomMessage() {
        System.out.println("Select random message");
        List<String> asList = new ArrayList<>(this.possibilities);
        Collections.shuffle(asList);
        return asList.get(0);
    }

    /**
     * Send a random message to the server
     * @throws IOException Raised if there is a problem during the sending or reception of the message.
     */
    public abstract void sendRandomMessage() throws IOException;

    /**
     * After sending a message I wait for the answer.
     * @throws IOException Raised if there is a problem during the reception.
     */
    public abstract void manageAnswer() throws IOException;

}
