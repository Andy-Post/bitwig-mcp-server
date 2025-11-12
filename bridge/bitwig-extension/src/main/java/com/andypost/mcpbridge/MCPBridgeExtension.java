package com.andypost.mcpbridge;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;

/**
 * Minimal skeleton for Codex.
 * Codex: wire WebSocket server startup/shutdown here and expose API to select tracks/devices.
 */
public class MCPBridgeExtension extends ControllerExtension {
    private ControllerHost host;
    private WSHandler ws;

    protected MCPBridgeExtension() {
        super(null); // Codex: replace with actual controller definition if needed.
    }

    @Override
    public void init() {
        this.host = getHost();
        this.host.println("MCPBridgeExtension: init");
        // Codex: initialize WS server (default ws://127.0.0.1:7011)
        this.ws = new WSHandler(this.host);
        this.ws.start();
    }

    @Override
    public void exit() {
        this.host.println("MCPBridgeExtension: exit");
        if (this.ws != null) {
            this.ws.stop();
        }
    }

    @Override
    public void flush() {
        // Codex: push any pending state if needed
    }
}
