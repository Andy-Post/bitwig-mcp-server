# Development Timeline

| Datum | Phase | Ereignis | Ergebnis | Status | Quelle |
|---|---|---|---|---|---|
| 25.–28.03.2025 | Upstream-Vorgeschichte | Upstream erweitert Device-URIs, Browser-Pagination und echte MCP-/Bitwig-Integrationstests. | Browser-/Navigation-Infrastruktur existiert bereits vor dem eigenen Fork. | VERIFIED | übernommene Git-Historie |
| 10.04.2025 | Upstream-Vorgeschichte | Browser-Indexer und OSC Query Tools werden aktualisiert. | Browser-Index ist kein erst im November 2025 entstandenes Feature. | VERIFIED | Commit `120f7a0` |
| 11.10.2025 | Inbetriebnahme | MCP-Server beantwortet Initialize nicht zuverlässig; ca. 60-s-Timeout diagnostiziert. | Start-/Transportproblem eingegrenzt. | VERIFIED | Claude-Chat #35 |
| 14.10.2025 | Transport | STDIO-Transport/Serverstart bearbeitet; falsche Settings-Namen identifiziert. | Transportcode laut Historie umgesetzt; späterer Server-/OSC-Betrieb belegt. | IMPLEMENTED / teilweise VERIFIED | Claude-Chat #36; Sekundärquelle |
| 14.10.2025 | Tool-Inventar | Rund 27 MCP-Tools dokumentiert. | Historischer Toolbestand systematisch erfasst. | VERIFIED als Dokumentationsereignis | Claude-Chat #37 |
| 14.10.2025 | Dokumentation | Tool-Referenz und Beispiel-Prompts erstellt. | Bedien-/Testkontext entsteht. | VERIFIED als Dokumentationsereignis | Claude-Chat #38 |
| 16.10.2025 | Parameter | 8-Parameter-Limit als Blocker identifiziert. | Geräte mit >8 Parametern nicht vollständig direkt erreichbar. | VERIFIED | Claude-Chat #39 |
| 16.10.2025 | Parameter | Sechs Extended-Parameter-Tools mit Paginglogik spezifiziert/umgesetzt. | MCP soll passende 8er-Seite automatisch wählen. | IMPLEMENTED | Claude-Chat #40 |
| 23.10.2025 | Git/Übergabe | Änderungen für Git-Commit vorbereitet. | Implementierung für Repository-Übernahme aufbereitet. | VERIFIED als Arbeitsereignis | Claude-Chat #41 |
| 12.11.2025 | Capability-Analyse | Grenzen von DrivenByMoss/MCP anhand komplexerer DAW-Automation untersucht. | Historische Arbeitsbewertung: etwa 50 % des gewünschten Workflows abdeckbar. | VERIFIED als damalige Bewertung | Claude-Chat #42 |
| 12.11.2025 | Roadmap | 4-Phasen-Roadmap OSC → Java Extension → WebSocket → Clip/Projekt/Export. | Lösungsraum über MCP-Paging hinaus erweitert. | PLANNED | Claude-Chat #43 |
| 12.11.2025 | Sprint 1 | Remote-OSC-/Batch-/YAML-Mapping-Sprint angelegt. | Tasks und Acceptance Criteria dokumentiert, nicht als erfüllt belegt. | PLANNED | Commit `89147a9` |
| 12.11.2025 | Sprint 2 | Java-Bridge-Starter mehrfach hinzugefügt/entfernt/wieder hinzugefügt. | Skeleton vorhanden; WebSocket/Bitwig-API-Anbindung noch TODO. | IMPLEMENTED als Skeleton | Commitserie 12.11. |
| 15.11.2025 | Sprint 3 | Unified-Mapping-/Controller-Dokumente angelegt und überarbeitet. | gemeinsamer Mapping-Layer geplant. | PLANNED | `eba24bc` u.a. |
| 22.11.2025 | Live-Test | MCP-Fähigkeiten, insbesondere Browser Index, live getestet. | Browser-Index-Funktion historisch live verifiziert. | VERIFIED | Claude-Chat #44 |
| 10.03.2026 | Wiederaufnahme | Codex-Auftrag zur Entfernung der 8-Parameter-Grenze kritisch überarbeitet. | Backward Compatibility, Observer-Pattern, UDP-Paketgrößen berücksichtigt. | VERIFIED als Reviewereignis | Claude-Chat #142 |
| 10.03.2026 | Review | Codex-Tasks mit GPT-5.4-Pro-Review abgeglichen. | 7 Verbesserungen übernommen, 1 spekulativer Vorschlag verworfen. | VERIFIED | Claude-Chat #143 |
| 10.03.2026 | Workflow | Codex/GitHub-Workflow ohne lokalen Clone eingerichtet. | Cloudbasierter Arbeitsweg vorbereitet. | VERIFIED | Claude-Chat #144 |
| 11.03.2026 | Workflow | GitHub-Anbindung in Codex konfiguriert. | Voraussetzung für Branch-/PR-Arbeit geschaffen. | VERIFIED | Claude-Chat #146 |
| 13.03.2026 | Architekturabgrenzung | MCP-Anforderung vs. DrivenByMoss-Erweiterung abgegrenzt. | Zuständigkeitsschichten erneut explizit diskutiert. | VERIFIED als Analyseereignis | Claude-Chat #150 |
| 13.03.2026 | DrivenByMoss Task 1 | `ANALYSIS.md` zu OSC-Architektur/Build erzeugt. | Dokumentations-only; Build scheitert an Maven-403. | IMPLEMENTED | PR #1 |
| 13.03.2026 | DrivenByMoss Task 2 | Parameterhandling und 8er-Grenze codebasiert analysiert. | bloßes Vergrößern der Bank als unzureichend bewertet. | IMPLEMENTED als Analyse | PR #2 |
| 13.03.2026 | DrivenByMoss Task 3 | optionale 512er Extended Parameter Bank implementiert. | Framework-API für Index-/Namenszugriff vorhanden; Build nicht vollständig verifiziert. | IMPLEMENTED | PR #3 |
| 13.03.2026 | DrivenByMoss Task 4 | `/device/allparams/*`-Handler und Multi-Value-OSC-Writer implementiert. | neue OSC-API auf Branch, alte 8er-API bleibt erhalten. | IMPLEMENTED | PR #4 |
| 13.03.2026 | DrivenByMoss Task 5 | `ARCHITECTURE.md` für allparams erstellt. | Adressen, 512-Limit, Dump-Protokoll und Integrationstouchpoints spezifiziert. | PLANNED / IMPLEMENTED als Doku | PR #5 |
| 13.03.2026 | DrivenByMoss Task 6 | Build-Validierung und manueller Bitwig-Testplan dokumentiert. | Build weiterhin durch Maven-403 blockiert; kein Live-Test belegt. | IMPLEMENTED als Doku | PR #6 |
| 13.03.2026 | DrivenByMoss Task 7 | `serum2_param_dump.py` erstellt. | Python-Syntax erfolgreich geprüft; realer Bitwig-/Serum-Lauf nicht belegt. | IMPLEMENTED | PR #7 |
| 07.–08.08.2026 | Später Kontext | Separate native Bitwig-Controller-Extension-Prompts für MX-1/TB-3 reviewed. | zeigt spätere Controller-API-Arbeit, aber keinen MCP-Projektfortschritt. | VERIFIED als separates Kontextprojekt | Claude-Chats #192–197 |

## Verlauf in Kurzform

```text
Inbetriebnahme
    -> Paging-Workaround
    -> größere Capability-Lücken erkannt
    -> Hybrid-/Bridge-/Remote-OSC-Roadmap
    -> Stillstand
    -> erneute Fokussierung auf Parameterblocker
    -> allparams-Implementierung auf ungemergten DrivenByMoss-Branches
    -> fehlende Build-/Live-Verifikation
```

Ein expliziter historischer Beschluss, der alle vorherigen Architekturansätze sauber ablöst, ist nicht belegt.
