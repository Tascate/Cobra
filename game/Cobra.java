package com.mygdx.game;

import java.awt.Point;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cobra extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	FieldObject[][] grid;
	Point char1;
	Point char2;
	Boolean paused;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		paused = false;
		grid = new FieldObject[12][12];
		
		grid[3][6] = new Player(5,new Color(0,0,1,1), 90);
		char1 = new Point(3,6);
		
		grid[9][6] = new Player(5,new Color(1,0,0,1), 90);
		char2 = new Point(9,6);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		checkForInput();
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	private void checkForInput() {
		int row = (int)char1.getX();
		int col = (int)char1.getY();
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            grid[row][col].setAngle(180);
        }
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            grid[row][col].setAngle(0);
        }
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            grid[row][col].setAngle(90);
        }
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            grid[row][col].setAngle(270);
        }
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			pauseGame();
		}
	}
	
	private void pauseGame() {
		paused = !paused;
	}
	
}
