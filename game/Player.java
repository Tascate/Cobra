package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

public class Player extends Character {
	int wins;
	int losses;
	public Player(int s,Color c,int a) {
		super(s,c,a);
		wins = 0;
	}
}
