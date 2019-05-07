package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Character class to make the characters in the game with the necessary features for the characters. 
 */
public class Character extends FieldObject {
	protected boolean alive;
	
	/**
	* Constructor to initialize the instance variable to the passed in values to make the character and add their features.
	*@param s - speed of character
	*@param c - color of the character
	*@param a - angle for where the character is changing directions, whether it's going up, down, left, or right.
	*/
	public Character(int s, Color c, int a) {
		speed = s;
		light = c;
		angle = a;
		alive = true;
	}
	
	/**
	 * Trail method to return the trail that is left behind from the character when the character is currently moving.
	 *@return the trail left behind from the character along with their color and angle at which they are moving.
	 */
	public Trail leaveTrail() {
		return new Trail(light, angle);
	}
	/**
	 * Method to set the boolean instance variable alive to false, indicating that the character has died.		
	 */
	public void die() {
		alive = false;
	}
	/**
	 *Method to return the speed of the character.
	 *@return speed of characterr.
	 */
	public int returnSpeed() {
		return speed;
	}
	
	/**
	 *Boolean method to return the left status of the character, whether the character is still alive or has died.
	 *@return whether the character is still alive or not(true if it is, false otherwise).
	 */
	public boolean returnLifeStatus() {
		return alive;
	}
}
