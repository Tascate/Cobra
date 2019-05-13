# To-Do 
- Properly end the game (End Screen, stop progressGame())
- Properly restart the game
- Program AI
- Create menu screen
- Add in power-up items (increase trailing light length, faster player?)

# Code Overview:
## Cobra.java
- RENDER THE GAME
- PACE THE GAME
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
