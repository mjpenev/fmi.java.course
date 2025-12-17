package bg.sofia.uni.fmi.mjt.network.net.simple.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static final int PORT = 1313;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started listening for connect request on port: " + PORT);

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Message received from client: " + inputLine);
                    pw.println("Echo " + inputLine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem in the server socket", e);
        }
    }


}
