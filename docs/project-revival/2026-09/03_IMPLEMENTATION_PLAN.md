# 03 Implementation Plan: P0 Vertical Slice

Stand: 04.09.2026, Revision 1 (Korrekturen K-1 bis K-5 aus WP-00 eingearbeitet)
Voraussetzung für jedes Paket ab WP-03: Freigabe der ADR (01_ARCHITECTURE_DECISION.md) durch Claude Opus als PROVISIONAL, für Pakete ab WP-04 zusätzlich LG-1 Pass.

Rollen: Fable (Audit, ADR, Plan), Claude Code (alles Lokale und Live), Codex (kleine Implementierungs-PRs), GPT-SOL (Zweitprüfung), Claude Opus (Governance), Andy (Bedienung, Zugang, Abnahme).

Aufwand: Low/Base/High in Stunden reiner Arbeitszeit des Owners, mit Confidence.

Allgemeine Stop-Regeln für alle Pakete:
- Ein Paket endet sofort, wenn es Änderungen außerhalb seiner erlaubten Dateien braucht; dann Rückmeldung statt Erweiterung.
- Kein Paket mergt eigene PRs; Merge ist Teil des jeweiligen Review-Pakets.
- Kein Paket beginnt P2/P3-Arbeit.

---

## 1. Sequenz

```
WP-00 Governance-Freigabe (Opus)
  -> WP-01 Local Evidence (Claude Code)
  -> WP-02 PR #4 sauber rebasen (Claude Code)
  -> WP-03 Build und LG-1 (Claude Code + Andy)
       Pass -> WP-04 ... WP-11
       Fail -> STOP; LG-2 (DG-9, Nachweisgate für Klasse C, 02 Abschnitt 3a) durch Claude Code + Andy;
               erst bei LG-2 Pass spezifiziert Fable WP-C1 (Klasse C, nicht gesichert)
  -> WP-04 Backend-Korrekturen (Codex)
  -> WP-05 Dump-/Probe-Werkzeug (Codex)
  -> WP-06 MCP-Tools allparams (Codex)
  -> WP-07 Zweitprüfung Backend + MCP (GPT-SOL)
  -> WP-08 Merge Backend, Merge MCP (Claude Code)
  -> WP-09 Live-Protokoll P0 vollständig (Claude Code + Andy)
  -> WP-10 Cleanup und verbindliche Dokumente (Codex)
  -> WP-11 P0-Abnahme (Andy, Opus)
```

---

## 2. Arbeitspakete

### WP-00 Governance-Freigabe der ADR

- Owner: Claude Opus
- Zweck: ADR als PROVISIONAL-PENDING-ONE-LIVE-GATE freigeben oder mit konkretem Gegenbeleg ablehnen. Bezug: OQ-021, OQ-007.
- Voraussetzungen: 00 und 01 gelesen.
- Betroffen: keine Repos.
- Erlaubt: Kommentar zur ADR, Ablehnung mit Verweis auf Claims Ledger.
- Nicht erlaubt: neue Architekturklasse einführen, Reassessment ohne Trigger.
- Schritte: Decision Gate prüfen; Rejected Alternatives prüfen; Freigabe protokollieren.
- Acceptance: schriftliche Freigabe mit Datum in 01, Abschnitt "Decision Status".
- Live-Test: nein.
- Deliverable: Freigabevermerk.
- Aufwand: Low 0,5 / Base 1 / High 2, Confidence hoch.
- Andy: keiner.

### WP-01 Local-Evidence-Paket

- Owner: Claude Code
- Zweck: ENV-1 bis ENV-12 aus 02, Abschnitt 1 erfassen. Bezug: U-3, U-4, CL-14, CL-25.
- Voraussetzungen: WP-00.
- Betroffen: MCP-Repo, neue Datei docs/project-revival/2026-09/evidence/00-environment.md (nur lokal, noch kein Commit).
- Erlaubt: Lesezugriffe, gh CLI, Dateiinspektion, curl.
- Nicht erlaubt: Installationen ändern, Bitwig-Settings ändern, Builds.
- Schritte: Tabelle ausfüllen; API-JAR-Version aus MANIFEST oder Javadoc; Serum-Format; gh run list für PR #3/#4 mit SHA und Conclusion.
- Acceptance: alle 12 Zeilen gefüllt oder als UNKNOWN markiert; ENV-3 und ENV-6 sind Pflicht.
- Deliverable: 00-environment.md.
- Aufwand: Low 0,5 / Base 1 / High 2, Confidence hoch.
- Andy: Bitwig öffnen, Serum-Info-Dialog zeigen, gh-Login bereitstellen (ca. 15 Minuten).

### WP-02 PR #4 sauber auf Upstream aufsetzen

- Owner: Claude Code
- Zweck: den Inhalt von PR #4 ohne CRLF-Rauschen als Branch feature/allparams auf upstream/master (bc3c7b59 oder neuer) anwenden. Bezug: CL-08, DG-6, OQ-006.
- Voraussetzungen: WP-01 (ENV-3 bekannt).
- Betroffen: DrivenByMoss-Fork; Dateien ausschließlich src/main/java/de/mossgrabers/controller/osc/OSCControllerSetup.java, controller/osc/module/DeviceModule.java, framework/osc/IOpenSoundControlWriter.java, framework/osc/AbstractOpenSoundControlWriter.java, CHANGES_OSC.md.
- Erlaubt: git diff --ignore-cr-at-eol 01f629c pr/4 als Patch mit Original-Zeilenenden neu anwenden; upstream als Remote hinzufügen; Branch lokal anlegen; Push des Branches (kein PR, kein Merge).
- Nicht erlaubt: PR #3-Inhalte übernehmen; Framework-Klassen außerhalb der Liste ändern; Zeilenenden konvertieren; Verhalten ändern (das ist WP-04).
- Schritte: upstream fetch; git checkout -b feature/allparams upstream/master; Patch anwenden; git diff --stat gegen upstream/master muss nur die fünf Dateien zeigen und für die vier Java-Dateien in Summe unter 320 Zeilen liegen; CRLF-Prüfung mit file.
- Build-Befehl: mvn clean install -DskipTests --batch-mode (mit lokal erreichbarem API-JAR oder maven.bitwig.com).
- Acceptance: Build grün; Diff-Fläche wie oben; Branch gepusht; Log mit SHA in evidence/01-rebase.md.
- Live-Test: nein.
- Deliverable: Branch feature/allparams, evidence/01-rebase.md.
- Stop: mehr als 3 Konflikte außerhalb der fünf Dateien; API-Kompilierfehler, die auf API-Versionswechsel deuten (dann Rückmeldung, Reassessment-Trigger 2 prüfen).
- Aufwand: Low 1 / Base 2 / High 4, Confidence mittel.
- Andy: keiner.

### WP-03 Build, Installation und Live-Gate LG-1

- Owner: Claude Code (Ausführung und Auswertung); Andy bedient Bitwig.
- Zweck: DG-1 beantworten. Bezug: CL-15, CL-16, CL-17, OQ-004, OQ-005.
- Voraussetzungen: WP-02.
- Betroffen: DrivenByMoss-Fork Branch feature/allparams (nur der Wert von EXTENDED_PARAMETER_BANK_SIZE in OSCControllerSetup.java für Build A = 512 und Build B = 2048); Bitwig Extensions-Ordner; Testprojekt.
- Erlaubt: zwei Builds mit unterschiedlicher Konstante; Installation der .bwextension; Ausführen von PR #7-Dump-Tool oder OSC-Rohbefehlen; Konsole mitschneiden.
- Nicht erlaubt: weitere Codeänderungen; Custom-Remote-Control-Pages anlegen; Projekt ändern nach Anlage.
- Schritte: 02, Abschnitt 2 (Aufbau) und Abschnitt 3 (LG-1) exakt ausführen.
- Acceptance: Pass- oder Fail-Ergebnis mit A1..B3, S, Konsolenlog, CPU-Screenshot in evidence/02-LG1.md.
- Live-Test: ja, dies ist das Gate.
- Deliverable: evidence/02-LG1.md, Entscheidung Pass/Fail, empfohlene Bankgröße.
- Stop: Bitwig-Absturz oder Exceptions beim Laden -> Fail dokumentieren, nicht reparieren.
- Aufwand: Low 2 / Base 3 / High 6, Confidence mittel.
- Andy: ca. 60 Minuten Bitwig-Bedienung.

Bei Fail: alle folgenden Pakete entfallen. Klasse C ist dann der nächste zu verifizierende Pfad, nicht ein gesicherter Fallback (CL-26). Vor jeder C-Implementierung ist LG-2 (DG-9, 02 Abschnitt 3a) zu bestehen: Nachweis, dass die Serum-2-Parameter-IDs enumerierbar sind oder aus einer belegten vollständigen Quelle vorliegen, und dass für das Format aus ENV-6 eine createSpecificXxxDevice-Entsprechung existiert. WP-C1 wird von Fable erst nach LG-2 Pass spezifiziert und beginnt mit dem Ergebnis dieses Nachweises (ID-Quelle, Factory), nicht mit einem Extension-Grundgerüst. Bei LG-2 Fail geht die Architekturfrage an Claude Opus zurück. Nicht Teil dieses Plans.

### WP-04 Backend-Korrekturen im allparams-Handler

- Owner: Codex
- Zweck: die drei Codemängel aus CL-10, CL-11 und der Wertesemantik beheben; Version exponieren. Bezug: HD-010, PR #4 Reviews.
- Voraussetzungen: WP-03 Pass; Bankgröße aus WP-03.
- Betroffen: DrivenByMoss-Fork, Branch feature/allparams, nur DeviceModule.java, OSCControllerSetup.java, CHANGES_OSC.md.
- Erlaubt:
  1. Index = Slot-Index (Position in getParameterList().getParameters(), 0-basiert), nicht kompaktiert; nicht existierende Slots werden im Dump ausgelassen, behalten aber ihren Index; count liefert die Zahl existierender Parameter, zusätzlich /device/allparams/slots mit der Bankgröße.
  2. byname: ungültige Payload -> /device/allparams/error "byname expects [string name, number value]"; Duplikate -> /device/allparams/error "ambiguous name: <name> indices: i1,i2,..."; case-insensitiv beibehalten.
  3. Wert-Argumente für {index}/value und byname als Float 0.0..1.0 (normalisiert) akzeptieren; Integer-Werte weiterhin in der konfigurierten Resolution interpretieren (Typ entscheidet); item bleibt normalisiert. Dokumentation in CHANGES_OSC.md.
  4. Nach jedem Write eine Antwort /device/allparams/{index}/value mit dem Wert nach dem Set (aus IParameter.getValue()) senden, damit der MCP ein sofortiges Ack hat; der Observer-Flush bleibt.
  5. /device/allparams/version -> Int 1.
  6. getExistingParameters() nicht mehr im Flush pro Zyklus neu allokieren; Count nur bei Änderung senden (dump-Flag beachten).
- Nicht erlaubt: Änderungen in framework/daw, ISpecificDevice, ModelSetup, SpecificDeviceImpl; neue Klassen aus PR #3; Änderungen an /device/param/{1..8}.
- Schritte: kleine Commits je Punkt; Build nach jedem Commit.
- Build: mvn clean install -DskipTests --batch-mode (Codex-Umgebung muss API-JAR erreichen; sonst Paket an Claude Code zurückgeben, nicht raten).
- Acceptance: Build grün; CHANGES_OSC.md beschreibt alle Adressen mit Typen; Diff gegen WP-02-Stand unter 200 Zeilen.
- Live-Test: nein (WP-09).
- Deliverable: PR gegen master des Forks, Titel "OSC allparams: slot index, error channel, normalized values, ack".
- Stop: falls Punkt 4 ohne Zugriff auf getValue() im Handler-Kontext nicht möglich ist, Punkt 4 auslassen und im PR notieren.
- Aufwand: Low 3 / Base 5 / High 8, Confidence mittel.
- Andy: keiner.

### WP-05 Probe- und Dump-Werkzeug im MCP-Repo

- Owner: Codex
- Zweck: PR #7 durch ein Werkzeug mit Vollständigkeitsprüfung ersetzen. Bezug: CL-12, Live-Protokoll Schritt 3, 4, 10.
- Voraussetzungen: WP-04 Adressschema (kann parallel zu WP-04 starten, da Schema in CHANGES_OSC.md festgelegt ist; Merge erst nach WP-04).
- Betroffen: MCP-Repo, neue Datei dev_tools/allparams_probe.py, tests/dev_tools/test_allparams_probe.py.
- Erlaubt: python-osc BlockingOSCUDPServer oder eigener Empfangsthread mit Queue; Subcommands count, dump --out file.json, get <index>, set <index> <float>, byname <name> <float>, raw <address> [args]; Dump akzeptiert end nur bei Item-Zahl = begin-Count, sortiert nach Index, meldet "incomplete dump (n/m)" mit Exit-Code 2; Timeout-Parameter; Zeitstempel je Nachricht im Log.
- Nicht erlaubt: Änderungen an bitwig_mcp_server/*; Abhängigkeiten außer python-osc.
- Schritte: Implementierung; Unit-Test mit lokalem Loopback-Sender, der begin/item/end in falscher Reihenfolge und unvollständig sendet.
- Test: python -m pytest tests/dev_tools.
- Acceptance: Tests grün; Werkzeug dokumentiert in dev_tools/README.md.
- Deliverable: PR gegen main.
- Aufwand: Low 2 / Base 3 / High 5, Confidence hoch.
- Andy: keiner.

### WP-06 MCP-Tools für allparams

- Owner: Codex
- Zweck: P0 Punkte 2 bis 6 auf MCP-Ebene. Bezug: CL-06, CL-07, HD-002 (Supersede).
- Voraussetzungen: WP-04 Schema.
- Betroffen: MCP-Repo: bitwig_mcp_server/osc/client.py, osc/controller.py, osc/server.py (Dispatcher für /device/allparams/*), mcp/tools.py, mcp/resources.py, tests/osc/*, tests/test_tools.py, tests/integration/test_allparams_integration.py.
- Erlaubt:
  1. Client-Methoden allparams_count, allparams_list(offset), allparams_get(index), allparams_set(index, value_normalized), allparams_set_by_name(name, value), allparams_set_batch(list) mit OSC-Bundle.
  2. Controller: Dump-Empfang mit Vollständigkeitsprüfung (Logik aus WP-05 wiederverwendbar), Write mit Read-back-Wartezeit (Ack aus WP-04 oder nachfolgender Flush), Timeout konfigurierbar in settings.py.
  3. Tools: list_device_parameters (liefert index, name, value, display; optional Filter nach Namensteil), get_device_parameter(index), set_device_parameters(items: [{index|name, value}]) mit Ergebnis je Item {index, requested, readback, ok}, snapshot_device_parameters (Dump als JSON-Resource bitwig://device/allparams).
  4. Fehler: strukturierte Fehlermeldungen aus /device/allparams/error durchreichen; Duplikatnamen als Fehler mit Kandidaten.
  5. Backend-Versionsprüfung beim Start (/device/allparams/version), Warnung bei Abweichung.
- Nicht erlaubt: set_device_parameter (Legacy) ändern; ChromaDB-Code anfassen; neue Abhängigkeiten.
- Tests: Mock-OSC-Tests für alle Methoden; Integrationstests mit skip_if_bitwig_not_running.
- Acceptance: make test grün mit Lockfile (mcp 1.4.1); neue Tests decken Dump-unvollständig, Timeout, Duplikat, Batch-Teilerfolg ab.
- Deliverable: PR gegen main.
- Stop: falls Read-back länger als 2 s je Write braucht, Rückmeldung an Fable statt Timeout-Erhöhung.
- Aufwand: Low 6 / Base 10 / High 16, Confidence mittel.
- Andy: keiner.

### WP-07 Unabhängige Zweitprüfung

- Owner: GPT-SOL
- Zweck: Robustheit, Protokoll, Nebenläufigkeit, Edge Cases der PRs aus WP-04, WP-05, WP-06.
- Voraussetzungen: alle drei PRs offen.
- Betroffen: keine Codeänderung; Review-Kommentare in den PRs.
- Erlaubt: Review, Reproduktion mit Mocks, Vorschläge als Kommentar.
- Nicht erlaubt: Pushes, Architekturvorschläge, Reassessment.
- Prüfliste: Index-Stabilität bei Lücken; Duplikatbehandlung; Float/Int-Typunterscheidung; Bundle-Größe gegen UDP-MTU (Empfehlung für Chunk-Größe); Race zwischen Ack und Flush; Timeout-Verhalten; Reconnect; Thread-Sicherheit des Message-Cache in osc/server.py.
- Acceptance: je PR eine Liste Blocker / Nicht-Blocker; jeder Blocker mit Reproduktion.
- Deliverable: Review-Kommentare plus Zusammenfassung evidence/07-review.md (durch Claude Code abgelegt).
- Aufwand: Low 2 / Base 4 / High 6, Confidence hoch.
- Andy: keiner.

### WP-08 Blocker beheben und mergen

- Owner: Claude Code
- Zweck: Blocker aus WP-07 beheben (kleine Fixes) und die drei PRs mergen.
- Voraussetzungen: WP-07.
- Betroffen: dieselben Dateien wie WP-04/05/06.
- Erlaubt: Fixes unter 50 Zeilen je Blocker; Merge feature/allparams nach master (Fork) und der beiden MCP-PRs nach main; Tag allparams-v1 im Fork.
- Nicht erlaubt: Nicht-Blocker in diesem Paket; Merge von PR #1 bis #7.
- Build/Test: mvn clean install -DskipTests; make test.
- Acceptance: alle Blocker geschlossen; Merges erfolgt; Fork-Master baut; MCP-CI grün (Workflow-Run-URL in evidence/08-merge.md).
- Deliverable: Merges, Tag, evidence/08-merge.md.
- Aufwand: Low 2 / Base 3 / High 6, Confidence mittel.
- Andy: keiner.

### WP-09 Live-Protokoll P0 vollständig

- Owner: Claude Code (Ausführung, Auswertung); Andy bedient.
- Zweck: 02, Abschnitt 4, Schritte 1 bis 14 mit dem gemergten Stand. Bezug: P0 Punkte 1 bis 8.
- Voraussetzungen: WP-08; Extension aus Fork-Master gebaut und installiert.
- Betroffen: nur evidence/-Dateien.
- Erlaubt: Ausführung, Logs, Screenshots; Wiederholung an zweitem Tag (Schritte 4, 8, 10).
- Nicht erlaubt: Codeänderungen (Befunde gehen als Issues an WP-08-Owner zurück, neues Paket).
- Acceptance: alle 14 Schritte mit Pass oder dokumentierter Abweichung; Schritte LG-1, 3-6, 8, 13 müssen Pass sein, sonst P0 nicht bestanden.
- Deliverable: evidence/<datum>-P0-summary.md plus Rohdaten; Commit nach Abnahme.
- Aufwand: Low 4 / Base 6 / High 10 (über zwei Tage), Confidence mittel.
- Andy: ca. 2 mal 90 Minuten.

### WP-10 Cleanup und verbindliche Dokumente

- Owner: Codex
- Zweck: Abschnitt 4 dieses Plans umsetzen; verbindliche Repository-Dokumente anlegen.
- Voraussetzungen: WP-09 Pass.
- Betroffen: MCP-Repo (docs/, README.md, CLAUDE.md, AGENTS.md neu, sprint1-extended-parameter-control.md, bridge/, config(1).yml, pyproject.toml Homepage/Repository-URLs); DrivenByMoss-Fork (docs/archive/, AGENTS.md).
- Erlaubt: Verschieben nach docs/archive/ mit Hinweiszeile am Dateianfang; Anlegen von ARCHITECTURE.md (Verweis auf 01 plus Kurzfassung), STATUS.md, TEST_MATRIX.md (aus 00 Abschnitt 4 plus Live-Ergebnisse), IMPLEMENTATION_PLAN.md (dieses Dokument mit Status je Paket), AGENTS.md (Rollen, Stop-Regeln, Verbot paralleler Backends); README-URLs auf WeModulate und eigene Extension; CLAUDE.md-Roadmap durch Verweis auf STATUS.md ersetzen; PR #1, #2, #5, #6, #7-Inhalte nach docs/archive/2026-03-codex/ kopieren; Schließen von PR #1 bis #7 mit Verweis auf ADR (Closing ist Andys Klick oder gh durch Claude Code, hier nur vorbereiten).
- Nicht erlaubt: Löschen historischer Dokumente; Umschreiben von docs/project-history; Änderungen an Python-Code.
- Acceptance: kein Dokument im Root außer README.md, CLAUDE.md, AGENTS.md, CONTRIBUTING.md, LICENSE, PROJECT_INIT.md; alle verschobenen Dateien mit Archivhinweis; STATUS.md mit Evidenzstufen aus WP-09.
- Deliverable: je Repo ein PR.
- Aufwand: Low 3 / Base 4 / High 6, Confidence hoch.
- Andy: PRs #1 bis #7 schließen (5 Minuten).

### WP-11 P0-Abnahme

- Owner: Andy (fachliche Abnahme); Claude Opus prüft Vollständigkeit der Evidenz.
- Zweck: P0 formal schließen, ADR-Status FINAL bestätigen, P1-Planung freigeben.
- Voraussetzungen: WP-09, WP-10.
- Acceptance: Andy bestätigt Schritt 13 aus eigener Bedienung; Opus bestätigt, dass jede P0-Zeile der Matrix BITWIG-LIVE-VERIFIED oder INTEGRATION-CONFIRMED trägt.
- Deliverable: STATUS.md-Eintrag "P0 abgeschlossen am <Datum>".
- Aufwand: Low 0,5 / Base 1 / High 2.
- Andy: 30 Minuten.

---

## 3. Erster Implementierungsschritt nach Freigabe

WP-01 durch Claude Code, unmittelbar gefolgt von WP-02. Beide zusammen sollen an einem Arbeitstag abgeschlossen sein, damit WP-03 (LG-1) mit Andy terminiert werden kann.

---

## 4. Cleanup- und Governance-Entscheidungen

| Artefakt | Entscheidung | Ausführung |
|---|---|---|
| Oktober-Paging im Python-MCP | Nicht vorhanden (CL-01); HD-002 in STATUS.md als "nie committet, superseded durch allparams" | WP-10 |
| Remote-OSC-Sprint-1-Dokument und Issue-Template | Archivieren unter docs/archive/2025-11-sprints/; Issue schließen | WP-10, Andy |
| bridge/bitwig-extension Starter | Archivieren unter docs/archive/2025-11-bridge/ (Quellen als Referenz für den Pfad Klasse C behalten, falls LG-2 besteht) | WP-10 |
| Sprint-3-Mapping-Idee (docs/README_Sprint3.md, Issue-Template, sprint3-Branch) | Archivieren; Branch sprint3 löschen nach Archivkopie | WP-10, Andy |
| DrivenByMoss PR #1, #2, #5, #6 | Inhalte nach docs/archive/2026-03-codex/ im Fork; PRs schließen ohne Merge | WP-10, Andy |
| DrivenByMoss PR #3 | Schließen, superseded durch ADR (redundant zu ParameterListImpl) | Andy nach WP-08 |
| DrivenByMoss PR #4 | Superseded durch feature/allparams (WP-02/04); schließen mit Verweis | Andy nach WP-08 |
| DrivenByMoss PR #7 | Ersetzt durch dev_tools/allparams_probe.py; Original archivieren; schließen | WP-05, Andy |
| Widersprüchliche README/CLAUDE/AGENTS-Aussagen | README-URLs korrigieren; CLAUDE.md-Roadmap durch STATUS.md-Verweis ersetzen; AGENTS.md neu in beiden Repos | WP-10 |
| docs/project-history | Unverändert behalten; README dort um einen Verweis auf docs/project-revival/2026-09 ergänzen | WP-10 |
| config(1).yml | Archivieren oder löschen nach Prüfung des Inhalts | WP-10 |
| ChromaDB/sentence-transformers als harte Abhängigkeit | P1-Paket "optionaler Import" nach P0 | später |

Verbindliche Dokumente nach WP-10 (MCP-Repo): ARCHITECTURE.md, STATUS.md, TEST_MATRIX.md, IMPLEMENTATION_PLAN.md, AGENTS.md. Im Fork: AGENTS.md, CHANGES_OSC.md.

---

## 5. Stop- und Reassessment-Regeln (Wiederholung, verbindlich)

- Kein Produktivcode vor WP-00-Freigabe; kein Code über WP-03 hinaus vor LG-1 Pass.
- Keine P2/P3-Arbeit vor WP-11.
- Reassessment nur über die vier Trigger in 01, Abschnitt "Reassessment Triggers"; Anträge gehen an Claude Opus.
- Jedes Paket hat genau einen Owner; Abweichungen im Paket führen zu Rückgabe, nicht zu Umverteilung.
- Kein Merge, um investierte Arbeit zu retten; PR #3 wird auch dann nicht gemergt, wenn er baut.
