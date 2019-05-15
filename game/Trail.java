
package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Trail class to make the trail of the player when it is currently moving.
 */
public class Trail extends FieldObject {
	int length; //length of trail
	int width; //Can change with Items/Specials later? // width of trail
	
	/**
	 * Trail constructor to construct the trail of the player and set length to 7 and width to 1.
	 * @param givenRow - given row
	 * @param givenColumn - given column
	 * @param c - color
	 * @param a - angle
         */	
	public Trail(int givenRow, int givenColumn, Color c, int a) {
		row = givenRow;
		col = givenColumn;
		
		light = c;
		angle = a;
		length = 7;
		width = 1;
		
	}
	
	/**
	 * Method to see if it is an actual character or not
	 * @return true if it is an actual character, false otherwise
	 */
	public boolean isCharacter() {
		return false;
	}
}
