package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Cobra extends ApplicationAdapter {
	ShapeRenderer shapeRender;
	Game round;
	
	@Override
	//Instantiates game
	public void create () {
		shapeRender = new ShapeRenderer();
		round = new Game(75,75);
	}

	@Override
	//Runs this method every frame
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		round.progressGame();
		
		
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(Color.GRAY);
		shapeRender.rect(100, 100, 150, 150);
		shapeRender.end();
		
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.GREEN);
		for (int i = 0; i < round.getGrid().length; i++) {
			for(int j = 0; j <round.getGrid()[i].length; j++) {
				if(round.getGrid()[i][j] != null)
				shapeRender.rect(100+i, 100+j, 2, 2);
			}
		}
		shapeRender.end();
		
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(100+round.getChar1Row(), 100+round.getChar1Col(), 2);
		shapeRender.end();
	}
	
	@Override
	//Disposes of objects after Quitting
	public void dispose () {
		shapeRender.dispose();
	}
	
	
}
