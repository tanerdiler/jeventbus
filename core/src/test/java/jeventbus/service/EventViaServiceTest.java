package jeventbus.service;

import jeventbus.TestEventType;
import jeventbus.core.*;
import jeventbus.service.EventBuilder;
import jeventbus.shared.EventListener;
import jeventbus.service.EventService;
import jeventbus.shared.EventSource;
import jeventbus.shared.ListenerTriggeringBreakerException;
import jeventbus.shared.Parameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventViaServiceTest {

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

    @AfterEach
    public void tearDown() {
        Events.reset();
    }

    @Test
    public void checkListenerTriggered() {
        EventBuilder eventBuilder = EventBuilder.aNew(TestEventType.VISITORLOGON).add(new TestListener());

        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        eventService.fire(TestEventType.VISITORLOGON, Parameter.by("name", "tanerdiler"));

        assertEquals(1, counter.get());
        assertEquals(1, paramCounter.get());
    }

    @Test
    public void triggerListenersOnMainPath() {
        final AtomicInteger counter = new AtomicInteger(0);

        EventBuilder eventBuilder = EventBuilder.aNew(TestEventType.VISITORLOGON)
                                                .add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        });

        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        eventService.fire(TestEventType.VISITORLOGON, Parameter.by("name", "tanerdiler"));

        assertEquals(2, counter.get());
    }

    @Test
    public void triggerSubPathListenersToo() {
        final AtomicInteger counter = new AtomicInteger(0);


        EventBuilder eventBuilder = EventBuilder.aNew(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(EventPath.subPath().add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        })).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {
                            public void onVisitorLogon(EventSource source) {
                                counter.getAndIncrement();
                            }
                        });

        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        eventService.fire(TestEventType.VISITORLOGON, Parameter.by("name", "tanerdiler"));


        assertEquals(4, counter.get());
    }

    @Test
    public void dontEffectMainPathExecutionWhenTriggerBreakerThrownBySubPathNode() {
        final AtomicInteger counter = new AtomicInteger(0);

        class Counter1 implements EventListener {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }

        class Counter2 implements EventListener {
            public void onVisitorLogon(EventSource source) {
                throw new ListenerTriggeringBreakerException("Break subpath triggering");
            }
        }

        class Counter3 implements EventListener {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }

        EventBuilder eventBuilder = EventBuilder.aNew(TestEventType.VISITORLOGON)
                                                .add(new Counter1())
                                                .add(EventPath.subPath().add(new Counter2()))
                                                .add(new Counter3());

        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        eventService.fire(TestEventType.VISITORLOGON, Parameter.by("name", "tanerdiler"));

        assertEquals(2, counter.get());
    }

    @Test
    public void keepContinueOnMainPathWhenTriggerBreakerThrownByMainPathNode() {
        final AtomicInteger counter = new AtomicInteger(0);

        EventBuilder eventBuilder = EventBuilder.aNew(TestEventType.VISITORLOGON).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                throw new ListenerTriggeringBreakerException("Break subpath triggering");
            }
        }).add(EventPath.subPath().add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        })).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        }).add(new EventListener() {
            public void onVisitorLogon(EventSource source) {
                counter.getAndIncrement();
            }
        });

        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        eventService.fire(TestEventType.VISITORLOGON, Parameter.by("name", "tanerdiler"));

        assertEquals(0, counter.get());
    }

    @Test
    public void breakTheEventTriggeringAfterRuntimeExceptionThrown() {
        assertThrows(RuntimeException.class, ()->{final AtomicInteger counter = new AtomicInteger(0);

            EventBuilder eventBuilder = EventBuilder.aNew(TestEventType.VISITORLOGON).add(new EventListener() {
                public void onVisitorLogon(EventSource source) {
                    counter.getAndIncrement();
                }
            }).add(EventPath.subPath().add(new EventListener() {
                public void onVisitorLogon(EventSource source) {
                    counter.getAndIncrement();
                }
            })).add(new EventListener() {

            }).add(new EventListener() {
                public void onVisitorLogon(EventSource source) {
                    counter.getAndIncrement();
                }
            });

            EventService eventService = new EventService();
            eventService.register(eventBuilder);
            eventService.fire(TestEventType.VISITORLOGON, Parameter.by("name", "tanerdiler"));});
    }

}
