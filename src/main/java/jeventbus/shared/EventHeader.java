package jeventbus.shared;

import java.util.ArrayList;
import java.util.List;

public class EventHeader {

    private String name;

    private String value;

    private List<String> values;

    public EventHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public EventHeader addValue(String newValue) {

        if (this.value == null) {
            this.value = newValue;
        }
        else {
            if (values == null) {
                values = new ArrayList();
                values.add(this.value);
                this.value = null;
            }
            values.add(newValue);
        }
        return this;
    }

    public boolean hasValue(String value) {

        if (this.value != null) {
            return this.value.equals(value);
        } else if (values != null) {
            return this.values.contains(value);
        }

        return false;
    }
}
