---
name: "Sprint 1 – Extended Parameter Control (Remote‑OSC)"
about: Implement Remote‑OSC integration, batch parameter control, YAML mappings, and smoke tests
title: "Sprint 1 – Extended Parameter Control (Remote‑OSC)"
labels: ["sprint", "enhancement"]
assignees: []
---

## Goal
Implement **Remote‑OSC integration** in the Python MCP server with **batch parameter control** and **YAML mappings** (EQ+, Compressor, Utility) including smoke tests and documentation.

---

## Scope (What we will do)
- Integrate `python-osc` and set default ports.
- Implement OSC bundle sender and Remote‑OSC wrapper.
- Provide YAML mappings and loader with scales/ranges.
- Add smoke tests for EQ+ and Compressor.
- Optional: VU feedback logging via `/track/<n>/vu/(peak|rms)`.

## Out of Scope (What we won't do)
- Export/Bounce automation (UI feature, no public controller API).
- Generic I/O routing; limit to device-level sidechain parameters.

---

## Tasks

### 1) Project Setup & Dependencies
- [ ] Add `python-osc` to `pyproject.toml` (or `requirements.txt`) and install.
- [ ] Introduce config with default ports (see **Ports** below).
- [ ] Add structured logging (send-time, bundle size, round-trip).

### 2) Core Implementation
- [ ] `core/osc_client.py` – generic OSC **bundle** sender (supports batching).
- [ ] `integrations/remote_osc.py` – wrappers for:  
      - Write: `/user/0001…/1024`  
      - Read (optional): `/track/<n>/vu/(peak|rms)`
- [ ] `tools/set_extended_parameters.py` – batch handler:  
      ```
      set_extended_parameters(track_name, device_name, {param: value, ...})
      ```

### 3) Mapping & Data
- [ ] `core/mapping_loader.py` – YAML/JSON → internal mapping model (device, slot, scale).
- [ ] Provide example mappings for **EQ+**, **Compressor**, **Tool (Utility)** with realistic ranges.
- [ ] Validation & warnings for missing parameters; schema/version key.

### 4) Tests
- [ ] Unit: bundle packing & send timing (< 50 ms for local send of ≥ 64 params).
- [ ] Smoke: EQ+ & Compressor → ≥ 24 params in < 100 ms applied.
- [ ] Optional: CLI log of VU peak/rms for `/track/1` for manual verification.

### 5) Documentation
- [ ] `README_Sprint1.md` with setup, ports, mapping examples, and smoke test steps.
- [ ] Troubleshooting (port conflicts, feedback loops, packet loss).

---

## Acceptance Criteria
- [ ] Local OSC round-trip **< 10 ms** (localhost).
- [ ] **≥ 64 parameters** per device **< 100 ms** (batch) without packet loss.
- [ ] Mappings for EQ+/Compressor/Utility load and apply correctly.
- [ ] Optional VU feedback visible in logs/CLI for at least one track.
- [ ] Documentation & tests merged into `main`.

---

## Ports (Defaults – configurable via ENV/CLI)
- **Remote‑OSC**:  
  - In (Bitwig receives): `127.0.0.1:8000`  
  - Out (Bitwig sends): `127.0.0.1:8001`  
  - Addresses: `/user/0001…/1024`, `/track/<n>/vu/(peak|rms)`
- **DrivenByMoss (optional)**: In `9000`, Out `9001`
- **Java Bridge (Sprint 2)**: WS `7011` (or OSC `7010/7012`)

---

## References
- Project Briefing: _MCP × Bitwig – Gesamt‑Spezifikation für Codex (Briefing)_
- Repos: `bitwig-mcp-server`, `Bitwig-Remote-OSC`, `DrivenByMoss`, `bitwig-extensions`

---

## Definition of Done
- ✅ Feature complete, tests green, docs updated
- ✅ No port conflicts, no feedback loops; logs contain timing & bundle-size info
- ✅ Ready for Sprint 2 (Java Bridge for stable targeting)
