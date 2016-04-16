package servers;

import gui.IHM;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiFunction;

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
    protected HashMap<String, BiFunction<String, IHM, String>> commands;

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
        System.out.println("Launch GUI");
        gui = new IHM("Ma  borne d’affichage");
        IHM.mettreListenerSortieProgramme(gui);
        gui.setVisible(true);
    }

    /**
     * I build a dictionary with all the actions I manage and a lambda to execute.
     */
    public void initCommands() {
        System.out.println("Init commands");
        this.commands = new HashMap<>();
        this.commands.put("afficher", (String rest, IHM ihm) -> {
            ihm.ajouterLigne(rest);
            return "Ok : Ordre execute";
        });
        this.commands.put("effacer", (String rest, IHM ihm) -> {
            ihm.raz();
            return "Ok : Ordre execute";
        });
        this.commands.put("error", (String order, IHM ihm) -> "ERREUR : Ordre inconnu");
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

}
