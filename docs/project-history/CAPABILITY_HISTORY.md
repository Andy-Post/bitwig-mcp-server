# Capability History

## Transport / MCP / STDIO

**Erstmals untersucht:** 11.10.2025  
**Backend:** MCP/JSON-RPC über STDIO → Python → OSC → DrivenByMoss.  
**Problem:** Initialize-/Starttimeout; fehlende/fehlerhafte STDIO-/Settings-Kette.  
**Lösung:** Transport/Serverstart korrigiert.  
**Tests:** spätere Zusammenfassung belegt Serverstart, OSC-Listener `127.0.0.1:9000` und Verbindung zu Bitwig `127.0.0.1:8000`.  
**Letzter historisch belegter Status:** **VERIFIED** für Server-/OSC-Verbindung; vollständiger Original-Initialize-Log nicht erhalten.

## Transportsteuerung

**Backend:** DrivenByMoss OSC.  
**Historische Capabilities:** Play/Stop/Tempo Teil des übernommenen MCP-Bestands.  
**Letzter historisch belegter Status:** **IMPLEMENTED**; individuelle Live-Verifikation jedes Tools **UNKNOWN – historical evidence insufficient**.

## Tracks / Mixer

**Backend:** DrivenByMoss OSC.  
**Historische Capabilities:** Track-Info, Volume, Pan, Mute im übernommenen Projekt.  
**Status:** **IMPLEMENTED**; vollständige eigene Live-Testmatrix **UNKNOWN**.

## Devices

**Backend:** DrivenByMoss OSC.  
**Themen:** Device-Informationen, Device Navigation, später stabile Zieladressierung als Motivation für Bridge/Mapping.  
**Status:** Basisebene **IMPLEMENTED**; komplexe Zieladressierung historisch unvollständig.

## Device Parameters — Basis

**Blocker seit:** 16.10.2025  
**Backend:** DrivenByMoss Remote-Control-Pages mit acht Parametern.  
**Einschränkung:** nur jeweils acht Parameter direkt sichtbar/steuerbar.  
**Status:** **VERIFIED**.

## Device Parameters — Oktober-Paging

**Backend:** Python-MCP orchestriert Page-Wechsel über bestehendes OSC.  
**Lösung:** sechs Extended-Parameter-Tools; Seitenauswahl sinngemäß `(index-1)//8`.  
**Status:** **IMPLEMENTED**.  
**Live-Verifikation:** **UNKNOWN – historical evidence insufficient**.

## Device Parameters — Remote-OSC Sprint 1

**Backend:** Python-MCP → Remote-OSC, `/user/0001…/1024`, OSC Bundles.  
**Lösung:** Batchsteuerung und YAML-Mappings.  
**Geplante Tests:** ≥64 Parameter <100 ms, lokale Roundtrip <10 ms.  
**Status:** **PLANNED**; Acceptance Criteria nicht als erfüllt belegt.

## Device Parameters — `allparams` März 2026

**Backend:** eigener DrivenByMoss-Fork → Bitwig Controller API.  
**Lösung:** optionale 512-Slot-Bank, Index-/Namenslookup, `/device/allparams/*`.  
**Code:** PR #3/#4.  
**Build:** versucht, durch Maven-403 blockiert.  
**Live-Test:** nicht belegt.  
**Status:** **IMPLEMENTED**, nicht **VERIFIED**.

## Device Navigation / Siblings

Diese Funktionen existierten bereits in der übernommenen Upstream-Codebasis.  
**Status Codebasis:** **IMPLEMENTED**.  
**Live-Test im eigenen Setup:** **UNKNOWN – historical evidence insufficient**.

## Device Layers

Bereits Teil der übernommenen Infrastruktur; im November war ein Multi-Layer-Rumble-Setup Teil der Capability-Gap-Analyse.  
**Status Basisfunktion:** **IMPLEMENTED**.  
**Abdeckung komplexer Workflows:** historisch als unvollständig bewertet.

## Browser

Upstream hatte Browsernavigation und Pagination bereits im März 2025 entwickelt und Integrationstests ergänzt. Am 22.11.2025 ist ein Live-Test des Browser Index historisch dokumentiert.  
**Status Browser Index:** **VERIFIED**.  
**Status sämtlicher Browseroperationen:** **UNKNOWN**.

## Browser Index / ChromaDB / Recommendations

Browser-Indexer, ChromaDB-Suche und Recommendation-Foundation stammen aus der übernommenen Codebasis.  
**Status Codebasis:** **IMPLEMENTED**.  
**Status Live-Betrieb im eigenen Setup:** Browser Index **VERIFIED**; vollständige Recommendation-Kette **UNKNOWN**.

## Presets

Keine abgeschlossene Preset-Automation des MCP-Projekts aus der Historie sicher belegt.  
**Status:** **UNKNOWN – historical evidence insufficient**.

## Clips / MIDI

Upstream enthielt bereits Roadmap-/Testarbeit; die eigene November-Roadmap führte Clip-Funktionalität dennoch als spätere Erweiterung.  
**Status bezogen auf vollständige gewünschte MCP-Automation:** **PLANNED / UNKNOWN**.

## Arrangement

Keine belastbare eigene Umsetzung belegt.  
**Status:** **UNKNOWN – historical evidence insufficient**.

## Automation

Parameterwerte konnten grundsätzlich über Controller-/OSC-Pfade geändert werden. Eine vollständige Arrangement-Automations-API ist historisch nicht belegt.  
**Status:** teilweise **IMPLEMENTED**, Gesamtfähigkeit **UNKNOWN**.

## Routing / Sidechain

Sprint 1 beschränkte Routing ausdrücklich auf device-level Sidechain-Parameter und erklärte generisches I/O-Routing für out of scope.  
**Status generisches Routing:** **PLANNED/OUT OF SCOPE** im damaligen Sprint; spätere Umsetzung **UNKNOWN**.

## Bounce

Sprint 1 bezeichnet Export/Bounce als UI-Funktion ohne öffentliche Controller-API und nahm sie aus dem Scope.  
**Status:** **PLANNED** als spätere Capability; Implementierung nicht belegt.

## Export

Wie Bounce.  
**Status:** **PLANNED**; Live-Implementierung **UNKNOWN – historical evidence insufficient**.

## Save / New Project

Keine belastbare historische MCP-Implementierung gefunden.  
**Status:** **UNKNOWN – historical evidence insufficient**.

## Java Extension / Bridge

**Erstmals geplant:** 12.11.2025  
**Artefakt:** `bridge/bitwig-extension` Starter.  
**Offen laut eigener README:** Bitwig Controller API Dependency und WebSocket-Server.  
**Status:** Skeleton **IMPLEMENTED**, lauffähige Extension **UNKNOWN**.

## WebSocket

**Motivation:** bidirektionale Bridge zwischen Java Extension und MCP.  
**Artefakt:** Starterstruktur/`WSHandler`.  
**Status:** **PLANNED**; funktionierende Verbindung **UNKNOWN**.

## Mapping / YAML Mapping

**Sprint 1:** YAML-Mappings für EQ+, Compressor, Tool/Utility geplant.  
**Sprint 3:** einheitliche Mapping Engine mit Track→Device→Param→Slot, Regelauflösung, Page/Bank-Abstraktion und Import/Export geplant.  
**Status:** **PLANNED**.

## Serum-2-Parameter-Dump

**März 2026, PR #7:** Python-CLI implementiert, die `/device/allparams/list` konsumiert und JSON-Mapping erzeugt.  
**Test:** `py_compile` erfolgreich; realer Bitwig-/Serum-Lauf nicht belegt. Codex-Review weist auf mögliche Race Condition durch `ThreadingOSCUDPServer` hin.  
**Status:** **IMPLEMENTED**, nicht **VERIFIED**.
