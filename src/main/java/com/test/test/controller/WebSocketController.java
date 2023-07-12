package com.test.test.controller;

import com.test.test.listener.websocketListener.WebSocketListener;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
@Log4j2
@Tag(name = "WebSocket")
public class WebSocketController {

    @Autowired
    WebSocketListener webSocketListener;

    @Operation(summary = "Отправка сообщения всем сокетам",
            description = "Все связанные веб сокеты получат сообщение из RequestParam.")
    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message) {
        webSocketListener.sendMessageToAllSessions(message);
        log.info("Sent message to all sessions: " + message);
    }
}
