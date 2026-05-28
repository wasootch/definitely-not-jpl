package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RoverEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(RoverEventPublisher.class);

    private final List<RoverEventListener> listeners = new ArrayList<>();

    public void subscribe(RoverEventListener listener) {
        listeners.add(listener);
    }

    public void publish(RoverEvent event) {
        logger.debug("Publishing event: {}", event);
        for (RoverEventListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                logger.error("Listener threw exception handling event {}: {}", event, e.getMessage(), e);
            }
        }
    }
}
