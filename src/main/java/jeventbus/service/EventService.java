package jeventbus.service;

import jeventbus.core.EventSource;
import jeventbus.core.Events;
import jeventbus.core.Parameter;

public class EventService {

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
