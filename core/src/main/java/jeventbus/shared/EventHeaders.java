package jeventbus.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public class EventHeaders implements Serializable {

    private Map<String, EventHeader> headers = new HashMap<>();

    public EventHeaders(){

    }

    public EventHeaders(Map<String, EventHeader> headers) {
        this.headers = headers;
    }

    public void overwrite(String name, String value) {

        headers.put(name, new EventHeader(name, value));
    }

    public Stream<Map.Entry<String, EventHeader>> stream() {
        return headers.entrySet().stream();
    }

    public void append(String name, String value) {
        EventHeader header = headers.get(name);
        if (isNull(header)) {
            headers.put(name, new EventHeader(name, value));
        }
        else {
            header.addValue(value);
        }

    }

    public void addHeader(EventHeader header) {
        headers.put(header.getName(), header);
    }

    public boolean hasValue(String name, String value) {
        return headers.get(name)!=null && headers.get(name).hasValue(value);
    }

    public Map<String, EventHeader> getHeaders() {
        return headers;
    }

    public Optional<EventHeader> get(String name) {
        return ofNullable(headers.get(name));
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder("[");
        strb.append(headers.entrySet().stream().map(e -> String.format("{name:%s, value:%s}", e.getKey(), e.getValue())).collect(Collectors.joining(", ")));
        return strb.append("]").toString();
    }
}

