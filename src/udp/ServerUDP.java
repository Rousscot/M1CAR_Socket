package udp;

import gui.IHM;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * I am a class to answer the question 1 of TP1.
 *
 * Implementation details:
 *
 * To avoid multiple if or try catch I create a dictionary mapping all possible commands with a lambda expression to execute.
 */
public class ServerUDP {

    protected static final int SOCKET = 8000;
    protected static final int SIZEPACKET = 512;
    protected IHM gui;
    protected DatagramSocket socket;

    /**
     * I am a map that store a lambda to execute for each actions I can handle.
     */
    protected HashMap<String, BiFunction<String, IHM, String>> commands;

    /**
     * If I go the false the server will stop.
     */
    protected Boolean stop;

    /**
     * I am a constructor that will launch the IHM, build a map with the different commands I can handle and init the socket.
     * @throws SocketException is raise if I cannot open the socket.
     */
    public ServerUDP() throws SocketException {
        this.launchGUI();
        this.initCommands();
        this.initSocket();
        this.stop = false;
    }

    /**
     * I launch the IHM.
     */
    public void launchGUI() {
        gui = new IHM("Ma  borne d’affichage");
        IHM.mettreListenerSortieProgramme(gui);
        gui.setVisible(true);
    }

    /**
     * I init the socket.
     * @throws SocketException raised if I cannot open the socket.
     */
    public void initSocket() throws SocketException {
        this.socket = new DatagramSocket(SOCKET);
    }

    /**
     * I build a dictionary with all the actions I manage and a lambda to execute.
     */
    public void initCommands() {
        this.commands = new HashMap<>();
        commands.put("afficher", (String rest, IHM ihm) -> {
            ihm.ajouterLigne(rest);
            return "Ok : Ordre execute";
        });
        commands.put("effacer", (String rest, IHM ihm) -> {
            ihm.raz();
            return "Ok : Ordre execute";
        });
    }

    /**
     * I launch the server.
     * @throws IOException if there is a problem with a datagram packet.
     */
    public void launch() throws IOException {
        while (!this.stop) {
            DatagramPacket packet = new DatagramPacket(new byte[SIZEPACKET], SIZEPACKET);
            socket.receive(packet);

            byte[] array = packet.getData();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < packet.getLength(); i++) {
                builder.append((char) array[i]);
            }

            String[] receivedMessages = builder.toString().split(" ", 2);
            String result = commands.getOrDefault(receivedMessages[0], (String order, IHM ihm) -> "ERREUR : Ordre inconnu").apply(receivedMessages[1], gui);

            (new DatagramSocket()).send(new DatagramPacket(result.getBytes(), result.length(), packet.getAddress(), packet.getPort()));

        }
    }

    /**
     * I stop the server and close the socket.
     */
    public void stop(){
        this.stop = true;
        this.socket.close();
    }
}

