package jeventbus.shared;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class EventHeader implements Serializable {

    private String name;

    private Set<String> values = new HashSet<>();

    private EventHeader() {
        // JUST FOR COPY
    }

    public EventHeader(String name, String value) {
        this.name = name;
        values.add(value);
    }

    public String getName() {
        return this.name;
    }

    public EventHeader addValue(String newValue) {
        values.add(newValue);
        return this;
    }

    public boolean hasValue(String value) {
        return values.contains(value);
    }

    @Override
    public String toString() {
        if (values.size() == 0) {
            return null;
        }
        else if (values.size() == 1) {
            return values.iterator().next();
        }
        else {
            return values.stream().collect(Collectors.joining(","));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EventHeader that = (EventHeader) o;
        return Objects.equals(name, that.name) && Objects.deepEquals(values, that.values);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, values);
    }

    public EventHeader copy() {
        EventHeader header = new EventHeader();
        if (nonNull(values)) {
            header.values = new HashSet<>(values);
        }
        header.name = this.name;
        return header;
    }

    public String getSingleValue() {
        Iterator<String> iterator = values.iterator();
        if (values.iterator().hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
