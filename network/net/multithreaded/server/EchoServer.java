package bg.sofia.uni.fmi.mjt.network.net.multithreaded.server;

import bg.sofia.uni.fmi.mjt.network.net.multithreaded.client.clientRequestHandler.ClientRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private static final int PORT = 1313;
    private static final int MAX_EXECUTOR_THREADS = 10;

    public static void main(String[] args) {
        Thread.currentThread().setName("Echo Server Thread");

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS)) {
            System.out.println("Server started listening to executor threads.");
            Socket clientSocket;

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted connection request from client " + clientSocket);

                ClientRequestHandler clientRequestHandler = new ClientRequestHandler(clientSocket);
                executor.execute(clientRequestHandler);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket.");
        }

    }
}
