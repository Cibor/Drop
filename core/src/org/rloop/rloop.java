package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class rloop extends Game {
	SpriteBatch batch;

	OurMusic ourMusic;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ourMusic = new OurMusic();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public OurMusic getOurMusic() { return ourMusic; }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
