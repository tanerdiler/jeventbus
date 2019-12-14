package jeventbus.core;

import jeventbus.shared.EventSource;

public interface EventPathNode {

    void execute(EventSource source, String methodName);
}

