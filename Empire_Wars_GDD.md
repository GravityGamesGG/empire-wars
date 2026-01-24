# Game Design Document

## Empire Wars

*A Persistent 3-Faction Territory War Game*

**Version 1.3 - Draft**  
**January 2026**

---

## 1. Executive Summary

Empire Wars is a persistent multiplayer war game where three factions compete for dominance over approximately one
month. Players commit to a single faction for the duration of the war and work together to destroy enemy monuments while
protecting their own. The game features a player-driven economy where all equipment is crafted by players, creating a
meaningful divide between frontline combat and backline production. Players organize into "Families" and build cities
within their faction's territory.

### Core Pillars

| Pillar                    | Description                                                                          |
|---------------------------|--------------------------------------------------------------------------------------|
| **Commitment**            | Players choose once and fight for their faction until the end                        |
| **Persistence**           | The war continues 24/7; progress and destruction are permanent                       |
| **Strategy**              | Building fortifications and outposts creates layered defensive and offensive options |
| **Finality**              | One winner per war; eliminated factions cannot return                                |
| **Player-Driven Economy** | Every weapon, piece of armor, and supply is crafted and transported by players       |
| **Community**             | Players form Families and build cities that become their home within the faction     |

---

## 2. Game Overview

### 2.1 High Concept

A month-long persistent war between three factions. Each faction defends a central monument while attempting to destroy
the others. The last faction standing wins. All equipment and supplies are produced by players in the backline and
transported to soldiers on the frontline. Players form Families and build cities that serve as their base of operations.

### 2.2 Genre & Platform

- **Genre:** Multiplayer FPS/TPS / Territory Control / Base Building / Economy Simulation
- **Perspective:** First-Person / Third-Person (player controlled single character)
- **Setting:** Medieval Fantasy with magic elements

### 2.3 War Duration & Cycle

Each war lasts approximately one month (28-35 days). After a war concludes, a new war begins with fresh factions, reset
territories, and rebuilt monuments. Players can choose a different faction in each new war.

---

## 3. Faction System

### 3.1 The Three Factions

Three distinct factions compete for victory. Faction identity and theming to be determined, but should provide clear
visual distinction and potential gameplay flavor.

| Faction A          | Faction B          | Faction C          |
|--------------------|--------------------|--------------------|
| 🔴 [Theme TBD]     | 🔵 [Theme TBD]     | 🟢 [Theme TBD]     |
| [Unique trait TBD] | [Unique trait TBD] | [Unique trait TBD] |

### 3.2 Faction Selection Rules

- Players select their faction at the start of each war
- Once selected, faction choice is **permanent** for that war
- Players cannot switch factions under any circumstances during an active war
- If a player's faction is eliminated, they can join another faction

### 3.3 Population Balancing

To prevent dominant factions from snowballing through sheer numbers:

- The game tracks active player counts for each faction
- If a faction has significantly more players than others, it becomes **temporarily unavailable** for new players
  joining the war
- New players must choose from the less populated factions
- This ensures relatively balanced populations throughout the war

### 3.4 Starting Positions

All three factions start equidistant from each other, forming a triangle. This ensures no faction has a geographical
advantage and all factions face equal threat from two opponents.

---

## 4. Monument System

### 4.1 Faction Monument

The Faction Monument is the heart of each faction - a massive, central structure that represents the faction's existence
in the war. It is both the primary objective to defend and the ultimate target to destroy.

### 4.2 Faction Monument Properties

| Property      | Details                                                  |
|---------------|----------------------------------------------------------|
| Health Pool   | [TBD - should require sustained effort to destroy]       |
| Regeneration  | [TBD - slow passive regen? None? Repairable by players?] |
| Visibility    | Always visible on map to all players                     |
| Defensibility | Players can build fortifications around it               |

### 4.3 Faction Monument Destruction

When a faction's monument is destroyed, that faction is **permanently eliminated** from the war.

**For Players:**

- Players of the eliminated faction can **choose to join one of the two remaining factions**
- This gives defeated players a second chance to continue fighting
- They start fresh with their new faction (no carried resources/gear)

**For Structures:**

- All structures belonging to the eliminated faction **remain on the map**
- However, with no players to maintain them, they will **run out of upkeep resources**
- Structures will gradually **decay and collapse** over time
- Remaining factions can raid these decaying cities for resources before they disappear
- Strategic opportunity: capture valuable enemy cities before they crumble

### 4.4 City Monuments

Every player-built city has its own **City Monument** — a smaller but critical structure:

- Represents ownership of the city
- Can be **captured** by enemy factions (see City Capture section)
- Must be protected to maintain control of the city

---

## 5. Family System

### 5.1 Overview

A **Family** is a player-formed group within a faction. Families are the primary social and organizational unit,
allowing players to cooperate, share resources, and control access to structures.

### 5.2 Family Formation

- Any player can create a Family
- Players can invite others to join their Family
- A player can only belong to **one Family** at a time
- Players can leave or be removed from Families
- [Further details TBD: Family size limits? Leadership structure?]

### 5.3 Family Permissions

Families control access to structures they build:

- When a player builds a structure, they can set permissions:
    - **Family Only** — Only members of their Family can use/access it
    - **City/Public** — Anyone in the faction can use it
    - [Other permission levels TBD]
- This allows Families to maintain private storage, workshops, and living spaces
- Shared city infrastructure can be made public for all residents

### 5.4 Families Within Cities

- A city can contain **multiple Families**
- Each Family may control different buildings or areas within the city
- Families can cooperate on shared projects (walls, public workshops)
- Inter-Family politics and cooperation emerge organically

### 5.5 Open Design Questions — Families

This system needs further development:

- How are disputes between Families resolved?
- Can Families formally ally or merge?
- Is there a "city council" of Family leaders?
- What happens to Family structures if all members leave?

---

## 6. City System

### 6.1 Overview

Cities are player-built settlements within faction. They serve as homes, production centers, and defensive
strongholds.

### 6.2 City Formation

- Players can establish a city by building a **City Monument**
- The City Monument defines the city's existence and ownership
- Cities grow organically as players build around the monument
- Multiple Families can inhabit and contribute to a single city

### 6.3 City Functions

| Function              | Description                                             |
|-----------------------|---------------------------------------------------------|
| **Home Base**         | Personal storage, respawn point, social hub             |
| **Production Center** | Shared workshops, forges, crafting stations             |
| **Trade Hub**         | Shops where players buy/sell resources and goods        |
| **Defense Point**     | Walls, towers, and automated defenses protect residents |
| **Identity**          | City name                                               |

### 6.4 City Capture

Cities can be **captured** rather than simply destroyed:

| Step                  | Description                                               |
|-----------------------|-----------------------------------------------------------|
| 1. Assault            | Enemy faction attacks the city, breaking through defenses |
| 2. Claim Monument     | Enemies interact with the City Monument to begin capture  |
| 3. Capture Process    | [TBD - instant? Channeled? Timed?]                        |
| 4. Ownership Transfer | City now belongs to the capturing faction                 |

**After Capture:**

- All buildings in the city transferred to the new faction
- Original owners lose access to their structures
- Capturing faction can use existing infrastructure
- Strategic value: capturing is more valuable than destroying

### 6.5 City Destruction

If attackers choose to destroy rather than capture:

- Buildings can be demolished individually
- City Monument can be destroyed, removing the city entirely
- Destruction denies the enemy but wastes potential resources

### 6.6 City Dynamics

- Cities near the frontline face more danger but may control strategic resources
- Cities in the backline are safer but must transport goods further
- Cities can specialize based on local biomes (mining town, farming village, trade hub)
- Inter-city trade within a faction creates a living economy

---

## 7. Food & Upkeep System

### 7.1 Overview

All structures and AI require ongoing resources to function. This creates constant demand that drives the economy and
prevents infinite accumulation of defenses.

### 7.2 Player Food

TBD

### 7.3 Building Upkeep

All structures require **resource upkeep** to remain standing:

| Upkeep Status     | Effect                                            |
|-------------------|---------------------------------------------------|
| **Maintained**    | Structure functions normally                      |
| **Low Resources** | Structure begins to decay                         |
| **No Resources**  | Structure decays rapidly and eventually collapses |

- Different structures require different resources
- More advanced/powerful structures cost more to maintain
- Abandoned structures naturally disappear from the map
- Players must actively supply buildings or lose them

### 7.4 AI Defense Upkeep

Automated defenses (archer towers, etc.) have **two resource requirements**:

| Requirement          | Purpose                         | If Depleted                              |
|----------------------|---------------------------------|------------------------------------------|
| **Structure Upkeep** | Maintains the physical building | Building decays and collapses            |
| **Food**             | Powers the AI defenders         | AI stops functioning (structure remains) |

This creates interesting tactical choices:

- A starved archer tower still stands but won't shoot
- Raiders can "starve out" defenses by cutting supply lines
- Defenders must maintain both food and material supply chains

### 7.5 Upkeep Economy

The upkeep system creates constant economic pressure:

- Cities must continuously produce resources just to maintain existing structures
- Expansion requires proportionally more production capacity
- Neglected areas naturally decay, keeping the map dynamic
- Encourages active play and discourages hoarding

---

## 8. Building System

### 8.1 Overview

Players can construct various structures to defend their cities, protect the monument, and establish forward positions.
Building is a core strategic element that shapes the battlefield over time.

### 8.2 Fortifications

Defensive structures built to protect cities, the monument, and faction territory:

- **Walls** – Block enemy movement and provide cover
- **Gates** – Controlled access points through walls

### 8.3 Automated Defenses

AI-controlled defensive structures that protect territory when players are offline:

- **Archer Towers** – Automatically target and fire at enemies within range
- **Watchtowers** - Provide vision and detect enemies (see Map & Intel section)
- **[Other automated defenses TBD]** - Magic-based defenses, ballistae, etc.

**Resource Requirements:**

- Structure upkeep (materials) — or building collapses
- Food — or AI stops functioning

These structures are critical for 24/7 persistence, allowing factions to maintain defense even during off-peak hours.

### 8.4 Siege Equipment

Offensive structures and equipment for assaulting enemy fortifications:

- **Trebuchets** - Long-range artillery for breaking walls and structures
- **[Other siege equipment TBD]** - Battering rams, siege towers, magical siege weapons

---

## 9. Combat System

### 9.1 Player Combat

- Players control a single character in **first-person or third-person** perspective
- Combat is real-time action with medieval weapons (swords, axes, bows, shields, etc.)
- **Magic spells** are available alongside traditional weapons [details TBD]
- Individual player impact is relatively low — victory comes from **numbers and coordination**

### 9.2 Death & Loss

When a player dies in PvP:

- They drop a **percentage of carried resources** on the ground
- Dropped resources can be looted by anyone (allies or enemies)
- Equipped gear has a **chance to break** on death
- Broken gear is lost permanently

### 9.3 Magic System

Magic exists in the world alongside traditional combat. Details to be determined:

- Spell types and effects [TBD]
- How magic is acquired/learned [TBD]
- Resource costs (mana, reagents, crafted items?) [TBD]
- Role in combat vs. utility vs. siege [TBD]

---

## 10. Map & Intelligence System

### 10.1 Overview

Information is power. The map system provides critical intelligence for both offense and defense, with
player-contributed data forming the backbone of faction awareness.

### 10.2 The Faction Map

All players in a faction share access to a **faction map** that displays:

- Faction monument location (always visible)
- Enemy monument locations
- Friendly cities and outposts
- Terrain and biome information
- Player-created markers and intel

### 10.3 Player-Created Markers

Players can add markers to the shared faction map:

| Marker Type        | Use Case                                         |
|--------------------|--------------------------------------------------|
| **Enemy Sighting** | "Enemies here" — warn allies of hostile presence |
| **Danger Zone**    | Warn of ambush spots or heavy enemy activity     |
| **Rally Point**    | Coordinate attacks or defense                    |
| **Custom Notes**   | Any text-based information                       |

### 10.4 Watchtowers & Vision

**Watchtowers** are structures that provide automatic vision:

- Reveal enemy players within their radius on the faction map
- Critical for detecting raids and incursions
- Destroying enemy watchtowers blinds their intel network
- Require food to function (see Upkeep section)

### 10.5 Fog of War

Areas without watchtower coverage or player presence have limited visibility:

- Enemy movements in unobserved areas are unknown
- Raiders can slip through gaps in watchtower coverage
- Maintaining vision coverage requires strategic tower placement

---

## 11. Raiding & Infiltration

### 11.1 Overview

Players can freely move anywhere on the map, including **deep into enemy territory**. This creates opportunities for
raiding, sabotage, and intelligence gathering.

### 11.2 Raiding Targets

| Target                | Impact                                   |
|-----------------------|------------------------------------------|
| **Supply Caravans**   | Steal resources, starve enemy frontline  |
| **Gatherers**         | Disrupt enemy resource production        |
| **Undefended Cities** | Loot storage, destroy infrastructure     |
| **Watchtowers**       | Blind enemy intel, enable larger attacks |

### 11.3 Raider Gameplay

- Small groups can slip past defenses and cause significant disruption
- High risk, high reward — raiders are outnumbered in enemy territory
- Successful raids require planning, speed, and escape routes
- Creates emergent cat-and-mouse gameplay

### 11.4 Counter-Raiding

Factions defend against raiders through:

- **Watchtower Networks** — Detect enemies entering territory
- **Map Markers** — Players report sightings to warn others
- **Patrols** — Players actively hunting raiders
- **Escort Missions** — Protecting valuable caravans
- **Defensive Positioning** — Cities and outposts with strong automated defenses

### 11.5 No Disguises

Faction identity is **always visible** — players cannot disguise themselves as enemy faction members. Enemies are always
recognizable.

---

## 12. Economy System

### 12.1 Core Philosophy

The economy is the backbone of the war. Every piece of equipment used on the frontline must be:

1. Gathered as raw materials
2. Processed through crafting stations
3. Assembled into final items
4. Transported to where it's needed

**No equipment spawns naturally** — if players don't produce it, it doesn't exist.

### 12.2 The Frontline/Backline Divide

| Zone          | Activities                                   | Players                                   |
|---------------|----------------------------------------------|-------------------------------------------|
| **Frontline** | Combat, raiding, siege warfare, defense      | Soldiers, scouts, siege operators         |
| **Backline**  | Mining, smelting, crafting, farming, trading | Gatherers, crafters, merchants, logistics |

Both roles are essential. Without backline production, frontline soldiers have no weapons. Without frontline soldiers,
backline workers are defenseless.

### 12.3 Resource Distribution & Biomes

Resources are **not uniformly distributed** across the map:

- Different biomes contain different resources (iron-rich mountains, copper deposits, magical forests)
- This creates natural **trade opportunities** between cities
- Cities can specialize based on local resources
- Players can set up **shops** to facilitate resource trading within their faction

Example: A mountain city rich in iron trades with a forest city that produces quality wood.

### 12.4 Player Freedom

- Players can freely switch between any role at any time
- No forced class system or locked specializations
- Natural incentives drive role distribution:
    - If nobody crafts, soldiers run out of gear
    - If nobody farms, everyone starves
    - If nobody fights, crafters get overrun
    - The economy self-balances based on player choices

---

## 13. Logistics System

### 13.1 Core Mechanics

All resources must be **physically transported** across the map:

- No teleportation or instant transfer of goods
- Resources have **weight** that limits how much a single player can carry
- A player cannot simply grab 500 swords and run to the frontline

### 13.2 Transportation Methods

| Method               | Capacity | Speed  | Risk            |
|----------------------|----------|--------|-----------------|
| On foot              | Very low | Medium | Low profile     |
| Cart (hand-pulled)   | Medium   | Slow   | Visible target  |
| Horse cart           | High     | Medium | Valuable target |
| [Other vehicles TBD] | [TBD]    | [TBD]  | [TBD]           |

### 13.3 Supply Lines

Established routes between backline production and frontline consumption:

- Supply lines can be **raided** by enemies
- Protecting supply lines becomes a strategic objective
- Cutting enemy supply lines can starve their frontline of equipment
- Creates emergent gameplay for ambushes and escort missions

### 13.4 Food Distribution

Food logistics are especially critical:

- Players need food everywhere (frontline and backline)
- AI defenses need a constant food supply to remain active
- A well-organized food distribution network is essential
- Disrupting enemy food logistics can disable their defenses

---

## 14. Victory Conditions

### 14.1 Primary Victory

The war ends when only one faction's monument remains standing. That faction is declared the winner of the war.

### 14.2 Victory Scenarios

| Scenario         | Outcome                                                         |
|------------------|-----------------------------------------------------------------|
| Standard Victory | One faction destroys both enemy monuments                       |
| 1v1 Finale       | After one faction is eliminated, remaining two fight to the end |

### 14.3 Post-War

- War statistics are recorded and displayed
- Player contributions tracked (optional leaderboards)
- Brief intermission period before next war begins
- All territories, structures, and worlds reset

---

## 15. Player Experience

### 15.1 New Player Flow

1. Player joins during an active war
2. Presented with a faction selection screen showing current war status
3. Chooses faction — the most populated faction may be locked (warned that choice is permanent)
4. Spawns at faction monument or available city
5. Encouraged to join an existing Family/city or start their own

### 15.2 Finding Your Role

Players naturally gravitate toward activities they enjoy:

- **Combat enthusiasts** → Frontline soldier, raider, defender
- **Economy enjoyers** → Miner, crafter, blacksmith, farmer
- **Social players** → City builder, merchant, trade coordinator
- **Strategic minds** → Supply line planner, siege coordinator, scout
- **Lone wolves** → Raider, spy, resource runner

### 15.3 Core Gameplay Loops

**Frontline Loop:**

- Get equipped from faction/city supplies
- Travel to the frontline
- Fight enemies, defend positions, assault structures
- Die, lose some gear → repeat

**City Loop:**

- Gather resources for your city
- Craft goods in city workshops
- Improve city defenses and infrastructure
- Maintain upkeep on buildings
- Defend against raids

**Logistics Loop:**

- Collect finished goods from city crafters
- Load transport vehicle
- Navigate to frontline depots (watch for raiders!)
- Distribute supplies to soldiers
- Ensure food reaches AI defenses

### 15.4 Eliminated Faction Players

When a faction is eliminated, its players have a choice:

- **Join a remaining faction** — Start fresh and continue fighting for a new side
- **Wait** — Leave and return for the next war

This keeps defeated players engaged rather than locked out for the rest of the month.

---

## 16. Open Design Questions

The following aspects require further design exploration:

### Combat & Magic

- Magic system depth (spell types, mana, cooldowns?)
- How powerful should automated defenses be?
- Siege equipment balance (how long to break walls?)

### Economy Details

- Crafting recipes and time requirements
- Shop/trading interface design
- How to prevent hoarding or market manipulation?

### Families & Cities

- Can Families formally ally or merge?
- Is there a "city council" of Family leaders?
- What happens to Family structures if all members leave?
- City capture process details (instant? timed? contested?)

### Progression

- What carries over between wars? (Cosmetics, titles, stats?)
- Faction-wide tech trees or upgrades?

### Map & Intel

- How long do player markers last?
- Can markers be false/misleading (griefing concern)?

---

## 17. Appendix: Map Concept

The battlefield forms an equilateral triangle with each faction's monument at a vertex. Neutral territory fills the
center, providing contested ground for outpost placement and major battles.

```
           [FACTION A]
           Monument + Starting Area
               /\
              /  \
             /    \
            /      \
           / NEUTRAL\
          /   ZONE   \
         /____________\
   [FACTION B]    [FACTION C]
   Monument +     Monument +
   Starting Area  Starting Area
```

### Biome Distribution

Resources are spread across the map to encourage expansion and trade:

- Each faction's starting area has **some** resources but not all types
- Neutral zones contain valuable resource deposits worth fighting over
- Biome variety creates natural trade routes between cities

---

## 18. Reference & Inspiration

Games with similar elements:

- **Foxhole, Anvil Empire** — Persistent war, player-driven logistics, frontline/backline divide
- **Eco** — Economic simulation, trading
- **Rust** — Building maintenance, survival, player-driven economy, group dynamics
- **Albion Online** — Player-crafted economy, full loot PvP, guild territories

---