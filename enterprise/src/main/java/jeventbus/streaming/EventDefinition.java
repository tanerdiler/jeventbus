package jeventbus.streaming;

import jeventbus.shared.EventType;

public interface EventDefinition {

    Integer getId();

    EventContext getContext();

    Boolean getReportable();

    EventType getEventType();

}
