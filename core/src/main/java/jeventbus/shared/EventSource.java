package jeventbus.shared;

import jeventbus.core.SpanId;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static jeventbus.shared.EventHeaderNames.HEADER_SPAN_ID;
import static jeventbus.shared.EventHeaderNames.HEADER_TRACE_ID;
import static jeventbus.shared.Parameter.by;

public class EventSource implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Parameter> params = new HashMap<>();

    private final EventHeaders headers = new EventHeaders();

    private final EventType type;

    private EventSource(EventType type, Parameter... parameters) {
        this.type = type;
        for (Parameter param : parameters) {
            params.put(param.name, param);
        }
    }

    private EventSource(EventType type, Collection<Parameter> parameters) {
        this.type = type;
        for (Parameter param : parameters) {
            params.put(param.name, param);
        }
    }

    public Object get(String key) {
        Parameter param = params.get(key);
        if (Objects.nonNull(param)) {
            return param.value;
        }
        return null;
    }

    public EventType eventType() {
        return type;
    }

    public String eventName() {
        return type.name();
    }

    public static final EventSource aNew(EventType type, Parameter... parameters) {
        return new EventSource(type, parameters);
    }

    public Map<String, Object> getMap() {
        return params.values().stream().filter(p -> p != null && p.value != null).collect(toMap(p -> p.name, p -> p.value));
    }

    public EventSource clone(EventType eventType) {
        Map<String, Parameter> newParams = new HashMap<>(params);

        EventSource newEventSource = new EventSource(eventType, newParams.values());

        // Copy header of trace id
        Optional<EventHeader> traceId = headers.get(HEADER_TRACE_ID);
        if (traceId.isPresent()){
            newEventSource.addHeader(traceId.get().copy());
        }

        return newEventSource;
    }

    public EventSource add(Parameter parameter) {
        params.put(parameter.name, parameter);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("{ eventType:%s , params: [");
        strb.append(params.values().stream().map(p -> p.toString()).collect(Collectors.joining(", ")));
        strb.append("], headers: %s}");
        return format(strb.toString(), type, headers);
    }

    public EventHeaders getHeaders() {
        return headers;
    }

    public void addHeader(EventHeader value) {
        headers.addHeader(value);
    }

    public void generateSpanIdIfAbsent() {

        if (!headers.get(HEADER_TRACE_ID).isPresent()){
            headers.addHeader(new EventHeader(HEADER_TRACE_ID, SpanId.generate()));
        }

        headers.addHeader(new EventHeader(HEADER_SPAN_ID, SpanId.generate()));
    }
}
