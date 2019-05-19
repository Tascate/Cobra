package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Abstract class for what things we will have inside our grid, like the characters and the trail along with other features like the color
 * angle, speed of character.
 */
public abstract class FieldObject {
	protected Color light; //color of character
	protected int direction; //direction of fieldObject, where they changed directions
	//1 - up
	//2 - right
	//3 - down
	//4 - left
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
	 * @return direction of character, which is where they changed directions.
	 */	
	public int getDirection() {
		return direction;
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
	public boolean setDirection(int d) {
		switch (d) {
		case 1:
			if (direction == 3) {
				return false;
			}
			break;
		case 2:
			if (direction == 4) {
				return false;
			}
			break;
		case 3:
			if (direction == 1) {
				return false;
			}
			break;
		case 4:
			if (direction == 2) {
				return false;
			}
			break;
		}
		direction = d;
		return true;
	}
	/**
	*Returns the opposite direction
	*@return opposite direction
	*/
	public int getOppositeDirection() {
		switch (direction) {
			case 1:
				return 3;
			case 2:
				return 4;
			case 3:
				return 1;
			case 4:
				return 2;
		}
		System.out.println("Error: Direction varaible is not in the range of 1-4.");
		return 0;
	}
	/**
	*Returns the right direction
	*@return the right direction
	*/
	public int getRightDirection() {
		switch (direction) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 4;
		case 4:
			return 1;
		}
		System.out.println("Error: The Direction varaible is not in the range of 1-4.");
		return 0;
	}
	/**
	*Returns the left direction
	*@return the left direction
	*/
	public int getLeftDirection() {
		switch (direction) {
		case 1:
			return 4;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		}
		System.out.println("Error: The Direction varaible is not in the range of 1-4.");
		return 0;
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
