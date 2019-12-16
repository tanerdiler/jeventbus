package jeventbus.core;

import jeventbus.TestEventType;
import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;
import jeventbus.shared.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventHeaderTest {


    @Test
    public void triggerListenersOnMainPath() {
        final AtomicBoolean exceptionFired = new AtomicBoolean(false);
        final AtomicInteger counter1 = new AtomicInteger(0);
        final AtomicInteger counter2 = new AtomicInteger(0);
        final AtomicInteger counter3 = new AtomicInteger(0);
        EventBuilder eventBuilder = null;


        class Listener1 implements EventListener {
            public void onUserCreated(EventSource source) {
                counter1.incrementAndGet();
            }
        }

        class Listener2 implements EventListener {
            public void onUserCreated(EventSource source) {
                if (exceptionFired.compareAndSet(false,true)) {
                    throw new IllegalStateException();
                }
                counter2.incrementAndGet();
            }
        }

        class Listener3 implements EventListener {
            public void onUserCreated(EventSource source) {
                counter3.incrementAndGet();
            }
        }

        eventBuilder = EventBuilder
                .aNew(TestEventType.USERCREATED)
                .add(new Listener1()).add(new Listener2()).add(new Listener3());


        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        EventSource source = EventSource.aNew(TestEventType.USERCREATED, Parameter.by("name", "tanerdiler"));
        try {
            eventService.fire(TestEventType.USERCREATED, source);
        } catch(Exception ex) {
        }

        assertEquals(1, counter1.get());
        assertEquals(0, counter2.get());
        assertEquals(0, counter3.get());

        try {
            eventService.fire(TestEventType.USERCREATED, source);
        } catch(Exception ex) {
        }

        assertEquals(1, counter1.get());
        assertEquals(1, counter2.get());
        assertEquals(1, counter3.get());
    }

    @Test
    public void triggerListenersOnMainPathV2() {
        final AtomicBoolean exceptionFired = new AtomicBoolean(false);
        final AtomicInteger counter1 = new AtomicInteger(0);
        final AtomicInteger counter2 = new AtomicInteger(0);
        final AtomicInteger counter3 = new AtomicInteger(0);
        EventBuilder eventBuilder = null;


        class Listener1 implements EventListener {
            public void onUserCreated(EventSource source) {
                counter1.incrementAndGet();
            }
        }

        class Listener2 implements EventListener {
            public void onUserCreated(EventSource source) {
                throw new ListenerTriggeringBreakerException("Event Stopped");
            }
        }

        class Listener3 implements EventListener {
            public void onUserCreated(EventSource source) {
                counter3.incrementAndGet();
            }
        }

        eventBuilder = EventBuilder
                .aNew(TestEventType.USERCREATED)
                .add(new Listener1()).add(new Listener2()).add(new Listener3());


        EventService eventService = new EventService();
        eventService.register(eventBuilder);
        EventSource source = EventSource.aNew(TestEventType.USERCREATED, Parameter.by("name", "tanerdiler"));
        eventService.fire(TestEventType.USERCREATED, source);

        assertEquals(1, counter1.get());
        assertEquals(0, counter2.get());
        assertEquals(0, counter3.get());
        source.getHeaders().hasValue(EventHeaderNames.HEADER_STOPPER_LISTENERS, "jeventbus.core.EventHeaderTest$2Listener2");
    }

    @Test
    public void should_add_headers_toString() {
        final AtomicBoolean exceptionFired = new AtomicBoolean(false);
        final AtomicInteger counter1 = new AtomicInteger(0);
        final AtomicInteger counter2 = new AtomicInteger(0);
        final AtomicInteger counter3 = new AtomicInteger(0);
        Events.reset();
        class Listener1 implements EventListener {
            public void onUserCreated(EventSource source) {
                counter1.incrementAndGet();
            }
        }

        class Listener2 implements EventListener {
            public void onUserCreated(EventSource source) {
                if (exceptionFired.compareAndSet(false,true)) {
                    throw new IllegalStateException();
                }
                counter2.incrementAndGet();
            }
        }

        class Listener3 implements EventListener {
            public void onUserCreated(EventSource source) {
                counter3.incrementAndGet();
            }
        }

        Event event = Events.event(TestEventType.USERCREATED)
                            .add(new Listener1()).add(new Listener2()).add(new Listener3());


        EventSource source = EventSource.aNew(TestEventType.USERCREATED, Parameter.by("name", "tanerdiler"));
        try {
            event.fire(source);
        } catch(Exception ex) {
        }

        assertEquals("{ eventType:USERCREATED , "
                             + "params: [{name:name, value:tanerdiler}], "
                             + "headers: [{name:succeeded-listeners, value:jeventbus.core.EventHeaderTest$3Listener1}, "
                             + "{name:failed-listeners, value:jeventbus.core.EventHeaderTest$3Listener2}]}", source.toString());
    }

}
