package com.tracker;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TrackerWebSocketHandler extends TextWebSocketHandler {
    
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Webpage Connected to Java Tracker!");
    }

    public void sendToggleSignal() {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage("toggle"));
                } catch (IOException e) {
                    System.err.println("Failed to send toggle signal.");
                }
            }
        }
    }
}