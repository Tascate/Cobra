package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Abstract class for what things we will have inside our grid, like the characters and the trail along with other features like the color
 * angle, speed of character.
 */
public abstract class FieldObject {
	protected Color light; //color of character
	protected int angle; //angle of character, where they changed directions
	protected int speed; //speed of character
	
	/**
	 * Method to return the color of the character in the game.
	 *@return color of character in game.
	 */
	public Color getColor() {
		return light;
	}
	
	/**
	 * Method to return the angle of the character, where they changed directions.
	 * @return angle of character, which is where they changed directions.
	 */	
	public int getAngle() {
		return angle;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Method to set the color of character to the passed in color.
	 * @param c - color
	 */
	public void setColor(Color c) {
		light = c;
	}
	
	/**
	 * Method to set the angle of the character to the passed in value.
	 * @param a - angle value
         */	
	public void setAngle(int a) {
		angle = a;
	}
	/**
	* Method that sets the speed of the character passed in value.
	* @param s - speed value
	*/
	public void setSpeed(int s) {
		speed = s;
	}
	
	public abstract boolean isCharacter();
	
	
}
