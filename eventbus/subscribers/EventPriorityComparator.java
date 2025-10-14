package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.Comparator;
import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;

public class EventPriorityComparator implements Comparator<Event<?>> {
    @Override
    public int compare(Event<?> e1, Event<?> e2) {
        if (e1 == null || e2 == null) {
            throw new IllegalArgumentException("At least one of the events is null.");
        }

        int priorityComparison = Integer.compare(e1.getPriority(), e2.getPriority());
        if (priorityComparison != 0) {
            return priorityComparison;
        }

        return e1.getTimestamp().compareTo(e2.getTimestamp());
    }

}
