package tcp;

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
 * Created by Cyril on 10/04/2016.
 */
public class ServerTCP {


    protected static final int SOCKET = 8000;
    protected ServerSocket socket;
    protected Socket connectionSocket;
    protected IHM gui;

    /**
     * I am a map that store a lambda to execute for each actions I can handle.
     */
    protected HashMap<String, BiFunction<String, IHM, String>> commands;


    /**
     * If I go the false the server will stop.
     */
    protected Boolean stop;

    public ServerTCP() throws IOException {
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

    public void initSocket() throws IOException {
        System.out.println("Open socket");
        this.socket = new ServerSocket(SOCKET);
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

    public void launch() throws IOException {
        while (!this.stop) {
            System.out.println("Open connection");
            this.connectionSocket = this.socket.accept();

            System.out.println("Get data");
            String[] receivedMessages = (new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()))).readLine().split(" ", 2);

            System.out.println("Execute action");
            String rest;
            try {
                rest = receivedMessages[1];
            } catch (IndexOutOfBoundsException e) {
                rest = "";
            }

            String result = commands.getOrDefault(receivedMessages[0], (String order, IHM ihm) -> "ERREUR : Ordre inconnu").apply(rest, gui);

            System.out.println("Send result");
            (new DataOutputStream(connectionSocket.getOutputStream())).writeBytes(result);

            System.out.println("Close socket");
            connectionSocket.close();
        }
    }

    /**
     * I stop the server and close the socket.
     */
    public void stop() throws IOException {
        System.out.println("Stop server.");
        this.stop = true;
        this.socket.close();
    }

}
