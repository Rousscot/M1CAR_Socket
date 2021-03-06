package servers;

import gui.IHM;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * I am an abstract class to not duplicate the code between UDP and TCP.
 * <p>
 * Implementation details:
 * <p>
 * To avoid multiple if or try catch I create a dictionary mapping all possible commands with a lambda expression to execute.
 */
public abstract class AbstractServer {

    protected static final int SOCKET = 8000;
    protected IHM gui;

    /**
     * I am a map that store a lambda to execute for each actions I can handle.
     */
    protected HashMap<String, Function<String, String>> commands;

    /**
     * If I go the false the server will stop.
     */
    protected Boolean stop;

    /**
     * I am a constructor that will launch the IHM, build a map with the different commands I can handle and init the socket.
     *
     * @throws IOException is raise if I cannot open the socket.
     */
    public AbstractServer() throws IOException {
        this.launchGUI();
        this.initCommands();
        this.initSocket();
        this.stop = false;
    }

    /**
     * I launch the IHM.
     */
    public void launchGUI() {
        this.log("Launch GUI");
        gui = new IHM("Ma  borne d’affichage");
        IHM.mettreListenerSortieProgramme(gui);
        gui.setVisible(true);
    }

    /**
     * I build a dictionary with all the actions I manage and a lambda to execute.
     */
    public void initCommands() {
        this.log("Init commands");
        this.commands = new HashMap<>();
        this.commands.put("afficher", (String rest) -> {
            this.gui.ajouterLigne(rest);
            return "Ok : Ordre execute";
        });
        this.commands.put("effacer", (String rest) -> {
            this.gui.raz();
            return "Ok : Ordre execute";
        });
        this.commands.put("error", (String order) -> "ERREUR : Ordre inconnu");
    }

    /**
     * I init the socket.
     *
     * @throws IOException raised if I cannot open the socket.
     */
    public abstract void initSocket() throws IOException;

    /**
     * I launch the server.
     *
     * @throws IOException if there is a problem with a datagram packet.
     */
    public abstract void launch() throws IOException;

    /**
     * I stop the server and close the socket.
     */
    public abstract void stop() throws IOException;

    public void log(String message){
        System.out.println("Server : " + message);
    }

}
