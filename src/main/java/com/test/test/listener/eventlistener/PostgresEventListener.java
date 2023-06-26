package com.test.test.listener.eventlistener;

import com.test.test.applicationevent.PostgresEvent;
import com.test.test.listener.websocketListener.WebSocketListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PostgresEventListener{

    @Autowired
    WebSocketListener webSocketListener;

    @EventListener
    public void handlePostgresEvent(PostgresEvent event) {
        log.info("I handle postgres event: " + event.getMessage());
        webSocketListener.sendMessageToAllSessions(event.getMessage());
    }

}
