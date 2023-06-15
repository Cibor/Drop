package pl.ciborowski.konrad;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import static com.badlogic.gdx.utils.ScreenUtils.clear;

public class GameScreen implements Screen {

    private final Drop game;
    private static final float CAMERA_WIDTH = 1000;
    private static final float CAMERA_HEIGHT = 1000;
    private static final float HERO_WIDTH = 50;
    private static final float HERO_HEIGHT = 25;

    private static final Texture heroImage  = new Texture(Gdx.files.internal("hero2.png"));
    
    private OrthographicCamera camera;
    private Rectangle hero;

    public GameScreen(final Drop game) {
        this.game = game;

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        hero = new Rectangle();
        hero.x = CAMERA_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = CAMERA_HEIGHT / 2 - HERO_HEIGHT / 2;
        
        hero.height = 64;
        hero.width = 64;

    }


    @Override
    public void render(float delta) {
        clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(heroImage, hero.x, hero.y, hero.width, hero.height);
        game.batch.end();
        
        
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
