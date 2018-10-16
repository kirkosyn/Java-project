import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Klasa opisująca uruchomienie serwera
 */

public class Server
{
    /**
     * port serwera
     */
    private int serverPort;

    /**
     * metoda uruchamiająca serwer
     */
    public void runServer() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("ConfigFiles\\port.txt"));
            serverPort = Integer.parseInt(br.readLine());
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is running.");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (Exception e) {
            System.out.println("Server couldn't be created.");
            System.out.println(e);
        }

    }
}