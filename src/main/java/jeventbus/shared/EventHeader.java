package jeventbus.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventHeader {

    private String name;

    private Set<String> values = new HashSet<>();

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
        if(values.size() == 0) {
            return null;
        }
        else if(values.size()==1) {
            return values.iterator().next();
        } else {
            return values.stream().collect(Collectors.joining(","));
        }
    }
}
