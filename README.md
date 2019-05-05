Cobra Game Code
Project Plan Overview:
Cobra.java
- Grid Instance that Objects are moving along
FieldObject Two-Dimensional Array
Contains either a Trail or Character
Render a Grid showing moving Characters
Checks when a Player collides with Character/line
Checks for Player Input and sets Direction of Player
Input Processing
Pauses
Sends Input to Player Changing Direction
Tie when both players are facing each other
Time Counter
FieldObject.java abstract
Abstract Color
Abstract Direction (Angle)
Abstract Return color / direction
Trail.java extends Object.java
Int Varaible declaring whether its Player 1 or 2
Return owner
Character.java extends Object.java
Variables
Direction
Alive
Speed
Color
Leaves trailing light behind
Player.java extends Character.java
Processes for input
Change Direction
Pause
Use Special/Items (WIP Implemented after Core Gameplay)
AI.java extends Character.java
AI that controls Player’s Opponent
Changes Direction of its own accord
