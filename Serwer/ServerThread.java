

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    /**
     * Socket
     */
    private Socket socket;

    /**
     * Konstruktor klasy
     *
     * @param socket socket
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Metoda obs³uguj¹ca wszystkie zdarzenia pomiedzy klientem i serwerem
     */

    @Override
    public void run() {
        try {
            while (true) {
                InputStream is = socket.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                OutputStream os = socket.getOutputStream();

                PrintWriter pw = new PrintWriter(os, true);
                String fromClient = br.readLine();

                if (fromClient != null) {
                    System.out.println("MessageFromClient " + fromClient);

                    String serverMessage = ServerCommands.serverAction(fromClient);
                    if (serverMessage == "CloseConnectionNow") {
                        socket.close();
                    }
                    if (serverMessage == "ConnectionRejected")
                        socket.close();
                    else {
                        pw.println(serverMessage);
                        pw.flush();
                        System.out.println("MessageForClient " + serverMessage);
                        if (serverMessage == "LoggedOut") {
                            socket.close();
                        }
                    }
                }
            }
        } catch (Exception er) {
            System.out.println("Server has stopped due to an error.");
            System.out.println(er);
        }
    }
}