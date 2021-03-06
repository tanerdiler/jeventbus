package jeventbus.core;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import jeventbus.shared.ListenerTriggeringBreakerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static jeventbus.shared.EventHeaderNames.HEADER_FAILED_LISTENERS;

public class EventPathListenerNode implements EventPathNode {

    private static final String EXCEPTION_ON_TRIGGERING_METHOD = "Exception on triggering %s  method of %s ";

    private EventListener listener;

    private EventPathListenerNode(EventListener listener) {
        this.listener = listener;
    }

    public void execute(EventSource source, String methodName) {
        Class<?> clazz = listener.getClass();
        try {
            Method method = clazz.getDeclaredMethod(methodName, EventSource.class);
            method.setAccessible(true);
            method.invoke(listener, source);
        }
        catch (InvocationTargetException e) {
            if (e.getCause() instanceof ListenerTriggeringBreakerException) {
                throw (ListenerTriggeringBreakerException) e.getCause();
            }
            else {
                throw new RuntimeException(format(EXCEPTION_ON_TRIGGERING_METHOD, methodName, listener.getClass().getName()), e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(format(EXCEPTION_ON_TRIGGERING_METHOD, methodName, listener.getClass().getName()), e);
        }
    }

    public EventListener getListener() {
        return this.listener;
    }

    public static EventPathListenerNode wrap(EventListener listener) {
        return new EventPathListenerNode(listener);
    }

}
