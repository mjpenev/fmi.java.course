package bg.sofia.uni.fmi.mjt.network.net.simple.client;

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
        try (Socket socket = new Socket(HOST_NAME, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            while (true) {
                System.out.println("Enter message: ");
                String message = sc.nextLine();

                if (message.equals(QUIT_MESSAGE)) {
                    break;
                }

                System.out.println("Sending message < " + message + " > to the server...");
                writer.println(message);

                String reply = reader.readLine();
                System.out.println("Server replied with: " + reply);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem in the network communication.", e);
        }
    }
}
