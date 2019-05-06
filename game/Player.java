package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * Player class to make a Player and get the features of that player such as their speed, color, and angle.
 */
public class Player extends Character {
	int wins; //# of wins for the player
	
	/**
	 * Constructor to create the player with their speed, color, and angle. It calls the constructor of the
	 * super class to get the speed, color, and angle of player. It also sets the number of wins to 0.
	 * @param s - speed
	 * @param c - color
	 * @param a - angle
	 */
	public Player(int s,Color c,int a) {
		super(s,c,a);
		wins = 0;
	}
}
