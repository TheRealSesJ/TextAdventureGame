# TextAdventure

the game has a 2d array of scenes that the player exists it
there are items and enemies scattered around the map
the player can advenutre to each of the different biomes to find them
I plan on adding more interfaces for simplicity in the future, the only one that exists is entity
All objects are organized into packagaes based on their purpose and superclass
GameParamters is a static class that contains all arbitrary game constants
this class is where the main method is located
this is static game code that is not player initiated
GameController is player caused code that is mapped from inputs gathered in this class (below)
Only this class and GameController are "allowed" to print to the console

## Command List:
      
### Normal Commands: 
scan: gives the user information about what is in the current scene
scan <x> <y>: gives the user information about what is in the scene at (x,y) relative to the player
move <x> <y>: moves the player to the scene at (x,y) relative to the player
interact <object>: gives the player information about the <enemy> or <item> in the current scene
minimap: displays the minimap (does not use a turn)
stats: displays the stats of the player (does not use a turn)
help: displays this command list (does not use a turn)    
grab_item: equips the item in the current scene to the player, the player drops its old item

### Combat Commands:
fight: takes a combat turn against the enemy
run: attempts to flee the scene to a nearby scene
scan: gives the user information about what is in the current scene
scan <x> <y>: gives the user information about what is in the scene at (x,y) relative to the player
interact <object>: gives the player information about the <enemy> or <item> in the current scene
minimap: displays the minimap (does not use a turn)
stats: displays the stats of the player (does not use a turn)
help: displays this command list (does not use a turn)
    
### all coordinates are assumed to be Cartesian ordered pairs