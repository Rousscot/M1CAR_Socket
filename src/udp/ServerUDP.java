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
 * <p>
 * Implementation details:
 * <p>
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
     *
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
        System.out.println("Launch GUI");
        gui = new IHM("Ma  borne d’affichage");
        IHM.mettreListenerSortieProgramme(gui);
        gui.setVisible(true);
    }

    /**
     * I init the socket.
     *
     * @throws SocketException raised if I cannot open the socket.
     */
    public void initSocket() throws SocketException {
        System.out.println("Open socket");
        this.socket = new DatagramSocket(SOCKET);
    }

    /**
     * I build a dictionary with all the actions I manage and a lambda to execute.
     */
    public void initCommands() {
        System.out.println("Init commands");
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
     *
     * @throws IOException if there is a problem with a datagram packet.
     */
    public void launch() throws IOException {
        while (!this.stop) {
            System.out.println("Wait for a message...");
            DatagramPacket packet = new DatagramPacket(new byte[SIZEPACKET], SIZEPACKET);
            socket.receive(packet);

            System.out.println("Get data");
            byte[] array = packet.getData();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < packet.getLength(); i++) {
                builder.append((char) array[i]);
            }

            System.out.println("Execute action");
            String[] receivedMessages = builder.toString().split(" ", 2);
            String rest;
            try {
                rest = receivedMessages[1];
            } catch (IndexOutOfBoundsException e) {
                rest = "";
            }
            String result = commands.getOrDefault(receivedMessages[0], (String order, IHM ihm) -> "ERREUR : Ordre inconnu").apply(rest, gui);


            System.out.println("Send result");
            (new DatagramSocket()).send(new DatagramPacket(result.getBytes(), result.length(), packet.getAddress(), packet.getPort()));

        }
    }

    /**
     * I stop the server and close the socket.
     */
    public void stop() {
        System.out.println("Stop server.");
        this.stop = true;
        this.socket.close();
    }
}

