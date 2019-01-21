package jeventbus.service;

import jeventbus.core.Event;
import jeventbus.core.EventPath;
import jeventbus.core.Events;

public class EventBuilder {

    public final EventType type;

    private EventPath mainPath = EventPath.mainPath();

    public static EventBuilder aNew(EventType eventType) {
        return new EventBuilder(eventType);
    }

    private EventBuilder(EventType event) {
        this.type = event;
    }

    public EventBuilder add(EventListener listener) {
        mainPath.add(listener);
        return this;
    }

    public EventBuilder add(EventPath subPath) {
        mainPath.add(subPath);
        return this;
    }

    public void buildAndRegister() {
        Events.put(new Event(type, mainPath));
    }

}
