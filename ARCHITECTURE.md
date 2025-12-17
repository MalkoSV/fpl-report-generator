1. Overview

This project generates analytical reports for Fantasy Premier League (FPL).

The application:
- fetches static season data (players, events) once
- fetches runtime data (entries, picks, transfers) in parallel
- aggregates domain statistics
- exports results to Excel

The architecture follows layered / clean architecture principles with a strong separation between:
- API contracts (DTO)
- domain model
- application orchestration
- output/export

2. Layers & Dependencies

app

└── orchestrates use cases

context

└── bootstrap / read-only contexts

api

├── dto        (API contracts)

├── mapper     (DTO → domain)

└── client     (HTTP, Jackson)

repository

└── API & bootstrap implementations

domain

├── model      (core domain)

├── stats      (aggregations)

├── service    (use cases)

└── transfers  (transfer domain)

output

└── Excel export

excel

└── low-level Excel writing

Dependency rule:

api → domain
repository → domain
app → all
domain → nothing

3. Domain Model Terminology

Player-related models

| Name                  | Meaning                            |
| --------------------- | ---------------------------------- |
| `PlayerDto`           | Raw API data (BootstrapResponse)   |
| `PlayerSeasonView`    | Static season snapshot (domain)    |
| `Pick`                | Gameweek pick (domain)             |
| `SquadPlayer`         | Player in a specific team for a GW |
| `PlayerGameweekStats` | Aggregated stats across teams      |
Rule: DTOs never appear outside the api package.

Team-related models

| Name          | Meaning                      |
| ------------- | ---------------------------- |
| `Team`        | One FPL entry for a gameweek |
| `TeamSummary` | Aggregated team statistics   |

4. Bootstrap Context

BootstrapContext:
- is created once per application run
- loads static data (PlayerSeasonView, EventView)
- is immutable and read-only
- is safe to share across threads

Runtime data (entries, leagues, transfers) is not part of this context.

5. Data Flow (Runtime)
- Application starts 
- BootstrapContext loads static data
- League standings are fetched
- Team entries are fetched in parallel
- Transfers are fetched in parallel
- Domain statistics are calculated
- Results are exported

6. Repositories

Repositories:
- expose domain models only
- hide API / HTTP / DTO details
- may be backed by:
  - API calls
  - in-memory bootstrap data

Example:
- PlayerRepository → PlayerSeasonView
- EventRepository  → EventView

7. Concurrency

- Parallelism is handled inside domain/application services
- BootstrapContext is immutable and thread-safe
- Repositories must be stateless or thread-safe

8. Architectural Rules (Do NOT break)
- ❌ Domain must not depend on API or DTO
- ❌ DTOs must not leak into domain
- ❌ HTTP / Jackson must not appear outside api
- ❌ Domain models must not contain parsing logic
- ❌ BootstrapContext must not load runtime data

9. Notes

This architecture intentionally prefers:
- clarity over cleverness
- explicit models over generic ones
- minimal abstractions over frameworks
