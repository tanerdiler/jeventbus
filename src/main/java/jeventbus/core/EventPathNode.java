package jeventbus.core;

import jeventbus.shared.EventSource;

public interface EventPathNode {

    public void execute(EventSource source, String methodName);
}

