package bg.sofia.uni.fmi.mjt.network.nio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class EchoClientNio {
    private static final int PORT = 1313;
    private static final String SC_NAME = "UTF-8";
    private static final String QUIT_MESSAGE = "quit";
    private static final String HOST_NAME = "localhost";

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, SC_NAME));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, SC_NAME), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(HOST_NAME, PORT));

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine(); // read a line from the console

                if (message.equals(QUIT_MESSAGE)) {
                    break;
                }

                System.out.println("Sending message <" + message + "> to the server...");

                writer.println(message);

                String reply = reader.readLine(); // read the response from the server
                System.out.println("The server replied <" + reply + ">");
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }
}
