# Project Context

## Ziel des Projekts

Das Projekt sollte einen AI-/MCP-gesteuerten Zugriff auf Bitwig Studio ermöglichen. Ausgangspunkt war ein Python-MCP-Server, der MCP-/JSON-RPC-Anfragen über OSC an die Open-Sound-Control-Integration von DrivenByMoss weiterleitete.

Historischer Basispfad:

```text
AI/MCP client
    |
    | MCP / JSON-RPC over STDIO
    v
bitwig-mcp-server (Python)
    |
    | OSC / UDP
    v
DrivenByMoss OSC controller
    |
    | Bitwig Controller API
    v
Bitwig Studio
```

Der Fork `Andy-Post/bitwig-mcp-server` basiert auf einem bereits vorhandenen Projekt. Der Repository-Hinweis, dass Dokumentation und Implementierung auseinanderliegen können, ist für die Historie wichtig: vorhandene Roadmaps oder Toollisten sind nicht automatisch Beweis für tatsächliche Funktion.

## Oktober 2025: Inbetriebnahme und erster Blocker

Zwischen 11. und 23.10.2025 wurde der vorhandene MCP-Server praktisch in Betrieb genommen und erweitert.

Historisch belegt sind:

- ein Initialize-/Startproblem mit etwa 60 Sekunden Timeout,
- Prüfung der OSC-Portkonfiguration,
- Identifikation einer fehlenden bzw. fehlerhaften STDIO-Transportkette und falscher Settings-Attributnamen,
- Dokumentation von rund 27 MCP-Tools,
- ein realer Zugriffsbeschränkung auf jeweils acht Device-Parameter,
- ein eigener Fork,
- sechs Extended-Parameter-Tools mit Paginglogik.

Eine spätere Projektauswertung nennt einen OSC-Listener auf `127.0.0.1:9000` sowie die Verbindung zu Bitwig auf `127.0.0.1:8000`. Damit ist ein funktionierender Server-/OSC-Pfad sekundär belegt. Die vollständigen Original-Logs sind jedoch nicht erhalten.

## Die 8-Parameter-Grenze

Für Device-Parameter war der Zugriff auf eine Bank von acht Parametern begrenzt. Bei Geräten mit deutlich mehr Parametern — historisch wird EQ+ mit 40+ Parametern als Beispiel genannt — war damit nur ein Teil direkt adressierbar.

Im Oktober wurde dies zunächst im MCP durch Paging adressiert. Die sinngemäße Seitenauswahl war:

```text
page = (parameter_index - 1) // 8
```

Historische Einordnung:

- 8-Parameter-Limit: **VERIFIED**
- Paging-Konzept: **IMPLEMENTED**
- sechs Extended-Parameter-Tools: **IMPLEMENTED**
- vollständiger Bitwig-Live-Test aller sechs Tools: **UNKNOWN – historical evidence insufficient**

## November 2025: das Problem wird größer als „nur acht Parameter“

Am 12.11.2025 wurde ein komplexeres Rumble-/Multi-Layer-Setup systematisch daraufhin untersucht, welche Schritte über den damaligen MCP/DrivenByMoss-Pfad automatisierbar waren.

Eine historische Zusammenfassung beziffert die Abdeckung grob mit etwa 50 %. Diese Zahl ist als damalige Arbeitsbewertung zu verstehen, nicht als heute reproduzierter Benchmark.

Daraus entstand eine 4-Phasen-Roadmap:

1. OSC-Erweiterung,
2. eigene Java-Bitwig-Extension,
3. WebSocket-Bridge,
4. zusätzliche Clip-/Projekt-/Export-Funktionalität.

Die Roadmap war **PLANNED**, nicht fertig implementiert.

## November 2025: mehrere Lösungswege parallel

### Remote-OSC / Sprint 1

`Sprint 1 – Extended Parameter Control (Remote-OSC)` plante im Python-MCP:

- `python-osc`,
- OSC-Bundles,
- einen Remote-OSC-Wrapper,
- `/user/0001…/1024`,
- YAML-/JSON-Mappings,
- Batch-Parametersteuerung,
- Smoke-Tests für EQ+ und Compressor.

Die Aufgaben und Acceptance Criteria sind im erhaltenen Sprint-Dokument nicht als abgeschlossen belegt.

Status: **PLANNED**.

Dieser Ansatz ist historisch nicht identisch mit der Oktober-Paging-Lösung.

### Java Bridge / Sprint 2

Unter `bridge/bitwig-extension` wurde ein Java-Starter eingecheckt. Die README nennt ihn ausdrücklich einen minimalen Startpunkt für Sprint 2 und nennt noch offene Arbeiten:

- Bitwig Controller API als Dependency ergänzen,
- WebSocket-Server in `WSHandler` implementieren,
- lauffähige `.bwextension` daraus machen.

Status:

- Java-Bridge-Skeleton: **IMPLEMENTED**
- funktionierende Java/WebSocket-Bridge: **UNKNOWN – historical evidence insufficient**

Die Commit-Historie zeigt am 12.11.2025 mehrfaches Hinzufügen, Löschen und erneutes Hinzufügen von Bridge-Artefakten. Das ist ein konkreter Repository-Beleg für die damalige Bewegung der Architekturartefakte.

### Unified Mapping / Sprint 3

Am 15.11.2025 wurden Sprint-3-Dokumente für einen gemeinsamen Mapping-/Controller-Layer angelegt und mehrfach überarbeitet.

Historischer Status: **PLANNED**.

## Browser, Device Navigation und ChromaDB

Browser-, Device-Navigation-, Layer- und ChromaDB-Artefakte stammen zum Teil bereits aus dem Upstream-Projekt vor dem eigenen Fork. Die übernommene Git-Historie enthält im März/April 2025 bereits:

- Device-spezifische URIs,
- Browser-Pagination,
- echte MCP-/Bitwig-Integrationstests,
- Browser-Indexer und OSC Query Tools.

Am 22.11.2025 wurde laut historischem Chatindex die Browser-Index-Funktion live getestet.

Daher:

- Existenz der Browser-/ChromaDB-Infrastruktur: **VERIFIED**
- Browser-Index-Livetest am 22.11.2025: **VERIFIED**
- vollständige Funktionsfähigkeit aller Browser-/Recommendation-Funktionen im eigenen Setup: **UNKNOWN – historical evidence insufficient**

## März 2026: der Parameterblocker wird in DrivenByMoss selbst adressiert

Im März 2026 wurde das Projekt erneut aufgenommen. Der Arbeitsweg wurde diesmal stärker auf den DrivenByMoss-Fork verlagert.

Belegt sind:

1. kritische Überarbeitung eines Codex-Auftrags zur Entfernung der 8-Parameter-Grenze,
2. Ergänzung von Backward Compatibility, Observer-Pattern und UDP-Paketgrößen als Anforderungen,
3. Review mit GPT-5.4 Pro; sieben Verbesserungen übernommen, ein spekulativer Codevorschlag verworfen,
4. Codex/GitHub-Workflow ohne lokalen Clone,
5. GitHub-Anbindung in Codex,
6. erneute Abgrenzung zwischen MCP, DrivenByMoss und eigener Extension.

## `allparams` war mehr als eine Idee

Am 13.03.2026 entstanden sieben offene Pull Requests im DrivenByMoss-Fork.

Wesentlich:

- PR #3 implementierte eine optionale Extended Parameter Bank mit 512 Slots und Lookup-API.
- PR #4 implementierte OSC-Handler für `/device/allparams/*` und einen Multi-Value-OSC-Writer.
- PR #5 dokumentierte die Architektur.
- PR #6 dokumentierte Build-Validierung und einen manuellen Bitwig-Testplan.
- PR #7 implementierte ein Serum-2-Parameter-Dump-Tool.

Adressraum:

```text
/device/allparams/count
/device/allparams/{index}/value
/device/allparams/{index}/name
/device/allparams/list
/device/allparams/list/{offset}
/device/allparams/byname
/device/allparams/error
```

PR #3 setzte die 512-Slot-Sicht aus 64 Seiten × 8 Parametern zusammen. PR #4 ließ die bestehende `/device/param/{1..8}/...`-API unangetastet.

## Entscheidender Verifikationsstatus im März

Die März-PRs berichten übereinstimmend:

- Buildversuche wurden durchgeführt.
- Maven scheiterte bei `maven-enforcer-plugin:3.6.2` mit HTTP 403.
- Vollständige Kompilierung/Integration konnte nicht bestätigt werden.
- Eine Bitwig-Live-Verifikation ist in den PRs nicht belegt.
- Die PRs wurden nicht in `master` gemergt und sind weiterhin offen.

Daher:

- `allparams`-Spezifikation: **PLANNED**
- Framework-Implementierung PR #3: **IMPLEMENTED**
- OSC-Handler PR #4: **IMPLEMENTED**
- Doku/Testplan PR #5/#6: **IMPLEMENTED** als Dokumentation
- Serum-2-Dump-Tool PR #7: **IMPLEMENTED**, Syntaxprüfung bestanden
- erfolgreicher Java-Build: **UNKNOWN – historical evidence insufficient**
- Live-Funktion in Bitwig: **UNKNOWN – historical evidence insufficient**

## Warum mehrere technische Ansätze nebeneinander existieren

Der Verlauf war nicht linear:

1. **Oktober:** vorhandene Architektur lauffähig machen und die 8er-Bank durch MCP-Paging umgehen.
2. **November:** größere Capability-Lücken führen zu einer Hybrid-Roadmap mit Remote-OSC, Java-Bridge/WebSocket und Mapping-Layer.
3. **März:** der konkrete Parameterblocker wird erneut betrachtet und diesmal tiefer in DrivenByMoss mit `allparams` adressiert.

Nicht belegt ist, dass alle jeweils älteren Ansätze ausdrücklich verworfen wurden. Teilweise wurden sie funktional überholt, teilweise nur liegen gelassen.

Das ist der zentrale historische Grund, warum heute mehrere technische Ansätze nebeneinander im Projektgedächtnis auftauchen.
