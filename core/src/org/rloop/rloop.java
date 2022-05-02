package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class rloop extends Game {
	SpriteBatch batch;

	float AudioVolume = 1;
	float MusicVolume = 1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void dispose () {
	}
}
