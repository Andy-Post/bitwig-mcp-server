package com.andypost.mcpbridge;

import com.bitwig.extension.controller.api.ControllerHost;

/**
 * WSHandler stub. Codex: implement a lightweight WebSocket server that exposes:
 *  - GET /api/v1/tracks
 *  - POST /api/v1/select-track { name|index }
 *  - GET /api/v1/devices?track=...
 *  - POST /api/v1/select-device { track, name|index }
 *  - GET /api/v1/params?track=...&device=...
 */
public class WSHandler {
    private final ControllerHost host;
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 7011;

    public WSHandler(ControllerHost host) {
        this.host = host;
    }

    public void start() {
        host.println("WSHandler: starting on ws://" + DEFAULT_HOST + ":" + DEFAULT_PORT);
        // Codex: start WebSocket server and route requests to Bitwig API
    }

    public void stop() {
        host.println("WSHandler: stopping");
        // Codex: stop WebSocket server
    }
}
