package jeventbus.core;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import jeventbus.shared.ListenerTriggeringBreakerException;

import java.util.ArrayList;
import java.util.List;


public class EventPath implements EventPathNode {

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
            try {
                listener.execute(source, methodName);
            }
            catch (ListenerTriggeringBreakerException e) {
                break;
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
