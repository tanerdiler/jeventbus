package jeventbus.core;

import jeventbus.TestEventType;
import jeventbus.shared.EventHeader;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jeventbus.TestEventType.USERCREATED;
import static jeventbus.shared.EventHeaderNames.HEADER_SPAN_ID;
import static jeventbus.shared.EventHeaderNames.HEADER_TRACE_ID;
import static jeventbus.shared.Parameter.by;
import static org.junit.jupiter.api.Assertions.*;

public class TracingTest {

    @Test
    public void should_put_traceid_and_spanid() {
        Events.reset();
        class TracingController implements EventListener {
            public void onVisitorLogon(EventSource source) {
                Optional<EventHeader> traceHeader = source.getHeaders().get(HEADER_TRACE_ID);
                Optional<EventHeader> spanHeader = source.getHeaders().get(HEADER_SPAN_ID);

                assertTrue(traceHeader.isPresent());
                assertTrue(spanHeader.isPresent());
            }
        }

        Events.event(TestEventType.VISITORLOGON)
              .add(new TracingController())
              .fire(by("name", "tanerdiler"));

    }

    @Test
    public void should_save_traceid_but_not_spanid_on_new_event_fired() {
        Events.reset();
        List<EventHeader> list = new ArrayList<>();

        class UserCreatedContoller implements EventListener {
            public void onUserCreated(EventSource source) {
                list.add(source.getHeaders().get(HEADER_TRACE_ID).get());
                list.add(source.getHeaders().get(HEADER_SPAN_ID).get());
            }
        }

        class VisitorLogonController implements EventListener {
            public void onVisitorLogon(EventSource source) {
                list.add(source.getHeaders().get(HEADER_TRACE_ID).get());
                list.add(source.getHeaders().get(HEADER_SPAN_ID).get());
                Events.event(USERCREATED).add(new UserCreatedContoller()).fire(source.clone(USERCREATED));
            }
        }

        Events.event(TestEventType.VISITORLOGON)
              .add(new VisitorLogonController())
              .fire(by("name", "tanerdiler"));

        assertEquals(list.get(0), list.get(2));
        assertNotEquals(list.get(1), list.get(3));
    }

}
