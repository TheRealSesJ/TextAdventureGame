# Text Adventure (TAG):
- Text Adventure or "TAG" is a 2d text based open world game, with minimal visuals.
- The commands possible for the player on a given turn are listed below under ``Command List``.
- The static game data is user configurable, more on this under ``Config``.



## Command List:



### Normal Commands:

- **scan**: gives the user information about what is in the current scene

- **scan (x) (y)**: gives the user information about what is in the scene at **(x,y)** relative to the player

- **interact**: if player is in a scene with an NPC, player enters NPC menu

- **minimap**: displays the minimap (does not use a turn)

- **enter**: if player is on a dungeon tile enter the dungeon

- **exit**: if player is inside a dungeon exit the dungeon and return to the world center (2,2)

- **stats**: displays the stats of the player (does not use a turn)

- **help**: displays this command list (does not use a turn)

- **end_game**: ends the game

​

### World Commands:

- **move (x) (y)**: moves the player to the scene at **(x,y)** relative to the player

- **grab_item**: equips the item in the current scene to the player, the player drops its old item

- **grab_weapon**: equips the weapon in the current scene to the player, the player drops its old weapon

- **grab_cons**: equips the consumable in the current scene to the player, the player drops its old consumable

- **use**: uses the players consumable and removes it from the player

​

### Combat Commands:

- **fight**: takes a combat turn against the enemy

- **run**: attempts to flee the scene to a nearby scene



## Notes

- all coordinates are assumed to be Cartesian ordered pairs, with **(0,0)** being the worlds southwest corner
- some commands require additional arguments labeled``()`` such as ``move`` do not use parentheses when entering the command in game
- **The JVM needs to be installed to run the tag.jar file**
- To run the game, execute the ``run_game.bat`` file
- Alternatively, to run the game in specific console, navigate the the main project directory and execute the command 
  ````
  java -jar build\libs\tag.jar
  ````
- the minimap is designed to be read in 3 by 3 character tiles called "scenes"
  - Example:
    ````
    ^ ^ ^ ^ ^ ^ . . .
    ^ ^ ^ ^ ^ ^ . . .
    ^ ^ ^ ^ ^ ^ . . .
    t t t . . . . . .
    t t t . @ . . . .
    t t t . . . . . .
    ~ ~ ~ ~ ~ ~ . . .
    ~ ~ ~ ~ ~ ~ . . .
    ~ ~ ~ ~ ~ ~ . . .
    ````
  - this is a 3x3 grid of scenes; each scene is 9 characters
  - to get more data on the scene you are in, use the scan command
  - use **scan** with **(x) (y)** input arguments to scan nearby tiles



## Config Information:



### Config.json
- Controls static game data about how the world is built and the stats of all of the game objects
- user configurable without recompiling ``tag.jar``
- To add new ``GameObjects``, define them in their respective json location, then reference them in the world's ``GameObject`` arrays
  - Example:
    ````
    "wild_beast":{
            "hp":"25",
            "weapon":"claws",
            "armor":"3",
            "canMove": "true",
            "namePool": "evil_beast",
            "xp": "10",
            "id":"Wild Beast"
        },
    ````
- this is the wild beast definition in the "enemy_stats" area
  - to add it to the game, reference it in the worlds "enemies array"
    ````
    "enemies":["wild_beast","wild_beast","wild_beast","witch],
    ````
  - as an example, this adds 3 wild beasts to the game world and one witch



### NameList.txt
- The name list document controls the names given to entities
- entity name sections are headed with a referencable title in ```[]```
- ``[evil_beast]`` which can be seen in the wild beast declaration is an example
- after a ```[title]``` in ``NameList.txt`` you can list the possbile names for entities with this "namepool"
  - Example:
    ```
    [evil_beast]
    Azgour
    Kazul
    Baehrog
    Jezeab
    Ilniae
    //this is a comment
    ```
- Comment declaration is also allowed in this file with a //