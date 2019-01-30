package jeventbus.shared;

import java.io.Serializable;

public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;

    public final String name;

    public final Object value;

    private Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public static final <T> Parameter by(String name, Object value) {
        return new Parameter(name, value);
    }
}
