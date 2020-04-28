package jeventbus.core;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import jeventbus.shared.EventType;
import jeventbus.shared.Parameter;

public class Event {

    public static boolean LOG_EVENTBRAKER = false;

    public final EventType type;

    private EventPath mainPath = EventPath.mainPath();

    public static Event aNew(EventType eventType) {
        return new Event(eventType);
    }

    public Event(EventType event) {
        this.type = event;
    }

    public Event(EventType event, EventPath mainPath) {
        this.type = event;
        this.mainPath = mainPath;
    }

    public Event add(EventListener listener) {
        mainPath.add(listener);
        return this;
    }

    public Event add(EventPath subPath) {
        mainPath.add(subPath);
        return this;
    }

    public EventSource fire(EventSource source) {
        source.generateSpanIdIfAbsent();
        mainPath.execute(source, type.getMethodName());
        return source;
    }

    public EventSource fire(Parameter... parameters) {
        EventSource source = EventSource.aNew(type, parameters);
        fire(source);
        return source;
    }
}
