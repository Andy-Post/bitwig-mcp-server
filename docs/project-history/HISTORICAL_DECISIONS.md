# Historical Decisions

## HD-001 — DrivenByMoss als OSC-Backend

**Datum:** spätestens 11.10.2025  
**Problem:** Bitwig sollte von einem externen MCP-Client steuerbar sein.  
**Damalige Entscheidung:** vorhandene Architektur `MCP -> OSC -> DrivenByMoss -> Bitwig` übernehmen und zuerst lauffähig machen.  
**Begründung:** DrivenByMoss bot bereits OSC-Controller und Bitwig-Controller-API-Schicht.  
**Alternativen:** eigene Bitwig Extension; direktere Bridge.  
**Ergebnis:** Server-/OSC-Pfad in Betrieb genommen; spätere Capability-Lücken blieben.  
**Heutiger historischer Status:** **VERIFIED** als verwendete Ausgangsarchitektur.  
**Quellen:** Claude-Chats #35–40; Repository-Historie.

## HD-002 — Extended Parameter Paging im MCP

**Datum:** 16.10.2025  
**Problem:** nur acht Device-Parameter gleichzeitig adressierbar.  
**Damalige Entscheidung:** Seitenwahl im MCP automatisieren; sinngemäß `page = (parameter_index - 1) // 8`.  
**Begründung:** vorhandene 8er-Bank nutzen, ohne DrivenByMoss sofort zu ändern.  
**Alternativen:** Backend ändern; größere native Parameterbank; eigene Extension.  
**Ergebnis:** sechs Extended-Parameter-Tools laut Historie implementiert und codebezogen geprüft.  
**Heutiger historischer Status:** **IMPLEMENTED**. Vollständige Live-Verifikation: **UNKNOWN – historical evidence insufficient**.  
**Quellen:** Claude-Chats #39/#40; Sekundärzusammenfassungen.

## HD-003 — Capability-Gap als größeres Architekturproblem

**Datum:** 12.11.2025  
**Problem:** komplexes Rumble-/Multi-Layer-Setup zeigte Grenzen jenseits der Parameterbank.  
**Damalige Entscheidung:** Funktionsumfang systematisch erfassen und breitere Roadmap formulieren.  
**Begründung:** historische Bewertung von nur ungefähr 50 % Abdeckung des gewünschten Workflows.  
**Ergebnis:** 4-Phasen-Roadmap.  
**Heutiger historischer Status:** **VERIFIED** als damalige Analyse/Entscheidung; Prozentzahl ist historische Schätzung.  
**Quellen:** Claude-Chats #42/#43.

## HD-004 — Remote-OSC als Sprint 1

**Datum:** 12.11.2025  
**Problem:** skalierbarer Parameterzugriff, Batch-Steuerung und gerätespezifische Zuordnung.  
**Damalige Entscheidung:** Remote-OSC im Python-MCP, OSC-Bundles und YAML-/JSON-Mappings.  
**Begründung:** User-Slots und Batchzugriff sollten die 8er-Bank umgehen bzw. ergänzen.  
**Alternativen:** nur DrivenByMoss-Paging; direkte Java-Bridge.  
**Ergebnis:** Sprint-Dokument mit Tasks, Ports und Acceptance Criteria.  
**Heutiger historischer Status:** **PLANNED**.  
**Quellen:** `sprint1-extended-parameter-control.md`, Commit `89147a9`.

## HD-005 — Eigene Java-Bitwig-Extension / Sprint 2

**Datum:** 12.11.2025  
**Problem:** OSC-/MCP-Ebene schien nicht alle benötigten Fähigkeiten für stabile Zieladressierung und tiefe DAW-Funktionen abzudecken.  
**Damalige Entscheidung:** eigene Bitwig Controller Extension als Bridge vorsehen.  
**Begründung:** direkterer Zugriff auf Bitwig Controller API.  
**Alternativen:** DrivenByMoss weiter ausbauen; Remote-OSC allein.  
**Ergebnis:** Java-Starterstruktur eingecheckt; Bitwig-API-Dependency und WebSocket-Server noch offen.  
**Heutiger historischer Status:** **IMPLEMENTED** als Skeleton; funktionierende Bridge **UNKNOWN**.  
**Quellen:** Bridge README; November-Commits.

## HD-006 — WebSocket-Bridge

**Datum:** 12.11.2025  
**Problem:** bidirektionale Kommunikation zwischen Java Extension und MCP.  
**Damalige Entscheidung:** WebSocket als Bridge-Transport vorsehen.  
**Begründung:** persistente Kommunikation und Event-/State-Übertragung.  
**Alternativen:** OSC-only.  
**Ergebnis:** Starterstruktur vorhanden; WebSocket-Server laut README noch zu implementieren.  
**Heutiger historischer Status:** **PLANNED**, mit **IMPLEMENTED** Skeleton.  
**Quellen:** Bridge README.

## HD-007 — Unified Mapping & Controller Layer / Sprint 3

**Datum:** 15.11.2025  
**Problem:** getrennte Mappinglogiken für Remote-OSC, Bridge und Controller sollten vermieden werden.  
**Damalige Entscheidung:** vereinheitlichten Mapping-Layer mit Track→Device→Parameter→Slot, Page/Bank-Abstraktion und YAML/JSON-Import/Export planen.  
**Ergebnis:** Sprint-3-Dokumente mehrfach überarbeitet.  
**Heutiger historischer Status:** **PLANNED**.  
**Quellen:** Commitserie 15.11.2025.

## HD-008 — Clip-/Projekt-/Export-Funktionalität als spätere Phase

**Datum:** 12.11.2025  
**Problem:** Teile des gewünschten DAW-Workflows lagen außerhalb der damaligen OSC-Fähigkeiten.  
**Damalige Entscheidung:** spätere Phase für Clip-/Projekt-/Export-Funktionen.  
**Ergebnis:** Roadmap; Sprint 1 nennt Export/Bounce explizit out of scope.  
**Heutiger historischer Status:** **PLANNED**.  
**Quellen:** Claude-Chat #43; Sprint-1-Dokument.

## HD-009 — März 2026: Parameterproblem in DrivenByMoss selbst lösen

**Datum:** 10.–13.03.2026  
**Problem:** 8-Parameter-Blocker bestand als strukturelles Problem fort.  
**Damalige Entscheidung:** DrivenByMoss um eine optionale seitenunabhängige Parameteransicht bis 512 Slots erweitern, ohne die bestehende 8er-API zu brechen.  
**Begründung:** Backward Compatibility und direkter OSC-Zugriff auf alle von Bitwig exponierten Plugin-Parameter.  
**Alternativen:** page-basierte API weiter orchestrieren; Remote-OSC; eigene Extension.  
**Ergebnis:** PR #3/#4 enthalten Java-Code; PR #5/#6 Design/Testdoku.  
**Heutiger historischer Status:** **IMPLEMENTED**, nicht **VERIFIED**.  
**Quellen:** DrivenByMoss PRs #1–#6.

## HD-010 — `allparams`-Adressraum

**Datum:** 13.03.2026  
**Problem:** Index-, Namens- und Dump-Zugriff auf große Parameterzahlen.  
**Damalige Entscheidung:** eigener Namespace `/device/allparams/...`, Namen als OSC-Argumente, Fehlerkanal und `begin/item/end`-Dump-Protokoll.  
**Begründung:** robuste Protokollstruktur, alte `/device/param/{1..8}/...`-API bleibt erhalten.  
**Ergebnis:** spezifiziert und auf PR-Branch implementiert.  
**Heutiger historischer Status:** **IMPLEMENTED**, Live-Funktion **UNKNOWN**.  
**Quellen:** PR #4/#5.

## HD-011 — Begrenzte Extended Bank statt unbounded allocation

**Datum:** März 2026  
**Problem:** große Parameterlisten ohne unkontrollierte Bankgröße.  
**Damalige Entscheidung:** Obergrenze 512; PR #3 baut 64 × 8 Seiten zu einer Sicht zusammen.  
**Heutiger historischer Status:** **IMPLEMENTED**.  
**Quellen:** PR #3/#5; AGENTS-Spezifikation.

## HD-012 — Codex-Arbeit in getrennten PRs

**Datum:** 10.–13.03.2026  
**Problem:** größere Änderung sollte analysierbar/reviewbar bleiben.  
**Damalige Entscheidung:** Analyse, Framework, OSC-Handler, Architektur, Testplan und Tooling in getrennten Branches/PRs.  
**Ergebnis:** sieben offene PRs.  
**Heutiger historischer Status:** **VERIFIED**.  
**Quellen:** DrivenByMoss PR #1–#7.

## Nicht belegte endgültige Beschlüsse

Für folgende Aussagen gibt es keinen belastbaren historischen Beleg:

- „DrivenByMoss wurde endgültig verworfen.“
- „Die Java Bridge hat Remote-OSC ersetzt.“
- „allparams hat Sprint 1 offiziell abgelöst.“
- „WebSocket wurde als endgültiger Transport beschlossen.“
- „Die Oktober-Paging-Tools wurden als technisch falsch verworfen.“

Status jeweils: **UNKNOWN – historical evidence insufficient**.
