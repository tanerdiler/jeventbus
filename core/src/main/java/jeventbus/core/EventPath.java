package jeventbus.core;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import jeventbus.shared.ListenerTriggeringBreakerException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static jeventbus.shared.EventHeaderNames.HEADER_FAILED_LISTENERS;
import static jeventbus.shared.EventHeaderNames.HEADER_STOPPER_LISTENERS;
import static jeventbus.shared.EventHeaderNames.HEADER_SUCCEEDED_LISTENERS;

public class EventPath implements EventPathNode {

    private static final String STOPPING_ON_TRIGGERING_METHODS = "Stopping triggering rest of listeners for %s. Reason : {%s}";

    //private static final Logger logger = LoggerFactory.getLogger(Event.class);

    private List<EventPathNode> listeners = new ArrayList();

    public EventPath add(EventListener listener) {
        listeners.add(EventPathListenerNode.wrap(listener));
        return this;
    }

    public EventPath add(EventPath subPath) {
        listeners.add(subPath);
        return this;
    }

    public void execute(EventSource source, String methodName) {
        for (EventPathNode listener : listeners) {
            if (listener instanceof EventPathListenerNode) {
                String listenerName = ((EventPathListenerNode) listener).getListener().getClass().getName();
                try {

                    if (source.getHeaders().hasValue(HEADER_SUCCEEDED_LISTENERS, listenerName)) {
                        continue;
                    }
                    if (source.getHeaders().hasValue(HEADER_STOPPER_LISTENERS, listenerName)) {
                        break;
                    }
                    listener.execute(source, methodName);

                    source.getHeaders().append(HEADER_SUCCEEDED_LISTENERS, listenerName);

                }
                catch (ListenerTriggeringBreakerException e) {
                    if (Event.LOG_EVENTBRAKER) {
                        //logger.warn(format(STOPPING_ON_TRIGGERING_METHODS, methodName, e.getMessage()));
                    }
                    source.getHeaders().append(HEADER_STOPPER_LISTENERS, listenerName);
                    break;
                }
                catch (Exception ex) {
                    source.getHeaders().append(HEADER_FAILED_LISTENERS, listenerName);
                    throw ex;
                }
            }
            else {
                listener.execute(source, methodName);
            }
        }
    }

    public static EventPath mainPath() {
        return new EventPath();
    }

    public static EventPath subPath() {
        return new EventPath();
    }
}
