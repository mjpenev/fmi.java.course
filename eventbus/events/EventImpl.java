package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class EventImpl<T extends Payload<?>> implements Event<T> {
    private Instant timestamp;
    private int priority;
    private String source;
    private T payload;

    public EventImpl(int priority, String source, T payload) {
        this.timestamp = Instant.now();
        this.priority = priority;
        this.source = source;
        this.payload = payload;
    }

    @Override
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public T getPayload() {
        return this.payload;
    }
}
