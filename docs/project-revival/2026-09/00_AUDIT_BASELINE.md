# 00 Audit Baseline: Bitwig-MCP Revival 2026

Audit-Datum: 04.09.2026, Revision 1 (CL-26 aus WP-00 ergänzt)
Auditor: Claude Fable 5.1 (Sandbox, read-only)
Werkzeuge: git 2.x, Python 3.12.3 (venv), pytest, curl gegen api.github.com (unauthentifiziert, teilweise rate-limited)
Nicht verfügbar in der Audit-Umgebung: Maven, maven.bitwig.com (HTTP 403 durch Netz-Allowlist), Bitwig Studio, Serum 2, torch/sentence-transformers (Plattenplatz)

Evidenzstufen gemäß Auftrag: HISTORICAL-ONLY, SOURCE-CONFIRMED, CODE-PRESENT, BUILD-PASSING, TEST-COVERED, TEST-PASSING, INTEGRATION-CONFIRMED, BITWIG-LIVE-VERIFIED, CONTRADICTED, UNKNOWN.

---

## 1. Reproduzierbarer Snapshot

### 1.1 Andy-Post/bitwig-mcp-server

| Merkmal | Wert |
|---|---|
| Default-Branch | main |
| HEAD | 688050cd2767f9018064a2206fc2dbc1cbadba15 (04.09.2026, "Add files via upload") |
| Commits gesamt / eigene | 39 / 18 |
| Upstream (real) | WeModulate/bitwig-mcp-server, HEAD 120f7a0fc01e240657a8a3bc6993557675a37fc0 (10.04.2025) |
| jxstanford/bitwig-mcp-server | HTTP 301 auf WeModulate; identische Historie |
| Merge-Base / Ahead / Behind | 120f7a0 / 18 / 0 |
| Weitere Branches | origin/sprint3 = 04acf5cac4ab405d236aca0beeb9f769e166d05a (12.11.2025), vollständig in main enthalten |
| Tags / Releases | keine |
| Lizenz Upstream | MIT |
| Lockfile-Pins (uv.lock) | mcp 1.4.1, python-osc 1.9.3, chromadb 0.6.3 |
| CI-Workflows | main.yml (make check, make test auf Python 3.10-3.13, mypy, mkdocs), on-release-main.yml, validate-codecov-config.yml |
| Reale Workflow-Runs | UNKNOWN (API-Rate-Limit); Startbeobachtung: keine Runs sichtbar |

Eigene Änderungen gegenüber Upstream (git diff --stat 120f7a0 main): 21 Dateien, 1483 Zeilen, ausschließlich Dokumente, Issue-Templates, Bridge-Starter, config(1).yml. **Keine Python-Datei geändert** (git diff 120f7a0 main -- '*.py' ist leer).

### 1.2 Andy-Post/DrivenByMoss

| Merkmal | Wert |
|---|---|
| Default-Branch | master |
| HEAD | c8b450721fb57f358c6902e6e5712bb47952291a (13.03.2026, Workflow-Änderung) |
| Merge-Base mit Upstream | 09cdf6e47fc6fc0929c402d75b53cd7fe59c0ce1 = Tag 26.6.0 (25.01.2026) |
| Ahead / Behind gegenüber upstream/master | 5 / 12 |
| Eigene Commits | 01f629c (AGENTS.md), 84cc630, 921afb9, 2a82b27, c8b4507 (alle .github/workflows/maven.yml) |
| Upstream HEAD | git-moss/DrivenByMoss bc3c7b593942236670eb1e8a4b037006a23c47cf (16.08.2026), pom-Version 26.6.5 |
| Upstream-Tags nach Fork | 26.6.1 (fa55b994, 10.04.2026), 26.6.2 (3477f025, 12.06.2026), 26.6.3 (836117be, 26.07.2026) |
| extension-api (Fork und Upstream) | 21 |
| Lizenz | LGPL-3.0 |
| Build | Maven, JDK 21 im Fork-Workflow, mvn clean install -DskipTests --batch-mode |
| Tests | keine (src/ enthält nur main und assembly) |

Offene PRs (alle vom 13.03.2026, alle auf Base 01f629c30e1f30d8f41ee033eb22800b62a255e6, jeweils 4 Commits hinter master, nur Workflow-Commits dazwischen):

| PR | Head SHA | Branch | Inhalt (ohne Whitespace) |
|---|---|---|---|
| #1 | 895a0093b9dfa520a604ec27ced27dfa6caba404 | codex/clone-repository-and-analyze-structure | ANALYSIS.md, 196 Zeilen |
| #2 | 8039963169e4494c43666263ec0b213878ebc0d5 | codex/analyze-existing-parameter-handling | PARAMETER_ANALYSIS.md, 212 Zeilen |
| #3 | 5ec18fc5342457900524004c43ab4f84f2412a3d | codex/add-extended-parameter-access-to-framework | 11 Dateien, +399/-6 real; Rohdiff +1650/-1257 durch CRLF-Konvertierung |
| #4 | 5c62c5acf75a8de75eaba67e6803bcac5551fb64 | codex/implement-osc-handler-with-new-addresses | 5 Dateien, +283 real; Rohdiff +1269/-986 durch CRLF-Konvertierung |
| #5 | fe4ecc5fd4d2551d6d785c50486dbeaf0d434d9d | codex/create-implementation-plan-for-architecture-expansion | ARCHITECTURE.md, 162 Zeilen |
| #6 | ce4b7d6875270952ded9efa51e62ac17cf12a72a | codex/build-project-and-validate-changes | README_EXTENSION.md, 74 Zeilen |
| #7 | 118c2a578ea2dd1b259b6460c1a7eb07e25253da | codex/create-serum2_param_dump.py-script | serum2_param_dump.py (193 Zeilen), serum2_mapping.json |

### 1.3 Lokal nicht ermittelbar (LOCAL-ONLY/UNKNOWN)

Siehe gebündeltes Verifikationspaket in 02_LIVE_VERIFICATION_PROTOCOL.md, Abschnitt 1:
Betriebssystem, Bitwig-Version, Controller-API-Version von Bitwig 6.1, lokal vorhandenes API-JAR, Java-Version lokal, Serum-2-Version und Plugin-Format (VST3/CLAP), Ergebnis der GitHub-Actions-Runs für PR #3/#4.

---

## 2. Claims Ledger

| ID | Aussage | Quelle | SHA/Datum | Evidenzstufe | Gegenbeleg | Nächster Beweis |
|---|---|---|---|---|---|---|
| CL-01 | Der MCP-Fork enthält sechs Oktober-Extended-Parameter-Tools mit Paging | CAPABILITY_HISTORY.md, HD-002 | 16.10.2025 | CONTRADICTED | git diff 120f7a0 main -- '*.py' leer; 26 Tools, alle Upstream | keiner nötig; Historie bleibt als Historie |
| CL-02 | Upstream des MCP-Forks ist jxstanford | README.md Zeile 35 | 688050c | SOURCE-CONFIRMED als Redirect | jxstanford liefert 301 auf WeModulate, gleiche Historie | README-Zeile aktualisieren (Cleanup) |
| CL-03 | Python-Server startet und OSC-Pfad 9000/8000 funktioniert | PROJECT_CONTEXT.md | Okt 2025 | HISTORICAL-ONLY | keiner | Live-Protokoll Schritt 12/13 |
| CL-04 | Python-Unit-Tests sind grün | CLAUDE.md | 120f7a0 | TEST-PASSING (eingeschränkt) | 126 passed, 6 failed in tests/utils/test_browser_indexer.py mit mcp 1.4.1; mit mcp 2.1.1 zwei weitere Fehler (inputSchema vs input_schema) | Claude Code: uv sync, make test lokal mit Lockfile |
| CL-05 | Echte MCP-/Bitwig-Integrationstests existieren | CLAUDE.md, tests/integration | 120f7a0 | TEST-COVERED, nicht TEST-PASSING | 19 Tests werden ohne Bitwig geskippt; kein Lauf belegt | Live-Protokoll Schritt 13 |
| CL-06 | set_device_parameter ist auf 8 Parameter begrenzt | osc/client.py Zeile 259-300 | 120f7a0 | CODE-PRESENT | keiner | keiner |
| CL-07 | MCP-Writes werden per Read-back bestätigt | keine | keine | CONTRADICTED | tools.py gibt nach send() sofort Erfolg zurück | P0-Arbeitspaket |
| CL-08 | PR #3 und PR #4 ergänzen sich (Framework + Handler) | HD-009, OQ-006 | März 2026 | CONTRADICTED | PR #4 nutzt Upstream ParameterListImpl via setNumListParams(512); PR #3 ExtendedParameterBankImpl ist eine strukturelle Kopie von ParameterListImpl | keiner; Entscheidung siehe ADR |
| CL-09 | ParameterListImpl existiert im Upstream | src/.../ParameterListImpl.java | seit ec6fcec0 (27.03.2025) | SOURCE-CONFIRMED | keiner | keiner |
| CL-10 | allparams-Index ist eine kanonische Identität | ARCHITECTURE.md (PR #5) | fe4ecc5f | CONTRADICTED | PR #4 getExistingParameters() kompaktiert die Liste vor der Indizierung; Index verschiebt bei Lücken | Live-Protokoll Schritt 7 nach Korrektur |
| CL-11 | Fehlerhafte byname-Payload liefert keine OSC-Fehlerantwort | Review PR #4 | 5c62c5ac | CODE-PRESENT (Fehler bestätigt) | parseAllParamsByName wirft IllegalParameterException | Fix im WP-04 |
| CL-12 | Dump-Tool kann vor Verarbeitung aller Items enden | Review PR #7 | 118c2a57 | CODE-PRESENT (Risiko bestätigt) | ThreadingOSCUDPServer, end_event.set() in handle_end ohne Item-Zähler-Abgleich | Live-Protokoll Schritt 10 |
| CL-13 | Maven-Build ist strukturell blockiert | PR #1, #6 | März 2026 | CONTRADICTED als Strukturaussage | Andys Angabe: GitHub Actions grün mit JDK 21 (HISTORICAL-ONLY); 403 ist Netz-Allowlist (hier reproduziert) | Claude Code lokaler Build |
| CL-14 | PR #3/#4 wurden von GitHub Actions erfolgreich gebaut | Andys Startbeobachtung | März 2026 | HISTORICAL-ONLY | API-Abfrage im Audit rate-limited | Claude Code: gh run list |
| CL-15 | allparams läuft in Bitwig | keine | keine | UNKNOWN | keiner | Live-Gate LG-1 |
| CL-16 | 512 Slots reichen für Serum 2 | AGENTS.md, PR #5 | März 2026 | UNKNOWN | keiner | Live-Gate LG-1 |
| CL-17 | Bitwig exponiert für ein Plugin ohne Custom-Mapping alle Parameter als Default-Remote-Control-Seiten | allgemeine Annahme aller Ansätze | - | UNKNOWN | keiner | Live-Gate LG-1 (Count gegen Serum-eigene Parameterliste) |
| CL-18 | Bitwig Controller API bietet gezielten Parameterzugriff per Plugin-Parameter-ID | KompleteDevice.java Zeile 50-52, EqualizerDeviceImpl.java Zeile 74-83 | upstream 26.6.x | SOURCE-CONFIRMED (createSpecificVst2Device, createSpecificVst3Device, SpecificPluginDevice.createParameter) | CLAP-Variante nicht im Upstream-Code belegt | Claude Code: API-Javadoc lokal prüfen |
| CL-19 | DrivenByMoss OSC empfängt über das Bitwig-eigene OscModule | HostImpl.java Zeile 214-223 | upstream | SOURCE-CONFIRMED | keiner | keiner |
| CL-20 | Eingebetteter MCP-Server in einer Bitwig-Extension ist technisch möglich | fabb/WigAI (Jetty 11 + MCP Java SDK 0.11, Streamable HTTP), March7-code/Bitwig-mcp (MCP SDK 2.0.0) | dc8600f7 (17.04.2026), c8425348 (29.07.2026) | CODE-PRESENT; Live-Funktion dort nicht selbst verifiziert | beide rufen Bitwig-API direkt aus Jetty-Threads auf | nur relevant, falls Klasse D gewählt wird |
| CL-21 | Vergleichsprojekte lösen das Parameterproblem | README-Featurelisten | - | CONTRADICTED | WigAI/March7: DEVICE_PARAMETER_COUNT = 8; maxdahl: PARAMS_PER_PAGE (8) | keiner |
| CL-22 | Sprint 1 Remote-OSC ist abgeschlossen | sprint1-extended-parameter-control.md DoD | 89147a9 | CONTRADICTED | 19 offene, 0 erledigte Task-Checkboxen | keiner; archivieren |
| CL-23 | bridge/bitwig-extension ist eine lauffähige Bridge | bridge README | 12.11.2025 | CONTRADICTED | README nennt API-Dependency und WSHandler als offen; WSHandler 31 Zeilen ohne Server | keiner; archivieren |
| CL-24 | Fork-Master ist 7 Commits voraus/zurück | Startbeobachtung | - | CONTRADICTED | exakt 5 voraus, 12 zurück gegenüber bc3c7b59 | keiner |
| CL-25 | Bitwig 6.1 ändert die Controller-API-Version | Startbeobachtung | 26.08.2026 | UNKNOWN | Upstream-pom am 16.08.2026 noch API 21 | Claude Code: lokale API-JAR-Version |
| CL-26 | Plugin-Parameter-IDs eines Plugins sind über die Bitwig Controller API dynamisch enumerierbar | Ableitung aus CL-18 in 01 (Bewertung Klasse C); Governance-Freigabe WP-00 (Opus, K-5) | 04.09.2026 | UNKNOWN | CL-18 belegt nur Zugriff per bekannter ID (createParameter(id)); Verwendung im Upstream ausschließlich hartcodiert (KompleteDevice, EqualizerDeviceImpl); keine Auflistungsmethode in Upstream, Vergleichsprojekten oder API-Quellen nachgewiesen | API-Javadoc/JAR-Prüfung in WP-01 (ENV-3); entscheidungsrelevant nur nach LG-1 Fail über DG-9/LG-2 |

---

## 3. Repository-, PR-, Build- und Testbefunde

### 3.1 MCP-Repository (Python)

- Modulumfang: 7831 Zeilen in bitwig_mcp_server; größte Dateien browser_indexer.py (1997), resources.py (1358), tools.py (1102), client.py (725).
- Harte Importkette: mcp/tools.py -> utils/device_recommender.py -> utils/browser_indexer.py -> chromadb, sentence_transformers. Ohne torch startet der Server nicht. Das ist eine Betriebs- und Installationslast, die für P0 nichts beiträgt.
- Tool-Inventar (26): browse_device_presets, browse_insert_device, cancel_browser, commit_browser_selection, device_browser_workflow, enter_device_layer, exit_device_layer, get_device_categories, get_device_info, navigate_browser_filter, navigate_browser_result, navigate_browser_tab, navigate_device, preset_browser_workflow, recommend_devices, reset_browser_filter, search_device_browser, select_device_sibling, set_device_parameter, set_tempo, set_track_pan, set_track_volume, toggle_device_bypass, toggle_device_window, toggle_track_mute, transport_play.
- State-Feedback: osc/server.py hält einen Message-Cache (get_message); controller.get_device_params() liest /device/param/{1..8}/exists|name|value. Kein Write-Verify in tools.py.
- Testlauf (Sandbox, Befehl: BITWIG_TESTS_DISABLED=1 python -m pytest --ignore=tests/integration, Stub für sentence_transformers, chromadb 1.5.9 statt 0.6.3):
  - mcp 1.4.1: 126 passed, 6 failed (alle test_browser_indexer.py: Tag-Formatierung, Kategorie 'Unknown' statt 'Synthesizer', Mock-Erwartungen). Upstream-Drift, nicht P0-relevant.
  - mcp 2.1.1: zusätzlich test_get_bitwig_tools und test_get_bitwig_resources rot (Tool.inputSchema heißt jetzt input_schema).
  - tests/integration: 19 skipped ohne Bitwig.
- Dokumentationswidersprüche: CLAUDE.md führt "real MCP integration tests" unter "Currently Implemented" und als abgehakte Punkte unter "In Progress"; offen dort nur "Ensure MCP server tests work with mock controllers". README verweist auf jxstanford-Badges und Clone-URL. pyproject.toml nennt jxstanford als Homepage.
- Sprint-1-Dokument: Definition of Done mit drei grünen Haken, 19 offene Task-Checkboxen. Kein Fertigstellungsnachweis.
- bridge/bitwig-extension: Gradle-Starter, Java 17, ohne API-Dependency, WSHandler ohne Serverlogik. Skeleton.
- Sprint-3-Dokumente (docs/README_Sprint3.md, Issue-Template): Planung ohne Code.

### 3.2 DrivenByMoss-Fork (Java)

- PR #3 (ExtendedParameterBankImpl): legt 64 CursorRemoteControlsPage-Instanzen an, pinnt jede per reAdjust auf ihren Seitenindex, hält ParameterRef mit exists/name/value/displayedValue und einen normalizedName-Lookup (erster Treffer gewinnt). Ergänzt ISpecificDevice um getAllParameters, getParameterByFullIndex, getParameterByName, getExistingParameterCount und ModelSetup um wantsExtendedParameterBank. Konstruktion, Observer und reAdjust sind zeilenweise identisch zum Upstream ParameterListImpl.
- PR #4 (DeviceModule allparams): setzt in OSCControllerSetup ms.setNumListParams(512) und arbeitet ausschließlich über cursorDevice.getParameterList().getParameters(). Handler: count, list, list/{offset}, byname, {index}/value, {index}/name, Fehlerkanal /device/allparams/error, Dump begin/item/end, zyklisches count im Flush. Multi-Value-Overload in IOpenSoundControlWriter/AbstractOpenSoundControlWriter.
- Technische Mängel PR #4:
  1. getExistingParameters() erzeugt bei jedem Aufruf (auch jedem Flush) eine gefilterte Kopie und indiziert diese. Der Index ist damit kompaktiert und nicht slot-stabil.
  2. byname wirft bei falscher Payload eine Exception statt /device/allparams/error zu senden.
  3. Werte laufen über toInteger() in der konfigurierten OSC Value Resolution (128/1024/16384), nicht normalisiert 0..1; item liefert dagegen normalizedValue als Float. Uneinheitliche Wertesemantik zwischen Read und Write.
  4. Kein Acknowledgement für Writes; Bestätigung nur über den regulären Observer-Flush.
- Beide PRs konvertieren CRLF nach LF in allen berührten Dateien. Ein Rebase auf upstream/master (12 Commits, u. a. Jackson-Bump, OSC insertClap) erzeugt dadurch Ganzdatei-Konflikte. Vor jeder Weiterverwendung: Diff mit Original-Zeilenenden neu anwenden.
- PR #7 (serum2_param_dump.py): ThreadingOSCUDPServer, end_event.set() in handle_end und handle_error; kein Abgleich Count aus begin gegen empfangene Items; Timeout-Default über --timeout. Race Condition möglich.
- Build: In der Audit-Umgebung nicht möglich (maven.bitwig.com 403). Andys Angabe grüner Actions-Runs mit JDK 21 und -DskipTests ist plausibel, hier nicht bestätigt. Auch wenn bestätigt: nur BUILD-PASSING, kein Testnachweis (Repo hat keine Tests).
- Upstream-Drift seit Fork: 26.6.1 bis 26.6.5 (Fixes, insertClap, Jackson 2.22.1, JNA 5.19.1). Kein Hinweis auf API-Versionswechsel.

### 3.3 Vergleichsprojekte

| Kriterium | fabb/WigAI | March7-code/Bitwig-mcp | maxdahl/bitwig-mcp |
|---|---|---|---|
| HEAD | dc8600f7c3ba0d9f2e2d321fbbb4e9fc164139b6, 17.04.2026 | c8425348552c45dcb4f465ac1d52b5ecbd53ca67, 29.07.2026 | c13fccf3ffd5fd573260088d18676fb896b017c9, 11.08.2026 |
| Lizenz | README: MIT; LICENSE-Datei fehlt im Repo | MIT (LICENSE vorhanden) | MIT |
| Aktivität | Release 0.9.0, seit April ruhig | Fork von WigAI (identische frühe Historie), aktiv | seit Dez 2025, aktiv |
| Architektur | MCP-Server in der Extension: Jetty 11, MCP Java SDK 0.11, HttpServletStreamableServerTransportProvider, Port 61169 | wie WigAI, MCP SDK 2.0.0, Jackson shaded; plus Arrangement/Automation/Creation/Instrument-Tools | Extension mit TCP ServerSocket (JSON) + Node/TypeScript-MCP-Server; Fokus MIDI-Clips/Noten |
| Bitwig API | 19 | 19 | 18 |
| Parameterzugriff | createCursorRemoteControlsPage(8) | createCursorRemoteControlsPage(8), zusätzlich targeted device cursor | PARAMS_PER_PAGE (8), Batch-Tool über die 8er-Seite |
| Threading | Bitwig-API direkt aus Jetty-Request-Threads; kein scheduleTask | wie WigAI | UNKNOWN (eigener Socket-Thread) |
| Tests | 22 Testdateien | 5 Testdateien | nur E2E-Prompt-Dokumente |
| Wiederverwendbar | Nachweis, dass Jetty + MCP-SDK in einer .bwextension lauffähig ist; Gradle-Setup als Vorlage | wie WigAI | Konzept Extension-Socket, sonst nichts für P0 |

Fazit Prior Art: Kein Projekt hat den Parameterzugriff jenseits von 8 gelöst. Ein Fork spart für P0 nichts. Relevant bleibt nur der Existenzbeweis für Klasse D.

---

## 4. P0/P1-Capability-Matrix (heutiger Stand)

Legende Spalten: Bitwig API (durch Upstream-Code oder API-Nutzung belegt), Backend (DrivenByMoss OSC), MCP (Python-Tool vorhanden), Code/Build/Test/Integration/Live wie Evidenzstufen.

| Capability | Bitwig API | Backend | MCP | Code | Build | Test | Integration | Bitwig live | Evidenz | Hinweis |
|---|---|---|---|---|---|---|---|---|---|---|
| Transport Play/Stop | ja | /play, /stop | transport_play | ja | n/a | Unit (Mock) | nein | nein | HISTORICAL-ONLY | Live-Test Schritt 13 |
| Tempo | ja | /tempo/raw | set_tempo | ja | n/a | Unit (Mock) | nein | nein | HISTORICAL-ONLY | |
| Track Listing | ja | /track/{n}/* | Resource bitwig://tracks | ja | n/a | Unit (Mock) | nein | nein | HISTORICAL-ONLY | Track-Bank-Größe in DrivenByMoss konfiguriert |
| Track Creation | ja (Application.createInstrumentTrack) | /track/add/* | kein Tool | nein | - | - | - | - | UNKNOWN | P1, Backend-Adresse zu prüfen |
| Device Listing/Auswahl/Navigation | ja | /device/*, /device/sibling | navigate_device, select_device_sibling, get_device_info | ja | n/a | Unit (Mock) | nein | nein | HISTORICAL-ONLY | |
| Device Laden (Browser) | ja | /browser/* | browse_insert_device, commit_browser_selection u. a. | ja | n/a | Unit (Mock), Integration (skipped) | nein | Browser-Index 22.11.2025 | HISTORICAL-ONLY | |
| Vollständige API-exponierte Parameterliste | über Remote-Control-Seiten (ParameterListImpl) | nur PR #4, ungemergt | kein Tool | CODE-PRESENT (Branch) | UNKNOWN | keine | nein | nein | UNKNOWN | P0-Kern; Live-Gate |
| Parameter Read (>8) | dito | PR #4 item/name | kein Tool | CODE-PRESENT (Branch) | UNKNOWN | keine | nein | nein | UNKNOWN | |
| Parameter Write (>8) | dito | PR #4 {index}/value, byname | kein Tool | CODE-PRESENT (Branch) | UNKNOWN | keine | nein | nein | UNKNOWN | Wertesemantik uneinheitlich |
| Parameter Read/Write (1..8) | ja | /device/param/{n}/value | set_device_parameter, Resource parameters | ja | n/a | Unit (Mock) | nein | nein | HISTORICAL-ONLY | Regressionspfad |
| Kanonische Parameteradressierung | Slot-Index page*8+slot; alternativ Plugin-Parameter-ID via SpecificPluginDevice | PR #4 kompaktiert (falsch) | nein | CONTRADICTED | - | - | - | - | CONTRADICTED | Korrektur zwingend |
| Name als sekundärer Lookup mit Duplikaten | - | PR #4 erster Treffer, case-insensitiv | nein | CODE-PRESENT (Branch) | - | - | - | - | UNKNOWN | Duplikate müssen Fehler/Listen liefern |
| Batch Parameter Set | OSC-Bundles möglich | keine Bundle-Verarbeitung dokumentiert | nein | nein | - | - | - | - | UNKNOWN | P0 |
| Write-Verifikation / State Snapshot | Observer-Werte | Flush nach Änderung | nein | nein | - | - | - | - | CONTRADICTED | P0 |
| Strukturierte Fehler, Timeouts, Reconnect | - | /device/allparams/error (Branch) | exceptions.py, error_handler.py, _attempt_reconnect | teilweise | n/a | Unit (Mock) | nein | nein | HISTORICAL-ONLY | |
| Browser Search/Navigation/Commit | ja | /browser/* | ja | ja | n/a | Unit, Integration (skipped) | nein | teilweise 22.11.2025 | HISTORICAL-ONLY | ChromaDB-Abhängigkeit für P0 nicht nötig |
| End-to-End über realen MCP-Client | STDIO-Server vorhanden | - | app.py | ja | n/a | test_app_integration (skipped) | nein | Okt 2025 sekundär | HISTORICAL-ONLY | Schritt 13 |
| Clips, Noten, Scenes | ja (API) | teilweise in DrivenByMoss OSC | nein | - | - | - | - | - | UNKNOWN | P1/P2 |
| Arrangement, Automation, Routing, Sidechain, Modulatoren | teilweise | teilweise | nein | - | - | - | - | - | UNKNOWN | P2/P3, nicht vertieft |
| Bounce, Export, Save/New Project | keine öffentliche API bekannt für Bounce/Export | - | nein | - | - | - | - | - | UNKNOWN / vermutlich Unsupported | P3, außerhalb dieses Auftrags |

---

## 5. Widersprüche und verbleibende Unknowns

Widersprüche (aufgelöst durch diesen Audit):
1. Oktober-Paging: dokumentiert als IMPLEMENTED, im Repo nicht vorhanden.
2. PR #3/#4: dokumentiert als komplementär, tatsächlich redundant.
3. allparams-Index: dokumentiert als Identität, tatsächlich kompaktiert.
4. Maven-403: dokumentiert als Blocker, tatsächlich Umgebungsproblem.
5. Sprint-1-DoD und Bridge-README: als fertig bzw. Startpunkt markiert, beides ohne Umsetzung.
6. Fork-Distanz: 5/12 statt 7.

Unknowns mit Entscheidungseinfluss (alle nur lokal auflösbar):
- U-1: Anzahl der von Bitwig für Serum 2 exponierten Remote-Control-Parameter und Stabilität der Seitenreihenfolge (Live-Gate LG-1).
- U-2: Ob Bitwig für Plugins ohne Custom-Mapping alle Parameter als Default-Seiten liefert oder ob bereits eine Obergrenze existiert.
- U-3: Controller-API-Version und JAR von Bitwig 6.1; Plugin-Format von Serum 2 (VST3/CLAP) und Verfügbarkeit einer createSpecificClapDevice-Entsprechung (zusammen mit CL-26 Gegenstand von DG-9/LG-2, nur nach LG-1 Fail).
- U-4: Build-Status von PR #4 (rebased) mit lokalem JAR.
- U-5: Thread-Kontrakt der Bitwig Controller API bei Aufrufen aus Fremd-Threads (nur relevant für Klasse D).

Unknowns ohne Entscheidungseinfluss (werden nicht weiter verfolgt): OQ-002, OQ-012 bis OQ-023 der Historie.
