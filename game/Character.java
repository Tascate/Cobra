package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

public class Character extends FieldObject {
	protected int speed;
	protected boolean alive;
	
	public Character(int s, Color c, int a) {
		speed = s;
		light = c;
		angle = a;
		alive = true;
	}
	public Trail leaveTrail() {
		return new Trail(light, angle);
	}
	
	public void die() {
		alive = false;
	}
}
