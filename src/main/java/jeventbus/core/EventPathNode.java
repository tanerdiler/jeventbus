package jeventbus.core;

public interface EventPathNode {

    public void execute(EventSource source, String methodName);
}

