package pl.ciborowski.konrad.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import static com.badlogic.gdx.utils.ScreenUtils.clear;
import pl.ciborowski.konrad.viewmodel.GameManager;

public class GameScreen implements Screen {

    public static final float HERO_WIDTH = 100;
    public static final float HERO_HEIGHT = 50;
    public static final float ENEMY_WIDTH = 100;
    public static final float ENEMY_HEIGHT = 50;

    public static final float CAMERA_WIDTH = 1000;
    public static final float CAMERA_HEIGHT = 1000;

    private OrthographicCamera camera;
    private Rectangle hero;
    private GameManager gameManager;
    private Game game;
    private SpriteBatch batch;

    public GameScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        game = gameManager.game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
    }

    @Override
    public void render(float delta) {
        clear(1, 1, 1, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (var shape : gameManager.getShapesAfterMove()) {
            batch.draw(shape.texture, shape.rectangle.x, shape.rectangle.y,
                    shape.rectangle.width, shape.rectangle.height);

        }
        batch.end();
////        
//        		if (Gdx.input.isKeyPressed(Keys.LEFT))
//			hero.x -= 200 * Gdx.graphics.getDeltaTime();
//		if (Gdx.input.isKeyPressed(Keys.UP))
//                    hero.y += 200 * Gdx.graphics.getDeltaTime();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

}
