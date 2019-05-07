package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
*Class that calls the methods to run the Cobra game. 
*Extends the ApplicationAdapter class.
*/
public class Cobra extends ApplicationAdapter {
	ShapeRenderer shapeRender;
	Game round;
	
	@Override
	//Instantiates game
	public void create () {
		shapeRender = new ShapeRenderer();
		round = new Game(20,20);
	}

	@Override
	//Runs this method every frame
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		round.progressGame();
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(100, 100, 5);
		shapeRender.end();
	}
	
	@Override
	//Disposes of objects after Quitting
	public void dispose () {
		shapeRender.dispose();
	}
	
	
}
