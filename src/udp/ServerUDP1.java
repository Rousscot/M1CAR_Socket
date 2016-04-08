package udp;

import gui.IHM;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * I am a class to answer the question 1 of TP1.
 */
public class ServerUDP1 {

    protected static final int SOCKET = 8000;
    protected static DatagramSocket socket;

    public static void initSocket(){

        try {
            ServerUDP1.socket = new DatagramSocket(SOCKET);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {


        IHM ihm = new IHM("Ma  borne d’affichage");
        IHM.mettreListenerSortieProgramme(ihm);
        ihm.setVisible(true);

        ServerUDP1.initSocket();
    }
}
