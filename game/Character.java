package com.mygdx.game;

public class Character extends FieldObject {
	protected int speed;
	protected boolean alive;
	
	public Character(int s) {
		speed = s;
		alive = true;
	}
	public Trail leaveTrail() {
		return new Trail(light, angle);
	}
	
	public void die() {
		alive = false;
	}
}
