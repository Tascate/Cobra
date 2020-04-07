# Simple Game Project made for fun, 
# A mesh of Tron & Snake
 - Defeat your opponent by forcing the opponent to crash into you or the wall.
 - Acquire power-ups to help you in this goal!
 - 2-player as well as CPU mode
 - Gameplay is fully functional
-  Item pickups that empower you
-  Simple AI with different states
-  Uses libgdx for drawing the game
  

# Code Overview:
## Cobra.java
- Responsible for the Game State
- Renders the game
- Grid Instance that Objects are moving along
- FieldObject Two-Dimensional Array
- Contains either a Trail or Character
- Render a Grid showing moving Characters
- Checks when a Player collides with Character/line
- Checks for Player Input and sets Direction of Player
- Input Processing
- Pauses
- Sends Input to Player Changing Direction
- Tie when both players are facing each other
- Time Counter
## FieldObject.java abstract
- Color
- Direction (Angle)
- Return color / direction
## Trail.java extends FieldObject.java
- Int Varaible declaring whether its Player 1 or 2
- Return owner
## Character.java extends FieldObject.java
- Direction (Abstract)
- Color (Abstract)
- Alive
- Speed
- Leaves trailing light behind
- Specials/Item Interactions (WIP Implemented later)
## Player.java extends Character.java
- Processes input & Changes Direction
## AI.java extends Character.java
- AI that controls Player’s Opponent (Implemented Later)
- Changes Direction of its own accord
