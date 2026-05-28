package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoverEventPublisherTest {

    private RoverEventPublisher publisher;
    private List<RoverEvent> received;

    @BeforeEach
    void setUp() {
        publisher = new RoverEventPublisher();
        received  = new ArrayList<>();
        publisher.subscribe(received::add);
    }

    @Test
    void listenerReceivesPublishedEvent() {
        RoverDeployedEvent event = new RoverDeployedEvent("R1", new int[]{0, 0});
        publisher.publish(event);
        assertEquals(1, received.size());
        assertSame(event, received.get(0));
    }

    @Test
    void multipleListenersAllReceiveEvent() {
        List<RoverEvent> second = new ArrayList<>();
        publisher.subscribe(second::add);

        publisher.publish(new RoverDeployedEvent("R1", new int[]{0, 0}));

        assertEquals(1, received.size());
        assertEquals(1, second.size());
    }

    @Test
    void multipleEventsDeliveredInOrder() {
        publisher.publish(new RoverDeployedEvent("R1", new int[]{0, 0}));
        publisher.publish(new RoverMovedEvent("R1", new int[]{0, 0}, new int[]{0, 1}));
        publisher.publish(new RoverBlockedEvent("R1", new int[]{0, 1}, new int[]{0, 6}));

        assertEquals(3, received.size());
        assertInstanceOf(RoverDeployedEvent.class, received.get(0));
        assertInstanceOf(RoverMovedEvent.class,    received.get(1));
        assertInstanceOf(RoverBlockedEvent.class,  received.get(2));
    }

    @Test
    void faultyListenerDoesNotPreventOthersReceiving() {
        publisher.subscribe(e -> { throw new RuntimeException("bad listener"); });
        List<RoverEvent> third = new ArrayList<>();
        publisher.subscribe(third::add);

        publisher.publish(new RoverDeployedEvent("R1", new int[]{0, 0}));

        assertEquals(1, received.size());
        assertEquals(1, third.size());
    }

    @Test
    void noListenersPublishesWithoutError() {
        RoverEventPublisher empty = new RoverEventPublisher();
        assertDoesNotThrow(() -> empty.publish(new RoverDeployedEvent("R1", new int[]{0, 0})));
    }
}
