package jeventbus.streaming;

import java.util.Optional;

import static java.lang.String.format;

public interface EventChannel<M> {

    M fromName(String name);

    String name();

    default Optional<M> parse(String name) throws UnknownEnumTypeException{
        Optional<M> type = Optional.empty();
        try {
            type = Optional.ofNullable(fromName(name));
        }
        catch (IllegalStateException ex) {
            throw new UnknownEnumTypeException(format("Unknown enum {%s} for EventChannel", name), ex);
        }
        return type;
    }
}
