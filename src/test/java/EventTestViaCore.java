import jeventbus.core.*;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import jeventbus.shared.ListenerTriggeringBreakerException;
import jeventbus.shared.Parameter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class EventTestViaCore {

    private static final AtomicInteger counter = new AtomicInteger();

    private static final AtomicInteger paramCounter = new AtomicInteger();

    public static class TestListener implements EventListener {

        private static final long serialVersionUID = 1L;

        public void onVisitorLogon(EventSource source) {
            counter.getAndIncrement();
            if (((String) source.get("name")).equals("tanerdiler")) {
                paramCounter.getAndIncrement();
            }
        }

    }

    @After
    public void tearDown() {
        Events.reset();
    }

    @Test
    public void checkListenerTriggered() {
        Events.event(TestEventType.VISITORLOGON).add(new TestListener()).fire(Parameter.by("name", "tanerdiler"));
        Assert.assertEquals(1, counter.get());
        Assert.assertEquals(1, paramCounter.get());
    }

    @Test
    public void triggerListenersOnMainPath() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).fire(Parameter.by("name", "tanerdiler"));
        Assert.assertEquals(2, counter.get());
    }

    @Test
    public void triggerSubPathListenersToo() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(EventPath.subPath().add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        })).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).fire(Parameter.by("name", "tanerdiler"));

        Assert.assertEquals(4, counter.get());
    }

    @Test
    public void dontEffectMainPathExecutionWhenTriggerBreakerThrownBySubPathNode() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(EventPath.subPath().add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                throw new ListenerTriggeringBreakerException("Break subpath triggering");
            }
        }).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        })).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).fire(Parameter.by("name", "tanerdiler"));

        Assert.assertEquals(2, counter.get());
    }

    @Test
    public void keepContinueOnMainPathWhenTriggerBreakerThrownByMainPathNode() {
        final AtomicInteger counter = new AtomicInteger(0);

        Events.event(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                throw new ListenerTriggeringBreakerException("Break subpath triggering");
            }
        }).add(EventPath.subPath().add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        })).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).fire(Parameter.by("name", "tanerdiler"));

        Assert.assertEquals(0, counter.get());
    }

    @Test(expected = RuntimeException.class)
    public void breakTheEventTriggeringAfterRuntimeExceptionThrown() {
        final AtomicInteger counter = new AtomicInteger(0);
        Events.event(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(EventPath.subPath().add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {

        })).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).fire(Parameter.by("name", "tanerdiler"));
    }

}
