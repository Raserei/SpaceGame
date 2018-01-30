package ru.raserei.spacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpaceGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture playerShip;
	float shipVelocity = 100.0f;
	float shipXpos;
	short dir;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playerShip = new Texture("player_ship.png");
		background = new Texture("bg.jpg");
		shipXpos = 10;
		dir=1;
	}

	@Override
	public void render () {

		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0.1f, 0.15f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(playerShip,shipXpos,10);
		batch.end();
	}

	private void update(float dt){
		if (shipXpos>=Gdx.graphics.getWidth()-playerShip.getWidth()) {
			dir=-1;
		}
		if (shipXpos<=0) {
			dir=1;
		}
		shipXpos+=shipVelocity*dt*dir;
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		playerShip.dispose();
	}
}
