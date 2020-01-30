package jeventbus.streaming;

import jeventbus.shared.EventType;

public class UnknownEventException extends RuntimeException {

    public UnknownEventException(Integer id) {
        super("Event with id{"+id+"} is unknown. Be sure EventType is synched with DB.");
    }

    public UnknownEventException(EventType eventType) {
        super("Event with type{"+eventType.name()+"} is unknown. Be sure EventType is synched with DB.");
    }
}
