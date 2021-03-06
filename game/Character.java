package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Character class to make the characters in the game with the necessary features for the characters. 
 */
public class Character extends FieldObject {
	protected boolean alive;
	int wins;
	
	protected int speed; //speed of character
	protected int nextActionableFrame; 
	protected double trailMultiplier;
	
	protected int rowOfTail;
	protected int colOfTail;
	public int tailDirection;
	protected boolean noTail;
	protected int trailLength;
	private Trail[] trailPool;
	
	/**
	* Constructor to initialize the instance variable to the passed in values to make the character and add their features.
	*@param theRow - row of character
	*@param theColumn - column of character
	*@param s - speed of character
	*@param c - color of the character
	*@param d - direction of character
	*/
	public Character(int theRow, int theColumn, int s, Color c, int d) {
		row = theRow;
		col = theColumn;
		speed = s;
		light = c;
		direction = d;
		nextActionableFrame = 1;
		trailMultiplier = 1;
		wins = 0;
		
		noTail = true;
		alive = true;
		trailPool = new Trail[4];
		trailPool[0] = new Trail(row,col,light,1);
		trailPool[1] = new Trail(row,col,light,2);
		trailPool[2] = new Trail(row,col,light,3);
		trailPool[3] = new Trail(row,col,light,4);
	}
	
	/**
	 * Trail method to return the trail that is left behind from the character when the character is currently moving.
	 *@param row - row to leave trail
	 *@param col - column to leave trail
	 *@return the trail left behind from the character along with their color and angle at which they are moving.
	 */
	public Trail leaveTrail(int row, int col) {
		if (noTail) {
			rowOfTail = row;
			colOfTail = col;
			tailDirection = direction;
			noTail = false;
		}
		trailLength++;
		return trailPool[direction-1];
	}
	/**
	*Returns the row that the tail is in
	*@return row of tail
	*/
	public int getTailRow() {
		return rowOfTail;
	}
	
	/**
	*Returns the column that the tail is in
	*@return col of tail
	*/
	public int getTailCol() {
		return colOfTail;
	}
	/**
	*Returns the direction that the tail is in
	*@return direction of tail
	*/
	public int getTailDirection() {
		return tailDirection;
	}
	
	/**
	*Sets the row of the tail
	*@param row - row of tail
	*/
	public void setTailRow(int row) {
		rowOfTail = row;
	}
	
	/**
	*Sets the column of the tail
	*@param col - col of tail
	*/
	public void setTailCol(int col) {
		colOfTail = col;
	}
	/**
	*Sets the direction of the tail
	*@param d - direction of tail
	*/
	public void setTailDirection(int d) {
		tailDirection = d;
	}
	/**
	*returns the length of the tail
	*@return tail length
	*/
	public int getTrailLength() {
		return trailLength;
	}
	
	/**
	*Decreases the tail length
	*/
	public void decrementTrailLength() {
		trailLength--;
	}
	
	/**
	 * Method to set the boolean instance variable alive to false, indicating that the character has died.		
	 */
	public void die() {
		alive = false;
	}
	/**
	*Sets the next actionable frame
	*@param frame - next frame
	*/
	public void setNextActionableFrame(int frame) {
		nextActionableFrame = frame;
	}
	/**
	*gets the next actionable frame
	*@return next actionable frame
	*/
	public int getNextActionableFrame() {
		return nextActionableFrame;
	}
	/**
	*sets the trail multiplier
	*@param multiplier - multiplier of tail
	*/
	public void setTrailMultiplier(int multiplier) {
		trailMultiplier = multiplier;
	}
	/**
	*gets the multiplier of tail
	*@return multiplier of tail
	*/
	public double getTrailMultiplier() {
		return trailMultiplier;
	}
	
	/**
	 * Method to get the speed of character.
	 * @return speed of character
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	* Method that sets the speed of the character passed in value.
	* @param s - speed value
	*/
	public void setSpeed(int s) {
		speed = s;
	}
	
	/**
	 *Boolean method to return the life status of the character, whether the character is still alive or has died.
	 *@return whether the character is still alive or not(true if it is, false otherwise).
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Method to return true if the character is an actual character in the game, false otherwise.
	 * @return true if character is an actual character in game, false otherwise.
	 */
	public boolean isCharacter() {
		return true;
	}
}
