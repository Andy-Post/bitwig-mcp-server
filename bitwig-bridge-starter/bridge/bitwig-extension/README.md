# MCP Bridge Extension (Starter)

Minimaler Startpunkt für Sprint 2 (Bitwig Bridge).
Codex erweitert diese Struktur zu einer lauffähigen .bwextension.

## Build
```bash
./gradlew clean build
# Artefakte: build/libs/*.jar und build/distributions/MCPBridgeExtension-*.bwextension
```

## Hinweise
- Java 17
- Bitwig Controller API als compileOnly-Dependency ergänzen (Codex-Task)
- WebSocket-Server in WSHandler implementieren
