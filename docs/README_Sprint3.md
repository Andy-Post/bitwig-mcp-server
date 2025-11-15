# Sprint 3 – Unified Mapping & Controller Integration

Ziel: Letzter Sprint des Projekts. Auf Basis von Sprint 1 (Remote-OSC) und Sprint 2 (Bridge)
entsteht eine einheitliche Mapping-Engine, eine Controller-/MCP-Integrationsebene und eine
minimale Persistenz für Mappings (nicht für Parameterwerte).

## 3.1 Unified Mapping Engine

- Abstraktion: Track -> Device -> Parameter -> Slot (Remote-OSC /user/0001...)
- Name- und Pattern-basiertes Matching (Track/Device/Param):
  - Explizite Namen
  - Index
  - Einfache Regeln wie „erstes EQ+ auf Track X“
- Validierung: prüft, ob alle referenzierten Parameter existieren und ob Slots mehrfach belegt sind.

Vorgeschlagene Pfade:
- `bitwig_mcp_server/core/mapping_engine.py`
- `tests/test_mapping_engine.py`

## 3.2 Controller/MCP Integration

- Ableitung von „Pages/Banks“ für Controller aus der Mapping-Engine:
  - Welche Tracks auf einer Page?
  - Welche Geräte pro Track?
  - Welche Parameter pro Page?
- Kürzung/Normalisierung von Namen für Scribble-Strips/Displays.
- Navigation:
  - Nächste/vorherige Track-Bank
  - Nächste/vorherige Device-Bank
  - Parameter-Seiten

## 3.3 Minimale Mapping-Persistenz

- Speichern/Laden von Mapping-Profilen (Regeln, keine Parameterwerte).
- Format: YAML oder JSON.
- Vorschlag Pfad:
  - `~/.mcp_bitwig/mappings/<profile>.yml`
- CLI-Tools (Beispiele):
  - `mapping_export --profile live_default`
  - `mapping_import --profile live_default`

## Tests

- Unit-Tests für Mapping-Engine (Auflösung + Validierung).
- Unit-Tests für Persistenz (Export/Import).
- Optionaler Integrationstest mit Bridge + Remote-OSC.

## Nicht in diesem Sprint

- Kein voll ausgebautes Snapshot-System für Parameterwerte.
- Kein Undo/History-System.
- Keine komplexen Preset-Browser-/Template-UI.
