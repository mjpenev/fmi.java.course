package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.*;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {

    private Set<T> events;

    public DeferredEventSubscriber(Set<T> events) {
        this.events = events;
    }

    @Override
    public void onEvent(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        this.events.add(event);
    }

    @Override
    public Iterator<T> iterator() {
        Set<T> sortedEvents = new TreeSet<>(new EventPriorityComparator().reversed());
        sortedEvents.addAll(this.events);
        return Collections.unmodifiableCollection(sortedEvents).iterator();
    }

    public boolean isEmpty() {
        return this.events.isEmpty();
    }

}