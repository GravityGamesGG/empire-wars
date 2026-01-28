# Hytale NPC Behavior System

This document explains how the NPC behavior/AI system works in Hytale, based on decompiled code analysis.

## Overview

Hytale uses a **priority-based instruction system** for NPC AI. Unlike traditional behavior trees, NPCs have an
`Instructions` array where each instruction is evaluated **in order** from top to bottom. The first instruction whose *
*Sensor** conditions match gets executed.

## Instruction Structure

Each instruction can contain the following components:

| Component      | Purpose                                                                         |
|----------------|---------------------------------------------------------------------------------|
| **Sensor**     | Conditions that must be met for the instruction to execute                      |
| **Filters**    | Additional criteria to narrow down sensor results                               |
| **HeadMotion** | Controls where the NPC looks                                                    |
| **BodyMotion** | Controls NPC movement                                                           |
| **Actions**    | What the NPC does when the instruction matches                                  |
| **Continue**   | If `true`, evaluation continues to the next instruction after this one executes |
| **TreeMode**   | Enables behavior-tree-like parent-child result propagation                      |

### Key Rule

An instruction has **either**:

- Nested `Instructions[]` (branch node)
- `HeadMotion`, `BodyMotion`, `Actions` (leaf node)

Never both.

## Continue Field Behavior

The `Continue` field is only relevant when the sensor **succeeds**.

| Sensor Result | Continue Value    | What Happens                                     |
|---------------|-------------------|--------------------------------------------------|
| **Fails**     | `true` or `false` | Instruction skipped, moves to next instruction   |
| **Succeeds**  | `false` (default) | Executes, then **stops** evaluation              |
| **Succeeds**  | `true`            | Executes, then **continues** to next instruction |

### Example

```json
{
    "Instructions": [
        {
            "$Comment": "Aim at target",
            "Continue": true,
            "Sensor": {
                "Type": "Mob",
                "Range": 30
            },
            "HeadMotion": {
                "Type": "Aim"
            }
        },
        {
            "$Comment": "Attack if in range",
            "Sensor": {
                "Type": "Any",
                "Range": 25
            },
            "Actions": [
                {
                    "Type": "Attack"
                }
            ]
        }
    ]
}
```

With `Continue: true` on the first instruction, the NPC can both aim **and** attack in the same tick.

## Nested Instructions

Instructions can contain child instructions, creating hierarchical decision structures.

```json
{
    "Sensor": {
        "Type": "SomeCondition"
    },
    "Instructions": [
        {
            "Sensor": {
                "Type": "ConditionA"
            },
            "Actions": [
                ...
            ]
        },
        {
            "Sensor": {
                "Type": "ConditionB"
            },
            "BodyMotion": {
                ...
            }
        },
        {
            "Sensor": {
                "Type": "Any"
            },
            "Actions": [
                ...
            ]
        }
    ]
}
```

When the parent sensor matches, child instructions are evaluated in order using the same priority-based logic.

## TreeMode (Behavior Tree Semantics)

The `TreeMode` field enables true behavior-tree-like logic where **child results propagate back to the parent**.

### How TreeMode Works

When `TreeMode: true`:

1. **On Match**: The instruction starts with `continueAfter = true`
2. **Child Matches**: If any child sensor matches, `notifyChildSensorMatch()` sets `continueAfter = false`
3. **On Completed**: The result is propagated to the parent based on `continueAfter` and `InvertTreeModeResult`

### TreeMode Result Propagation

| TreeMode | InvertTreeModeResult | Child Matched | Result to Parent         |
|----------|----------------------|---------------|--------------------------|
| `true`   | `false`              | Yes           | Notify parent (success)  |
| `true`   | `false`              | No            | Don't notify (failure)   |
| `true`   | `true`               | Yes           | Don't notify (inverted)  |
| `true`   | `true`               | No            | Notify parent (inverted) |

This enables:

- **Selector** behavior: First child that succeeds stops evaluation
- **Sequence** behavior (with invert): All children must succeed
- **Inverter** decorator: `InvertTreeModeResult: true`

### TreeMode Example

```json
{
    "TreeMode": true,
    "Sensor": {
        "Type": "Any"
    },
    "Instructions": [
        {
            "TreeMode": true,
            "InvertTreeModeResult": false,
            "Sensor": {
                "Type": "Any"
            },
            "Instructions": [
                {
                    "Sensor": {
                        "Type": "Player",
                        "Range": 5
                    },
                    "Actions": [
                        {
                            "Type": "DisplayName",
                            "DisplayName": "Player nearby!"
                        }
                    ]
                }
            ]
        },
        {
            "Sensor": {
                "Type": "Any"
            },
            "Actions": [
                {
                    "Type": "DisplayName",
                    "DisplayName": "No player found"
                }
            ]
        }
    ]
}
```

## Common Sensor Types

| Sensor Type | Description                                       |
|-------------|---------------------------------------------------|
| `Any`       | Always matches (or uses previously locked target) |
| `Mob`       | Detects entities (players/NPCs) within range      |
| `Player`    | Detects players specifically                      |
| `Target`    | Checks if current locked target is in range       |
| `State`     | Matches based on NPC state machine                |
| `Eval`      | Evaluates an expression (e.g., `"blocked"`)       |
| `Or`        | Matches if any child sensor matches               |
| `Not`       | Inverts child sensor result                       |
| `Beacon`    | Listens for beacon messages from other NPCs       |

## Common Filters

| Filter Type   | Description                                     |
|---------------|-------------------------------------------------|
| `Attitude`    | Filter by attitude (Hostile, Neutral, Friendly) |
| `LineOfSight` | Requires clear line of sight to target          |
| `NPCGroup`    | Filter by NPC group membership                  |

## Common HeadMotion Types

| Type      | Description                                |
|-----------|--------------------------------------------|
| `Aim`     | Aim at target with ballistic prediction    |
| `Watch`   | Look at target without aiming calculations |
| `Nothing` | Don't control head rotation                |

### Aim Parameters

```json
{
    "Type": "Aim",
    "Spread": 0.5,
    "HitProbability": 1,
    "Deflection": true
}
```

- `Spread`: Inaccuracy factor
- `HitProbability`: Chance to aim accurately (1 = always accurate)
- `Deflection`: Lead target based on velocity (predictive aiming)

## Common BodyMotion Types

| Type             | Description                   |
|------------------|-------------------------------|
| `Nothing`        | Stand still                   |
| `Seek`           | Move toward target            |
| `Flee`           | Move away from target         |
| `Wander`         | Random movement               |
| `WanderInCircle` | Wander within a radius        |
| `Sequence`       | Execute motions in sequence   |
| `Timer`          | Execute motion for a duration |

## Common Action Types

| Type             | Description                |
|------------------|----------------------------|
| `Attack`         | Perform an attack          |
| `PlayAnimation`  | Play an animation          |
| `SpawnParticles` | Spawn particle effects     |
| `DisplayName`    | Set display name (debug)   |
| `ParentState`    | Transition to parent state |
| `State`          | Transition to local state  |
| `Timeout`        | Execute action after delay |

## Practical Example: Archer Guard

```json
{
    "Instructions": [
        {
            "$Comment": "Aim at hostile targets",
            "Continue": true,
            "Sensor": {
                "Type": "Mob",
                "Range": 30,
                "GetPlayers": true,
                "LockOnTarget": true,
                "Filters": [
                    {
                        "Type": "Attitude",
                        "Attitudes": [
                            "Hostile"
                        ]
                    },
                    {
                        "Type": "LineOfSight"
                    }
                ]
            },
            "HeadMotion": {
                "Type": "Aim",
                "Spread": 0.5,
                "HitProbability": 1,
                "Deflection": true
            }
        },
        {
            "$Comment": "Attack hostile targets in range",
            "Sensor": {
                "Type": "Any",
                "Range": 25,
                "Filters": [
                    {
                        "Type": "LineOfSight"
                    }
                ]
            },
            "Actions": [
                {
                    "Type": "Attack",
                    "Attack": "Skeleton_Archer_Bow_Shoot",
                    "BallisticMode": "Short",
                    "AimingTimeRange": [
                        2,
                        3
                    ],
                    "AvoidFriendlyFire": true,
                    "AttackPauseRange": [
                        1,
                        2
                    ]
                }
            ]
        },
        {
            "$Comment": "Idle when no hostile targets",
            "Sensor": {
                "Type": "Any"
            },
            "BodyMotion": {
                "Type": "Nothing"
            }
        }
    ]
}
```

### Behavior Flow

```
Every tick:
+-- Check for hostile players within 30 blocks with line of sight
|   +-- Found? -> Aim at them, CONTINUE to next instruction
|   |   +-- Is target within 25 blocks with line of sight?
|   |       +-- Yes -> Fire bow -> STOP
|   |       +-- No -> Fall through to idle -> STOP
|   +-- Not found? -> Fall through to idle -> STOP
```

## References

- `Instruction.java` - Core instruction execution logic
- `Sensor.java` - Sensor interface
- `HeadMotionAim.java` - Aiming logic with ballistic calculations
- `ActionList.java` - Action execution logic
