package com.tracker;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TrackerWebSocketHandler trackerWebSocketHandler;

    public WebSocketConfig(TrackerWebSocketHandler trackerWebSocketHandler) {
        this.trackerWebSocketHandler = trackerWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Exposes the connection at ws://localhost:8080/shortcut
        registry.addHandler(trackerWebSocketHandler, "/shortcut").setAllowedOrigins("*");
    }
}