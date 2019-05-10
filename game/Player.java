package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Player class to make a Player and get the features of that player such as their speed, color, and angle.
 */
public class Player extends Character {
	int wins; //# of wins for the player
	
	/**
	 * Constructor to create the player with their speed, color, and angle. It calls the constructor of the
	 * super class to get the speed, color, and angle of player. It also sets the number of wins to 0 as a start.
	 * @param row - row of player
	 * @param col - column of player
	 * @param s - speed
	 * @param c - color
	 * @param a - angle
	 */
	public Player(int row, int col, int speed, Color color, int angle) {
		super(row, col, speed, color, angle);
		wins = 0;
	}
}
