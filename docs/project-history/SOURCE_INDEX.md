# Source Index

## Historische Claude-Chats

| Datum | Titel/Thema | Quelle | Relevanz |
|---|---|---|---|
| 11.10.2025 | Initialisierungs-Timeout-Diagnose | https://claude.ai/chat/7f9ceb3a-f05f-4724-a197-544f3fd9f1d9 | Initialize, Start, OSC |
| 14.10.2025 | STDIO-Transport-Implementierung | https://claude.ai/chat/131508b6-6d3a-4335-bda5-c3fb10900aa7 | STDIO/Serverstart |
| 14.10.2025 | Tool-Dokumentation (27 Tools) | https://claude.ai/chat/9340544a-edc6-4503-8ee9-231341b28e04 | Tool-Inventar |
| 14.10.2025 | Tool-Referenz & Beispiel-Prompts | https://claude.ai/chat/f87c326c-fdf7-4b56-a035-8b1b61db2864 | Doku/Prompts |
| 16.10.2025 | 8-Parameter-Limit als Blocker | https://claude.ai/chat/1877585b-87be-4cf0-a3ca-e7298122721c | Parameterblocker |
| 16.10.2025 | Extended-Parameter-Tools | https://claude.ai/chat/c62e5aa1-327a-4679-a3ba-055b5f3ab49b | sechs Paging-Tools |
| 23.10.2025 | Änderungen für Git-Commit | https://claude.ai/chat/799a9327-17b2-4292-a4f4-f7037e37fe11 | Übergabestand |
| 12.11.2025 | Grenzen der DrivenByMoss-Automatisierung | https://claude.ai/chat/f33b9784-e991-4fc3-88d6-032dc44f2b24 | Rumble/Capability-Gap |
| 12.11.2025 | 4-Phasen-Roadmap | https://claude.ai/chat/57687973-ea35-42f0-a6e7-6af2d99b750c | OSC/Java/WebSocket |
| 22.11.2025 | Live-Test / Browser Index | https://claude.ai/chat/223c65f3-ec73-4a60-951f-bda28e9f784f | Live-Verifikation |
| 10.03.2026 | DrivenByMoss-Codex-Auftrag kritisch überarbeitet | https://claude.ai/chat/8c94b414-a216-437c-bcda-dfded6a93f7f | allparams-Auftrag |
| 10.03.2026 | Codex-Tasks mit GPT-5.4-Pro-Review | https://claude.ai/chat/aab9edb5-324f-4a1b-835e-9a8720bdcbe4 | Multi-Modell-Review |
| 10.03.2026 | Codex/GitHub ohne lokalen Clone | https://claude.ai/chat/27dbdcbf-1428-4102-a3b9-e8a2e2c6ed15 | Workflow |
| 11.03.2026 | GitHub-Anbindung in Codex | https://claude.ai/chat/af20bd72-f6d2-4b41-92fa-1c54a5cdab0f | PR-/Branch-Setup |
| 13.03.2026 | MCP vs. DrivenByMoss abgegrenzt | https://claude.ai/chat/ec37d599-56e9-495c-acf8-72358e3ed9d0 | Zuständigkeitsgrenzen |

## Späterer separater Bitwig-Kontext

Nicht als Beleg für MCP-Fortschritt verwenden:

- MX-1, 07.08.2026: https://claude.ai/chat/fadba791-b278-4385-b24b-6abf913ac9cf
- MX-1 Review: https://claude.ai/chat/5250001f-e5a3-4d69-9822-168b17670ea1
- MX-1 final: https://claude.ai/chat/4fc4d092-44e4-4f3e-a1ba-c70d68e88828
- TB-3: https://claude.ai/chat/64f3f172-b2a3-459c-918d-08906173c389
- TB-3 v3: https://claude.ai/chat/c0cc8d83-5324-49db-8c95-19cfb8f281cb
- TB-3 final review: https://claude.ai/chat/4137980f-aa62-438d-9ba2-2189c11b454a

## Library-/Zusammenfassungsquellen

### `A_Chat_Liste(1).md`

Chronologischer Index technischer Chats August 2025–August 2026. Liefert Datum, Titel und originale Claude-Chat-URLs.

### `B_Chat_Zusammenfassungen_GESAMT_v3.md`

Sekundärquelle für:

- Chats 35–41: Oktober-MCP-Serie,
- Chats 42–44: November-Grenzen/Roadmap/Live-Test,
- Chats 142–150: März-DrivenByMoss/Codex-Serie.

### `C_Kompetenzprofil*.md`

Nachträgliche Projektverdichtung mit:

- 60-s-Initialize-Timeout,
- OSC-Ports,
- 8-von-40+-Parameterproblem,
- Pagingformel,
- sechs Extended-Parameter-Tools,
- Rumble-/50-%-Bewertung.

Diese Quelle wurde nicht allein verwendet, um unklare Live-Verifikation zu behaupten.

## GitHub — bitwig-mcp-server

Repository: https://github.com/Andy-Post/bitwig-mcp-server

Wichtige Artefakte:

- README: https://github.com/Andy-Post/bitwig-mcp-server/blob/main/README.md
- Sprint 1: https://github.com/Andy-Post/bitwig-mcp-server/blob/main/sprint1-extended-parameter-control.md
- Bridge-Starter: https://github.com/Andy-Post/bitwig-mcp-server/tree/main/bridge/bitwig-extension
- Issues: https://github.com/Andy-Post/bitwig-mcp-server/issues
- Commits: https://github.com/Andy-Post/bitwig-mcp-server/commits/main/

Wichtige Commits:

- `89147a9` — Sprint-1 Remote-OSC, 12.11.2025  
  https://github.com/Andy-Post/bitwig-mcp-server/commit/89147a94c8502ca1e892f75c8b2fc443e4fa7d81
- `a6dafa3`, `ec2d0fd`, `7ac0941`, `85785a6`, `04acf5c` — Bridge-Artefaktbewegungen, 12.11.2025
- `eba24bc` — Sprint-3 Unified Mapping & Controller, 15.11.2025  
  https://github.com/Andy-Post/bitwig-mcp-server/commit/eba24bcd0387d9f68d5bb58e3956d82fc58cca07
- `a331116`, `3b51a8b`, `61c943d`, `507500a` — weitere Sprint-3-Überarbeitungen.

Übernommene Upstream-Historie im Fork:

- `120f7a0` — Browser Indexer + OSC Query Tools, 10.04.2025
- `d750cdc` — reale MCP-Integrationstests mit Bitwig, 28.03.2025
- `1cfa2da` — Browser-Pagination-Integrationstests, 28.03.2025
- `2dd47d2` — Device-spezifische URIs, 25.03.2025.

## GitHub — DrivenByMoss Fork

Repository: https://github.com/Andy-Post/DrivenByMoss

Alle folgenden PRs wurden am 13.03.2026 eröffnet und waren bei der Rekonstruktion weiterhin offen:

1. PR #1 — OSC architecture/build analysis  
   https://github.com/Andy-Post/DrivenByMoss/pull/1
2. PR #2 — Parameter handling/limits analysis  
   https://github.com/Andy-Post/DrivenByMoss/pull/2
3. PR #3 — Optional 512er Extended Parameter Bank  
   https://github.com/Andy-Post/DrivenByMoss/pull/3
4. PR #4 — `/device/allparams` handlers + Multi-Value OSC writer  
   https://github.com/Andy-Post/DrivenByMoss/pull/4
5. PR #5 — Architecture design for allparams  
   https://github.com/Andy-Post/DrivenByMoss/pull/5
6. PR #6 — Build validation + manual Bitwig test plan  
   https://github.com/Andy-Post/DrivenByMoss/pull/6
7. PR #7 — Serum 2 OSC parameter dump tooling  
   https://github.com/Andy-Post/DrivenByMoss/pull/7

PR-Commits, soweit über die PR-Oberfläche belegt:

- #1 `895a009`
- #2 `8039963`
- #3 `d818532`
- #4 `081ed66`
- #5 `fe4ecc5`
- #6 `ce4b7d6`
- #7 `118c2a5`

## GitHub — DrivenByMoss Upstream

Repository: https://github.com/git-moss/DrivenByMoss

Dient als Referenz für die ursprüngliche OSC-/Controller-Schicht. Nicht als Beweis verwenden, dass `allparams` upstream übernommen wurde.

## Quellenlücken

Die vollständigen Originaltexte der Claude-Chats #35–44 und #142–150 waren über die derzeit zugängliche Library nicht als Volltext auffindbar. Vorhanden sind Chatindex, URLs und nachträgliche Zusammenfassungen.

Deshalb bleiben u.a. offen:

- exakte Namen/Signaturen aller 27 Tools,
- exakte Namen aller sechs Oktober-Extended-Parameter-Tools,
- vollständige damalige Testprotokolle,
- genaue Liste aller im Rumble-Setup positiv/negativ getesteten Aktionen.

Für diese Punkte gilt: **UNKNOWN – historical evidence insufficient**.
