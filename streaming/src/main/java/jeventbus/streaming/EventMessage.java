package jeventbus.streaming;

import jeventbus.shared.EventType;

import java.util.Map;

public class EventMessage {

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

    private Long occurredAt;

    public EventMessage(Long actorId, ActorType actorType, Integer eventId, Long entityId, EventChannel channel, EventReason reason,
                        EventContext context, EventType event, Boolean reportable, Map<String, Object> detail, Long occurredAt) {
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
        this.occurredAt = occurredAt;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public void setActorType(ActorType actorType) {
        this.actorType = actorType;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EventChannel getChannel() {
        return channel;
    }

    public void setChannel(EventChannel channel) {
        this.channel = channel;
    }

    public EventReason getReason() {
        return reason;
    }

    public void setReason(EventReason reason) {
        this.reason = reason;
    }

    public EventContext getContext() {
        return context;
    }

    public void setContext(EventContext context) {
        this.context = context;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public Boolean getReportable() {
        return reportable;
    }

    public void setReportable(Boolean reportable) {
        this.reportable = reportable;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    public Long getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Long occurredAt) {
        this.occurredAt = occurredAt;
    }

    @Override
    public String toString() {
        return "EventMessage{" + "actorId=" + actorId + ", actorType=" + actorType + ", eventId=" + eventId + ", entityId=" + entityId + ", channel="
                + channel + ", reason=" + reason + ", context=" + context + ", event=" + event + ", reportable=" + reportable + ", detail=" + detail
                + ", occurredAt=" + occurredAt + '}';
    }
}
