# Open Questions From History

Diese Datei sammelt ausschließlich historisch offene Punkte. Sie löst sie nicht.

## Parameterpfad

### OQ-001 — Wurden die sechs Oktober-Extended-Parameter-Tools live in Bitwig getestet?

Code-/Review- und Commit-Vorbereitung sind sekundär belegt. Ein eindeutiger Live-Test aller sechs Tools ist nicht erhalten.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-002 — Wie hießen die sechs Oktober-Tools exakt?

Die verfügbare Zusammenfassung nennt Anzahl und Zweck, aber nicht die vollständigen Toolnamen/Signaturen.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-003 — Wurde das Oktober-Paging später bewusst entfernt oder nur nicht weiterverfolgt?

Kein expliziter Ablöse-/Löschbeschluss gefunden.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-004 — Ist `allparams` jemals erfolgreich kompiliert worden?

Die PRs dokumentieren nur fehlgeschlagene Maven-Builds wegen HTTP 403.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-005 — Ist `allparams` jemals in Bitwig live gelaufen?

Kein Live-Testbeleg in den PRs oder zugänglichen historischen Zusammenfassungen.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-006 — Welche DrivenByMoss-PRs müssen technisch gemeinsam betrachtet werden?

PR #3 und #4 bilden offensichtlich Framework + OSC. Ob die Branches sauber kombinierbar waren, wurde historisch nicht vollständig verifiziert.

**Status:** UNKNOWN – historical evidence insufficient

## DrivenByMoss vs. eigene Extension

### OQ-007 — Brauchen wir langfristig DrivenByMoss?

Historisch offen. November plante zusätzliche Wege; März investierte erneut in DrivenByMoss.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-008 — Welche Funktionen benötigen wirklich eine eigene Bitwig Extension?

Die November-Roadmap nahm dies an, aber die exakte Capability-Grenze ist nicht vollständig erhalten.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-009 — War die Java Bridge Ergänzung oder sollte sie DrivenByMoss teilweise ersetzen?

Kein eindeutiger finaler Beschluss.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-010 — Wurde WebSocket je funktionsfähig implementiert?

Der Starter nennt `WSHandler` ausdrücklich als noch zu implementieren.

**Status:** UNKNOWN – historical evidence insufficient

## Remote OSC / Mapping

### OQ-011 — Wurde Remote-OSC durch `allparams` funktional überholt?

Beide adressieren Parameter-Skalierung über unterschiedliche Wege. Ein ausdrücklicher Supersede-Beschluss fehlt.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-012 — Wurden YAML-Mappings für EQ+, Compressor und Tool/Utility erstellt?

Sprint 1 plante sie; keine abgeschlossene Umsetzung gefunden.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-013 — Wurde Sprint 3 Unified Mapping praktisch implementiert?

Plan-/Template-Artefakte sind sichtbar, Produktivcode ist nicht belegt.

**Status:** UNKNOWN – historical evidence insufficient

## Browser / Navigation

### OQ-014 — Welche Browser-Funktionen wurden am 22.11.2025 konkret live getestet?

Der Chatindex nennt Browser-Index, der vollständige Testablauf ist nicht verfügbar.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-015 — Wie vollständig funktionierten ChromaDB und Device Recommendations im eigenen Setup?

Upstream-Code existierte; vollständiger eigener Live-Test nicht belegt.

**Status:** UNKNOWN – historical evidence insufficient

## Weitere Capabilities

### OQ-016 — Welche Funktionen machten die historische „ca. 50 %“-Abdeckung aus?

Der Rumble-/Capability-Chat ist nicht als Volltext verfügbar.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-017 — Welche Funktionen waren dokumentiert, aber nie live getestet?

Ohne Original-Testprotokolle nicht vollständig trennbar.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-018 — Welche Clip-/Arrangement-/Automation-Funktionen waren real erreichbar?

Roadmap zeigt Bedarf, aber keinen vollständigen Ist-Zustand.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-019 — Bounce/Export

Historisch als spätere Capability bzw. im Sprint 1 als out of scope behandelt. Ob ein tragfähiger API-Weg entstand, wurde in dieser Historienrekonstruktion nicht neu untersucht.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-020 — Save / New Project

Keine belastbare historische Implementierung gefunden.

**Status:** UNKNOWN – historical evidence insufficient

## Architekturprozess

### OQ-021 — Welcher Ansatz war beim Einschlafen des Projekts zuletzt akzeptiert?

Die Historie endet nicht mit einem einzigen finalen Architecture Decision Record. März hinterließ mehrere ungemergte PRs.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-022 — Welche November-Artefakte wurden absichtlich verworfen und welche nur liegen gelassen?

Die Add/Delete/Add-Commitfolge zeigt Bewegung, aber keine klare Begründung.

**Status:** UNKNOWN – historical evidence insufficient

### OQ-023 — Welche damaligen KI-Reviewaussagen wurden später widerlegt?

Dokumentiert ist, dass im März sieben Reviewpunkte übernommen und ein spekulativer Codevorschlag verworfen wurde. Eine vollständige Matrix älterer Review-Widersprüche fehlt.

**Status:** UNKNOWN – historical evidence insufficient

## Historische Prüfobjekte für einen späteren Audit

Ohne hier eine Empfehlung auszusprechen, sollte ein späterer Audit diese Artefakte getrennt betrachten:

- Oktober-Paging im Python-MCP,
- November Remote-OSC Sprint 1,
- November Java-Bridge-Starter,
- November Sprint-3-Mapping,
- März DrivenByMoss PR #3/#4,
- März Design-/Test-PRs #5/#6,
- März Serum-Dump-Tool #7.
