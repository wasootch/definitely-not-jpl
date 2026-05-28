# Mars Rover

In a recent technical coding interview, I was asked to write the code for moving a rover on the surface of Mars given a
set of commands. I was able to complete about 80% of the implementation during the interview and figured I'd finish it
and share here for reference. Then I added some tests for easy validation.

## The Challenge

Rovers start at `(0, 0)` facing North and execute a string of commands:

| Command | Action        |
|---------|---------------|
| `F`     | Move forward  |
| `B`     | Move backward |
| `L`     | Turn left     |
| `R`     | Turn right    |

### Example

Input: `FFRFF`

```
Rover: Rover1
Position: [2, 2]
Direction: East
```

## Implementation

- **Phase 1** — Single rover executing a command string
- **Phase 2** — Multiple rovers, each with their own command queue
- **Phase 3** — 5x5 grid boundary enforcement; moves that would leave the grid or collide with another rover are ignored

### Key classes

| Class              | Responsibility                                                  |
|--------------------|-----------------------------------------------------------------|
| `SquadCoordinator` | Deploys rovers and routes commands to them                      |
| `Rover`            | Rover state (name, position, direction)                         |
| `SurfaceGrid`      | Tracks grid bounds and occupied cells                           |
| `MovementExecutor` | Translates a command string into rover movements                |
| `Direction`        | Enum for N/E/S/W with `turnLeft()` / `turnRight()`              |
| `CommandValidator` | Validates that a command string contains only valid characters  |

## Building and Running

Requires Java 11+ and uses the Gradle wrapper — no separate Gradle install needed.

```bash
cd mars-rover

# Build
./gradlew build

# Run tests
./gradlew test

# Test coverage report (output: build/reports/jacoco/test/html/index.html)
./gradlew test jacocoTestReport
```
