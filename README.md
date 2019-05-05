# Cobra Game Code
## Project Plan Overview:
### Cobra.java
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
### FieldObject.java abstract
#### Variables
- Color
- Direction (Angle)
#### Methods
- Return color / direction
### Trail.java extends FieldObject.java
- Int Varaible declaring whether its Player 1 or 2
- Return owner
### Character.java extends FieldObject.java
####Variables
- Direction (Abstract)
- Color (Abstract)
- Alive
- Speed
#### Methods
- Leaves trailing light behind
- Specials/Item Interactions (WIP, Implemented later)
### Player.java extends Character.java
- Processes input & Changes Direction
### AI.java extends Character.java
- AI that controls Player’s Opponent
- Changes Direction of its own accord
