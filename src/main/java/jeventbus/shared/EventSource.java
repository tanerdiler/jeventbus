package jeventbus.shared;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventSource implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Parameter> params = new HashMap<>();

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

    public EventSource clone() {
        Map<String, Parameter> newParams = new HashMap<>(params);
        EventSource newEventSource = new EventSource(type, newParams.values());
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
        strb.append("]}");
        return strb.toString();
    }
}
