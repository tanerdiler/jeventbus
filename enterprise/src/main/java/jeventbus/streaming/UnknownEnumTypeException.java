package jeventbus.streaming;

public class UnknownEnumTypeException extends Exception {

    public UnknownEnumTypeException(String name, Throwable ex) {
        super(name, ex);
    }
}
