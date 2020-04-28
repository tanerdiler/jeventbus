package jeventbus.streaming;

import jeventbus.shared.EventHeaders;
import jeventbus.shared.EventType;

import java.util.Map;

public class EventMessage {

    private String traceId;

    private String spanId;

    private Long actorId;

    private ActorType actorType;

    private Integer eventId;

    private Long entityId;

    private EventChannel channel;

    private EventReason reason;

    private EventContext context;

    private EventType event;

    private Boolean reportable;

    private Map<String, Object> detail;

    private EventHeaders headers;

    private Long occurredAt;

    public EventMessage() {
        // JUST FOR SERIALIZATION
    }

    public EventMessage(String traceId, String spanId, Long actorId, ActorType actorType, Integer eventId, Long entityId, EventChannel channel, EventReason reason,
                        EventContext context, EventType event, Boolean reportable, Map<String, Object> detail, EventHeaders headers, Long occurredAt) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.actorId = actorId;
        this.actorType = actorType;
        this.eventId = eventId;
        this.entityId = entityId;
        this.channel = channel;
        this.reason = reason;
        this.context = context;
        this.event = event;
        this.reportable = reportable;
        this.detail = detail;
        this.headers = headers;
        this.occurredAt = occurredAt;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public Long getActorId() {
        return actorId;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public EventChannel getChannel() {
        return channel;
    }

    public EventReason getReason() {
        return reason;
    }

    public EventContext getContext() {
        return context;
    }

    public EventType getEvent() {
        return event;
    }

    public Boolean getReportable() {
        return reportable;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public EventHeaders getHeaders() {
        return headers;
    }

    public Long getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "EventMessage{" + "traceId=" + traceId + ", spanId=" + spanId + ", actorId=" + actorId + ", actorType=" + actorType + ", eventId=" + eventId + ", entityId=" + entityId + ", channel="
                + channel + ", reason=" + reason + ", context=" + context + ", event=" + event + ", reportable=" + reportable + ", detail=" + detail
                + ", occurredAt=" + occurredAt + '}';
    }
}
