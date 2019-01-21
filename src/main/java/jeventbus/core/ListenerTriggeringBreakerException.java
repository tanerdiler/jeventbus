package jeventbus.core;

public class ListenerTriggeringBreakerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ListenerTriggeringBreakerException(String message) {
        super(message);
    }

    public ListenerTriggeringBreakerException(String message, Throwable cause) {
        super(message, cause);
    }

    public static final ListenerTriggeringBreakerException aNew(String message) {
        return new ListenerTriggeringBreakerException(message);
    }

    public static final ListenerTriggeringBreakerException aNew(String message, Exception ex) {
        return new ListenerTriggeringBreakerException(message, ex);
    }

}
