package jeventbus.shared;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class EventHeaders {

    private Map<String, EventHeader> headers = new HashMap<>();

    public void overwrite(String name, String value) {

        headers.put(name, new EventHeader(name, value));
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

    public boolean hasValue(String name, String value) {
        return headers.get(name)!=null && headers.get(name).hasValue(value);
    }
}
