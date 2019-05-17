package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Abstract class for what things we will have inside our grid, like the characters and the trail along with other features like the color
 * angle, speed of character.
 */
public abstract class FieldObject {
	protected Color light; //color of character
	protected int angle; //angle of fieldObject, where they changed directions
	protected int row; //row of the FieldObject
	protected int col; //column of the FieldObject
	
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
	 * Method to set the row of grid to the passed in value for row.
	 * @param theRow - row of grid 
	 */
	public void setRow(int theRow) {
		row = theRow;
	}
	
	/**
	 * Method to set the column of grid to the passed in value for column.
	 * @param theColumn - column of grid 
	 */
	public void setCol(int theColumn) {
		col = theColumn;
	}
	
	/**
	 * Method to get the row.
	 * @return row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Method to get the column.
	 * @return column.
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Abstract boolean method to see if it's an actual character or not. This is to be implemented
	 * in the child class.
	 * @return true if it's a character, false otherwise
	 */
	public abstract boolean isCharacter();
}
