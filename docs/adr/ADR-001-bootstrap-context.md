ADR-001: BootstrapContext for Static Season Data

Status: Accepted

Date: 2025-12-17

Context: FPL Report Generator

Context

The FPL API provides two fundamentally different types of data:

1. Static season data
- players
- events
- positions
- rarely changes during a season
- fetched from bootstrap-static/

2. Runtime data
- entries
- picks
- transfers
- league standings
- fetched many times
- fetched in parallel
- event-specific

Initially, all data was fetched directly from API clients inside domain services.

This led to:
- repeated parsing of static data
- unclear ownership of bootstrap data
- risk of mixing static and runtime concerns
- unclear thread-safety guarantees

Decision

Introduce a dedicated BootstrapContext.

BootstrapContext:
- is created once at application startup
- loads static season data from bootstrap-static
- transforms DTOs into domain views
- exposes data only via repositories:
  - PlayerRepository → PlayerSeasonView
  - EventRepository → EventView
- is immutable and thread-safe
- is shared across all services and threads

Runtime data (entries, transfers, leagues) is explicitly excluded from this context.

Implementation

var bootstrapContext = new BootstrapContext(api);

Inside BootstrapContext:
- PlayerDto → PlayerSeasonView
- EventDto → EventView
- data is stored in immutable collections
- no lazy loading
- no network calls after construction

Consequences

Positive
- ✅ Static data is loaded once per run
- ✅ Clear separation between static and runtime data
- ✅ Domain services do not depend on API clients
- ✅ Thread-safe access to shared data
- ✅ Simplified reasoning about data freshness

Negative
- ❌ Slightly higher startup cost
- ❌ All static data is loaded even if not fully used

These trade-offs are acceptable because:
- bootstrap data is small
- the report generation process is batch-oriented
- simplicity and correctness are preferred over micro-optimizations

Alternatives Considered
1. Lazy-loading static data on demand

Rejected
- Complicates thread-safety
- Requires synchronization or caching
- Makes data ownership unclear
- Increases risk of partial initialization

2. Fetch bootstrap data inside each service

Rejected
- Repeated API calls
- Harder to test
- Harder to reason about consistency
- Violates single source of truth

3. Merge bootstrap and runtime data into a single context

Rejected
- Runtime data is large and volatile
- Requires lifecycle management
- Makes parallel processing harder
- Blurs domain boundaries

Rules
- BootstrapContext must not:
  - fetch runtime data
  - perform API calls after construction
  - expose DTOs
  - contain mutable state
- Domain services must:
  - treat BootstrapContext as read-only
  - not cache or modify its data

Notes

This decision intentionally favors:
- architectural clarity
- predictable behavior
- ease of reasoning
over maximal flexibility.

Future Considerations

If FPL API changes to:
- introduce versioned bootstrap data
- or provide partial bootstrap endpoints

BootstrapContext may evolve into a cached or versioned context.

File Location

/docs/adr/ADR-001-bootstrap-context.md
