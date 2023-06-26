package com.test.test.publisher.eventpublisher;

import com.test.test.applicationevent.PostgresEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PostgresEventPublisher {
    private final ApplicationEventPublisher publisher;

    public PostgresEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(String message) {
        publisher.publishEvent(new PostgresEvent(this, message));
    }
}
