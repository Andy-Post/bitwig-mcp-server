# 01 Architecture Decision: Bitwig-MCP Revival 2026

Stand: 04.09.2026, Revision 1 (Korrekturen K-1 bis K-5 aus WP-00 eingearbeitet)
Bezug: 00_AUDIT_BASELINE.md (Claims Ledger CL-xx), docs/project-history (HD-xxx, OQ-xxx)

---

## 1. Decision Gate

| Frage | möglicher Einfluss auf die Entscheidung | kleinster Beweis mit hohem Informationsgewinn | aktueller Confidence-Level | vor ADR zwingend? | Owner |
|---|---|---|---|---|---|
| DG-1: Exponiert Bitwig für Serum 2 alle Plugin-Parameter über Remote-Control-Seiten, und liegt die Anzahl unter der konfigurierten Bank? | Entscheidet zwischen Seiten-basiertem Zugriff (Klassen A/B) und ID-basiertem Zugriff über SpecificPluginDevice (Klassen C/D) | PR #4 rebased bauen, in Bitwig laden, /device/allparams/count und einen Dump mit numListParams=512 und dann 2048 erfassen; Count gegen Serum-2-Parameterliste im Plugin vergleichen | niedrig (UNKNOWN, CL-16/17) | ja (einziges Live-Gate) | Claude Code |
| DG-2: Ist die Seitenreihenfolge über Reselect, Extension-Neustart, Projekt-Reload, Bitwig-Neustart stabil? | Entscheidet, ob Slot-Index kanonisch sein darf oder Name/ID-Mapping nötig ist | vier Dumps vergleichen (Schritt 4/5 Live-Protokoll) | mittel (ParameterListImpl wird upstream produktiv genutzt, CL-09) | nein, folgt aus DG-1-Lauf | Claude Code |
| DG-3: PR #3 oder PR #4 als Parameterbank-Strategie? | Bestimmt, welcher Code Basis wird | Codevergleich (erledigt): PR #3 dupliziert ParameterListImpl, PR #4 nutzt sie | hoch (CL-08) | ja (erledigt) | Fable |
| DG-4: Ist der allparams-Index kanonisch? | Bestimmt Adressierungsmodell | Codeanalyse (erledigt): kompaktiert, nicht slot-stabil (CL-10) | hoch | ja (erledigt) | Fable |
| DG-5: Genügt OSC/UDP für Dumps von mehreren hundert Items und Batch-Writes? | Entscheidet, ob ein Acknowledge-/Chunk-Protokoll nötig ist oder ein TCP/HTTP-Transport | Dump 3x wiederholen, Item-Count gegen begin-Count prüfen; Batch 64 mit Read-back | mittel (localhost-UDP verliert selten, aber Reihenfolge ungesichert) | nein | Claude Code |
| DG-6: Ist ein dauerhafter DrivenByMoss-Fork wartbar? | Klasse B gegen C | Rebase von PR #4 (ohne CRLF-Rauschen) auf upstream/master; Konfliktzahl | mittel (5/12, kleine Diffs) | nein, aber im ersten Paket erledigt | Claude Code |
| DG-7: Ist ein eingebetteter MCP-Server (Klasse D) sinnvoller als Python plus OSC? | Klasse D gegen B/C | Prior Art zeigt Existenz (CL-20), aber ungeklärter Threadkontrakt (U-5) und kein Parametergewinn (CL-21) | mittel | nein | Fable (erledigt), Opus (Governance) |
| DG-8: Welche P0/P1-Fähigkeiten verhindert eine Architektur strukturell? | Keine Klasse verhindert P0; Bounce/Export sind API-seitig unsupported in allen Klassen | Capability-Matrix (erledigt) | hoch | nein | Fable |
| DG-9 (nur nach LG-1 Fail): Sind die Plugin-Parameter-IDs von Serum 2 zur Laufzeit über die Controller API enumerierbar oder aus einer belegten, vollständigen Quelle verfügbar, und existiert für das tatsächliche Plugin-Format von Serum 2 (ENV-6) eine createSpecificXxxDevice-Entsprechung? | Entscheidet, ob Klasse C nach einem LG-1-Fail überhaupt gangbar ist; ohne Enumeration entspricht C der manuellen Mapping-Last von G | Live-Gate LG-2 (02, Abschnitt 3a): API-JAR/Javadoc auf Enumerationsmethode prüfen (ENV-3), Format aus ENV-6 gegen createSpecificVst3Device/CLAP-Entsprechung prüfen, ID-Quelle für Serum 2 benennen und Vollständigkeit gegen ENV-12 belegen | niedrig (UNKNOWN, CL-26; CLAP-Entsprechung unbelegt, CL-18) | nein für Klasse B; ja vor jeder C-Implementierung (WP-C1) | Claude Code (Nachweis), Fable (Bewertung) |

Nur DG-1 bleibt vor der finalen ADR offen. Alle anderen Fragen ändern die Entscheidung nicht oder sind beantwortet. DG-9 wird ausschließlich nach einem LG-1-Fail relevant und ist dann Voraussetzung für jede Arbeit an Klasse C; für die Entscheidung zu Klasse B hat DG-9 keinen Einfluss.

---

## 2. Bewertung der Architekturklassen

Bewertungskriterien laut Auftrag; Skala: ++ / + / 0 / - / --.

| Kriterium | A: Python + DrivenByMoss unverändert | B: Python + eigener DBM-Fork (allparams) | C: Python + eigene schlanke Extension | D: Extension mit eingebettetem MCP | E: Fork eines Vergleichsprojekts | F: Hybrid | G: Remote-OSC / Mapping |
|---|---|---|---|---|---|---|---|
| P0 erreichbar | -- (8 Parameter, Page-Orchestrierung fragil) | ++ (Code vorhanden, DG-1 offen) | UNKNOWN (gezielter ID-Zugriff belegt, ID-Enumeration unbelegt, CL-26) | + (alles neu, inkl. MCP-Server in Java) | -- (alle bei 8) | unzulässig ohne primären Pfad | - (1024 User-Slots, aber manuelles Mapping je Preset) |
| P1 erreichbar | + (Browser, Transport, Tracks vorhanden) | ++ | 0 (nur was neu gebaut wird) | 0 | + (March7: Arrangement/Automation) | - | -- |
| Zuverlässigkeit / State Feedback | 0 (Observer-Flush, kein Ack) | 0 bis + (Flush plus Read-back im MCP) | + (eigenes Protokoll mit Ack) | + | 0 | - | - |
| Kanonische Identität | -- (Page+Slot, Page wechselt) | + nach Korrektur (Slot-Index page*8+slot) | ++ nur unter der unbelegten Voraussetzung enumerierbarer IDs (CL-26) | ++ | - | - | -- |
| Batch / Latenz | - (Page-Wechsel mit 500 ms reAdjust) | + (Bundle über UDP) | + | ++ (kein Zwischenprozess) | 0 | - | 0 |
| Testbarkeit ohne Bitwig | + (Python-Mocks) | + (Python-Mocks; Java ohne Tests) | + (Java-Unit-Tests möglich, Facade-Muster) | 0 (Jetty-Tests möglich, API-Mock aufwendig) | 0 | - | 0 |
| Failure Isolation / Recovery | + (Python-Prozess getrennt) | + | + | - (Absturz im Extension-Thread trifft Bitwig) | - | - | + |
| Wartbarkeit / Update-Risiko | ++ (kein Fork) | 0 (Fork nachziehen, kleine Diffs) | + (eigener Code, API-Bumps selbst) | 0 (eigener Code plus MCP-SDK plus Jetty) | - (Fremdcode, BMAD-Struktur) | -- | 0 |
| Abhängigkeit Fremdprojekte | hoch (DBM) | hoch (DBM-Fork) | niedrig | niedrig bis mittel | hoch | hoch | mittel |
| Aufwand dauerhafter DBM-Fork | keiner | mittel (Rebase je Release, kleine Fläche) | keiner | keiner | keiner | mittel | keiner |
| MCP-Eignung für LLM-Agenten | + (Python-MCP mit Ressourcen) | + | + | + (native Tools) | 0 | - | - |
| Lizenz / Verteilung | LGPL-3.0 unverändert nutzbar | LGPL-3.0: Fork-Quellen offenlegen, unproblematisch | frei wählbar | frei wählbar | MIT (WigAI ohne LICENSE-Datei) | gemischt | MIT |
| Migrationsaufwand aus Stand | 0 | niedrig (PR #4 plus Python-Tools) | hoch (Extension von null) | sehr hoch (Extension plus MCP in Java) | hoch (Codeübernahme, Anpassung) | - | hoch (Mapping-Engine ungebaut) |
| Manueller Aufwand für Andy | niedrig | niedrig (Extension installieren, Live-Test) | mittel | mittel | mittel | hoch | hoch (Mappings pro Preset) |

Kommentare:

- A scheitert an P0 Punkt 2 (Parameter ohne Page-Navigation ermitteln). Die Page-Orchestrierung braucht pro Seite den 500-ms-reAdjust-Zyklus und liefert keine stabile Identität.
- B ist die einzige Klasse mit vorhandenem, reviewtem Code für den P0-Kern. Die drei Codemängel (Index-Kompaktierung, byname-Exception, Wertesemantik) sind je unter 30 Zeilen.
- C ist der nächste zu verifizierende Pfad, falls DG-1 ergibt, dass Remote-Control-Seiten die Parameter nicht vollständig oder nicht stabil liefern; dann ist SpecificPluginDevice.createParameter(id) (CL-18) der einzige belegte API-Weg. createParameter(id) setzt eine bereits bekannte Parameter-ID voraus und wird im Upstream ausschließlich mit hartcodierten IDs verwendet (KompleteDevice, EqualizerDeviceImpl). Eine Methode zum Auflisten der IDs eines beliebigen Plugins ist nicht belegt (CL-26); eine CLAP-Entsprechung von createSpecificVst3Device ist ebenfalls nicht belegt (CL-18, U-3). Ohne Enumeration bräuchte C eine externe ID-Quelle je Plugin und trüge damit dieselbe manuelle Mapping-Last, wegen der G verworfen wurde. C ist deshalb kein gesicherter Fallback, sondern muss vor jeder Implementierung DG-9/LG-2 bestehen.
- D ist attraktiv wegen Latenz und Verzicht auf Python, löst aber das Parameterproblem nicht besser als B und bringt zwei ungeklärte Risiken: Thread-Kontrakt (U-5) und Jetty/MCP-SDK-Größe innerhalb der Extension-Classloader. Beides ist Aufwand ohne P0-Gewinn.
- E: keines der Projekte hat >8 Parameter; März7 ist ein WigAI-Fork mit BMAD-Overhead; WigAI hat keine LICENSE-Datei. Kein Vorteil.
- G: Remote-OSC braucht pro Preset ein manuelles Mapping der User-Slots und ist damit für "alle Parameter automatisch ermitteln" ungeeignet. Historische Zwischenlösung.

---

## 3. Architecture Decision Record

### Decision Status

PROVISIONAL-PENDING-ONE-LIVE-GATE

Das Gate ist DG-1 (Live-Gate LG-1, siehe 02_LIVE_VERIFICATION_PROTOCOL.md Abschnitt 3). Kein anderes offenes Thema kann die Entscheidung ändern.

> Governance-Freigabe WP-00, 04.09.2026, Claude Opus.
>
> Status bestätigt: PROVISIONAL-PENDING-ONE-LIVE-GATE, Klasse B, einziges Gate DG-1/LG-1.
>
> Freigabe unter der Auflage der Korrekturen K-1 bis K-5 (CL-18 belegt gezielten Zugriff per bekannter Plugin-Parameter-ID, nicht die Enumeration aller IDs; Klasse C ist damit nach LG-1-Fail kein gesicherter Fallback, sondern der nächste zu verifizierende Pfad mit eigenem Nachweisgate). Die Korrekturen sind vor Start von WP-01 einzutragen; sie ändern die gewählte Architektur nicht.

Einarbeitung der Korrekturen K-1 bis K-5: 04.09.2026, Claude Fable 5.1 (dieses Dokument, 00_AUDIT_BASELINE.md, 02_LIVE_VERIFICATION_PROTOCOL.md, 03_IMPLEMENTATION_PLAN.md).

### Chosen Architecture

Klasse B: Python-MCP (bestehendes Repo, Upstream-Code) als MCP-Schicht, eigener DrivenByMoss-Fork als primäres und einziges Backend, Parameterzugriff über die Upstream-Klasse ParameterListImpl mit erhöhter Bankgröße und den OSC-Namespace /device/allparams/* aus PR #4, korrigiert um Slot-Index-Identität, Fehlerkanal und einheitliche Wertesemantik.

Pfad:

MCP-Client -> MCP/STDIO (später optional Streamable HTTP) -> Python bitwig-mcp-server -> OSC/UDP localhost -> DrivenByMoss-Fork (OSC-Controller) -> Bitwig Controller API 21 -> Bitwig Studio

Bei Pass von LG-1: Status wird FINAL, Klasse B.
Bei Fail von LG-1: nächster zu verifizierender Architekturpfad ist Klasse C (eigene schlanke Extension, Parameterzugriff per Plugin-Parameter-ID über SpecificPluginDevice, OSC oder einfacher TCP-Transport, Python-MCP bleibt). Klasse C ist nicht gesichert: CL-18 belegt nur den gezielten Zugriff per bekannter ID, nicht die Enumeration der IDs (CL-26). Vor jeder C-Implementierung ist das Gate DG-9/LG-2 (02_LIVE_VERIFICATION_PROTOCOL.md, Abschnitt 3a) zu bestehen, das zwei Punkte nachweist: (a) die Parameter-IDs von Serum 2 sind zur Laufzeit über die Controller API ermittelbar oder liegen aus einer belegten, vollständigen Quelle vor; (b) für das tatsächliche Plugin-Format von Serum 2 (ENV-6) existiert eine createSpecificXxxDevice-Entsprechung (U-3). WP-C1 beginnt mit diesem Nachweis, nicht mit einem Extension-Grundgerüst. Besteht LG-2 nicht, ist auch C verworfen und die Architekturfrage geht ohne vorbestimmten Pfad an Claude Opus zurück. DrivenByMoss würde bei C nur Legacy für Transport/Tracks/Browser, bis diese Fähigkeiten in der eigenen Extension nachgezogen sind. C wird nicht parallel zu B implementiert.

### Decision Drivers

1. Kürzester Weg zu P0: der einzige vorhandene Code für >8 Parameter liegt in PR #4 (CL-08).
2. ParameterListImpl ist Upstream-Produktivcode (ROTO-CONTROL seit 27.03.2025, CL-09); die Erhöhung von numListParams ist ein Konfigurationswert, kein Framework-Umbau.
3. Der Python-MCP enthält bereits Transport, Tracks, Device-Navigation, Browser und STDIO-Server; nichts davon muss neu gebaut werden.
4. Klasse D bringt für P0 keinen Fähigkeitsgewinn (CL-21) und zwei ungeklärte Risiken.
5. Andys manueller Aufwand bleibt bei Installation und Live-Tests.

### Evidence

- CL-08, CL-09, CL-10, CL-11, CL-13, CL-18, CL-19, CL-20, CL-21 (00_AUDIT_BASELINE.md).
- Offen: CL-15, CL-16, CL-17 (Live-Gate LG-1).
- Offen, nur für den Fail-Zweig relevant: CL-26 (Enumerierbarkeit von Plugin-Parameter-IDs, Gate DG-9/LG-2).

### Rejected Alternatives

- A: verhindert P0 strukturell (8 Parameter, Page-Wechsel).
- C als Erstwahl: höherer Aufwand ohne Nachweis, dass B scheitert; bleibt der nächste zu prüfende Pfad, mit eigenem Nachweisschritt (DG-9/LG-2), da die ID-Enumeration unbelegt ist (CL-26).
- D: kein P0-Gewinn, Thread- und Packaging-Risiko, MCP-Server müsste in Java neu entstehen, Python-Bestand verfiele.
- E: kein Projekt löst das Parameterproblem; Lizenzunklarheit bei WigAI; hoher Anpassungsaufwand.
- F: ausdrücklich unzulässig als "mehrere Wege parallel"; Klasse B mit klar benannter Legacy-8er-API ist kein Hybrid im Sinne von F, sondern eine Migration mit definiertem Ende.
- G: manuelles Mapping je Preset widerspricht P0 Punkt 2; wird archiviert.
- PR #3 als Parameterbank: redundant zu Upstream-Code, erhöht Wartungsfläche (ISpecificDevice, ModelSetup, SpecificDeviceImpl), wird geschlossen ohne Merge.

### Consequences and Accepted Disadvantages

- Dauerhafter DrivenByMoss-Fork mit Rebase-Pflicht je Upstream-Release; Diff-Fläche muss klein bleiben (nur controller/osc und framework/osc, keine Framework-API-Änderungen).
- Zwei Prozesse (Python, Bitwig) und UDP ohne Zustellgarantie; wird durch Read-back und Dump-Vollständigkeitsprüfung im MCP abgefangen.
- Obergrenze der Parameterbank ist ein statischer Wert (Observer für numListParams Parameter werden beim Start registriert); Speicher- und CPU-Last bei 2048 Slots ist live zu messen.
- Remote-Control-Seiten sind eine Bitwig-Abstraktion, keine Plugin-Parameterliste. Falls ein Nutzer Custom-Pages anlegt, sieht das Backend nur diese. Das wird als dokumentierte Grenze akzeptiert; das Testprojekt nutzt ausschließlich Default-Pages.
- LGPL-3.0: Fork-Quellen bleiben öffentlich; kein Problem für ein eigenes Werkzeug.
- ChromaDB/sentence-transformers bleiben vorerst harte Abhängigkeit des Python-Pakets; P0 fordert keine Entkopplung, sie wird als P1-Cleanup geführt.

### Migration and Cleanup

Siehe 03_IMPLEMENTATION_PLAN.md, Abschnitt 4. Kurzfassung:
- PR #4 wird ohne CRLF-Rauschen auf upstream/master neu aufgesetzt, korrigiert und als einziger Code-PR gemergt.
- PR #1, #2, #5, #6 werden nach docs/archive/2026-03-codex/ übernommen und ohne Merge geschlossen.
- PR #3 wird geschlossen (superseded).
- PR #7 wird durch ein Python-Dump-Werkzeug im MCP-Repo ersetzt (Vollständigkeitsprüfung), Original archiviert.
- Im MCP-Repo: Sprint-1-Dokument, Sprint-3-Dokumente, bridge/bitwig-extension, config(1).yml nach docs/archive/; README/CLAUDE/AGENTS bereinigt.

### Compatibility Strategy

- /device/param/{1..8}/* bleibt unverändert im Backend (Upstream-Code) und im Python-Tool set_device_parameter bis Ende von P0. Danach wird set_device_parameter auf den allparams-Pfad umgestellt und die 8er-Beschränkung im Python-Client entfällt; das Backend behält die Upstream-Adressen.
- Bestehende MCP-Toolnamen bleiben; neue Tools bekommen eigene Namen (list_device_parameters, get_device_parameter, set_device_parameters, snapshot_device_parameters).
- Backend-Version wird als /device/allparams/version (Int) exponiert, damit der MCP eine inkompatible Extension erkennt.

### Testing Consequences

- Java: keine Unit-Tests im Repo; Nachweis erfolgt über das Live-Protokoll. Ein Java-Unit-Test für die Index-/Name-Logik wird als P1 aufgenommen, sobald diese Logik in eine host-unabhängige Klasse extrahiert ist.
- Python: neue Tools bekommen Mock-Tests (OSC-Client) und Integrationstests unter tests/integration mit skip_if_bitwig_not_running; Vollständigkeits- und Read-back-Prüfungen werden als Tests kodiert.
- CI: MCP-Repo main.yml mit uv sync und Lockfile; DBM-Fork maven.yml bleibt Build-Check.

### Reassessment Triggers

Nur:
1. LG-1 Fail (definiert in 02, Abschnitt 3).
2. Neue Primärevidenz, dass die Bitwig Controller API eine direkte Parameter-Enumeration für Plugins anbietet (z. B. in einer API-Version nach 21).
3. Nachgewiesenes, nicht beherrschbares Stabilitätsproblem der Seitenpinnung (reAdjust) in wiederholten Live-Läufen.
4. Nachgewiesener Aufwand für den Fork-Rebase, der zwei aufeinanderfolgende Upstream-Releases nicht innerhalb eines Arbeitspakets Low/Base schafft.

Keine Reassessment-Auslöser sind: neue Modellmeinungen, neue Vergleichsprojekte mit Featurelisten, Wunsch nach einem "saubereren" Neuanfang.
