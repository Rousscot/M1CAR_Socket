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
 */
public class ServerUDP {

    protected static final int SOCKET = 8000;
    protected static final int SIZEPAQUET = 512;
    protected IHM gui;
    protected DatagramSocket socket;
    protected HashMap<String, BiFunction<String, IHM, String>> commands;
    protected Boolean stop;

    public ServerUDP() throws SocketException {
        this.launchGUI();
        this.commands = this.initCommands();
        this.initSocket();
        this.stop = false;
    }

    public void launchGUI() {
        gui = new IHM("Ma  borne d’affichage");
        IHM.mettreListenerSortieProgramme(gui);
        gui.setVisible(true);
    }

    public void initSocket() throws SocketException {
        this.socket = new DatagramSocket(SOCKET);
    }

    public HashMap<String, BiFunction<String, IHM, String>> initCommands() {
        HashMap<String, BiFunction<String, IHM, String>> map = new HashMap<>();
        map.put("afficher", (String rest, IHM ihm) -> {
            ihm.ajouterLigne(rest);
            return "Ok : Ordre execute";
        });
        map.put("effacer", (String rest, IHM ihm) -> {
            ihm.raz();
            return "Ok : Ordre execute";
        });
        return map;
    }

    public void launch() throws IOException {

        while (!this.stop) {
            DatagramPacket paquet = new DatagramPacket(new byte[SIZEPAQUET], SIZEPAQUET);
            socket.receive(paquet);

            byte[] array = paquet.getData();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < paquet.getLength(); i++) {
                builder.append((char) array[i]);
            }

            String[] strings = builder.toString().split(" ", 2);
            String result = commands.getOrDefault(strings[0], (String order, IHM ihm) -> "ERREUR : Ordre inconnu").apply(strings[1], gui);

            (new DatagramSocket()).send(new DatagramPacket(result.getBytes(), result.length(), paquet.getAddress(), paquet.getPort()));

        }
    }

    public void stop(){
        this.stop = true;
        this.socket.close();
    }
}

