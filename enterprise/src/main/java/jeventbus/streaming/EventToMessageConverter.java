package jeventbus.streaming;



import jeventbus.shared.EventHeader;
import jeventbus.shared.EventSource;
import jeventbus.shared.EventType;

import java.util.Map;

import static jeventbus.shared.EventHeaderNames.HEADER_SPAN_ID;
import static jeventbus.shared.EventHeaderNames.HEADER_TRACE_ID;
import static jeventbus.streaming.EventSourceKeys.*;
import static jeventbus.shared.Parameter.by;

public class EventToMessageConverter {

    public static EventMessage convert(EventType event, EventSource source, EventDefinition definition) throws UnknownEventException {
        // @formatter:off
        return new EventMessage(
                                source.getHeaders().get(HEADER_TRACE_ID).map(h->h.getSingleValue()).orElse(null),
                                source.getHeaders().get(HEADER_SPAN_ID).map(h->h.getSingleValue()).orElse(null),
                                (Long) source.get(ACTOR_ID),
                                (ActorType) source.get(ACTOR_TYPE),
                                definition.getId(),
                                (Long) source.get(ENTITY_ID),
                                (EventChannel) source.get(EVENT_CHANNEL),
                                (EventReason) source.get(EVENT_REASON),
                                definition.getContext(),
                                definition.getEventType(),
                                definition.getReportable(),
                                source.getMap(),
                                source.getHeaders(),
                                (Long) source.get(OCCURRED_AT));
        // @formatter:on
    }

    public static EventSource convert(EventMessage message) throws UnknownEventException {
        // @formatter:off
        EventSource eventSource = EventSource.aNew(message.getEvent(),
                         by(ACTOR_ID, message.getActorId()),
                         by(ACTOR_TYPE, message.getActorType()),
                         by(ENTITY_ID, message.getEntityId()),
                         by(EVENT_CHANNEL, message.getChannel()),
                         by(EVENT_REASON, message.getReason()),
                         by(EVENT_CONTEXT, message.getContext()),
                         by(EVENT_REPORTABLE, message.getReportable()),
                         by(OCCURRED_AT, message.getOccurredAt()));
        // @formatter:on
        for (Map.Entry<String, Object> entry : message.getDetail().entrySet()) {
            eventSource.add(by(entry.getKey(), entry.getValue()));
        }

        message.getHeaders().stream().forEach(e->eventSource.addHeader(e.getValue()));

        return eventSource;
    }
}
