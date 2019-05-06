package com.mygdx.game;

import java.awt.Point;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Cobra extends ApplicationAdapter {
	ShapeRenderer shapeRender;
	FieldObject[][] grid;
	Point char1;
	Point char2;
	Boolean paused;
	int row = 100;
	int col = 100;
	
	@Override
	public void create () {
		shapeRender = new ShapeRenderer();
		paused = false;
		
		grid = new FieldObject[Gdx.graphics.getWidth()][Gdx.graphics.getHeight()];
		grid[3][6] = new Player(5,new Color(0,0,1,1), 90);
		char1 = new Point(3,6);
		grid[9][6] = new Player(5,new Color(1,0,0,1), 90);
		char2 = new Point(9,6);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		checkForInput();
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(row, col, 5);
		shapeRender.end();
	}
	
	@Override
	public void dispose () {
		shapeRender.dispose();
	}
	
	private void checkForInput() {
		int row = (int)char1.getX();
		int col = (int)char1.getY();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            grid[row][col].setAngle(180);
            System.out.println("Left!");
            this.row -= 4; //testing moving circle
        }
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            grid[row][col].setAngle(0);
            System.out.println("Right!");
            this.row += 4;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            grid[row][col].setAngle(90);
            System.out.println("Up!");
            this.col += 4;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            grid[row][col].setAngle(270);
            System.out.println("Down!");
            this.col -= 4;
        }
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			pauseGame();
		}
	}
	
	private void pauseGame() {
		paused = !paused;
	}
	
}
