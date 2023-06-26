package com.test.test.listener.websocketListener;

import com.test.test.controller.WebSocketController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Log4j2
public class WebSocketListener extends TextWebSocketHandler implements WebSocketHandler {

    private static final Queue<WebSocketSession> webSocketSessionList = new ConcurrentLinkedQueue<WebSocketSession>();

    public synchronized void sendMessageToAllSessions(String message) {
        webSocketSessionList.forEach(session -> {
            try {
                log.info("Sending message to session: " + session.getId());
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Connected to websocket");
        webSocketSessionList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received: " + message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed to websocket: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        webSocketSessionList.remove(session);
        super.afterConnectionClosed(session, closeStatus);
    }
}
