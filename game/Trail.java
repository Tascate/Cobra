package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;

public class Trail extends FieldObject {
	int length;
	int width; //Can change with Items/Specials later?
	int decaySpeed;
	public Trail(Color c, int a) {
		light = c;
		angle = a;
		length = 7;
		width = 1;
		decaySpeed = 4;
	}
}
