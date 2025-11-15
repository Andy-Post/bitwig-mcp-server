# Sprint 3 – Unified Mapping & Controller Integration

## Summary
Final sprint: build a unified mapping engine on top of the bridge (WS) and Remote-OSC,
integrate it with the controller/MCP layer, and add minimal mapping persistence.

## Related
- Closes: #<IssueNr>
- Base: `main`
- Compare: `feature/sprint3-unified-mapping`

## Changes
### 3.1 Unified Mapping Engine
- feat(mapping): introduce mapping engine (Track -> Device -> Param -> Slot)
- feat(mapping): rule-based resolution (by name, index, simple patterns)
- feat(mapping): validation of mappings (missing params, conflicting slots)

### 3.2 Controller/MCP Integration
- feat(controller): add page/bank abstraction for controller surfaces
- feat(controller): label/compact parameter names for displays
- feat(controller): helpers for bank navigation (tracks/devices/params)

### 3.3 Minimal Mapping Persistence
- feat(persistence): export/import mapping profiles (YAML/JSON)
- feat(cli): mapping_export / mapping_import commands
- docs: add docs/README_Sprint3.md
- test: add tests for mapping resolution, validation, and persistence

## Test Instructions
1. Build & run environment as in Sprint 1 & 2.

2. Mapping engine:
   ```bash
   uv run pytest tests/test_mapping_engine.py
