# 02 Live Verification Protocol: P0 Vertical Slice

Stand: 04.09.2026, Revision 1 (Korrekturen K-1 bis K-5 aus WP-00 eingearbeitet)
Ausführende: Claude Code (Befehle, Auswertung, Evidenzablage) und Andy (Bitwig-Bedienung, Installation, Abnahme)
Evidenzablage: docs/project-revival/2026-09/evidence/<datum>-<schritt>/ im MCP-Repo (Logs, JSON-Dumps, Screenshots, kurzes Protokoll)

Grundregel: Kein Schritt gilt als bestanden, wenn nur "es reagiert" beobachtet wurde. Jeder Write braucht Read-back.

---

## 1. Gebündeltes Local-Evidence-Paket (einmalig, vor allem anderen)

Claude Code erfasst und legt als evidence/00-environment.md ab:

| ID | Wert | Befehl / Quelle |
|---|---|---|
| ENV-1 | Betriebssystem und Version | uname -a bzw. winver / sw_vers |
| ENV-2 | Bitwig-Studio-Version | Bitwig: Hilfe > Über; Dateiname des Installers |
| ENV-3 | Controller-API-Version und JAR-Pfad | Bitwig-Installationsordner, resources/extension-api oder Dokumentation; ls, jar tf, MANIFEST |
| ENV-4 | Lokale Java-Version | java -version; Bitwig-eigene JRE-Version aus dem Installationsordner |
| ENV-5 | Maven-Version und ob maven.bitwig.com erreichbar ist | mvn -version; curl -I https://maven.bitwig.com/com/bitwig/extension-api/21/extension-api-21.pom |
| ENV-6 | Serum-2-Version und Plugin-Format in Bitwig (VST3 oder CLAP), Plugin-ID | Bitwig Plugin-Browser, Serum-Info-Dialog |
| ENV-7 | Installierte DrivenByMoss-Version (Bitwig Extensions-Ordner) | ls Extensions/; Datei-Datum |
| ENV-8 | Python-Version, uv-Version | python --version; uv --version |
| ENV-9 | MCP-Client(s), die für Schritt 13 genutzt werden (Claude Desktop, Claude Code, Version) | Client-Einstellungen |
| ENV-10 | Ergebnis von gh run list --repo Andy-Post/DrivenByMoss (PR #3/#4 Runs, Conclusion, SHA) | gh CLI |
| ENV-11 | OSC-Ports in DrivenByMoss OSC-Settings (Receive, Send) und Value Resolution | Bitwig Controller-Settings |
| ENV-12 | Anzahl Parameter, die Serum 2 selbst meldet (Plugin-eigene Liste oder DAW-Automation-Liste) | Bitwig Automation-Parameterliste oder Serum-Dokumentation |

Fehlt ein Wert, wird er als UNKNOWN eingetragen; kein Schritt wird deshalb übersprungen, außer ENV-3 fehlt (dann kein Build möglich).

---

## 2. Testaufbau

1. Bitwig-Projekt bitwig-mcp-p0.bwproject: ein Instrument-Track mit Serum 2, keine weiteren Devices, keine Custom-Remote-Control-Pages (Remote-Controls im Zustand "Default"). Projekt wird nach Anlage nicht mehr verändert; für Schritt 5 wird eine Kopie verwendet.
2. Serum-2-Ausgangszustand: Init-Preset des Plugins (Name notieren). Vor jedem Write-Test zurücksetzen.
3. Backend: Build aus WP-03 (siehe 03_IMPLEMENTATION_PLAN.md), Datei DrivenByMoss-<version>-allparams.bwextension, im Extensions-Ordner installiert; OSC-Controller in Bitwig mit Receive 8000, Send 9000, Value Resolution "High (16384)" (Wert für ENV-11 notieren; das Protokoll rechnet mit dem konfigurierten Wert).
4. Werkzeug für Rohzugriff: dev_tools/allparams_probe.py (WP-05) im MCP-Repo, das OSC senden und alle /device/allparams/* Antworten mit Zeitstempel loggen kann, sowie beliebige Adressen abfragt. Bis WP-05 fertig ist: PR #7 serum2_param_dump.py nur für Schritt 3, mit dem Hinweis auf die bekannte Race Condition (CL-12).
5. Vor jedem Schritt: Bitwig-Controller-Skript-Konsole öffnen und Log mitschneiden.

---

## 3. Live-Gate LG-1 (entscheidet ADR-Status)

Voraussetzung: WP-03 gebaut, Extension geladen, Serum 2 ausgewählt (Cursor Device).

Ablauf:
1. Build A mit numListParams = 512: /device/allparams/count senden, Antwort A1 notieren. Dump /device/allparams/list, Item-Zahl A2 und begin-Count A3 notieren.
2. Build B mit numListParams = 2048: gleiches Vorgehen, Werte B1, B2, B3.
3. Serum-2-eigene Parameterzahl ENV-12 als Referenz S.
4. Bitwig-Konsole auf Fehler/Exceptions prüfen; CPU-Last in Bitwig bei Build B über 60 s beobachten (Screenshot Performance-Anzeige).

Pass-Kriterien (alle müssen gelten):
- A1 = A2 = A3 oder A1 = 512 mit B1 = B2 = B3 < 2048 (d. h. eine Bankgröße existiert, bei der Count nicht am Limit liegt).
- B1 liegt bei mindestens 90 % von S oder die Abweichung ist durch Bitwig-seitig nicht automatisierbare Parameter erklärbar (Notiz mit Beispielen).
- Keine Exceptions in der Konsole; Bitwig bleibt bedienbar; Extension-Ladezeit unter 10 s.

Fail-Kriterien (eines genügt):
- B1 = 2048 (Limit erneut erreicht) und S > 2048: Seitenmodell reicht für Serum 2 nicht.
- B1 unter 50 % von S ohne Erklärung: Default-Seiten liefern die Parameter nicht vollständig.
- Reproduzierbare Exceptions oder Bitwig-Instabilität bei Build B.

Ergebnis Pass: ADR wird FINAL, Klasse B, Bankgröße = kleinste Zweierpotenz über B1 plus 25 % Reserve, im Code als Konstante dokumentiert.
Ergebnis Fail: WP-04 bis WP-09 werden gestoppt. Klasse C (Plugin-Parameter-ID über SpecificPluginDevice) ist dann der nächste zu verifizierende Architekturpfad, nicht ein gesicherter Fallback (CL-26). Vor jeder C-Implementierung ist LG-2 (Abschnitt 3a) zu bestehen; WP-C1 wird von Fable so spezifiziert, dass es mit diesem Nachweis beginnt, nicht mit einem Extension-Grundgerüst.

---

## 3a. Live-Gate LG-2 (DG-9; nur nach LG-1 Fail, vor jeder Arbeit an Klasse C)

Zweck: nachweisen, ob Klasse C für Serum 2 überhaupt gangbar ist. Bezug: CL-18, CL-26, U-3, ENV-3, ENV-6, ENV-12.

Voraussetzung: LG-1 Fail dokumentiert; ENV-3 (API-JAR) und ENV-6 (Serum-2-Plugin-Format und Plugin-ID) aus WP-01 vorhanden.

Ablauf:
1. Nachweis (a), ID-Quelle: Controller-API-JAR aus ENV-3 (Javadoc, jar tf, javap) auf eine Methode prüfen, die die Parameter-IDs eines SpecificPluginDevice zur Laufzeit auflistet. Ergebnis mit Klassen-/Methodennamen und API-Version festhalten. Existiert keine solche Methode: eine externe, belegte und vollständige ID-Quelle für Serum 2 benennen (z. B. Plugin-Dokumentation oder Host-Dump mit ID-Ausgabe), Anzahl der IDs gegen ENV-12 vergleichen und Herkunft dokumentieren.
2. Nachweis (b), Formatunterstützung: für das in ENV-6 ermittelte Format (VST3 oder CLAP) die passende Factory in der API prüfen (createSpecificVst3Device belegt, CL-18; CLAP-Entsprechung unbelegt). Bei CLAP ohne Entsprechung: prüfen und dokumentieren, ob Serum 2 zusätzlich als VST3 installiert und im Testprojekt verwendbar ist.
3. Minimalprobe: mit der ID-Quelle aus Schritt 1 und der Factory aus Schritt 2 einen einzelnen bekannten Serum-2-Parameter über createParameter(id) lesen und schreiben (Read-back). Umfang: ein Wegwerf-Extension-Stub, keine Architektur, kein Transport. Konsolen-Log und Ergebnis in evidence/<datum>-LG2.md.

Pass-Kriterien (alle müssen gelten):
- (a) Eine Enumerationsmethode ist in der API belegt, oder eine externe ID-Quelle deckt mindestens 90 % von ENV-12 ab und ist reproduzierbar (nicht handgepflegt je Preset).
- (b) Für das tatsächliche Format aus ENV-6 existiert eine createSpecificXxxDevice-Factory, oder Serum 2 ist im unterstützten Format verwendbar.
- Minimalprobe: Read-back entspricht dem geschriebenen Wert.

Fail-Kriterien (eines genügt):
- Keine Enumerationsmethode und keine reproduzierbare vollständige ID-Quelle: C hätte dieselbe manuelle Mapping-Last wie G.
- Format aus ENV-6 ohne Factory und ohne Ausweichformat.
- Minimalprobe scheitert reproduzierbar.

Ergebnis Pass: CL-26 wird auf SOURCE-CONFIRMED bzw. BITWIG-LIVE-VERIFIED gesetzt; Fable spezifiziert WP-C1 (Extension-Grundgerüst) auf Basis der belegten ID-Quelle und Factory.
Ergebnis Fail: Klasse C ist verworfen; keine weitere Implementierung; die Architekturfrage geht an Claude Opus zurück (Reassessment ohne vorbestimmten Pfad).

---

## 4. P0-Prüfschritte (nach LG-1 Pass, nach WP-04/WP-06)

Für jeden Schritt: Befehl, Rohantwort, Ergebnis, Abweichung, Datei in evidence/.

### Schritt 1: Umgebung
Erledigt durch Abschnitt 1. Zusätzlich: Commit-SHAs von MCP-Repo und DBM-Fork, aus denen gebaut wurde, in evidence/00-environment.md.

### Schritt 2: Testprojekt und Ausgangszustand
Projektdatei, Serum-Preset-Name, Screenshot der Remote-Control-Ansicht (Default-Pages sichtbar). Hash der Projektdatei (sha256sum).

### Schritt 3: Vollständige Enumeration
/device/allparams/list; Antwort als JSON speichern (index, name, displayValue, normalizedValue). Pass: begin-Count = Item-Zahl = end erhalten; Count aus Schritt LG-1 identisch.

### Schritt 4: Wiederholte Dumps
Drei Dumps hintereinander ohne Änderung. Pass: identische Count, identische Index-Name-Zuordnung (diff der JSON-Dateien leer bis auf displayValue-Rundung).

### Schritt 5: Stabilität über Zustandswechsel
Je ein Dump nach: (a) anderes Device wählen und zurück, (b) Extension in Bitwig deaktivieren/aktivieren, (c) Projekt schließen und neu laden, (d) Bitwig-Neustart. Pass: Index-Name-Zuordnung in allen vier Fällen identisch zu Schritt 3. Abweichung dokumentieren; bei Abweichung in (c) oder (d) ist der Slot-Index als kanonische Identität gescheitert und der Name-plus-Slot-Fingerprint (WP-06) wird zwingend.

### Schritt 6: Read und Write an erstem, mittlerem, letztem Parameter
Für index 0, floor(count/2), count-1: Wert lesen (item), Write auf 25 %, 50 %, 75 % des Wertebereichs, jeweils Read-back über /device/allparams/{index}/value-Antwort bzw. erneuten Dump, danach Ausgangswert zurückschreiben und erneut lesen. Pass: Read-back entspricht dem geschriebenen Wert innerhalb der Auflösung (1/16384 bei High); Serum-GUI zeigt die Änderung (Screenshot für mittleren Parameter).

### Schritt 7: Kanonische Identität und Name-Lookup
- Zwei Parameter mit identischem Namen im Dump suchen (bei Serum 2 zu erwarten, z. B. Osc A/B); byname mit diesem Namen: erwartete Antwort ist ein strukturierter Fehler mit Liste der Kandidaten-Indizes (WP-04-Verhalten), kein stiller Erst-Treffer.
- byname mit abweichender Groß-/Kleinschreibung: Pass = Treffer (case-insensitiv dokumentiert).
- byname mit Sonderzeichen (z. B. Name mit Schrägstrich oder Prozent) aus dem Dump: Pass = Treffer.
- byname mit nicht vorhandenem Namen: Pass = /device/allparams/error "parameter not found".
- Adressierung per Slot-Index gegen Adressierung per kompaktiertem Index: bei mindestens einer Lücke im Dump (exists = false) muss der Slot-Index den Parameter stabil treffen.

### Schritt 8: Batch-Write
64 Parameter (Indizes 0..63 oder die ersten 64 existierenden) in einem OSC-Bundle auf definierte Werte setzen; danach Dump. Pass: alle 64 Werte im Dump entsprechen den gesetzten Werten; Dauer vom Senden bis zum vollständigen Read-back unter 500 ms (Log-Zeitstempel). Zurücksetzen und erneut prüfen. Wiederholen mit 256 Parametern; falls UDP-Verluste auftreten, Chunk-Größe ermitteln (128, 64, 32) und im Protokoll festhalten.

### Schritt 9: Fehlerfälle
- Kein Device (leerer Track auswählen): list, count, 0/value -> jeweils /device/allparams/error "no device selected".
- Ungültiger Index (count+10, -1, "abc"): error mit "index out of range".
- Malformed byname (nur ein Argument, Argument falschen Typs): error, keine Exception in der Konsole (CL-11 behoben).
- Timeout: Backend-Extension deaktivieren, MCP-Tool aufrufen -> strukturierter Timeout-Fehler im MCP innerhalb der konfigurierten Frist, kein Hänger.
- Verspätete Antwort: Dump mit künstlich kleinem MCP-Timeout starten -> MCP meldet unvollständigen Dump mit erhaltenem/erwartetem Count, kein Teilerfolg.

### Schritt 10: Dump-Reihenfolge und Abschluss
Dump-Tool (WP-05) muss end erst akzeptieren, wenn Item-Zahl = begin-Count; sonst Fehler "incomplete dump (n/m)". Drei Läufe, dabei einmal Bitwig-CPU-Last durch Playback erzeugen. Pass: kein falsch positiver Abschluss; Items in aufsteigender Index-Reihenfolge oder vom Tool sortiert und lückenfrei.

### Schritt 11: Regression /device/param/{1..8}
Über bestehendes Tool set_device_parameter Parameter 1 der aktuellen Remote-Control-Seite setzen, über Resource bitwig://device/parameters lesen. Pass: unverändert funktionsfähig; Seitenwechsel per /device/param/+ ändert allparams-Ergebnisse nicht.

### Schritt 12: Reconnect
(a) MCP-Server-Prozess beenden und neu starten, Tool aufrufen. (b) Bitwig-Extension deaktivieren/aktivieren bei laufendem MCP, Tool aufrufen. Pass: beide Fälle liefern nach maximal einem Retry korrekte Antworten; Log zeigt Reconnect-Meldung.

### Schritt 13: End-to-End mit realem MCP-Client
Client aus ENV-9 (mindestens einer): Prompt "Liste alle Parameter des ausgewählten Devices und setze den Parameter <Name aus Schritt 6> auf 50 %". Pass: Client zeigt die Liste mit Count, der Write ist in Serum-GUI sichtbar, das nachfolgende Tool-Ergebnis enthält den zurückgelesenen Wert. Screenshot von Client und Bitwig.

### Schritt 14: Evidenzablage
Alle Rohantworten (JSON), Konsolen-Logs, MCP-Logs, Screenshots und ein Protokoll evidence/<datum>-P0-summary.md mit Tabelle Schritt / Ergebnis / Abweichung / Evidenzdatei. Commit im MCP-Repo durch Claude Code nach Freigabe durch Andy.

---

## 5. Erwartete Ergebnisse in Kurzform

| Schritt | Erwartung | Evidenzstufe bei Pass |
|---|---|---|
| LG-1 | Count stabil, unter Banklimit, nahe Serum-Referenz | BITWIG-LIVE-VERIFIED für Enumeration |
| 3-5 | Dumps identisch über Zustandswechsel | BITWIG-LIVE-VERIFIED für Identität |
| 6, 8 | Writes per Read-back bestätigt | BITWIG-LIVE-VERIFIED für Read/Write/Batch |
| 7, 9, 10 | definierte Fehler, kein Teilerfolg | INTEGRATION-CONFIRMED für Protokoll |
| 11, 12 | Legacy und Reconnect intakt | INTEGRATION-CONFIRMED |
| 13 | Client bis Bitwig und zurück | BITWIG-LIVE-VERIFIED End-to-End |

Ein einzelner Durchlauf ist kein Stabilitätsnachweis. Schritte 4, 8 und 10 werden an einem zweiten Tag wiederholt, bevor P0 als abgeschlossen gilt.
