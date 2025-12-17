package bg.sofia.uni.fmi.mjt.poll.server;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public class PollServer {
    private static final int BUFFER_SIZE = 1024;
    private static final String HOST_NAME = "localhost";

    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    private final int port;
    private final PollRepository pollRepository;

    public PollServer(int port, PollRepository pollRepository) {
        this.port = port;
        this.pollRepository = pollRepository;
    }

    public void start() {

    }

    public void stop() {

    }
}
