FPL Report Generator

A tool for collecting, aggregating, and exporting Fantasy Premier League statistics
(teams, players, transfers, formations, chips) into Excel reports.

The project is built with a strong focus on:
- clear separation of layers
- isolation of domain logic from API details
- scalability and readability
- parallel data processing

✨ Features
- 📥 Fetches data from the official FPL API
- ⚙️ Parallel parsing of:
  - teams
  - picks
  - transfers
- 🧠 Domain-level aggregation:
  - team squads
  - per–gameweek player statistics
  - chip usage analysis
- 📊 Excel report generation
- 🧱 Clean, layered architecture (API / Domain / Infrastructure)

🧭 Architecture Overview

API (DTO, HTTP)

↓

BootstrapContext (loaded once)

↓

Domain Services

↓

Domain Models / Stats

↓

Export (Excel)

Key principle:

The Domain layer never depends directly on API DTOs.

📦 Project Structure

fpl

├── api                // HTTP client, DTOs, API mappers

├── app                // application entry point (main)

├── context            // BootstrapContext

├── domain

│   ├── model          // core domain models

│   ├── repository     // domain repository interfaces

│   ├── service        // domain services (orchestration)

│   ├── stats          // aggregations & statistics

│   └── transfers

├── repository         // repository implementations

├── excel              // Excel generation

├── output             // export orchestration

├── logging

├── utils

└── docs

└── adr            // Architectural Decision Records

🧠 Core Concepts

BootstrapContext 

BootstrapContext is responsible for static season data that:
- does not change during execution
- is required across many use cases

It is initialized once and provides:
- players (PlayerSeasonView)
- events (EventView)

📄 See architectural rationale:
- docs/adr/ADR-001-bootstrap-context.md

DTO vs Domain Models

| Layer              | Model                 | Purpose                   |
| ------------------ | --------------------- | ------------------------- |
| API                | `PlayerDto`           | Raw data from FPL API     |
| Domain / bootstrap | `PlayerSeasonView`    | Static season snapshot    |
| Domain / team      | `SquadPlayer`         | Player in a specific team |
| Domain / stats     | `PlayerGameweekStats` | Aggregated per-GW stats   |

Repositories
- Domain repositories are interfaces
- Infrastructure repositories provide implementations

PlayerRepository

└── BootstrapPlayerRepository

👉 Repositories always return domain models, never DTOs.

Domain Services

Services handle orchestration, not persistence:
- StandingsParsingService
- TeamsParsingService
- TransfersParsingService

Characteristics:
- parallel execution (CompletableFuture)
- thread pool control
- progress reporting

🚀 Execution Flow
1. Create BootstrapContext
2. Fetch team IDs from league standings
3. Parse teams in parallel
4. Parse transfers in parallel
5. Aggregate statistics
6. Export results to Excel

🏗️ Entry Point

public class FplReportGenerator {

public static void main(String[] args) {

BootstrapContext bootstrap = new BootstrapContext(api);

List<Team> teams = TeamsParsingService.collectTeamStats(...);

List<Transfer> transfers = TransfersParsingService.collectTransfers(...);
// export

}
}

📚 Architectural Decision Records (ADR)

Key architectural decisions are documented as ADRs:
- ADR-001: BootstrapContext

🧪 Project Status
- ✔️ Architecture stabilized
- ✔️ DTOs fully isolated
- ✔️ Clean domain layer
- 🚧 Export layer refactoring in progress

🧠 Philosophy

Code should explain intent,

architecture should explain why.
