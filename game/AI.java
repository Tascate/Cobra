package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

/**
 * AI class to make the AI, which is the opponent.
 */
public class AI extends Character {
	int wins; // number of wins
	
	/**
	 * AI constructor to create the AI, which is the opponent of the other player, and sets their wins to 0.
	 * @param s - speed of AI
	 * @param c - color of AI
	 * @param a - angle of AI
	 */
	public AI(int s,Color c,int a) {
		super(s,c,a);
		wins = 0;
	}
	//AI to be implemented later
}
