package pl.ciborowski.konrad;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); // important!
    }

    @Override
    public void dispose() {
        System.out.println("Usuwam");
        batch.dispose();
        font.dispose();
    }

}
