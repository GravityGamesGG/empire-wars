# Hytale NPC Role JSON Reference

This document describes the JSON structure for defining NPC Roles in Hytale.

## Table of Contents

1. [Role Types](#role-types)
2. [Top-Level Fields](#top-level-fields)
3. [Parameters](#parameters)
4. [Motion Controllers](#motion-controllers)
5. [Instructions](#instructions)
6. [Sensors](#sensors)
7. [Actions](#actions)
8. [Body Motion](#body-motion)
9. [Head Motion](#head-motion)
10. [Filters](#filters)
11. [Components & References](#components--references)

---

## Role Types

### Generic

Standard NPC role with full configuration.

```json
{
  "Type": "Generic",
  "Appearance": "Trork_Warrior",
  "MaxHealth": 100,
  "Instructions": [
    ...
  ]
}
```

### Variant

Extends another role with modifications.

```json
{
  "Type": "Variant",
  "Reference": "Template_Trork_Melee",
  "Modify": {
    "Appearance": "Trork_Warrior",
    "MaxHealth": 61,
    "DropList": "Drop_Trork_Warrior"
  }
}
```

### Abstract

Template role that cannot be spawned directly (used as base for Variants).

```json
{
  "Type": "Abstract",
  "Parameters": {
    ...
  },
  "Instructions": [
    ...
  ]
}
```

### Component

Reusable instruction/sensor/action/motion snippets.

```json
{
  "Type": "Component",
  "Class": "Instruction",
  "Interface": "Hytale.Instruction.AttackSequence",
  "Parameters": {
    ...
  },
  "Content": {
    ...
  }
}
```

---

## Top-Level Fields

| Field                   | Type           | Description                                                          |
|-------------------------|----------------|----------------------------------------------------------------------|
| `Type`                  | String         | `Generic`, `Variant`, `Abstract`, or `Component`                     |
| `Reference`             | String         | Parent role for Variant type                                         |
| `Modify`                | Object         | Modifications for Variant type                                       |
| `Parameters`            | Object         | Configurable parameters with defaults                                |
| `Appearance`            | String         | Model asset name                                                     |
| `MaxHealth`             | Number/Compute | Maximum health                                                       |
| `StartState`            | String         | Initial state name                                                   |
| `DefaultSubState`       | String         | Initial sub-state                                                    |
| `DefaultPlayerAttitude` | String         | Attitude toward players (`Hostile`, `Neutral`, `Friendly`, `Ignore`) |
| `DefaultNPCAttitude`    | String         | Attitude toward other NPCs                                           |
| `AttitudeGroup`         | String         | Group for attitude lookups                                           |
| `HotbarItems`           | Array          | Equipped weapon items                                                |
| `OffHandItems`          | Array          | Off-hand items                                                       |
| `HotbarSize`            | Number         | Hotbar slot count                                                    |
| `Armor`                 | Array          | Equipped armor pieces                                                |
| `DropList`              | String         | Drop configuration reference                                         |
| `Invulnerable`          | Boolean        | Cannot take damage                                                   |
| `BreathesInAir`         | Boolean        | Can breathe in air                                                   |
| `BreathesInWater`       | Boolean        | Can breathe in water                                                 |
| `KnockbackScale`        | Number         | Knockback multiplier                                                 |
| `DeathAnimationTime`    | Number         | Death animation duration                                             |
| `BusyStates`            | Array          | States considered "busy"                                             |
| `DisableDamageGroups`   | Array          | Groups that cannot damage this NPC                                   |
| `MotionControllerList`  | Array          | Movement configurations                                              |
| `Instructions`          | Array          | Behavior tree                                                        |
| `NameTranslationKey`    | String         | Localization key for name                                            |
| `IsMemory`              | Boolean        | Whether this NPC is a memory entry                                   |
| `MemoriesCategory`      | String         | Memory category                                                      |
| `MemoriesNameOverride`  | String         | Override name for memories                                           |

---

## Parameters

Parameters allow configurable values with descriptions. They can be referenced using `{ "Compute": "ParameterName" }`.

```json
{
  "Parameters": {
    "MaxHealth": {
      "Value": 100,
      "Description": "Max health for the NPC"
    },
    "ViewRange": {
      "Value": 15,
      "Description": "View range for detecting players"
    },
    "AttackDistance": {
      "Value": 3,
      "Description": "Distance at which NPC will attack"
    },
    "Weapons": {
      "Value": [
        "Weapon_Sword_Iron"
      ],
      "Description": "NPC Weapon"
    }
  },
  "MaxHealth": {
    "Compute": "MaxHealth"
  },
  "HotbarItems": {
    "Compute": "Weapons"
  }
}
```

### Compute Expressions

Parameters support computed expressions:

```json
{
  "Compute": "ViewRange"
}           // Direct reference
{
  "Compute": "ViewRange/2"
}         // Math operations
{
  "Compute": "MaxHealth * 0.5"
}     // Multiplication
```

---

## Motion Controllers

Define how NPCs move physically.

### Walk Controller

```json
{
  "Type": "Walk",
  "MaxWalkSpeed": 3,
  "RunThreshold": 0.7,
  "RunThresholdRange": 0.1,
  "Gravity": 10,
  "MaxFallSpeed": 20,
  "Acceleration": 10,
  "MaxClimbHeight": 1,
  "MinJumpHeight": 0.8,
  "JumpHeight": 1.5,
  "JumpForce": 1.5,
  "MaxRotationSpeed": 360,
  "DescendFlatness": 0.7,
  "DescendSpeedCompensation": 0.5
}
```

### Fly Controller

```json
{
  "Type": "Fly",
  "MaxSpeed": 5,
  "Acceleration": 8
}
```

---

## Instructions

Instructions form the behavior tree. Each instruction has a **Sensor** (condition), optional **Actions**, and optional *
*Motion**.

### Basic Structure

```json
{
  "Instructions": [
    {
      "Sensor": {
        "Type": "Any"
      },
      "Actions": [
        ...
      ],
      "BodyMotion": {
        ...
      },
      "HeadMotion": {
        ...
      }
    }
  ]
}
```

### Nested Instructions (Behavior Tree)

```json
{
  "Instructions": [
    {
      "Sensor": {
        "Type": "State",
        "State": "Idle"
      },
      "Instructions": [
        {
          "Sensor": {
            "Type": "Player",
            "Range": 15
          },
          "Actions": [
            {
              "Type": "State",
              "State": "Combat"
            }
          ]
        },
        {
          "Sensor": {
            "Type": "Any"
          },
          "BodyMotion": {
            "Type": "Wander"
          }
        }
      ]
    }
  ]
}
```

### Instruction Properties

| Property          | Type    | Description                                 |
|-------------------|---------|---------------------------------------------|
| `Sensor`          | Object  | Condition to match                          |
| `Actions`         | Array   | Actions to execute                          |
| `ActionsBlocking` | Boolean | Block until actions complete                |
| `BodyMotion`      | Object  | Body movement behavior                      |
| `HeadMotion`      | Object  | Head/aiming behavior                        |
| `Instructions`    | Array   | Child instructions                          |
| `Continue`        | Boolean | Continue to next instruction after matching |
| `Enabled`         | Compute | Conditionally enable instruction            |
| `Weight`          | Number  | Weight for random selection                 |
| `Reference`       | String  | Reference to a Component                    |
| `Modify`          | Object  | Modifications when using Reference          |

---

## Sensors

Sensors are conditions that trigger instructions.

### Any

Always matches.

```json
{
  "Type": "Any"
}
{
  "Type": "Any",
  "Once": true
}  // Match only once
```

### Player

Detects players within range.

```json
{
  "Type": "Player",
  "Range": 15,
  "LockOnTarget": true,
  "ViewSector": 180
}
```

### Target

Checks for locked target.

```json
{
  "Type": "Target",
  "Range": 5,
  "AutoUnlockTarget": true,
  "Filters": [
    {
      "Type": "LineOfSight"
    }
  ]
}
```

### State

Matches current state.

```json
{
  "Type": "State",
  "State": "Idle"
}
{
  "Type": "State",
  "State": ".Attack"
}  // Sub-state
{
  "Type": "State",
  "State": ".Default"
}
```

### Timer

Timer-based conditions.

```json
{
  "Type": "Timer",
  "Name": "AttackCooldown",
  "State": "Stopped"
}
```

### Random

Random true/false with duration.

```json
{
  "Type": "Random",
  "TrueDurationRange": [
    1,
    2
  ],
  "FalseDurationRange": [
    3,
    3
  ]
}
```

### Damage

Detects damage events.

```json
{
  "Type": "Damage",
  "CombatDamage": true,
  "EnvironmentDamage": false,
  "TargetSlot": "LockedTarget"
}
```

### DroppedItem

Detects items on ground.

```json
{
  "Type": "DroppedItem",
  "Range": 10,
  "Attitudes": [
    "Love"
  ],
  "Items": [
    "*Axe*"
  ]
}
```

### Block

Detects specific blocks.

```json
{
  "Type": "Block",
  "Range": 20,
  "Blocks": "Grass",
  "Random": true
}
```

### SearchRay

Ray-cast block detection.

```json
{
  "Type": "SearchRay",
  "Name": "Ray",
  "Range": 10,
  "Angle": 45,
  "Blocks": "Grass"
}
```

### Path

Detects patrol paths.

```json
{
  "Type": "Path",
  "Path": "Test_Path",
  "Range": 10
}
```

### Mob

Detects mobs (players/NPCs).

```json
{
  "Type": "Mob",
  "GetPlayers": true,
  "GetNPCs": true,
  "Range": 10,
  "Filters": [
    ...
  ]
}
```

### Self

Checks own properties.

```json
{
  "Type": "Self",
  "Filters": [
    {
      "Type": "Stat",
      "Stat": "Health",
      "StatTarget": "Value",
      "RelativeTo": "Health",
      "RelativeToTarget": "Max",
      "ValueRange": [
        0,
        0.5
      ]
    }
  ]
}
```

### Beacon

Receives beacon messages.

```json
{
  "Type": "Beacon",
  "Message": "TrorkEnemySighted",
  "TargetSlot": "LockedTarget",
  "ConsumeMessage": false
}
```

### Leash

Distance from spawn point.

```json
{
  "Type": "Leash",
  "Range": 30
}
```

### FlockLeader

Detects flock leader.

```json
{
  "Type": "FlockLeader"
}
```

### CanInteract / HasInteracted

Interaction state checks.

```json
{
  "Type": "CanInteract"
}
{
  "Type": "HasInteracted"
}
```

### HasTask

Task state checks.

```json
{
  "Type": "HasTask",
  "TasksById": [
    "task_1",
    "task_2"
  ]
}
```

### Logic Sensors

```json
// AND
{
  "Type": "And",
  "Sensors": [
    {
      "Type": "Player",
      "Range": 10
    },
    {
      "Type": "Target"
    }
  ]
}

// OR
{
  "Type": "Or",
  "Sensors": [
    {
      "Type": "State",
      "State": "Idle"
    },
    {
      "Type": "State",
      "State": "Patrol"
    }
  ]
}

// NOT
{
  "Type": "Not",
  "Sensor": {
    "Type": "Target"
  }
}
```

---

## Actions

Actions define what the NPC does when sensors match.

### State

Change state.

```json
{
  "Type": "State",
  "State": "Combat"
}
{
  "Type": "ParentState",
  "State": "Attack"
}
```

### Attack

Execute attack.

```json
{
  "Type": "Attack",
  "Attack": "Trork_Warrior_Battleaxe_Swing_Left",
  "AttackPauseRange": [
    1,
    2
  ],
  "AimingTimeRange": [
    3,
    3
  ]
}
```

### PlayAnimation

Play animation.

```json
{
  "Type": "PlayAnimation",
  "Slot": "Status",
  "Animation": "Alerted"
}
```

### Timeout

Wait/delay with optional action.

```json
{
  "Type": "Timeout",
  "Delay": [
    2,
    3
  ],
  "Action": {
    "Type": "State",
    "State": "Idle"
  }
}
```

### Timer Actions

```json
{
  "Type": "TimerStart",
  "Name": "Cooldown",
  "StartValueRange": [
    5,
    5
  ],
  "RestartValueRange": [
    5,
    5
  ]
}
{
  "Type": "TimerStop",
  "Name": "Cooldown"
}
{
  "Type": "TimerRestart",
  "Name": "Cooldown"
}
{
  "Type": "TimerContinue",
  "Name": "Cooldown"
}
```

### SetStat

Modify stats.

```json
{
  "Type": "SetStat",
  "Stat": "Health",
  "Value": -50,
  "Add": true
}
```

### DisplayName

Change displayed name.

```json
{
  "Type": "DisplayName",
  "DisplayName": "Combat Mode"
}
```

### SpawnParticles

Spawn particle effects.

```json
{
  "Type": "SpawnParticles",
  "Offset": [
    0,
    2.1,
    0
  ],
  "ParticleSystem": "Alerted"
}
```

### Beacon

Send message to nearby NPCs.

```json
{
  "Type": "Beacon",
  "Range": 30,
  "Message": "TrorkEnemySighted",
  "TargetGroups": [
    "Trork"
  ],
  "SendTargetSlot": "LockedTarget"
}
```

### Inventory

Inventory operations.

```json
{
  "Type": "Inventory",
  "Operation": "EquipHotbar",
  "Slot": 0,
  "UseTarget": false
}
```

### PickUpItem

Pick up dropped items.

```json
{
  "Type": "PickUpItem",
  "Once": true,
  "Range": 1
}
```

### SetLeashPosition

Set leash anchor point.

```json
{
  "Type": "SetLeashPosition",
  "ToCurrent": true
}
```

### OverrideAttitude

Temporarily change attitude.

```json
{
  "Type": "OverrideAttitude",
  "Attitude": "Hostile",
  "Duration": 120
}
```

### SetInteractable

Enable/disable interactions.

```json
{
  "Type": "SetInteractable",
  "Interactable": true
}
```

### CompleteTask

Complete a task.

```json
{
  "Type": "CompleteTask",
  "Slot": "Status"
}
```

### ResetBlockSensors

Reset block sensor cache.

```json
{
  "Type": "ResetBlockSensors",
  "BlockSets": [
    "Gaia"
  ]
}
```

### Nothing

Explicit no-op.

```json
{
  "Type": "Nothing"
}
```

---

## Body Motion

Body motion defines NPC movement behavior.

### Nothing

Stay still.

```json
{
  "Type": "Nothing"
}
```

### Wander

Random exploration.

```json
{
  "Type": "Wander",
  "RelativeSpeed": 0.45,
  "MinWalkTime": 2,
  "MaxHeadingChange": 1,
  "RelaxedMoveConstraints": false
}
```

### WanderInCircle

Wander within a radius.

```json
{
  "Type": "WanderInCircle",
  "Radius": 10,
  "MaxHeadingChange": 60,
  "RelativeSpeed": 0.45,
  "MinWalkTime": 1
}
```

### Seek

Move toward target.

```json
{
  "Type": "Seek",
  "SlowDownDistance": 3,
  "StopDistance": 1,
  "RelativeSpeed": 0.5
}
```

### Flee

Move away from target.

```json
{
  "Type": "Flee"
}
```

### Find

Pathfinding to target.

```json
{
  "Type": "Find",
  "UsePathfinder": true,
  "RelativeSpeed": 0.8
}
```

### MaintainDistance

Keep specific distance from target.

```json
{
  "Type": "MaintainDistance",
  "DesiredDistanceRange": [
    3,
    5
  ],
  "MoveThreshold": 0.1,
  "RelativeForwardsSpeed": 0.45,
  "RelativeBackwardsSpeed": 0.25
}
```

### Path

Follow predefined path.

```json
{
  "Type": "Path",
  "UseNodeViewDirection": true,
  "MinRelSpeed": 0.35,
  "MaxRelSpeed": 0.35,
  "Shape": "Line",
  "ViewSegments": 1
}
```

### Sequence

Chain multiple motions.

```json
{
  "Type": "Sequence",
  "Looped": true,
  "Motions": [
    {
      "Type": "Timer",
      "Time": [
        3,
        6
      ],
      "Motion": {
        "Type": "Wander",
        "RelativeSpeed": 0.45
      }
    },
    {
      "Type": "Timer",
      "Time": [
        1,
        2
      ],
      "Motion": {
        "Type": "Nothing"
      }
    }
  ]
}
```

---

## Head Motion

Head motion controls where the NPC looks.

### Aim

Look at target.

```json
{
  "Type": "Aim",
  "RelativeTurnSpeed": 1.5
}
```

### Watch

Watch target without following.

```json
{
  "Type": "Watch"
}
```

### Observe

Look around randomly.

```json
{
  "Type": "Observe",
  "AngleRange": [
    -90,
    90
  ],
  "PauseTimeRange": [
    1,
    1
  ],
  "ViewSegments": 1,
  "RelativeTurnSpeed": 0.5
}
```

### Sequence

Chain head motions.

```json
{
  "Type": "Sequence",
  "Looped": true,
  "Motions": [
    {
      "Type": "Timer",
      "Time": [
        4,
        5
      ],
      "Motion": {
        "Type": "Observe"
      }
    },
    {
      "Type": "Timer",
      "Time": [
        3,
        5
      ],
      "Motion": {
        "Type": "Nothing"
      }
    }
  ]
}
```

---

## Filters

Filters narrow down sensor matches.

### LineOfSight

Requires clear line of sight.

```json
{
  "Type": "LineOfSight"
}
```

### NPCGroup

Filter by NPC group membership.

```json
{
  "Type": "NPCGroup",
  "IncludeGroups": [
    "Trork"
  ],
  "ExcludeGroups": [
    "Trork_Prisoner"
  ]
}
```

### Stat

Filter by stat values.

```json
{
  "Type": "Stat",
  "Stat": "Health",
  "StatTarget": "Value",
  "RelativeTo": "Health",
  "RelativeToTarget": "Max",
  "ValueRange": [
    0,
    0.5
  ]
}
```

### Combat

Filter by combat state.

```json
{
  "Type": "Combat",
  "Mode": "Charging"
}
```

### Flock

Filter by flock status.

```json
{
  "Type": "Flock",
  "FlockStatus": "Follower"
}
```

### Attitude

Filter by attitude.

```json
{
  "Type": "Attitude",
  "Attitudes": [
    "Hostile"
  ]
}
```

---

## Components & References

Components are reusable snippets that can be referenced.

### Defining a Component

```json
{
  "Type": "Component",
  "Class": "Instruction",
  "Interface": "Hytale.Instruction.AttackSequence",
  "Parameters": {
    "AttackDistance": {
      "Value": 3,
      "Description": "Attack range"
    }
  },
  "Content": {
    "Sensor": {
      "Type": "Target",
      "Range": {
        "Compute": "AttackDistance"
      }
    },
    "ActionsBlocking": true,
    "Actions": [
      {
        "Type": "Attack",
        "Attack": "Sword_Swing"
      }
    ]
  }
}
```

### Using a Reference

```json
{
  "Reference": "Component_Instruction_Attack_Sequence_Trork_Warrior",
  "Modify": {
    "AttackDistance": 5
  }
}
```

### State Import/Export

Components can import and export states for communication:

```json
{
  "Parameters": {
    "_ImportStates": [
      "Idle",
      "Attack",
      "Search"
    ],
    "_ExportStates": [
      "Combat",
      "Flee"
    ]
  }
}
```

---

## Complete Example

```json
{
  "Type": "Generic",
  "StartState": "Idle",
  "DefaultPlayerAttitude": "Hostile",
  "DefaultNPCAttitude": "Neutral",
  "Parameters": {
    "MaxHealth": {
      "Value": 100,
      "Description": "Max health"
    },
    "ViewRange": {
      "Value": 15,
      "Description": "Detection range"
    },
    "AttackDistance": {
      "Value": 2.5,
      "Description": "Melee range"
    }
  },
  "Appearance": "Guard",
  "MaxHealth": {
    "Compute": "MaxHealth"
  },
  "HotbarItems": [
    "Weapon_Sword_Iron"
  ],
  "DropList": "Drop_Guard",
  "MotionControllerList": [
    {
      "Type": "Walk",
      "MaxWalkSpeed": 5,
      "Gravity": 10,
      "MaxFallSpeed": 15,
      "Acceleration": 10
    }
  ],
  "Instructions": [
    {
      "Instructions": [
        {
          "Sensor": {
            "Type": "State",
            "State": "Idle"
          },
          "Instructions": [
            {
              "Sensor": {
                "Type": "Player",
                "Range": {
                  "Compute": "ViewRange"
                },
                "LockOnTarget": true,
                "Filters": [
                  {
                    "Type": "LineOfSight"
                  }
                ]
              },
              "Actions": [
                {
                  "Type": "State",
                  "State": "Combat"
                }
              ]
            },
            {
              "Sensor": {
                "Type": "Any"
              },
              "BodyMotion": {
                "Type": "Wander",
                "RelativeSpeed": 0.3,
                "MinWalkTime": 3
              }
            }
          ]
        },
        {
          "Sensor": {
            "Type": "State",
            "State": "Combat"
          },
          "Instructions": [
            {
              "Sensor": {
                "Type": "Target",
                "Range": {
                  "Compute": "AttackDistance"
                },
                "Filters": [
                  {
                    "Type": "LineOfSight"
                  }
                ]
              },
              "HeadMotion": {
                "Type": "Aim"
              },
              "ActionsBlocking": true,
              "Actions": [
                {
                  "Type": "Attack",
                  "Attack": "Guard_Sword_Swing",
                  "AttackPauseRange": [
                    1,
                    2
                  ]
                }
              ]
            },
            {
              "Sensor": {
                "Type": "Target",
                "Range": {
                  "Compute": "ViewRange"
                }
              },
              "HeadMotion": {
                "Type": "Aim"
              },
              "BodyMotion": {
                "Type": "Find",
                "UsePathfinder": true,
                "RelativeSpeed": 0.8
              }
            },
            {
              "Sensor": {
                "Type": "Any"
              },
              "Actions": [
                {
                  "Type": "State",
                  "State": "Idle"
                }
              ]
            }
          ]
        }
      ]
    }
  ],
  "NameTranslationKey": "server.npcRoles.Guard.name"
}
```
