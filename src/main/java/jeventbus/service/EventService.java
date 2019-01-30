package jeventbus.service;

import jeventbus.shared.EventSource;
import jeventbus.core.Events;
import jeventbus.shared.EventType;
import jeventbus.shared.Parameter;

public class EventService {

    public static EventService get() {
        return new EventService();
    }

    public void register(EventBuilder eventBuilder) {
        eventBuilder.buildAndRegister();
    }

    public EventSource fire(EventType eventType, EventSource source) {
        Events.get(eventType).fire(source);
        return source;
    }

    public EventSource fire(EventType eventType, Parameter... parameters) {
        EventSource source = EventSource.aNew(eventType, parameters);
        Events.get(eventType).fire(source);
        return source;
    }
}
