package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

public abstract class FieldObject {
	protected Color light;
	protected int angle;
	
	public Color getColor() {
		return light;
	}
	
	public int getAngle() {
		return angle;
	}
	
	public void setColor(Color c) {
		light = c;
	}
	
	public void setAngle(int a) {
		angle = a;
	}
	
	
	
}
