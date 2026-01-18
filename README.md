# Empire Wars

A persistent 3-faction territory war game for Hytale servers.

## Overview

Empire Wars is a month-long persistent multiplayer war where three factions compete for dominance. Players commit to a single faction and work together to destroy enemy monuments while protecting their own. Features include:

- **Player-driven economy** - All equipment is crafted and transported by players
- **Family system** - Form groups to cooperate, share resources, and build cities
- **Territory control** - Build fortifications, outposts, and automated defenses
- **24/7 persistence** - The war continues around the clock with permanent consequences

See `Empire_Wars_GDD.md` for the full game design document.

## Inspiration

This project is inspired by games like [Foxhole](https://www.foxholegame.com/), [Anvil Empire](https://www.anvilempires.com/), and [Eco](https://play.eco/).

## Requirements

- Java 24+
- Hytale (release version)
- Gradle 8+

## Building

```bash
# Build the server pack
./gradlew build

# Run the development server
./gradlew runServer
```

## Project Structure

```
src/main/kotlin/gravitygames/
├── Empire.kt                 # Main plugin entry point
├── EmpireData.kt             # Data management
├── Factions.kt               # Faction system
├── commands/                 # Player commands (factions, families)
├── component/                # Entity components
├── listeners/                # Event listeners
├── registries/               # Command, component, event registries
├── systems/events/           # Game event handlers
└── utils/                    # Utility functions
```

## License

This project is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html) - see the LICENSE file for details.
