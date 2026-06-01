package com.tracker;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GlobalHotkeyListener implements NativeKeyListener {

    private final TrackerWebSocketHandler webSocketHandler;
    private boolean ctrlPressed = false;
    private boolean altPressed = false;

    public GlobalHotkeyListener(TrackerWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @PostConstruct
    public void init() {
        try {
            // Disable JNativeHook's verbose logging
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            System.out.println("✅ Global Shortcut Listener Active! Press Ctrl+Alt+S to toggle the timer.");
        } catch (Exception e) {
            System.err.println("Could not register native global keyboard hook.");
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) ctrlPressed = true;
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) altPressed = true;

        // Trigger condition: Ctrl + Alt + S
        if (ctrlPressed && altPressed && e.getKeyCode() == NativeKeyEvent.VC_S) {
            System.out.println("⚡ Shortcut Detected! Sending signal to webpage...");
            webSocketHandler.sendToggleSignal(); 
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) ctrlPressed = false;
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) altPressed = false;
    }
}