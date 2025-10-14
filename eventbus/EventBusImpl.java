package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.*;

public class EventBusImpl implements EventBus {
    private final Map<Class<? extends Event<?>>, Set<Subscriber<?>>> eventBus = new HashMap<>();
    private final Map<Class<? extends Event<?>>, List<Event<?>>> eventLogs = new HashMap<>();

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("EventType is null.");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber is null.");
        }

        this.eventBus.computeIfAbsent(eventType, k -> new HashSet<>()).add(subscriber);
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber) throws MissingSubscriptionException {
        if (eventType == null || subscriber == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        Set<Subscriber<?>> subs = eventBus.get(eventType);
        if (subs == null || !subs.remove(subscriber)) {
            throw new MissingSubscriptionException(String.format("Subscriber not found for event type: %s", eventType));
        }
        if (subs.isEmpty()) {
            eventBus.remove(eventType);
        }
    }

    @Override
    public <T extends Event<?>> void publish(T event) {

    }

    @Override
    public void clear() {
        eventBus.clear();
        eventLogs.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null || from == null || to == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Period is not legal.");
        }
        if (this.eventLogs.get(eventType).isEmpty()) {
            return List.of();
        }
        Set<Event<?>> result = new HashSet<>();
        for (Event<?> entry : this.eventLogs.get(eventType)) {
            if (entry.getTimestamp().isBefore(to) && entry.getTimestamp().isAfter(from)) {
                result.add(entry);
            }
        }
        return Collections.unmodifiableCollection(result);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (this.eventBus.get(eventType) == null) {
            return List.of();
        }
        return Collections.unmodifiableCollection(this.eventBus.get(eventType));
    }
}
