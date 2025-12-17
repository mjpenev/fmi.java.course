package bg.sofia.uni.fmi.mjt.network.net.multithreaded.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    private static final int PORT = 1313;
    private static final String HOST_NAME = "localhost";
    private static final String QUIT_MESSAGE = "quit";

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(HOST_NAME, PORT);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            Thread.currentThread().setName("Echo client thread " + clientSocket.getLocalPort());

            System.out.println("Connected to the server.");

            while (true) {
                System.out.println("Enter message: ");
                String message = sc.nextLine();

                if (message.equals(QUIT_MESSAGE)) {
                    break;
                }

                System.out.println("Sending message < " + message + " > to the server");
                writer.println(message);

                String reply = reader.readLine();
                System.out.println("The server replied < " + reply + " >");
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication.", e);
        }
    }
}

