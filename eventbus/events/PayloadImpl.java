package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.util.Collection;

public class PayloadImpl<T extends Collection<?>> implements Payload<T> {
    private T data;

    public PayloadImpl(T data) {
        this.data = data;
    }

    @Override
    public int getSize() {
        return this.data.size();
    }

    @Override
    public T getPayload() {
        return this.data;
    }
}
